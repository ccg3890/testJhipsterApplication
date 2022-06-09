package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Config;
import com.atensys.domain.enumeration.CodeType;
import com.atensys.repository.ConfigRepository;
import com.atensys.service.dto.ConfigDTO;
import com.atensys.service.mapper.ConfigMapper;
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
 * Integration tests for the {@link ConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfigResourceIT {

    private static final Long DEFAULT_CONFIG_ID = 1L;
    private static final Long UPDATED_CONFIG_ID = 2L;

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final CodeType DEFAULT_CODE_TYPE = CodeType.STRING;
    private static final CodeType UPDATED_CODE_TYPE = CodeType.NUMBER;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_YN = false;
    private static final Boolean UPDATED_USE_YN = true;

    private static final String ENTITY_API_URL = "/api/configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfigMockMvc;

    private Config config;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Config createEntity(EntityManager em) {
        Config config = new Config()
            .configId(DEFAULT_CONFIG_ID)
            .parentId(DEFAULT_PARENT_ID)
            .code(DEFAULT_CODE)
            .codeType(DEFAULT_CODE_TYPE)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .useYn(DEFAULT_USE_YN);
        return config;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Config createUpdatedEntity(EntityManager em) {
        Config config = new Config()
            .configId(UPDATED_CONFIG_ID)
            .parentId(UPDATED_PARENT_ID)
            .code(UPDATED_CODE)
            .codeType(UPDATED_CODE_TYPE)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .useYn(UPDATED_USE_YN);
        return config;
    }

    @BeforeEach
    public void initTest() {
        config = createEntity(em);
    }

    @Test
    @Transactional
    void createConfig() throws Exception {
        int databaseSizeBeforeCreate = configRepository.findAll().size();
        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);
        restConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeCreate + 1);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigId()).isEqualTo(DEFAULT_CONFIG_ID);
        assertThat(testConfig.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testConfig.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConfig.getCodeType()).isEqualTo(DEFAULT_CODE_TYPE);
        assertThat(testConfig.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConfig.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    void createConfigWithExistingId() throws Exception {
        // Create the Config with an existing ID
        config.setId(1L);
        ConfigDTO configDTO = configMapper.toDto(config);

        int databaseSizeBeforeCreate = configRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkConfigIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setConfigId(null);

        // Create the Config, which fails.
        ConfigDTO configDTO = configMapper.toDto(config);

        restConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setCode(null);

        // Create the Config, which fails.
        ConfigDTO configDTO = configMapper.toDto(config);

        restConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setCodeType(null);

        // Create the Config, which fails.
        ConfigDTO configDTO = configMapper.toDto(config);

        restConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = configRepository.findAll().size();
        // set the field null
        config.setValue(null);

        // Create the Config, which fails.
        ConfigDTO configDTO = configMapper.toDto(config);

        restConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConfigs() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configList
        restConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(config.getId().intValue())))
            .andExpect(jsonPath("$.[*].configId").value(hasItem(DEFAULT_CONFIG_ID.intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeType").value(hasItem(DEFAULT_CODE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.booleanValue())));
    }

    @Test
    @Transactional
    void getConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get the config
        restConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, config.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(config.getId().intValue()))
            .andExpect(jsonPath("$.configId").value(DEFAULT_CONFIG_ID.intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.codeType").value(DEFAULT_CODE_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingConfig() throws Exception {
        // Get the config
        restConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config
        Config updatedConfig = configRepository.findById(config.getId()).get();
        // Disconnect from session so that the updates on updatedConfig are not directly saved in db
        em.detach(updatedConfig);
        updatedConfig
            .configId(UPDATED_CONFIG_ID)
            .parentId(UPDATED_PARENT_ID)
            .code(UPDATED_CODE)
            .codeType(UPDATED_CODE_TYPE)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .useYn(UPDATED_USE_YN);
        ConfigDTO configDTO = configMapper.toDto(updatedConfig);

        restConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigId()).isEqualTo(UPDATED_CONFIG_ID);
        assertThat(testConfig.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testConfig.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConfig.getCodeType()).isEqualTo(UPDATED_CODE_TYPE);
        assertThat(testConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConfig.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void putNonExistingConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, configDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfigWithPatch() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config using partial update
        Config partialUpdatedConfig = new Config();
        partialUpdatedConfig.setId(config.getId());

        partialUpdatedConfig.configId(UPDATED_CONFIG_ID).parentId(UPDATED_PARENT_ID).value(UPDATED_VALUE).useYn(UPDATED_USE_YN);

        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfig.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfig))
            )
            .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigId()).isEqualTo(UPDATED_CONFIG_ID);
        assertThat(testConfig.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testConfig.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConfig.getCodeType()).isEqualTo(DEFAULT_CODE_TYPE);
        assertThat(testConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConfig.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void fullUpdateConfigWithPatch() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config using partial update
        Config partialUpdatedConfig = new Config();
        partialUpdatedConfig.setId(config.getId());

        partialUpdatedConfig
            .configId(UPDATED_CONFIG_ID)
            .parentId(UPDATED_PARENT_ID)
            .code(UPDATED_CODE)
            .codeType(UPDATED_CODE_TYPE)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .useYn(UPDATED_USE_YN);

        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfig.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfig))
            )
            .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configList.get(configList.size() - 1);
        assertThat(testConfig.getConfigId()).isEqualTo(UPDATED_CONFIG_ID);
        assertThat(testConfig.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testConfig.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConfig.getCodeType()).isEqualTo(UPDATED_CODE_TYPE);
        assertThat(testConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConfig.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void patchNonExistingConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, configDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfig() throws Exception {
        int databaseSizeBeforeUpdate = configRepository.findAll().size();
        config.setId(count.incrementAndGet());

        // Create the Config
        ConfigDTO configDTO = configMapper.toDto(config);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(configDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Config in the database
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        int databaseSizeBeforeDelete = configRepository.findAll().size();

        // Delete the config
        restConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, config.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Config> configList = configRepository.findAll();
        assertThat(configList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
