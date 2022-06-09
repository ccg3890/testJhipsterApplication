package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.AttributeOffice;
import com.atensys.repository.AttributeOfficeRepository;
import com.atensys.service.dto.AttributeOfficeDTO;
import com.atensys.service.mapper.AttributeOfficeMapper;
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
 * Integration tests for the {@link AttributeOfficeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeOfficeResourceIT {

    private static final String ENTITY_API_URL = "/api/attribute-offices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeOfficeRepository attributeOfficeRepository;

    @Autowired
    private AttributeOfficeMapper attributeOfficeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeOfficeMockMvc;

    private AttributeOffice attributeOffice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeOffice createEntity(EntityManager em) {
        AttributeOffice attributeOffice = new AttributeOffice();
        return attributeOffice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeOffice createUpdatedEntity(EntityManager em) {
        AttributeOffice attributeOffice = new AttributeOffice();
        return attributeOffice;
    }

    @BeforeEach
    public void initTest() {
        attributeOffice = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributeOffice() throws Exception {
        int databaseSizeBeforeCreate = attributeOfficeRepository.findAll().size();
        // Create the AttributeOffice
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);
        restAttributeOfficeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeCreate + 1);
        AttributeOffice testAttributeOffice = attributeOfficeList.get(attributeOfficeList.size() - 1);
    }

    @Test
    @Transactional
    void createAttributeOfficeWithExistingId() throws Exception {
        // Create the AttributeOffice with an existing ID
        attributeOffice.setId(1L);
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);

        int databaseSizeBeforeCreate = attributeOfficeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeOfficeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributeOffices() throws Exception {
        // Initialize the database
        attributeOfficeRepository.saveAndFlush(attributeOffice);

        // Get all the attributeOfficeList
        restAttributeOfficeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributeOffice.getId().intValue())));
    }

    @Test
    @Transactional
    void getAttributeOffice() throws Exception {
        // Initialize the database
        attributeOfficeRepository.saveAndFlush(attributeOffice);

        // Get the attributeOffice
        restAttributeOfficeMockMvc
            .perform(get(ENTITY_API_URL_ID, attributeOffice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributeOffice.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAttributeOffice() throws Exception {
        // Get the attributeOffice
        restAttributeOfficeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttributeOffice() throws Exception {
        // Initialize the database
        attributeOfficeRepository.saveAndFlush(attributeOffice);

        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();

        // Update the attributeOffice
        AttributeOffice updatedAttributeOffice = attributeOfficeRepository.findById(attributeOffice.getId()).get();
        // Disconnect from session so that the updates on updatedAttributeOffice are not directly saved in db
        em.detach(updatedAttributeOffice);
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(updatedAttributeOffice);

        restAttributeOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeOfficeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
        AttributeOffice testAttributeOffice = attributeOfficeList.get(attributeOfficeList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAttributeOffice() throws Exception {
        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();
        attributeOffice.setId(count.incrementAndGet());

        // Create the AttributeOffice
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeOfficeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributeOffice() throws Exception {
        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();
        attributeOffice.setId(count.incrementAndGet());

        // Create the AttributeOffice
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributeOffice() throws Exception {
        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();
        attributeOffice.setId(count.incrementAndGet());

        // Create the AttributeOffice
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeOfficeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeOfficeWithPatch() throws Exception {
        // Initialize the database
        attributeOfficeRepository.saveAndFlush(attributeOffice);

        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();

        // Update the attributeOffice using partial update
        AttributeOffice partialUpdatedAttributeOffice = new AttributeOffice();
        partialUpdatedAttributeOffice.setId(attributeOffice.getId());

        restAttributeOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeOffice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeOffice))
            )
            .andExpect(status().isOk());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
        AttributeOffice testAttributeOffice = attributeOfficeList.get(attributeOfficeList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAttributeOfficeWithPatch() throws Exception {
        // Initialize the database
        attributeOfficeRepository.saveAndFlush(attributeOffice);

        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();

        // Update the attributeOffice using partial update
        AttributeOffice partialUpdatedAttributeOffice = new AttributeOffice();
        partialUpdatedAttributeOffice.setId(attributeOffice.getId());

        restAttributeOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeOffice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeOffice))
            )
            .andExpect(status().isOk());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
        AttributeOffice testAttributeOffice = attributeOfficeList.get(attributeOfficeList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAttributeOffice() throws Exception {
        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();
        attributeOffice.setId(count.incrementAndGet());

        // Create the AttributeOffice
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributeOfficeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributeOffice() throws Exception {
        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();
        attributeOffice.setId(count.incrementAndGet());

        // Create the AttributeOffice
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributeOffice() throws Exception {
        int databaseSizeBeforeUpdate = attributeOfficeRepository.findAll().size();
        attributeOffice.setId(count.incrementAndGet());

        // Create the AttributeOffice
        AttributeOfficeDTO attributeOfficeDTO = attributeOfficeMapper.toDto(attributeOffice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeOfficeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeOffice in the database
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributeOffice() throws Exception {
        // Initialize the database
        attributeOfficeRepository.saveAndFlush(attributeOffice);

        int databaseSizeBeforeDelete = attributeOfficeRepository.findAll().size();

        // Delete the attributeOffice
        restAttributeOfficeMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributeOffice.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributeOffice> attributeOfficeList = attributeOfficeRepository.findAll();
        assertThat(attributeOfficeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
