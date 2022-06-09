package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Reservation;
import com.atensys.domain.enumeration.ResvStatusType;
import com.atensys.repository.ReservationRepository;
import com.atensys.service.dto.ReservationDTO;
import com.atensys.service.mapper.ReservationMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ReservationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ResvStatusType DEFAULT_STATUS = ResvStatusType.RESERVED;
    private static final ResvStatusType UPDATED_STATUS = ResvStatusType.COMFIRMED;

    private static final String DEFAULT_ORGANIZER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_USE_ALL_DAY = false;
    private static final Boolean UPDATED_USE_ALL_DAY = true;

    private static final Boolean DEFAULT_USE_RECURRENCE = false;
    private static final Boolean UPDATED_USE_RECURRENCE = true;

    private static final Boolean DEFAULT_USE_RESPONSE = false;
    private static final Boolean UPDATED_USE_RESPONSE = true;

    private static final String DEFAULT_PIN = "AAAAAAAAAA";
    private static final String UPDATED_PIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reservations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .organizerId(DEFAULT_ORGANIZER_ID)
            .organizerName(DEFAULT_ORGANIZER_NAME)
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .useAllDay(DEFAULT_USE_ALL_DAY)
            .useRecurrence(DEFAULT_USE_RECURRENCE)
            .useResponse(DEFAULT_USE_RESPONSE)
            .pin(DEFAULT_PIN);
        return reservation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createUpdatedEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .organizerId(UPDATED_ORGANIZER_ID)
            .organizerName(UPDATED_ORGANIZER_NAME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .useAllDay(UPDATED_USE_ALL_DAY)
            .useRecurrence(UPDATED_USE_RECURRENCE)
            .useResponse(UPDATED_USE_RESPONSE)
            .pin(UPDATED_PIN);
        return reservation;
    }

    @BeforeEach
    public void initTest() {
        reservation = createEntity(em);
    }

    @Test
    @Transactional
    void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();
        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);
        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReservation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReservation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReservation.getOrganizerId()).isEqualTo(DEFAULT_ORGANIZER_ID);
        assertThat(testReservation.getOrganizerName()).isEqualTo(DEFAULT_ORGANIZER_NAME);
        assertThat(testReservation.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testReservation.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testReservation.getUseAllDay()).isEqualTo(DEFAULT_USE_ALL_DAY);
        assertThat(testReservation.getUseRecurrence()).isEqualTo(DEFAULT_USE_RECURRENCE);
        assertThat(testReservation.getUseResponse()).isEqualTo(DEFAULT_USE_RESPONSE);
        assertThat(testReservation.getPin()).isEqualTo(DEFAULT_PIN);
    }

    @Test
    @Transactional
    void createReservationWithExistingId() throws Exception {
        // Create the Reservation with an existing ID
        reservation.setId(1L);
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setTitle(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setStatus(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrganizerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setOrganizerId(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrganizerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setOrganizerName(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setStartDateTime(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setEndDateTime(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseAllDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setUseAllDay(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseRecurrenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setUseRecurrence(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseResponseIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setUseResponse(null);

        // Create the Reservation, which fails.
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList
        restReservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].organizerId").value(hasItem(DEFAULT_ORGANIZER_ID)))
            .andExpect(jsonPath("$.[*].organizerName").value(hasItem(DEFAULT_ORGANIZER_NAME)))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(DEFAULT_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(DEFAULT_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].useAllDay").value(hasItem(DEFAULT_USE_ALL_DAY.booleanValue())))
            .andExpect(jsonPath("$.[*].useRecurrence").value(hasItem(DEFAULT_USE_RECURRENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].useResponse").value(hasItem(DEFAULT_USE_RESPONSE.booleanValue())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)));
    }

    @Test
    @Transactional
    void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc
            .perform(get(ENTITY_API_URL_ID, reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.organizerId").value(DEFAULT_ORGANIZER_ID))
            .andExpect(jsonPath("$.organizerName").value(DEFAULT_ORGANIZER_NAME))
            .andExpect(jsonPath("$.startDateTime").value(DEFAULT_START_DATE_TIME.toString()))
            .andExpect(jsonPath("$.endDateTime").value(DEFAULT_END_DATE_TIME.toString()))
            .andExpect(jsonPath("$.useAllDay").value(DEFAULT_USE_ALL_DAY.booleanValue()))
            .andExpect(jsonPath("$.useRecurrence").value(DEFAULT_USE_RECURRENCE.booleanValue()))
            .andExpect(jsonPath("$.useResponse").value(DEFAULT_USE_RESPONSE.booleanValue()))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN));
    }

    @Test
    @Transactional
    void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
        // Disconnect from session so that the updates on updatedReservation are not directly saved in db
        em.detach(updatedReservation);
        updatedReservation
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .organizerId(UPDATED_ORGANIZER_ID)
            .organizerName(UPDATED_ORGANIZER_NAME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .useAllDay(UPDATED_USE_ALL_DAY)
            .useRecurrence(UPDATED_USE_RECURRENCE)
            .useResponse(UPDATED_USE_RESPONSE)
            .pin(UPDATED_PIN);
        ReservationDTO reservationDTO = reservationMapper.toDto(updatedReservation);

        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReservation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReservation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReservation.getOrganizerId()).isEqualTo(UPDATED_ORGANIZER_ID);
        assertThat(testReservation.getOrganizerName()).isEqualTo(UPDATED_ORGANIZER_NAME);
        assertThat(testReservation.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testReservation.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testReservation.getUseAllDay()).isEqualTo(UPDATED_USE_ALL_DAY);
        assertThat(testReservation.getUseRecurrence()).isEqualTo(UPDATED_USE_RECURRENCE);
        assertThat(testReservation.getUseResponse()).isEqualTo(UPDATED_USE_RESPONSE);
        assertThat(testReservation.getPin()).isEqualTo(UPDATED_PIN);
    }

    @Test
    @Transactional
    void putNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservationWithPatch() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation using partial update
        Reservation partialUpdatedReservation = new Reservation();
        partialUpdatedReservation.setId(reservation.getId());

        partialUpdatedReservation
            .status(UPDATED_STATUS)
            .organizerName(UPDATED_ORGANIZER_NAME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .useAllDay(UPDATED_USE_ALL_DAY)
            .useRecurrence(UPDATED_USE_RECURRENCE)
            .pin(UPDATED_PIN);

        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservation))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReservation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReservation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReservation.getOrganizerId()).isEqualTo(DEFAULT_ORGANIZER_ID);
        assertThat(testReservation.getOrganizerName()).isEqualTo(UPDATED_ORGANIZER_NAME);
        assertThat(testReservation.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testReservation.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testReservation.getUseAllDay()).isEqualTo(UPDATED_USE_ALL_DAY);
        assertThat(testReservation.getUseRecurrence()).isEqualTo(UPDATED_USE_RECURRENCE);
        assertThat(testReservation.getUseResponse()).isEqualTo(DEFAULT_USE_RESPONSE);
        assertThat(testReservation.getPin()).isEqualTo(UPDATED_PIN);
    }

    @Test
    @Transactional
    void fullUpdateReservationWithPatch() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation using partial update
        Reservation partialUpdatedReservation = new Reservation();
        partialUpdatedReservation.setId(reservation.getId());

        partialUpdatedReservation
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .organizerId(UPDATED_ORGANIZER_ID)
            .organizerName(UPDATED_ORGANIZER_NAME)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .useAllDay(UPDATED_USE_ALL_DAY)
            .useRecurrence(UPDATED_USE_RECURRENCE)
            .useResponse(UPDATED_USE_RESPONSE)
            .pin(UPDATED_PIN);

        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservation))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReservation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReservation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReservation.getOrganizerId()).isEqualTo(UPDATED_ORGANIZER_ID);
        assertThat(testReservation.getOrganizerName()).isEqualTo(UPDATED_ORGANIZER_NAME);
        assertThat(testReservation.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testReservation.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testReservation.getUseAllDay()).isEqualTo(UPDATED_USE_ALL_DAY);
        assertThat(testReservation.getUseRecurrence()).isEqualTo(UPDATED_USE_RECURRENCE);
        assertThat(testReservation.getUseResponse()).isEqualTo(UPDATED_USE_RESPONSE);
        assertThat(testReservation.getPin()).isEqualTo(UPDATED_PIN);
    }

    @Test
    @Transactional
    void patchNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Delete the reservation
        restReservationMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
