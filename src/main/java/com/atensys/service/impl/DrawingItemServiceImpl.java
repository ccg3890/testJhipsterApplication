package com.atensys.service.impl;

import com.atensys.domain.DrawingItem;
import com.atensys.repository.DrawingItemRepository;
import com.atensys.service.DrawingItemService;
import com.atensys.service.dto.DrawingItemDTO;
import com.atensys.service.mapper.DrawingItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DrawingItem}.
 */
@Service
@Transactional
public class DrawingItemServiceImpl implements DrawingItemService {

    private final Logger log = LoggerFactory.getLogger(DrawingItemServiceImpl.class);

    private final DrawingItemRepository drawingItemRepository;

    private final DrawingItemMapper drawingItemMapper;

    public DrawingItemServiceImpl(DrawingItemRepository drawingItemRepository, DrawingItemMapper drawingItemMapper) {
        this.drawingItemRepository = drawingItemRepository;
        this.drawingItemMapper = drawingItemMapper;
    }

    @Override
    public DrawingItemDTO save(DrawingItemDTO drawingItemDTO) {
        log.debug("Request to save DrawingItem : {}", drawingItemDTO);
        DrawingItem drawingItem = drawingItemMapper.toEntity(drawingItemDTO);
        drawingItem = drawingItemRepository.save(drawingItem);
        return drawingItemMapper.toDto(drawingItem);
    }

    @Override
    public DrawingItemDTO update(DrawingItemDTO drawingItemDTO) {
        log.debug("Request to save DrawingItem : {}", drawingItemDTO);
        DrawingItem drawingItem = drawingItemMapper.toEntity(drawingItemDTO);
        drawingItem = drawingItemRepository.save(drawingItem);
        return drawingItemMapper.toDto(drawingItem);
    }

    @Override
    public Optional<DrawingItemDTO> partialUpdate(DrawingItemDTO drawingItemDTO) {
        log.debug("Request to partially update DrawingItem : {}", drawingItemDTO);

        return drawingItemRepository
            .findById(drawingItemDTO.getId())
            .map(existingDrawingItem -> {
                drawingItemMapper.partialUpdate(existingDrawingItem, drawingItemDTO);

                return existingDrawingItem;
            })
            .map(drawingItemRepository::save)
            .map(drawingItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DrawingItemDTO> findAll() {
        log.debug("Request to get all DrawingItems");
        return drawingItemRepository.findAll().stream().map(drawingItemMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DrawingItemDTO> findOne(Long id) {
        log.debug("Request to get DrawingItem : {}", id);
        return drawingItemRepository.findById(id).map(drawingItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DrawingItem : {}", id);
        drawingItemRepository.deleteById(id);
    }
}
