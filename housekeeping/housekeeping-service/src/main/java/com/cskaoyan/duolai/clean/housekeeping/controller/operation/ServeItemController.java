package com.cskaoyan.duolai.clean.housekeeping.controller.operation;

import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeItemDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeItemCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeItemPageRequest;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * <p>
 * 服务表 前端控制器
 * </p>
 */
@Validated
@RestController("operationServeItemController")
@RequestMapping("/operation/serve-item")
@Api(tags = "运营端 - 服务项相关接口")
public class ServeItemController {
    @Resource
    private IServeItemService serveItemService;

    @PostMapping
    @ApiOperation("服务项新增")
    public void add(@RequestBody ServeItemCommand serveItemCommand) {
        serveItemService.addServeItem(serveItemCommand);
    }

    @PutMapping("/{id}")
    @ApiOperation("服务项修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务项id", required = true, dataTypeClass = Long.class)
    })
    public void update(@PathVariable("id") Long id, @RequestBody ServeItemCommand serveItemCommand) {
        serveItemService.updateServeItem(id, serveItemCommand);
    }


    @PutMapping("/activate/{id}")    //启用状态，1：禁用，:2：启用
    @ApiOperation("服务项启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务id", required = true, dataTypeClass = Long.class),
    })
    public void activate(@PathVariable("id") Long id) {
        serveItemService.activateServeItem(id);
    }

    @PutMapping("/deactivate/{id}")
    @ApiOperation("服务项禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务id", required = true, dataTypeClass = Long.class),
    })
    public void deactivate(@PathVariable("id") Long id) {
        serveItemService.deactivate(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("服务项删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务项id", required = true, dataTypeClass = Long.class),
    })
    public void delete(@PathVariable("id") Long id) {
        serveItemService.deleteById(id);
    }

    @GetMapping("/page")
    @ApiOperation("服务项分页查询")
    public PageDTO<ServeItemDTO> page(ServeItemPageRequest serveItemPageQueryReqDTO) {
        return serveItemService.page(serveItemPageQueryReqDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询服务项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务项id", required = true, dataTypeClass = Long.class)
    })
    public ServeItemDTO findById(@PathVariable("id") Long id) {
        return serveItemService.queryServeItemAndTypeById(id);
    }


    @PutMapping("refresh/{id}")
    @ApiOperation("根据id刷新服务项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务项id", required = true, dataTypeClass = Long.class)
    })
    public void refreshById(@PathVariable("id") Long id) {
        serveItemService.refreshServeItemCache(id);
    }
}
