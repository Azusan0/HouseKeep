package com.cskaoyan.duolai.clean.user.converter;

import com.cskaoyan.duolai.clean.user.dao.entity.WorkerCertificationAuditDO;
import com.cskaoyan.duolai.clean.user.dao.entity.WorkerCertificationDO;
import com.cskaoyan.duolai.clean.user.request.WorkerCertificationAuditCommand;
import com.cskaoyan.duolai.clean.user.dto.CertificationStatusDTO;
import com.cskaoyan.duolai.clean.user.dto.WorkerCertificationAuditDTO;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.user.dto.WorkerCertificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkerCertificationConverter {

    WorkerCertificationDO workCertificationAuditCommandToWorkCertificationDO(WorkerCertificationAuditCommand command);

    WorkerCertificationAuditDO workCertificationAuditCommandToDO(WorkerCertificationAuditCommand command);

    WorkerCertificationAuditDTO workerCertificationAuditResDTO(WorkerCertificationAuditDO workerCertificationAuditDO);

    WorkerCertificationDTO workerCertificationDOToDTO(WorkerCertificationDO workerCertificationDO);

    CertificationStatusDTO workCertificationDO2CertificationStatusDTO(WorkerCertificationDO workerCertificationDO);

    @Mapping(source = "workerCertificationAuditDOs", target = "list")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "page", target = "pages")
    PageDTO<WorkerCertificationAuditDTO> workerCertificationAuditDOsToPageDTO(List<WorkerCertificationAuditDO> workerCertificationAuditDOs, Long total, Long page);
}
