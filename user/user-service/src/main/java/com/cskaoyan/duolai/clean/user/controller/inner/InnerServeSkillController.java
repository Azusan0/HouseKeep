package com.cskaoyan.duolai.clean.user.controller.inner;

import com.cskaoyan.duolai.clean.user.service.IServeSkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 内部接口-服务技能相关接口
 **/
@RestController
@RequestMapping("/inner/serve-skill")
@Api(tags = "内部接口 - 服务技能相关接口")
public class InnerServeSkillController{
    @Resource
    private IServeSkillService serveSkillService;

    @GetMapping("/queryServeSkillListByServeProvider")
    @ApiOperation("查询服务者的服务技能")
    public List<Long> queryServeSkillListByServeProvider(@RequestParam("providerId") Long providerId,
                                    @RequestParam("providerType") Integer providerType,
                                    @RequestParam("cityCode") String cityCode) {
        return serveSkillService.queryServeSkillListByServeProvider(providerId, providerType, cityCode);
    }
}
