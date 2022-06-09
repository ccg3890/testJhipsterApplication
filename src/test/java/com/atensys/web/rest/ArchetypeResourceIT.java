package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Archetype;
import com.atensys.domain.enumeration.AttributeType;
import com.atensys.repository.ArchetypeRepository;
import com.atensys.service.ArchetypeService;
import com.atensys.service.dto.ArchetypeDTO;
import com.atensys.service.mapper.ArchetypeMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArchetypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArchetypeResourceIT {

    private static final AttributeType DEFAULT_ATTRIBUTE_TYPE = AttributeType.GLOBAL;
    private static final AttributeType UPDATED_ATTRIBUTE_TYPE = AttributeType.COMPANY;

    private static final Boolean DEFAULT_MANDANTORY = false;
    private static final Boolean UPDATED_MANDANTORY = true;

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/archetypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArchetypeRepository archetypeRepository;

    @Mock
    private ArchetypeRepository archetypeRepositoryMock;

    @Autowired
    private ArchetypeMapper archetypeMapper;

    @Mock
    private ArchetypeService archetypeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArchetypeMockMvc;

    private Archetype archetype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archetype createEntity(EntityManager em) {
        Archetype archetype = new Archetype().attributeType(DEFAULT_ATTRIBUTE_TYPE).mandantory(DEFAULT_MANDANTORY).tag(DEFAULT_TAG);
        return archetype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Archetype createUpdatedEntity(EntityManager em) {
        Archetype archetype = new Archetype().attributeType(UPDATED_ATTRIBUTE_TYPE).mandantory(UPDATED_MANDANTORY).tag(UPDATED_TAG);
        return archetype;
    }

    @BeforeEach
    public void initTest() {
        archetype = createEntity(em);
    }

    @Test
    @Transactional
    void createArchetype() throws Exception {
        int databaseSizeBeforeCreate = archetypeRepository.findAll().size();
        // Create the Archetype
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);
        restArchetypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeCreate + 1);
        Archetype testArchetype = archetypeList.get(archetypeList.size() - 1);
        assertThat(testArchetype.getAttributeType()).isEqualTo(DEFAULT_ATTRIBUTE_TYPE);
        assertThat(testArchetype.getMandantory()).isEqualTo(DEFAULT_MANDANTORY);
        assertThat(testArchetype.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    void createArchetypeWithExistingId() throws Exception {
        // Create the Archetype with an existing ID
        archetype.setId(1L);
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        int databaseSizeBeforeCreate = archetypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchetypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttributeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = archetypeRepository.findAll().size();
        // set the field null
        archetype.setAttributeType(null);

        // Create the Archetype, which fails.
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        restArchetypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMandantoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = archetypeRepository.findAll().size();
        // set the field null
        archetype.setMandantory(null);

        // Create the Archetype, which fails.
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        restArchetypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArchetypes() throws Exception {
        // Initialize the database
        archetypeRepository.saveAndFlush(archetype);

        // Get all the archetypeList
        restArchetypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeType").value(hasItem(DEFAULT_ATTRIBUTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mandantory").value(hasItem(DEFAULT_MANDANTORY.booleanValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArchetypesWithEagerRelationshipsIsEnabled() throws Exception {
        when(archetypeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArchetypeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(archetypeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArchetypesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(archetypeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArchetypeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(archetypeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getArchetype() throws Exception {
        // Initialize the database
        archetypeRepository.saveAndFlush(archetype);

        // Get the archetype
        restArchetypeMockMvc
            .perform(get(ENTITY_API_URL_ID, archetype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archetype.getId().intValue()))
            .andExpect(jsonPath("$.attributeType").value(DEFAULT_ATTRIBUTE_TYPE.toString()))
            .andExpect(jsonPath("$.mandantory").value(DEFAULT_MANDANTORY.booleanValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG));
    }

    @Test
    @Transactional
    void getNonExistingArchetype() throws Exception {
        // Get the archetype
        restArchetypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArchetype() throws Exception {
        // Initialize the database
        archetypeRepository.saveAndFlush(archetype);

        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();

        // Update the archetype
        Archetype updatedArchetype = archetypeRepository.findById(archetype.getId()).get();
        // Disconnect from session so that the updates on updatedArchetype are not directly saved in db
        em.detach(updatedArchetype);
        updatedArchetype.attributeType(UPDATED_ATTRIBUTE_TYPE).mandantory(UPDATED_MANDANTORY).tag(UPDATED_TAG);
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(updatedArchetype);

        restArchetypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, archetypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
        Archetype testArchetype = archetypeList.get(archetypeList.size() - 1);
        assertThat(testArchetype.getAttributeType()).isEqualTo(UPDATED_ATTRIBUTE_TYPE);
        assertThat(testArchetype.getMandantory()).isEqualTo(UPDATED_MANDANTORY);
        assertThat(testArchetype.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    void putNonExistingArchetype() throws Exception {
        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();
        archetype.setId(count.incrementAndGet());

        // Create the Archetype
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchetypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, archetypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArchetype() throws Exception {
        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();
        archetype.setId(count.incrementAndGet());

        // Create the Archetype
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchetypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArchetype() throws Exception {
        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();
        archetype.setId(count.incrementAndGet());

        // Create the Archetype
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchetypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArchetypeWithPatch() throws Exception {
        // Initialize the database
        archetypeRepository.saveAndFlush(archetype);

        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();

        // Update the archetype using partial update
        Archetype partialUpdatedArchetype = new Archetype();
        partialUpdatedArchetype.setId(archetype.getId());

        partialUpdatedArchetype.mandantory(UPDATED_MANDANTORY);

        restArchetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchetype.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArchetype))
            )
            .andExpect(status().isOk());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
        Archetype testArchetype = archetypeList.get(archetypeList.size() - 1);
        assertThat(testArchetype.getAttributeType()).isEqualTo(DEFAULT_ATTRIBUTE_TYPE);
        assertThat(testArchetype.getMandantory()).isEqualTo(UPDATED_MANDANTORY);
        assertThat(testArchetype.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    void fullUpdateArchetypeWithPatch() throws Exception {
        // Initialize the database
        archetypeRepository.saveAndFlush(archetype);

        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();

        // Update the archetype using partial update
        Archetype partialUpdatedArchetype = new Archetype();
        partialUpdatedArchetype.setId(archetype.getId());

        partialUpdatedArchetype.attributeType(UPDATED_ATTRIBUTE_TYPE).mandantory(UPDATED_MANDANTORY).tag(UPDATED_TAG);

        restArchetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchetype.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArchetype))
            )
            .andExpect(status().isOk());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
        Archetype testArchetype = archetypeList.get(archetypeList.size() - 1);
        assertThat(testArchetype.getAttributeType()).isEqualTo(UPDATED_ATTRIBUTE_TYPE);
        assertThat(testArchetype.getMandantory()).isEqualTo(UPDATED_MANDANTORY);
        assertThat(testArchetype.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    void patchNonExistingArchetype() throws Exception {
        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();
        archetype.setId(count.incrementAndGet());

        // Create the Archetype
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, archetypeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArchetype() throws Exception {
        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();
        archetype.setId(count.incrementAndGet());

        // Create the Archetype
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArchetype() throws Exception {
        int databaseSizeBeforeUpdate = archetypeRepository.findAll().size();
        archetype.setId(count.incrementAndGet());

        // Create the Archetype
        ArchetypeDTO archetypeDTO = archetypeMapper.toDto(archetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchetypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(archetypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Archetype in the database
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArchetype() throws Exception {
        // Initialize the database
        archetypeRepository.saveAndFlush(archetype);

        int databaseSizeBeforeDelete = archetypeRepository.findAll().size();

        // Delete the archetype
        restArchetypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, archetype.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Archetype> archetypeList = archetypeRepository.findAll();
        assertThat(archetypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
