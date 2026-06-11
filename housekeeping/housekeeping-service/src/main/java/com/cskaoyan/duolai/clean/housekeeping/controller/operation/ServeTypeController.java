package com.cskaoyan.duolai.clean.housekeeping.controller.operation;

import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeTypeCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.ServeTypePageRequest;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeTypeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 服务类型相关接口
 **/
@RestController("operationServeCategoryController")
@RequestMapping("/operation/serve-type")
public class ServeTypeController {
    @Resource
    private IServeTypeService serveTypeService;

    @GetMapping("active/list")
    @ApiOperation("返回启用的服务类型列表")
    public List<ServeTypeSimpleDTO> activeList() {
        // 2 为激活状态
        return serveTypeService.activeList(2);
    }

    @PostMapping
    @ApiOperation("服务类型新增")
    public void add(@RequestBody ServeTypeCommand serveTypeCommand) {
        serveTypeService.addServeType(serveTypeCommand);
    }

    @PutMapping("/{id}")
    @ApiOperation("服务类型修改")
    public void update( @PathVariable("id") Long id,
                       @RequestBody ServeTypeCommand serveTypeCommand) {
        serveTypeService.updateServeType(id, serveTypeCommand);
    }

    @PutMapping("/activate/{id}")
    @ApiOperation("服务类型启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务类型id", required = true, dataTypeClass = Long.class),
    })
    public void activate(@PathVariable("id") Long id) {
        serveTypeService.activateServeType(id);
    }

    @PutMapping("/deactivate/{id}")
    @ApiOperation("服务类型禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务类型id", required = true, dataTypeClass = Long.class),
    })
    public void deactivate(@PathVariable("id") Long id) {
        serveTypeService.deactivateServeType(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("服务类型删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务类型id", required = true, dataTypeClass = Long.class)
    })
    public void delete(@PathVariable("id") Long id) {
        serveTypeService.deleteById(id);
    }

    @GetMapping("/page")
    @ApiOperation("服务类型分页查询")
    public PageDTO<ServeTypeDTO> page(ServeTypePageRequest serveTypePageRequest) {
        return serveTypeService.getPage(serveTypePageRequest);
    }
}
