package com.akipgenerationweb.process.generationProcess.taskGenerateEntities;

import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskGenerateEntitiesContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AttachmentDTO metadataAkipEntityDomain;

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

    public AttachmentDTO getMetadataAkipEntityDomain() {
        return metadataAkipEntityDomain;
    }

    public void setMetadataAkipEntityDomain(AttachmentDTO metadataAkipEntityDomain) {
        this.metadataAkipEntityDomain = metadataAkipEntityDomain;
    }
}
