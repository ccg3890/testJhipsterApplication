package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Drawing;
import com.atensys.repository.DrawingRepository;
import com.atensys.service.dto.DrawingDTO;
import com.atensys.service.mapper.DrawingMapper;
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
 * Integration tests for the {@link DrawingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DrawingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/drawings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DrawingRepository drawingRepository;

    @Autowired
    private DrawingMapper drawingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrawingMockMvc;

    private Drawing drawing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drawing createEntity(EntityManager em) {
        Drawing drawing = new Drawing().name(DEFAULT_NAME);
        return drawing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drawing createUpdatedEntity(EntityManager em) {
        Drawing drawing = new Drawing().name(UPDATED_NAME);
        return drawing;
    }

    @BeforeEach
    public void initTest() {
        drawing = createEntity(em);
    }

    @Test
    @Transactional
    void createDrawing() throws Exception {
        int databaseSizeBeforeCreate = drawingRepository.findAll().size();
        // Create the Drawing
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);
        restDrawingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeCreate + 1);
        Drawing testDrawing = drawingList.get(drawingList.size() - 1);
        assertThat(testDrawing.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createDrawingWithExistingId() throws Exception {
        // Create the Drawing with an existing ID
        drawing.setId(1L);
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);

        int databaseSizeBeforeCreate = drawingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrawingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDrawings() throws Exception {
        // Initialize the database
        drawingRepository.saveAndFlush(drawing);

        // Get all the drawingList
        restDrawingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drawing.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getDrawing() throws Exception {
        // Initialize the database
        drawingRepository.saveAndFlush(drawing);

        // Get the drawing
        restDrawingMockMvc
            .perform(get(ENTITY_API_URL_ID, drawing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drawing.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingDrawing() throws Exception {
        // Get the drawing
        restDrawingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDrawing() throws Exception {
        // Initialize the database
        drawingRepository.saveAndFlush(drawing);

        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();

        // Update the drawing
        Drawing updatedDrawing = drawingRepository.findById(drawing.getId()).get();
        // Disconnect from session so that the updates on updatedDrawing are not directly saved in db
        em.detach(updatedDrawing);
        updatedDrawing.name(UPDATED_NAME);
        DrawingDTO drawingDTO = drawingMapper.toDto(updatedDrawing);

        restDrawingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, drawingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
        Drawing testDrawing = drawingList.get(drawingList.size() - 1);
        assertThat(testDrawing.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingDrawing() throws Exception {
        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();
        drawing.setId(count.incrementAndGet());

        // Create the Drawing
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrawingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, drawingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDrawing() throws Exception {
        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();
        drawing.setId(count.incrementAndGet());

        // Create the Drawing
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDrawing() throws Exception {
        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();
        drawing.setId(count.incrementAndGet());

        // Create the Drawing
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDrawingWithPatch() throws Exception {
        // Initialize the database
        drawingRepository.saveAndFlush(drawing);

        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();

        // Update the drawing using partial update
        Drawing partialUpdatedDrawing = new Drawing();
        partialUpdatedDrawing.setId(drawing.getId());

        restDrawingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrawing.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrawing))
            )
            .andExpect(status().isOk());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
        Drawing testDrawing = drawingList.get(drawingList.size() - 1);
        assertThat(testDrawing.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateDrawingWithPatch() throws Exception {
        // Initialize the database
        drawingRepository.saveAndFlush(drawing);

        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();

        // Update the drawing using partial update
        Drawing partialUpdatedDrawing = new Drawing();
        partialUpdatedDrawing.setId(drawing.getId());

        partialUpdatedDrawing.name(UPDATED_NAME);

        restDrawingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrawing.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrawing))
            )
            .andExpect(status().isOk());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
        Drawing testDrawing = drawingList.get(drawingList.size() - 1);
        assertThat(testDrawing.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingDrawing() throws Exception {
        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();
        drawing.setId(count.incrementAndGet());

        // Create the Drawing
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrawingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, drawingDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDrawing() throws Exception {
        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();
        drawing.setId(count.incrementAndGet());

        // Create the Drawing
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDrawing() throws Exception {
        int databaseSizeBeforeUpdate = drawingRepository.findAll().size();
        drawing.setId(count.incrementAndGet());

        // Create the Drawing
        DrawingDTO drawingDTO = drawingMapper.toDto(drawing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drawingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drawing in the database
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDrawing() throws Exception {
        // Initialize the database
        drawingRepository.saveAndFlush(drawing);

        int databaseSizeBeforeDelete = drawingRepository.findAll().size();

        // Delete the drawing
        restDrawingMockMvc
            .perform(delete(ENTITY_API_URL_ID, drawing.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drawing> drawingList = drawingRepository.findAll();
        assertThat(drawingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
