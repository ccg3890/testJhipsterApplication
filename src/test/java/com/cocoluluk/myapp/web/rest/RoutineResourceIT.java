package com.cocoluluk.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cocoluluk.myapp.IntegrationTest;
import com.cocoluluk.myapp.domain.Routine;
import com.cocoluluk.myapp.repository.RoutineRepository;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link RoutineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoutineResourceIT {

    private static final String DEFAULT_REGISTER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/routines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoutineMockMvc;

    private Routine routine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Routine createEntity(EntityManager em) {
        Routine routine = new Routine().register(DEFAULT_REGISTER).type(DEFAULT_TYPE).desc(DEFAULT_DESC);
        return routine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Routine createUpdatedEntity(EntityManager em) {
        Routine routine = new Routine().register(UPDATED_REGISTER).type(UPDATED_TYPE).desc(UPDATED_DESC);
        return routine;
    }

    @BeforeEach
    public void initTest() {
        routine = createEntity(em);
    }

    @Test
    @Transactional
    void createRoutine() throws Exception {
        int databaseSizeBeforeCreate = routineRepository.findAll().size();
        // Create the Routine
        restRoutineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routine)))
            .andExpect(status().isCreated());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeCreate + 1);
        Routine testRoutine = routineList.get(routineList.size() - 1);
        assertThat(testRoutine.getRegister()).isEqualTo(DEFAULT_REGISTER);
        assertThat(testRoutine.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRoutine.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    void createRoutineWithExistingId() throws Exception {
        // Create the Routine with an existing ID
        routine.setId("existing_id");

        int databaseSizeBeforeCreate = routineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoutineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routine)))
            .andExpect(status().isBadRequest());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRegisterIsRequired() throws Exception {
        int databaseSizeBeforeTest = routineRepository.findAll().size();
        // set the field null
        routine.setRegister(null);

        // Create the Routine, which fails.

        restRoutineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routine)))
            .andExpect(status().isBadRequest());

        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = routineRepository.findAll().size();
        // set the field null
        routine.setType(null);

        // Create the Routine, which fails.

        restRoutineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routine)))
            .andExpect(status().isBadRequest());

        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoutines() throws Exception {
        // Initialize the database
        routine.setId(UUID.randomUUID().toString());
        routineRepository.saveAndFlush(routine);

        // Get all the routineList
        restRoutineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(routine.getId())))
            .andExpect(jsonPath("$.[*].register").value(hasItem(DEFAULT_REGISTER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }

    @Test
    @Transactional
    void getRoutine() throws Exception {
        // Initialize the database
        routine.setId(UUID.randomUUID().toString());
        routineRepository.saveAndFlush(routine);

        // Get the routine
        restRoutineMockMvc
            .perform(get(ENTITY_API_URL_ID, routine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(routine.getId()))
            .andExpect(jsonPath("$.register").value(DEFAULT_REGISTER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC));
    }

    @Test
    @Transactional
    void getNonExistingRoutine() throws Exception {
        // Get the routine
        restRoutineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoutine() throws Exception {
        // Initialize the database
        routine.setId(UUID.randomUUID().toString());
        routineRepository.saveAndFlush(routine);

        int databaseSizeBeforeUpdate = routineRepository.findAll().size();

        // Update the routine
        Routine updatedRoutine = routineRepository.findById(routine.getId()).get();
        // Disconnect from session so that the updates on updatedRoutine are not directly saved in db
        em.detach(updatedRoutine);
        updatedRoutine.register(UPDATED_REGISTER).type(UPDATED_TYPE).desc(UPDATED_DESC);

        restRoutineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoutine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoutine))
            )
            .andExpect(status().isOk());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
        Routine testRoutine = routineList.get(routineList.size() - 1);
        assertThat(testRoutine.getRegister()).isEqualTo(UPDATED_REGISTER);
        assertThat(testRoutine.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoutine.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    void putNonExistingRoutine() throws Exception {
        int databaseSizeBeforeUpdate = routineRepository.findAll().size();
        routine.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoutineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, routine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoutine() throws Exception {
        int databaseSizeBeforeUpdate = routineRepository.findAll().size();
        routine.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoutine() throws Exception {
        int databaseSizeBeforeUpdate = routineRepository.findAll().size();
        routine.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoutineWithPatch() throws Exception {
        // Initialize the database
        routine.setId(UUID.randomUUID().toString());
        routineRepository.saveAndFlush(routine);

        int databaseSizeBeforeUpdate = routineRepository.findAll().size();

        // Update the routine using partial update
        Routine partialUpdatedRoutine = new Routine();
        partialUpdatedRoutine.setId(routine.getId());

        partialUpdatedRoutine.type(UPDATED_TYPE);

        restRoutineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoutine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoutine))
            )
            .andExpect(status().isOk());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
        Routine testRoutine = routineList.get(routineList.size() - 1);
        assertThat(testRoutine.getRegister()).isEqualTo(DEFAULT_REGISTER);
        assertThat(testRoutine.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoutine.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    void fullUpdateRoutineWithPatch() throws Exception {
        // Initialize the database
        routine.setId(UUID.randomUUID().toString());
        routineRepository.saveAndFlush(routine);

        int databaseSizeBeforeUpdate = routineRepository.findAll().size();

        // Update the routine using partial update
        Routine partialUpdatedRoutine = new Routine();
        partialUpdatedRoutine.setId(routine.getId());

        partialUpdatedRoutine.register(UPDATED_REGISTER).type(UPDATED_TYPE).desc(UPDATED_DESC);

        restRoutineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoutine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoutine))
            )
            .andExpect(status().isOk());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
        Routine testRoutine = routineList.get(routineList.size() - 1);
        assertThat(testRoutine.getRegister()).isEqualTo(UPDATED_REGISTER);
        assertThat(testRoutine.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoutine.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    void patchNonExistingRoutine() throws Exception {
        int databaseSizeBeforeUpdate = routineRepository.findAll().size();
        routine.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoutineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, routine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(routine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoutine() throws Exception {
        int databaseSizeBeforeUpdate = routineRepository.findAll().size();
        routine.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(routine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoutine() throws Exception {
        int databaseSizeBeforeUpdate = routineRepository.findAll().size();
        routine.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(routine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Routine in the database
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoutine() throws Exception {
        // Initialize the database
        routine.setId(UUID.randomUUID().toString());
        routineRepository.saveAndFlush(routine);

        int databaseSizeBeforeDelete = routineRepository.findAll().size();

        // Delete the routine
        restRoutineMockMvc
            .perform(delete(ENTITY_API_URL_ID, routine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Routine> routineList = routineRepository.findAll();
        assertThat(routineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
