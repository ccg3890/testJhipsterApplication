package com.atensys.service.impl;

import com.atensys.domain.Dictionary;
import com.atensys.repository.DictionaryRepository;
import com.atensys.service.DictionaryService;
import com.atensys.service.dto.DictionaryDTO;
import com.atensys.service.mapper.DictionaryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dictionary}.
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    private final Logger log = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    private final DictionaryRepository dictionaryRepository;

    private final DictionaryMapper dictionaryMapper;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository, DictionaryMapper dictionaryMapper) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    public DictionaryDTO save(DictionaryDTO dictionaryDTO) {
        log.debug("Request to save Dictionary : {}", dictionaryDTO);
        Dictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);
        dictionary = dictionaryRepository.save(dictionary);
        return dictionaryMapper.toDto(dictionary);
    }

    @Override
    public DictionaryDTO update(DictionaryDTO dictionaryDTO) {
        log.debug("Request to save Dictionary : {}", dictionaryDTO);
        Dictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);
        dictionary = dictionaryRepository.save(dictionary);
        return dictionaryMapper.toDto(dictionary);
    }

    @Override
    public Optional<DictionaryDTO> partialUpdate(DictionaryDTO dictionaryDTO) {
        log.debug("Request to partially update Dictionary : {}", dictionaryDTO);

        return dictionaryRepository
            .findById(dictionaryDTO.getId())
            .map(existingDictionary -> {
                dictionaryMapper.partialUpdate(existingDictionary, dictionaryDTO);

                return existingDictionary;
            })
            .map(dictionaryRepository::save)
            .map(dictionaryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryDTO> findAll() {
        log.debug("Request to get all Dictionaries");
        return dictionaryRepository.findAll().stream().map(dictionaryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DictionaryDTO> findOne(Long id) {
        log.debug("Request to get Dictionary : {}", id);
        return dictionaryRepository.findById(id).map(dictionaryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dictionary : {}", id);
        dictionaryRepository.deleteById(id);
    }
}
