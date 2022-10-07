package com.akipgenerationweb.process.generationProcess.listener;

import com.akipgenerationweb.domain.enumeration.StatusProcess;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.camunda.CamundaConstants;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatusAndPercentageExecutedAkipProcessListener implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(UpdateStatusAndPercentageExecutedAkipProcessListener.class);

    private Expression status;

    private Expression percentageExecuted;

    @Autowired
    private AkipProcessService akipProcessService;

    @Override
    public void notify(DelegateTask delegateTask) {
        log.debug("Init of {}", UpdateStatusAndPercentageExecutedAkipProcessListener.class.getSimpleName());
        GenerationProcessDTO generationProcessDTO = (GenerationProcessDTO) delegateTask.getVariable(CamundaConstants.PROCESS_INSTANCE);
        if (this.status.getValue(delegateTask) != null && !this.status.getValue(delegateTask).toString().isEmpty()) {
            generationProcessDTO.getAkipProcess().setStatus(StatusProcess.valueOf(this.status.getValue(delegateTask).toString()));
        }
        if (
            this.percentageExecuted.getValue(delegateTask) != null && !this.percentageExecuted.getValue(delegateTask).toString().isEmpty()
        ) {
            generationProcessDTO
                .getAkipProcess()
                .setPercentageExecuted(Integer.parseInt(this.percentageExecuted.getValue(delegateTask).toString()));
        }
        akipProcessService.save(generationProcessDTO.getAkipProcess());
    }
}
