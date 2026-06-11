package com.cskaoyan.duolai.clean.housekeeping.controller.inner;


import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeInfoDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeItemService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 内部接口 - 服务项相关接口
 * </p>
 */
@RestController
@RequestMapping("/inner/serve-item")
public class InnerServeItemController {
    @Resource
    private IServeItemService serveItemService;

    @GetMapping("/{id}")
    public ServeItemDTO findById(@PathVariable("id") Long id) {
        return serveItemService.queryServeItemAndTypeById(id);
    }

    @GetMapping("/listByIds")
    public List<ServeItemSimpleDTO> listByIds(@RequestParam("ids") List<Long> ids) {
        return serveItemService.queryServeItemListByIds(ids);
    }

    @GetMapping("/queryActiveServeItemCategory")
    public List<ServeTypeInfoDTO> queryActiveServeItemCategoryInfo() {
        return serveItemService.queryActiveServeItemCategory();
    }
}
