package com.atensys.web.rest;

import com.atensys.repository.AttributeRoomRepository;
import com.atensys.service.AttributeRoomService;
import com.atensys.service.dto.AttributeRoomDTO;
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
 * REST controller for managing {@link com.atensys.domain.AttributeRoom}.
 */
@RestController
@RequestMapping("/api")
public class AttributeRoomResource {

    private final Logger log = LoggerFactory.getLogger(AttributeRoomResource.class);

    private static final String ENTITY_NAME = "attributeRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeRoomService attributeRoomService;

    private final AttributeRoomRepository attributeRoomRepository;

    public AttributeRoomResource(AttributeRoomService attributeRoomService, AttributeRoomRepository attributeRoomRepository) {
        this.attributeRoomService = attributeRoomService;
        this.attributeRoomRepository = attributeRoomRepository;
    }

    /**
     * {@code POST  /attribute-rooms} : Create a new attributeRoom.
     *
     * @param attributeRoomDTO the attributeRoomDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeRoomDTO, or with status {@code 400 (Bad Request)} if the attributeRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-rooms")
    public ResponseEntity<AttributeRoomDTO> createAttributeRoom(@RequestBody AttributeRoomDTO attributeRoomDTO) throws URISyntaxException {
        log.debug("REST request to save AttributeRoom : {}", attributeRoomDTO);
        if (attributeRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributeRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributeRoomDTO result = attributeRoomService.save(attributeRoomDTO);
        return ResponseEntity
            .created(new URI("/api/attribute-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-rooms/:id} : Updates an existing attributeRoom.
     *
     * @param id the id of the attributeRoomDTO to save.
     * @param attributeRoomDTO the attributeRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeRoomDTO,
     * or with status {@code 400 (Bad Request)} if the attributeRoomDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributeRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-rooms/{id}")
    public ResponseEntity<AttributeRoomDTO> updateAttributeRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeRoomDTO attributeRoomDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttributeRoom : {}, {}", id, attributeRoomDTO);
        if (attributeRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeRoomDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributeRoomDTO result = attributeRoomService.update(attributeRoomDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-rooms/:id} : Partial updates given fields of an existing attributeRoom, field will ignore if it is null
     *
     * @param id the id of the attributeRoomDTO to save.
     * @param attributeRoomDTO the attributeRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeRoomDTO,
     * or with status {@code 400 (Bad Request)} if the attributeRoomDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attributeRoomDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributeRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-rooms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttributeRoomDTO> partialUpdateAttributeRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeRoomDTO attributeRoomDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributeRoom partially : {}, {}", id, attributeRoomDTO);
        if (attributeRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeRoomDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributeRoomDTO> result = attributeRoomService.partialUpdate(attributeRoomDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeRoomDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-rooms} : get all the attributeRooms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributeRooms in body.
     */
    @GetMapping("/attribute-rooms")
    public List<AttributeRoomDTO> getAllAttributeRooms() {
        log.debug("REST request to get all AttributeRooms");
        return attributeRoomService.findAll();
    }

    /**
     * {@code GET  /attribute-rooms/:id} : get the "id" attributeRoom.
     *
     * @param id the id of the attributeRoomDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeRoomDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-rooms/{id}")
    public ResponseEntity<AttributeRoomDTO> getAttributeRoom(@PathVariable Long id) {
        log.debug("REST request to get AttributeRoom : {}", id);
        Optional<AttributeRoomDTO> attributeRoomDTO = attributeRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributeRoomDTO);
    }

    /**
     * {@code DELETE  /attribute-rooms/:id} : delete the "id" attributeRoom.
     *
     * @param id the id of the attributeRoomDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-rooms/{id}")
    public ResponseEntity<Void> deleteAttributeRoom(@PathVariable Long id) {
        log.debug("REST request to delete AttributeRoom : {}", id);
        attributeRoomService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
