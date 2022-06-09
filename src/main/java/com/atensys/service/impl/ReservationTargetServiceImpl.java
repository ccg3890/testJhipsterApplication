package com.atensys.service.impl;

import com.atensys.domain.ReservationTarget;
import com.atensys.repository.ReservationTargetRepository;
import com.atensys.service.ReservationTargetService;
import com.atensys.service.dto.ReservationTargetDTO;
import com.atensys.service.mapper.ReservationTargetMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReservationTarget}.
 */
@Service
@Transactional
public class ReservationTargetServiceImpl implements ReservationTargetService {

    private final Logger log = LoggerFactory.getLogger(ReservationTargetServiceImpl.class);

    private final ReservationTargetRepository reservationTargetRepository;

    private final ReservationTargetMapper reservationTargetMapper;

    public ReservationTargetServiceImpl(
        ReservationTargetRepository reservationTargetRepository,
        ReservationTargetMapper reservationTargetMapper
    ) {
        this.reservationTargetRepository = reservationTargetRepository;
        this.reservationTargetMapper = reservationTargetMapper;
    }

    @Override
    public ReservationTargetDTO save(ReservationTargetDTO reservationTargetDTO) {
        log.debug("Request to save ReservationTarget : {}", reservationTargetDTO);
        ReservationTarget reservationTarget = reservationTargetMapper.toEntity(reservationTargetDTO);
        reservationTarget = reservationTargetRepository.save(reservationTarget);
        return reservationTargetMapper.toDto(reservationTarget);
    }

    @Override
    public ReservationTargetDTO update(ReservationTargetDTO reservationTargetDTO) {
        log.debug("Request to save ReservationTarget : {}", reservationTargetDTO);
        ReservationTarget reservationTarget = reservationTargetMapper.toEntity(reservationTargetDTO);
        reservationTarget = reservationTargetRepository.save(reservationTarget);
        return reservationTargetMapper.toDto(reservationTarget);
    }

    @Override
    public Optional<ReservationTargetDTO> partialUpdate(ReservationTargetDTO reservationTargetDTO) {
        log.debug("Request to partially update ReservationTarget : {}", reservationTargetDTO);

        return reservationTargetRepository
            .findById(reservationTargetDTO.getId())
            .map(existingReservationTarget -> {
                reservationTargetMapper.partialUpdate(existingReservationTarget, reservationTargetDTO);

                return existingReservationTarget;
            })
            .map(reservationTargetRepository::save)
            .map(reservationTargetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationTargetDTO> findAll() {
        log.debug("Request to get all ReservationTargets");
        return reservationTargetRepository
            .findAll()
            .stream()
            .map(reservationTargetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservationTargetDTO> findOne(Long id) {
        log.debug("Request to get ReservationTarget : {}", id);
        return reservationTargetRepository.findById(id).map(reservationTargetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReservationTarget : {}", id);
        reservationTargetRepository.deleteById(id);
    }
}
