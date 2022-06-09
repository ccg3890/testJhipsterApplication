package com.atensys.web.rest;

import com.atensys.repository.AuthRepository;
import com.atensys.service.AuthService;
import com.atensys.service.dto.AuthDTO;
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
 * REST controller for managing {@link com.atensys.domain.Auth}.
 */
@RestController
@RequestMapping("/api")
public class AuthResource {

    private final Logger log = LoggerFactory.getLogger(AuthResource.class);

    private static final String ENTITY_NAME = "auth";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthService authService;

    private final AuthRepository authRepository;

    public AuthResource(AuthService authService, AuthRepository authRepository) {
        this.authService = authService;
        this.authRepository = authRepository;
    }

    /**
     * {@code POST  /auths} : Create a new auth.
     *
     * @param authDTO the authDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authDTO, or with status {@code 400 (Bad Request)} if the auth has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auths")
    public ResponseEntity<AuthDTO> createAuth(@Valid @RequestBody AuthDTO authDTO) throws URISyntaxException {
        log.debug("REST request to save Auth : {}", authDTO);
        if (authDTO.getId() != null) {
            throw new BadRequestAlertException("A new auth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthDTO result = authService.save(authDTO);
        return ResponseEntity
            .created(new URI("/api/auths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auths/:id} : Updates an existing auth.
     *
     * @param id the id of the authDTO to save.
     * @param authDTO the authDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authDTO,
     * or with status {@code 400 (Bad Request)} if the authDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auths/{id}")
    public ResponseEntity<AuthDTO> updateAuth(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuthDTO authDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Auth : {}, {}", id, authDTO);
        if (authDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthDTO result = authService.update(authDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /auths/:id} : Partial updates given fields of an existing auth, field will ignore if it is null
     *
     * @param id the id of the authDTO to save.
     * @param authDTO the authDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authDTO,
     * or with status {@code 400 (Bad Request)} if the authDTO is not valid,
     * or with status {@code 404 (Not Found)} if the authDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the authDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/auths/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuthDTO> partialUpdateAuth(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuthDTO authDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Auth partially : {}, {}", id, authDTO);
        if (authDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthDTO> result = authService.partialUpdate(authDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /auths} : get all the auths.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auths in body.
     */
    @GetMapping("/auths")
    public List<AuthDTO> getAllAuths(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Auths");
        return authService.findAll();
    }

    /**
     * {@code GET  /auths/:id} : get the "id" auth.
     *
     * @param id the id of the authDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auths/{id}")
    public ResponseEntity<AuthDTO> getAuth(@PathVariable Long id) {
        log.debug("REST request to get Auth : {}", id);
        Optional<AuthDTO> authDTO = authService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authDTO);
    }

    /**
     * {@code DELETE  /auths/:id} : delete the "id" auth.
     *
     * @param id the id of the authDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auths/{id}")
    public ResponseEntity<Void> deleteAuth(@PathVariable Long id) {
        log.debug("REST request to delete Auth : {}", id);
        authService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
