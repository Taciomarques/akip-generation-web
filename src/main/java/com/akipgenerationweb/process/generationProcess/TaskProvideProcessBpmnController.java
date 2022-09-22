package com.akipgenerationweb.process.generationProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generation-process/task-provide-process-bpmn")
public class TaskProvideProcessBpmnController {

    private final Logger log = LoggerFactory.getLogger(TaskProvideProcessBpmnController.class);

    private final TaskProvideProcessBpmnService taskProvideProcessBpmnService;

    public TaskProvideProcessBpmnController(TaskProvideProcessBpmnService taskProvideProcessBpmnService) {
        this.taskProvideProcessBpmnService = taskProvideProcessBpmnService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskProvideProcessBpmnContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContext = taskProvideProcessBpmnService.loadContext(id);
        return ResponseEntity.ok(taskProvideProcessBpmnContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskProvideProcessBpmnContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContext = taskProvideProcessBpmnService.claim(id);
        return ResponseEntity.ok(taskProvideProcessBpmnContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContext) {
        log.debug(
            "REST request to complete GenerationProcess.TaskProvideProcessBpmn {}",
            taskProvideProcessBpmnContext.getTaskInstance().getId()
        );
        taskProvideProcessBpmnService.complete(taskProvideProcessBpmnContext);
        return ResponseEntity.noContent().build();
    }
}
