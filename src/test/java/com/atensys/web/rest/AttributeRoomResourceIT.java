package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.AttributeRoom;
import com.atensys.repository.AttributeRoomRepository;
import com.atensys.service.dto.AttributeRoomDTO;
import com.atensys.service.mapper.AttributeRoomMapper;
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
 * Integration tests for the {@link AttributeRoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeRoomResourceIT {

    private static final String ENTITY_API_URL = "/api/attribute-rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeRoomRepository attributeRoomRepository;

    @Autowired
    private AttributeRoomMapper attributeRoomMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeRoomMockMvc;

    private AttributeRoom attributeRoom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeRoom createEntity(EntityManager em) {
        AttributeRoom attributeRoom = new AttributeRoom();
        return attributeRoom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeRoom createUpdatedEntity(EntityManager em) {
        AttributeRoom attributeRoom = new AttributeRoom();
        return attributeRoom;
    }

    @BeforeEach
    public void initTest() {
        attributeRoom = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributeRoom() throws Exception {
        int databaseSizeBeforeCreate = attributeRoomRepository.findAll().size();
        // Create the AttributeRoom
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);
        restAttributeRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeCreate + 1);
        AttributeRoom testAttributeRoom = attributeRoomList.get(attributeRoomList.size() - 1);
    }

    @Test
    @Transactional
    void createAttributeRoomWithExistingId() throws Exception {
        // Create the AttributeRoom with an existing ID
        attributeRoom.setId(1L);
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);

        int databaseSizeBeforeCreate = attributeRoomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributeRooms() throws Exception {
        // Initialize the database
        attributeRoomRepository.saveAndFlush(attributeRoom);

        // Get all the attributeRoomList
        restAttributeRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributeRoom.getId().intValue())));
    }

    @Test
    @Transactional
    void getAttributeRoom() throws Exception {
        // Initialize the database
        attributeRoomRepository.saveAndFlush(attributeRoom);

        // Get the attributeRoom
        restAttributeRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, attributeRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributeRoom.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAttributeRoom() throws Exception {
        // Get the attributeRoom
        restAttributeRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttributeRoom() throws Exception {
        // Initialize the database
        attributeRoomRepository.saveAndFlush(attributeRoom);

        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();

        // Update the attributeRoom
        AttributeRoom updatedAttributeRoom = attributeRoomRepository.findById(attributeRoom.getId()).get();
        // Disconnect from session so that the updates on updatedAttributeRoom are not directly saved in db
        em.detach(updatedAttributeRoom);
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(updatedAttributeRoom);

        restAttributeRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeRoomDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
        AttributeRoom testAttributeRoom = attributeRoomList.get(attributeRoomList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAttributeRoom() throws Exception {
        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();
        attributeRoom.setId(count.incrementAndGet());

        // Create the AttributeRoom
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeRoomDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributeRoom() throws Exception {
        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();
        attributeRoom.setId(count.incrementAndGet());

        // Create the AttributeRoom
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributeRoom() throws Exception {
        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();
        attributeRoom.setId(count.incrementAndGet());

        // Create the AttributeRoom
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeRoomMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeRoomWithPatch() throws Exception {
        // Initialize the database
        attributeRoomRepository.saveAndFlush(attributeRoom);

        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();

        // Update the attributeRoom using partial update
        AttributeRoom partialUpdatedAttributeRoom = new AttributeRoom();
        partialUpdatedAttributeRoom.setId(attributeRoom.getId());

        restAttributeRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeRoom.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeRoom))
            )
            .andExpect(status().isOk());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
        AttributeRoom testAttributeRoom = attributeRoomList.get(attributeRoomList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAttributeRoomWithPatch() throws Exception {
        // Initialize the database
        attributeRoomRepository.saveAndFlush(attributeRoom);

        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();

        // Update the attributeRoom using partial update
        AttributeRoom partialUpdatedAttributeRoom = new AttributeRoom();
        partialUpdatedAttributeRoom.setId(attributeRoom.getId());

        restAttributeRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeRoom.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeRoom))
            )
            .andExpect(status().isOk());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
        AttributeRoom testAttributeRoom = attributeRoomList.get(attributeRoomList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAttributeRoom() throws Exception {
        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();
        attributeRoom.setId(count.incrementAndGet());

        // Create the AttributeRoom
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributeRoomDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributeRoom() throws Exception {
        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();
        attributeRoom.setId(count.incrementAndGet());

        // Create the AttributeRoom
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributeRoom() throws Exception {
        int databaseSizeBeforeUpdate = attributeRoomRepository.findAll().size();
        attributeRoom.setId(count.incrementAndGet());

        // Create the AttributeRoom
        AttributeRoomDTO attributeRoomDTO = attributeRoomMapper.toDto(attributeRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeRoomMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeRoomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeRoom in the database
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributeRoom() throws Exception {
        // Initialize the database
        attributeRoomRepository.saveAndFlush(attributeRoom);

        int databaseSizeBeforeDelete = attributeRoomRepository.findAll().size();

        // Delete the attributeRoom
        restAttributeRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributeRoom.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributeRoom> attributeRoomList = attributeRoomRepository.findAll();
        assertThat(attributeRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
