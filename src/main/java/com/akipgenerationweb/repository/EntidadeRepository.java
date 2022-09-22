package com.akipgenerationweb.repository;

import com.akipgenerationweb.domain.Entidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Entidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadeRepository extends JpaRepository<Entidade, Long> {}
