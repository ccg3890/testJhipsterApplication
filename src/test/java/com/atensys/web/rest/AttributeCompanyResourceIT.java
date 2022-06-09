package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.AttributeCompany;
import com.atensys.repository.AttributeCompanyRepository;
import com.atensys.service.dto.AttributeCompanyDTO;
import com.atensys.service.mapper.AttributeCompanyMapper;
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
 * Integration tests for the {@link AttributeCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeCompanyResourceIT {

    private static final String ENTITY_API_URL = "/api/attribute-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeCompanyRepository attributeCompanyRepository;

    @Autowired
    private AttributeCompanyMapper attributeCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeCompanyMockMvc;

    private AttributeCompany attributeCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeCompany createEntity(EntityManager em) {
        AttributeCompany attributeCompany = new AttributeCompany();
        return attributeCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeCompany createUpdatedEntity(EntityManager em) {
        AttributeCompany attributeCompany = new AttributeCompany();
        return attributeCompany;
    }

    @BeforeEach
    public void initTest() {
        attributeCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributeCompany() throws Exception {
        int databaseSizeBeforeCreate = attributeCompanyRepository.findAll().size();
        // Create the AttributeCompany
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);
        restAttributeCompanyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        AttributeCompany testAttributeCompany = attributeCompanyList.get(attributeCompanyList.size() - 1);
    }

    @Test
    @Transactional
    void createAttributeCompanyWithExistingId() throws Exception {
        // Create the AttributeCompany with an existing ID
        attributeCompany.setId(1L);
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);

        int databaseSizeBeforeCreate = attributeCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeCompanyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributeCompanies() throws Exception {
        // Initialize the database
        attributeCompanyRepository.saveAndFlush(attributeCompany);

        // Get all the attributeCompanyList
        restAttributeCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributeCompany.getId().intValue())));
    }

    @Test
    @Transactional
    void getAttributeCompany() throws Exception {
        // Initialize the database
        attributeCompanyRepository.saveAndFlush(attributeCompany);

        // Get the attributeCompany
        restAttributeCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, attributeCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributeCompany.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAttributeCompany() throws Exception {
        // Get the attributeCompany
        restAttributeCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttributeCompany() throws Exception {
        // Initialize the database
        attributeCompanyRepository.saveAndFlush(attributeCompany);

        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();

        // Update the attributeCompany
        AttributeCompany updatedAttributeCompany = attributeCompanyRepository.findById(attributeCompany.getId()).get();
        // Disconnect from session so that the updates on updatedAttributeCompany are not directly saved in db
        em.detach(updatedAttributeCompany);
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(updatedAttributeCompany);

        restAttributeCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeCompanyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
        AttributeCompany testAttributeCompany = attributeCompanyList.get(attributeCompanyList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAttributeCompany() throws Exception {
        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();
        attributeCompany.setId(count.incrementAndGet());

        // Create the AttributeCompany
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeCompanyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributeCompany() throws Exception {
        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();
        attributeCompany.setId(count.incrementAndGet());

        // Create the AttributeCompany
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributeCompany() throws Exception {
        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();
        attributeCompany.setId(count.incrementAndGet());

        // Create the AttributeCompany
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeCompanyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeCompanyWithPatch() throws Exception {
        // Initialize the database
        attributeCompanyRepository.saveAndFlush(attributeCompany);

        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();

        // Update the attributeCompany using partial update
        AttributeCompany partialUpdatedAttributeCompany = new AttributeCompany();
        partialUpdatedAttributeCompany.setId(attributeCompany.getId());

        restAttributeCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeCompany.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeCompany))
            )
            .andExpect(status().isOk());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
        AttributeCompany testAttributeCompany = attributeCompanyList.get(attributeCompanyList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAttributeCompanyWithPatch() throws Exception {
        // Initialize the database
        attributeCompanyRepository.saveAndFlush(attributeCompany);

        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();

        // Update the attributeCompany using partial update
        AttributeCompany partialUpdatedAttributeCompany = new AttributeCompany();
        partialUpdatedAttributeCompany.setId(attributeCompany.getId());

        restAttributeCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeCompany.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeCompany))
            )
            .andExpect(status().isOk());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
        AttributeCompany testAttributeCompany = attributeCompanyList.get(attributeCompanyList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAttributeCompany() throws Exception {
        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();
        attributeCompany.setId(count.incrementAndGet());

        // Create the AttributeCompany
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributeCompanyDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributeCompany() throws Exception {
        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();
        attributeCompany.setId(count.incrementAndGet());

        // Create the AttributeCompany
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributeCompany() throws Exception {
        int databaseSizeBeforeUpdate = attributeCompanyRepository.findAll().size();
        attributeCompany.setId(count.incrementAndGet());

        // Create the AttributeCompany
        AttributeCompanyDTO attributeCompanyDTO = attributeCompanyMapper.toDto(attributeCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeCompany in the database
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributeCompany() throws Exception {
        // Initialize the database
        attributeCompanyRepository.saveAndFlush(attributeCompany);

        int databaseSizeBeforeDelete = attributeCompanyRepository.findAll().size();

        // Delete the attributeCompany
        restAttributeCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributeCompany.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributeCompany> attributeCompanyList = attributeCompanyRepository.findAll();
        assertThat(attributeCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
