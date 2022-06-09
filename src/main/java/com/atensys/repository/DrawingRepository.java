package com.atensys.repository;

import com.atensys.domain.Drawing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Drawing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrawingRepository extends JpaRepository<Drawing, Long> {}
