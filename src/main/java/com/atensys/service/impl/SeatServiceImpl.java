package com.atensys.service.impl;

import com.atensys.domain.Seat;
import com.atensys.repository.SeatRepository;
import com.atensys.service.SeatService;
import com.atensys.service.dto.SeatDTO;
import com.atensys.service.mapper.SeatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Seat}.
 */
@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final Logger log = LoggerFactory.getLogger(SeatServiceImpl.class);

    private final SeatRepository seatRepository;

    private final SeatMapper seatMapper;

    public SeatServiceImpl(SeatRepository seatRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
    }

    @Override
    public SeatDTO save(SeatDTO seatDTO) {
        log.debug("Request to save Seat : {}", seatDTO);
        Seat seat = seatMapper.toEntity(seatDTO);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    @Override
    public SeatDTO update(SeatDTO seatDTO) {
        log.debug("Request to save Seat : {}", seatDTO);
        Seat seat = seatMapper.toEntity(seatDTO);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    @Override
    public Optional<SeatDTO> partialUpdate(SeatDTO seatDTO) {
        log.debug("Request to partially update Seat : {}", seatDTO);

        return seatRepository
            .findById(seatDTO.getId())
            .map(existingSeat -> {
                seatMapper.partialUpdate(existingSeat, seatDTO);

                return existingSeat;
            })
            .map(seatRepository::save)
            .map(seatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatDTO> findAll() {
        log.debug("Request to get all Seats");
        return seatRepository.findAll().stream().map(seatMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeatDTO> findOne(Long id) {
        log.debug("Request to get Seat : {}", id);
        return seatRepository.findById(id).map(seatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seat : {}", id);
        seatRepository.deleteById(id);
    }
}
