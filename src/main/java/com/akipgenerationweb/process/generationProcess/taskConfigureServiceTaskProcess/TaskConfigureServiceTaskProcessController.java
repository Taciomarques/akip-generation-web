package com.akipgenerationweb.process.generationProcess.taskConfigureServiceTaskProcess;

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
@RequestMapping("/api/generation-process/task-configure-service-task-process")
public class TaskConfigureServiceTaskProcessController {

    private final Logger log = LoggerFactory.getLogger(TaskConfigureServiceTaskProcessController.class);

    private final TaskConfigureServiceTaskProcessService taskConfigureServiceTaskProcessService;

    public TaskConfigureServiceTaskProcessController(TaskConfigureServiceTaskProcessService taskConfigureServiceTaskProcessService) {
        this.taskConfigureServiceTaskProcessService = taskConfigureServiceTaskProcessService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskConfigureServiceTaskProcessContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task TaskConfigureServiceTaskProcess {}", id);
        TaskConfigureServiceTaskProcessContextDTO taskConfigureServiceTaskProcessContextDTO = taskConfigureServiceTaskProcessService.loadContext(
            id
        );
        return ResponseEntity.ok(taskConfigureServiceTaskProcessContextDTO);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskConfigureServiceTaskProcessContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task TaskConfigureServiceTaskProcess {}", id);
        TaskConfigureServiceTaskProcessContextDTO taskConfigureServiceTaskProcessContextDTO = taskConfigureServiceTaskProcessService.claim(
            id
        );
        return ResponseEntity.ok(taskConfigureServiceTaskProcessContextDTO);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskConfigureServiceTaskProcessContextDTO taskConfigureServiceTaskProcessContextDTO) {
        log.debug(
            "REST request to complete of task TaskConfigureServiceTaskProcess {}",
            taskConfigureServiceTaskProcessContextDTO.getTaskInstance().getId()
        );
        taskConfigureServiceTaskProcessService.complete(taskConfigureServiceTaskProcessContextDTO);
        return ResponseEntity.noContent().build();
    }
}
