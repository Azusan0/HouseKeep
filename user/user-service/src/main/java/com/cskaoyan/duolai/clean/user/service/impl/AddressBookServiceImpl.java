package com.cskaoyan.duolai.clean.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.dto.LocationResDTO;
import com.cskaoyan.duolai.clean.user.client.MapApi;
import com.cskaoyan.duolai.clean.user.converter.AddressBookConverter;
import com.cskaoyan.duolai.clean.user.dao.mapper.AddressBookMapper;
import com.cskaoyan.duolai.clean.user.dao.entity.AddressBookDO;
import com.cskaoyan.duolai.clean.user.request.AddressBookPageQueryReq;
import com.cskaoyan.duolai.clean.user.request.AddressBookCommand;
import com.cskaoyan.duolai.clean.user.service.IAddressBookService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.common.utils.CollUtils;
import com.cskaoyan.duolai.clean.common.utils.NumberUtils;
import com.cskaoyan.duolai.clean.common.utils.StringUtils;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import com.cskaoyan.duolai.clean.mysql.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cskaoyan.duolai.clean.user.dto.AddressBookDTO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 地址薄 服务实现类
 * </p>
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBookDO> implements IAddressBookService {
    @Resource
    private AddressBookMapper addressBookMapper;
    @Resource
    private MapApi mapApi;

    @Resource
    AddressBookConverter addressBookConverter;

    /**
     * 地址薄新增
     *
     * @param addressBookCommand 插入更新地址薄
     */
    @Override
    public void addAdress(AddressBookCommand addressBookCommand) {
        //当前用户id
        Long userId = UserContext.currentUserId();
        //如果新增地址设为默认，取消其他默认地址
        if (1 == addressBookCommand.getIsDefault()) {
            cancelDefault(userId);
        }

        AddressBookDO addressBookDO = addressBookConverter.addressBookCommandToDO(addressBookCommand);
        addressBookDO.setUserId(userId);

        //组装详细地址
        String completeAddress = addressBookCommand.getProvince() +
                addressBookCommand.getCity() +
                addressBookCommand.getCounty() +
                addressBookCommand.getAddress();

        //如果请求体中没有经纬度，需要调用第三方api根据详细地址获取经纬度
        if(ObjectUtil.isEmpty(addressBookCommand.getLocation())){
            //远程请求高德获取经纬度
            LocationResDTO locationDto = mapApi.getLocationByAddress(completeAddress);
            //经纬度(字符串格式：经度,纬度),经度在前，纬度在后
            String location = locationDto.getLocation();
            addressBookCommand.setLocation(location);
        }

        if(StringUtils.isNotEmpty(addressBookCommand.getLocation())) {
            // 经度
            addressBookDO.setLon(NumberUtils.parseDouble(addressBookCommand.getLocation().split(",")[0]));
            // 纬度
            addressBookDO.setLat(NumberUtils.parseDouble(addressBookCommand.getLocation().split(",")[1]));
        }
        addressBookMapper.insert(addressBookDO);
    }

    /**
     * 地址薄修改
     *
     * @param id                      地址薄id
     * @param addressBookCommand 插入更新地址薄
     */
    @Override
    @Transactional
    public void update(Long id, AddressBookCommand addressBookCommand) {
        if (1 == addressBookCommand.getIsDefault()) {
            cancelDefault(UserContext.currentUserId());
        }

        AddressBookDO addressBookDO = addressBookConverter.addressBookCommandToDO(addressBookCommand);
        addressBookDO.setId(id);

        //调用第三方，根据地址获取经纬度坐标
        String completeAddress = addressBookCommand.getProvince() +
                addressBookCommand.getCity() +
                addressBookCommand.getCounty() +
                addressBookCommand.getAddress();
        //远程请求高德获取经纬度
        LocationResDTO locationDto = mapApi.getLocationByAddress(completeAddress);
        //经纬度(字符串格式：经度,纬度),经度在前，纬度在后
        String location = locationDto.getLocation();
        if(StringUtils.isNotEmpty(location)) {
            // 经度
            addressBookDO.setLon(NumberUtils.parseDouble(locationDto.getLocation().split(",")[0]));
            // 纬度
            addressBookDO.setLat(NumberUtils.parseDouble(locationDto.getLocation().split(",")[1]));
        }
        addressBookMapper.updateById(addressBookDO);
    }

    /**
     * 取消默认
     *
     * @param userId 用户id
     */
    private void cancelDefault(Long userId) {
        LambdaUpdateWrapper<AddressBookDO> updateWrapper = Wrappers.<AddressBookDO>lambdaUpdate()
                .eq(AddressBookDO::getUserId, userId)
                .set(AddressBookDO::getIsDefault, 0);
        super.update(updateWrapper);
    }

    /**
     * 地址薄设为默认/取消默认
     *
     * @param userId 用户id
     * @param id   地址薄id
     * @param flag 是否为默认地址，0：否，1：是
     */
    @Override
    public void updateDefaultStatus(Long userId,Long id, Integer flag) {
        if (1 == flag) {
            //如果设默认地址，先把其他地址取消默认
            cancelDefault(userId);
        }

        AddressBookDO addressBookDO = new AddressBookDO();
        addressBookDO.setId(id);
        addressBookDO.setIsDefault(flag);
        addressBookMapper.updateById(addressBookDO);
    }

    /**
     * 分页查询
     *
     * @param addressBookPageQueryReqDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageDTO<AddressBookDTO> page(AddressBookPageQueryReq addressBookPageQueryReqDTO) {
        Page<AddressBookDO> page = PageUtils.parsePageQuery(addressBookPageQueryReqDTO, AddressBookDO.class);

        LambdaQueryWrapper<AddressBookDO> queryWrapper = Wrappers.<AddressBookDO>lambdaQuery().eq(AddressBookDO::getUserId, UserContext.currentUserId());
        Page<AddressBookDO> serveTypePage = addressBookMapper.selectPage(page, queryWrapper);
        List<AddressBookDTO> addressBookDTOS = addressBookConverter.addressBooksToDTOs(serveTypePage.getRecords());
        return PageUtils.toPage(serveTypePage, addressBookDTOS);
    }

    /**
     * 获取默认地址
     *
     * @return 默认地址
     */
    @Override
    public AddressBookDTO defaultAddress() {
        LambdaQueryWrapper<AddressBookDO> queryWrapper = Wrappers.<AddressBookDO>lambdaQuery()
                .eq(AddressBookDO::getUserId, UserContext.currentUserId())
                .eq(AddressBookDO::getIsDefault, 1);
        AddressBookDO addressBookDO = addressBookMapper.selectOne(queryWrapper);
        return addressBookConverter.addressBookToDTO(addressBookDO);
    }

    @Override
    public List<AddressBookDTO> getByUserIdAndCity(Long userId, String city) {

        List<AddressBookDO> addressBookDOS = lambdaQuery()
                .eq(AddressBookDO::getUserId, userId)
                .eq(AddressBookDO::getCity, city)
                .list();
        if(CollUtils.isEmpty(addressBookDOS)) {
            return new ArrayList<>();
        }

        return  addressBookConverter.addressBooksToDTOs(addressBookDOS);
    }
}
