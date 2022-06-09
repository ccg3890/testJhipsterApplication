package com.atensys.repository;

import com.atensys.domain.Recurrence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Recurrence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecurrenceRepository extends JpaRepository<Recurrence, Long> {}
