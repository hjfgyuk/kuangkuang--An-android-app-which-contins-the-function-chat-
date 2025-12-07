package com.kuangkuang.kuangkuang.pojo.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kuangkuang.kuangkuang.mapper.GroupMapper;
import com.kuangkuang.kuangkuang.pojo.entity.Group;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class dragonTask {

    @Autowired
    private org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GroupMapper groupMapper;

    // 创建线程池（建议改为配置文件配置大小）
    private final ExecutorService asyncPool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2
    );

    @XxlJob("selectDragonKing")
    public void selectDragonKing() {
        try {
            XxlJobHelper.log("任务开始执行");
            log.info("=== 评选龙王任务开始 ===");

            // 获取分片参数
            int shardIndex = XxlJobHelper.getShardIndex();
            int shardTotal = XxlJobHelper.getShardTotal();

            // 获取昨天的日期key
            String dateKey = LocalDate.now().minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);

            XxlJobHelper.log("分片[{}/{}]，日期:{}", shardIndex + 1, shardTotal, dateKey);
            log.info("分片[{}/{}]，日期:{}", shardIndex + 1, shardTotal, dateKey);

            // 获取所有活跃群组
            List<String> allGroupIds = getActiveGroupIds(dateKey);
            XxlJobHelper.log("总群组数量: {}", allGroupIds.size());
            log.info("总群组数量: {}", allGroupIds.size());

            if (allGroupIds.isEmpty()) {
                XxlJobHelper.log("没有找到活跃群组");
                log.info("没有找到活跃群组");
                XxlJobHelper.handleSuccess();
                return;
            }

            // 异步处理每个群组
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (int i = 0; i < allGroupIds.size(); i++) {
                if (i % shardTotal == shardIndex) {
                    final String groupId = allGroupIds.get(i);

                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        try {
                            getDragon(groupId, dateKey);
                        } catch (Exception e) {
                            log.error("处理群聊 {} 时异常: {}", groupId, e.getMessage());
                            XxlJobHelper.log("处理群聊 {} 时异常: {}", groupId, e.getMessage());
                        }
                    }, asyncPool);

                    futures.add(future);
                }
            }

            // 等待所有任务完成
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    futures.toArray(new CompletableFuture[0])
            );

            allFutures.join();

            XxlJobHelper.log("分片[{}/{}] 处理完成，共处理 {} 个群聊",
                    shardIndex + 1, shardTotal, futures.size());
            log.info("分片[{}/{}] 处理完成，共处理 {} 个群聊",
                    shardIndex + 1, shardTotal, futures.size());

            XxlJobHelper.handleSuccess();

        } catch (Exception e) {
            log.error("任务执行异常: {}", e.getMessage(), e);
            XxlJobHelper.log("任务执行异常: {}", e.getMessage());
            XxlJobHelper.handleFail(e.getMessage());
        }
    }

    private void getDragon(String groupId, String dateKey) {
        try {
            String rankKey = "chat:rank:group:" + groupId + ":" + dateKey;

            // 获取分数最高的一个成员（第一名）
            Set<ZSetOperations.TypedTuple<Object>> topUsers = redisTemplate.opsForZSet()
                    .reverseRangeWithScores(rankKey, 0, 0);

            if (topUsers == null || topUsers.isEmpty()) {
                log.debug("群组 {} 没有找到排名数据", groupId);
                return;
            }

            ZSetOperations.TypedTuple<Object> topUser = topUsers.iterator().next();
            Object value = topUser.getValue();
            Long userId = null;

            // 处理不同类型的值
            if (value instanceof String) {
                userId = Long.parseLong((String) value);
            } else if (value instanceof Integer) {
                userId = ((Integer) value).longValue();
            } else if (value instanceof Long) {
                userId = (Long) value;
            } else {
                log.error("群组 {} 的用户ID类型不支持: {}", groupId, value.getClass());
                return;
            }

            Double score = topUser.getScore();

            // 如果分数为0或null，说明该群聊昨日无有效发言
            if (score == null || score <= 0) {
                log.debug("群组 {} 的龙王分数无效: {}", groupId, score);
                return;
            }

            // 更新数据库
            UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", groupId);
            updateWrapper.set("dragon", userId);

            int result = groupMapper.update(updateWrapper);

            if (result > 0) {
                log.info("群组 {} 成功设置龙王为 {}，分数: {}", groupId, userId, score);
            } else {
                log.warn("群组 {} 更新龙王失败，可能不存在该群组", groupId);
            }

        } catch (NumberFormatException e) {
            log.error("群组 {} 的用户ID格式错误", groupId, e);
        } catch (Exception e) {
            log.error("处理群组 {} 异常", groupId, e);
        }
    }

    private List<String> getActiveGroupIds(String dateKey) {
        List<String> groupIds = new ArrayList<>();
        String pattern = "chat:rank:group:*:" + dateKey;

        try {
            // 使用 RedisTemplate 的 scan 方法
            ScanOptions options = ScanOptions.scanOptions()
                    .match(pattern)
                    .count(1000)
                    .build();

            Cursor<String> cursor = redisTemplate.scan(options);

            while (cursor.hasNext()) {
                String key = cursor.next();
                // 解析key格式: chat:rank:group:{groupId}:{date}
                String[] parts = key.split(":");
                if (parts.length >= 4) {
                    String groupId = parts[3];
                    if (!groupId.isEmpty()) {
                        groupIds.add(groupId);
                    }
                }
            }

            cursor.close();

            log.info("找到活跃群组数量: {}", groupIds.size());

        } catch (Exception e) {
            log.error("获取活跃群组ID异常", e);
        }

        return groupIds;
    }

}