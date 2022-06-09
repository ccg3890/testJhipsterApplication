package com.atensys.service.impl;

import com.atensys.domain.Recurrence;
import com.atensys.repository.RecurrenceRepository;
import com.atensys.service.RecurrenceService;
import com.atensys.service.dto.RecurrenceDTO;
import com.atensys.service.mapper.RecurrenceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Recurrence}.
 */
@Service
@Transactional
public class RecurrenceServiceImpl implements RecurrenceService {

    private final Logger log = LoggerFactory.getLogger(RecurrenceServiceImpl.class);

    private final RecurrenceRepository recurrenceRepository;

    private final RecurrenceMapper recurrenceMapper;

    public RecurrenceServiceImpl(RecurrenceRepository recurrenceRepository, RecurrenceMapper recurrenceMapper) {
        this.recurrenceRepository = recurrenceRepository;
        this.recurrenceMapper = recurrenceMapper;
    }

    @Override
    public RecurrenceDTO save(RecurrenceDTO recurrenceDTO) {
        log.debug("Request to save Recurrence : {}", recurrenceDTO);
        Recurrence recurrence = recurrenceMapper.toEntity(recurrenceDTO);
        recurrence = recurrenceRepository.save(recurrence);
        return recurrenceMapper.toDto(recurrence);
    }

    @Override
    public RecurrenceDTO update(RecurrenceDTO recurrenceDTO) {
        log.debug("Request to save Recurrence : {}", recurrenceDTO);
        Recurrence recurrence = recurrenceMapper.toEntity(recurrenceDTO);
        recurrence = recurrenceRepository.save(recurrence);
        return recurrenceMapper.toDto(recurrence);
    }

    @Override
    public Optional<RecurrenceDTO> partialUpdate(RecurrenceDTO recurrenceDTO) {
        log.debug("Request to partially update Recurrence : {}", recurrenceDTO);

        return recurrenceRepository
            .findById(recurrenceDTO.getId())
            .map(existingRecurrence -> {
                recurrenceMapper.partialUpdate(existingRecurrence, recurrenceDTO);

                return existingRecurrence;
            })
            .map(recurrenceRepository::save)
            .map(recurrenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecurrenceDTO> findAll() {
        log.debug("Request to get all Recurrences");
        return recurrenceRepository.findAll().stream().map(recurrenceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecurrenceDTO> findOne(Long id) {
        log.debug("Request to get Recurrence : {}", id);
        return recurrenceRepository.findById(id).map(recurrenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recurrence : {}", id);
        recurrenceRepository.deleteById(id);
    }
}
