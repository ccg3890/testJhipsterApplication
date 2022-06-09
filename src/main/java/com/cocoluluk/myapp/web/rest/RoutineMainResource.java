package com.cocoluluk.myapp.web.rest;

import com.cocoluluk.myapp.domain.RoutineMain;
import com.cocoluluk.myapp.repository.RoutineMainRepository;
import com.cocoluluk.myapp.web.rest.errors.BadRequestAlertException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cocoluluk.myapp.domain.RoutineMain}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RoutineMainResource {

    private final Logger log = LoggerFactory.getLogger(RoutineMainResource.class);

    private static final String ENTITY_NAME = "routineMain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoutineMainRepository routineMainRepository;

    public RoutineMainResource(RoutineMainRepository routineMainRepository) {
        this.routineMainRepository = routineMainRepository;
    }

    /**
     * {@code POST  /routine-mains} : Create a new routineMain.
     *
     * @param routineMain the routineMain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new routineMain, or with status {@code 400 (Bad Request)} if the routineMain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/routine-mains")
    public ResponseEntity<RoutineMain> createRoutineMain(@Valid @RequestBody RoutineMain routineMain) throws URISyntaxException {
        log.debug("REST request to save RoutineMain : {}", routineMain);
        if (routineMain.getId() != null) {
            throw new BadRequestAlertException("A new routineMain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoutineMain result = routineMainRepository.save(routineMain);
        return ResponseEntity
            .created(new URI("/api/routine-mains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /routine-mains/:id} : Updates an existing routineMain.
     *
     * @param id the id of the routineMain to save.
     * @param routineMain the routineMain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated routineMain,
     * or with status {@code 400 (Bad Request)} if the routineMain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the routineMain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/routine-mains/{id}")
    public ResponseEntity<RoutineMain> updateRoutineMain(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody RoutineMain routineMain
    ) throws URISyntaxException {
        log.debug("REST request to update RoutineMain : {}, {}", id, routineMain);
        if (routineMain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, routineMain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!routineMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        routineMain.setIsPersisted();
        RoutineMain result = routineMainRepository.save(routineMain);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, routineMain.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /routine-mains/:id} : Partial updates given fields of an existing routineMain, field will ignore if it is null
     *
     * @param id the id of the routineMain to save.
     * @param routineMain the routineMain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated routineMain,
     * or with status {@code 400 (Bad Request)} if the routineMain is not valid,
     * or with status {@code 404 (Not Found)} if the routineMain is not found,
     * or with status {@code 500 (Internal Server Error)} if the routineMain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/routine-mains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoutineMain> partialUpdateRoutineMain(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody RoutineMain routineMain
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoutineMain partially : {}, {}", id, routineMain);
        if (routineMain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, routineMain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!routineMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoutineMain> result = routineMainRepository
            .findById(routineMain.getId())
            .map(existingRoutineMain -> {
                if (routineMain.getRegisterid() != null) {
                    existingRoutineMain.setRegisterid(routineMain.getRegisterid());
                }
                if (routineMain.getDescription() != null) {
                    existingRoutineMain.setDescription(routineMain.getDescription());
                }

                return existingRoutineMain;
            })
            .map(routineMainRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, routineMain.getId())
        );
    }

    /**
     * {@code GET  /routine-mains} : get all the routineMains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of routineMains in body.
     */
    @GetMapping("/routine-mains")
    public List<RoutineMain> getAllRoutineMains() {
        log.debug("REST request to get all RoutineMains");
        return routineMainRepository.findAll();
    }

    /**
     * {@code GET  /routine-mains/:id} : get the "id" routineMain.
     *
     * @param id the id of the routineMain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the routineMain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/routine-mains/{id}")
    public ResponseEntity<RoutineMain> getRoutineMain(@PathVariable String id) {
        log.debug("REST request to get RoutineMain : {}", id);
        Optional<RoutineMain> routineMain = routineMainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(routineMain);
    }

    /**
     * {@code DELETE  /routine-mains/:id} : delete the "id" routineMain.
     *
     * @param id the id of the routineMain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/routine-mains/{id}")
    public ResponseEntity<Void> deleteRoutineMain(@PathVariable String id) {
        log.debug("REST request to delete RoutineMain : {}", id);
        routineMainRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
