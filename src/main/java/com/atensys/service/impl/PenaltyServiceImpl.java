package com.atensys.service.impl;

import com.atensys.domain.Penalty;
import com.atensys.repository.PenaltyRepository;
import com.atensys.service.PenaltyService;
import com.atensys.service.dto.PenaltyDTO;
import com.atensys.service.mapper.PenaltyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Penalty}.
 */
@Service
@Transactional
public class PenaltyServiceImpl implements PenaltyService {

    private final Logger log = LoggerFactory.getLogger(PenaltyServiceImpl.class);

    private final PenaltyRepository penaltyRepository;

    private final PenaltyMapper penaltyMapper;

    public PenaltyServiceImpl(PenaltyRepository penaltyRepository, PenaltyMapper penaltyMapper) {
        this.penaltyRepository = penaltyRepository;
        this.penaltyMapper = penaltyMapper;
    }

    @Override
    public PenaltyDTO save(PenaltyDTO penaltyDTO) {
        log.debug("Request to save Penalty : {}", penaltyDTO);
        Penalty penalty = penaltyMapper.toEntity(penaltyDTO);
        penalty = penaltyRepository.save(penalty);
        return penaltyMapper.toDto(penalty);
    }

    @Override
    public PenaltyDTO update(PenaltyDTO penaltyDTO) {
        log.debug("Request to save Penalty : {}", penaltyDTO);
        Penalty penalty = penaltyMapper.toEntity(penaltyDTO);
        penalty = penaltyRepository.save(penalty);
        return penaltyMapper.toDto(penalty);
    }

    @Override
    public Optional<PenaltyDTO> partialUpdate(PenaltyDTO penaltyDTO) {
        log.debug("Request to partially update Penalty : {}", penaltyDTO);

        return penaltyRepository
            .findById(penaltyDTO.getId())
            .map(existingPenalty -> {
                penaltyMapper.partialUpdate(existingPenalty, penaltyDTO);

                return existingPenalty;
            })
            .map(penaltyRepository::save)
            .map(penaltyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PenaltyDTO> findAll() {
        log.debug("Request to get all Penalties");
        return penaltyRepository.findAll().stream().map(penaltyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PenaltyDTO> findOne(Long id) {
        log.debug("Request to get Penalty : {}", id);
        return penaltyRepository.findById(id).map(penaltyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Penalty : {}", id);
        penaltyRepository.deleteById(id);
    }
}
