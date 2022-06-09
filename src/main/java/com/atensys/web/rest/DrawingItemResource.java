package com.atensys.web.rest;

import com.atensys.repository.DrawingItemRepository;
import com.atensys.service.DrawingItemService;
import com.atensys.service.dto.DrawingItemDTO;
import com.atensys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.atensys.domain.DrawingItem}.
 */
@RestController
@RequestMapping("/api")
public class DrawingItemResource {

    private final Logger log = LoggerFactory.getLogger(DrawingItemResource.class);

    private static final String ENTITY_NAME = "drawingItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrawingItemService drawingItemService;

    private final DrawingItemRepository drawingItemRepository;

    public DrawingItemResource(DrawingItemService drawingItemService, DrawingItemRepository drawingItemRepository) {
        this.drawingItemService = drawingItemService;
        this.drawingItemRepository = drawingItemRepository;
    }

    /**
     * {@code POST  /drawing-items} : Create a new drawingItem.
     *
     * @param drawingItemDTO the drawingItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drawingItemDTO, or with status {@code 400 (Bad Request)} if the drawingItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drawing-items")
    public ResponseEntity<DrawingItemDTO> createDrawingItem(@Valid @RequestBody DrawingItemDTO drawingItemDTO) throws URISyntaxException {
        log.debug("REST request to save DrawingItem : {}", drawingItemDTO);
        if (drawingItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new drawingItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrawingItemDTO result = drawingItemService.save(drawingItemDTO);
        return ResponseEntity
            .created(new URI("/api/drawing-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drawing-items/:id} : Updates an existing drawingItem.
     *
     * @param id the id of the drawingItemDTO to save.
     * @param drawingItemDTO the drawingItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drawingItemDTO,
     * or with status {@code 400 (Bad Request)} if the drawingItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drawingItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drawing-items/{id}")
    public ResponseEntity<DrawingItemDTO> updateDrawingItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DrawingItemDTO drawingItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DrawingItem : {}, {}", id, drawingItemDTO);
        if (drawingItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drawingItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drawingItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DrawingItemDTO result = drawingItemService.update(drawingItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drawingItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /drawing-items/:id} : Partial updates given fields of an existing drawingItem, field will ignore if it is null
     *
     * @param id the id of the drawingItemDTO to save.
     * @param drawingItemDTO the drawingItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drawingItemDTO,
     * or with status {@code 400 (Bad Request)} if the drawingItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the drawingItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the drawingItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/drawing-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DrawingItemDTO> partialUpdateDrawingItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DrawingItemDTO drawingItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DrawingItem partially : {}, {}", id, drawingItemDTO);
        if (drawingItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drawingItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drawingItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DrawingItemDTO> result = drawingItemService.partialUpdate(drawingItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drawingItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /drawing-items} : get all the drawingItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drawingItems in body.
     */
    @GetMapping("/drawing-items")
    public List<DrawingItemDTO> getAllDrawingItems() {
        log.debug("REST request to get all DrawingItems");
        return drawingItemService.findAll();
    }

    /**
     * {@code GET  /drawing-items/:id} : get the "id" drawingItem.
     *
     * @param id the id of the drawingItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drawingItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drawing-items/{id}")
    public ResponseEntity<DrawingItemDTO> getDrawingItem(@PathVariable Long id) {
        log.debug("REST request to get DrawingItem : {}", id);
        Optional<DrawingItemDTO> drawingItemDTO = drawingItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drawingItemDTO);
    }

    /**
     * {@code DELETE  /drawing-items/:id} : delete the "id" drawingItem.
     *
     * @param id the id of the drawingItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drawing-items/{id}")
    public ResponseEntity<Void> deleteDrawingItem(@PathVariable Long id) {
        log.debug("REST request to delete DrawingItem : {}", id);
        drawingItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
