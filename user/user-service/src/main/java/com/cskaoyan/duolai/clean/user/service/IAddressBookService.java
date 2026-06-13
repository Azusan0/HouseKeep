package com.cskaoyan.duolai.clean.user.service;

import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.user.dto.AddressBookDTO;
import com.cskaoyan.duolai.clean.user.dao.entity.AddressBookDO;
import com.cskaoyan.duolai.clean.user.request.AddressBookPageQueryReq;
import com.cskaoyan.duolai.clean.user.request.AddressBookCommand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 地址薄 服务类
 * </p>
 */
public interface IAddressBookService extends IService<AddressBookDO> {
    /**
     * 地址薄新增
     *
     * @param addressBookCommand 插入更新地址薄
     */
    void addAdress(AddressBookCommand addressBookCommand);

    /**
     * 地址薄修改
     *
     * @param id                      地址薄id
     * @param addressBookCommand 插入更新地址薄
     */
    void update(Long id, AddressBookCommand addressBookCommand);

    /**
     * 地址薄设为默认/取消默认
     *
     * @param userId   用户id
     * @param id   地址薄id
     * @param flag 是否为默认地址，0：否，1：是
     */
    void updateDefaultStatus(Long userId,Long id, Integer flag);

    /**
     * 分页查询
     *
     * @param addressBookPageQueryReqDTO 查询条件
     * @return 分页结果
     */
    PageDTO<AddressBookDTO> page(AddressBookPageQueryReq addressBookPageQueryReqDTO);

    /**
     * 获取默认地址
     *
     * @return 默认地址
     */
    AddressBookDTO defaultAddress();

    /**
     * 根据用户id和城市编码获取地址
     *
     * @param userId 用户id
     * @param cityCode 城市编码
     * @return 地址编码
     */
    List<AddressBookDTO> getByUserIdAndCity(Long userId, String cityCode);
}
