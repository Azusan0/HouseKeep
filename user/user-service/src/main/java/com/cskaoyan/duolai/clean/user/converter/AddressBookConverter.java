package com.cskaoyan.duolai.clean.user.converter;

import com.cskaoyan.duolai.clean.user.dto.AddressBookDTO;
import com.cskaoyan.duolai.clean.user.dao.entity.AddressBookDO;
import com.cskaoyan.duolai.clean.user.request.AddressBookCommand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressBookConverter {

    AddressBookDO addressBookCommandToDO(AddressBookCommand addressBookCommand);
    AddressBookDTO addressBookToDTO(AddressBookDO addressBookDO);

    List<AddressBookDTO> addressBooksToDTOs(List<AddressBookDO> addressBookDO);
}
