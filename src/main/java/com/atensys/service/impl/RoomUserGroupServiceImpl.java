package com.atensys.service.impl;

import com.atensys.domain.RoomUserGroup;
import com.atensys.repository.RoomUserGroupRepository;
import com.atensys.service.RoomUserGroupService;
import com.atensys.service.dto.RoomUserGroupDTO;
import com.atensys.service.mapper.RoomUserGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoomUserGroup}.
 */
@Service
@Transactional
public class RoomUserGroupServiceImpl implements RoomUserGroupService {

    private final Logger log = LoggerFactory.getLogger(RoomUserGroupServiceImpl.class);

    private final RoomUserGroupRepository roomUserGroupRepository;

    private final RoomUserGroupMapper roomUserGroupMapper;

    public RoomUserGroupServiceImpl(RoomUserGroupRepository roomUserGroupRepository, RoomUserGroupMapper roomUserGroupMapper) {
        this.roomUserGroupRepository = roomUserGroupRepository;
        this.roomUserGroupMapper = roomUserGroupMapper;
    }

    @Override
    public RoomUserGroupDTO save(RoomUserGroupDTO roomUserGroupDTO) {
        log.debug("Request to save RoomUserGroup : {}", roomUserGroupDTO);
        RoomUserGroup roomUserGroup = roomUserGroupMapper.toEntity(roomUserGroupDTO);
        roomUserGroup = roomUserGroupRepository.save(roomUserGroup);
        return roomUserGroupMapper.toDto(roomUserGroup);
    }

    @Override
    public RoomUserGroupDTO update(RoomUserGroupDTO roomUserGroupDTO) {
        log.debug("Request to save RoomUserGroup : {}", roomUserGroupDTO);
        RoomUserGroup roomUserGroup = roomUserGroupMapper.toEntity(roomUserGroupDTO);
        roomUserGroup = roomUserGroupRepository.save(roomUserGroup);
        return roomUserGroupMapper.toDto(roomUserGroup);
    }

    @Override
    public Optional<RoomUserGroupDTO> partialUpdate(RoomUserGroupDTO roomUserGroupDTO) {
        log.debug("Request to partially update RoomUserGroup : {}", roomUserGroupDTO);

        return roomUserGroupRepository
            .findById(roomUserGroupDTO.getId())
            .map(existingRoomUserGroup -> {
                roomUserGroupMapper.partialUpdate(existingRoomUserGroup, roomUserGroupDTO);

                return existingRoomUserGroup;
            })
            .map(roomUserGroupRepository::save)
            .map(roomUserGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomUserGroupDTO> findAll() {
        log.debug("Request to get all RoomUserGroups");
        return roomUserGroupRepository.findAll().stream().map(roomUserGroupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomUserGroupDTO> findOne(Long id) {
        log.debug("Request to get RoomUserGroup : {}", id);
        return roomUserGroupRepository.findById(id).map(roomUserGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomUserGroup : {}", id);
        roomUserGroupRepository.deleteById(id);
    }
}
