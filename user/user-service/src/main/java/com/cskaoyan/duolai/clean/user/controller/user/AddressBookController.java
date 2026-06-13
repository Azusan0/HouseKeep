package com.cskaoyan.duolai.clean.user.controller.user;


import com.cskaoyan.duolai.clean.user.converter.AddressBookConverter;
import com.cskaoyan.duolai.clean.user.dto.AddressBookDTO;
import com.cskaoyan.duolai.clean.user.dao.entity.AddressBookDO;
import com.cskaoyan.duolai.clean.user.request.AddressBookPageQueryReq;
import com.cskaoyan.duolai.clean.user.request.AddressBookCommand;
import com.cskaoyan.duolai.clean.user.service.IAddressBookService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.wildfly.common.annotation.NotNull;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 地址薄 前端控制器
 * </p>
 */
@RestController("consumerAddressBookController")
@RequestMapping("/consumer/address-book")
@Api(tags = "用户端 - 地址薄相关接口")
public class AddressBookController {
    @Resource
    private IAddressBookService addressBookService;

    @Resource
    private AddressBookConverter addressBookConverter;

    @PostMapping
    @ApiOperation("地址薄新增")
    public void add(@RequestBody AddressBookCommand addressBookCommand) {
        addressBookService.addAdress(addressBookCommand);
    }

    @PutMapping("/{id}")
    @ApiOperation("地址薄修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址薄id", required = true, dataTypeClass = Long.class)
    })
    public void update(@PathVariable("id") Long id,
                       @RequestBody AddressBookCommand addressBookCommand) {
        addressBookService.update(id, addressBookCommand);
    }

    @GetMapping("/{id}")
    @ApiOperation("地址薄详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址薄id", required = true, dataTypeClass = Long.class)
    })
    public AddressBookDTO detail(@PathVariable("id") Long id) {
        AddressBookDO addressBookDO = addressBookService.getById(id);
        return addressBookConverter.addressBookToDTO(addressBookDO);
    }

    @PutMapping("/default")
    @ApiOperation("地址薄设为默认/取消默认")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址薄id", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "flag", value = "是否为默认地址，0：否，1：是", required = true, dataTypeClass = Integer.class)
    })
    public void updateDefaultStatus(@RequestParam("id") Long id,
                                     @RequestParam("flag") Integer flag) {
        //当前登录用户id
        Long userId = UserContext.currentUserId();
        addressBookService.updateDefaultStatus(userId,id, flag);
    }

    @DeleteMapping("/batch")
    @ApiOperation("地址薄批量删除")
    @ApiImplicitParam(name = "ids", value = "地址薄id列表", required = true, dataTypeClass = List.class)
    public void logicallyDelete( @RequestBody List<Long> ids) {
        addressBookService.removeByIds(ids);
    }

    @GetMapping("/page")
    @ApiOperation("地址薄分页查询")
    public PageDTO<AddressBookDTO> page(AddressBookPageQueryReq addressBookPageQueryReqDTO) {
        return addressBookService.page(addressBookPageQueryReqDTO);
    }

    @GetMapping("/defaultAddress")
    @ApiOperation("获取默认地址")
    public AddressBookDTO defaultAddress() {
        return addressBookService.defaultAddress();
    }
}
