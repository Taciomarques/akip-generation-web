package com.akipgenerationweb.repository;

import com.akipgenerationweb.domain.AkipEntity;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AkipEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkipEntityRepository extends JpaRepository<AkipEntity, Long> {
    List<AkipEntity> findAkipEntitiesByApplication_Id(Long applicationId);
}
