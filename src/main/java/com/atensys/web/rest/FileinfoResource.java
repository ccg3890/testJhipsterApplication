package com.atensys.web.rest;

import com.atensys.repository.FileinfoRepository;
import com.atensys.service.FileinfoService;
import com.atensys.service.dto.FileinfoDTO;
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
 * REST controller for managing {@link com.atensys.domain.Fileinfo}.
 */
@RestController
@RequestMapping("/api")
public class FileinfoResource {

    private final Logger log = LoggerFactory.getLogger(FileinfoResource.class);

    private static final String ENTITY_NAME = "fileinfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileinfoService fileinfoService;

    private final FileinfoRepository fileinfoRepository;

    public FileinfoResource(FileinfoService fileinfoService, FileinfoRepository fileinfoRepository) {
        this.fileinfoService = fileinfoService;
        this.fileinfoRepository = fileinfoRepository;
    }

    /**
     * {@code POST  /fileinfos} : Create a new fileinfo.
     *
     * @param fileinfoDTO the fileinfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileinfoDTO, or with status {@code 400 (Bad Request)} if the fileinfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fileinfos")
    public ResponseEntity<FileinfoDTO> createFileinfo(@Valid @RequestBody FileinfoDTO fileinfoDTO) throws URISyntaxException {
        log.debug("REST request to save Fileinfo : {}", fileinfoDTO);
        if (fileinfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileinfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileinfoDTO result = fileinfoService.save(fileinfoDTO);
        return ResponseEntity
            .created(new URI("/api/fileinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fileinfos/:id} : Updates an existing fileinfo.
     *
     * @param id the id of the fileinfoDTO to save.
     * @param fileinfoDTO the fileinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileinfoDTO,
     * or with status {@code 400 (Bad Request)} if the fileinfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fileinfos/{id}")
    public ResponseEntity<FileinfoDTO> updateFileinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FileinfoDTO fileinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fileinfo : {}, {}", id, fileinfoDTO);
        if (fileinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileinfoDTO result = fileinfoService.update(fileinfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileinfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fileinfos/:id} : Partial updates given fields of an existing fileinfo, field will ignore if it is null
     *
     * @param id the id of the fileinfoDTO to save.
     * @param fileinfoDTO the fileinfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileinfoDTO,
     * or with status {@code 400 (Bad Request)} if the fileinfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileinfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileinfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fileinfos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileinfoDTO> partialUpdateFileinfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FileinfoDTO fileinfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fileinfo partially : {}, {}", id, fileinfoDTO);
        if (fileinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileinfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileinfoDTO> result = fileinfoService.partialUpdate(fileinfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileinfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fileinfos} : get all the fileinfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileinfos in body.
     */
    @GetMapping("/fileinfos")
    public List<FileinfoDTO> getAllFileinfos() {
        log.debug("REST request to get all Fileinfos");
        return fileinfoService.findAll();
    }

    /**
     * {@code GET  /fileinfos/:id} : get the "id" fileinfo.
     *
     * @param id the id of the fileinfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileinfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fileinfos/{id}")
    public ResponseEntity<FileinfoDTO> getFileinfo(@PathVariable Long id) {
        log.debug("REST request to get Fileinfo : {}", id);
        Optional<FileinfoDTO> fileinfoDTO = fileinfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileinfoDTO);
    }

    /**
     * {@code DELETE  /fileinfos/:id} : delete the "id" fileinfo.
     *
     * @param id the id of the fileinfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fileinfos/{id}")
    public ResponseEntity<Void> deleteFileinfo(@PathVariable Long id) {
        log.debug("REST request to delete Fileinfo : {}", id);
        fileinfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
