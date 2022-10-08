package com.akipgenerationweb.process.generationProcess.taskConfigureStartFormProcess;

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
@RequestMapping("/api/generation-process/task-configure-start-form-process")
public class TaskConfigureStartFormProcessController {

    private final Logger log = LoggerFactory.getLogger(TaskConfigureStartFormProcessController.class);

    private final TaskConfigureStartFormProcessService taskConfigureStartFormProcessService;

    public TaskConfigureStartFormProcessController(TaskConfigureStartFormProcessService taskConfigureStartFormProcessService) {
        this.taskConfigureStartFormProcessService = taskConfigureStartFormProcessService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskConfigureStartFormProcessContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task TaskConfigureStartFormProcess {}", id);
        TaskConfigureStartFormProcessContextDTO taskConfigureStartFormProcessContextDTO = taskConfigureStartFormProcessService.loadContext(
            id
        );
        return ResponseEntity.ok(taskConfigureStartFormProcessContextDTO);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskConfigureStartFormProcessContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task TaskConfigureStartFormProcess {}", id);
        TaskConfigureStartFormProcessContextDTO taskConfigureStartFormProcessContextDTO = taskConfigureStartFormProcessService.claim(id);
        return ResponseEntity.ok(taskConfigureStartFormProcessContextDTO);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskConfigureStartFormProcessContextDTO taskConfigureStartFormProcessContextDTO) {
        log.debug(
            "REST request to complete of task TaskConfigureStartFormProcess {}",
            taskConfigureStartFormProcessContextDTO.getTaskInstance().getId()
        );
        taskConfigureStartFormProcessService.complete(taskConfigureStartFormProcessContextDTO);
        return ResponseEntity.noContent().build();
    }
}
