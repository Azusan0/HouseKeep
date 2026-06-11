package com.cskaoyan.duolai.clean.housekeeping.request;

import com.cskaoyan.duolai.clean.common.model.dto.PageRequest;
import lombok.Data;

/**
 * 服务分页查询类
 **/
@Data
public class ServePageRequest extends PageRequest {
    private Long regionId;
}
