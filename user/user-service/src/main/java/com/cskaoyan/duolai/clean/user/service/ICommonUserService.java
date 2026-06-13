package com.cskaoyan.duolai.clean.user.service;

import com.cskaoyan.duolai.clean.user.dto.CommonUserDTO;
import com.cskaoyan.duolai.clean.user.dao.entity.CommonUserDO;
import com.cskaoyan.duolai.clean.user.request.CommonUserPageRequest;
import com.cskaoyan.duolai.clean.user.request.CommonUserCommand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface ICommonUserService extends IService<CommonUserDO> {

    CommonUserDO findByOpenId(String openId);
}
