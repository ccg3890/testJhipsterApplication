package com.atensys.web.rest;

import com.atensys.repository.ShapeAssetRepository;
import com.atensys.service.ShapeAssetService;
import com.atensys.service.dto.ShapeAssetDTO;
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
 * REST controller for managing {@link com.atensys.domain.ShapeAsset}.
 */
@RestController
@RequestMapping("/api")
public class ShapeAssetResource {

    private final Logger log = LoggerFactory.getLogger(ShapeAssetResource.class);

    private static final String ENTITY_NAME = "shapeAsset";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShapeAssetService shapeAssetService;

    private final ShapeAssetRepository shapeAssetRepository;

    public ShapeAssetResource(ShapeAssetService shapeAssetService, ShapeAssetRepository shapeAssetRepository) {
        this.shapeAssetService = shapeAssetService;
        this.shapeAssetRepository = shapeAssetRepository;
    }

    /**
     * {@code POST  /shape-assets} : Create a new shapeAsset.
     *
     * @param shapeAssetDTO the shapeAssetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shapeAssetDTO, or with status {@code 400 (Bad Request)} if the shapeAsset has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shape-assets")
    public ResponseEntity<ShapeAssetDTO> createShapeAsset(@Valid @RequestBody ShapeAssetDTO shapeAssetDTO) throws URISyntaxException {
        log.debug("REST request to save ShapeAsset : {}", shapeAssetDTO);
        if (shapeAssetDTO.getId() != null) {
            throw new BadRequestAlertException("A new shapeAsset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShapeAssetDTO result = shapeAssetService.save(shapeAssetDTO);
        return ResponseEntity
            .created(new URI("/api/shape-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shape-assets/:id} : Updates an existing shapeAsset.
     *
     * @param id the id of the shapeAssetDTO to save.
     * @param shapeAssetDTO the shapeAssetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shapeAssetDTO,
     * or with status {@code 400 (Bad Request)} if the shapeAssetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shapeAssetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shape-assets/{id}")
    public ResponseEntity<ShapeAssetDTO> updateShapeAsset(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShapeAssetDTO shapeAssetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShapeAsset : {}, {}", id, shapeAssetDTO);
        if (shapeAssetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shapeAssetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shapeAssetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShapeAssetDTO result = shapeAssetService.update(shapeAssetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shapeAssetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shape-assets/:id} : Partial updates given fields of an existing shapeAsset, field will ignore if it is null
     *
     * @param id the id of the shapeAssetDTO to save.
     * @param shapeAssetDTO the shapeAssetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shapeAssetDTO,
     * or with status {@code 400 (Bad Request)} if the shapeAssetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shapeAssetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shapeAssetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shape-assets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShapeAssetDTO> partialUpdateShapeAsset(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShapeAssetDTO shapeAssetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShapeAsset partially : {}, {}", id, shapeAssetDTO);
        if (shapeAssetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shapeAssetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shapeAssetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShapeAssetDTO> result = shapeAssetService.partialUpdate(shapeAssetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shapeAssetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shape-assets} : get all the shapeAssets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shapeAssets in body.
     */
    @GetMapping("/shape-assets")
    public List<ShapeAssetDTO> getAllShapeAssets() {
        log.debug("REST request to get all ShapeAssets");
        return shapeAssetService.findAll();
    }

    /**
     * {@code GET  /shape-assets/:id} : get the "id" shapeAsset.
     *
     * @param id the id of the shapeAssetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shapeAssetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shape-assets/{id}")
    public ResponseEntity<ShapeAssetDTO> getShapeAsset(@PathVariable Long id) {
        log.debug("REST request to get ShapeAsset : {}", id);
        Optional<ShapeAssetDTO> shapeAssetDTO = shapeAssetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shapeAssetDTO);
    }

    /**
     * {@code DELETE  /shape-assets/:id} : delete the "id" shapeAsset.
     *
     * @param id the id of the shapeAssetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shape-assets/{id}")
    public ResponseEntity<Void> deleteShapeAsset(@PathVariable Long id) {
        log.debug("REST request to delete ShapeAsset : {}", id);
        shapeAssetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
