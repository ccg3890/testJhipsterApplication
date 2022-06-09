package com.atensys.service.impl;

import com.atensys.domain.Shape;
import com.atensys.repository.ShapeRepository;
import com.atensys.service.ShapeService;
import com.atensys.service.dto.ShapeDTO;
import com.atensys.service.mapper.ShapeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Shape}.
 */
@Service
@Transactional
public class ShapeServiceImpl implements ShapeService {

    private final Logger log = LoggerFactory.getLogger(ShapeServiceImpl.class);

    private final ShapeRepository shapeRepository;

    private final ShapeMapper shapeMapper;

    public ShapeServiceImpl(ShapeRepository shapeRepository, ShapeMapper shapeMapper) {
        this.shapeRepository = shapeRepository;
        this.shapeMapper = shapeMapper;
    }

    @Override
    public ShapeDTO save(ShapeDTO shapeDTO) {
        log.debug("Request to save Shape : {}", shapeDTO);
        Shape shape = shapeMapper.toEntity(shapeDTO);
        shape = shapeRepository.save(shape);
        return shapeMapper.toDto(shape);
    }

    @Override
    public ShapeDTO update(ShapeDTO shapeDTO) {
        log.debug("Request to save Shape : {}", shapeDTO);
        Shape shape = shapeMapper.toEntity(shapeDTO);
        shape = shapeRepository.save(shape);
        return shapeMapper.toDto(shape);
    }

    @Override
    public Optional<ShapeDTO> partialUpdate(ShapeDTO shapeDTO) {
        log.debug("Request to partially update Shape : {}", shapeDTO);

        return shapeRepository
            .findById(shapeDTO.getId())
            .map(existingShape -> {
                shapeMapper.partialUpdate(existingShape, shapeDTO);

                return existingShape;
            })
            .map(shapeRepository::save)
            .map(shapeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShapeDTO> findAll() {
        log.debug("Request to get all Shapes");
        return shapeRepository.findAll().stream().map(shapeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShapeDTO> findOne(Long id) {
        log.debug("Request to get Shape : {}", id);
        return shapeRepository.findById(id).map(shapeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shape : {}", id);
        shapeRepository.deleteById(id);
    }
}
