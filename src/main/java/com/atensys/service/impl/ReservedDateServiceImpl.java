package com.atensys.service.impl;

import com.atensys.domain.ReservedDate;
import com.atensys.repository.ReservedDateRepository;
import com.atensys.service.ReservedDateService;
import com.atensys.service.dto.ReservedDateDTO;
import com.atensys.service.mapper.ReservedDateMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReservedDate}.
 */
@Service
@Transactional
public class ReservedDateServiceImpl implements ReservedDateService {

    private final Logger log = LoggerFactory.getLogger(ReservedDateServiceImpl.class);

    private final ReservedDateRepository reservedDateRepository;

    private final ReservedDateMapper reservedDateMapper;

    public ReservedDateServiceImpl(ReservedDateRepository reservedDateRepository, ReservedDateMapper reservedDateMapper) {
        this.reservedDateRepository = reservedDateRepository;
        this.reservedDateMapper = reservedDateMapper;
    }

    @Override
    public ReservedDateDTO save(ReservedDateDTO reservedDateDTO) {
        log.debug("Request to save ReservedDate : {}", reservedDateDTO);
        ReservedDate reservedDate = reservedDateMapper.toEntity(reservedDateDTO);
        reservedDate = reservedDateRepository.save(reservedDate);
        return reservedDateMapper.toDto(reservedDate);
    }

    @Override
    public ReservedDateDTO update(ReservedDateDTO reservedDateDTO) {
        log.debug("Request to save ReservedDate : {}", reservedDateDTO);
        ReservedDate reservedDate = reservedDateMapper.toEntity(reservedDateDTO);
        reservedDate = reservedDateRepository.save(reservedDate);
        return reservedDateMapper.toDto(reservedDate);
    }

    @Override
    public Optional<ReservedDateDTO> partialUpdate(ReservedDateDTO reservedDateDTO) {
        log.debug("Request to partially update ReservedDate : {}", reservedDateDTO);

        return reservedDateRepository
            .findById(reservedDateDTO.getId())
            .map(existingReservedDate -> {
                reservedDateMapper.partialUpdate(existingReservedDate, reservedDateDTO);

                return existingReservedDate;
            })
            .map(reservedDateRepository::save)
            .map(reservedDateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservedDateDTO> findAll() {
        log.debug("Request to get all ReservedDates");
        return reservedDateRepository.findAll().stream().map(reservedDateMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservedDateDTO> findOne(Long id) {
        log.debug("Request to get ReservedDate : {}", id);
        return reservedDateRepository.findById(id).map(reservedDateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReservedDate : {}", id);
        reservedDateRepository.deleteById(id);
    }
}
