package com.akipgenerationweb.process.generationProcess;

import com.akipgenerationweb.domain.GenerationProcess;
import com.akipgenerationweb.domain.Process;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.dto.ProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskProvideProcessBpmnMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    GenerationProcessDTO toGenerationProcessDTO(GenerationProcess generationProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    //    @Mapping(target = "bpmn", source = "bpmn")
    ProcessDTO toProcessDTO(Process process);
}
