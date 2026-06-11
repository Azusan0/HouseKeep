package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.OperatorMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.OperatorDO;
import com.cskaoyan.duolai.clean.housekeeping.service.IOperatorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * <p>
 * 运营人员 服务实现类
 * </p>
 */
@Service
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper, OperatorDO> implements IOperatorService {

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 根据名称查询运营人员
     *
     * @param username 名称
     * @return 运营人员
     */
    @Override
    public OperatorDO findByUsername(String username) {
        return lambdaQuery().eq(OperatorDO::getUsername, username).one();
    }
}
