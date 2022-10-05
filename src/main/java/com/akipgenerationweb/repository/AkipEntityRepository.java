package com.akipgenerationweb.repository;

import com.akipgenerationweb.domain.AkipEntity;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AkipEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkipEntityRepository extends JpaRepository<AkipEntity, Long> {
    List<AkipEntity> findAkipEntitiesByApplication_IdAndType(Long applicationId, TypeEntity typeEntity);
}
