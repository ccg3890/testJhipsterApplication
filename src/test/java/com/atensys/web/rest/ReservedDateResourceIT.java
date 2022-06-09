package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.ReservedDate;
import com.atensys.repository.ReservedDateRepository;
import com.atensys.service.dto.ReservedDateDTO;
import com.atensys.service.mapper.ReservedDateMapper;
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
 * Integration tests for the {@link ReservedDateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservedDateResourceIT {

    private static final LocalDate DEFAULT_START_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    private static final String ENTITY_API_URL = "/api/reserved-dates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReservedDateRepository reservedDateRepository;

    @Autowired
    private ReservedDateMapper reservedDateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservedDateMockMvc;

    private ReservedDate reservedDate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservedDate createEntity(EntityManager em) {
        ReservedDate reservedDate = new ReservedDate()
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .step(DEFAULT_STEP);
        return reservedDate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservedDate createUpdatedEntity(EntityManager em) {
        ReservedDate reservedDate = new ReservedDate()
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .step(UPDATED_STEP);
        return reservedDate;
    }

    @BeforeEach
    public void initTest() {
        reservedDate = createEntity(em);
    }

    @Test
    @Transactional
    void createReservedDate() throws Exception {
        int databaseSizeBeforeCreate = reservedDateRepository.findAll().size();
        // Create the ReservedDate
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);
        restReservedDateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeCreate + 1);
        ReservedDate testReservedDate = reservedDateList.get(reservedDateList.size() - 1);
        assertThat(testReservedDate.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testReservedDate.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testReservedDate.getStep()).isEqualTo(DEFAULT_STEP);
    }

    @Test
    @Transactional
    void createReservedDateWithExistingId() throws Exception {
        // Create the ReservedDate with an existing ID
        reservedDate.setId(1L);
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        int databaseSizeBeforeCreate = reservedDateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservedDateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservedDateRepository.findAll().size();
        // set the field null
        reservedDate.setStartDateTime(null);

        // Create the ReservedDate, which fails.
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        restReservedDateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservedDateRepository.findAll().size();
        // set the field null
        reservedDate.setEndDateTime(null);

        // Create the ReservedDate, which fails.
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        restReservedDateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReservedDates() throws Exception {
        // Initialize the database
        reservedDateRepository.saveAndFlush(reservedDate);

        // Get all the reservedDateList
        restReservedDateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservedDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(DEFAULT_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(DEFAULT_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)));
    }

    @Test
    @Transactional
    void getReservedDate() throws Exception {
        // Initialize the database
        reservedDateRepository.saveAndFlush(reservedDate);

        // Get the reservedDate
        restReservedDateMockMvc
            .perform(get(ENTITY_API_URL_ID, reservedDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservedDate.getId().intValue()))
            .andExpect(jsonPath("$.startDateTime").value(DEFAULT_START_DATE_TIME.toString()))
            .andExpect(jsonPath("$.endDateTime").value(DEFAULT_END_DATE_TIME.toString()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP));
    }

    @Test
    @Transactional
    void getNonExistingReservedDate() throws Exception {
        // Get the reservedDate
        restReservedDateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReservedDate() throws Exception {
        // Initialize the database
        reservedDateRepository.saveAndFlush(reservedDate);

        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();

        // Update the reservedDate
        ReservedDate updatedReservedDate = reservedDateRepository.findById(reservedDate.getId()).get();
        // Disconnect from session so that the updates on updatedReservedDate are not directly saved in db
        em.detach(updatedReservedDate);
        updatedReservedDate.startDateTime(UPDATED_START_DATE_TIME).endDateTime(UPDATED_END_DATE_TIME).step(UPDATED_STEP);
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(updatedReservedDate);

        restReservedDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservedDateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
        ReservedDate testReservedDate = reservedDateList.get(reservedDateList.size() - 1);
        assertThat(testReservedDate.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testReservedDate.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testReservedDate.getStep()).isEqualTo(UPDATED_STEP);
    }

    @Test
    @Transactional
    void putNonExistingReservedDate() throws Exception {
        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();
        reservedDate.setId(count.incrementAndGet());

        // Create the ReservedDate
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservedDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservedDateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservedDate() throws Exception {
        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();
        reservedDate.setId(count.incrementAndGet());

        // Create the ReservedDate
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservedDate() throws Exception {
        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();
        reservedDate.setId(count.incrementAndGet());

        // Create the ReservedDate
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedDateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservedDateWithPatch() throws Exception {
        // Initialize the database
        reservedDateRepository.saveAndFlush(reservedDate);

        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();

        // Update the reservedDate using partial update
        ReservedDate partialUpdatedReservedDate = new ReservedDate();
        partialUpdatedReservedDate.setId(reservedDate.getId());

        partialUpdatedReservedDate.startDateTime(UPDATED_START_DATE_TIME).step(UPDATED_STEP);

        restReservedDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservedDate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservedDate))
            )
            .andExpect(status().isOk());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
        ReservedDate testReservedDate = reservedDateList.get(reservedDateList.size() - 1);
        assertThat(testReservedDate.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testReservedDate.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testReservedDate.getStep()).isEqualTo(UPDATED_STEP);
    }

    @Test
    @Transactional
    void fullUpdateReservedDateWithPatch() throws Exception {
        // Initialize the database
        reservedDateRepository.saveAndFlush(reservedDate);

        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();

        // Update the reservedDate using partial update
        ReservedDate partialUpdatedReservedDate = new ReservedDate();
        partialUpdatedReservedDate.setId(reservedDate.getId());

        partialUpdatedReservedDate.startDateTime(UPDATED_START_DATE_TIME).endDateTime(UPDATED_END_DATE_TIME).step(UPDATED_STEP);

        restReservedDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservedDate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservedDate))
            )
            .andExpect(status().isOk());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
        ReservedDate testReservedDate = reservedDateList.get(reservedDateList.size() - 1);
        assertThat(testReservedDate.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testReservedDate.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testReservedDate.getStep()).isEqualTo(UPDATED_STEP);
    }

    @Test
    @Transactional
    void patchNonExistingReservedDate() throws Exception {
        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();
        reservedDate.setId(count.incrementAndGet());

        // Create the ReservedDate
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservedDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservedDateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservedDate() throws Exception {
        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();
        reservedDate.setId(count.incrementAndGet());

        // Create the ReservedDate
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservedDate() throws Exception {
        int databaseSizeBeforeUpdate = reservedDateRepository.findAll().size();
        reservedDate.setId(count.incrementAndGet());

        // Create the ReservedDate
        ReservedDateDTO reservedDateDTO = reservedDateMapper.toDto(reservedDate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedDateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservedDateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservedDate in the database
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservedDate() throws Exception {
        // Initialize the database
        reservedDateRepository.saveAndFlush(reservedDate);

        int databaseSizeBeforeDelete = reservedDateRepository.findAll().size();

        // Delete the reservedDate
        restReservedDateMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservedDate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservedDate> reservedDateList = reservedDateRepository.findAll();
        assertThat(reservedDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
