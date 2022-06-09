package com.atensys.service.impl;

import com.atensys.domain.ShapeAsset;
import com.atensys.repository.ShapeAssetRepository;
import com.atensys.service.ShapeAssetService;
import com.atensys.service.dto.ShapeAssetDTO;
import com.atensys.service.mapper.ShapeAssetMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShapeAsset}.
 */
@Service
@Transactional
public class ShapeAssetServiceImpl implements ShapeAssetService {

    private final Logger log = LoggerFactory.getLogger(ShapeAssetServiceImpl.class);

    private final ShapeAssetRepository shapeAssetRepository;

    private final ShapeAssetMapper shapeAssetMapper;

    public ShapeAssetServiceImpl(ShapeAssetRepository shapeAssetRepository, ShapeAssetMapper shapeAssetMapper) {
        this.shapeAssetRepository = shapeAssetRepository;
        this.shapeAssetMapper = shapeAssetMapper;
    }

    @Override
    public ShapeAssetDTO save(ShapeAssetDTO shapeAssetDTO) {
        log.debug("Request to save ShapeAsset : {}", shapeAssetDTO);
        ShapeAsset shapeAsset = shapeAssetMapper.toEntity(shapeAssetDTO);
        shapeAsset = shapeAssetRepository.save(shapeAsset);
        return shapeAssetMapper.toDto(shapeAsset);
    }

    @Override
    public ShapeAssetDTO update(ShapeAssetDTO shapeAssetDTO) {
        log.debug("Request to save ShapeAsset : {}", shapeAssetDTO);
        ShapeAsset shapeAsset = shapeAssetMapper.toEntity(shapeAssetDTO);
        shapeAsset = shapeAssetRepository.save(shapeAsset);
        return shapeAssetMapper.toDto(shapeAsset);
    }

    @Override
    public Optional<ShapeAssetDTO> partialUpdate(ShapeAssetDTO shapeAssetDTO) {
        log.debug("Request to partially update ShapeAsset : {}", shapeAssetDTO);

        return shapeAssetRepository
            .findById(shapeAssetDTO.getId())
            .map(existingShapeAsset -> {
                shapeAssetMapper.partialUpdate(existingShapeAsset, shapeAssetDTO);

                return existingShapeAsset;
            })
            .map(shapeAssetRepository::save)
            .map(shapeAssetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShapeAssetDTO> findAll() {
        log.debug("Request to get all ShapeAssets");
        return shapeAssetRepository.findAll().stream().map(shapeAssetMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShapeAssetDTO> findOne(Long id) {
        log.debug("Request to get ShapeAsset : {}", id);
        return shapeAssetRepository.findById(id).map(shapeAssetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShapeAsset : {}", id);
        shapeAssetRepository.deleteById(id);
    }
}
