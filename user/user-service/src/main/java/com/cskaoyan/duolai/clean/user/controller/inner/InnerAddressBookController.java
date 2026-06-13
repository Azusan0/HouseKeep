package com.cskaoyan.duolai.clean.user.controller.inner;

import com.cskaoyan.duolai.clean.user.converter.AddressBookConverter;
import com.cskaoyan.duolai.clean.user.dao.entity.AddressBookDO;
import com.cskaoyan.duolai.clean.user.dto.AddressBookDTO;
import com.cskaoyan.duolai.clean.user.service.IAddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 内部接口 - 地址薄相关接口
 **/
@RestController
@RequestMapping("inner/address-book")
@Api(tags = "内部接口 - 地址薄相关接口")
public class InnerAddressBookController {
    @Resource
    private IAddressBookService addressBookService;

    @Resource
    private AddressBookConverter addressBookConverter;


    @GetMapping("/{id}")
    @ApiOperation("地址薄详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址薄id", required = true, dataTypeClass = Long.class)
    })
    public AddressBookDTO detail(@PathVariable("id") Long id) {
        AddressBookDO addressBookDO = addressBookService.getById(id);
        return addressBookConverter.addressBookToDTO(addressBookDO);
    }


    @GetMapping("/getByUserIdAndCity")
    @ApiOperation("根据用户id和城市获取用户地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "city", value = "城市名称", required = true, dataTypeClass = String.class)

    })
    public List<AddressBookDTO> getByUserIdAndCity(@RequestParam("userId") Long userId, @RequestParam("city") String city) {
        return addressBookService.getByUserIdAndCity(userId, city);
    }
}
