package com.kuangkuang.kuangkuang.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiCheatConfig {
    @Bean
    public ChatClient chatClient(OllamaChatModel model){
        return ChatClient
                .builder(model)
                .defaultSystem( "你是一个专业的内容安全审核员。请严格审核以下消息：\n\n" +
                        "消息：%s\n\n" +
                        "审核标准：\n" +
                        "1. 政治敏感：涉及政府、领导人、政策的负面言论\n" +
                        "2. 暴力恐怖：暴力、恐怖主义、极端主义内容\n" +
                        "3. 色情低俗：色情、淫秽、低俗内容\n" +
                        "4. 广告骚扰：垃圾广告、骚扰信息\n" +
                        "5. 人身攻击：侮辱、诽谤、人身攻击\n" +
                        "6. 违法信息：赌博、毒品、诈骗等违法内容\n\n" +
                        "输出格式（必须严格遵循）：\n" +
                        "===审核结果===\n" +
                        "结果: [通过/拒绝]\n" +
                        "类型: [无/政治敏感/暴力恐怖/色情低俗/广告骚扰/人身攻击/违法信息/其他]\n" +
                        "置信度: [0.00-1.00]\n" +
                        "风险等级: [无风险/低风险/中风险/高风险]\n" +
                        "违规内容: [具体违规词句，如无则写\"无\"]\n" +
                        "审核意见: [详细说明]")
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

}
