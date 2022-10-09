package com.akipgenerationweb.process.generationProcess.taskProvideProcessBpmn;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.mapper.GenerationProcessMapper;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TaskProvideProcessBpmnService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskProvideProcessBpmnMapper taskProvideProcessBpmnMapper;

    private final GenerationProcessMapper generationProcessMapper;

    private final RuntimeService runtimeService;

    private static final String USER_TASKS = "userTasks";
    private static final String SERVICE_TASKS = "serviceTasks";
    private static final String ID = "id";

    public TaskProvideProcessBpmnService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskProvideProcessBpmnMapper taskProvideProcessBpmnMapper,
        GenerationProcessMapper generationProcessMapper,
        RuntimeService runtimeService
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskProvideProcessBpmnMapper = taskProvideProcessBpmnMapper;
        this.generationProcessMapper = generationProcessMapper;
        this.runtimeService = runtimeService;
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
        taskProvideProcessBpmnContext.getBpmn().setProcessId(taskProvideProcessBpmnContext.getGenerationProcess().getAkipProcess().getId());

        taskProvideProcessBpmnContext.getGenerationProcess().getAkipProcess().getAttachments().add(taskProvideProcessBpmnContext.getBpmn());

        akipProcessService.save(taskProvideProcessBpmnContext.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContext) {
        setProcessBpmnId(taskProvideProcessBpmnContext);
        readUserTasksAndServiceTasksForBpmn(taskProvideProcessBpmnContext);
        save(taskProvideProcessBpmnContext);
        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskProvideProcessBpmnContext.getGenerationProcess().getProcessInstance().getId())
            .map(generationProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskProvideProcessBpmnContext.getTaskInstance(), generationProcess);
    }

    private void setProcessBpmnId(TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContextDTO) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(
            new ByteArrayInputStream(taskProvideProcessBpmnContextDTO.getBpmn().getSpecificationFile())
        );
        ModelElementType processType = bpmnModelInstance.getModel().getType(Process.class);
        Process process = (Process) bpmnModelInstance.getModelElementsByType(processType).iterator().next();
        taskProvideProcessBpmnContextDTO.getGenerationProcess().getAkipProcess().setProcessBpmnId(process.getId());
    }

    private void readUserTasksAndServiceTasksForBpmn(TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContextDTO) {
        List<AkipEntityDTO> akipEntitiesUserTask = createAkipEntitiesByTypeEntity(TypeEntity.USER_TASK, taskProvideProcessBpmnContextDTO);

        List<AkipEntityDTO> akipEntitiesServiceTask = createAkipEntitiesByTypeEntity(
            TypeEntity.SERVICE_TASK,
            taskProvideProcessBpmnContextDTO
        );

        setVariableInCamunda(
            taskProvideProcessBpmnContextDTO.getGenerationProcess().getProcessInstance().getCamundaProcessInstanceId(),
            USER_TASKS,
            akipEntitiesUserTask
        );
        setVariableInCamunda(
            taskProvideProcessBpmnContextDTO.getGenerationProcess().getProcessInstance().getCamundaProcessInstanceId(),
            SERVICE_TASKS,
            akipEntitiesServiceTask
        );
    }

    private List<AkipEntityDTO> createAkipEntitiesByTypeEntity(
        TypeEntity typeEntity,
        TaskProvideProcessBpmnContextDTO taskProvideProcessBpmnContextDTO
    ) {
        Collection<ModelElementInstance> taskInstances = createTaskInstancesForBPMN(taskProvideProcessBpmnContextDTO.getBpmn(), typeEntity);

        List<AkipEntityDTO> akipEntities = new ArrayList<>();

        for (ModelElementInstance task : taskInstances) {
            AkipEntityDTO akipEntity = new AkipEntityDTO();
            akipEntity.setName(task.getAttributeValue(ID));
            akipEntity.setType(typeEntity);
            akipEntity.setApplication(taskProvideProcessBpmnContextDTO.getGenerationProcess().getAkipProcess().getApplication());
            akipEntities.add(akipEntity);
        }

        return akipEntities;
    }

    private Collection<ModelElementInstance> createTaskInstancesForBPMN(AttachmentDTO bpmn, TypeEntity typeEntity) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(bpmn.getSpecificationFile()));

        ModelElementType taskType = bpmnModelInstance
            .getModel()
            .getType(typeEntity == TypeEntity.USER_TASK ? UserTask.class : ServiceTask.class);
        return bpmnModelInstance.getModelElementsByType(taskType);
    }

    private void setVariableInCamunda(String camundaProcessInstanceId, String variableName, List<AkipEntityDTO> variable) {
        runtimeService.setVariable(camundaProcessInstanceId, variableName, variable);
    }
}
