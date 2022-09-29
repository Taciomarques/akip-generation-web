package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.*;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GenerationProcess} and its DTO {@link GenerationProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, AkipProcessMapper.class })
public interface GenerationProcessMapper extends EntityMapper<GenerationProcessDTO, GenerationProcess> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "akipProcess", source = "akipProcess")
    GenerationProcessDTO toDto(GenerationProcess s);

    default String mapToString(Map<String, String> map) throws JsonProcessingException {
        if (map == null) {
            return null;
        }

        return objectMapper.writeValueAsString(map);
    }

    default Map<String, String> stringToMap(String s) throws JsonProcessingException {
        if (s == null) {
            return new HashMap<>();
        }

        return objectMapper.readValue(s, new TypeReference<Map<String, String>>() {});
    }
}
