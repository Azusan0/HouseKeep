package com.cskaoyan.duolai.clean.housekeeping.converter;

import com.cskaoyan.duolai.clean.housekeeping.dto.ConfigRegionDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ConfigRegionDO;
import com.cskaoyan.duolai.clean.housekeeping.request.ConfigRegionCommand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfigRegionConverter {

    ConfigRegionDO configRegionCommandToDO(ConfigRegionCommand configRegionCommand);

    ConfigRegionDTO configRegionDOToDTO(ConfigRegionDO configRegionDO);

    List<ConfigRegionDTO> configRegionDOsToDTOs(List<ConfigRegionDO> configRegionDOs);
}
