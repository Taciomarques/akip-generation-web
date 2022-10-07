package com.akipgenerationweb.process.generationProcess.taskGenerateUserTaskProcess;

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
@RequestMapping("/api/generation-process/task-generate-user-task-process")
public class TaskGenerateUserTaskProcessController {

    private final Logger log = LoggerFactory.getLogger(TaskGenerateUserTaskProcessController.class);

    private final TaskGenerateUserTaskProcessService taskGenerateUserTaskProcessService;

    public TaskGenerateUserTaskProcessController(TaskGenerateUserTaskProcessService taskGenerateUserTaskProcessService) {
        this.taskGenerateUserTaskProcessService = taskGenerateUserTaskProcessService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskGenerateUserTaskProcessContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task TaskGenerateUserTaskProcess {}", id);
        TaskGenerateUserTaskProcessContextDTO taskGenerateUserTaskProcessContextDTO = taskGenerateUserTaskProcessService.loadContext(id);
        return ResponseEntity.ok(taskGenerateUserTaskProcessContextDTO);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskGenerateUserTaskProcessContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task TaskGenerateUserTaskProcess {}", id);
        TaskGenerateUserTaskProcessContextDTO taskGenerateUserTaskProcessContextDTO = taskGenerateUserTaskProcessService.claim(id);
        return ResponseEntity.ok(taskGenerateUserTaskProcessContextDTO);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskGenerateUserTaskProcessContextDTO taskGenerateUserTaskProcessContextDTO) {
        log.debug(
            "REST request to complete of task TaskGenerateUserTaskProcess {}",
            taskGenerateUserTaskProcessContextDTO.getTaskInstance().getId()
        );
        taskGenerateUserTaskProcessService.complete(taskGenerateUserTaskProcessContextDTO);
        return ResponseEntity.noContent().build();
    }
}
