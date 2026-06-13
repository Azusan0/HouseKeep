package com.cskaoyan.duolai.clean.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.common.utils.*;
import com.cskaoyan.duolai.clean.mysql.utils.PageUtils;
import com.cskaoyan.duolai.clean.user.converter.ServeProviderConverter;
import com.cskaoyan.duolai.clean.user.converter.WorkerCertificationConverter;
import com.cskaoyan.duolai.clean.user.dao.mapper.WorkerCertificationMapper;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderSimpleDTO;
import com.cskaoyan.duolai.clean.user.dao.mapper.ServeProviderMapper;
import com.cskaoyan.duolai.clean.user.dao.entity.*;
import com.cskaoyan.duolai.clean.user.request.ServeProviderPageRequest;
import com.cskaoyan.duolai.clean.user.dto.CertificationStatusDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderAndSkillInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderOperationDTO;
import com.cskaoyan.duolai.clean.user.service.*;
import com.cskaoyan.duolai.clean.user.request.ServerProviderStatusCommand;

import com.cskaoyan.duolai.clean.common.constants.CommonStatusConstants;
import com.cskaoyan.duolai.clean.common.enums.EnableStatusEnum;
import com.cskaoyan.duolai.clean.common.expcetions.BadRequestException;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务人员 服务实现类
 * </p>
 */
@Service
public class ServeProviderServiceImpl extends ServiceImpl<ServeProviderMapper, ServeProviderDO> implements IServeProviderService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IServeProviderSettingsService serveProviderSettingsService;

    @Resource
    private IServeProviderSyncService serveProviderSyncService;
    @Resource
    private IWorkerCertificationService workerCertificationService;


    @Resource
    ServeProviderConverter serveProviderConverter;

    @Resource
    WorkerCertificationConverter workerCertificationConverter;

    @Override
    public PageDTO<ServeProviderOperationDTO> pageQueryWorker(ServeProviderPageRequest serveProviderPageRequestDTO) {
        Page<ServeProviderOperationDO> paramPage = PageUtils.parsePageQuery(serveProviderPageRequestDTO, ServeProviderOperationDO.class);
        Page<ServeProviderOperationDO> result = baseMapper.queryWorkerList(serveProviderPageRequestDTO, paramPage);
        return serveProviderConverter.toServeProviderOperationPageDTO(result.getRecords(), result.getTotal(), result.getPages());
    }

    @Override
    public ServeProviderDTO findByPhone(String phone) {
        LambdaQueryWrapper<ServeProviderDO> wrapper = Wrappers.<ServeProviderDO>lambdaQuery().eq(ServeProviderDO::getPhone, phone);
        ServeProviderDO serveProviderDO = baseMapper.selectOne(wrapper);
        ServeProviderDTO serveProviderDTO
                = serveProviderConverter.serveProviderDOToDTO(serveProviderDO);
        return serveProviderDTO;
    }

    @Override
    public ServeProviderDTO findById(Long id) {
        ServeProviderDO serveProviderDO = baseMapper.selectById(id);
        return serveProviderConverter.serveProviderDOToDTO(serveProviderDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServeProviderDTO addServeProvider(String phone) {

        //新增服务人员信息(设置电话和状态为CommonStatusConstants.USER_STATUS_NORMAL保存即可)

        //新增服务人员添加状态设置记录(serve_provider_setting表)，调用serveProviderSettingsService.add(服务从业者id)

        // 返回ServeProviderDTO对象主要是方便生成jwt


        return null;
    }

    @Override
    public void settingStatus(Long currentUserId) {
        ServeProviderDO serveProviderDO = new ServeProviderDO();
        serveProviderDO.setId(currentUserId);
        serveProviderDO.setSettingsStatus(1);
        baseMapper.updateById(serveProviderDO);
    }


    @Override
    public ServeProviderInfoDTO findServeProviderInfo(Long id) {
        ServeProviderDTO serveProviderDTO = this.findById(id);
        ServeProviderInfoDTO serveProviderInfoDTO = serveProviderConverter.serveProviderDTOToServeProviderInfoDTO(serveProviderDTO);

        ServeProviderSettingsDO serveProviderSettingsDO = serveProviderSettingsService.findById(id);
        // location
        serveProviderInfoDTO.setLat(serveProviderSettingsDO.getLat());
        serveProviderInfoDTO.setLon(serveProviderSettingsDO.getLon());
        // cityCode
        serveProviderInfoDTO.setCityCode(serveProviderSettingsDO.getCityCode());
        // 是否开启接单
        serveProviderInfoDTO.setCanPickUp(EnableStatusEnum.ENABLE.equals(serveProviderSettingsDO.getCanPickUp()));

        //获取认证状态
        CertificationStatusDTO certificationStatusDTO = getCertificationStatus(id);
        //获取认证状态
        Integer certificationStatus = ObjectUtils.get(certificationStatusDTO, CertificationStatusDTO::getCertificationStatus);
        serveProviderInfoDTO.setVerifyStatus(certificationStatus);

        return serveProviderInfoDTO;
    }


    @Resource
    WorkerCertificationMapper workerCertificationMapper;
    /**
     * 获取服务人员的信息状态
     *
     * @param providerId
     * @return
     */
    @Override
    public CertificationStatusDTO getCertificationStatus(Long providerId) {
        LambdaQueryWrapper<WorkerCertificationDO> wrapper = Wrappers.lambdaQuery(WorkerCertificationDO.class)
                .eq(WorkerCertificationDO::getServeProviderId, providerId)
                .orderByDesc(WorkerCertificationDO::getCreateTime)
                .last("limit 1");

        WorkerCertificationDO workerCertificationDO = workerCertificationMapper.selectOne(wrapper);
        return workerCertificationConverter.workCertificationDO2CertificationStatusDTO(workerCertificationDO);

    }


    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @Override
    public ServeProviderDTO currentUserInfo() {
        ServeProviderDO serveProviderDO = baseMapper.selectById(UserContext.currentUserId());
        return serveProviderConverter.serveProviderDOToDTO(serveProviderDO);
    }
}
