package com.atensys.web.rest;

import com.atensys.repository.DrawingRepository;
import com.atensys.service.DrawingService;
import com.atensys.service.dto.DrawingDTO;
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
 * REST controller for managing {@link com.atensys.domain.Drawing}.
 */
@RestController
@RequestMapping("/api")
public class DrawingResource {

    private final Logger log = LoggerFactory.getLogger(DrawingResource.class);

    private static final String ENTITY_NAME = "drawing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrawingService drawingService;

    private final DrawingRepository drawingRepository;

    public DrawingResource(DrawingService drawingService, DrawingRepository drawingRepository) {
        this.drawingService = drawingService;
        this.drawingRepository = drawingRepository;
    }

    /**
     * {@code POST  /drawings} : Create a new drawing.
     *
     * @param drawingDTO the drawingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drawingDTO, or with status {@code 400 (Bad Request)} if the drawing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drawings")
    public ResponseEntity<DrawingDTO> createDrawing(@Valid @RequestBody DrawingDTO drawingDTO) throws URISyntaxException {
        log.debug("REST request to save Drawing : {}", drawingDTO);
        if (drawingDTO.getId() != null) {
            throw new BadRequestAlertException("A new drawing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrawingDTO result = drawingService.save(drawingDTO);
        return ResponseEntity
            .created(new URI("/api/drawings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drawings/:id} : Updates an existing drawing.
     *
     * @param id the id of the drawingDTO to save.
     * @param drawingDTO the drawingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drawingDTO,
     * or with status {@code 400 (Bad Request)} if the drawingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drawingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drawings/{id}")
    public ResponseEntity<DrawingDTO> updateDrawing(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DrawingDTO drawingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Drawing : {}, {}", id, drawingDTO);
        if (drawingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drawingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drawingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DrawingDTO result = drawingService.update(drawingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drawingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /drawings/:id} : Partial updates given fields of an existing drawing, field will ignore if it is null
     *
     * @param id the id of the drawingDTO to save.
     * @param drawingDTO the drawingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drawingDTO,
     * or with status {@code 400 (Bad Request)} if the drawingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the drawingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the drawingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/drawings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DrawingDTO> partialUpdateDrawing(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DrawingDTO drawingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Drawing partially : {}, {}", id, drawingDTO);
        if (drawingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drawingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drawingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DrawingDTO> result = drawingService.partialUpdate(drawingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drawingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /drawings} : get all the drawings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drawings in body.
     */
    @GetMapping("/drawings")
    public List<DrawingDTO> getAllDrawings() {
        log.debug("REST request to get all Drawings");
        return drawingService.findAll();
    }

    /**
     * {@code GET  /drawings/:id} : get the "id" drawing.
     *
     * @param id the id of the drawingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drawingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drawings/{id}")
    public ResponseEntity<DrawingDTO> getDrawing(@PathVariable Long id) {
        log.debug("REST request to get Drawing : {}", id);
        Optional<DrawingDTO> drawingDTO = drawingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drawingDTO);
    }

    /**
     * {@code DELETE  /drawings/:id} : delete the "id" drawing.
     *
     * @param id the id of the drawingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drawings/{id}")
    public ResponseEntity<Void> deleteDrawing(@PathVariable Long id) {
        log.debug("REST request to delete Drawing : {}", id);
        drawingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
