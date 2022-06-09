package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.RoomSeat;
import com.atensys.repository.RoomSeatRepository;
import com.atensys.service.dto.RoomSeatDTO;
import com.atensys.service.mapper.RoomSeatMapper;
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
 * Integration tests for the {@link RoomSeatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomSeatResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_TOP = 0L;
    private static final Long UPDATED_TOP = 1L;

    private static final Long DEFAULT_LEFT = 0L;
    private static final Long UPDATED_LEFT = 1L;

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String ENTITY_API_URL = "/api/room-seats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomSeatRepository roomSeatRepository;

    @Autowired
    private RoomSeatMapper roomSeatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomSeatMockMvc;

    private RoomSeat roomSeat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomSeat createEntity(EntityManager em) {
        RoomSeat roomSeat = new RoomSeat()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .top(DEFAULT_TOP)
            .left(DEFAULT_LEFT)
            .valid(DEFAULT_VALID);
        return roomSeat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoomSeat createUpdatedEntity(EntityManager em) {
        RoomSeat roomSeat = new RoomSeat()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .top(UPDATED_TOP)
            .left(UPDATED_LEFT)
            .valid(UPDATED_VALID);
        return roomSeat;
    }

    @BeforeEach
    public void initTest() {
        roomSeat = createEntity(em);
    }

    @Test
    @Transactional
    void createRoomSeat() throws Exception {
        int databaseSizeBeforeCreate = roomSeatRepository.findAll().size();
        // Create the RoomSeat
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);
        restRoomSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeCreate + 1);
        RoomSeat testRoomSeat = roomSeatList.get(roomSeatList.size() - 1);
        assertThat(testRoomSeat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoomSeat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoomSeat.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testRoomSeat.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testRoomSeat.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void createRoomSeatWithExistingId() throws Exception {
        // Create the RoomSeat with an existing ID
        roomSeat.setId(1L);
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        int databaseSizeBeforeCreate = roomSeatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomSeatRepository.findAll().size();
        // set the field null
        roomSeat.setName(null);

        // Create the RoomSeat, which fails.
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        restRoomSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomSeatRepository.findAll().size();
        // set the field null
        roomSeat.setValid(null);

        // Create the RoomSeat, which fails.
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        restRoomSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoomSeats() throws Exception {
        // Initialize the database
        roomSeatRepository.saveAndFlush(roomSeat);

        // Get all the roomSeatList
        restRoomSeatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomSeat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].top").value(hasItem(DEFAULT_TOP.intValue())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }

    @Test
    @Transactional
    void getRoomSeat() throws Exception {
        // Initialize the database
        roomSeatRepository.saveAndFlush(roomSeat);

        // Get the roomSeat
        restRoomSeatMockMvc
            .perform(get(ENTITY_API_URL_ID, roomSeat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roomSeat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.top").value(DEFAULT_TOP.intValue()))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRoomSeat() throws Exception {
        // Get the roomSeat
        restRoomSeatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoomSeat() throws Exception {
        // Initialize the database
        roomSeatRepository.saveAndFlush(roomSeat);

        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();

        // Update the roomSeat
        RoomSeat updatedRoomSeat = roomSeatRepository.findById(roomSeat.getId()).get();
        // Disconnect from session so that the updates on updatedRoomSeat are not directly saved in db
        em.detach(updatedRoomSeat);
        updatedRoomSeat.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).top(UPDATED_TOP).left(UPDATED_LEFT).valid(UPDATED_VALID);
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(updatedRoomSeat);

        restRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomSeatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isOk());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
        RoomSeat testRoomSeat = roomSeatList.get(roomSeatList.size() - 1);
        assertThat(testRoomSeat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoomSeat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomSeat.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testRoomSeat.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testRoomSeat.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void putNonExistingRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();
        roomSeat.setId(count.incrementAndGet());

        // Create the RoomSeat
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomSeatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();
        roomSeat.setId(count.incrementAndGet());

        // Create the RoomSeat
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();
        roomSeat.setId(count.incrementAndGet());

        // Create the RoomSeat
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomSeatWithPatch() throws Exception {
        // Initialize the database
        roomSeatRepository.saveAndFlush(roomSeat);

        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();

        // Update the roomSeat using partial update
        RoomSeat partialUpdatedRoomSeat = new RoomSeat();
        partialUpdatedRoomSeat.setId(roomSeat.getId());

        partialUpdatedRoomSeat.description(UPDATED_DESCRIPTION).top(UPDATED_TOP);

        restRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomSeat))
            )
            .andExpect(status().isOk());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
        RoomSeat testRoomSeat = roomSeatList.get(roomSeatList.size() - 1);
        assertThat(testRoomSeat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoomSeat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomSeat.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testRoomSeat.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testRoomSeat.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void fullUpdateRoomSeatWithPatch() throws Exception {
        // Initialize the database
        roomSeatRepository.saveAndFlush(roomSeat);

        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();

        // Update the roomSeat using partial update
        RoomSeat partialUpdatedRoomSeat = new RoomSeat();
        partialUpdatedRoomSeat.setId(roomSeat.getId());

        partialUpdatedRoomSeat.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).top(UPDATED_TOP).left(UPDATED_LEFT).valid(UPDATED_VALID);

        restRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoomSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoomSeat))
            )
            .andExpect(status().isOk());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
        RoomSeat testRoomSeat = roomSeatList.get(roomSeatList.size() - 1);
        assertThat(testRoomSeat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoomSeat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoomSeat.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testRoomSeat.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testRoomSeat.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void patchNonExistingRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();
        roomSeat.setId(count.incrementAndGet());

        // Create the RoomSeat
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomSeatDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();
        roomSeat.setId(count.incrementAndGet());

        // Create the RoomSeat
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = roomSeatRepository.findAll().size();
        roomSeat.setId(count.incrementAndGet());

        // Create the RoomSeat
        RoomSeatDTO roomSeatDTO = roomSeatMapper.toDto(roomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomSeatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoomSeat in the database
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoomSeat() throws Exception {
        // Initialize the database
        roomSeatRepository.saveAndFlush(roomSeat);

        int databaseSizeBeforeDelete = roomSeatRepository.findAll().size();

        // Delete the roomSeat
        restRoomSeatMockMvc
            .perform(delete(ENTITY_API_URL_ID, roomSeat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoomSeat> roomSeatList = roomSeatRepository.findAll();
        assertThat(roomSeatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
