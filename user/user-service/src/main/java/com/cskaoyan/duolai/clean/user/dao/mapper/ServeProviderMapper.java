package com.cskaoyan.duolai.clean.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderAndSkillInfoDO;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderDO;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderOperationDO;
import com.cskaoyan.duolai.clean.user.request.ServeProviderPageRequest;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务人员表 Mapper 接口
 * </p>
 */
public interface ServeProviderMapper extends BaseMapper<ServeProviderDO> {

    /**
     * 分页查询服务人员
     *
     * @param serveProviderPageRequest 分页查询请求体
     * @return 服务人员列表
     */
    Page<ServeProviderOperationDO> queryWorkerList(@Param("pageRequest") ServeProviderPageRequest serveProviderPageRequest, Page<ServeProviderOperationDO> page);


    /**
     * 根据服务人员id查询基本信息
     *
     * @param id 服务人员id
     * @return 基本信息
     */
    ServeProviderAndSkillInfoDO findBasicInformationById(@Param("id") Long id);
}
