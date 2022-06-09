package com.atensys.repository;

import com.atensys.domain.AttributeRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttributeRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeRoomRepository extends JpaRepository<AttributeRoom, Long> {}
