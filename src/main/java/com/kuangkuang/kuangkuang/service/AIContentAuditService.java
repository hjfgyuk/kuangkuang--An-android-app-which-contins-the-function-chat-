package com.kuangkuang.kuangkuang.service;

import com.kuangkuang.kuangkuang.pojo.entity.AuditResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.concurrent.CompletableFuture;

@Service
public class AIContentAuditService {

    private final ChatClient chatClient;
    @Autowired
    public AIContentAuditService(ChatClient chatClient) {
        this.chatClient = chatClient;
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