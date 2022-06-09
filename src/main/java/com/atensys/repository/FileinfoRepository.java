package com.atensys.repository;

import com.atensys.domain.Fileinfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Fileinfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileinfoRepository extends JpaRepository<Fileinfo, Long> {}
