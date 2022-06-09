package com.atensys.repository;

import com.atensys.domain.Attendee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Attendee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {}
