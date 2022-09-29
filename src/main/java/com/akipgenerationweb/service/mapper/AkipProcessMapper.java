package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.AkipApplication;
import com.akipgenerationweb.domain.AkipProcess;
import com.akipgenerationweb.service.dto.AkipApplicationDTO;
import com.akipgenerationweb.service.dto.AkipProcessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AkipProcess} and its DTO {@link AkipProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { AkipApplicationMapper.class, AkipEntityMapper.class })
public interface AkipProcessMapper extends EntityMapper<AkipProcessDTO, AkipProcess> {
    @Mapping(target = "application", source = "application", qualifiedByName = "name")
    AkipProcessDTO toDto(AkipProcess s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AkipProcessDTO toDtoId(AkipProcess process);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AkipProcessDTO toDtoName(AkipProcess process);
}
