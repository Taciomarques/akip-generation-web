package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.*;
import com.akipgenerationweb.service.dto.EntidadeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entidade} and its DTO {@link EntidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessMapper.class, ApplicationMapper.class })
public interface EntidadeMapper extends EntityMapper<EntidadeDTO, Entidade> {
    @Mapping(target = "process", source = "process", qualifiedByName = "id")
    @Mapping(target = "application", source = "application", qualifiedByName = "id")
    EntidadeDTO toDto(Entidade s);
}
