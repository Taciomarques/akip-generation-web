package com.akipgenerationweb.process.generationProcess.taskGenerateStartFormProcess;

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
@RequestMapping("/api/generation-process/task-generate-start-form-process")
public class TaskGenerateStartFormProcessController {

    private final Logger log = LoggerFactory.getLogger(TaskGenerateStartFormProcessController.class);

    private final TaskGenerateStartFormProcessService taskGenerateStartFormProcessService;

    public TaskGenerateStartFormProcessController(TaskGenerateStartFormProcessService taskGenerateStartFormProcessService) {
        this.taskGenerateStartFormProcessService = taskGenerateStartFormProcessService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskGenerateStartFormProcessContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task TaskGenerateStartFormProcess {}", id);
        TaskGenerateStartFormProcessContextDTO taskGenerateStartFormProcessContextDTO = taskGenerateStartFormProcessService.loadContext(id);
        return ResponseEntity.ok(taskGenerateStartFormProcessContextDTO);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskGenerateStartFormProcessContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task TaskGenerateStartFormProcess {}", id);
        TaskGenerateStartFormProcessContextDTO taskGenerateStartFormProcessContextDTO = taskGenerateStartFormProcessService.claim(id);
        return ResponseEntity.ok(taskGenerateStartFormProcessContextDTO);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskGenerateStartFormProcessContextDTO taskGenerateStartFormProcessContextDTO) {
        log.debug(
            "REST request to complete of task TaskGenerateStartFormProcess {}",
            taskGenerateStartFormProcessContextDTO.getTaskInstance().getId()
        );
        taskGenerateStartFormProcessService.complete(taskGenerateStartFormProcessContextDTO);
        return ResponseEntity.noContent().build();
    }
}
