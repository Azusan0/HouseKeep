package com.cskaoyan.duolai.clean.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSettingsDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 服务人员附属信息 Mapper 接口
 * </p>
 */
public interface ServeProviderSettingsMapper extends BaseMapper<ServeProviderSettingsDO> {

    @Select("<script>select id,city_code as cityCode from serve_provider_settings where id in (<foreach collection='ids' item='id' separator=','>#{id}</foreach>)</script>")
    List<ServeProviderSettingsDO> batchQueryCityCodeByIds(@Param("ids") List<Long> ids);

}
