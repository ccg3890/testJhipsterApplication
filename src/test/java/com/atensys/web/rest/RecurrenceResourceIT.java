package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Recurrence;
import com.atensys.domain.enumeration.EndConditionType;
import com.atensys.domain.enumeration.FrequenceType;
import com.atensys.repository.RecurrenceRepository;
import com.atensys.service.dto.RecurrenceDTO;
import com.atensys.service.mapper.RecurrenceMapper;
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
 * Integration tests for the {@link RecurrenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecurrenceResourceIT {

    private static final FrequenceType DEFAULT_FREQ = FrequenceType.DAILY;
    private static final FrequenceType UPDATED_FREQ = FrequenceType.WEEKLY;

    private static final String DEFAULT_DAYS_OF_WEEK = "AAAAAAAAAA";
    private static final String UPDATED_DAYS_OF_WEEK = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEEK_OF_MONTH = 0;
    private static final Integer UPDATED_WEEK_OF_MONTH = 1;

    private static final Integer DEFAULT_DAY_OF_MONTH = 1;
    private static final Integer UPDATED_DAY_OF_MONTH = 2;

    private static final Integer DEFAULT_DAY_OF_YEAR = 1;
    private static final Integer UPDATED_DAY_OF_YEAR = 2;

    private static final EndConditionType DEFAULT_END_CONDITION = EndConditionType.NONE;
    private static final EndConditionType UPDATED_END_CONDITION = EndConditionType.DATE;

    private static final LocalDate DEFAULT_END_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUM_OF_STEP = 1;
    private static final Integer UPDATED_NUM_OF_STEP = 2;

    private static final String ENTITY_API_URL = "/api/recurrences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecurrenceRepository recurrenceRepository;

    @Autowired
    private RecurrenceMapper recurrenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecurrenceMockMvc;

    private Recurrence recurrence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurrence createEntity(EntityManager em) {
        Recurrence recurrence = new Recurrence()
            .freq(DEFAULT_FREQ)
            .daysOfWeek(DEFAULT_DAYS_OF_WEEK)
            .weekOfMonth(DEFAULT_WEEK_OF_MONTH)
            .dayOfMonth(DEFAULT_DAY_OF_MONTH)
            .dayOfYear(DEFAULT_DAY_OF_YEAR)
            .endCondition(DEFAULT_END_CONDITION)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .numOfStep(DEFAULT_NUM_OF_STEP);
        return recurrence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurrence createUpdatedEntity(EntityManager em) {
        Recurrence recurrence = new Recurrence()
            .freq(UPDATED_FREQ)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .weekOfMonth(UPDATED_WEEK_OF_MONTH)
            .dayOfMonth(UPDATED_DAY_OF_MONTH)
            .dayOfYear(UPDATED_DAY_OF_YEAR)
            .endCondition(UPDATED_END_CONDITION)
            .endDateTime(UPDATED_END_DATE_TIME)
            .numOfStep(UPDATED_NUM_OF_STEP);
        return recurrence;
    }

    @BeforeEach
    public void initTest() {
        recurrence = createEntity(em);
    }

    @Test
    @Transactional
    void createRecurrence() throws Exception {
        int databaseSizeBeforeCreate = recurrenceRepository.findAll().size();
        // Create the Recurrence
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);
        restRecurrenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeCreate + 1);
        Recurrence testRecurrence = recurrenceList.get(recurrenceList.size() - 1);
        assertThat(testRecurrence.getFreq()).isEqualTo(DEFAULT_FREQ);
        assertThat(testRecurrence.getDaysOfWeek()).isEqualTo(DEFAULT_DAYS_OF_WEEK);
        assertThat(testRecurrence.getWeekOfMonth()).isEqualTo(DEFAULT_WEEK_OF_MONTH);
        assertThat(testRecurrence.getDayOfMonth()).isEqualTo(DEFAULT_DAY_OF_MONTH);
        assertThat(testRecurrence.getDayOfYear()).isEqualTo(DEFAULT_DAY_OF_YEAR);
        assertThat(testRecurrence.getEndCondition()).isEqualTo(DEFAULT_END_CONDITION);
        assertThat(testRecurrence.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testRecurrence.getNumOfStep()).isEqualTo(DEFAULT_NUM_OF_STEP);
    }

    @Test
    @Transactional
    void createRecurrenceWithExistingId() throws Exception {
        // Create the Recurrence with an existing ID
        recurrence.setId(1L);
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        int databaseSizeBeforeCreate = recurrenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecurrenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFreqIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurrenceRepository.findAll().size();
        // set the field null
        recurrence.setFreq(null);

        // Create the Recurrence, which fails.
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        restRecurrenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurrenceRepository.findAll().size();
        // set the field null
        recurrence.setEndCondition(null);

        // Create the Recurrence, which fails.
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        restRecurrenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecurrences() throws Exception {
        // Initialize the database
        recurrenceRepository.saveAndFlush(recurrence);

        // Get all the recurrenceList
        restRecurrenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurrence.getId().intValue())))
            .andExpect(jsonPath("$.[*].freq").value(hasItem(DEFAULT_FREQ.toString())))
            .andExpect(jsonPath("$.[*].daysOfWeek").value(hasItem(DEFAULT_DAYS_OF_WEEK)))
            .andExpect(jsonPath("$.[*].weekOfMonth").value(hasItem(DEFAULT_WEEK_OF_MONTH)))
            .andExpect(jsonPath("$.[*].dayOfMonth").value(hasItem(DEFAULT_DAY_OF_MONTH)))
            .andExpect(jsonPath("$.[*].dayOfYear").value(hasItem(DEFAULT_DAY_OF_YEAR)))
            .andExpect(jsonPath("$.[*].endCondition").value(hasItem(DEFAULT_END_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(DEFAULT_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].numOfStep").value(hasItem(DEFAULT_NUM_OF_STEP)));
    }

    @Test
    @Transactional
    void getRecurrence() throws Exception {
        // Initialize the database
        recurrenceRepository.saveAndFlush(recurrence);

        // Get the recurrence
        restRecurrenceMockMvc
            .perform(get(ENTITY_API_URL_ID, recurrence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recurrence.getId().intValue()))
            .andExpect(jsonPath("$.freq").value(DEFAULT_FREQ.toString()))
            .andExpect(jsonPath("$.daysOfWeek").value(DEFAULT_DAYS_OF_WEEK))
            .andExpect(jsonPath("$.weekOfMonth").value(DEFAULT_WEEK_OF_MONTH))
            .andExpect(jsonPath("$.dayOfMonth").value(DEFAULT_DAY_OF_MONTH))
            .andExpect(jsonPath("$.dayOfYear").value(DEFAULT_DAY_OF_YEAR))
            .andExpect(jsonPath("$.endCondition").value(DEFAULT_END_CONDITION.toString()))
            .andExpect(jsonPath("$.endDateTime").value(DEFAULT_END_DATE_TIME.toString()))
            .andExpect(jsonPath("$.numOfStep").value(DEFAULT_NUM_OF_STEP));
    }

    @Test
    @Transactional
    void getNonExistingRecurrence() throws Exception {
        // Get the recurrence
        restRecurrenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRecurrence() throws Exception {
        // Initialize the database
        recurrenceRepository.saveAndFlush(recurrence);

        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();

        // Update the recurrence
        Recurrence updatedRecurrence = recurrenceRepository.findById(recurrence.getId()).get();
        // Disconnect from session so that the updates on updatedRecurrence are not directly saved in db
        em.detach(updatedRecurrence);
        updatedRecurrence
            .freq(UPDATED_FREQ)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .weekOfMonth(UPDATED_WEEK_OF_MONTH)
            .dayOfMonth(UPDATED_DAY_OF_MONTH)
            .dayOfYear(UPDATED_DAY_OF_YEAR)
            .endCondition(UPDATED_END_CONDITION)
            .endDateTime(UPDATED_END_DATE_TIME)
            .numOfStep(UPDATED_NUM_OF_STEP);
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(updatedRecurrence);

        restRecurrenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recurrenceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
        Recurrence testRecurrence = recurrenceList.get(recurrenceList.size() - 1);
        assertThat(testRecurrence.getFreq()).isEqualTo(UPDATED_FREQ);
        assertThat(testRecurrence.getDaysOfWeek()).isEqualTo(UPDATED_DAYS_OF_WEEK);
        assertThat(testRecurrence.getWeekOfMonth()).isEqualTo(UPDATED_WEEK_OF_MONTH);
        assertThat(testRecurrence.getDayOfMonth()).isEqualTo(UPDATED_DAY_OF_MONTH);
        assertThat(testRecurrence.getDayOfYear()).isEqualTo(UPDATED_DAY_OF_YEAR);
        assertThat(testRecurrence.getEndCondition()).isEqualTo(UPDATED_END_CONDITION);
        assertThat(testRecurrence.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testRecurrence.getNumOfStep()).isEqualTo(UPDATED_NUM_OF_STEP);
    }

    @Test
    @Transactional
    void putNonExistingRecurrence() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();
        recurrence.setId(count.incrementAndGet());

        // Create the Recurrence
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecurrenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recurrenceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecurrence() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();
        recurrence.setId(count.incrementAndGet());

        // Create the Recurrence
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecurrence() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();
        recurrence.setId(count.incrementAndGet());

        // Create the Recurrence
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecurrenceWithPatch() throws Exception {
        // Initialize the database
        recurrenceRepository.saveAndFlush(recurrence);

        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();

        // Update the recurrence using partial update
        Recurrence partialUpdatedRecurrence = new Recurrence();
        partialUpdatedRecurrence.setId(recurrence.getId());

        partialUpdatedRecurrence
            .freq(UPDATED_FREQ)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .dayOfMonth(UPDATED_DAY_OF_MONTH)
            .dayOfYear(UPDATED_DAY_OF_YEAR)
            .endDateTime(UPDATED_END_DATE_TIME)
            .numOfStep(UPDATED_NUM_OF_STEP);

        restRecurrenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurrence.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecurrence))
            )
            .andExpect(status().isOk());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
        Recurrence testRecurrence = recurrenceList.get(recurrenceList.size() - 1);
        assertThat(testRecurrence.getFreq()).isEqualTo(UPDATED_FREQ);
        assertThat(testRecurrence.getDaysOfWeek()).isEqualTo(UPDATED_DAYS_OF_WEEK);
        assertThat(testRecurrence.getWeekOfMonth()).isEqualTo(DEFAULT_WEEK_OF_MONTH);
        assertThat(testRecurrence.getDayOfMonth()).isEqualTo(UPDATED_DAY_OF_MONTH);
        assertThat(testRecurrence.getDayOfYear()).isEqualTo(UPDATED_DAY_OF_YEAR);
        assertThat(testRecurrence.getEndCondition()).isEqualTo(DEFAULT_END_CONDITION);
        assertThat(testRecurrence.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testRecurrence.getNumOfStep()).isEqualTo(UPDATED_NUM_OF_STEP);
    }

    @Test
    @Transactional
    void fullUpdateRecurrenceWithPatch() throws Exception {
        // Initialize the database
        recurrenceRepository.saveAndFlush(recurrence);

        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();

        // Update the recurrence using partial update
        Recurrence partialUpdatedRecurrence = new Recurrence();
        partialUpdatedRecurrence.setId(recurrence.getId());

        partialUpdatedRecurrence
            .freq(UPDATED_FREQ)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .weekOfMonth(UPDATED_WEEK_OF_MONTH)
            .dayOfMonth(UPDATED_DAY_OF_MONTH)
            .dayOfYear(UPDATED_DAY_OF_YEAR)
            .endCondition(UPDATED_END_CONDITION)
            .endDateTime(UPDATED_END_DATE_TIME)
            .numOfStep(UPDATED_NUM_OF_STEP);

        restRecurrenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecurrence.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecurrence))
            )
            .andExpect(status().isOk());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
        Recurrence testRecurrence = recurrenceList.get(recurrenceList.size() - 1);
        assertThat(testRecurrence.getFreq()).isEqualTo(UPDATED_FREQ);
        assertThat(testRecurrence.getDaysOfWeek()).isEqualTo(UPDATED_DAYS_OF_WEEK);
        assertThat(testRecurrence.getWeekOfMonth()).isEqualTo(UPDATED_WEEK_OF_MONTH);
        assertThat(testRecurrence.getDayOfMonth()).isEqualTo(UPDATED_DAY_OF_MONTH);
        assertThat(testRecurrence.getDayOfYear()).isEqualTo(UPDATED_DAY_OF_YEAR);
        assertThat(testRecurrence.getEndCondition()).isEqualTo(UPDATED_END_CONDITION);
        assertThat(testRecurrence.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testRecurrence.getNumOfStep()).isEqualTo(UPDATED_NUM_OF_STEP);
    }

    @Test
    @Transactional
    void patchNonExistingRecurrence() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();
        recurrence.setId(count.incrementAndGet());

        // Create the Recurrence
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecurrenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recurrenceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecurrence() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();
        recurrence.setId(count.incrementAndGet());

        // Create the Recurrence
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecurrence() throws Exception {
        int databaseSizeBeforeUpdate = recurrenceRepository.findAll().size();
        recurrence.setId(count.incrementAndGet());

        // Create the Recurrence
        RecurrenceDTO recurrenceDTO = recurrenceMapper.toDto(recurrence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecurrenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recurrenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recurrence in the database
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecurrence() throws Exception {
        // Initialize the database
        recurrenceRepository.saveAndFlush(recurrence);

        int databaseSizeBeforeDelete = recurrenceRepository.findAll().size();

        // Delete the recurrence
        restRecurrenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, recurrence.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recurrence> recurrenceList = recurrenceRepository.findAll();
        assertThat(recurrenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
