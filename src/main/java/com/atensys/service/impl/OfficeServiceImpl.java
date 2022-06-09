package com.atensys.service.impl;

import com.atensys.domain.Office;
import com.atensys.repository.OfficeRepository;
import com.atensys.service.OfficeService;
import com.atensys.service.dto.OfficeDTO;
import com.atensys.service.mapper.OfficeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Office}.
 */
@Service
@Transactional
public class OfficeServiceImpl implements OfficeService {

    private final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    private final OfficeRepository officeRepository;

    private final OfficeMapper officeMapper;

    public OfficeServiceImpl(OfficeRepository officeRepository, OfficeMapper officeMapper) {
        this.officeRepository = officeRepository;
        this.officeMapper = officeMapper;
    }

    @Override
    public OfficeDTO save(OfficeDTO officeDTO) {
        log.debug("Request to save Office : {}", officeDTO);
        Office office = officeMapper.toEntity(officeDTO);
        office = officeRepository.save(office);
        return officeMapper.toDto(office);
    }

    @Override
    public OfficeDTO update(OfficeDTO officeDTO) {
        log.debug("Request to save Office : {}", officeDTO);
        Office office = officeMapper.toEntity(officeDTO);
        office = officeRepository.save(office);
        return officeMapper.toDto(office);
    }

    @Override
    public Optional<OfficeDTO> partialUpdate(OfficeDTO officeDTO) {
        log.debug("Request to partially update Office : {}", officeDTO);

        return officeRepository
            .findById(officeDTO.getId())
            .map(existingOffice -> {
                officeMapper.partialUpdate(existingOffice, officeDTO);

                return existingOffice;
            })
            .map(officeRepository::save)
            .map(officeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfficeDTO> findAll() {
        log.debug("Request to get all Offices");
        return officeRepository.findAll().stream().map(officeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OfficeDTO> findOne(Long id) {
        log.debug("Request to get Office : {}", id);
        return officeRepository.findById(id).map(officeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Office : {}", id);
        officeRepository.deleteById(id);
    }
}
