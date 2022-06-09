package com.atensys.service.impl;

import com.atensys.domain.AttributeFloor;
import com.atensys.repository.AttributeFloorRepository;
import com.atensys.service.AttributeFloorService;
import com.atensys.service.dto.AttributeFloorDTO;
import com.atensys.service.mapper.AttributeFloorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributeFloor}.
 */
@Service
@Transactional
public class AttributeFloorServiceImpl implements AttributeFloorService {

    private final Logger log = LoggerFactory.getLogger(AttributeFloorServiceImpl.class);

    private final AttributeFloorRepository attributeFloorRepository;

    private final AttributeFloorMapper attributeFloorMapper;

    public AttributeFloorServiceImpl(AttributeFloorRepository attributeFloorRepository, AttributeFloorMapper attributeFloorMapper) {
        this.attributeFloorRepository = attributeFloorRepository;
        this.attributeFloorMapper = attributeFloorMapper;
    }

    @Override
    public AttributeFloorDTO save(AttributeFloorDTO attributeFloorDTO) {
        log.debug("Request to save AttributeFloor : {}", attributeFloorDTO);
        AttributeFloor attributeFloor = attributeFloorMapper.toEntity(attributeFloorDTO);
        attributeFloor = attributeFloorRepository.save(attributeFloor);
        return attributeFloorMapper.toDto(attributeFloor);
    }

    @Override
    public AttributeFloorDTO update(AttributeFloorDTO attributeFloorDTO) {
        log.debug("Request to save AttributeFloor : {}", attributeFloorDTO);
        AttributeFloor attributeFloor = attributeFloorMapper.toEntity(attributeFloorDTO);
        attributeFloor = attributeFloorRepository.save(attributeFloor);
        return attributeFloorMapper.toDto(attributeFloor);
    }

    @Override
    public Optional<AttributeFloorDTO> partialUpdate(AttributeFloorDTO attributeFloorDTO) {
        log.debug("Request to partially update AttributeFloor : {}", attributeFloorDTO);

        return attributeFloorRepository
            .findById(attributeFloorDTO.getId())
            .map(existingAttributeFloor -> {
                attributeFloorMapper.partialUpdate(existingAttributeFloor, attributeFloorDTO);

                return existingAttributeFloor;
            })
            .map(attributeFloorRepository::save)
            .map(attributeFloorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttributeFloorDTO> findAll() {
        log.debug("Request to get all AttributeFloors");
        return attributeFloorRepository
            .findAll()
            .stream()
            .map(attributeFloorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributeFloorDTO> findOne(Long id) {
        log.debug("Request to get AttributeFloor : {}", id);
        return attributeFloorRepository.findById(id).map(attributeFloorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributeFloor : {}", id);
        attributeFloorRepository.deleteById(id);
    }
}
