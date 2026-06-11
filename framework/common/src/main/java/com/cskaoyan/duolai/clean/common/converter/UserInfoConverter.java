package com.cskaoyan.duolai.clean.common.converter;

import cn.hutool.json.JSONObject;
import com.cskaoyan.duolai.clean.common.model.CurrentUserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfoConverter {
    UserInfoConverter INSTANCE = Mappers.getMapper(UserInfoConverter.class);
    CurrentUserInfo convertToCurrentUserInfo(JSONObject token);
}
