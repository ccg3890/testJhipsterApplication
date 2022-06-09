package com.atensys.repository;

import com.atensys.domain.RoomSeat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoomSeat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomSeatRepository extends JpaRepository<RoomSeat, Long> {}
