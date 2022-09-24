package com.akipgenerationweb.repository;

import com.akipgenerationweb.domain.AkipProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AkipProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkipProcessRepository extends JpaRepository<AkipProcess, Long> {}
