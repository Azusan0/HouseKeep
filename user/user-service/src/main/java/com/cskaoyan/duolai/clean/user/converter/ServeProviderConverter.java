package com.cskaoyan.duolai.clean.user.converter;

import com.cskaoyan.duolai.clean.user.dto.ServeProviderInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderSimpleDTO;
import com.cskaoyan.duolai.clean.user.request.ServerProviderStatusCommand;
import com.cskaoyan.duolai.clean.common.model.ServeProviderInfo;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderAndSkillInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderOperationDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeSkillSimpleDTO;
import com.cskaoyan.duolai.clean.user.dao.entity.*;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServeProviderConverter {

    ServeProviderDO serveProviderStatusCommandToServeProviderDO(ServerProviderStatusCommand serverProviderStatusCommand);



    ServeProviderDTO serveProviderDOToDTO(ServeProviderDO serveProviderDO);

    ServeProviderInfoDTO serveProviderDTOToServeProviderInfoDTO(ServeProviderDTO serveProviderDO);

    ServeProviderSimpleDTO serveProviderDOToSimpleDTO(ServeProviderDO serveProviderDO);

    List<ServeProviderSimpleDTO> serveProviderDOsToSimpleDTOs(List<ServeProviderDO> serveProviderDO);

    ServeSkillSimpleDTO serveSkillSimpleDOToDTO(ServeSkillSimpleDO serveSkillSimpleDO);

    ServeProviderAndSkillInfoDTO serveProviderAndSkillInfoDOToDTO(ServeProviderAndSkillInfoDO serveProviderAndSkillInfoDO);


    ServeProviderOperationDTO serveProviderOperationDOToDTO(ServeProviderOperationDO serveProviderOperationDO);
    @Mapping(source = "list", target = "list")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "pages", target = "pages")
    PageDTO<ServeProviderOperationDTO> toServeProviderOperationPageDTO(List<ServeProviderOperationDO> list,
                                                                       long total, long pages);


    ServeProviderInfo serveProviderSyncDOToServeProviderInfo(ServeProviderSyncDO serveProviderDO);

    List<ServeProviderInfo> serveProviderSyncDOsToServeProviderInfos(List<ServeProviderSyncDO> serveProviderDO);
}
