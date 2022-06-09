package com.atensys.service.impl;

import com.atensys.domain.Fileinfo;
import com.atensys.repository.FileinfoRepository;
import com.atensys.service.FileinfoService;
import com.atensys.service.dto.FileinfoDTO;
import com.atensys.service.mapper.FileinfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Fileinfo}.
 */
@Service
@Transactional
public class FileinfoServiceImpl implements FileinfoService {

    private final Logger log = LoggerFactory.getLogger(FileinfoServiceImpl.class);

    private final FileinfoRepository fileinfoRepository;

    private final FileinfoMapper fileinfoMapper;

    public FileinfoServiceImpl(FileinfoRepository fileinfoRepository, FileinfoMapper fileinfoMapper) {
        this.fileinfoRepository = fileinfoRepository;
        this.fileinfoMapper = fileinfoMapper;
    }

    @Override
    public FileinfoDTO save(FileinfoDTO fileinfoDTO) {
        log.debug("Request to save Fileinfo : {}", fileinfoDTO);
        Fileinfo fileinfo = fileinfoMapper.toEntity(fileinfoDTO);
        fileinfo = fileinfoRepository.save(fileinfo);
        return fileinfoMapper.toDto(fileinfo);
    }

    @Override
    public FileinfoDTO update(FileinfoDTO fileinfoDTO) {
        log.debug("Request to save Fileinfo : {}", fileinfoDTO);
        Fileinfo fileinfo = fileinfoMapper.toEntity(fileinfoDTO);
        fileinfo = fileinfoRepository.save(fileinfo);
        return fileinfoMapper.toDto(fileinfo);
    }

    @Override
    public Optional<FileinfoDTO> partialUpdate(FileinfoDTO fileinfoDTO) {
        log.debug("Request to partially update Fileinfo : {}", fileinfoDTO);

        return fileinfoRepository
            .findById(fileinfoDTO.getId())
            .map(existingFileinfo -> {
                fileinfoMapper.partialUpdate(existingFileinfo, fileinfoDTO);

                return existingFileinfo;
            })
            .map(fileinfoRepository::save)
            .map(fileinfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileinfoDTO> findAll() {
        log.debug("Request to get all Fileinfos");
        return fileinfoRepository.findAll().stream().map(fileinfoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileinfoDTO> findOne(Long id) {
        log.debug("Request to get Fileinfo : {}", id);
        return fileinfoRepository.findById(id).map(fileinfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fileinfo : {}", id);
        fileinfoRepository.deleteById(id);
    }
}
