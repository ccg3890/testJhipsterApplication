package com.cocoluluk.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cocoluluk.myapp.IntegrationTest;
import com.cocoluluk.myapp.domain.RoutineMain;
import com.cocoluluk.myapp.repository.RoutineMainRepository;
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
 * Integration tests for the {@link RoutineMainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoutineMainResourceIT {

    private static final String DEFAULT_REGISTERID = "AAAAAAAAAA";
    private static final String UPDATED_REGISTERID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/routine-mains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RoutineMainRepository routineMainRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoutineMainMockMvc;

    private RoutineMain routineMain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoutineMain createEntity(EntityManager em) {
        RoutineMain routineMain = new RoutineMain().registerid(DEFAULT_REGISTERID).description(DEFAULT_DESCRIPTION);
        return routineMain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoutineMain createUpdatedEntity(EntityManager em) {
        RoutineMain routineMain = new RoutineMain().registerid(UPDATED_REGISTERID).description(UPDATED_DESCRIPTION);
        return routineMain;
    }

    @BeforeEach
    public void initTest() {
        routineMain = createEntity(em);
    }

    @Test
    @Transactional
    void createRoutineMain() throws Exception {
        int databaseSizeBeforeCreate = routineMainRepository.findAll().size();
        // Create the RoutineMain
        restRoutineMainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routineMain)))
            .andExpect(status().isCreated());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeCreate + 1);
        RoutineMain testRoutineMain = routineMainList.get(routineMainList.size() - 1);
        assertThat(testRoutineMain.getRegisterid()).isEqualTo(DEFAULT_REGISTERID);
        assertThat(testRoutineMain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRoutineMainWithExistingId() throws Exception {
        // Create the RoutineMain with an existing ID
        routineMain.setId("existing_id");

        int databaseSizeBeforeCreate = routineMainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoutineMainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routineMain)))
            .andExpect(status().isBadRequest());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRegisteridIsRequired() throws Exception {
        int databaseSizeBeforeTest = routineMainRepository.findAll().size();
        // set the field null
        routineMain.setRegisterid(null);

        // Create the RoutineMain, which fails.

        restRoutineMainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routineMain)))
            .andExpect(status().isBadRequest());

        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoutineMains() throws Exception {
        // Initialize the database
        routineMain.setId(UUID.randomUUID().toString());
        routineMainRepository.saveAndFlush(routineMain);

        // Get all the routineMainList
        restRoutineMainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(routineMain.getId())))
            .andExpect(jsonPath("$.[*].registerid").value(hasItem(DEFAULT_REGISTERID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRoutineMain() throws Exception {
        // Initialize the database
        routineMain.setId(UUID.randomUUID().toString());
        routineMainRepository.saveAndFlush(routineMain);

        // Get the routineMain
        restRoutineMainMockMvc
            .perform(get(ENTITY_API_URL_ID, routineMain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(routineMain.getId()))
            .andExpect(jsonPath("$.registerid").value(DEFAULT_REGISTERID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRoutineMain() throws Exception {
        // Get the routineMain
        restRoutineMainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoutineMain() throws Exception {
        // Initialize the database
        routineMain.setId(UUID.randomUUID().toString());
        routineMainRepository.saveAndFlush(routineMain);

        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();

        // Update the routineMain
        RoutineMain updatedRoutineMain = routineMainRepository.findById(routineMain.getId()).get();
        // Disconnect from session so that the updates on updatedRoutineMain are not directly saved in db
        em.detach(updatedRoutineMain);
        updatedRoutineMain.registerid(UPDATED_REGISTERID).description(UPDATED_DESCRIPTION);

        restRoutineMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRoutineMain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRoutineMain))
            )
            .andExpect(status().isOk());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
        RoutineMain testRoutineMain = routineMainList.get(routineMainList.size() - 1);
        assertThat(testRoutineMain.getRegisterid()).isEqualTo(UPDATED_REGISTERID);
        assertThat(testRoutineMain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRoutineMain() throws Exception {
        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();
        routineMain.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoutineMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, routineMain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routineMain))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoutineMain() throws Exception {
        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();
        routineMain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routineMain))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoutineMain() throws Exception {
        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();
        routineMain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(routineMain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoutineMainWithPatch() throws Exception {
        // Initialize the database
        routineMain.setId(UUID.randomUUID().toString());
        routineMainRepository.saveAndFlush(routineMain);

        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();

        // Update the routineMain using partial update
        RoutineMain partialUpdatedRoutineMain = new RoutineMain();
        partialUpdatedRoutineMain.setId(routineMain.getId());

        partialUpdatedRoutineMain.registerid(UPDATED_REGISTERID).description(UPDATED_DESCRIPTION);

        restRoutineMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoutineMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoutineMain))
            )
            .andExpect(status().isOk());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
        RoutineMain testRoutineMain = routineMainList.get(routineMainList.size() - 1);
        assertThat(testRoutineMain.getRegisterid()).isEqualTo(UPDATED_REGISTERID);
        assertThat(testRoutineMain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRoutineMainWithPatch() throws Exception {
        // Initialize the database
        routineMain.setId(UUID.randomUUID().toString());
        routineMainRepository.saveAndFlush(routineMain);

        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();

        // Update the routineMain using partial update
        RoutineMain partialUpdatedRoutineMain = new RoutineMain();
        partialUpdatedRoutineMain.setId(routineMain.getId());

        partialUpdatedRoutineMain.registerid(UPDATED_REGISTERID).description(UPDATED_DESCRIPTION);

        restRoutineMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoutineMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoutineMain))
            )
            .andExpect(status().isOk());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
        RoutineMain testRoutineMain = routineMainList.get(routineMainList.size() - 1);
        assertThat(testRoutineMain.getRegisterid()).isEqualTo(UPDATED_REGISTERID);
        assertThat(testRoutineMain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRoutineMain() throws Exception {
        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();
        routineMain.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoutineMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, routineMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(routineMain))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoutineMain() throws Exception {
        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();
        routineMain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(routineMain))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoutineMain() throws Exception {
        int databaseSizeBeforeUpdate = routineMainRepository.findAll().size();
        routineMain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoutineMainMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(routineMain))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoutineMain in the database
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoutineMain() throws Exception {
        // Initialize the database
        routineMain.setId(UUID.randomUUID().toString());
        routineMainRepository.saveAndFlush(routineMain);

        int databaseSizeBeforeDelete = routineMainRepository.findAll().size();

        // Delete the routineMain
        restRoutineMainMockMvc
            .perform(delete(ENTITY_API_URL_ID, routineMain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoutineMain> routineMainList = routineMainRepository.findAll();
        assertThat(routineMainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
