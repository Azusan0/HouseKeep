package com.cskaoyan.duolai.clean.housekeeping.request;

import com.cskaoyan.duolai.clean.common.model.dto.PageRequest;
import lombok.Data;

/**
 * 服务项分页查询类
 **/
@Data
public class ServeItemPageRequest extends PageRequest {

    private String name;

    private Long serveTypeId;

    private Integer activeStatus;
}
