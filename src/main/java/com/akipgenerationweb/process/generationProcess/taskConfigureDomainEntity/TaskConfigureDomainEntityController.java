package com.akipgenerationweb.process.generationProcess.taskConfigureDomainEntity;

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
@RequestMapping("/api/generation-process/task-configure-domain-entity")
public class TaskConfigureDomainEntityController {

    private final Logger log = LoggerFactory.getLogger(TaskConfigureDomainEntityController.class);

    private final TaskConfigureDomainEntityService taskConfigureDomainEntityService;

    public TaskConfigureDomainEntityController(TaskConfigureDomainEntityService taskConfigureDomainEntityService) {
        this.taskConfigureDomainEntityService = taskConfigureDomainEntityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskConfigureDomainEntityContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task taskConfigureDomainEntity {}", id);
        TaskConfigureDomainEntityContextDTO taskConfigureDomainEntityContext = taskConfigureDomainEntityService.loadContext(id);
        return ResponseEntity.ok(taskConfigureDomainEntityContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskConfigureDomainEntityContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to claim of task taskConfigureDomainEntity {}", id);
        TaskConfigureDomainEntityContextDTO taskConfigureDomainEntityContext = taskConfigureDomainEntityService.claim(id);
        return ResponseEntity.ok(taskConfigureDomainEntityContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskConfigureDomainEntityContextDTO taskConfigureDomainEntityContext) {
        log.debug(
            "REST request to complete of task taskConfigureDomainEntity {}",
            taskConfigureDomainEntityContext.getTaskInstance().getId()
        );
        taskConfigureDomainEntityService.complete(taskConfigureDomainEntityContext);
        return ResponseEntity.noContent().build();
    }
}
