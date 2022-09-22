package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.*;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GenerationProcess} and its DTO {@link GenerationProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, ProcessMapper.class })
public interface GenerationProcessMapper extends EntityMapper<GenerationProcessDTO, GenerationProcess> {
    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "process", source = "process")
    GenerationProcessDTO toDto(GenerationProcess s);
}
