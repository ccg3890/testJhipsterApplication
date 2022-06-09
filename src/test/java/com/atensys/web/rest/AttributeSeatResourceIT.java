package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.AttributeSeat;
import com.atensys.repository.AttributeSeatRepository;
import com.atensys.service.dto.AttributeSeatDTO;
import com.atensys.service.mapper.AttributeSeatMapper;
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
 * Integration tests for the {@link AttributeSeatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeSeatResourceIT {

    private static final String ENTITY_API_URL = "/api/attribute-seats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeSeatRepository attributeSeatRepository;

    @Autowired
    private AttributeSeatMapper attributeSeatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeSeatMockMvc;

    private AttributeSeat attributeSeat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeSeat createEntity(EntityManager em) {
        AttributeSeat attributeSeat = new AttributeSeat();
        return attributeSeat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeSeat createUpdatedEntity(EntityManager em) {
        AttributeSeat attributeSeat = new AttributeSeat();
        return attributeSeat;
    }

    @BeforeEach
    public void initTest() {
        attributeSeat = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributeSeat() throws Exception {
        int databaseSizeBeforeCreate = attributeSeatRepository.findAll().size();
        // Create the AttributeSeat
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);
        restAttributeSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeCreate + 1);
        AttributeSeat testAttributeSeat = attributeSeatList.get(attributeSeatList.size() - 1);
    }

    @Test
    @Transactional
    void createAttributeSeatWithExistingId() throws Exception {
        // Create the AttributeSeat with an existing ID
        attributeSeat.setId(1L);
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);

        int databaseSizeBeforeCreate = attributeSeatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributeSeats() throws Exception {
        // Initialize the database
        attributeSeatRepository.saveAndFlush(attributeSeat);

        // Get all the attributeSeatList
        restAttributeSeatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributeSeat.getId().intValue())));
    }

    @Test
    @Transactional
    void getAttributeSeat() throws Exception {
        // Initialize the database
        attributeSeatRepository.saveAndFlush(attributeSeat);

        // Get the attributeSeat
        restAttributeSeatMockMvc
            .perform(get(ENTITY_API_URL_ID, attributeSeat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributeSeat.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAttributeSeat() throws Exception {
        // Get the attributeSeat
        restAttributeSeatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttributeSeat() throws Exception {
        // Initialize the database
        attributeSeatRepository.saveAndFlush(attributeSeat);

        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();

        // Update the attributeSeat
        AttributeSeat updatedAttributeSeat = attributeSeatRepository.findById(attributeSeat.getId()).get();
        // Disconnect from session so that the updates on updatedAttributeSeat are not directly saved in db
        em.detach(updatedAttributeSeat);
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(updatedAttributeSeat);

        restAttributeSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeSeatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
        AttributeSeat testAttributeSeat = attributeSeatList.get(attributeSeatList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAttributeSeat() throws Exception {
        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();
        attributeSeat.setId(count.incrementAndGet());

        // Create the AttributeSeat
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeSeatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributeSeat() throws Exception {
        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();
        attributeSeat.setId(count.incrementAndGet());

        // Create the AttributeSeat
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributeSeat() throws Exception {
        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();
        attributeSeat.setId(count.incrementAndGet());

        // Create the AttributeSeat
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeSeatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeSeatWithPatch() throws Exception {
        // Initialize the database
        attributeSeatRepository.saveAndFlush(attributeSeat);

        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();

        // Update the attributeSeat using partial update
        AttributeSeat partialUpdatedAttributeSeat = new AttributeSeat();
        partialUpdatedAttributeSeat.setId(attributeSeat.getId());

        restAttributeSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeSeat))
            )
            .andExpect(status().isOk());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
        AttributeSeat testAttributeSeat = attributeSeatList.get(attributeSeatList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAttributeSeatWithPatch() throws Exception {
        // Initialize the database
        attributeSeatRepository.saveAndFlush(attributeSeat);

        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();

        // Update the attributeSeat using partial update
        AttributeSeat partialUpdatedAttributeSeat = new AttributeSeat();
        partialUpdatedAttributeSeat.setId(attributeSeat.getId());

        restAttributeSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeSeat))
            )
            .andExpect(status().isOk());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
        AttributeSeat testAttributeSeat = attributeSeatList.get(attributeSeatList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAttributeSeat() throws Exception {
        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();
        attributeSeat.setId(count.incrementAndGet());

        // Create the AttributeSeat
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributeSeatDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributeSeat() throws Exception {
        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();
        attributeSeat.setId(count.incrementAndGet());

        // Create the AttributeSeat
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributeSeat() throws Exception {
        int databaseSizeBeforeUpdate = attributeSeatRepository.findAll().size();
        attributeSeat.setId(count.incrementAndGet());

        // Create the AttributeSeat
        AttributeSeatDTO attributeSeatDTO = attributeSeatMapper.toDto(attributeSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeSeatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeSeatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeSeat in the database
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributeSeat() throws Exception {
        // Initialize the database
        attributeSeatRepository.saveAndFlush(attributeSeat);

        int databaseSizeBeforeDelete = attributeSeatRepository.findAll().size();

        // Delete the attributeSeat
        restAttributeSeatMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributeSeat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributeSeat> attributeSeatList = attributeSeatRepository.findAll();
        assertThat(attributeSeatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
