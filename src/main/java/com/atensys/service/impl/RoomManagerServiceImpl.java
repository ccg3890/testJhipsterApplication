package com.atensys.service.impl;

import com.atensys.domain.RoomManager;
import com.atensys.repository.RoomManagerRepository;
import com.atensys.service.RoomManagerService;
import com.atensys.service.dto.RoomManagerDTO;
import com.atensys.service.mapper.RoomManagerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoomManager}.
 */
@Service
@Transactional
public class RoomManagerServiceImpl implements RoomManagerService {

    private final Logger log = LoggerFactory.getLogger(RoomManagerServiceImpl.class);

    private final RoomManagerRepository roomManagerRepository;

    private final RoomManagerMapper roomManagerMapper;

    public RoomManagerServiceImpl(RoomManagerRepository roomManagerRepository, RoomManagerMapper roomManagerMapper) {
        this.roomManagerRepository = roomManagerRepository;
        this.roomManagerMapper = roomManagerMapper;
    }

    @Override
    public RoomManagerDTO save(RoomManagerDTO roomManagerDTO) {
        log.debug("Request to save RoomManager : {}", roomManagerDTO);
        RoomManager roomManager = roomManagerMapper.toEntity(roomManagerDTO);
        roomManager = roomManagerRepository.save(roomManager);
        return roomManagerMapper.toDto(roomManager);
    }

    @Override
    public RoomManagerDTO update(RoomManagerDTO roomManagerDTO) {
        log.debug("Request to save RoomManager : {}", roomManagerDTO);
        RoomManager roomManager = roomManagerMapper.toEntity(roomManagerDTO);
        roomManager = roomManagerRepository.save(roomManager);
        return roomManagerMapper.toDto(roomManager);
    }

    @Override
    public Optional<RoomManagerDTO> partialUpdate(RoomManagerDTO roomManagerDTO) {
        log.debug("Request to partially update RoomManager : {}", roomManagerDTO);

        return roomManagerRepository
            .findById(roomManagerDTO.getId())
            .map(existingRoomManager -> {
                roomManagerMapper.partialUpdate(existingRoomManager, roomManagerDTO);

                return existingRoomManager;
            })
            .map(roomManagerRepository::save)
            .map(roomManagerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomManagerDTO> findAll() {
        log.debug("Request to get all RoomManagers");
        return roomManagerRepository.findAll().stream().map(roomManagerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomManagerDTO> findOne(Long id) {
        log.debug("Request to get RoomManager : {}", id);
        return roomManagerRepository.findById(id).map(roomManagerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomManager : {}", id);
        roomManagerRepository.deleteById(id);
    }
}
