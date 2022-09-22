package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.Process;
import com.akipgenerationweb.service.dto.ProcessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Process} and its DTO {@link ProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ApplicationMapper.class })
public interface ProcessMapper extends EntityMapper<ProcessDTO, Process> {
    @Mapping(target = "application", source = "application", qualifiedByName = "id")
    ProcessDTO toDto(Process s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessDTO toDtoId(Process process);
}
