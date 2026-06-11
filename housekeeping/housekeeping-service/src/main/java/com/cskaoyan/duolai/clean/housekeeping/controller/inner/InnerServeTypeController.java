package com.cskaoyan.duolai.clean.housekeeping.controller.inner;


import com.cskaoyan.duolai.clean.housekeeping.converter.ServeTypeConverter;
import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.ServeTypeDO;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 内部接口 - 服务类型相关接口
 * </p>
 */
@RestController
@RequestMapping("/inner/serve-type")
public class InnerServeTypeController {
    @Resource
    private IServeTypeService serveTypeService;

    @Resource
    ServeTypeConverter serveTypeConverter;


    @GetMapping("/listByIds")
    public List<ServeTypeSimpleDTO> listByIds(@RequestParam("ids") List<Long> ids) {
        List<ServeTypeDO> serveTypeDOs = serveTypeService.listByIds(ids);
        return serveTypeConverter.serveTypesToSimpleDTOs(serveTypeDOs);
    }
}
