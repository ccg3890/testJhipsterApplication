package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Floor;
import com.atensys.repository.FloorRepository;
import com.atensys.service.dto.FloorDTO;
import com.atensys.service.mapper.FloorMapper;
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
 * Integration tests for the {@link FloorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FloorResourceIT {

    private static final String DEFAULT_FLOOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FLOOR_DESC = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR_DESC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_YN = false;
    private static final Boolean UPDATED_USE_YN = true;

    private static final Integer DEFAULT_ORDER_NO = 1;
    private static final Integer UPDATED_ORDER_NO = 2;

    private static final String DEFAULT_BACKGROUND_IMAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_IMAGE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SVG_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_SVG_IMAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/floors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFloorMockMvc;

    private Floor floor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Floor createEntity(EntityManager em) {
        Floor floor = new Floor()
            .floorName(DEFAULT_FLOOR_NAME)
            .floorDesc(DEFAULT_FLOOR_DESC)
            .useYn(DEFAULT_USE_YN)
            .orderNo(DEFAULT_ORDER_NO)
            .backgroundImageId(DEFAULT_BACKGROUND_IMAGE_ID)
            .svgImage(DEFAULT_SVG_IMAGE);
        return floor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Floor createUpdatedEntity(EntityManager em) {
        Floor floor = new Floor()
            .floorName(UPDATED_FLOOR_NAME)
            .floorDesc(UPDATED_FLOOR_DESC)
            .useYn(UPDATED_USE_YN)
            .orderNo(UPDATED_ORDER_NO)
            .backgroundImageId(UPDATED_BACKGROUND_IMAGE_ID)
            .svgImage(UPDATED_SVG_IMAGE);
        return floor;
    }

    @BeforeEach
    public void initTest() {
        floor = createEntity(em);
    }

    @Test
    @Transactional
    void createFloor() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();
        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);
        restFloorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate + 1);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getFloorName()).isEqualTo(DEFAULT_FLOOR_NAME);
        assertThat(testFloor.getFloorDesc()).isEqualTo(DEFAULT_FLOOR_DESC);
        assertThat(testFloor.getUseYn()).isEqualTo(DEFAULT_USE_YN);
        assertThat(testFloor.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testFloor.getBackgroundImageId()).isEqualTo(DEFAULT_BACKGROUND_IMAGE_ID);
        assertThat(testFloor.getSvgImage()).isEqualTo(DEFAULT_SVG_IMAGE);
    }

    @Test
    @Transactional
    void createFloorWithExistingId() throws Exception {
        // Create the Floor with an existing ID
        floor.setId(1L);
        FloorDTO floorDTO = floorMapper.toDto(floor);

        int databaseSizeBeforeCreate = floorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFloorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFloors() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get all the floorList
        restFloorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floor.getId().intValue())))
            .andExpect(jsonPath("$.[*].floorName").value(hasItem(DEFAULT_FLOOR_NAME)))
            .andExpect(jsonPath("$.[*].floorDesc").value(hasItem(DEFAULT_FLOOR_DESC)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].backgroundImageId").value(hasItem(DEFAULT_BACKGROUND_IMAGE_ID)))
            .andExpect(jsonPath("$.[*].svgImage").value(hasItem(DEFAULT_SVG_IMAGE)));
    }

    @Test
    @Transactional
    void getFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get the floor
        restFloorMockMvc
            .perform(get(ENTITY_API_URL_ID, floor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(floor.getId().intValue()))
            .andExpect(jsonPath("$.floorName").value(DEFAULT_FLOOR_NAME))
            .andExpect(jsonPath("$.floorDesc").value(DEFAULT_FLOOR_DESC))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.booleanValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO))
            .andExpect(jsonPath("$.backgroundImageId").value(DEFAULT_BACKGROUND_IMAGE_ID))
            .andExpect(jsonPath("$.svgImage").value(DEFAULT_SVG_IMAGE));
    }

    @Test
    @Transactional
    void getNonExistingFloor() throws Exception {
        // Get the floor
        restFloorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor
        Floor updatedFloor = floorRepository.findById(floor.getId()).get();
        // Disconnect from session so that the updates on updatedFloor are not directly saved in db
        em.detach(updatedFloor);
        updatedFloor
            .floorName(UPDATED_FLOOR_NAME)
            .floorDesc(UPDATED_FLOOR_DESC)
            .useYn(UPDATED_USE_YN)
            .orderNo(UPDATED_ORDER_NO)
            .backgroundImageId(UPDATED_BACKGROUND_IMAGE_ID)
            .svgImage(UPDATED_SVG_IMAGE);
        FloorDTO floorDTO = floorMapper.toDto(updatedFloor);

        restFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, floorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getFloorName()).isEqualTo(UPDATED_FLOOR_NAME);
        assertThat(testFloor.getFloorDesc()).isEqualTo(UPDATED_FLOOR_DESC);
        assertThat(testFloor.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testFloor.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testFloor.getBackgroundImageId()).isEqualTo(UPDATED_BACKGROUND_IMAGE_ID);
        assertThat(testFloor.getSvgImage()).isEqualTo(UPDATED_SVG_IMAGE);
    }

    @Test
    @Transactional
    void putNonExistingFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(count.incrementAndGet());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, floorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(count.incrementAndGet());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(count.incrementAndGet());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFloorWithPatch() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor using partial update
        Floor partialUpdatedFloor = new Floor();
        partialUpdatedFloor.setId(floor.getId());

        partialUpdatedFloor.floorDesc(UPDATED_FLOOR_DESC).useYn(UPDATED_USE_YN).backgroundImageId(UPDATED_BACKGROUND_IMAGE_ID);

        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloor))
            )
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getFloorName()).isEqualTo(DEFAULT_FLOOR_NAME);
        assertThat(testFloor.getFloorDesc()).isEqualTo(UPDATED_FLOOR_DESC);
        assertThat(testFloor.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testFloor.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testFloor.getBackgroundImageId()).isEqualTo(UPDATED_BACKGROUND_IMAGE_ID);
        assertThat(testFloor.getSvgImage()).isEqualTo(DEFAULT_SVG_IMAGE);
    }

    @Test
    @Transactional
    void fullUpdateFloorWithPatch() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor using partial update
        Floor partialUpdatedFloor = new Floor();
        partialUpdatedFloor.setId(floor.getId());

        partialUpdatedFloor
            .floorName(UPDATED_FLOOR_NAME)
            .floorDesc(UPDATED_FLOOR_DESC)
            .useYn(UPDATED_USE_YN)
            .orderNo(UPDATED_ORDER_NO)
            .backgroundImageId(UPDATED_BACKGROUND_IMAGE_ID)
            .svgImage(UPDATED_SVG_IMAGE);

        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloor))
            )
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getFloorName()).isEqualTo(UPDATED_FLOOR_NAME);
        assertThat(testFloor.getFloorDesc()).isEqualTo(UPDATED_FLOOR_DESC);
        assertThat(testFloor.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testFloor.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testFloor.getBackgroundImageId()).isEqualTo(UPDATED_BACKGROUND_IMAGE_ID);
        assertThat(testFloor.getSvgImage()).isEqualTo(UPDATED_SVG_IMAGE);
    }

    @Test
    @Transactional
    void patchNonExistingFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(count.incrementAndGet());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, floorDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(count.incrementAndGet());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(count.incrementAndGet());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        int databaseSizeBeforeDelete = floorRepository.findAll().size();

        // Delete the floor
        restFloorMockMvc
            .perform(delete(ENTITY_API_URL_ID, floor.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
