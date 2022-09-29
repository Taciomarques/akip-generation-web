package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.*;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AkipFieldDTO;
import com.akipgenerationweb.service.dto.AkipRelationshipDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AkipEntity} and its DTO {@link AkipEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = { AkipApplicationMapper.class, AkipProcessMapper.class })
public interface AkipEntityMapper extends EntityMapper<AkipEntityDTO, AkipEntity> {
    ObjectMapper mapper = new ObjectMapper();

    @Mapping(target = "application", source = "application", qualifiedByName = "name")
    @Mapping(target = "processes", source = "processes", qualifiedByName = "name")
    AkipEntityDTO toDto(AkipEntity s);

    default List<AkipFieldDTO> stringToAkipFieldList(String fields) throws JsonProcessingException {
        if (fields != null) {
            return mapper.readValue(fields, new TypeReference<>() {});
        }
        return new ArrayList<>();
    }

    default String akipFieldlistToString(List<AkipFieldDTO> fields) throws JsonProcessingException {
        if (fields != null && !fields.isEmpty()) {
            return mapper.writeValueAsString(fields);
        }
        return null;
    }

    default List<AkipRelationshipDTO> stringToAkipRelationshipList(String fields) throws JsonProcessingException {
        if (fields != null) {
            return mapper.readValue(fields, new TypeReference<>() {});
        }
        return new ArrayList<>();
    }

    default String akipRelationshiplistToString(List<AkipRelationshipDTO> relationships) throws JsonProcessingException {
        if (relationships != null && !relationships.isEmpty()) {
            return mapper.writeValueAsString(relationships);
        }
        return null;
    }
}
