package com.akipgenerationweb.process.generationProcess.taskGenerateProcessBinding;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import java.util.List;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskGenerateProcessBindingContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AkipEntityDTO entity;

    private List<AkipEntityDTO> entities;

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

    public List<AkipEntityDTO> getEntities() {
        return entities;
    }

    public void setEntities(List<AkipEntityDTO> entities) {
        this.entities = entities;
    }
}
