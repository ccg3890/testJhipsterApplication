package com.cocoluluk.myapp.repository;

import com.cocoluluk.myapp.domain.Routine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Routine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoutineRepository extends JpaRepository<Routine, String> {}
