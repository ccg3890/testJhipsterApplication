package com.atensys.repository;

import com.atensys.domain.ShapeAsset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ShapeAsset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShapeAssetRepository extends JpaRepository<ShapeAsset, Long> {}
