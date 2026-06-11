package com.cskaoyan.duolai.clean.housekeeping.controller.user;


import com.cskaoyan.duolai.clean.housekeeping.dto.RegionSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 区域表 前端控制器
 * </p>
 */
@RestController("consumerRegionController")
@RequestMapping("/consumer/region")
public class RegionController {
    @Resource
    private IRegionService regionService;

    @GetMapping("/activeRegionList")
    public List<RegionSimpleDTO> activeRegionList() {
        return regionService.queryActiveRegionListCache();
    }

}
