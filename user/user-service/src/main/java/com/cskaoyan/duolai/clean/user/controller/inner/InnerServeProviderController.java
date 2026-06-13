package com.cskaoyan.duolai.clean.user.controller.inner;


import com.cskaoyan.duolai.clean.user.dto.ServeProviderInfoDTO;
import com.cskaoyan.duolai.clean.user.service.IServeProviderService;
import com.cskaoyan.duolai.clean.user.service.IServeProviderSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务人员 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/inner/serve-provider")
@Api(tags = "内部接口 - 服务人员、机构相关接口")
public class InnerServeProviderController {
    @Resource
    private IServeProviderService serveProviderService;

    @Resource
    private IServeProviderSettingsService serveProviderSettingsService;


    @GetMapping("/{id}")
    @ApiOperation("服务人员详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务人员id", required = true, dataTypeClass = Long.class)
    })
    public ServeProviderInfoDTO getDetail(@PathVariable("id") Long id) {
        return serveProviderService.findServeProviderInfo(id);
    }

    @GetMapping("/batchCityCode")
    @ApiOperation("批量获取服务人员所在城市编码")
    public Map<Long, String> batchCityCode(@RequestParam(value = "ids") List<Long> ids) {
        return serveProviderSettingsService.findManyCityCodeOfServeProvider(ids);
    }

}
