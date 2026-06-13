package com.cskaoyan.duolai.clean.user.request;

import com.cskaoyan.duolai.clean.common.model.dto.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 地址薄分页查询请求
 * </p>
 *
 */
@Data
@ApiModel("地址薄分页查询请求")
public class AddressBookPageQueryReq extends PageRequest {
}
