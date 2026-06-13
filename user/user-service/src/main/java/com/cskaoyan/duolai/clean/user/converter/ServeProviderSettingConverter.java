package com.cskaoyan.duolai.clean.user.converter;

import com.cskaoyan.duolai.clean.user.dao.entity.ServeProviderSettingsDO;
import com.cskaoyan.duolai.clean.user.request.ServeScopeCommand;
import com.cskaoyan.duolai.clean.user.dto.ServeProviderSettingsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServeProviderSettingConverter {

    @Mapping(target = "haveSkill", expression = "java(serveProviderSettingsDO.getHaveSkill() == 1 ? true : false)")
    @Mapping(target = "canPickUp", expression = "java(serveProviderSettingsDO.getCanPickUp() == 1 ? true : false)")
    ServeProviderSettingsDTO serveProviderSettingsDOToDTO(ServeProviderSettingsDO serveProviderSettingsDO);


    ServeProviderSettingsDO serveScopeCommandToServeProviderSettingsDO(ServeScopeCommand serveScopeCommand);
}
