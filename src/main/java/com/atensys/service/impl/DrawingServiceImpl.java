package com.atensys.service.impl;

import com.atensys.domain.Drawing;
import com.atensys.repository.DrawingRepository;
import com.atensys.service.DrawingService;
import com.atensys.service.dto.DrawingDTO;
import com.atensys.service.mapper.DrawingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Drawing}.
 */
@Service
@Transactional
public class DrawingServiceImpl implements DrawingService {

    private final Logger log = LoggerFactory.getLogger(DrawingServiceImpl.class);

    private final DrawingRepository drawingRepository;

    private final DrawingMapper drawingMapper;

    public DrawingServiceImpl(DrawingRepository drawingRepository, DrawingMapper drawingMapper) {
        this.drawingRepository = drawingRepository;
        this.drawingMapper = drawingMapper;
    }

    @Override
    public DrawingDTO save(DrawingDTO drawingDTO) {
        log.debug("Request to save Drawing : {}", drawingDTO);
        Drawing drawing = drawingMapper.toEntity(drawingDTO);
        drawing = drawingRepository.save(drawing);
        return drawingMapper.toDto(drawing);
    }

    @Override
    public DrawingDTO update(DrawingDTO drawingDTO) {
        log.debug("Request to save Drawing : {}", drawingDTO);
        Drawing drawing = drawingMapper.toEntity(drawingDTO);
        drawing = drawingRepository.save(drawing);
        return drawingMapper.toDto(drawing);
    }

    @Override
    public Optional<DrawingDTO> partialUpdate(DrawingDTO drawingDTO) {
        log.debug("Request to partially update Drawing : {}", drawingDTO);

        return drawingRepository
            .findById(drawingDTO.getId())
            .map(existingDrawing -> {
                drawingMapper.partialUpdate(existingDrawing, drawingDTO);

                return existingDrawing;
            })
            .map(drawingRepository::save)
            .map(drawingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DrawingDTO> findAll() {
        log.debug("Request to get all Drawings");
        return drawingRepository.findAll().stream().map(drawingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DrawingDTO> findOne(Long id) {
        log.debug("Request to get Drawing : {}", id);
        return drawingRepository.findById(id).map(drawingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Drawing : {}", id);
        drawingRepository.deleteById(id);
    }
}
