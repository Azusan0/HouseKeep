package com.cskaoyan.duolai.clean.housekeeping.service;


import com.cskaoyan.duolai.clean.housekeeping.dto.SearchRegionServeDTO;

import java.util.List;

public interface IFirstPageService {

    /**
     * 查询服务列表
     *
     * @param cityCode    城市编码
     * @param serveTypeId 服务类型id
     * @param keyword     关键词
     * @return 服务列表
     */
    List<SearchRegionServeDTO> findServeList(String cityCode, Long serveTypeId, String keyword);
}
