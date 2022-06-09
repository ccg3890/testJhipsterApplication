package com.atensys.service.impl;

import com.atensys.domain.Archetype;
import com.atensys.repository.ArchetypeRepository;
import com.atensys.service.ArchetypeService;
import com.atensys.service.dto.ArchetypeDTO;
import com.atensys.service.mapper.ArchetypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Archetype}.
 */
@Service
@Transactional
public class ArchetypeServiceImpl implements ArchetypeService {

    private final Logger log = LoggerFactory.getLogger(ArchetypeServiceImpl.class);

    private final ArchetypeRepository archetypeRepository;

    private final ArchetypeMapper archetypeMapper;

    public ArchetypeServiceImpl(ArchetypeRepository archetypeRepository, ArchetypeMapper archetypeMapper) {
        this.archetypeRepository = archetypeRepository;
        this.archetypeMapper = archetypeMapper;
    }

    @Override
    public ArchetypeDTO save(ArchetypeDTO archetypeDTO) {
        log.debug("Request to save Archetype : {}", archetypeDTO);
        Archetype archetype = archetypeMapper.toEntity(archetypeDTO);
        archetype = archetypeRepository.save(archetype);
        return archetypeMapper.toDto(archetype);
    }

    @Override
    public ArchetypeDTO update(ArchetypeDTO archetypeDTO) {
        log.debug("Request to save Archetype : {}", archetypeDTO);
        Archetype archetype = archetypeMapper.toEntity(archetypeDTO);
        archetype = archetypeRepository.save(archetype);
        return archetypeMapper.toDto(archetype);
    }

    @Override
    public Optional<ArchetypeDTO> partialUpdate(ArchetypeDTO archetypeDTO) {
        log.debug("Request to partially update Archetype : {}", archetypeDTO);

        return archetypeRepository
            .findById(archetypeDTO.getId())
            .map(existingArchetype -> {
                archetypeMapper.partialUpdate(existingArchetype, archetypeDTO);

                return existingArchetype;
            })
            .map(archetypeRepository::save)
            .map(archetypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArchetypeDTO> findAll() {
        log.debug("Request to get all Archetypes");
        return archetypeRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(archetypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ArchetypeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return archetypeRepository.findAllWithEagerRelationships(pageable).map(archetypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArchetypeDTO> findOne(Long id) {
        log.debug("Request to get Archetype : {}", id);
        return archetypeRepository.findOneWithEagerRelationships(id).map(archetypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Archetype : {}", id);
        archetypeRepository.deleteById(id);
    }
}
