package com.cskaoyan.duolai.clean.housekeeping.controller.inner;

import com.cskaoyan.duolai.clean.housekeeping.dto.ServeDetailDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionServeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/inner/serve")
@Api(tags = "内部接口 - 服务相关接口")
public class InnerServeController {
    @Resource
    private IRegionServeService serveService;

    @GetMapping("/{id}")
    @ApiOperation("根据id查询服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务项id", required = true, dataTypeClass = Long.class)
    })
    public ServeDetailDTO findById(@PathVariable("id") Long id) {
        return serveService.findServeDetailById(id);
    }
}
