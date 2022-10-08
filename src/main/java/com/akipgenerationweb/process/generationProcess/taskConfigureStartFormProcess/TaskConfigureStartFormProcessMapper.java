package com.akipgenerationweb.process.generationProcess.taskConfigureStartFormProcess;

import com.akipgenerationweb.domain.GenerationProcess;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.mapper.AkipProcessMapper;
import com.akipgenerationweb.service.mapper.GenerationProcessMapper;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, AkipProcessMapper.class, GenerationProcessMapper.class })
public interface TaskConfigureStartFormProcessMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    GenerationProcessDTO toGenerationProcessDTO(GenerationProcess generationProcess);
}
