package com.cskaoyan.duolai.clean.user.controller.worker;


import com.cskaoyan.duolai.clean.user.request.ServeSkillCommand;
import com.cskaoyan.duolai.clean.user.dto.ServeSkillInfoDTO;
import com.cskaoyan.duolai.clean.user.service.IServeSkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务技能表 前端控制器
 * </p>
 */
@RestController("workerServeSkillController")
@RequestMapping("/worker/serve-skill")
@Api(tags = "服务端 - 服务技能相关接口")
public class ServeSkillController {
    @Resource
    private IServeSkillService serveSkillService;

    @PostMapping("/batchUpsert")
    @ApiOperation("批量新增或修改服务技能")
    public void listServeType(@RequestBody List<ServeSkillCommand> serveSkillCommandList) {
        serveSkillService.batchUpsert(serveSkillCommandList);
    }

    @GetMapping("/category")
    @ApiOperation("查询服务技能目录")
    public List<ServeSkillInfoDTO> category() {
        return serveSkillService.category();
    }
}
