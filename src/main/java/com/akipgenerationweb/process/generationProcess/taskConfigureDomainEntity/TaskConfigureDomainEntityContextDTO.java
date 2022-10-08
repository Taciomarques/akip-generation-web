package com.akipgenerationweb.process.generationProcess.taskConfigureDomainEntity;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskConfigureDomainEntityContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AkipEntityDTO akipEntityDomain;

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

    public AkipEntityDTO getAkipEntityDomain() {
        return akipEntityDomain;
    }

    public void setAkipEntityDomain(AkipEntityDTO akipEntityDomain) {
        this.akipEntityDomain = akipEntityDomain;
    }
}
