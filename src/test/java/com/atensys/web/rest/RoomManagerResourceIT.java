package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.RoomManager;
import com.atensys.repository.RoomManagerRepository;
import com.atensys.service.dto.RoomManagerDTO;
import com.atensys.service.mapper.RoomManagerMapper;
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
 * Integration tests for the {@link RoomManagerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomManagerResourceIT {

    private static final String DEFAULT_MANAGER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/room-managers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomManagerRepository roomManagerRepository;

    @Autowired
    private RoomManagerMapper roomManagerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomManagerMockMvc;

    private RoomManager roomManager;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomManager createEntity(EntityManager em) {
        RoomManager roomManager = new RoomManager().managerId(DEFAULT_MANAGER_ID).name(DEFAULT_NAME);
        return roomManager;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomManager createUpdatedEntity(EntityManager em) {
        RoomManager roomManager = new RoomManager().managerId(UPDATED_MANAGER_ID).name(UPDATED_NAME);
        return roomManager;
    }

    @BeforeEach
    public void initTest() {
        roomManager = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomManager() throws Exception {
        int databaseSizeBeforeCreate = roomManagerRepository.findAll().size();
        // Create the RoomManager
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);
        restRoomManagerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeCreate + 1);
        RoomManager testRoomManager = roomManagerList.get(roomManagerList.size() - 1);
        assertThat(testRoomManager.getManagerId()).isEqualTo(DEFAULT_MANAGER_ID);
        assertThat(testRoomManager.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRoomManagerWithExistingId() throws Exception {
        // Create the RoomManager with an existing ID
        roomManager.setId(1L);
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        int databaseSizeBeforeCreate = roomManagerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomManagerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomManagerRepository.findAll().size();
        // set the field null
        roomManager.setName(null);

        // Create the RoomManager, which fails.
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        restRoomManagerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isBadRequest());

        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoomManagers() throws Exception {
        // Initialize the database
        roomManagerRepository.saveAndFlush(roomManager);

        // Get all the roomManagerList
        restRoomManagerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].managerId").value(hasItem(DEFAULT_MANAGER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRoomManager() throws Exception {
        // Initialize the database
        roomManagerRepository.saveAndFlush(roomManager);

        // Get the roomManager
        restRoomManagerMockMvc
            .perform(get(ENTITY_API_URL_ID, roomManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomManager.getId().intValue()))
            .andExpect(jsonPath("$.managerId").value(DEFAULT_MANAGER_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRoomManager() throws Exception {
        // Get the roomManager
        restRoomManagerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoomManager() throws Exception {
        // Initialize the database
        roomManagerRepository.saveAndFlush(roomManager);

        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();

        // Update the roomManager
        RoomManager updatedRoomManager = roomManagerRepository.findById(roomManager.getId()).get();
        // Disconnect from session so that the updates on updatedRoomManager are not directly saved in db
        em.detach(updatedRoomManager);
        updatedRoomManager.managerId(UPDATED_MANAGER_ID).name(UPDATED_NAME);
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(updatedRoomManager);

        restRoomManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomManagerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isOk());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
        RoomManager testRoomManager = roomManagerList.get(roomManagerList.size() - 1);
        assertThat(testRoomManager.getManagerId()).isEqualTo(UPDATED_MANAGER_ID);
        assertThat(testRoomManager.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRoomManager() throws Exception {
        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();
        roomManager.setId(count.incrementAndGet());

        // Create the RoomManager
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomManagerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomManager() throws Exception {
        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();
        roomManager.setId(count.incrementAndGet());

        // Create the RoomManager
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomManagerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomManager() throws Exception {
        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();
        roomManager.setId(count.incrementAndGet());

        // Create the RoomManager
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomManagerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomManagerWithPatch() throws Exception {
        // Initialize the database
        roomManagerRepository.saveAndFlush(roomManager);

        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();

        // Update the roomManager using partial update
        RoomManager partialUpdatedRoomManager = new RoomManager();
        partialUpdatedRoomManager.setId(roomManager.getId());

        partialUpdatedRoomManager.name(UPDATED_NAME);

        restRoomManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomManager.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomManager))
            )
            .andExpect(status().isOk());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
        RoomManager testRoomManager = roomManagerList.get(roomManagerList.size() - 1);
        assertThat(testRoomManager.getManagerId()).isEqualTo(DEFAULT_MANAGER_ID);
        assertThat(testRoomManager.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRoomManagerWithPatch() throws Exception {
        // Initialize the database
        roomManagerRepository.saveAndFlush(roomManager);

        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();

        // Update the roomManager using partial update
        RoomManager partialUpdatedRoomManager = new RoomManager();
        partialUpdatedRoomManager.setId(roomManager.getId());

        partialUpdatedRoomManager.managerId(UPDATED_MANAGER_ID).name(UPDATED_NAME);

        restRoomManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomManager.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomManager))
            )
            .andExpect(status().isOk());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
        RoomManager testRoomManager = roomManagerList.get(roomManagerList.size() - 1);
        assertThat(testRoomManager.getManagerId()).isEqualTo(UPDATED_MANAGER_ID);
        assertThat(testRoomManager.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRoomManager() throws Exception {
        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();
        roomManager.setId(count.incrementAndGet());

        // Create the RoomManager
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomManagerDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomManager() throws Exception {
        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();
        roomManager.setId(count.incrementAndGet());

        // Create the RoomManager
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomManagerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomManager() throws Exception {
        int databaseSizeBeforeUpdate = roomManagerRepository.findAll().size();
        roomManager.setId(count.incrementAndGet());

        // Create the RoomManager
        RoomManagerDTO roomManagerDTO = roomManagerMapper.toDto(roomManager);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomManagerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomManagerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomManager in the database
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomManager() throws Exception {
        // Initialize the database
        roomManagerRepository.saveAndFlush(roomManager);

        int databaseSizeBeforeDelete = roomManagerRepository.findAll().size();

        // Delete the roomManager
        restRoomManagerMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomManager.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomManager> roomManagerList = roomManagerRepository.findAll();
        assertThat(roomManagerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
