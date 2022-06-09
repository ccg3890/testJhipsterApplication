package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.ShapeAsset;
import com.atensys.domain.enumeration.AlignVertical;
import com.atensys.domain.enumeration.ShapeAssetType;
import com.atensys.domain.enumeration.ShapeImageType;
import com.atensys.repository.ShapeAssetRepository;
import com.atensys.service.dto.ShapeAssetDTO;
import com.atensys.service.mapper.ShapeAssetMapper;
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
 * Integration tests for the {@link ShapeAssetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShapeAssetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ShapeAssetType DEFAULT_ASSET_TYPE = ShapeAssetType.SEAT;
    private static final ShapeAssetType UPDATED_ASSET_TYPE = ShapeAssetType.ROOM;

    private static final ShapeImageType DEFAULT_SHAPE_IMAGE_TYPE = ShapeImageType.VECTOR;
    private static final ShapeImageType UPDATED_SHAPE_IMAGE_TYPE = ShapeImageType.IMAGE;

    private static final Long DEFAULT_WIDTH = 0L;
    private static final Long UPDATED_WIDTH = 1L;

    private static final Long DEFAULT_HEIGHT = 0L;
    private static final Long UPDATED_HEIGHT = 1L;

    private static final AlignVertical DEFAULT_TEXT_ALIGN = AlignVertical.TOP;
    private static final AlignVertical UPDATED_TEXT_ALIGN = AlignVertical.MIDDLE;

    private static final String DEFAULT_SHAPE_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_SHAPE_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_STYLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SCALABLE = false;
    private static final Boolean UPDATED_SCALABLE = true;

    private static final Boolean DEFAULT_USE_YN = false;
    private static final Boolean UPDATED_USE_YN = true;

    private static final String ENTITY_API_URL = "/api/shape-assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShapeAssetRepository shapeAssetRepository;

    @Autowired
    private ShapeAssetMapper shapeAssetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShapeAssetMockMvc;

    private ShapeAsset shapeAsset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShapeAsset createEntity(EntityManager em) {
        ShapeAsset shapeAsset = new ShapeAsset()
            .name(DEFAULT_NAME)
            .assetType(DEFAULT_ASSET_TYPE)
            .shapeImageType(DEFAULT_SHAPE_IMAGE_TYPE)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .textAlign(DEFAULT_TEXT_ALIGN)
            .shapeStyle(DEFAULT_SHAPE_STYLE)
            .textStyle(DEFAULT_TEXT_STYLE)
            .scalable(DEFAULT_SCALABLE)
            .useYn(DEFAULT_USE_YN);
        return shapeAsset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShapeAsset createUpdatedEntity(EntityManager em) {
        ShapeAsset shapeAsset = new ShapeAsset()
            .name(UPDATED_NAME)
            .assetType(UPDATED_ASSET_TYPE)
            .shapeImageType(UPDATED_SHAPE_IMAGE_TYPE)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .textAlign(UPDATED_TEXT_ALIGN)
            .shapeStyle(UPDATED_SHAPE_STYLE)
            .textStyle(UPDATED_TEXT_STYLE)
            .scalable(UPDATED_SCALABLE)
            .useYn(UPDATED_USE_YN);
        return shapeAsset;
    }

    @BeforeEach
    public void initTest() {
        shapeAsset = createEntity(em);
    }

    @Test
    @Transactional
    void createShapeAsset() throws Exception {
        int databaseSizeBeforeCreate = shapeAssetRepository.findAll().size();
        // Create the ShapeAsset
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);
        restShapeAssetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeCreate + 1);
        ShapeAsset testShapeAsset = shapeAssetList.get(shapeAssetList.size() - 1);
        assertThat(testShapeAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShapeAsset.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testShapeAsset.getShapeImageType()).isEqualTo(DEFAULT_SHAPE_IMAGE_TYPE);
        assertThat(testShapeAsset.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testShapeAsset.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testShapeAsset.getTextAlign()).isEqualTo(DEFAULT_TEXT_ALIGN);
        assertThat(testShapeAsset.getShapeStyle()).isEqualTo(DEFAULT_SHAPE_STYLE);
        assertThat(testShapeAsset.getTextStyle()).isEqualTo(DEFAULT_TEXT_STYLE);
        assertThat(testShapeAsset.getScalable()).isEqualTo(DEFAULT_SCALABLE);
        assertThat(testShapeAsset.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    void createShapeAssetWithExistingId() throws Exception {
        // Create the ShapeAsset with an existing ID
        shapeAsset.setId(1L);
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        int databaseSizeBeforeCreate = shapeAssetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShapeAssetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAssetTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shapeAssetRepository.findAll().size();
        // set the field null
        shapeAsset.setAssetType(null);

        // Create the ShapeAsset, which fails.
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        restShapeAssetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShapeImageTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shapeAssetRepository.findAll().size();
        // set the field null
        shapeAsset.setShapeImageType(null);

        // Create the ShapeAsset, which fails.
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        restShapeAssetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = shapeAssetRepository.findAll().size();
        // set the field null
        shapeAsset.setWidth(null);

        // Create the ShapeAsset, which fails.
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        restShapeAssetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = shapeAssetRepository.findAll().size();
        // set the field null
        shapeAsset.setHeight(null);

        // Create the ShapeAsset, which fails.
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        restShapeAssetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkScalableIsRequired() throws Exception {
        int databaseSizeBeforeTest = shapeAssetRepository.findAll().size();
        // set the field null
        shapeAsset.setScalable(null);

        // Create the ShapeAsset, which fails.
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        restShapeAssetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShapeAssets() throws Exception {
        // Initialize the database
        shapeAssetRepository.saveAndFlush(shapeAsset);

        // Get all the shapeAssetList
        restShapeAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shapeAsset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].shapeImageType").value(hasItem(DEFAULT_SHAPE_IMAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].textAlign").value(hasItem(DEFAULT_TEXT_ALIGN.toString())))
            .andExpect(jsonPath("$.[*].shapeStyle").value(hasItem(DEFAULT_SHAPE_STYLE)))
            .andExpect(jsonPath("$.[*].textStyle").value(hasItem(DEFAULT_TEXT_STYLE)))
            .andExpect(jsonPath("$.[*].scalable").value(hasItem(DEFAULT_SCALABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.booleanValue())));
    }

    @Test
    @Transactional
    void getShapeAsset() throws Exception {
        // Initialize the database
        shapeAssetRepository.saveAndFlush(shapeAsset);

        // Get the shapeAsset
        restShapeAssetMockMvc
            .perform(get(ENTITY_API_URL_ID, shapeAsset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shapeAsset.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE.toString()))
            .andExpect(jsonPath("$.shapeImageType").value(DEFAULT_SHAPE_IMAGE_TYPE.toString()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.intValue()))
            .andExpect(jsonPath("$.textAlign").value(DEFAULT_TEXT_ALIGN.toString()))
            .andExpect(jsonPath("$.shapeStyle").value(DEFAULT_SHAPE_STYLE))
            .andExpect(jsonPath("$.textStyle").value(DEFAULT_TEXT_STYLE))
            .andExpect(jsonPath("$.scalable").value(DEFAULT_SCALABLE.booleanValue()))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingShapeAsset() throws Exception {
        // Get the shapeAsset
        restShapeAssetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShapeAsset() throws Exception {
        // Initialize the database
        shapeAssetRepository.saveAndFlush(shapeAsset);

        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();

        // Update the shapeAsset
        ShapeAsset updatedShapeAsset = shapeAssetRepository.findById(shapeAsset.getId()).get();
        // Disconnect from session so that the updates on updatedShapeAsset are not directly saved in db
        em.detach(updatedShapeAsset);
        updatedShapeAsset
            .name(UPDATED_NAME)
            .assetType(UPDATED_ASSET_TYPE)
            .shapeImageType(UPDATED_SHAPE_IMAGE_TYPE)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .textAlign(UPDATED_TEXT_ALIGN)
            .shapeStyle(UPDATED_SHAPE_STYLE)
            .textStyle(UPDATED_TEXT_STYLE)
            .scalable(UPDATED_SCALABLE)
            .useYn(UPDATED_USE_YN);
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(updatedShapeAsset);

        restShapeAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shapeAssetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
        ShapeAsset testShapeAsset = shapeAssetList.get(shapeAssetList.size() - 1);
        assertThat(testShapeAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShapeAsset.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testShapeAsset.getShapeImageType()).isEqualTo(UPDATED_SHAPE_IMAGE_TYPE);
        assertThat(testShapeAsset.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testShapeAsset.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testShapeAsset.getTextAlign()).isEqualTo(UPDATED_TEXT_ALIGN);
        assertThat(testShapeAsset.getShapeStyle()).isEqualTo(UPDATED_SHAPE_STYLE);
        assertThat(testShapeAsset.getTextStyle()).isEqualTo(UPDATED_TEXT_STYLE);
        assertThat(testShapeAsset.getScalable()).isEqualTo(UPDATED_SCALABLE);
        assertThat(testShapeAsset.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void putNonExistingShapeAsset() throws Exception {
        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();
        shapeAsset.setId(count.incrementAndGet());

        // Create the ShapeAsset
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShapeAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shapeAssetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShapeAsset() throws Exception {
        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();
        shapeAsset.setId(count.incrementAndGet());

        // Create the ShapeAsset
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShapeAsset() throws Exception {
        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();
        shapeAsset.setId(count.incrementAndGet());

        // Create the ShapeAsset
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeAssetMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShapeAssetWithPatch() throws Exception {
        // Initialize the database
        shapeAssetRepository.saveAndFlush(shapeAsset);

        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();

        // Update the shapeAsset using partial update
        ShapeAsset partialUpdatedShapeAsset = new ShapeAsset();
        partialUpdatedShapeAsset.setId(shapeAsset.getId());

        partialUpdatedShapeAsset.shapeImageType(UPDATED_SHAPE_IMAGE_TYPE).scalable(UPDATED_SCALABLE);

        restShapeAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShapeAsset.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShapeAsset))
            )
            .andExpect(status().isOk());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
        ShapeAsset testShapeAsset = shapeAssetList.get(shapeAssetList.size() - 1);
        assertThat(testShapeAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShapeAsset.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testShapeAsset.getShapeImageType()).isEqualTo(UPDATED_SHAPE_IMAGE_TYPE);
        assertThat(testShapeAsset.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testShapeAsset.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testShapeAsset.getTextAlign()).isEqualTo(DEFAULT_TEXT_ALIGN);
        assertThat(testShapeAsset.getShapeStyle()).isEqualTo(DEFAULT_SHAPE_STYLE);
        assertThat(testShapeAsset.getTextStyle()).isEqualTo(DEFAULT_TEXT_STYLE);
        assertThat(testShapeAsset.getScalable()).isEqualTo(UPDATED_SCALABLE);
        assertThat(testShapeAsset.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    void fullUpdateShapeAssetWithPatch() throws Exception {
        // Initialize the database
        shapeAssetRepository.saveAndFlush(shapeAsset);

        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();

        // Update the shapeAsset using partial update
        ShapeAsset partialUpdatedShapeAsset = new ShapeAsset();
        partialUpdatedShapeAsset.setId(shapeAsset.getId());

        partialUpdatedShapeAsset
            .name(UPDATED_NAME)
            .assetType(UPDATED_ASSET_TYPE)
            .shapeImageType(UPDATED_SHAPE_IMAGE_TYPE)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .textAlign(UPDATED_TEXT_ALIGN)
            .shapeStyle(UPDATED_SHAPE_STYLE)
            .textStyle(UPDATED_TEXT_STYLE)
            .scalable(UPDATED_SCALABLE)
            .useYn(UPDATED_USE_YN);

        restShapeAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShapeAsset.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShapeAsset))
            )
            .andExpect(status().isOk());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
        ShapeAsset testShapeAsset = shapeAssetList.get(shapeAssetList.size() - 1);
        assertThat(testShapeAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShapeAsset.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testShapeAsset.getShapeImageType()).isEqualTo(UPDATED_SHAPE_IMAGE_TYPE);
        assertThat(testShapeAsset.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testShapeAsset.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testShapeAsset.getTextAlign()).isEqualTo(UPDATED_TEXT_ALIGN);
        assertThat(testShapeAsset.getShapeStyle()).isEqualTo(UPDATED_SHAPE_STYLE);
        assertThat(testShapeAsset.getTextStyle()).isEqualTo(UPDATED_TEXT_STYLE);
        assertThat(testShapeAsset.getScalable()).isEqualTo(UPDATED_SCALABLE);
        assertThat(testShapeAsset.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void patchNonExistingShapeAsset() throws Exception {
        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();
        shapeAsset.setId(count.incrementAndGet());

        // Create the ShapeAsset
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShapeAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shapeAssetDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShapeAsset() throws Exception {
        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();
        shapeAsset.setId(count.incrementAndGet());

        // Create the ShapeAsset
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShapeAsset() throws Exception {
        int databaseSizeBeforeUpdate = shapeAssetRepository.findAll().size();
        shapeAsset.setId(count.incrementAndGet());

        // Create the ShapeAsset
        ShapeAssetDTO shapeAssetDTO = shapeAssetMapper.toDto(shapeAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeAssetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shapeAssetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShapeAsset in the database
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShapeAsset() throws Exception {
        // Initialize the database
        shapeAssetRepository.saveAndFlush(shapeAsset);

        int databaseSizeBeforeDelete = shapeAssetRepository.findAll().size();

        // Delete the shapeAsset
        restShapeAssetMockMvc
            .perform(delete(ENTITY_API_URL_ID, shapeAsset.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShapeAsset> shapeAssetList = shapeAssetRepository.findAll();
        assertThat(shapeAssetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
