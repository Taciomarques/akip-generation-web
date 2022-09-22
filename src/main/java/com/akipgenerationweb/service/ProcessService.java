package com.akipgenerationweb.service;

import com.akipgenerationweb.domain.Process;
import com.akipgenerationweb.repository.ProcessRepository;
import com.akipgenerationweb.service.dto.ProcessDTO;
import com.akipgenerationweb.service.mapper.ProcessMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Process}.
 */
@Service
@Transactional
public class ProcessService {

    private final Logger log = LoggerFactory.getLogger(ProcessService.class);

    private final ProcessRepository processRepository;

    private final ProcessMapper processMapper;

    public ProcessService(ProcessRepository processRepository, ProcessMapper processMapper) {
        this.processRepository = processRepository;
        this.processMapper = processMapper;
    }

    /**
     * Save a process.
     *
     * @param processDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessDTO save(ProcessDTO processDTO) {
        log.debug("Request to save Process : {}", processDTO);
        Process process = processMapper.toEntity(processDTO);
        process = processRepository.save(process);
        return processMapper.toDto(process);
    }

    /**
     * Partially update a process.
     *
     * @param processDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcessDTO> partialUpdate(ProcessDTO processDTO) {
        log.debug("Request to partially update Process : {}", processDTO);

        return processRepository
            .findById(processDTO.getId())
            .map(
                existingProcess -> {
                    processMapper.partialUpdate(existingProcess, processDTO);
                    return existingProcess;
                }
            )
            .map(processRepository::save)
            .map(processMapper::toDto);
    }

    /**
     * Get all the processes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessDTO> findAll() {
        log.debug("Request to get all Processes");
        return processRepository.findAll().stream().map(processMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one process by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessDTO> findOne(Long id) {
        log.debug("Request to get Process : {}", id);
        return processRepository.findById(id).map(processMapper::toDto);
    }

    /**
     * Delete the process by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Process : {}", id);
        processRepository.deleteById(id);
    }
}
