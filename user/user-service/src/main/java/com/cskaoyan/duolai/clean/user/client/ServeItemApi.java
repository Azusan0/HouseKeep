package com.cskaoyan.duolai.clean.user.client;

import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 内部接口 - 服务项相关接口
 */
@FeignClient(name = "housekeeping", contextId = "housekeeping-serve-item", path = "/housekeeping/inner/serve-item")
public interface ServeItemApi {

    @GetMapping("/{id}")
    ServeItemDTO findById(@PathVariable("id") Long id);

    @GetMapping("/listByIds")
    List<ServeItemSimpleDTO> listByIds(@RequestParam("ids") List<Long> ids);

    /**
     * 查询启用状态的服务项目录
     *
     * @return 服务项目录
     */
    @GetMapping("/queryActiveServeItemCategory")
    List<ServeTypeInfoDTO> queryActiveServeItemCategoryInfo();
}
