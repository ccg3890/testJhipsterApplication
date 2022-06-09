package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Seat;
import com.atensys.domain.enumeration.SeatSubType;
import com.atensys.domain.enumeration.SeatType;
import com.atensys.repository.SeatRepository;
import com.atensys.service.dto.SeatDTO;
import com.atensys.service.mapper.SeatMapper;
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
 * Integration tests for the {@link SeatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeatResourceIT {

    private static final Long DEFAULT_OFFICE_ID = 1L;
    private static final Long UPDATED_OFFICE_ID = 2L;

    private static final Long DEFAULT_FLOOR_ID = 1L;
    private static final Long UPDATED_FLOOR_ID = 2L;

    private static final SeatType DEFAULT_SEAT_TYPE = SeatType.FULL;
    private static final SeatType UPDATED_SEAT_TYPE = SeatType.PART;

    private static final String DEFAULT_SEAT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NAME = "BBBBBBBBBB";

    private static final SeatSubType DEFAULT_SEAT_SUB_TYPE = SeatSubType.NORMAL;
    private static final SeatSubType UPDATED_SEAT_SUB_TYPE = SeatSubType.COLABORATION;

    private static final Boolean DEFAULT_USE_YN = false;
    private static final Boolean UPDATED_USE_YN = true;

    private static final Long DEFAULT_TOP = 0L;
    private static final Long UPDATED_TOP = 1L;

    private static final Long DEFAULT_LEFT = 0L;
    private static final Long UPDATED_LEFT = 1L;

    private static final String ENTITY_API_URL = "/api/seats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeatMockMvc;

    private Seat seat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seat createEntity(EntityManager em) {
        Seat seat = new Seat()
            .officeId(DEFAULT_OFFICE_ID)
            .floorId(DEFAULT_FLOOR_ID)
            .seatType(DEFAULT_SEAT_TYPE)
            .seatName(DEFAULT_SEAT_NAME)
            .seatSubType(DEFAULT_SEAT_SUB_TYPE)
            .useYn(DEFAULT_USE_YN)
            .top(DEFAULT_TOP)
            .left(DEFAULT_LEFT);
        return seat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seat createUpdatedEntity(EntityManager em) {
        Seat seat = new Seat()
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .seatType(UPDATED_SEAT_TYPE)
            .seatName(UPDATED_SEAT_NAME)
            .seatSubType(UPDATED_SEAT_SUB_TYPE)
            .useYn(UPDATED_USE_YN)
            .top(UPDATED_TOP)
            .left(UPDATED_LEFT);
        return seat;
    }

    @BeforeEach
    public void initTest() {
        seat = createEntity(em);
    }

    @Test
    @Transactional
    void createSeat() throws Exception {
        int databaseSizeBeforeCreate = seatRepository.findAll().size();
        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);
        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeCreate + 1);
        Seat testSeat = seatList.get(seatList.size() - 1);
        assertThat(testSeat.getOfficeId()).isEqualTo(DEFAULT_OFFICE_ID);
        assertThat(testSeat.getFloorId()).isEqualTo(DEFAULT_FLOOR_ID);
        assertThat(testSeat.getSeatType()).isEqualTo(DEFAULT_SEAT_TYPE);
        assertThat(testSeat.getSeatName()).isEqualTo(DEFAULT_SEAT_NAME);
        assertThat(testSeat.getSeatSubType()).isEqualTo(DEFAULT_SEAT_SUB_TYPE);
        assertThat(testSeat.getUseYn()).isEqualTo(DEFAULT_USE_YN);
        assertThat(testSeat.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testSeat.getLeft()).isEqualTo(DEFAULT_LEFT);
    }

    @Test
    @Transactional
    void createSeatWithExistingId() throws Exception {
        // Create the Seat with an existing ID
        seat.setId(1L);
        SeatDTO seatDTO = seatMapper.toDto(seat);

        int databaseSizeBeforeCreate = seatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOfficeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRepository.findAll().size();
        // set the field null
        seat.setOfficeId(null);

        // Create the Seat, which fails.
        SeatDTO seatDTO = seatMapper.toDto(seat);

        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFloorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRepository.findAll().size();
        // set the field null
        seat.setFloorId(null);

        // Create the Seat, which fails.
        SeatDTO seatDTO = seatMapper.toDto(seat);

        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeatTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRepository.findAll().size();
        // set the field null
        seat.setSeatType(null);

        // Create the Seat, which fails.
        SeatDTO seatDTO = seatMapper.toDto(seat);

        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeatNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRepository.findAll().size();
        // set the field null
        seat.setSeatName(null);

        // Create the Seat, which fails.
        SeatDTO seatDTO = seatMapper.toDto(seat);

        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeatSubTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRepository.findAll().size();
        // set the field null
        seat.setSeatSubType(null);

        // Create the Seat, which fails.
        SeatDTO seatDTO = seatMapper.toDto(seat);

        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseYnIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRepository.findAll().size();
        // set the field null
        seat.setUseYn(null);

        // Create the Seat, which fails.
        SeatDTO seatDTO = seatMapper.toDto(seat);

        restSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSeats() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        // Get all the seatList
        restSeatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seat.getId().intValue())))
            .andExpect(jsonPath("$.[*].officeId").value(hasItem(DEFAULT_OFFICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].floorId").value(hasItem(DEFAULT_FLOOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].seatType").value(hasItem(DEFAULT_SEAT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].seatName").value(hasItem(DEFAULT_SEAT_NAME)))
            .andExpect(jsonPath("$.[*].seatSubType").value(hasItem(DEFAULT_SEAT_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].top").value(hasItem(DEFAULT_TOP.intValue())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())));
    }

    @Test
    @Transactional
    void getSeat() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        // Get the seat
        restSeatMockMvc
            .perform(get(ENTITY_API_URL_ID, seat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seat.getId().intValue()))
            .andExpect(jsonPath("$.officeId").value(DEFAULT_OFFICE_ID.intValue()))
            .andExpect(jsonPath("$.floorId").value(DEFAULT_FLOOR_ID.intValue()))
            .andExpect(jsonPath("$.seatType").value(DEFAULT_SEAT_TYPE.toString()))
            .andExpect(jsonPath("$.seatName").value(DEFAULT_SEAT_NAME))
            .andExpect(jsonPath("$.seatSubType").value(DEFAULT_SEAT_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.booleanValue()))
            .andExpect(jsonPath("$.top").value(DEFAULT_TOP.intValue()))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSeat() throws Exception {
        // Get the seat
        restSeatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeat() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        int databaseSizeBeforeUpdate = seatRepository.findAll().size();

        // Update the seat
        Seat updatedSeat = seatRepository.findById(seat.getId()).get();
        // Disconnect from session so that the updates on updatedSeat are not directly saved in db
        em.detach(updatedSeat);
        updatedSeat
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .seatType(UPDATED_SEAT_TYPE)
            .seatName(UPDATED_SEAT_NAME)
            .seatSubType(UPDATED_SEAT_SUB_TYPE)
            .useYn(UPDATED_USE_YN)
            .top(UPDATED_TOP)
            .left(UPDATED_LEFT);
        SeatDTO seatDTO = seatMapper.toDto(updatedSeat);

        restSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isOk());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
        Seat testSeat = seatList.get(seatList.size() - 1);
        assertThat(testSeat.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testSeat.getFloorId()).isEqualTo(UPDATED_FLOOR_ID);
        assertThat(testSeat.getSeatType()).isEqualTo(UPDATED_SEAT_TYPE);
        assertThat(testSeat.getSeatName()).isEqualTo(UPDATED_SEAT_NAME);
        assertThat(testSeat.getSeatSubType()).isEqualTo(UPDATED_SEAT_SUB_TYPE);
        assertThat(testSeat.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testSeat.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testSeat.getLeft()).isEqualTo(UPDATED_LEFT);
    }

    @Test
    @Transactional
    void putNonExistingSeat() throws Exception {
        int databaseSizeBeforeUpdate = seatRepository.findAll().size();
        seat.setId(count.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeat() throws Exception {
        int databaseSizeBeforeUpdate = seatRepository.findAll().size();
        seat.setId(count.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeat() throws Exception {
        int databaseSizeBeforeUpdate = seatRepository.findAll().size();
        seat.setId(count.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeatWithPatch() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        int databaseSizeBeforeUpdate = seatRepository.findAll().size();

        // Update the seat using partial update
        Seat partialUpdatedSeat = new Seat();
        partialUpdatedSeat.setId(seat.getId());

        partialUpdatedSeat
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .seatType(UPDATED_SEAT_TYPE)
            .seatName(UPDATED_SEAT_NAME)
            .seatSubType(UPDATED_SEAT_SUB_TYPE)
            .useYn(UPDATED_USE_YN)
            .left(UPDATED_LEFT);

        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeat))
            )
            .andExpect(status().isOk());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
        Seat testSeat = seatList.get(seatList.size() - 1);
        assertThat(testSeat.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testSeat.getFloorId()).isEqualTo(UPDATED_FLOOR_ID);
        assertThat(testSeat.getSeatType()).isEqualTo(UPDATED_SEAT_TYPE);
        assertThat(testSeat.getSeatName()).isEqualTo(UPDATED_SEAT_NAME);
        assertThat(testSeat.getSeatSubType()).isEqualTo(UPDATED_SEAT_SUB_TYPE);
        assertThat(testSeat.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testSeat.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testSeat.getLeft()).isEqualTo(UPDATED_LEFT);
    }

    @Test
    @Transactional
    void fullUpdateSeatWithPatch() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        int databaseSizeBeforeUpdate = seatRepository.findAll().size();

        // Update the seat using partial update
        Seat partialUpdatedSeat = new Seat();
        partialUpdatedSeat.setId(seat.getId());

        partialUpdatedSeat
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .seatType(UPDATED_SEAT_TYPE)
            .seatName(UPDATED_SEAT_NAME)
            .seatSubType(UPDATED_SEAT_SUB_TYPE)
            .useYn(UPDATED_USE_YN)
            .top(UPDATED_TOP)
            .left(UPDATED_LEFT);

        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeat))
            )
            .andExpect(status().isOk());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
        Seat testSeat = seatList.get(seatList.size() - 1);
        assertThat(testSeat.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testSeat.getFloorId()).isEqualTo(UPDATED_FLOOR_ID);
        assertThat(testSeat.getSeatType()).isEqualTo(UPDATED_SEAT_TYPE);
        assertThat(testSeat.getSeatName()).isEqualTo(UPDATED_SEAT_NAME);
        assertThat(testSeat.getSeatSubType()).isEqualTo(UPDATED_SEAT_SUB_TYPE);
        assertThat(testSeat.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testSeat.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testSeat.getLeft()).isEqualTo(UPDATED_LEFT);
    }

    @Test
    @Transactional
    void patchNonExistingSeat() throws Exception {
        int databaseSizeBeforeUpdate = seatRepository.findAll().size();
        seat.setId(count.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seatDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeat() throws Exception {
        int databaseSizeBeforeUpdate = seatRepository.findAll().size();
        seat.setId(count.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeat() throws Exception {
        int databaseSizeBeforeUpdate = seatRepository.findAll().size();
        seat.setId(count.incrementAndGet());

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeat() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        int databaseSizeBeforeDelete = seatRepository.findAll().size();

        // Delete the seat
        restSeatMockMvc
            .perform(delete(ENTITY_API_URL_ID, seat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
