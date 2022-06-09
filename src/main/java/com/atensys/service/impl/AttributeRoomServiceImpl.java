package com.atensys.service.impl;

import com.atensys.domain.AttributeRoom;
import com.atensys.repository.AttributeRoomRepository;
import com.atensys.service.AttributeRoomService;
import com.atensys.service.dto.AttributeRoomDTO;
import com.atensys.service.mapper.AttributeRoomMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributeRoom}.
 */
@Service
@Transactional
public class AttributeRoomServiceImpl implements AttributeRoomService {

    private final Logger log = LoggerFactory.getLogger(AttributeRoomServiceImpl.class);

    private final AttributeRoomRepository attributeRoomRepository;

    private final AttributeRoomMapper attributeRoomMapper;

    public AttributeRoomServiceImpl(AttributeRoomRepository attributeRoomRepository, AttributeRoomMapper attributeRoomMapper) {
        this.attributeRoomRepository = attributeRoomRepository;
        this.attributeRoomMapper = attributeRoomMapper;
    }

    @Override
    public AttributeRoomDTO save(AttributeRoomDTO attributeRoomDTO) {
        log.debug("Request to save AttributeRoom : {}", attributeRoomDTO);
        AttributeRoom attributeRoom = attributeRoomMapper.toEntity(attributeRoomDTO);
        attributeRoom = attributeRoomRepository.save(attributeRoom);
        return attributeRoomMapper.toDto(attributeRoom);
    }

    @Override
    public AttributeRoomDTO update(AttributeRoomDTO attributeRoomDTO) {
        log.debug("Request to save AttributeRoom : {}", attributeRoomDTO);
        AttributeRoom attributeRoom = attributeRoomMapper.toEntity(attributeRoomDTO);
        attributeRoom = attributeRoomRepository.save(attributeRoom);
        return attributeRoomMapper.toDto(attributeRoom);
    }

    @Override
    public Optional<AttributeRoomDTO> partialUpdate(AttributeRoomDTO attributeRoomDTO) {
        log.debug("Request to partially update AttributeRoom : {}", attributeRoomDTO);

        return attributeRoomRepository
            .findById(attributeRoomDTO.getId())
            .map(existingAttributeRoom -> {
                attributeRoomMapper.partialUpdate(existingAttributeRoom, attributeRoomDTO);

                return existingAttributeRoom;
            })
            .map(attributeRoomRepository::save)
            .map(attributeRoomMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttributeRoomDTO> findAll() {
        log.debug("Request to get all AttributeRooms");
        return attributeRoomRepository.findAll().stream().map(attributeRoomMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributeRoomDTO> findOne(Long id) {
        log.debug("Request to get AttributeRoom : {}", id);
        return attributeRoomRepository.findById(id).map(attributeRoomMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributeRoom : {}", id);
        attributeRoomRepository.deleteById(id);
    }
}
