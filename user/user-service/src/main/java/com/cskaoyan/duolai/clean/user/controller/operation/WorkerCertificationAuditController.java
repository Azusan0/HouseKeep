package com.cskaoyan.duolai.clean.user.controller.operation;

import com.cskaoyan.duolai.clean.user.request.CertificationAuditCommand;
import com.cskaoyan.duolai.clean.user.request.WorkerCertificationAuditPageRequest;
import com.cskaoyan.duolai.clean.user.dto.WorkerCertificationAuditDTO;
import com.cskaoyan.duolai.clean.user.service.IWorkerCertificationAuditService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("operationWorkerCertificationAuditController")
@RequestMapping("/operation/worker-certification-audit")
@Api(tags = "运营端 - 服务人员认证审核相关接口")
public class WorkerCertificationAuditController {

    @Resource
    private IWorkerCertificationAuditService workerCertificationAuditService;

    @GetMapping("/page")
    @ApiOperation("服务人员认证审核信息分页查询")
    public PageDTO<WorkerCertificationAuditDTO> page(WorkerCertificationAuditPageRequest workerCertificationAuditPageRequest) {
        return workerCertificationAuditService.pageQuery(workerCertificationAuditPageRequest);
    }

    @PutMapping("/audit/{id}")
    @ApiOperation("审核服务人员认证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "审核记录id", required = true, dataTypeClass = Long.class)
    })
    public void auditCertification(@PathVariable("id") Long id, CertificationAuditCommand certificationAuditCommand) {
        workerCertificationAuditService.auditCertification(id, certificationAuditCommand);
    }
}
