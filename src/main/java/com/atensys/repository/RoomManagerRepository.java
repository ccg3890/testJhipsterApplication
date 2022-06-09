package com.atensys.repository;

import com.atensys.domain.RoomManager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoomManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomManagerRepository extends JpaRepository<RoomManager, Long> {}
