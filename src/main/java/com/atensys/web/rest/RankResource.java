package com.atensys.web.rest;

import com.atensys.repository.RankRepository;
import com.atensys.service.RankService;
import com.atensys.service.dto.RankDTO;
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
 * REST controller for managing {@link com.atensys.domain.Rank}.
 */
@RestController
@RequestMapping("/api")
public class RankResource {

    private final Logger log = LoggerFactory.getLogger(RankResource.class);

    private static final String ENTITY_NAME = "rank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RankService rankService;

    private final RankRepository rankRepository;

    public RankResource(RankService rankService, RankRepository rankRepository) {
        this.rankService = rankService;
        this.rankRepository = rankRepository;
    }

    /**
     * {@code POST  /ranks} : Create a new rank.
     *
     * @param rankDTO the rankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rankDTO, or with status {@code 400 (Bad Request)} if the rank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ranks")
    public ResponseEntity<RankDTO> createRank(@Valid @RequestBody RankDTO rankDTO) throws URISyntaxException {
        log.debug("REST request to save Rank : {}", rankDTO);
        if (rankDTO.getId() != null) {
            throw new BadRequestAlertException("A new rank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RankDTO result = rankService.save(rankDTO);
        return ResponseEntity
            .created(new URI("/api/ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ranks/:id} : Updates an existing rank.
     *
     * @param id the id of the rankDTO to save.
     * @param rankDTO the rankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankDTO,
     * or with status {@code 400 (Bad Request)} if the rankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ranks/{id}")
    public ResponseEntity<RankDTO> updateRank(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RankDTO rankDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rank : {}, {}", id, rankDTO);
        if (rankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RankDTO result = rankService.update(rankDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rankDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ranks/:id} : Partial updates given fields of an existing rank, field will ignore if it is null
     *
     * @param id the id of the rankDTO to save.
     * @param rankDTO the rankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankDTO,
     * or with status {@code 400 (Bad Request)} if the rankDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rankDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ranks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RankDTO> partialUpdateRank(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RankDTO rankDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rank partially : {}, {}", id, rankDTO);
        if (rankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RankDTO> result = rankService.partialUpdate(rankDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rankDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ranks} : get all the ranks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ranks in body.
     */
    @GetMapping("/ranks")
    public List<RankDTO> getAllRanks() {
        log.debug("REST request to get all Ranks");
        return rankService.findAll();
    }

    /**
     * {@code GET  /ranks/:id} : get the "id" rank.
     *
     * @param id the id of the rankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ranks/{id}")
    public ResponseEntity<RankDTO> getRank(@PathVariable Long id) {
        log.debug("REST request to get Rank : {}", id);
        Optional<RankDTO> rankDTO = rankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rankDTO);
    }

    /**
     * {@code DELETE  /ranks/:id} : delete the "id" rank.
     *
     * @param id the id of the rankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ranks/{id}")
    public ResponseEntity<Void> deleteRank(@PathVariable Long id) {
        log.debug("REST request to delete Rank : {}", id);
        rankService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
