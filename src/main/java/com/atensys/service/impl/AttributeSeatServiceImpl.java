package com.atensys.service.impl;

import com.atensys.domain.AttributeSeat;
import com.atensys.repository.AttributeSeatRepository;
import com.atensys.service.AttributeSeatService;
import com.atensys.service.dto.AttributeSeatDTO;
import com.atensys.service.mapper.AttributeSeatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributeSeat}.
 */
@Service
@Transactional
public class AttributeSeatServiceImpl implements AttributeSeatService {

    private final Logger log = LoggerFactory.getLogger(AttributeSeatServiceImpl.class);

    private final AttributeSeatRepository attributeSeatRepository;

    private final AttributeSeatMapper attributeSeatMapper;

    public AttributeSeatServiceImpl(AttributeSeatRepository attributeSeatRepository, AttributeSeatMapper attributeSeatMapper) {
        this.attributeSeatRepository = attributeSeatRepository;
        this.attributeSeatMapper = attributeSeatMapper;
    }

    @Override
    public AttributeSeatDTO save(AttributeSeatDTO attributeSeatDTO) {
        log.debug("Request to save AttributeSeat : {}", attributeSeatDTO);
        AttributeSeat attributeSeat = attributeSeatMapper.toEntity(attributeSeatDTO);
        attributeSeat = attributeSeatRepository.save(attributeSeat);
        return attributeSeatMapper.toDto(attributeSeat);
    }

    @Override
    public AttributeSeatDTO update(AttributeSeatDTO attributeSeatDTO) {
        log.debug("Request to save AttributeSeat : {}", attributeSeatDTO);
        AttributeSeat attributeSeat = attributeSeatMapper.toEntity(attributeSeatDTO);
        attributeSeat = attributeSeatRepository.save(attributeSeat);
        return attributeSeatMapper.toDto(attributeSeat);
    }

    @Override
    public Optional<AttributeSeatDTO> partialUpdate(AttributeSeatDTO attributeSeatDTO) {
        log.debug("Request to partially update AttributeSeat : {}", attributeSeatDTO);

        return attributeSeatRepository
            .findById(attributeSeatDTO.getId())
            .map(existingAttributeSeat -> {
                attributeSeatMapper.partialUpdate(existingAttributeSeat, attributeSeatDTO);

                return existingAttributeSeat;
            })
            .map(attributeSeatRepository::save)
            .map(attributeSeatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttributeSeatDTO> findAll() {
        log.debug("Request to get all AttributeSeats");
        return attributeSeatRepository.findAll().stream().map(attributeSeatMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributeSeatDTO> findOne(Long id) {
        log.debug("Request to get AttributeSeat : {}", id);
        return attributeSeatRepository.findById(id).map(attributeSeatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributeSeat : {}", id);
        attributeSeatRepository.deleteById(id);
    }
}
