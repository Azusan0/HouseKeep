package com.cskaoyan.duolai.clean.housekeeping.converter;

import com.cskaoyan.duolai.clean.housekeeping.dto.RegionSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionCommand;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionConverter {

    RegionDO regionCommandToRegionDO(RegionCommand regionCommand);

    RegionDTO regionDOToRegionDTO(RegionDO regionDO);

    List<RegionDTO> regionDOsToRegionDTOs(List<RegionDO> regionDOs);

    RegionSimpleDTO regionDOToRegionSimpleDTO(RegionDO regionDO);
    List<RegionSimpleDTO> regionDOsToRegionSimpleDTOs(List<RegionDO> regionDOs);
}
