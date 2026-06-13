package com.cskaoyan.duolai.clean.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderSimpleDTO;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderDO;
import com.cskaoyan.duolai.clean.user.request.ServeProviderPageRequest;
import com.cskaoyan.duolai.clean.user.dto.CertificationStatusDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderAndSkillInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderDTO;
import com.cskaoyan.duolai.clean.user.request.ServerProviderStatusCommand;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderOperationDTO;

import java.util.List;

/**
 * <p>
 * 服务人员表 服务类
 * </p>
 */
public interface IServeProviderService extends IService<ServeProviderDO> {

    /**
     * 分页查询服务人员列表
     *
     * @param serveProviderPageRequestDTO 查询条件
     * @return 分页结果
     */
    PageDTO<ServeProviderOperationDTO> pageQueryWorker(ServeProviderPageRequest serveProviderPageRequestDTO);


    /**
     * 根据手机号和用户类型获取用户信息
     *
     * @param phone 注册手机号
     * @return
     */

    ServeProviderDTO findByPhone(String phone);

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    ServeProviderDTO findById(Long id);


    /**
     * 新增用户
     *
     * @param phone    新增服务人员手机号
     */
    ServeProviderDTO addServeProvider(String phone);


    /**
     * 校验是否完成首次配置，如果完成则打上标记
     * @param currentUserId
     */
    void settingStatus(Long currentUserId);

    ServeProviderInfoDTO findServeProviderInfo(Long id);


    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    ServeProviderDTO currentUserInfo();

    /**
     * 获取服务人员或机构的信息状态
     * @param providerId
     * @return
     */
    CertificationStatusDTO getCertificationStatus(Long providerId);
}
