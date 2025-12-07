package com.kuangkuang.kuangkuang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/redis-test")
    public String testRedis() {
        try {
            // 测试基本连接
            redisTemplate.opsForValue().set("test_key", "test_value");
            String value = (String) redisTemplate.opsForValue().get("test_key");

            // 测试 Stream 操作
            Map<String, String> testMap = new HashMap<>();
            testMap.put("test_field", "test_value");


            // 测试 ZSet 操作
            Double score = redisTemplate.opsForZSet().incrementScore("test_zset", "test_member", 1);

            // 清理测试数据
            redisTemplate.delete("test_key");
            redisTemplate.delete("test_stream");
            redisTemplate.delete("test_zset");

            return String.format("✅ Redis测试成功 - StreamID: %s, Score: %s",1, score);

        } catch (Exception e) {
            return String.format("❌ Redis测试失败: %s", e.getMessage());
        }
    }
}