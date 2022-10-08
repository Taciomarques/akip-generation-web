package com.akipgenerationweb.process.generationProcess.taskConfigureServiceTaskProcess;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskConfigureServiceTaskProcessContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AkipEntityDTO akipEntityServiceTask;

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

    public AkipEntityDTO getAkipEntityServiceTask() {
        return akipEntityServiceTask;
    }

    public void setAkipEntityServiceTask(AkipEntityDTO akipEntityServiceTask) {
        this.akipEntityServiceTask = akipEntityServiceTask;
    }
}
