package com.atensys.repository;

import com.atensys.domain.ReservationTarget;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReservationTarget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationTargetRepository extends JpaRepository<ReservationTarget, Long> {}
