package com.cskaoyan.duolai.clean.user.client;

import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 内部接口 - 服务类型相关接口
 */
@FeignClient(name = "housekeeping", contextId = "housekeeping-serve-type", path = "/housekeeping/inner/serve-type")
public interface ServeTypeApi {

    @GetMapping("/listByIds")
    List<ServeTypeSimpleDTO> listByIds(@RequestParam("ids") List<Long> ids);
}
