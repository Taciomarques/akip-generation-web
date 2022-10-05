package com.akipgenerationweb.process.generationProcess.taskGenerateProcessBinding;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import java.util.List;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskGenerateProcessBindingContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AkipEntityDTO akipEntityProcessBinding;

    private List<AkipEntityDTO> akipEntitiesDomain;

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

    public AkipEntityDTO getAkipEntityProcessBinding() {
        return akipEntityProcessBinding;
    }

    public void setAkipEntityProcessBinding(AkipEntityDTO akipEntityProcessBinding) {
        this.akipEntityProcessBinding = akipEntityProcessBinding;
    }

    public List<AkipEntityDTO> getAkipEntitiesDomain() {
        return akipEntitiesDomain;
    }

    public void setAkipEntitiesDomain(List<AkipEntityDTO> akipEntitiesDomain) {
        this.akipEntitiesDomain = akipEntitiesDomain;
    }
}
