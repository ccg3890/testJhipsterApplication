package com.atensys.web.rest;

import com.atensys.repository.AttributeSeatRepository;
import com.atensys.service.AttributeSeatService;
import com.atensys.service.dto.AttributeSeatDTO;
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
 * REST controller for managing {@link com.atensys.domain.AttributeSeat}.
 */
@RestController
@RequestMapping("/api")
public class AttributeSeatResource {

    private final Logger log = LoggerFactory.getLogger(AttributeSeatResource.class);

    private static final String ENTITY_NAME = "attributeSeat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeSeatService attributeSeatService;

    private final AttributeSeatRepository attributeSeatRepository;

    public AttributeSeatResource(AttributeSeatService attributeSeatService, AttributeSeatRepository attributeSeatRepository) {
        this.attributeSeatService = attributeSeatService;
        this.attributeSeatRepository = attributeSeatRepository;
    }

    /**
     * {@code POST  /attribute-seats} : Create a new attributeSeat.
     *
     * @param attributeSeatDTO the attributeSeatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeSeatDTO, or with status {@code 400 (Bad Request)} if the attributeSeat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-seats")
    public ResponseEntity<AttributeSeatDTO> createAttributeSeat(@RequestBody AttributeSeatDTO attributeSeatDTO) throws URISyntaxException {
        log.debug("REST request to save AttributeSeat : {}", attributeSeatDTO);
        if (attributeSeatDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributeSeat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributeSeatDTO result = attributeSeatService.save(attributeSeatDTO);
        return ResponseEntity
            .created(new URI("/api/attribute-seats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-seats/:id} : Updates an existing attributeSeat.
     *
     * @param id the id of the attributeSeatDTO to save.
     * @param attributeSeatDTO the attributeSeatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeSeatDTO,
     * or with status {@code 400 (Bad Request)} if the attributeSeatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributeSeatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-seats/{id}")
    public ResponseEntity<AttributeSeatDTO> updateAttributeSeat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeSeatDTO attributeSeatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttributeSeat : {}, {}", id, attributeSeatDTO);
        if (attributeSeatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeSeatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeSeatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributeSeatDTO result = attributeSeatService.update(attributeSeatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeSeatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-seats/:id} : Partial updates given fields of an existing attributeSeat, field will ignore if it is null
     *
     * @param id the id of the attributeSeatDTO to save.
     * @param attributeSeatDTO the attributeSeatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeSeatDTO,
     * or with status {@code 400 (Bad Request)} if the attributeSeatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attributeSeatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributeSeatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-seats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttributeSeatDTO> partialUpdateAttributeSeat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeSeatDTO attributeSeatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributeSeat partially : {}, {}", id, attributeSeatDTO);
        if (attributeSeatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeSeatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeSeatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributeSeatDTO> result = attributeSeatService.partialUpdate(attributeSeatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeSeatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-seats} : get all the attributeSeats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributeSeats in body.
     */
    @GetMapping("/attribute-seats")
    public List<AttributeSeatDTO> getAllAttributeSeats() {
        log.debug("REST request to get all AttributeSeats");
        return attributeSeatService.findAll();
    }

    /**
     * {@code GET  /attribute-seats/:id} : get the "id" attributeSeat.
     *
     * @param id the id of the attributeSeatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeSeatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-seats/{id}")
    public ResponseEntity<AttributeSeatDTO> getAttributeSeat(@PathVariable Long id) {
        log.debug("REST request to get AttributeSeat : {}", id);
        Optional<AttributeSeatDTO> attributeSeatDTO = attributeSeatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributeSeatDTO);
    }

    /**
     * {@code DELETE  /attribute-seats/:id} : delete the "id" attributeSeat.
     *
     * @param id the id of the attributeSeatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-seats/{id}")
    public ResponseEntity<Void> deleteAttributeSeat(@PathVariable Long id) {
        log.debug("REST request to delete AttributeSeat : {}", id);
        attributeSeatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
