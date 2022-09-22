package com.akipgenerationweb.service;

import com.akipgenerationweb.domain.Entidade;
import com.akipgenerationweb.repository.EntidadeRepository;
import com.akipgenerationweb.service.dto.EntidadeDTO;
import com.akipgenerationweb.service.mapper.EntidadeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entidade}.
 */
@Service
@Transactional
public class EntidadeService {

    private final Logger log = LoggerFactory.getLogger(EntidadeService.class);

    private final EntidadeRepository entidadeRepository;

    private final EntidadeMapper entidadeMapper;

    public EntidadeService(EntidadeRepository entidadeRepository, EntidadeMapper entidadeMapper) {
        this.entidadeRepository = entidadeRepository;
        this.entidadeMapper = entidadeMapper;
    }

    /**
     * Save a entidade.
     *
     * @param entidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public EntidadeDTO save(EntidadeDTO entidadeDTO) {
        log.debug("Request to save Entidade : {}", entidadeDTO);
        Entidade entidade = entidadeMapper.toEntity(entidadeDTO);
        entidade = entidadeRepository.save(entidade);
        return entidadeMapper.toDto(entidade);
    }

    /**
     * Partially update a entidade.
     *
     * @param entidadeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntidadeDTO> partialUpdate(EntidadeDTO entidadeDTO) {
        log.debug("Request to partially update Entidade : {}", entidadeDTO);

        return entidadeRepository
            .findById(entidadeDTO.getId())
            .map(
                existingEntidade -> {
                    entidadeMapper.partialUpdate(existingEntidade, entidadeDTO);
                    return existingEntidade;
                }
            )
            .map(entidadeRepository::save)
            .map(entidadeMapper::toDto);
    }

    /**
     * Get all the entidades.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EntidadeDTO> findAll() {
        log.debug("Request to get all Entidades");
        return entidadeRepository.findAll().stream().map(entidadeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one entidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntidadeDTO> findOne(Long id) {
        log.debug("Request to get Entidade : {}", id);
        return entidadeRepository.findById(id).map(entidadeMapper::toDto);
    }

    /**
     * Delete the entidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entidade : {}", id);
        entidadeRepository.deleteById(id);
    }
}
