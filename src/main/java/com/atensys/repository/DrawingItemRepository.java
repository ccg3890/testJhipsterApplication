package com.atensys.repository;

import com.atensys.domain.DrawingItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DrawingItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrawingItemRepository extends JpaRepository<DrawingItem, Long> {}
