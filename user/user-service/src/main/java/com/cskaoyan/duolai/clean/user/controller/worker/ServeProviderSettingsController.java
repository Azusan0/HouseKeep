package com.cskaoyan.duolai.clean.user.controller.worker;

import com.cskaoyan.duolai.clean.user.request.ServePickUpReqDTO;
import com.cskaoyan.duolai.clean.user.request.ServeScopeCommand;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderSettingsDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeSettingsStatusDTO;
import com.cskaoyan.duolai.clean.user.service.IServeProviderSettingsService;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController("workerServeProviderSettingsController")
@RequestMapping("/worker/serve-settings")
@Api(tags = "服务端 - 服务设置相关接口")
public class ServeProviderSettingsController {

    @Resource
    private IServeProviderSettingsService serveProviderSettingsService;

    @PutMapping("/serve-scope")
    @ApiOperation("服务范围设置")
    public void setServeScope(@RequestBody ServeScopeCommand serveScopeCommand) {
        serveProviderSettingsService.setServeScope(serveScopeCommand);
    }

    @GetMapping
    @ApiOperation("获取服务范围设置")
    public ServeProviderSettingsDTO getServeScope() {
        return serveProviderSettingsService.getServeScope();
    }

    @PutMapping("/pick-up")
    @ApiOperation("接单设置")
    public void setPickUp(@RequestBody ServePickUpReqDTO servePickUpReqDTO) {
        serveProviderSettingsService.setPickUp(UserContext.currentUserId(), servePickUpReqDTO.getCanPickUp());
    }

    @GetMapping("/status")
    @ApiOperation("获取所有设置状态")
    public ServeSettingsStatusDTO getStatus() {
        return serveProviderSettingsService.getSettingStatus();
    }
}
