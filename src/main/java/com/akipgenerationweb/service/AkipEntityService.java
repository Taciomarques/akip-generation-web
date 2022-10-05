package com.akipgenerationweb.service;

import com.akipgenerationweb.domain.AkipEntity;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.AkipEntityRepository;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.mapper.AkipEntityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AkipEntity}.
 */
@Service
@Transactional
public class AkipEntityService {

    private final Logger log = LoggerFactory.getLogger(AkipEntityService.class);

    private final AkipEntityRepository akipEntityRepository;

    private final AkipEntityMapper akipEntityMapper;

    public AkipEntityService(AkipEntityRepository akipEntityRepository, AkipEntityMapper akipEntityMapper) {
        this.akipEntityRepository = akipEntityRepository;
        this.akipEntityMapper = akipEntityMapper;
    }

    /**
     * Save a AkipEntity.
     *
     * @param akipEntityDTO the entity to save.
     * @return the persisted entity.
     */
    public AkipEntityDTO save(AkipEntityDTO akipEntityDTO) {
        log.debug("Request to save AkipEntity : {}", akipEntityDTO);
        AkipEntity akipEntity = akipEntityMapper.toEntity(akipEntityDTO);
        akipEntity = akipEntityRepository.save(akipEntity);
        return akipEntityMapper.toDto(akipEntity);
    }

    /**
     * Partially update a akipEntiy.
     *
     * @param akipEntityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AkipEntityDTO> partialUpdate(AkipEntityDTO akipEntityDTO) {
        log.debug("Request to partially update AkipEntity : {}", akipEntityDTO);

        return akipEntityRepository
            .findById(akipEntityDTO.getId())
            .map(
                existingAkipEntity -> {
                    akipEntityMapper.partialUpdate(existingAkipEntity, akipEntityDTO);
                    return existingAkipEntity;
                }
            )
            .map(akipEntityRepository::save)
            .map(akipEntityMapper::toDto);
    }

    /**
     * Get all the akipEntities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AkipEntityDTO> findAll() {
        log.debug("Request to get all AkipEntities");
        return akipEntityRepository.findAll().stream().map(akipEntityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the akipEntities by applicationId.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AkipEntityDTO> findByApplicationIdAndTypeEntity(Long applicationId, TypeEntity typeEntity) {
        log.debug("Request to get all AkipEntities by applicationId {} and TypeEntity {} ", applicationId, typeEntity);
        return akipEntityRepository
            .findAkipEntitiesByApplication_IdAndType(applicationId, typeEntity)
            .stream()
            .map(akipEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one akipEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AkipEntityDTO> findOne(Long id) {
        log.debug("Request to get AKipEntity : {}", id);
        return akipEntityRepository.findById(id).map(akipEntityMapper::toDto);
    }

    /**
     * Delete the akipEntity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AkipEntity : {}", id);
        akipEntityRepository.deleteById(id);
    }
}
