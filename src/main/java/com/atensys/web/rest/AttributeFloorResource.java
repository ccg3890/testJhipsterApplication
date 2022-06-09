package com.atensys.web.rest;

import com.atensys.repository.AttributeFloorRepository;
import com.atensys.service.AttributeFloorService;
import com.atensys.service.dto.AttributeFloorDTO;
import com.atensys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.atensys.domain.AttributeFloor}.
 */
@RestController
@RequestMapping("/api")
public class AttributeFloorResource {

    private final Logger log = LoggerFactory.getLogger(AttributeFloorResource.class);

    private static final String ENTITY_NAME = "attributeFloor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeFloorService attributeFloorService;

    private final AttributeFloorRepository attributeFloorRepository;

    public AttributeFloorResource(AttributeFloorService attributeFloorService, AttributeFloorRepository attributeFloorRepository) {
        this.attributeFloorService = attributeFloorService;
        this.attributeFloorRepository = attributeFloorRepository;
    }

    /**
     * {@code POST  /attribute-floors} : Create a new attributeFloor.
     *
     * @param attributeFloorDTO the attributeFloorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeFloorDTO, or with status {@code 400 (Bad Request)} if the attributeFloor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-floors")
    public ResponseEntity<AttributeFloorDTO> createAttributeFloor(@RequestBody AttributeFloorDTO attributeFloorDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttributeFloor : {}", attributeFloorDTO);
        if (attributeFloorDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributeFloor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributeFloorDTO result = attributeFloorService.save(attributeFloorDTO);
        return ResponseEntity
            .created(new URI("/api/attribute-floors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-floors/:id} : Updates an existing attributeFloor.
     *
     * @param id the id of the attributeFloorDTO to save.
     * @param attributeFloorDTO the attributeFloorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeFloorDTO,
     * or with status {@code 400 (Bad Request)} if the attributeFloorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributeFloorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-floors/{id}")
    public ResponseEntity<AttributeFloorDTO> updateAttributeFloor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeFloorDTO attributeFloorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttributeFloor : {}, {}", id, attributeFloorDTO);
        if (attributeFloorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeFloorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeFloorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributeFloorDTO result = attributeFloorService.update(attributeFloorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeFloorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-floors/:id} : Partial updates given fields of an existing attributeFloor, field will ignore if it is null
     *
     * @param id the id of the attributeFloorDTO to save.
     * @param attributeFloorDTO the attributeFloorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeFloorDTO,
     * or with status {@code 400 (Bad Request)} if the attributeFloorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attributeFloorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributeFloorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-floors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttributeFloorDTO> partialUpdateAttributeFloor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeFloorDTO attributeFloorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributeFloor partially : {}, {}", id, attributeFloorDTO);
        if (attributeFloorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeFloorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeFloorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributeFloorDTO> result = attributeFloorService.partialUpdate(attributeFloorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeFloorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-floors} : get all the attributeFloors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributeFloors in body.
     */
    @GetMapping("/attribute-floors")
    public List<AttributeFloorDTO> getAllAttributeFloors() {
        log.debug("REST request to get all AttributeFloors");
        return attributeFloorService.findAll();
    }

    /**
     * {@code GET  /attribute-floors/:id} : get the "id" attributeFloor.
     *
     * @param id the id of the attributeFloorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeFloorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-floors/{id}")
    public ResponseEntity<AttributeFloorDTO> getAttributeFloor(@PathVariable Long id) {
        log.debug("REST request to get AttributeFloor : {}", id);
        Optional<AttributeFloorDTO> attributeFloorDTO = attributeFloorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributeFloorDTO);
    }

    /**
     * {@code DELETE  /attribute-floors/:id} : delete the "id" attributeFloor.
     *
     * @param id the id of the attributeFloorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-floors/{id}")
    public ResponseEntity<Void> deleteAttributeFloor(@PathVariable Long id) {
        log.debug("REST request to delete AttributeFloor : {}", id);
        attributeFloorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
