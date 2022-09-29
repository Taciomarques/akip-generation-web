package com.akipgenerationweb.process.generationProcess.taskGenerateDomainEntity;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskGenerateDomainEntityContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AkipEntityDTO entity;

    public GenerationProcessDTO getGenerationProcess() {
        return generationProcess;
    }

    public void setGenerationProcess(GenerationProcessDTO generationProcess) {
        this.generationProcess = generationProcess;
    }

    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
    }

    public AkipEntityDTO getEntity() {
        return entity;
    }

    public void setEntity(AkipEntityDTO entity) {
        this.entity = entity;
    }
}
