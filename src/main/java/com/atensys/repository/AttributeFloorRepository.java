package com.atensys.repository;

import com.atensys.domain.AttributeFloor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttributeFloor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeFloorRepository extends JpaRepository<AttributeFloor, Long> {}
