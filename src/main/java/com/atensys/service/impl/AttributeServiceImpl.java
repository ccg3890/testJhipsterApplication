package com.atensys.service.impl;

import com.atensys.domain.Attribute;
import com.atensys.repository.AttributeRepository;
import com.atensys.service.AttributeService;
import com.atensys.service.dto.AttributeDTO;
import com.atensys.service.mapper.AttributeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Attribute}.
 */
@Service
@Transactional
public class AttributeServiceImpl implements AttributeService {

    private final Logger log = LoggerFactory.getLogger(AttributeServiceImpl.class);

    private final AttributeRepository attributeRepository;

    private final AttributeMapper attributeMapper;

    public AttributeServiceImpl(AttributeRepository attributeRepository, AttributeMapper attributeMapper) {
        this.attributeRepository = attributeRepository;
        this.attributeMapper = attributeMapper;
    }

    @Override
    public AttributeDTO save(AttributeDTO attributeDTO) {
        log.debug("Request to save Attribute : {}", attributeDTO);
        Attribute attribute = attributeMapper.toEntity(attributeDTO);
        attribute = attributeRepository.save(attribute);
        return attributeMapper.toDto(attribute);
    }

    @Override
    public AttributeDTO update(AttributeDTO attributeDTO) {
        log.debug("Request to save Attribute : {}", attributeDTO);
        Attribute attribute = attributeMapper.toEntity(attributeDTO);
        attribute = attributeRepository.save(attribute);
        return attributeMapper.toDto(attribute);
    }

    @Override
    public Optional<AttributeDTO> partialUpdate(AttributeDTO attributeDTO) {
        log.debug("Request to partially update Attribute : {}", attributeDTO);

        return attributeRepository
            .findById(attributeDTO.getId())
            .map(existingAttribute -> {
                attributeMapper.partialUpdate(existingAttribute, attributeDTO);

                return existingAttribute;
            })
            .map(attributeRepository::save)
            .map(attributeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttributeDTO> findAll() {
        log.debug("Request to get all Attributes");
        return attributeRepository.findAll().stream().map(attributeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributeDTO> findOne(Long id) {
        log.debug("Request to get Attribute : {}", id);
        return attributeRepository.findById(id).map(attributeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attribute : {}", id);
        attributeRepository.deleteById(id);
    }
}
