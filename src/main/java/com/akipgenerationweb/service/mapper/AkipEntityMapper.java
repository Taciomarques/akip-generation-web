package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.*;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AkipEntity} and its DTO {@link AkipEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = { AkipApplicationMapper.class })
public interface AkipEntityMapper extends EntityMapper<AkipEntityDTO, AkipEntity> {
    @Mapping(target = "application", source = "application", qualifiedByName = "id")
    AkipEntityDTO toDto(AkipEntity s);
}
