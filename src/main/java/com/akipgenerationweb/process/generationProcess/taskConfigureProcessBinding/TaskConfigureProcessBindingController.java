package com.akipgenerationweb.process.generationProcess.taskConfigureProcessBinding;

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
@RequestMapping("/api/generation-process/task-configure-process-binding")
public class TaskConfigureProcessBindingController {

    private final Logger log = LoggerFactory.getLogger(TaskConfigureProcessBindingController.class);

    private final TaskConfigureProcessBindingService taskConfigureProcessBindingService;

    public TaskConfigureProcessBindingController(TaskConfigureProcessBindingService taskConfigureProcessBindingService) {
        this.taskConfigureProcessBindingService = taskConfigureProcessBindingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskConfigureProcessBindingContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task TaskConfigureProcessBinding {}", id);
        TaskConfigureProcessBindingContextDTO taskConfigureProcessBindingContext = taskConfigureProcessBindingService.loadContext(id);
        return ResponseEntity.ok(taskConfigureProcessBindingContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskConfigureProcessBindingContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task TaskConfigureProcessBinding {}", id);
        TaskConfigureProcessBindingContextDTO taskConfigureProcessBindingContext = taskConfigureProcessBindingService.claim(id);
        return ResponseEntity.ok(taskConfigureProcessBindingContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskConfigureProcessBindingContextDTO taskConfigureProcessBindingContext) {
        log.debug(
            "REST request to complete of task taskConfigureProcessBinding {}",
            taskConfigureProcessBindingContext.getTaskInstance().getId()
        );
        taskConfigureProcessBindingService.complete(taskConfigureProcessBindingContext);
        return ResponseEntity.noContent().build();
    }
}
