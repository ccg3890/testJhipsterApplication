package com.atensys.web.rest;

import com.atensys.repository.AttributeCompanyRepository;
import com.atensys.service.AttributeCompanyService;
import com.atensys.service.dto.AttributeCompanyDTO;
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
 * REST controller for managing {@link com.atensys.domain.AttributeCompany}.
 */
@RestController
@RequestMapping("/api")
public class AttributeCompanyResource {

    private final Logger log = LoggerFactory.getLogger(AttributeCompanyResource.class);

    private static final String ENTITY_NAME = "attributeCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeCompanyService attributeCompanyService;

    private final AttributeCompanyRepository attributeCompanyRepository;

    public AttributeCompanyResource(
        AttributeCompanyService attributeCompanyService,
        AttributeCompanyRepository attributeCompanyRepository
    ) {
        this.attributeCompanyService = attributeCompanyService;
        this.attributeCompanyRepository = attributeCompanyRepository;
    }

    /**
     * {@code POST  /attribute-companies} : Create a new attributeCompany.
     *
     * @param attributeCompanyDTO the attributeCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeCompanyDTO, or with status {@code 400 (Bad Request)} if the attributeCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-companies")
    public ResponseEntity<AttributeCompanyDTO> createAttributeCompany(@RequestBody AttributeCompanyDTO attributeCompanyDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttributeCompany : {}", attributeCompanyDTO);
        if (attributeCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributeCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributeCompanyDTO result = attributeCompanyService.save(attributeCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/attribute-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-companies/:id} : Updates an existing attributeCompany.
     *
     * @param id the id of the attributeCompanyDTO to save.
     * @param attributeCompanyDTO the attributeCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the attributeCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributeCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-companies/{id}")
    public ResponseEntity<AttributeCompanyDTO> updateAttributeCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeCompanyDTO attributeCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttributeCompany : {}, {}", id, attributeCompanyDTO);
        if (attributeCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributeCompanyDTO result = attributeCompanyService.update(attributeCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-companies/:id} : Partial updates given fields of an existing attributeCompany, field will ignore if it is null
     *
     * @param id the id of the attributeCompanyDTO to save.
     * @param attributeCompanyDTO the attributeCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the attributeCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attributeCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributeCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-companies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttributeCompanyDTO> partialUpdateAttributeCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeCompanyDTO attributeCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributeCompany partially : {}, {}", id, attributeCompanyDTO);
        if (attributeCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributeCompanyDTO> result = attributeCompanyService.partialUpdate(attributeCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-companies} : get all the attributeCompanies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributeCompanies in body.
     */
    @GetMapping("/attribute-companies")
    public List<AttributeCompanyDTO> getAllAttributeCompanies() {
        log.debug("REST request to get all AttributeCompanies");
        return attributeCompanyService.findAll();
    }

    /**
     * {@code GET  /attribute-companies/:id} : get the "id" attributeCompany.
     *
     * @param id the id of the attributeCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-companies/{id}")
    public ResponseEntity<AttributeCompanyDTO> getAttributeCompany(@PathVariable Long id) {
        log.debug("REST request to get AttributeCompany : {}", id);
        Optional<AttributeCompanyDTO> attributeCompanyDTO = attributeCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributeCompanyDTO);
    }

    /**
     * {@code DELETE  /attribute-companies/:id} : delete the "id" attributeCompany.
     *
     * @param id the id of the attributeCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-companies/{id}")
    public ResponseEntity<Void> deleteAttributeCompany(@PathVariable Long id) {
        log.debug("REST request to delete AttributeCompany : {}", id);
        attributeCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
