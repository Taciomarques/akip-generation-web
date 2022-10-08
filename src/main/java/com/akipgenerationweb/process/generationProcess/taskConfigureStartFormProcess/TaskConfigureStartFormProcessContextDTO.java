package com.akipgenerationweb.process.generationProcess.taskConfigureStartFormProcess;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskConfigureStartFormProcessContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AkipEntityDTO akipEntityStartForm;

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

    public AkipEntityDTO getAkipEntityStartForm() {
        return akipEntityStartForm;
    }

    public void setAkipEntityStartForm(AkipEntityDTO akipEntityStartForm) {
        this.akipEntityStartForm = akipEntityStartForm;
    }
}
