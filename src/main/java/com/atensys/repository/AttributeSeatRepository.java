package com.atensys.repository;

import com.atensys.domain.AttributeSeat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttributeSeat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeSeatRepository extends JpaRepository<AttributeSeat, Long> {}
