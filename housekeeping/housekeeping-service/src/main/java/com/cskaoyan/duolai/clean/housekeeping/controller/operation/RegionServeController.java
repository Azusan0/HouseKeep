package com.cskaoyan.duolai.clean.housekeeping.controller.operation;

import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.RegionServeDO;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionServeDetailDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeDetailDTO;
import com.cskaoyan.duolai.clean.housekeeping.enums.HousekeepingStatusEnum;
import com.cskaoyan.duolai.clean.housekeeping.request.RegionServeCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServePageRequest;
import com.cskaoyan.duolai.clean.housekeeping.dto.RegionServeDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IRegionServeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.wildfly.common.annotation.NotNull;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 */
@RestController("operationServeController")
@RequestMapping("/operation/serve")
@Api(tags = "运营端 - 区域服务相关接口")
public class RegionServeController {

    @Resource
    private IRegionServeService serveService;


    @GetMapping("/page")
    // "区域服务分页查询"
    public PageDTO<RegionServeDTO> getPage(ServePageRequest servePageQueryReqDTO) {
        PageDTO<RegionServeDTO> page = serveService.getPage(servePageQueryReqDTO);
        return page;
    }

    @PostMapping("/batch")
    // "区域服务批量新增"
    public void add(@RequestBody List<RegionServeCommand> regionServeCommandList) {
        serveService.batchAdd(regionServeCommandList);
    }

    @PutMapping("/{id}")
    // "区域服务价格修改"
    public void updatePrice(@NotNull() @PathVariable("id") Long id,
                       @NotNull() @RequestParam("price") Double price) {
        RegionServeDO byId = serveService.getById(id);
        serveService.updatePrice(id, byId.getRegionId(), BigDecimal.valueOf(price));
    }

    @DeleteMapping("/{id}")
    //"区域服务删除"
    public void delete(@NotNull() @PathVariable("id") Long id) {
        serveService.deleteById(id);
    }

    @PutMapping("/onSale/{id}")
    // "区域服务上架"
    public void onSale(@PathVariable("id") Long id) {
        serveService.onSale(id);
    }

    @PutMapping("/offSale/{id}")
    // "区域服务下架"
    public void offSale(@PathVariable("id") Long id) {
        serveService.offSale(id);
    }

    @PutMapping("/onHot/{id}")
    // "区域服务设置热门"
    public void onHot(@NotNull() @PathVariable("id") Long id) {
        RegionServeDO byId = serveService.getById(id);
        serveService.changeHotStatus(id, byId.getRegionId(), HousekeepingStatusEnum.HOT.getStatus());
    }

    @PutMapping("/offHot/{id}")
    // "区域服务取消热门"
    public void offHot(@NotNull() @PathVariable("id") Long id) {
        RegionServeDO byId = serveService.getById(id);
        serveService.changeHotStatus(id, byId.getRegionId(), HousekeepingStatusEnum.NOT_HOT.getStatus());
    }


}
