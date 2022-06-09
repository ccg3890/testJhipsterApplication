package com.atensys.service.impl;

import com.atensys.domain.AttributeOffice;
import com.atensys.repository.AttributeOfficeRepository;
import com.atensys.service.AttributeOfficeService;
import com.atensys.service.dto.AttributeOfficeDTO;
import com.atensys.service.mapper.AttributeOfficeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributeOffice}.
 */
@Service
@Transactional
public class AttributeOfficeServiceImpl implements AttributeOfficeService {

    private final Logger log = LoggerFactory.getLogger(AttributeOfficeServiceImpl.class);

    private final AttributeOfficeRepository attributeOfficeRepository;

    private final AttributeOfficeMapper attributeOfficeMapper;

    public AttributeOfficeServiceImpl(AttributeOfficeRepository attributeOfficeRepository, AttributeOfficeMapper attributeOfficeMapper) {
        this.attributeOfficeRepository = attributeOfficeRepository;
        this.attributeOfficeMapper = attributeOfficeMapper;
    }

    @Override
    public AttributeOfficeDTO save(AttributeOfficeDTO attributeOfficeDTO) {
        log.debug("Request to save AttributeOffice : {}", attributeOfficeDTO);
        AttributeOffice attributeOffice = attributeOfficeMapper.toEntity(attributeOfficeDTO);
        attributeOffice = attributeOfficeRepository.save(attributeOffice);
        return attributeOfficeMapper.toDto(attributeOffice);
    }

    @Override
    public AttributeOfficeDTO update(AttributeOfficeDTO attributeOfficeDTO) {
        log.debug("Request to save AttributeOffice : {}", attributeOfficeDTO);
        AttributeOffice attributeOffice = attributeOfficeMapper.toEntity(attributeOfficeDTO);
        attributeOffice = attributeOfficeRepository.save(attributeOffice);
        return attributeOfficeMapper.toDto(attributeOffice);
    }

    @Override
    public Optional<AttributeOfficeDTO> partialUpdate(AttributeOfficeDTO attributeOfficeDTO) {
        log.debug("Request to partially update AttributeOffice : {}", attributeOfficeDTO);

        return attributeOfficeRepository
            .findById(attributeOfficeDTO.getId())
            .map(existingAttributeOffice -> {
                attributeOfficeMapper.partialUpdate(existingAttributeOffice, attributeOfficeDTO);

                return existingAttributeOffice;
            })
            .map(attributeOfficeRepository::save)
            .map(attributeOfficeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttributeOfficeDTO> findAll() {
        log.debug("Request to get all AttributeOffices");
        return attributeOfficeRepository
            .findAll()
            .stream()
            .map(attributeOfficeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributeOfficeDTO> findOne(Long id) {
        log.debug("Request to get AttributeOffice : {}", id);
        return attributeOfficeRepository.findById(id).map(attributeOfficeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributeOffice : {}", id);
        attributeOfficeRepository.deleteById(id);
    }
}
