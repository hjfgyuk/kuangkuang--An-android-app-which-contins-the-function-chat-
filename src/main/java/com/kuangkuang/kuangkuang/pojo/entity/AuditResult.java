package com.kuangkuang.kuangkuang.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditResult {
    private boolean passed = true;           // 是否通过审核
    private String label;            // 违规类型
    private double confidence;       // 置信度
    private String riskLevel;        // 风险等级
    private String violationContent; // 具体违规内容
    private String auditOpinion;     // 审核意见
    private String filteredContent;  // 过滤后的内容

    // 静态工厂方法
    public static AuditResult passed() {
        AuditResult result = new AuditResult();
        result.setPassed(true);
        result.setLabel("无");
        result.setConfidence(1.0);
        result.setRiskLevel("无风险");
        return result;
    }

    public static AuditResult rejected(String reason) {
        AuditResult result = new AuditResult();
        result.setPassed(false);
        result.setLabel("违规");
        result.setConfidence(0.8);
        result.setRiskLevel("高风险");
        result.setAuditOpinion(reason);
        return result;
    }
}