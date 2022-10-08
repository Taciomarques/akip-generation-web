package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.Attachment;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { AkipProcessMapper.class })
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(source = "process.id", target = "processId")
    AttachmentDTO toDto(Attachment attachment);

    @Mapping(source = "processId", target = "process.id")
    Attachment toEntity(AttachmentDTO attachmentDTO);
}
