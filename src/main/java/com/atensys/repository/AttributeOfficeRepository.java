package com.atensys.repository;

import com.atensys.domain.AttributeOffice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttributeOffice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeOfficeRepository extends JpaRepository<AttributeOffice, Long> {}
