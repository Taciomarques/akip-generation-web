package com.akipgenerationweb.process.generationProcess.taskGenerateDomainEntity;

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
@RequestMapping("/api/generation-process/task-generate-domain-entity")
public class TaskGenerateDomainEntityController {

    private final Logger log = LoggerFactory.getLogger(TaskGenerateDomainEntityController.class);

    private final TaskGenerateDomainEntityService taskGenerateDomainEntityService;

    public TaskGenerateDomainEntityController(TaskGenerateDomainEntityService taskGenerateDomainEntityService) {
        this.taskGenerateDomainEntityService = taskGenerateDomainEntityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskGenerateDomainEntityContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task taskGenerateDomainEntity {}", id);
        TaskGenerateDomainEntityContextDTO taskGenerateDomainEntityContext = taskGenerateDomainEntityService.loadContext(id);
        return ResponseEntity.ok(taskGenerateDomainEntityContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskGenerateDomainEntityContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task taskGenerateDomainEntity {}", id);
        TaskGenerateDomainEntityContextDTO taskGenerateDomainEntityContext = taskGenerateDomainEntityService.claim(id);
        return ResponseEntity.ok(taskGenerateDomainEntityContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskGenerateDomainEntityContextDTO taskGenerateDomainEntityContext) {
        log.debug(
            "REST request to complete of task taskGenerateDomainEntity {}",
            taskGenerateDomainEntityContext.getTaskInstance().getId()
        );
        taskGenerateDomainEntityService.complete(taskGenerateDomainEntityContext);
        return ResponseEntity.noContent().build();
    }
}
