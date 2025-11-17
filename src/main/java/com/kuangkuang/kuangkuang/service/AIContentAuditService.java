package com.kuangkuang.kuangkuang.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kuangkuang.kuangkuang.mapper.MessageMapper;
import com.kuangkuang.kuangkuang.pojo.entity.AuditResult;
import com.kuangkuang.kuangkuang.pojo.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AIContentAuditService {
    @Autowired
    private MessageMapper messageMapper;
    private static RabbitTemplate rabbitTemplate;

    private final ChatClient chatClient;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    @Autowired
    public AIContentAuditService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
//    获取待审核消息
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "ai.check.queue"),
        exchange = @Exchange(name = "ai.direct",type = ExchangeTypes.DIRECT),
        key = {"check"}
))
public void getMessageFromMQ(Message message){
        log.info("获取待审核信息"+message);
        try{
            CompletableFuture<String> future = auditMessageAsync(message.getMessage());
            future.thenAccept(response->{
                try {
                    log.info("获取审核结果"+response);
                    AuditResult result = parseAuditResult(response);
                    log.info(result.toString());
                    Map<String,Object> re = new HashMap<>();
                    re.put("isPassed",result.isPassed());
                    re.put("reason",result.getAuditOpinion());
                    re.put("uuid",message.getUuid());
                    rabbitTemplate.convertAndSend("ai.direct",
                            "result",
                            re);
                }catch (Exception e){
                    log.error(e.getMessage());
                    throw  e;
                }
            });
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
}
//接收审核结果
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "ai.result.queue"),
        exchange = @Exchange(name = "ai.direct",type = ExchangeTypes.DIRECT),
        key = {"result"}
))
public void getAiCheckResult(Map<String,Object> result){
        log.info("获取最终审核结果"+result);
        String uuid = result.get("uuid").toString();
        boolean isPassed = (boolean) result.get("isPassed");
        String reason = result.get("reason").toString();
        if(isPassed){
            log.info("审核通过，审核号："+uuid+"审核内容："+result);
        }else {
            UpdateWrapper<Message> wrapper = new UpdateWrapper<>();
            wrapper.eq("uuid",uuid).set("message",reason);
            log.info("修改消息内容,工单号："+uuid);
            messageMapper.update(null,wrapper);
        }
}
    /**
     * 同步审核消息内容
     */
    public String auditMessageSync(String messageContent) {
        try {
            ChatResponse response = chatClient.prompt()
                    .user(messageContent)
                    .call()
                    .chatResponse();

            return response.getResult().getOutput().getText();

        } catch (Exception e) {
            // 审核失败时的降级处理
            return "审核服务暂时不可用，消息已放行";
        }
    }

    /**
     * 异步审核消息内容
     */

    public CompletableFuture<String> auditMessageAsync(String messageContent) {
        return CompletableFuture.supplyAsync(() -> auditMessageSync(messageContent));
    }

    /**
     * 流式审核（适用于长内容）
     */
    public Flux<String> auditMessageStream(String messageContent) {
        return chatClient.prompt()
                .user(messageContent)
                .stream()
                .content();
    }

    /**
     * 解析审核结果
     */
    public AuditResult parseAuditResult(String aiResponse) {
        // 解析 AI 返回的格式化结果
        return parseFormattedResponse(aiResponse);
    }

    private AuditResult parseFormattedResponse(String response) {
        AuditResult result = new AuditResult();

        try {
            // 解析格式化的审核结果
            if (response.contains("===审核结果===")) {
                String[] lines = response.split("\n");
                for (String line : lines) {
                    if (line.startsWith("结果:")) {
                        result.setPassed(line.contains("通过"));
                    } else if (line.startsWith("类型:")) {
                        result.setLabel(line.split(":")[1].trim());
                    } else if (line.startsWith("置信度:")) {
                        try {
                            String confidenceStr = line.split(":")[1].trim();
                            result.setConfidence(Double.parseDouble(confidenceStr));
                        } catch (NumberFormatException e) {
                            result.setConfidence(0.5);
                        }
                    } else if (line.startsWith("风险等级:")) {
                        result.setRiskLevel(line.split(":")[1].trim());
                    } else if (line.startsWith("违规内容:")) {
                        result.setViolationContent(line.split(":")[1].trim());
                    } else if (line.startsWith("审核意见:")) {
                        result.setAuditOpinion(line.split(":")[1].trim());
                    }
                }
            } else {
                // 如果 AI 没有返回格式化结果，进行简单判断
                result.setPassed(!response.contains("拒绝") && !response.contains("违规"));
                result.setLabel("其他");
                result.setConfidence(0.7);
            }

        } catch (Exception e) {
            // 解析失败时的默认处理
            result.setPassed(true); // 保守策略：解析失败时放行
            result.setLabel("解析错误");
            result.setConfidence(0.5);
        }

        return result;
    }
}