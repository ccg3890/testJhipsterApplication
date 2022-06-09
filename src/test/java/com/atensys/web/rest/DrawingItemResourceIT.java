package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.DrawingItem;
import com.atensys.repository.DrawingItemRepository;
import com.atensys.service.dto.DrawingItemDTO;
import com.atensys.service.mapper.DrawingItemMapper;
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
 * Integration tests for the {@link DrawingItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DrawingItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_TOP = 0L;
    private static final Long UPDATED_TOP = 1L;

    private static final Long DEFAULT_LEFT = 0L;
    private static final Long UPDATED_LEFT = 1L;

    private static final String ENTITY_API_URL = "/api/drawing-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DrawingItemRepository drawingItemRepository;

    @Autowired
    private DrawingItemMapper drawingItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrawingItemMockMvc;

    private DrawingItem drawingItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrawingItem createEntity(EntityManager em) {
        DrawingItem drawingItem = new DrawingItem().name(DEFAULT_NAME).top(DEFAULT_TOP).left(DEFAULT_LEFT);
        return drawingItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrawingItem createUpdatedEntity(EntityManager em) {
        DrawingItem drawingItem = new DrawingItem().name(UPDATED_NAME).top(UPDATED_TOP).left(UPDATED_LEFT);
        return drawingItem;
    }

    @BeforeEach
    public void initTest() {
        drawingItem = createEntity(em);
    }

    @Test
    @Transactional
    void createDrawingItem() throws Exception {
        int databaseSizeBeforeCreate = drawingItemRepository.findAll().size();
        // Create the DrawingItem
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);
        restDrawingItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeCreate + 1);
        DrawingItem testDrawingItem = drawingItemList.get(drawingItemList.size() - 1);
        assertThat(testDrawingItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrawingItem.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testDrawingItem.getLeft()).isEqualTo(DEFAULT_LEFT);
    }

    @Test
    @Transactional
    void createDrawingItemWithExistingId() throws Exception {
        // Create the DrawingItem with an existing ID
        drawingItem.setId(1L);
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);

        int databaseSizeBeforeCreate = drawingItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrawingItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDrawingItems() throws Exception {
        // Initialize the database
        drawingItemRepository.saveAndFlush(drawingItem);

        // Get all the drawingItemList
        restDrawingItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drawingItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].top").value(hasItem(DEFAULT_TOP.intValue())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())));
    }

    @Test
    @Transactional
    void getDrawingItem() throws Exception {
        // Initialize the database
        drawingItemRepository.saveAndFlush(drawingItem);

        // Get the drawingItem
        restDrawingItemMockMvc
            .perform(get(ENTITY_API_URL_ID, drawingItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drawingItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.top").value(DEFAULT_TOP.intValue()))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDrawingItem() throws Exception {
        // Get the drawingItem
        restDrawingItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDrawingItem() throws Exception {
        // Initialize the database
        drawingItemRepository.saveAndFlush(drawingItem);

        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();

        // Update the drawingItem
        DrawingItem updatedDrawingItem = drawingItemRepository.findById(drawingItem.getId()).get();
        // Disconnect from session so that the updates on updatedDrawingItem are not directly saved in db
        em.detach(updatedDrawingItem);
        updatedDrawingItem.name(UPDATED_NAME).top(UPDATED_TOP).left(UPDATED_LEFT);
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(updatedDrawingItem);

        restDrawingItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, drawingItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
        DrawingItem testDrawingItem = drawingItemList.get(drawingItemList.size() - 1);
        assertThat(testDrawingItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrawingItem.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testDrawingItem.getLeft()).isEqualTo(UPDATED_LEFT);
    }

    @Test
    @Transactional
    void putNonExistingDrawingItem() throws Exception {
        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();
        drawingItem.setId(count.incrementAndGet());

        // Create the DrawingItem
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrawingItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, drawingItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDrawingItem() throws Exception {
        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();
        drawingItem.setId(count.incrementAndGet());

        // Create the DrawingItem
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDrawingItem() throws Exception {
        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();
        drawingItem.setId(count.incrementAndGet());

        // Create the DrawingItem
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDrawingItemWithPatch() throws Exception {
        // Initialize the database
        drawingItemRepository.saveAndFlush(drawingItem);

        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();

        // Update the drawingItem using partial update
        DrawingItem partialUpdatedDrawingItem = new DrawingItem();
        partialUpdatedDrawingItem.setId(drawingItem.getId());

        partialUpdatedDrawingItem.name(UPDATED_NAME).left(UPDATED_LEFT);

        restDrawingItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrawingItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrawingItem))
            )
            .andExpect(status().isOk());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
        DrawingItem testDrawingItem = drawingItemList.get(drawingItemList.size() - 1);
        assertThat(testDrawingItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrawingItem.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testDrawingItem.getLeft()).isEqualTo(UPDATED_LEFT);
    }

    @Test
    @Transactional
    void fullUpdateDrawingItemWithPatch() throws Exception {
        // Initialize the database
        drawingItemRepository.saveAndFlush(drawingItem);

        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();

        // Update the drawingItem using partial update
        DrawingItem partialUpdatedDrawingItem = new DrawingItem();
        partialUpdatedDrawingItem.setId(drawingItem.getId());

        partialUpdatedDrawingItem.name(UPDATED_NAME).top(UPDATED_TOP).left(UPDATED_LEFT);

        restDrawingItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrawingItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrawingItem))
            )
            .andExpect(status().isOk());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
        DrawingItem testDrawingItem = drawingItemList.get(drawingItemList.size() - 1);
        assertThat(testDrawingItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrawingItem.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testDrawingItem.getLeft()).isEqualTo(UPDATED_LEFT);
    }

    @Test
    @Transactional
    void patchNonExistingDrawingItem() throws Exception {
        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();
        drawingItem.setId(count.incrementAndGet());

        // Create the DrawingItem
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrawingItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, drawingItemDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDrawingItem() throws Exception {
        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();
        drawingItem.setId(count.incrementAndGet());

        // Create the DrawingItem
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDrawingItem() throws Exception {
        int databaseSizeBeforeUpdate = drawingItemRepository.findAll().size();
        drawingItem.setId(count.incrementAndGet());

        // Create the DrawingItem
        DrawingItemDTO drawingItemDTO = drawingItemMapper.toDto(drawingItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrawingItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drawingItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DrawingItem in the database
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDrawingItem() throws Exception {
        // Initialize the database
        drawingItemRepository.saveAndFlush(drawingItem);

        int databaseSizeBeforeDelete = drawingItemRepository.findAll().size();

        // Delete the drawingItem
        restDrawingItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, drawingItem.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DrawingItem> drawingItemList = drawingItemRepository.findAll();
        assertThat(drawingItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
