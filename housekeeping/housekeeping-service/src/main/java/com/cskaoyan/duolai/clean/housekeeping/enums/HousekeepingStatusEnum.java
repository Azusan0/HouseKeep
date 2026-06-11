package com.cskaoyan.duolai.clean.housekeeping.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HousekeepingStatusEnum {
    INIT(0,"草稿"),
    ENABLE(2,"启用"),
    HOT(1, "热门"),
    NOT_HOT(0, "取消热门"),
    DISABLE(1, "禁用");
    private int status;
    private String description;

    public boolean equals(Integer status) {
        return this.status == status;
    }

    public boolean equals(HousekeepingStatusEnum enableStatusEnum) {
        return enableStatusEnum != null && enableStatusEnum.status == this.getStatus();
    }
}
