package com.cskaoyan.duolai.clean.user.controller.operation;


import com.cskaoyan.duolai.clean.user.dto.ServeProviderInfoDTO;
import com.cskaoyan.duolai.clean.user.request.ServeProviderPageRequest;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderAndSkillInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderOperationDTO;
import com.cskaoyan.duolai.clean.user.service.IServeProviderService;
import com.cskaoyan.duolai.clean.user.request.ServerProviderStatusCommand;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 服务人员相关接口
 * </p>
 */
@Validated
@RestController("operationServeProviderController")
@RequestMapping("/operation/serve-provider")
@Api(tags = "运营端 - 服务人员或机构相关接口")
public class ServeProviderController {
    @Resource
    private IServeProviderService serveProviderService;
    @GetMapping("/pageQueryWorker")
    @ApiOperation("服务人员分页查询")
    public PageDTO<ServeProviderOperationDTO> pageQueryWorker(ServeProviderPageRequest serveProviderPageRequest) {
        return serveProviderService.pageQueryWorker(serveProviderPageRequest);
    }

}
