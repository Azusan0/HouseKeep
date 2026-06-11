package com.cskaoyan.duolai.clean.housekeeping.controller.inner;


import com.cskaoyan.duolai.clean.housekeeping.dto.ConfigRegionDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IConfigRegionService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 区域表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/inner/region")
public class InnerRegionController {
    @Resource
    private IConfigRegionService configRegionService;



    @GetMapping("/findAllConfigRegion")
    public List<ConfigRegionDTO> findAll() {
        return configRegionService.queryAll();
    }


    @GetMapping("/findConfigRegionById/{id}")
    public ConfigRegionDTO findConfigRegionById(@PathVariable("id") Long id) {
        ConfigRegionDTO configRegionDTO = configRegionService.queryById(id);
        return configRegionDTO;
    }

    @GetMapping("/findConfigRegionByCityCode")
    public ConfigRegionDTO findConfigRegionByCityCode(@RequestParam("cityCode") String cityCode) {
        return configRegionService.queryByCityCode(cityCode);
    }
}
