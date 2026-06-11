package com.cskaoyan.duolai.clean.housekeeping.controller.operation;


import com.cskaoyan.duolai.clean.housekeeping.dto.ConfigRegionDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.ConfigRegionCommand;
import com.cskaoyan.duolai.clean.housekeeping.service.IConfigRegionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * <p>
 * 区域表 前端控制器
 * </p>
 */
@Validated
@RestController("operationConfigRegionController")
@RequestMapping("/operation/config-region")
public class ConfigRegionController {
    @Resource
    private IConfigRegionService configRegionService;

    @GetMapping("/{id}")
    public ConfigRegionDTO queryById(@PathVariable(value = "id") Long id) {
        return configRegionService.queryById(id);
    }

    @PutMapping("/{id}")
    public void putById(@PathVariable(value = "id") Long id, @Validated @RequestBody ConfigRegionCommand configRegionCommand) {
        configRegionService.setConfigRegionById(id, configRegionCommand);
    }
}
