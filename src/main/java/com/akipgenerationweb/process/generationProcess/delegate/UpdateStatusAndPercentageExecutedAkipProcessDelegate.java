package com.akipgenerationweb.process.generationProcess.delegate;

import com.akipgenerationweb.domain.enumeration.StatusProcess;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.camunda.CamundaConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusAndPercentageExecutedAkipProcessDelegate implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(UpdateStatusAndPercentageExecutedAkipProcessDelegate.class);

    private Expression status;

    private Expression percentageExecuted;

    private AkipProcessService akipProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("Inicio de {}", UpdateStatusAndPercentageExecutedAkipProcessDelegate.class.getSimpleName());
        GenerationProcessDTO generationProcessDTO = (GenerationProcessDTO) delegateExecution.getVariable(CamundaConstants.PROCESS_INSTANCE);
        generationProcessDTO.getAkipProcess().setStatus(StatusProcess.valueOf(this.status.getValue(delegateExecution).toString()));
        if (!this.percentageExecuted.getValue(delegateExecution).toString().isEmpty()) {
            generationProcessDTO
                .getAkipProcess()
                .setPercentageExecuted(Integer.parseInt(this.percentageExecuted.getValue(delegateExecution).toString()));
        }
        akipProcessService.save(generationProcessDTO.getAkipProcess());
    }
}
