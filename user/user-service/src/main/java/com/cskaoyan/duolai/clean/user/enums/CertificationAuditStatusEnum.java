package com.cskaoyan.duolai.clean.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertificationAuditStatusEnum {
    NOT_AUDIT(0, "未审核"),
    AUDIT_FINISH(1, "已审核");

    /**
     * 状态值
     */
    private final int status;

    /**
     * 描述
     */
    private final String description;
}
