package com.akipgenerationweb.repository;

import com.akipgenerationweb.domain.GenerationProcess;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GenerationProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenerationProcessRepository extends JpaRepository<GenerationProcess, Long> {
    Optional<GenerationProcess> findByProcessInstanceId(Long processInstanceId);
}
