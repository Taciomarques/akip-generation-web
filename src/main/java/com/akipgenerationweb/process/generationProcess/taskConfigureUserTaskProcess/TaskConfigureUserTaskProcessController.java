package com.akipgenerationweb.process.generationProcess.taskConfigureUserTaskProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generation-process/task-configure-user-task-process")
public class TaskConfigureUserTaskProcessController {

    private final Logger log = LoggerFactory.getLogger(TaskConfigureUserTaskProcessController.class);

    private final TaskConfigureUserTaskProcessService taskConfigureUserTaskProcessService;

    public TaskConfigureUserTaskProcessController(TaskConfigureUserTaskProcessService taskConfigureUserTaskProcessService) {
        this.taskConfigureUserTaskProcessService = taskConfigureUserTaskProcessService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskConfigureUserTaskProcessContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task TaskConfigureUserTaskProcess {}", id);
        TaskConfigureUserTaskProcessContextDTO taskConfigureUserTaskProcessContextDTO = taskConfigureUserTaskProcessService.loadContext(id);
        return ResponseEntity.ok(taskConfigureUserTaskProcessContextDTO);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskConfigureUserTaskProcessContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task TaskConfigureUserTaskProcess {}", id);
        TaskConfigureUserTaskProcessContextDTO taskConfigureUserTaskProcessContextDTO = taskConfigureUserTaskProcessService.claim(id);
        return ResponseEntity.ok(taskConfigureUserTaskProcessContextDTO);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskConfigureUserTaskProcessContextDTO taskConfigureUserTaskProcessContextDTO) {
        log.debug(
            "REST request to complete of task TaskConfigureUserTaskProcess {}",
            taskConfigureUserTaskProcessContextDTO.getTaskInstance().getId()
        );
        taskConfigureUserTaskProcessService.complete(taskConfigureUserTaskProcessContextDTO);
        return ResponseEntity.noContent().build();
    }
}
