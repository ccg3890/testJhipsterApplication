package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Dictionary;
import com.atensys.repository.DictionaryRepository;
import com.atensys.service.dto.DictionaryDTO;
import com.atensys.service.mapper.DictionaryMapper;
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
 * Integration tests for the {@link DictionaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DictionaryResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_NO = 0;
    private static final Integer UPDATED_ORDER_NO = 1;

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String ENTITY_API_URL = "/api/dictionaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDictionaryMockMvc;

    private Dictionary dictionary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dictionary createEntity(EntityManager em) {
        Dictionary dictionary = new Dictionary()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME)
            .discription(DEFAULT_DISCRIPTION)
            .checkPattern(DEFAULT_CHECK_PATTERN)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .orderNo(DEFAULT_ORDER_NO)
            .valid(DEFAULT_VALID);
        return dictionary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dictionary createUpdatedEntity(EntityManager em) {
        Dictionary dictionary = new Dictionary()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .checkPattern(UPDATED_CHECK_PATTERN)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .orderNo(UPDATED_ORDER_NO)
            .valid(UPDATED_VALID);
        return dictionary;
    }

    @BeforeEach
    public void initTest() {
        dictionary = createEntity(em);
    }

    @Test
    @Transactional
    void createDictionary() throws Exception {
        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();
        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);
        restDictionaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate + 1);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testDictionary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDictionary.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testDictionary.getCheckPattern()).isEqualTo(DEFAULT_CHECK_PATTERN);
        assertThat(testDictionary.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testDictionary.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testDictionary.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void createDictionaryWithExistingId() throws Exception {
        // Create the Dictionary with an existing ID
        dictionary.setId(1L);
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictionaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryRepository.findAll().size();
        // set the field null
        dictionary.setKey(null);

        // Create the Dictionary, which fails.
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        restDictionaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isBadRequest());

        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryRepository.findAll().size();
        // set the field null
        dictionary.setName(null);

        // Create the Dictionary, which fails.
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        restDictionaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isBadRequest());

        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDictionaries() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get all the dictionaryList
        restDictionaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION)))
            .andExpect(jsonPath("$.[*].checkPattern").value(hasItem(DEFAULT_CHECK_PATTERN)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }

    @Test
    @Transactional
    void getDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get the dictionary
        restDictionaryMockMvc
            .perform(get(ENTITY_API_URL_ID, dictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dictionary.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION))
            .andExpect(jsonPath("$.checkPattern").value(DEFAULT_CHECK_PATTERN))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDictionary() throws Exception {
        // Get the dictionary
        restDictionaryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Update the dictionary
        Dictionary updatedDictionary = dictionaryRepository.findById(dictionary.getId()).get();
        // Disconnect from session so that the updates on updatedDictionary are not directly saved in db
        em.detach(updatedDictionary);
        updatedDictionary
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .checkPattern(UPDATED_CHECK_PATTERN)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .orderNo(UPDATED_ORDER_NO)
            .valid(UPDATED_VALID);
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(updatedDictionary);

        restDictionaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dictionaryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testDictionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDictionary.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testDictionary.getCheckPattern()).isEqualTo(UPDATED_CHECK_PATTERN);
        assertThat(testDictionary.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testDictionary.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testDictionary.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void putNonExistingDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();
        dictionary.setId(count.incrementAndGet());

        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictionaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dictionaryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();
        dictionary.setId(count.incrementAndGet());

        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictionaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();
        dictionary.setId(count.incrementAndGet());

        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictionaryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDictionaryWithPatch() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Update the dictionary using partial update
        Dictionary partialUpdatedDictionary = new Dictionary();
        partialUpdatedDictionary.setId(dictionary.getId());

        partialUpdatedDictionary.name(UPDATED_NAME).defaultValue(UPDATED_DEFAULT_VALUE);

        restDictionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDictionary.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDictionary))
            )
            .andExpect(status().isOk());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testDictionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDictionary.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testDictionary.getCheckPattern()).isEqualTo(DEFAULT_CHECK_PATTERN);
        assertThat(testDictionary.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testDictionary.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testDictionary.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void fullUpdateDictionaryWithPatch() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Update the dictionary using partial update
        Dictionary partialUpdatedDictionary = new Dictionary();
        partialUpdatedDictionary.setId(dictionary.getId());

        partialUpdatedDictionary
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .checkPattern(UPDATED_CHECK_PATTERN)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .orderNo(UPDATED_ORDER_NO)
            .valid(UPDATED_VALID);

        restDictionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDictionary.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDictionary))
            )
            .andExpect(status().isOk());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testDictionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDictionary.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testDictionary.getCheckPattern()).isEqualTo(UPDATED_CHECK_PATTERN);
        assertThat(testDictionary.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testDictionary.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testDictionary.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void patchNonExistingDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();
        dictionary.setId(count.incrementAndGet());

        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dictionaryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();
        dictionary.setId(count.incrementAndGet());

        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();
        dictionary.setId(count.incrementAndGet());

        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictionaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        int databaseSizeBeforeDelete = dictionaryRepository.findAll().size();

        // Delete the dictionary
        restDictionaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, dictionary.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
