package com.atensys.service.impl;

import com.atensys.domain.Floor;
import com.atensys.repository.FloorRepository;
import com.atensys.service.FloorService;
import com.atensys.service.dto.FloorDTO;
import com.atensys.service.mapper.FloorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Floor}.
 */
@Service
@Transactional
public class FloorServiceImpl implements FloorService {

    private final Logger log = LoggerFactory.getLogger(FloorServiceImpl.class);

    private final FloorRepository floorRepository;

    private final FloorMapper floorMapper;

    public FloorServiceImpl(FloorRepository floorRepository, FloorMapper floorMapper) {
        this.floorRepository = floorRepository;
        this.floorMapper = floorMapper;
    }

    @Override
    public FloorDTO save(FloorDTO floorDTO) {
        log.debug("Request to save Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    @Override
    public FloorDTO update(FloorDTO floorDTO) {
        log.debug("Request to save Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    @Override
    public Optional<FloorDTO> partialUpdate(FloorDTO floorDTO) {
        log.debug("Request to partially update Floor : {}", floorDTO);

        return floorRepository
            .findById(floorDTO.getId())
            .map(existingFloor -> {
                floorMapper.partialUpdate(existingFloor, floorDTO);

                return existingFloor;
            })
            .map(floorRepository::save)
            .map(floorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FloorDTO> findAll() {
        log.debug("Request to get all Floors");
        return floorRepository.findAll().stream().map(floorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FloorDTO> findOne(Long id) {
        log.debug("Request to get Floor : {}", id);
        return floorRepository.findById(id).map(floorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Floor : {}", id);
        floorRepository.deleteById(id);
    }
}
