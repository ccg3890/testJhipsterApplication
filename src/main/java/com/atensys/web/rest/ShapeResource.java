package com.atensys.web.rest;

import com.atensys.repository.ShapeRepository;
import com.atensys.service.ShapeService;
import com.atensys.service.dto.ShapeDTO;
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
 * REST controller for managing {@link com.atensys.domain.Shape}.
 */
@RestController
@RequestMapping("/api")
public class ShapeResource {

    private final Logger log = LoggerFactory.getLogger(ShapeResource.class);

    private static final String ENTITY_NAME = "shape";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShapeService shapeService;

    private final ShapeRepository shapeRepository;

    public ShapeResource(ShapeService shapeService, ShapeRepository shapeRepository) {
        this.shapeService = shapeService;
        this.shapeRepository = shapeRepository;
    }

    /**
     * {@code POST  /shapes} : Create a new shape.
     *
     * @param shapeDTO the shapeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shapeDTO, or with status {@code 400 (Bad Request)} if the shape has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shapes")
    public ResponseEntity<ShapeDTO> createShape(@Valid @RequestBody ShapeDTO shapeDTO) throws URISyntaxException {
        log.debug("REST request to save Shape : {}", shapeDTO);
        if (shapeDTO.getId() != null) {
            throw new BadRequestAlertException("A new shape cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShapeDTO result = shapeService.save(shapeDTO);
        return ResponseEntity
            .created(new URI("/api/shapes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shapes/:id} : Updates an existing shape.
     *
     * @param id the id of the shapeDTO to save.
     * @param shapeDTO the shapeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shapeDTO,
     * or with status {@code 400 (Bad Request)} if the shapeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shapeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shapes/{id}")
    public ResponseEntity<ShapeDTO> updateShape(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShapeDTO shapeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Shape : {}, {}", id, shapeDTO);
        if (shapeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shapeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shapeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShapeDTO result = shapeService.update(shapeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shapeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shapes/:id} : Partial updates given fields of an existing shape, field will ignore if it is null
     *
     * @param id the id of the shapeDTO to save.
     * @param shapeDTO the shapeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shapeDTO,
     * or with status {@code 400 (Bad Request)} if the shapeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shapeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shapeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shapes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShapeDTO> partialUpdateShape(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShapeDTO shapeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Shape partially : {}, {}", id, shapeDTO);
        if (shapeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shapeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shapeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShapeDTO> result = shapeService.partialUpdate(shapeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shapeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shapes} : get all the shapes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shapes in body.
     */
    @GetMapping("/shapes")
    public List<ShapeDTO> getAllShapes() {
        log.debug("REST request to get all Shapes");
        return shapeService.findAll();
    }

    /**
     * {@code GET  /shapes/:id} : get the "id" shape.
     *
     * @param id the id of the shapeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shapeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shapes/{id}")
    public ResponseEntity<ShapeDTO> getShape(@PathVariable Long id) {
        log.debug("REST request to get Shape : {}", id);
        Optional<ShapeDTO> shapeDTO = shapeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shapeDTO);
    }

    /**
     * {@code DELETE  /shapes/:id} : delete the "id" shape.
     *
     * @param id the id of the shapeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shapes/{id}")
    public ResponseEntity<Void> deleteShape(@PathVariable Long id) {
        log.debug("REST request to delete Shape : {}", id);
        shapeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
