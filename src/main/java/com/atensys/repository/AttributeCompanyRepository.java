package com.atensys.repository;

import com.atensys.domain.AttributeCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttributeCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeCompanyRepository extends JpaRepository<AttributeCompany, Long> {}
