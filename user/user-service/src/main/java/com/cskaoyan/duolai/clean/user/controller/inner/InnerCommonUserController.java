package com.cskaoyan.duolai.clean.user.controller.inner;

import com.cskaoyan.duolai.clean.user.converter.CommonUserConverter;
import com.cskaoyan.duolai.clean.user.dao.entity.CommonUserDO;
import com.cskaoyan.duolai.clean.user.dto.CommonUserDTO;
import com.cskaoyan.duolai.clean.user.service.ICommonUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 内部接口-普通用户相关接口
 **/
@RestController
@RequestMapping("inner/common-user")
@Api(tags = "内部接口 - 普通用户相关接口")
public class InnerCommonUserController {
    @Resource
    private ICommonUserService commonUserService;

    @Resource
    private CommonUserConverter commonUserConverter;
    @GetMapping("/{id}")
    public CommonUserDTO findById(@PathVariable Long id) {
        CommonUserDO commonUserDO = commonUserService.getById(id);
        return commonUserConverter.commonUserToDTO(commonUserDO);
    }
}
