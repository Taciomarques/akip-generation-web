package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.*;
import com.akipgenerationweb.service.dto.AkipApplicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AkipApplication} and its DTO {@link AkipApplicationDTO}.
 */
@Mapper(componentModel = "spring", uses = { AkipEntityMapper.class, UserMapper.class })
public interface AkipApplicationMapper extends EntityMapper<AkipApplicationDTO, AkipApplication> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AkipApplicationDTO toDtoId(AkipApplication akipApplication);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AkipApplicationDTO toDtoName(AkipApplication akipApplication);
}
