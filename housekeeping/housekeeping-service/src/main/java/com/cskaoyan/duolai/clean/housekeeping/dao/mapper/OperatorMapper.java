package com.cskaoyan.duolai.clean.housekeeping.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.OperatorDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 运营人员 Mapper 接口
 * </p>
 */
public interface OperatorMapper extends BaseMapper<OperatorDO> {

    @Select("select * from operator")
    List<OperatorDO> queryAll();
}
