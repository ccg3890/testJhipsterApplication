package com.atensys.repository;

import com.atensys.domain.ReservedRoomSeat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReservedRoomSeat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservedRoomSeatRepository extends JpaRepository<ReservedRoomSeat, Long> {}
