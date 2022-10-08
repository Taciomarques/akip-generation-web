package com.akipgenerationweb.process.generationProcess.taskGenerateEntities;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/api/generation-process/task-generate-entities")
public class TaskGenerateEntitiesController {

    private final Logger log = LoggerFactory.getLogger(TaskGenerateEntitiesController.class);

    private final TaskGenerateEntitiesService taskGenerateEntitiesService;

    public TaskGenerateEntitiesController(TaskGenerateEntitiesService taskGenerateEntitiesService) {
        this.taskGenerateEntitiesService = taskGenerateEntitiesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskGenerateEntitiesContextDTO> loadContext(@PathVariable Long id) throws JsonProcessingException {
        log.debug("REST request to load the context of task TaskGenerateEntities {}", id);
        TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO = taskGenerateEntitiesService.loadContext(id);
        return ResponseEntity.ok(taskGenerateEntitiesContextDTO);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskGenerateEntitiesContextDTO> claim(@PathVariable Long id) throws JsonProcessingException {
        log.debug("REST request to claim of task TaskGenerateEntities {}", id);
        TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO = taskGenerateEntitiesService.claim(id);
        return ResponseEntity.ok(taskGenerateEntitiesContextDTO);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO) {
        log.debug("REST request to complete of task TaskGenerateEntities {}", taskGenerateEntitiesContextDTO.getTaskInstance().getId());
        taskGenerateEntitiesService.complete(taskGenerateEntitiesContextDTO);
        return ResponseEntity.noContent().build();
    }
}
