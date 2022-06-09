package com.atensys.web.rest;

import com.atensys.repository.AttributeOfficeRepository;
import com.atensys.service.AttributeOfficeService;
import com.atensys.service.dto.AttributeOfficeDTO;
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
 * REST controller for managing {@link com.atensys.domain.AttributeOffice}.
 */
@RestController
@RequestMapping("/api")
public class AttributeOfficeResource {

    private final Logger log = LoggerFactory.getLogger(AttributeOfficeResource.class);

    private static final String ENTITY_NAME = "attributeOffice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeOfficeService attributeOfficeService;

    private final AttributeOfficeRepository attributeOfficeRepository;

    public AttributeOfficeResource(AttributeOfficeService attributeOfficeService, AttributeOfficeRepository attributeOfficeRepository) {
        this.attributeOfficeService = attributeOfficeService;
        this.attributeOfficeRepository = attributeOfficeRepository;
    }

    /**
     * {@code POST  /attribute-offices} : Create a new attributeOffice.
     *
     * @param attributeOfficeDTO the attributeOfficeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeOfficeDTO, or with status {@code 400 (Bad Request)} if the attributeOffice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-offices")
    public ResponseEntity<AttributeOfficeDTO> createAttributeOffice(@RequestBody AttributeOfficeDTO attributeOfficeDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttributeOffice : {}", attributeOfficeDTO);
        if (attributeOfficeDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributeOffice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributeOfficeDTO result = attributeOfficeService.save(attributeOfficeDTO);
        return ResponseEntity
            .created(new URI("/api/attribute-offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-offices/:id} : Updates an existing attributeOffice.
     *
     * @param id the id of the attributeOfficeDTO to save.
     * @param attributeOfficeDTO the attributeOfficeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeOfficeDTO,
     * or with status {@code 400 (Bad Request)} if the attributeOfficeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributeOfficeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-offices/{id}")
    public ResponseEntity<AttributeOfficeDTO> updateAttributeOffice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeOfficeDTO attributeOfficeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttributeOffice : {}, {}", id, attributeOfficeDTO);
        if (attributeOfficeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeOfficeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeOfficeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributeOfficeDTO result = attributeOfficeService.update(attributeOfficeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeOfficeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-offices/:id} : Partial updates given fields of an existing attributeOffice, field will ignore if it is null
     *
     * @param id the id of the attributeOfficeDTO to save.
     * @param attributeOfficeDTO the attributeOfficeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeOfficeDTO,
     * or with status {@code 400 (Bad Request)} if the attributeOfficeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attributeOfficeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributeOfficeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-offices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttributeOfficeDTO> partialUpdateAttributeOffice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeOfficeDTO attributeOfficeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributeOffice partially : {}, {}", id, attributeOfficeDTO);
        if (attributeOfficeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeOfficeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeOfficeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributeOfficeDTO> result = attributeOfficeService.partialUpdate(attributeOfficeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeOfficeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-offices} : get all the attributeOffices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributeOffices in body.
     */
    @GetMapping("/attribute-offices")
    public List<AttributeOfficeDTO> getAllAttributeOffices() {
        log.debug("REST request to get all AttributeOffices");
        return attributeOfficeService.findAll();
    }

    /**
     * {@code GET  /attribute-offices/:id} : get the "id" attributeOffice.
     *
     * @param id the id of the attributeOfficeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeOfficeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-offices/{id}")
    public ResponseEntity<AttributeOfficeDTO> getAttributeOffice(@PathVariable Long id) {
        log.debug("REST request to get AttributeOffice : {}", id);
        Optional<AttributeOfficeDTO> attributeOfficeDTO = attributeOfficeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributeOfficeDTO);
    }

    /**
     * {@code DELETE  /attribute-offices/:id} : delete the "id" attributeOffice.
     *
     * @param id the id of the attributeOfficeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-offices/{id}")
    public ResponseEntity<Void> deleteAttributeOffice(@PathVariable Long id) {
        log.debug("REST request to delete AttributeOffice : {}", id);
        attributeOfficeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
