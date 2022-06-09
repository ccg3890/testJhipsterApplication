package com.atensys.service.impl;

import com.atensys.domain.ReservedRoomSeat;
import com.atensys.repository.ReservedRoomSeatRepository;
import com.atensys.service.ReservedRoomSeatService;
import com.atensys.service.dto.ReservedRoomSeatDTO;
import com.atensys.service.mapper.ReservedRoomSeatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReservedRoomSeat}.
 */
@Service
@Transactional
public class ReservedRoomSeatServiceImpl implements ReservedRoomSeatService {

    private final Logger log = LoggerFactory.getLogger(ReservedRoomSeatServiceImpl.class);

    private final ReservedRoomSeatRepository reservedRoomSeatRepository;

    private final ReservedRoomSeatMapper reservedRoomSeatMapper;

    public ReservedRoomSeatServiceImpl(
        ReservedRoomSeatRepository reservedRoomSeatRepository,
        ReservedRoomSeatMapper reservedRoomSeatMapper
    ) {
        this.reservedRoomSeatRepository = reservedRoomSeatRepository;
        this.reservedRoomSeatMapper = reservedRoomSeatMapper;
    }

    @Override
    public ReservedRoomSeatDTO save(ReservedRoomSeatDTO reservedRoomSeatDTO) {
        log.debug("Request to save ReservedRoomSeat : {}", reservedRoomSeatDTO);
        ReservedRoomSeat reservedRoomSeat = reservedRoomSeatMapper.toEntity(reservedRoomSeatDTO);
        reservedRoomSeat = reservedRoomSeatRepository.save(reservedRoomSeat);
        return reservedRoomSeatMapper.toDto(reservedRoomSeat);
    }

    @Override
    public ReservedRoomSeatDTO update(ReservedRoomSeatDTO reservedRoomSeatDTO) {
        log.debug("Request to save ReservedRoomSeat : {}", reservedRoomSeatDTO);
        ReservedRoomSeat reservedRoomSeat = reservedRoomSeatMapper.toEntity(reservedRoomSeatDTO);
        reservedRoomSeat = reservedRoomSeatRepository.save(reservedRoomSeat);
        return reservedRoomSeatMapper.toDto(reservedRoomSeat);
    }

    @Override
    public Optional<ReservedRoomSeatDTO> partialUpdate(ReservedRoomSeatDTO reservedRoomSeatDTO) {
        log.debug("Request to partially update ReservedRoomSeat : {}", reservedRoomSeatDTO);

        return reservedRoomSeatRepository
            .findById(reservedRoomSeatDTO.getId())
            .map(existingReservedRoomSeat -> {
                reservedRoomSeatMapper.partialUpdate(existingReservedRoomSeat, reservedRoomSeatDTO);

                return existingReservedRoomSeat;
            })
            .map(reservedRoomSeatRepository::save)
            .map(reservedRoomSeatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservedRoomSeatDTO> findAll() {
        log.debug("Request to get all ReservedRoomSeats");
        return reservedRoomSeatRepository
            .findAll()
            .stream()
            .map(reservedRoomSeatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservedRoomSeatDTO> findOne(Long id) {
        log.debug("Request to get ReservedRoomSeat : {}", id);
        return reservedRoomSeatRepository.findById(id).map(reservedRoomSeatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReservedRoomSeat : {}", id);
        reservedRoomSeatRepository.deleteById(id);
    }
}
