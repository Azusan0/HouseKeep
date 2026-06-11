package com.cskaoyan.duolai.clean.housekeeping.converter;

import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeInfoDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeInfoDO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeTypeCommand;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ServeItemConverter.class})
public interface ServeTypeConverter {

   ServeTypeDO serverTypeCommandToServeTypeDO(ServeTypeCommand serveTypeCommand);

    ServeTypeSimpleDTO serveTypesToSimpleDTOs(ServeTypeDO serveTypeDO);

    List<ServeTypeSimpleDTO> serveTypesToSimpleDTOs(List<ServeTypeDO> serveTypeDO);

    List<ServeTypeDTO> serveTypeToSimpleResDTOs(List<ServeTypeDO> serveTypeDOs);

    ServeTypeInfoDTO serveTypeInfoDO2DTO(ServeTypeInfoDO serveTypeInfoDO);

     ServeTypeDTO serveTypeDOToServeTypeDTO(ServeTypeDO serveTypeDO);

 List<ServeTypeInfoDTO> serveTypeInfoDOs2DTOs(List<ServeTypeInfoDO> serveTypeDO);
    @Mapping(source = "serveTypeDOS", target = "list")
    @Mapping(source = "totalCount", target = "total")
    @Mapping(source = "totalPage", target = "pages")
    PageDTO<ServeTypeDTO> servCategoryDOToServCategoryHomepage(List<ServeTypeDO> serveTypeDOS, Long totalCount, Long totalPage);



}
