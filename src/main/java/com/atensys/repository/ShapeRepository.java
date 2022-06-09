package com.atensys.repository;

import com.atensys.domain.Shape;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Shape entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {}
