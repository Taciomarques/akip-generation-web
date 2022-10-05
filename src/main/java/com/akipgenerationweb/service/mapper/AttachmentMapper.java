package com.akipgenerationweb.service.mapper;

import com.akipgenerationweb.domain.Attachment;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {}
