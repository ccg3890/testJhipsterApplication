package com.atensys.repository;

import com.atensys.domain.Penalty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Penalty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {}
