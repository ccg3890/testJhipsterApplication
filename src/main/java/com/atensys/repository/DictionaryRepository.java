package com.atensys.repository;

import com.atensys.domain.Dictionary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dictionary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {}
