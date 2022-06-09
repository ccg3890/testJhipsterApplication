package com.atensys.web.rest;

import com.atensys.repository.FloorRepository;
import com.atensys.service.FloorService;
import com.atensys.service.dto.FloorDTO;
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
 * REST controller for managing {@link com.atensys.domain.Floor}.
 */
@RestController
@RequestMapping("/api")
public class FloorResource {

    private final Logger log = LoggerFactory.getLogger(FloorResource.class);

    private static final String ENTITY_NAME = "floor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FloorService floorService;

    private final FloorRepository floorRepository;

    public FloorResource(FloorService floorService, FloorRepository floorRepository) {
        this.floorService = floorService;
        this.floorRepository = floorRepository;
    }

    /**
     * {@code POST  /floors} : Create a new floor.
     *
     * @param floorDTO the floorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new floorDTO, or with status {@code 400 (Bad Request)} if the floor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/floors")
    public ResponseEntity<FloorDTO> createFloor(@Valid @RequestBody FloorDTO floorDTO) throws URISyntaxException {
        log.debug("REST request to save Floor : {}", floorDTO);
        if (floorDTO.getId() != null) {
            throw new BadRequestAlertException("A new floor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FloorDTO result = floorService.save(floorDTO);
        return ResponseEntity
            .created(new URI("/api/floors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /floors/:id} : Updates an existing floor.
     *
     * @param id the id of the floorDTO to save.
     * @param floorDTO the floorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floorDTO,
     * or with status {@code 400 (Bad Request)} if the floorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the floorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/floors/{id}")
    public ResponseEntity<FloorDTO> updateFloor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FloorDTO floorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Floor : {}, {}", id, floorDTO);
        if (floorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FloorDTO result = floorService.update(floorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, floorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /floors/:id} : Partial updates given fields of an existing floor, field will ignore if it is null
     *
     * @param id the id of the floorDTO to save.
     * @param floorDTO the floorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floorDTO,
     * or with status {@code 400 (Bad Request)} if the floorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the floorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the floorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/floors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FloorDTO> partialUpdateFloor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FloorDTO floorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Floor partially : {}, {}", id, floorDTO);
        if (floorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FloorDTO> result = floorService.partialUpdate(floorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, floorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /floors} : get all the floors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of floors in body.
     */
    @GetMapping("/floors")
    public List<FloorDTO> getAllFloors() {
        log.debug("REST request to get all Floors");
        return floorService.findAll();
    }

    /**
     * {@code GET  /floors/:id} : get the "id" floor.
     *
     * @param id the id of the floorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the floorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/floors/{id}")
    public ResponseEntity<FloorDTO> getFloor(@PathVariable Long id) {
        log.debug("REST request to get Floor : {}", id);
        Optional<FloorDTO> floorDTO = floorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(floorDTO);
    }

    /**
     * {@code DELETE  /floors/:id} : delete the "id" floor.
     *
     * @param id the id of the floorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/floors/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
        log.debug("REST request to delete Floor : {}", id);
        floorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
