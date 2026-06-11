package com.cskaoyan.duolai.clean.housekeeping.converter;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemInfoSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeItemDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeItemInfoSimpleDO;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeSyncDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeItemCommand;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionServeDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServeItemConverter {

    ServeItemDO serveItemCommandToServItemDO(ServeItemCommand serveItemCommand);

    RegionServeSyncDTO serveItemCommandToRegionServeSyncDTO(ServeItemCommand serveItemCommand);

    ServeItemDTO serveItemDOToServeItemDTO(ServeItemDO serveItemDO);

    List<ServeItemDTO> serveItemDOsToServeItemDTOs(List<ServeItemDO> serveItemDOs);

    ServeItemSimpleDTO serveItemDOToServeItemSimpleDTO(ServeItemDO serveItemDO);
    List<ServeItemSimpleDTO> serveItemDOsToServeItemSimpleDTOs(List<ServeItemDO> serveItemDOs);

    ServeItemInfoSimpleDTO serveItemInfoSimpleDO2DTO(ServeItemInfoSimpleDO serveItemInfoSimpleDO);
    @Mapping(source = "id", target = "serveItemId")
    @Mapping(source = "name", target = "serveItemName")
    @Mapping(source = "img", target = "serveItemImg")
    RegionServeDetailDTO serveItemDO2RegionServeDetailDTO(ServeItemDO serveItemDO);


    @Mapping(source = "serveItemDOs", target = "list")
    @Mapping(source = "totalCount", target = "total")
    @Mapping(source = "totalPage", target = "pages")
    PageDTO<ServeItemDTO> serveItemDOsToPageDTO(List<ServeItemDO> serveItemDOs, Long totalCount, Long totalPage);

}
