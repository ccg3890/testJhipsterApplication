package com.cocoluluk.myapp.repository;

import com.cocoluluk.myapp.domain.RoutineMain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoutineMain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoutineMainRepository extends JpaRepository<RoutineMain, String> {}
