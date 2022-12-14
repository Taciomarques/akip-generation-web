package com.akipgenerationweb.process.generationProcess.taskProvideProcessBpmn;

import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskProvideProcessBpmnContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;

    private AttachmentDTO bpmn;

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

    public AttachmentDTO getBpmn() {
        return bpmn;
    }

    public void setBpmn(AttachmentDTO bpmn) {
        this.bpmn = bpmn;
    }
}
