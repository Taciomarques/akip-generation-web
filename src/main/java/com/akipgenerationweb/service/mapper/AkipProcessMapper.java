package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.AkipProcess;
import com.akipgenerationweb.service.dto.AkipProcessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AkipProcess} and its DTO {@link AkipProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { AkipApplicationMapper.class })
public interface AkipProcessMapper extends EntityMapper<AkipProcessDTO, AkipProcess> {
    @Mapping(target = "application", source = "application", qualifiedByName = "id")
    AkipProcessDTO toDto(AkipProcess s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AkipProcessDTO toDtoId(AkipProcess process);
}
