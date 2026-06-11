package com.cskaoyan.duolai.clean.canal.converter;

import com.cskaoyan.duolai.clean.canal.model.CanalMqInfo;
import com.cskaoyan.duolai.clean.canal.model.dto.CanalBaseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CanalConverter {

    CanalBaseDTO convertToCanalBaseDTO(CanalMqInfo canalMqInfo);
}
