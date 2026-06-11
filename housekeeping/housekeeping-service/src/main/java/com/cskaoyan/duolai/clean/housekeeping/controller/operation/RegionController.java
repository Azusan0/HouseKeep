package com.cskaoyan.duolai.clean.housekeeping.controller.operation;


import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.converter.RegionConverter;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionPageRequest;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IHomeService;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 区域表 前端控制器
 * </p>
 */
@RestController("operationRegionController")
@RequestMapping("/operation/region")
@Api(tags = "运营端 - 区域相关接口")
public class RegionController {
    @Resource
    private IRegionService regionService;
    @Resource
    RegionConverter regionConverter;

    @PostMapping
    // "区域新增"
    public void addRegion(@RequestBody RegionCommand regionCommand) {
        regionService.addRegion(regionCommand);
    }

    @PutMapping("/{id}")
    //"区域修改"
    public void update(@PathVariable("id") Long id,
                       @RequestParam("managerName") String managerName,
                       @RequestParam("managerPhone") String managerPhone) {
        regionService.update(id, managerName, managerPhone);
    }

    @DeleteMapping("/{id}")
    // "区域删除"
    public void delete(@PathVariable("id") Long id) {
        regionService.deleteById(id);
    }

    @GetMapping("/page")
    // "区域分页查询"
    public PageDTO<RegionDTO> getPage(RegionPageRequest regionPageQueryReqDTO) {
        return regionService.getPage(regionPageQueryReqDTO);
    }

    @GetMapping("/{id}")
    // "根据id查询"
    public RegionDTO findById(@PathVariable("id") Long id) {
        RegionDO regionDO = regionService.getById(id);
        RegionDTO regionDTO = regionConverter.regionDOToRegionDTO(regionDO);
        return regionDTO;
    }

    @PutMapping("/activate/{id}")
    // "区域启用"
    public void activate(@PathVariable("id") Long id) {
        regionService.active(id);
    }

    @PutMapping("/deactivate/{id}")
    // "区域禁用"
    public void deactivate(@PathVariable("id") Long id) {
        regionService.deactivate(id);
    }

    @PutMapping("/refreshRegionRelateCaches/{id}")
    // "刷新区域相关缓存"
    public void refreshRegionRelateCaches(@PathVariable("id") Long id) {
        regionService.refreshRegionRelateCaches(id);
    }

}
