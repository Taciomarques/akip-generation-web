package com.akipgenerationweb.repository;

import com.akipgenerationweb.domain.AkipApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AkipApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkipApplicationRepository extends JpaRepository<AkipApplication, Long> {}
