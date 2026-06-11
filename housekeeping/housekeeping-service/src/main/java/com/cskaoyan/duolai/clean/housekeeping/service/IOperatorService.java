package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.OperatorDO;

/**
 * <p>
 * 运营人员 服务类
 * </p>
 */
public interface IOperatorService extends IService<OperatorDO> {

    /**
     * 根据名称查询运营人员
     *
     * @param username 名称
     * @return 运营人员
     */
    OperatorDO findByUsername(String username);

}
