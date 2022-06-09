package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Fileinfo;
import com.atensys.repository.FileinfoRepository;
import com.atensys.service.dto.FileinfoDTO;
import com.atensys.service.mapper.FileinfoMapper;
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
 * Integration tests for the {@link FileinfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileinfoResourceIT {

    private static final String DEFAULT_ORIG_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIG_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 0L;
    private static final Long UPDATED_FILE_SIZE = 1L;

    private static final String ENTITY_API_URL = "/api/fileinfos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileinfoRepository fileinfoRepository;

    @Autowired
    private FileinfoMapper fileinfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileinfoMockMvc;

    private Fileinfo fileinfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fileinfo createEntity(EntityManager em) {
        Fileinfo fileinfo = new Fileinfo()
            .origFileName(DEFAULT_ORIG_FILE_NAME)
            .fileName(DEFAULT_FILE_NAME)
            .filePath(DEFAULT_FILE_PATH)
            .mimeType(DEFAULT_MIME_TYPE)
            .fileSize(DEFAULT_FILE_SIZE);
        return fileinfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fileinfo createUpdatedEntity(EntityManager em) {
        Fileinfo fileinfo = new Fileinfo()
            .origFileName(UPDATED_ORIG_FILE_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .mimeType(UPDATED_MIME_TYPE)
            .fileSize(UPDATED_FILE_SIZE);
        return fileinfo;
    }

    @BeforeEach
    public void initTest() {
        fileinfo = createEntity(em);
    }

    @Test
    @Transactional
    void createFileinfo() throws Exception {
        int databaseSizeBeforeCreate = fileinfoRepository.findAll().size();
        // Create the Fileinfo
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);
        restFileinfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeCreate + 1);
        Fileinfo testFileinfo = fileinfoList.get(fileinfoList.size() - 1);
        assertThat(testFileinfo.getOrigFileName()).isEqualTo(DEFAULT_ORIG_FILE_NAME);
        assertThat(testFileinfo.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileinfo.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testFileinfo.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testFileinfo.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
    }

    @Test
    @Transactional
    void createFileinfoWithExistingId() throws Exception {
        // Create the Fileinfo with an existing ID
        fileinfo.setId(1L);
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        int databaseSizeBeforeCreate = fileinfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileinfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOrigFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileinfoRepository.findAll().size();
        // set the field null
        fileinfo.setOrigFileName(null);

        // Create the Fileinfo, which fails.
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        restFileinfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileinfoRepository.findAll().size();
        // set the field null
        fileinfo.setFileName(null);

        // Create the Fileinfo, which fails.
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        restFileinfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilePathIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileinfoRepository.findAll().size();
        // set the field null
        fileinfo.setFilePath(null);

        // Create the Fileinfo, which fails.
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        restFileinfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFileinfos() throws Exception {
        // Initialize the database
        fileinfoRepository.saveAndFlush(fileinfo);

        // Get all the fileinfoList
        restFileinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].origFileName").value(hasItem(DEFAULT_ORIG_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())));
    }

    @Test
    @Transactional
    void getFileinfo() throws Exception {
        // Initialize the database
        fileinfoRepository.saveAndFlush(fileinfo);

        // Get the fileinfo
        restFileinfoMockMvc
            .perform(get(ENTITY_API_URL_ID, fileinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileinfo.getId().intValue()))
            .andExpect(jsonPath("$.origFileName").value(DEFAULT_ORIG_FILE_NAME))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFileinfo() throws Exception {
        // Get the fileinfo
        restFileinfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileinfo() throws Exception {
        // Initialize the database
        fileinfoRepository.saveAndFlush(fileinfo);

        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();

        // Update the fileinfo
        Fileinfo updatedFileinfo = fileinfoRepository.findById(fileinfo.getId()).get();
        // Disconnect from session so that the updates on updatedFileinfo are not directly saved in db
        em.detach(updatedFileinfo);
        updatedFileinfo
            .origFileName(UPDATED_ORIG_FILE_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .mimeType(UPDATED_MIME_TYPE)
            .fileSize(UPDATED_FILE_SIZE);
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(updatedFileinfo);

        restFileinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileinfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
        Fileinfo testFileinfo = fileinfoList.get(fileinfoList.size() - 1);
        assertThat(testFileinfo.getOrigFileName()).isEqualTo(UPDATED_ORIG_FILE_NAME);
        assertThat(testFileinfo.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileinfo.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFileinfo.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testFileinfo.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void putNonExistingFileinfo() throws Exception {
        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();
        fileinfo.setId(count.incrementAndGet());

        // Create the Fileinfo
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileinfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileinfo() throws Exception {
        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();
        fileinfo.setId(count.incrementAndGet());

        // Create the Fileinfo
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileinfo() throws Exception {
        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();
        fileinfo.setId(count.incrementAndGet());

        // Create the Fileinfo
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileinfoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileinfoWithPatch() throws Exception {
        // Initialize the database
        fileinfoRepository.saveAndFlush(fileinfo);

        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();

        // Update the fileinfo using partial update
        Fileinfo partialUpdatedFileinfo = new Fileinfo();
        partialUpdatedFileinfo.setId(fileinfo.getId());

        partialUpdatedFileinfo.fileName(UPDATED_FILE_NAME).filePath(UPDATED_FILE_PATH).mimeType(UPDATED_MIME_TYPE);

        restFileinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileinfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileinfo))
            )
            .andExpect(status().isOk());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
        Fileinfo testFileinfo = fileinfoList.get(fileinfoList.size() - 1);
        assertThat(testFileinfo.getOrigFileName()).isEqualTo(DEFAULT_ORIG_FILE_NAME);
        assertThat(testFileinfo.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileinfo.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFileinfo.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testFileinfo.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
    }

    @Test
    @Transactional
    void fullUpdateFileinfoWithPatch() throws Exception {
        // Initialize the database
        fileinfoRepository.saveAndFlush(fileinfo);

        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();

        // Update the fileinfo using partial update
        Fileinfo partialUpdatedFileinfo = new Fileinfo();
        partialUpdatedFileinfo.setId(fileinfo.getId());

        partialUpdatedFileinfo
            .origFileName(UPDATED_ORIG_FILE_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .mimeType(UPDATED_MIME_TYPE)
            .fileSize(UPDATED_FILE_SIZE);

        restFileinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileinfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileinfo))
            )
            .andExpect(status().isOk());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
        Fileinfo testFileinfo = fileinfoList.get(fileinfoList.size() - 1);
        assertThat(testFileinfo.getOrigFileName()).isEqualTo(UPDATED_ORIG_FILE_NAME);
        assertThat(testFileinfo.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileinfo.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFileinfo.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testFileinfo.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void patchNonExistingFileinfo() throws Exception {
        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();
        fileinfo.setId(count.incrementAndGet());

        // Create the Fileinfo
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileinfoDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileinfo() throws Exception {
        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();
        fileinfo.setId(count.incrementAndGet());

        // Create the Fileinfo
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileinfo() throws Exception {
        int databaseSizeBeforeUpdate = fileinfoRepository.findAll().size();
        fileinfo.setId(count.incrementAndGet());

        // Create the Fileinfo
        FileinfoDTO fileinfoDTO = fileinfoMapper.toDto(fileinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileinfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileinfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fileinfo in the database
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileinfo() throws Exception {
        // Initialize the database
        fileinfoRepository.saveAndFlush(fileinfo);

        int databaseSizeBeforeDelete = fileinfoRepository.findAll().size();

        // Delete the fileinfo
        restFileinfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileinfo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fileinfo> fileinfoList = fileinfoRepository.findAll();
        assertThat(fileinfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
