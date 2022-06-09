package com.atensys.repository;

import com.atensys.domain.RoomUserGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RoomUserGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomUserGroupRepository extends JpaRepository<RoomUserGroup, Long> {}
