package com.cskaoyan.duolai.clean.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.user.client.WechatApi;
import com.cskaoyan.duolai.clean.user.converter.CommonUserConverter;
import com.cskaoyan.duolai.clean.user.dao.mapper.CommonUserMapper;
import com.cskaoyan.duolai.clean.user.dao.entity.CommonUserDO;
import com.cskaoyan.duolai.clean.user.service.ICommonUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class CommonUserServiceImpl extends ServiceImpl<CommonUserMapper, CommonUserDO> implements ICommonUserService {

    @Override
    public CommonUserDO findByOpenId(String openId) {
        LambdaQueryWrapper<CommonUserDO> eq = Wrappers.<CommonUserDO>lambdaQuery().eq(CommonUserDO::getOpenId, openId);
        CommonUserDO commonUserDO = baseMapper.selectOne(eq);
        return commonUserDO;
    }

}
