package com.akipgenerationweb.process.generationProcess.taskConfigureUserTaskProcess;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskConfigureUserTaskProcessContextDTO {

    private GenerationProcessDTO generationProcess;
    private TaskInstanceDTO taskInstance;
    private AkipEntityDTO akipEntityUserTask;

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

    public AkipEntityDTO getAkipEntityUserTask() {
        return akipEntityUserTask;
    }

    public void setAkipEntityUserTask(AkipEntityDTO akipEntityUserTask) {
        this.akipEntityUserTask = akipEntityUserTask;
    }
}
