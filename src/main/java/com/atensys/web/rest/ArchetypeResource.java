package com.atensys.web.rest;

import com.atensys.repository.ArchetypeRepository;
import com.atensys.service.ArchetypeService;
import com.atensys.service.dto.ArchetypeDTO;
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
 * REST controller for managing {@link com.atensys.domain.Archetype}.
 */
@RestController
@RequestMapping("/api")
public class ArchetypeResource {

    private final Logger log = LoggerFactory.getLogger(ArchetypeResource.class);

    private static final String ENTITY_NAME = "archetype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArchetypeService archetypeService;

    private final ArchetypeRepository archetypeRepository;

    public ArchetypeResource(ArchetypeService archetypeService, ArchetypeRepository archetypeRepository) {
        this.archetypeService = archetypeService;
        this.archetypeRepository = archetypeRepository;
    }

    /**
     * {@code POST  /archetypes} : Create a new archetype.
     *
     * @param archetypeDTO the archetypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new archetypeDTO, or with status {@code 400 (Bad Request)} if the archetype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/archetypes")
    public ResponseEntity<ArchetypeDTO> createArchetype(@Valid @RequestBody ArchetypeDTO archetypeDTO) throws URISyntaxException {
        log.debug("REST request to save Archetype : {}", archetypeDTO);
        if (archetypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new archetype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArchetypeDTO result = archetypeService.save(archetypeDTO);
        return ResponseEntity
            .created(new URI("/api/archetypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /archetypes/:id} : Updates an existing archetype.
     *
     * @param id the id of the archetypeDTO to save.
     * @param archetypeDTO the archetypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archetypeDTO,
     * or with status {@code 400 (Bad Request)} if the archetypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the archetypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/archetypes/{id}")
    public ResponseEntity<ArchetypeDTO> updateArchetype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArchetypeDTO archetypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Archetype : {}, {}", id, archetypeDTO);
        if (archetypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archetypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archetypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArchetypeDTO result = archetypeService.update(archetypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, archetypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /archetypes/:id} : Partial updates given fields of an existing archetype, field will ignore if it is null
     *
     * @param id the id of the archetypeDTO to save.
     * @param archetypeDTO the archetypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archetypeDTO,
     * or with status {@code 400 (Bad Request)} if the archetypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the archetypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the archetypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/archetypes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArchetypeDTO> partialUpdateArchetype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArchetypeDTO archetypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Archetype partially : {}, {}", id, archetypeDTO);
        if (archetypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archetypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archetypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArchetypeDTO> result = archetypeService.partialUpdate(archetypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, archetypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /archetypes} : get all the archetypes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of archetypes in body.
     */
    @GetMapping("/archetypes")
    public List<ArchetypeDTO> getAllArchetypes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Archetypes");
        return archetypeService.findAll();
    }

    /**
     * {@code GET  /archetypes/:id} : get the "id" archetype.
     *
     * @param id the id of the archetypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the archetypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/archetypes/{id}")
    public ResponseEntity<ArchetypeDTO> getArchetype(@PathVariable Long id) {
        log.debug("REST request to get Archetype : {}", id);
        Optional<ArchetypeDTO> archetypeDTO = archetypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(archetypeDTO);
    }

    /**
     * {@code DELETE  /archetypes/:id} : delete the "id" archetype.
     *
     * @param id the id of the archetypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/archetypes/{id}")
    public ResponseEntity<Void> deleteArchetype(@PathVariable Long id) {
        log.debug("REST request to delete Archetype : {}", id);
        archetypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
