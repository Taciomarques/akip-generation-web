package com.akipgenerationweb.service;

import com.akipgenerationweb.domain.AkipApplication;
import com.akipgenerationweb.repository.AkipApplicationRepository;
import com.akipgenerationweb.security.SecurityUtils;
import com.akipgenerationweb.service.dto.AkipApplicationDTO;
import com.akipgenerationweb.service.mapper.AkipApplicationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AkipApplication}.
 */
@Service
@Transactional
public class AkipApplicationService {

    private final Logger log = LoggerFactory.getLogger(AkipApplicationService.class);

    private final AkipApplicationRepository akipApplicationRepository;

    private final AkipApplicationMapper akipApplicationMapper;

    private final UserService userService;

    public AkipApplicationService(
        AkipApplicationRepository akipApplicationRepository,
        AkipApplicationMapper akipApplicationMapper,
        UserService userService
    ) {
        this.akipApplicationRepository = akipApplicationRepository;
        this.akipApplicationMapper = akipApplicationMapper;
        this.userService = userService;
    }

    /**
     * Save a akipApplication.
     *
     * @param akipApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    public AkipApplicationDTO save(AkipApplicationDTO akipApplicationDTO) {
        log.debug("Request to save AkipApplication : {}", akipApplicationDTO);
        AkipApplication akipApplication = akipApplicationMapper.toEntity(akipApplicationDTO);
        akipApplication.setOwner(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
        akipApplication = akipApplicationRepository.save(akipApplication);
        return akipApplicationMapper.toDto(akipApplication);
    }

    /**
     * Partially update a akipApplication.
     *
     * @param akipApplicationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AkipApplicationDTO> partialUpdate(AkipApplicationDTO akipApplicationDTO) {
        log.debug("Request to partially update AkipApplication : {}", akipApplicationDTO);

        return akipApplicationRepository
            .findById(akipApplicationDTO.getId())
            .map(
                existingApplication -> {
                    akipApplicationMapper.partialUpdate(existingApplication, akipApplicationDTO);
                    return existingApplication;
                }
            )
            .map(akipApplicationRepository::save)
            .map(akipApplicationMapper::toDto);
    }

    /**
     * Get all the akipApplications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AkipApplicationDTO> findAll() {
        log.debug("Request to get all AkipApplications");
        return akipApplicationRepository
            .findAll()
            .stream()
            .filter(
                akipApplication ->
                    akipApplication
                        .getOwner()
                        .getId()
                        .equals(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId())
            )
            .map(akipApplicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one akipApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AkipApplicationDTO> findOne(Long id) {
        log.debug("Request to get AkipApplication : {}", id);
        return akipApplicationRepository.findById(id).map(akipApplicationMapper::toDto);
    }

    /**
     * Delete the akipApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AkipApplication : {}", id);
        akipApplicationRepository.deleteById(id);
    }
}
