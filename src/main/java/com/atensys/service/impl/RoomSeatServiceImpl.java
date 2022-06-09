package com.atensys.service.impl;

import com.atensys.domain.RoomSeat;
import com.atensys.repository.RoomSeatRepository;
import com.atensys.service.RoomSeatService;
import com.atensys.service.dto.RoomSeatDTO;
import com.atensys.service.mapper.RoomSeatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoomSeat}.
 */
@Service
@Transactional
public class RoomSeatServiceImpl implements RoomSeatService {

    private final Logger log = LoggerFactory.getLogger(RoomSeatServiceImpl.class);

    private final RoomSeatRepository roomSeatRepository;

    private final RoomSeatMapper roomSeatMapper;

    public RoomSeatServiceImpl(RoomSeatRepository roomSeatRepository, RoomSeatMapper roomSeatMapper) {
        this.roomSeatRepository = roomSeatRepository;
        this.roomSeatMapper = roomSeatMapper;
    }

    @Override
    public RoomSeatDTO save(RoomSeatDTO roomSeatDTO) {
        log.debug("Request to save RoomSeat : {}", roomSeatDTO);
        RoomSeat roomSeat = roomSeatMapper.toEntity(roomSeatDTO);
        roomSeat = roomSeatRepository.save(roomSeat);
        return roomSeatMapper.toDto(roomSeat);
    }

    @Override
    public RoomSeatDTO update(RoomSeatDTO roomSeatDTO) {
        log.debug("Request to save RoomSeat : {}", roomSeatDTO);
        RoomSeat roomSeat = roomSeatMapper.toEntity(roomSeatDTO);
        roomSeat = roomSeatRepository.save(roomSeat);
        return roomSeatMapper.toDto(roomSeat);
    }

    @Override
    public Optional<RoomSeatDTO> partialUpdate(RoomSeatDTO roomSeatDTO) {
        log.debug("Request to partially update RoomSeat : {}", roomSeatDTO);

        return roomSeatRepository
            .findById(roomSeatDTO.getId())
            .map(existingRoomSeat -> {
                roomSeatMapper.partialUpdate(existingRoomSeat, roomSeatDTO);

                return existingRoomSeat;
            })
            .map(roomSeatRepository::save)
            .map(roomSeatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomSeatDTO> findAll() {
        log.debug("Request to get all RoomSeats");
        return roomSeatRepository.findAll().stream().map(roomSeatMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomSeatDTO> findOne(Long id) {
        log.debug("Request to get RoomSeat : {}", id);
        return roomSeatRepository.findById(id).map(roomSeatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomSeat : {}", id);
        roomSeatRepository.deleteById(id);
    }
}
