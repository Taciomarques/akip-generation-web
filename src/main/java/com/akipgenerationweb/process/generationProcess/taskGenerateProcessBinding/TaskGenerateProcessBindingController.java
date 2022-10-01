package com.akipgenerationweb.process.generationProcess.taskGenerateProcessBinding;

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
@RequestMapping("/api/generation-process/task-generate-process-binding")
public class TaskGenerateProcessBindingController {

    private final Logger log = LoggerFactory.getLogger(TaskGenerateProcessBindingController.class);

    private final TaskGenerateProcessBindingService taskGenerateProcessBindingService;

    public TaskGenerateProcessBindingController(TaskGenerateProcessBindingService taskGenerateProcessBindingService) {
        this.taskGenerateProcessBindingService = taskGenerateProcessBindingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskGenerateProcessBindingContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task TaskGenerateProcessBinding {}", id);
        TaskGenerateProcessBindingContextDTO taskGenerateProcessBindingContext = taskGenerateProcessBindingService.loadContext(id);
        return ResponseEntity.ok(taskGenerateProcessBindingContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskGenerateProcessBindingContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task TaskGenerateProcessBinding {}", id);
        TaskGenerateProcessBindingContextDTO taskGenerateProcessBindingContext = taskGenerateProcessBindingService.claim(id);
        return ResponseEntity.ok(taskGenerateProcessBindingContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskGenerateProcessBindingContextDTO taskGenerateProcessBindingContext) {
        log.debug(
            "REST request to complete of task taskGenerateProcessBinding {}",
            taskGenerateProcessBindingContext.getTaskInstance().getId()
        );
        taskGenerateProcessBindingService.complete(taskGenerateProcessBindingContext);
        return ResponseEntity.noContent().build();
    }
}
