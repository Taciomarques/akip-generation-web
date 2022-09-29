package com.akipgenerationweb.process.generationProcess.taskProvideProcessBpmn;

import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.AkipProcessDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.mapper.GenerationProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskProvideProcessBpmnService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskProvideProcessBpmnMapper taskProvideProcessBpmnMapper;

    private final GenerationProcessMapper generationProcessMapper;

    public TaskProvideProcessBpmnService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskProvideProcessBpmnMapper taskProvideProcessBpmnMapper,
        GenerationProcessMapper generationProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskProvideProcessBpmnMapper = taskProvideProcessBpmnMapper;
        this.generationProcessMapper = generationProcessMapper;
    }

    public TaskProvideProcessBpmnContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskProvideProcessBpmnMapper::toGenerationProcessDTO)
            .orElseThrow();

        TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContext = new TaskProvideProcessBpmnContextDTO();
        taskProvideProcessBpmnContext.setTaskInstance(taskInstanceDTO);
        taskProvideProcessBpmnContext.setGenerationProcess(generationProcess);

        return taskProvideProcessBpmnContext;
    }

    public TaskProvideProcessBpmnContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContext) {
        AkipProcessDTO akipProcessDTO = akipProcessService
            .findOne(taskProvideProcessBpmnContext.getGenerationProcess().getAkipProcess().getId())
            .orElseThrow();
        //        processDTO.setBpmn(taskProvideProcessBpmnContext.getGenerationProcess().getProcess().getBpmn());
        akipProcessService.save(akipProcessDTO);
    }

    public void complete(TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContext) {
        save(taskProvideProcessBpmnContext);
        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskProvideProcessBpmnContext.getGenerationProcess().getProcessInstance().getId())
            .map(generationProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskProvideProcessBpmnContext.getTaskInstance(), generationProcess);
    }
}
