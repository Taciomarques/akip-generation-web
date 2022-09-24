package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.*;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GenerationProcess} and its DTO {@link GenerationProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, AkipProcessMapper.class })
public interface GenerationProcessMapper extends EntityMapper<GenerationProcessDTO, GenerationProcess> {
    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "akipProcess", source = "akipProcess")
    GenerationProcessDTO toDto(GenerationProcess s);
}
