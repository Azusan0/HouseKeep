package com.cskaoyan.duolai.clean.foundation.controller.outer;

import com.cskaoyan.duolai.clean.thirdparty.core.map.MapService;
import com.cskaoyan.duolai.clean.thirdparty.dto.MapLocationDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wildfly.common.annotation.NotNull;

import jakarta.annotation.Resource;

/**
 * 用户端 - 地址相关接口
 **/
@RestController
@RequestMapping("/map")
@Api(tags = "地图服务相关接口")
public class MapController {
    @Resource
    private MapService mapService;

    @GetMapping("/regeo")
    @ApiOperation("根据经纬度查询地址信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "location", value = "经纬度", required = true, dataTypeClass = String.class)
    })
    public MapLocationDTO getCityCodeByLocation(@NotNull() @RequestParam("location") String location) {
        return mapService.getCityCodeByLocation(location);
    }
}
