package com.atensys.service.impl;

import com.atensys.domain.AttributeCompany;
import com.atensys.repository.AttributeCompanyRepository;
import com.atensys.service.AttributeCompanyService;
import com.atensys.service.dto.AttributeCompanyDTO;
import com.atensys.service.mapper.AttributeCompanyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributeCompany}.
 */
@Service
@Transactional
public class AttributeCompanyServiceImpl implements AttributeCompanyService {

    private final Logger log = LoggerFactory.getLogger(AttributeCompanyServiceImpl.class);

    private final AttributeCompanyRepository attributeCompanyRepository;

    private final AttributeCompanyMapper attributeCompanyMapper;

    public AttributeCompanyServiceImpl(
        AttributeCompanyRepository attributeCompanyRepository,
        AttributeCompanyMapper attributeCompanyMapper
    ) {
        this.attributeCompanyRepository = attributeCompanyRepository;
        this.attributeCompanyMapper = attributeCompanyMapper;
    }

    @Override
    public AttributeCompanyDTO save(AttributeCompanyDTO attributeCompanyDTO) {
        log.debug("Request to save AttributeCompany : {}", attributeCompanyDTO);
        AttributeCompany attributeCompany = attributeCompanyMapper.toEntity(attributeCompanyDTO);
        attributeCompany = attributeCompanyRepository.save(attributeCompany);
        return attributeCompanyMapper.toDto(attributeCompany);
    }

    @Override
    public AttributeCompanyDTO update(AttributeCompanyDTO attributeCompanyDTO) {
        log.debug("Request to save AttributeCompany : {}", attributeCompanyDTO);
        AttributeCompany attributeCompany = attributeCompanyMapper.toEntity(attributeCompanyDTO);
        attributeCompany = attributeCompanyRepository.save(attributeCompany);
        return attributeCompanyMapper.toDto(attributeCompany);
    }

    @Override
    public Optional<AttributeCompanyDTO> partialUpdate(AttributeCompanyDTO attributeCompanyDTO) {
        log.debug("Request to partially update AttributeCompany : {}", attributeCompanyDTO);

        return attributeCompanyRepository
            .findById(attributeCompanyDTO.getId())
            .map(existingAttributeCompany -> {
                attributeCompanyMapper.partialUpdate(existingAttributeCompany, attributeCompanyDTO);

                return existingAttributeCompany;
            })
            .map(attributeCompanyRepository::save)
            .map(attributeCompanyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttributeCompanyDTO> findAll() {
        log.debug("Request to get all AttributeCompanies");
        return attributeCompanyRepository
            .findAll()
            .stream()
            .map(attributeCompanyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributeCompanyDTO> findOne(Long id) {
        log.debug("Request to get AttributeCompany : {}", id);
        return attributeCompanyRepository.findById(id).map(attributeCompanyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributeCompany : {}", id);
        attributeCompanyRepository.deleteById(id);
    }
}
