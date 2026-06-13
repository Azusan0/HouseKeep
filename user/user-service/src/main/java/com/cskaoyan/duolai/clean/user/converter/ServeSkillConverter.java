package com.cskaoyan.duolai.clean.user.converter;

import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemInfoSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeInfoDTO;
import com.cskaoyan.duolai.clean.user.dao.entity.ServeSkillDO;
import com.cskaoyan.duolai.clean.user.request.ServeSkillCommand;
import com.cskaoyan.duolai.clean.user.dto.ServeSkillInfoDTO;
import com.cskaoyan.duolai.clean.user.dto.ServeSkillItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServeSkillConverter {


    ServeSkillDO serveSkillCommandToServeSkillDO(ServeSkillCommand serveSkillCommand);

    List<ServeSkillDO> serveSkillCommandsToServeSkillDOs(List<ServeSkillCommand> serveSkillCommand);

    @Mapping(source = "serveItemList", target = "serveSkillItemDTOList")
    ServeSkillInfoDTO serveTypeInfoDTOToserveSkillInfoDTO(ServeTypeInfoDTO serveTypeInfoDTO);


    List<ServeSkillInfoDTO> serveTypeInfoDTOsToServeSkillInfoDTOs(List<ServeTypeInfoDTO> serveTypeInfoDTOs);

    ServeItemInfoSimpleDTO serveItemInfoSimpleDTOToServeSkillItemDTO(ServeSkillItemDTO serveSkillItemDTO);
}
