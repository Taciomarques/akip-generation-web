package com.akipgenerationweb.process.generationProcess.taskGenerateEntities;

import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import java.util.List;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskGenerateEntitiesContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private List<AttachmentDTO> metadatasAkipEntitiesDomain;
    private AttachmentDTO metadataAkipEntityProcessBinding;
    private AttachmentDTO metadataAkipEntityStartForm;
    private List<AttachmentDTO> metadatasAkipEntitiesUserTasks;
    private List<AttachmentDTO> metadatasAkipEntitiesServiceTasks;

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

    public List<AttachmentDTO> getMetadatasAkipEntitiesDomain() {
        return metadatasAkipEntitiesDomain;
    }

    public void setMetadatasAkipEntitiesDomain(List<AttachmentDTO> metadatasAkipEntitiesDomain) {
        this.metadatasAkipEntitiesDomain = metadatasAkipEntitiesDomain;
    }

    public AttachmentDTO getMetadataAkipEntityProcessBinding() {
        return metadataAkipEntityProcessBinding;
    }

    public void setMetadataAkipEntityProcessBinding(AttachmentDTO metadataAkipEntityProcessBinding) {
        this.metadataAkipEntityProcessBinding = metadataAkipEntityProcessBinding;
    }

    public AttachmentDTO getMetadataAkipEntityStartForm() {
        return metadataAkipEntityStartForm;
    }

    public void setMetadataAkipEntityStartForm(AttachmentDTO metadataAkipEntityStartForm) {
        this.metadataAkipEntityStartForm = metadataAkipEntityStartForm;
    }

    public List<AttachmentDTO> getMetadatasAkipEntitiesUserTasks() {
        return metadatasAkipEntitiesUserTasks;
    }

    public void setMetadatasAkipEntitiesUserTasks(List<AttachmentDTO> metadatasAkipEntitiesUserTasks) {
        this.metadatasAkipEntitiesUserTasks = metadatasAkipEntitiesUserTasks;
    }

    public List<AttachmentDTO> getMetadatasAkipEntitiesServiceTasks() {
        return metadatasAkipEntitiesServiceTasks;
    }

    public void setMetadatasAkipEntitiesServiceTasks(List<AttachmentDTO> metadatasAkipEntitiesServiceTasks) {
        this.metadatasAkipEntitiesServiceTasks = metadatasAkipEntitiesServiceTasks;
    }
}
