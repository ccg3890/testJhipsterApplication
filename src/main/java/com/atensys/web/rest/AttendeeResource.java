package com.atensys.web.rest;

import com.atensys.repository.AttendeeRepository;
import com.atensys.service.AttendeeService;
import com.atensys.service.dto.AttendeeDTO;
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
 * REST controller for managing {@link com.atensys.domain.Attendee}.
 */
@RestController
@RequestMapping("/api")
public class AttendeeResource {

    private final Logger log = LoggerFactory.getLogger(AttendeeResource.class);

    private static final String ENTITY_NAME = "attendee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendeeService attendeeService;

    private final AttendeeRepository attendeeRepository;

    public AttendeeResource(AttendeeService attendeeService, AttendeeRepository attendeeRepository) {
        this.attendeeService = attendeeService;
        this.attendeeRepository = attendeeRepository;
    }

    /**
     * {@code POST  /attendees} : Create a new attendee.
     *
     * @param attendeeDTO the attendeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendeeDTO, or with status {@code 400 (Bad Request)} if the attendee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendees")
    public ResponseEntity<AttendeeDTO> createAttendee(@Valid @RequestBody AttendeeDTO attendeeDTO) throws URISyntaxException {
        log.debug("REST request to save Attendee : {}", attendeeDTO);
        if (attendeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendeeDTO result = attendeeService.save(attendeeDTO);
        return ResponseEntity
            .created(new URI("/api/attendees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendees/:id} : Updates an existing attendee.
     *
     * @param id the id of the attendeeDTO to save.
     * @param attendeeDTO the attendeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendeeDTO,
     * or with status {@code 400 (Bad Request)} if the attendeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendees/{id}")
    public ResponseEntity<AttendeeDTO> updateAttendee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttendeeDTO attendeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Attendee : {}, {}", id, attendeeDTO);
        if (attendeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttendeeDTO result = attendeeService.update(attendeeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attendees/:id} : Partial updates given fields of an existing attendee, field will ignore if it is null
     *
     * @param id the id of the attendeeDTO to save.
     * @param attendeeDTO the attendeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendeeDTO,
     * or with status {@code 400 (Bad Request)} if the attendeeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attendeeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attendeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attendees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttendeeDTO> partialUpdateAttendee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttendeeDTO attendeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attendee partially : {}, {}", id, attendeeDTO);
        if (attendeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttendeeDTO> result = attendeeService.partialUpdate(attendeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendeeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attendees} : get all the attendees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendees in body.
     */
    @GetMapping("/attendees")
    public List<AttendeeDTO> getAllAttendees() {
        log.debug("REST request to get all Attendees");
        return attendeeService.findAll();
    }

    /**
     * {@code GET  /attendees/:id} : get the "id" attendee.
     *
     * @param id the id of the attendeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeDTO> getAttendee(@PathVariable Long id) {
        log.debug("REST request to get Attendee : {}", id);
        Optional<AttendeeDTO> attendeeDTO = attendeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendeeDTO);
    }

    /**
     * {@code DELETE  /attendees/:id} : delete the "id" attendee.
     *
     * @param id the id of the attendeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendees/{id}")
    public ResponseEntity<Void> deleteAttendee(@PathVariable Long id) {
        log.debug("REST request to delete Attendee : {}", id);
        attendeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
