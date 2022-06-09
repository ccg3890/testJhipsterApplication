package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.AttributeFloor;
import com.atensys.repository.AttributeFloorRepository;
import com.atensys.service.dto.AttributeFloorDTO;
import com.atensys.service.mapper.AttributeFloorMapper;
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
 * Integration tests for the {@link AttributeFloorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeFloorResourceIT {

    private static final String ENTITY_API_URL = "/api/attribute-floors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeFloorRepository attributeFloorRepository;

    @Autowired
    private AttributeFloorMapper attributeFloorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeFloorMockMvc;

    private AttributeFloor attributeFloor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeFloor createEntity(EntityManager em) {
        AttributeFloor attributeFloor = new AttributeFloor();
        return attributeFloor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeFloor createUpdatedEntity(EntityManager em) {
        AttributeFloor attributeFloor = new AttributeFloor();
        return attributeFloor;
    }

    @BeforeEach
    public void initTest() {
        attributeFloor = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributeFloor() throws Exception {
        int databaseSizeBeforeCreate = attributeFloorRepository.findAll().size();
        // Create the AttributeFloor
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);
        restAttributeFloorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeCreate + 1);
        AttributeFloor testAttributeFloor = attributeFloorList.get(attributeFloorList.size() - 1);
    }

    @Test
    @Transactional
    void createAttributeFloorWithExistingId() throws Exception {
        // Create the AttributeFloor with an existing ID
        attributeFloor.setId(1L);
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);

        int databaseSizeBeforeCreate = attributeFloorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeFloorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributeFloors() throws Exception {
        // Initialize the database
        attributeFloorRepository.saveAndFlush(attributeFloor);

        // Get all the attributeFloorList
        restAttributeFloorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributeFloor.getId().intValue())));
    }

    @Test
    @Transactional
    void getAttributeFloor() throws Exception {
        // Initialize the database
        attributeFloorRepository.saveAndFlush(attributeFloor);

        // Get the attributeFloor
        restAttributeFloorMockMvc
            .perform(get(ENTITY_API_URL_ID, attributeFloor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributeFloor.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAttributeFloor() throws Exception {
        // Get the attributeFloor
        restAttributeFloorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttributeFloor() throws Exception {
        // Initialize the database
        attributeFloorRepository.saveAndFlush(attributeFloor);

        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();

        // Update the attributeFloor
        AttributeFloor updatedAttributeFloor = attributeFloorRepository.findById(attributeFloor.getId()).get();
        // Disconnect from session so that the updates on updatedAttributeFloor are not directly saved in db
        em.detach(updatedAttributeFloor);
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(updatedAttributeFloor);

        restAttributeFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeFloorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
        AttributeFloor testAttributeFloor = attributeFloorList.get(attributeFloorList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAttributeFloor() throws Exception {
        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();
        attributeFloor.setId(count.incrementAndGet());

        // Create the AttributeFloor
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeFloorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributeFloor() throws Exception {
        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();
        attributeFloor.setId(count.incrementAndGet());

        // Create the AttributeFloor
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributeFloor() throws Exception {
        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();
        attributeFloor.setId(count.incrementAndGet());

        // Create the AttributeFloor
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeFloorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeFloorWithPatch() throws Exception {
        // Initialize the database
        attributeFloorRepository.saveAndFlush(attributeFloor);

        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();

        // Update the attributeFloor using partial update
        AttributeFloor partialUpdatedAttributeFloor = new AttributeFloor();
        partialUpdatedAttributeFloor.setId(attributeFloor.getId());

        restAttributeFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeFloor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeFloor))
            )
            .andExpect(status().isOk());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
        AttributeFloor testAttributeFloor = attributeFloorList.get(attributeFloorList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAttributeFloorWithPatch() throws Exception {
        // Initialize the database
        attributeFloorRepository.saveAndFlush(attributeFloor);

        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();

        // Update the attributeFloor using partial update
        AttributeFloor partialUpdatedAttributeFloor = new AttributeFloor();
        partialUpdatedAttributeFloor.setId(attributeFloor.getId());

        restAttributeFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeFloor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeFloor))
            )
            .andExpect(status().isOk());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
        AttributeFloor testAttributeFloor = attributeFloorList.get(attributeFloorList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAttributeFloor() throws Exception {
        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();
        attributeFloor.setId(count.incrementAndGet());

        // Create the AttributeFloor
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributeFloorDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributeFloor() throws Exception {
        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();
        attributeFloor.setId(count.incrementAndGet());

        // Create the AttributeFloor
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributeFloor() throws Exception {
        int databaseSizeBeforeUpdate = attributeFloorRepository.findAll().size();
        attributeFloor.setId(count.incrementAndGet());

        // Create the AttributeFloor
        AttributeFloorDTO attributeFloorDTO = attributeFloorMapper.toDto(attributeFloor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeFloorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeFloorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeFloor in the database
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributeFloor() throws Exception {
        // Initialize the database
        attributeFloorRepository.saveAndFlush(attributeFloor);

        int databaseSizeBeforeDelete = attributeFloorRepository.findAll().size();

        // Delete the attributeFloor
        restAttributeFloorMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributeFloor.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributeFloor> attributeFloorList = attributeFloorRepository.findAll();
        assertThat(attributeFloorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
