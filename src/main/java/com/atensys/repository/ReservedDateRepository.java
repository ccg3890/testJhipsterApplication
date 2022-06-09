package com.atensys.repository;

import com.atensys.domain.ReservedDate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReservedDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservedDateRepository extends JpaRepository<ReservedDate, Long> {}
