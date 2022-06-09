package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Shape;
import com.atensys.domain.enumeration.ShapeType;
import com.atensys.repository.ShapeRepository;
import com.atensys.service.dto.ShapeDTO;
import com.atensys.service.mapper.ShapeMapper;
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
 * Integration tests for the {@link ShapeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShapeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ShapeType DEFAULT_SHAPE_TYPE = ShapeType.RECTANGLE;
    private static final ShapeType UPDATED_SHAPE_TYPE = ShapeType.TRIANBLE;

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String ENTITY_API_URL = "/api/shapes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShapeRepository shapeRepository;

    @Autowired
    private ShapeMapper shapeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShapeMockMvc;

    private Shape shape;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shape createEntity(EntityManager em) {
        Shape shape = new Shape().name(DEFAULT_NAME).shapeType(DEFAULT_SHAPE_TYPE).path(DEFAULT_PATH).valid(DEFAULT_VALID);
        return shape;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shape createUpdatedEntity(EntityManager em) {
        Shape shape = new Shape().name(UPDATED_NAME).shapeType(UPDATED_SHAPE_TYPE).path(UPDATED_PATH).valid(UPDATED_VALID);
        return shape;
    }

    @BeforeEach
    public void initTest() {
        shape = createEntity(em);
    }

    @Test
    @Transactional
    void createShape() throws Exception {
        int databaseSizeBeforeCreate = shapeRepository.findAll().size();
        // Create the Shape
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);
        restShapeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeCreate + 1);
        Shape testShape = shapeList.get(shapeList.size() - 1);
        assertThat(testShape.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShape.getShapeType()).isEqualTo(DEFAULT_SHAPE_TYPE);
        assertThat(testShape.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testShape.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void createShapeWithExistingId() throws Exception {
        // Create the Shape with an existing ID
        shape.setId(1L);
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        int databaseSizeBeforeCreate = shapeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShapeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkShapeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shapeRepository.findAll().size();
        // set the field null
        shape.setShapeType(null);

        // Create the Shape, which fails.
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        restShapeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = shapeRepository.findAll().size();
        // set the field null
        shape.setValid(null);

        // Create the Shape, which fails.
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        restShapeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShapes() throws Exception {
        // Initialize the database
        shapeRepository.saveAndFlush(shape);

        // Get all the shapeList
        restShapeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shape.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shapeType").value(hasItem(DEFAULT_SHAPE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }

    @Test
    @Transactional
    void getShape() throws Exception {
        // Initialize the database
        shapeRepository.saveAndFlush(shape);

        // Get the shape
        restShapeMockMvc
            .perform(get(ENTITY_API_URL_ID, shape.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shape.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shapeType").value(DEFAULT_SHAPE_TYPE.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingShape() throws Exception {
        // Get the shape
        restShapeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShape() throws Exception {
        // Initialize the database
        shapeRepository.saveAndFlush(shape);

        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();

        // Update the shape
        Shape updatedShape = shapeRepository.findById(shape.getId()).get();
        // Disconnect from session so that the updates on updatedShape are not directly saved in db
        em.detach(updatedShape);
        updatedShape.name(UPDATED_NAME).shapeType(UPDATED_SHAPE_TYPE).path(UPDATED_PATH).valid(UPDATED_VALID);
        ShapeDTO shapeDTO = shapeMapper.toDto(updatedShape);

        restShapeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shapeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
        Shape testShape = shapeList.get(shapeList.size() - 1);
        assertThat(testShape.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShape.getShapeType()).isEqualTo(UPDATED_SHAPE_TYPE);
        assertThat(testShape.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testShape.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void putNonExistingShape() throws Exception {
        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();
        shape.setId(count.incrementAndGet());

        // Create the Shape
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShapeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shapeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShape() throws Exception {
        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();
        shape.setId(count.incrementAndGet());

        // Create the Shape
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShape() throws Exception {
        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();
        shape.setId(count.incrementAndGet());

        // Create the Shape
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShapeWithPatch() throws Exception {
        // Initialize the database
        shapeRepository.saveAndFlush(shape);

        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();

        // Update the shape using partial update
        Shape partialUpdatedShape = new Shape();
        partialUpdatedShape.setId(shape.getId());

        partialUpdatedShape.name(UPDATED_NAME).shapeType(UPDATED_SHAPE_TYPE);

        restShapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShape.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShape))
            )
            .andExpect(status().isOk());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
        Shape testShape = shapeList.get(shapeList.size() - 1);
        assertThat(testShape.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShape.getShapeType()).isEqualTo(UPDATED_SHAPE_TYPE);
        assertThat(testShape.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testShape.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void fullUpdateShapeWithPatch() throws Exception {
        // Initialize the database
        shapeRepository.saveAndFlush(shape);

        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();

        // Update the shape using partial update
        Shape partialUpdatedShape = new Shape();
        partialUpdatedShape.setId(shape.getId());

        partialUpdatedShape.name(UPDATED_NAME).shapeType(UPDATED_SHAPE_TYPE).path(UPDATED_PATH).valid(UPDATED_VALID);

        restShapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShape.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShape))
            )
            .andExpect(status().isOk());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
        Shape testShape = shapeList.get(shapeList.size() - 1);
        assertThat(testShape.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShape.getShapeType()).isEqualTo(UPDATED_SHAPE_TYPE);
        assertThat(testShape.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testShape.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void patchNonExistingShape() throws Exception {
        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();
        shape.setId(count.incrementAndGet());

        // Create the Shape
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shapeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShape() throws Exception {
        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();
        shape.setId(count.incrementAndGet());

        // Create the Shape
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShape() throws Exception {
        int databaseSizeBeforeUpdate = shapeRepository.findAll().size();
        shape.setId(count.incrementAndGet());

        // Create the Shape
        ShapeDTO shapeDTO = shapeMapper.toDto(shape);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShapeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shapeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shape in the database
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShape() throws Exception {
        // Initialize the database
        shapeRepository.saveAndFlush(shape);

        int databaseSizeBeforeDelete = shapeRepository.findAll().size();

        // Delete the shape
        restShapeMockMvc
            .perform(delete(ENTITY_API_URL_ID, shape.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shape> shapeList = shapeRepository.findAll();
        assertThat(shapeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
