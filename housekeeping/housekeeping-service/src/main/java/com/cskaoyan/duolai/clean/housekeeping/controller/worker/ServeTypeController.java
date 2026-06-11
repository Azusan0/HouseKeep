package com.cskaoyan.duolai.clean.housekeeping.controller.worker;

import com.cskaoyan.duolai.clean.housekeeping.dto.ServeTypeSimpleDTO;
import com.cskaoyan.duolai.clean.housekeeping.service.IServeTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 服务类型相关接口
 **/
@RestController("workerServeTypeController")
@RequestMapping("/worker/serve-type")
public class ServeTypeController {
    @Resource
    private IServeTypeService serveTypeService;

    @GetMapping("/queryServeTypeListByActiveStatus")
    public List<ServeTypeSimpleDTO> queryServeTypeListByActiveStatus(@RequestParam(value = "activeStatus", required = false) Integer activeStatus) {
        return serveTypeService.activeList(activeStatus);
    }
}
