package com.cskaoyan.duolai.clean.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.user.dao.entity.WorkerCertificationAuditDO;
import com.cskaoyan.duolai.clean.user.request.CertificationAuditCommand;
import com.cskaoyan.duolai.clean.user.request.WorkerCertificationAuditCommand;
import com.cskaoyan.duolai.clean.user.request.WorkerCertificationAuditPageRequest;
import com.cskaoyan.duolai.clean.user.dto.RejectReasonResDTO;
import com.cskaoyan.duolai.clean.user.dto.WorkerCertificationAuditDTO;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;

/**
 * <p>
 * 服务人员认证审核表 服务类
 * </p>
 */
public interface IWorkerCertificationAuditService extends IService<WorkerCertificationAuditDO> {
    /**
     * 服务人员申请资质认证
     *
     * @param workerCertificationAuditCommand 认证申请请求体
     */
    void applyCertification(WorkerCertificationAuditCommand workerCertificationAuditCommand);

    /**
     * 审核认证信息
     *
     * @param id                       申请记录id
     * @param certificationAuditCommand 审核请求
     */
    void auditCertification(Long id, CertificationAuditCommand certificationAuditCommand);

    /**
     * 分页查询
     *
     * @param workerCertificationAuditPageRequestDTO 分页查询条件
     * @return 分页结果
     */
    PageDTO<WorkerCertificationAuditDTO> pageQuery(WorkerCertificationAuditPageRequest workerCertificationAuditPageRequestDTO);

    /**
     * 查询当前用户最近驳回原因
     *
     * @return 驳回原因
     */
    RejectReasonResDTO queryCurrentUserLastRejectReason();
}
