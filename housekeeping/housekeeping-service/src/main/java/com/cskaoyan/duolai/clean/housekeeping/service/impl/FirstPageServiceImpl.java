package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import com.cskaoyan.duolai.clean.common.model.RegionServeInfo;
import com.cskaoyan.duolai.clean.housekeeping.converter.RegionServeConverter;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.ServeTypeMapper;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeDO;
import com.cskaoyan.duolai.clean.housekeeping.dto.SearchRegionServeDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.repository.RegionServeRepository;
import com.cskaoyan.duolai.clean.housekeeping.service.IFirstPageService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class FirstPageServiceImpl implements IFirstPageService {

    @Resource
    RegionServeRepository regionServeRepository;

    @Resource
    ServeTypeMapper serveTypeMapper;

    @Resource
    RegionServeConverter regionServeConverter;
    @Override
    public List<SearchRegionServeDTO> findServeList(String cityCode, Long serveTypeId, String keyword) {

        return null;
    }


}
