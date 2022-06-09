package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Penalty;
import com.atensys.repository.PenaltyRepository;
import com.atensys.service.dto.PenaltyDTO;
import com.atensys.service.mapper.PenaltyMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PenaltyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PenaltyResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/penalties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Autowired
    private PenaltyMapper penaltyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPenaltyMockMvc;

    private Penalty penalty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Penalty createEntity(EntityManager em) {
        Penalty penalty = new Penalty()
            .userId(DEFAULT_USER_ID)
            .officeId(DEFAULT_OFFICE_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return penalty;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Penalty createUpdatedEntity(EntityManager em) {
        Penalty penalty = new Penalty()
            .userId(UPDATED_USER_ID)
            .officeId(UPDATED_OFFICE_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return penalty;
    }

    @BeforeEach
    public void initTest() {
        penalty = createEntity(em);
    }

    @Test
    @Transactional
    void createPenalty() throws Exception {
        int databaseSizeBeforeCreate = penaltyRepository.findAll().size();
        // Create the Penalty
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);
        restPenaltyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeCreate + 1);
        Penalty testPenalty = penaltyList.get(penaltyList.size() - 1);
        assertThat(testPenalty.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPenalty.getOfficeId()).isEqualTo(DEFAULT_OFFICE_ID);
        assertThat(testPenalty.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPenalty.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void createPenaltyWithExistingId() throws Exception {
        // Create the Penalty with an existing ID
        penalty.setId(1L);
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);

        int databaseSizeBeforeCreate = penaltyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPenaltyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPenalties() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get all the penaltyList
        restPenaltyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(penalty.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].officeId").value(hasItem(DEFAULT_OFFICE_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    void getPenalty() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        // Get the penalty
        restPenaltyMockMvc
            .perform(get(ENTITY_API_URL_ID, penalty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(penalty.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.officeId").value(DEFAULT_OFFICE_ID))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPenalty() throws Exception {
        // Get the penalty
        restPenaltyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPenalty() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();

        // Update the penalty
        Penalty updatedPenalty = penaltyRepository.findById(penalty.getId()).get();
        // Disconnect from session so that the updates on updatedPenalty are not directly saved in db
        em.detach(updatedPenalty);
        updatedPenalty.userId(UPDATED_USER_ID).officeId(UPDATED_OFFICE_ID).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(updatedPenalty);

        restPenaltyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, penaltyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
        Penalty testPenalty = penaltyList.get(penaltyList.size() - 1);
        assertThat(testPenalty.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPenalty.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testPenalty.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPenalty.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPenalty() throws Exception {
        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();
        penalty.setId(count.incrementAndGet());

        // Create the Penalty
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPenaltyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, penaltyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPenalty() throws Exception {
        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();
        penalty.setId(count.incrementAndGet());

        // Create the Penalty
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPenaltyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPenalty() throws Exception {
        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();
        penalty.setId(count.incrementAndGet());

        // Create the Penalty
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPenaltyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePenaltyWithPatch() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();

        // Update the penalty using partial update
        Penalty partialUpdatedPenalty = new Penalty();
        partialUpdatedPenalty.setId(penalty.getId());

        partialUpdatedPenalty.userId(UPDATED_USER_ID);

        restPenaltyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPenalty.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPenalty))
            )
            .andExpect(status().isOk());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
        Penalty testPenalty = penaltyList.get(penaltyList.size() - 1);
        assertThat(testPenalty.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPenalty.getOfficeId()).isEqualTo(DEFAULT_OFFICE_ID);
        assertThat(testPenalty.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPenalty.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePenaltyWithPatch() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();

        // Update the penalty using partial update
        Penalty partialUpdatedPenalty = new Penalty();
        partialUpdatedPenalty.setId(penalty.getId());

        partialUpdatedPenalty.userId(UPDATED_USER_ID).officeId(UPDATED_OFFICE_ID).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);

        restPenaltyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPenalty.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPenalty))
            )
            .andExpect(status().isOk());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
        Penalty testPenalty = penaltyList.get(penaltyList.size() - 1);
        assertThat(testPenalty.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPenalty.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testPenalty.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPenalty.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPenalty() throws Exception {
        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();
        penalty.setId(count.incrementAndGet());

        // Create the Penalty
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPenaltyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, penaltyDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPenalty() throws Exception {
        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();
        penalty.setId(count.incrementAndGet());

        // Create the Penalty
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPenaltyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPenalty() throws Exception {
        int databaseSizeBeforeUpdate = penaltyRepository.findAll().size();
        penalty.setId(count.incrementAndGet());

        // Create the Penalty
        PenaltyDTO penaltyDTO = penaltyMapper.toDto(penalty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPenaltyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(penaltyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Penalty in the database
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePenalty() throws Exception {
        // Initialize the database
        penaltyRepository.saveAndFlush(penalty);

        int databaseSizeBeforeDelete = penaltyRepository.findAll().size();

        // Delete the penalty
        restPenaltyMockMvc
            .perform(delete(ENTITY_API_URL_ID, penalty.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Penalty> penaltyList = penaltyRepository.findAll();
        assertThat(penaltyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
