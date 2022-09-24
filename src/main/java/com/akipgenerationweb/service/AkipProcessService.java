package com.akipgenerationweb.service;

import com.akipgenerationweb.domain.AkipProcess;
import com.akipgenerationweb.repository.AkipProcessRepository;
import com.akipgenerationweb.service.dto.AkipProcessDTO;
import com.akipgenerationweb.service.mapper.AkipProcessMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AkipProcess}.
 */
@Service
@Transactional
public class AkipProcessService {

    private final Logger log = LoggerFactory.getLogger(AkipProcessService.class);

    private final AkipProcessRepository akipProcessRepository;

    private final AkipProcessMapper akipProcessMapper;

    public AkipProcessService(AkipProcessRepository akipProcessRepository, AkipProcessMapper akipProcessMapper) {
        this.akipProcessRepository = akipProcessRepository;
        this.akipProcessMapper = akipProcessMapper;
    }

    /**
     * Save a akipProcess.
     *
     * @param akipProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public AkipProcessDTO save(AkipProcessDTO akipProcessDTO) {
        log.debug("Request to save AkipProcess : {}", akipProcessDTO);
        AkipProcess akipProcess = akipProcessMapper.toEntity(akipProcessDTO);
        akipProcess = akipProcessRepository.save(akipProcess);
        return akipProcessMapper.toDto(akipProcess);
    }

    /**
     * Partially update a akipProcess.
     *
     * @param akipProcessDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AkipProcessDTO> partialUpdate(AkipProcessDTO akipProcessDTO) {
        log.debug("Request to partially update AkipProcess : {}", akipProcessDTO);

        return akipProcessRepository
            .findById(akipProcessDTO.getId())
            .map(
                existingAkipProcess -> {
                    akipProcessMapper.partialUpdate(existingAkipProcess, akipProcessDTO);
                    return existingAkipProcess;
                }
            )
            .map(akipProcessRepository::save)
            .map(akipProcessMapper::toDto);
    }

    /**
     * Get all the akipProcesses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AkipProcessDTO> findAll() {
        log.debug("Request to get all AkipProcess");
        return akipProcessRepository.findAll().stream().map(akipProcessMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one akipProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AkipProcessDTO> findOne(Long id) {
        log.debug("Request to get AkipProcess : {}", id);
        return akipProcessRepository.findById(id).map(akipProcessMapper::toDto);
    }

    /**
     * Delete the akipProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AkipProcess : {}", id);
        akipProcessRepository.deleteById(id);
    }
}
