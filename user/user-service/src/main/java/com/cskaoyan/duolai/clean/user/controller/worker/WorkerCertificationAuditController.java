package com.cskaoyan.duolai.clean.user.controller.worker;

import com.cskaoyan.duolai.clean.user.request.WorkerCertificationAuditCommand;
import com.cskaoyan.duolai.clean.user.dto.RejectReasonResDTO;
import com.cskaoyan.duolai.clean.user.service.IWorkerCertificationAuditService;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("workerWorkerCertificationAuditController")
@RequestMapping("/worker/worker-certification-audit")
@Api(tags = "服务端 - 服务人员认证审核相关接口")
public class WorkerCertificationAuditController {

    @Resource
    private IWorkerCertificationAuditService workerCertificationAuditService;

    @PostMapping
    @ApiOperation("提交认证申请")
    public void auditCertification(@RequestBody WorkerCertificationAuditCommand workerCertificationAuditCommand) {
        // 获取当前完成实名认证的服务从业者的id
        Long serveProviderId = UserContext.currentUserId();
        workerCertificationAuditCommand.setServeProviderId(serveProviderId);
        workerCertificationAuditService.applyCertification(workerCertificationAuditCommand);
    }



    @GetMapping("/rejectReason")
    @ApiOperation("查询最新的驳回原因")
    public RejectReasonResDTO queryCurrentUserLastRejectReason() {
        return workerCertificationAuditService.queryCurrentUserLastRejectReason();
    }
}
