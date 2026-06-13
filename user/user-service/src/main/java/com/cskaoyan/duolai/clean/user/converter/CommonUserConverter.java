package com.cskaoyan.duolai.clean.user.converter;

import com.cskaoyan.duolai.clean.user.dto.CommonUserDTO;
import com.cskaoyan.duolai.clean.user.request.CommonUserCommand;
import com.cskaoyan.duolai.clean.user.dao.entity.CommonUserDO;
import com.cskaoyan.duolai.clean.user.request.LoginForCustomerCommand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonUserConverter {

    CommonUserDO loginForCustomerCommandToCommonUserDO(LoginForCustomerCommand loginForCustomerCommand);
    CommonUserDO commonUserCommandToDO(CommonUserCommand command);

    CommonUserDTO commonUserToDTO(CommonUserDO commonUserDO);

    List<CommonUserDTO> commonUsersToDTOs(List<CommonUserDO> commonUserDOS);
}
