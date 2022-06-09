package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.RoomUserGroup;
import com.atensys.repository.RoomUserGroupRepository;
import com.atensys.service.dto.RoomUserGroupDTO;
import com.atensys.service.mapper.RoomUserGroupMapper;
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
 * Integration tests for the {@link RoomUserGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomUserGroupResourceIT {

    private static final String DEFAULT_GROUP_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/room-user-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomUserGroupRepository roomUserGroupRepository;

    @Autowired
    private RoomUserGroupMapper roomUserGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomUserGroupMockMvc;

    private RoomUserGroup roomUserGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomUserGroup createEntity(EntityManager em) {
        RoomUserGroup roomUserGroup = new RoomUserGroup().groupId(DEFAULT_GROUP_ID).name(DEFAULT_NAME);
        return roomUserGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomUserGroup createUpdatedEntity(EntityManager em) {
        RoomUserGroup roomUserGroup = new RoomUserGroup().groupId(UPDATED_GROUP_ID).name(UPDATED_NAME);
        return roomUserGroup;
    }

    @BeforeEach
    public void initTest() {
        roomUserGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomUserGroup() throws Exception {
        int databaseSizeBeforeCreate = roomUserGroupRepository.findAll().size();
        // Create the RoomUserGroup
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);
        restRoomUserGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeCreate + 1);
        RoomUserGroup testRoomUserGroup = roomUserGroupList.get(roomUserGroupList.size() - 1);
        assertThat(testRoomUserGroup.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testRoomUserGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRoomUserGroupWithExistingId() throws Exception {
        // Create the RoomUserGroup with an existing ID
        roomUserGroup.setId(1L);
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        int databaseSizeBeforeCreate = roomUserGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomUserGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomUserGroupRepository.findAll().size();
        // set the field null
        roomUserGroup.setGroupId(null);

        // Create the RoomUserGroup, which fails.
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        restRoomUserGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomUserGroupRepository.findAll().size();
        // set the field null
        roomUserGroup.setName(null);

        // Create the RoomUserGroup, which fails.
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        restRoomUserGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoomUserGroups() throws Exception {
        // Initialize the database
        roomUserGroupRepository.saveAndFlush(roomUserGroup);

        // Get all the roomUserGroupList
        restRoomUserGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomUserGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRoomUserGroup() throws Exception {
        // Initialize the database
        roomUserGroupRepository.saveAndFlush(roomUserGroup);

        // Get the roomUserGroup
        restRoomUserGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, roomUserGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomUserGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRoomUserGroup() throws Exception {
        // Get the roomUserGroup
        restRoomUserGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoomUserGroup() throws Exception {
        // Initialize the database
        roomUserGroupRepository.saveAndFlush(roomUserGroup);

        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();

        // Update the roomUserGroup
        RoomUserGroup updatedRoomUserGroup = roomUserGroupRepository.findById(roomUserGroup.getId()).get();
        // Disconnect from session so that the updates on updatedRoomUserGroup are not directly saved in db
        em.detach(updatedRoomUserGroup);
        updatedRoomUserGroup.groupId(UPDATED_GROUP_ID).name(UPDATED_NAME);
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(updatedRoomUserGroup);

        restRoomUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomUserGroupDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
        RoomUserGroup testRoomUserGroup = roomUserGroupList.get(roomUserGroupList.size() - 1);
        assertThat(testRoomUserGroup.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testRoomUserGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRoomUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();
        roomUserGroup.setId(count.incrementAndGet());

        // Create the RoomUserGroup
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomUserGroupDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();
        roomUserGroup.setId(count.incrementAndGet());

        // Create the RoomUserGroup
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();
        roomUserGroup.setId(count.incrementAndGet());

        // Create the RoomUserGroup
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomUserGroupWithPatch() throws Exception {
        // Initialize the database
        roomUserGroupRepository.saveAndFlush(roomUserGroup);

        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();

        // Update the roomUserGroup using partial update
        RoomUserGroup partialUpdatedRoomUserGroup = new RoomUserGroup();
        partialUpdatedRoomUserGroup.setId(roomUserGroup.getId());

        partialUpdatedRoomUserGroup.name(UPDATED_NAME);

        restRoomUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomUserGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
        RoomUserGroup testRoomUserGroup = roomUserGroupList.get(roomUserGroupList.size() - 1);
        assertThat(testRoomUserGroup.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testRoomUserGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRoomUserGroupWithPatch() throws Exception {
        // Initialize the database
        roomUserGroupRepository.saveAndFlush(roomUserGroup);

        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();

        // Update the roomUserGroup using partial update
        RoomUserGroup partialUpdatedRoomUserGroup = new RoomUserGroup();
        partialUpdatedRoomUserGroup.setId(roomUserGroup.getId());

        partialUpdatedRoomUserGroup.groupId(UPDATED_GROUP_ID).name(UPDATED_NAME);

        restRoomUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomUserGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
        RoomUserGroup testRoomUserGroup = roomUserGroupList.get(roomUserGroupList.size() - 1);
        assertThat(testRoomUserGroup.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testRoomUserGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRoomUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();
        roomUserGroup.setId(count.incrementAndGet());

        // Create the RoomUserGroup
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomUserGroupDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();
        roomUserGroup.setId(count.incrementAndGet());

        // Create the RoomUserGroup
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = roomUserGroupRepository.findAll().size();
        roomUserGroup.setId(count.incrementAndGet());

        // Create the RoomUserGroup
        RoomUserGroupDTO roomUserGroupDTO = roomUserGroupMapper.toDto(roomUserGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomUserGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomUserGroup in the database
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomUserGroup() throws Exception {
        // Initialize the database
        roomUserGroupRepository.saveAndFlush(roomUserGroup);

        int databaseSizeBeforeDelete = roomUserGroupRepository.findAll().size();

        // Delete the roomUserGroup
        restRoomUserGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomUserGroup.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomUserGroup> roomUserGroupList = roomUserGroupRepository.findAll();
        assertThat(roomUserGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
