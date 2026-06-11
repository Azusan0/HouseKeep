package com.cskaoyan.duolai.clean.housekeeping.converter;


import com.cskaoyan.duolai.clean.common.model.RegionServeInfo;
import com.cskaoyan.duolai.clean.housekeeping.dto.*;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.*;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionServeConverter {

   RegionServeDTO regionServeDOToRegionServeDTO(RegionServeDO regionServeDO);

   List<RegionServeDTO> regionServeDOsToRegionServeDTOs(List<RegionServeDO> records);

   RegionServeDO regionServeCommand2DO(RegionServeCommand command);

   RegionServeInfo syncServeDO2Info(RegionServeSyncDO regionServeSyncDO);

   List<RegionServeInfo> syncServeDO2Infos(List<RegionServeSyncDO> regionServeSyncDOS);

   RegionServeDetailDTO regionServeDetailDO2DTO (RegionServeDetailDO regionServeDetailDO);

   List<RegionServeDetailDTO> regionServeDetailDOs2DTOs (List<RegionServeDetailDO> regionServeDetailDO);

   @Mapping(source = "id", target = "serveTypeId")
   @Mapping(source = "name", target = "serveTypeName")
   @Mapping(source = "img", target = "serveTypeImg")
   @Mapping(source = "sortNum", target = "serveTypeSortNum")
   DisplayServeTypeDTO serveTypeDOToFirstPageServeTypeDTO(ServeTypeDO serveTypeDO);

   List<DisplayServeTypeDTO> serveTypeDOsToFirstPageServeTypeDTOs(List<ServeTypeDO> serveTypeDO);

   ServeDetailDTO serveDetailDO2DTO(ServeDetailDO regionServeDetailDO);

   @Mapping(source = "serveTypeDO.id", target = "serveTypeId")
   @Mapping(source = "serveTypeDO.name", target = "serveTypeName")
   @Mapping(source = "serveTypeDO.serveTypeIcon", target = "serveTypeIcon")
   @Mapping(source = "serveTypeDO.img", target = "serveTypeImg")
   @Mapping(source = "serveTypeDO.sortNum", target = "serveTypeSortNum")
   @Mapping(source = "serveItemDO.id", target = "serveItemId")
   @Mapping(source = "serveItemDO.serveItemIcon", target = "serveItemIcon")
   @Mapping(source = "serveItemDO.name", target = "serveItemName")
   @Mapping(source = "serveItemDO.img", target = "serveItemImg")
   @Mapping(source = "serveItemDO.sortNum", target = "serveItemSortNum")
   @Mapping(source = "serveItemDO.unit", target = "unit")
   @Mapping(source = "serveItemDO.detailImg", target = "detailImg")
   @Mapping(source = "regionServe.price", target = "price")
   @Mapping(source = "regionServe.cityCode", target = "cityCode")
   @Mapping(source = "regionServe.id", target = "id")
   // @Mapping(source = "regionServe.isHot", target = "isHot")
   RegionServeSyncDO regionServeToServeSyncDO(ServeTypeDO serveTypeDO, ServeItemDO serveItemDO
           , RegionServeDO regionServe, String cityCode);


   List<SearchRegionServeDTO> regionServeInfosToFirstPageRegionServeDTOs(List<RegionServeInfo> regionServeInfo);


   ServeTypeHomeDTO serveTypeHomeDOToDTO(ServeTypeHomeDO serveTypeHomeDO);

   List<ServeTypeHomeDTO>  serveTypeHomeDOsToDTOs(List<ServeTypeHomeDO> serveTypeHomeDO);

}
