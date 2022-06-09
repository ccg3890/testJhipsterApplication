package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Room;
import com.atensys.repository.RoomRepository;
import com.atensys.service.dto.RoomDTO;
import com.atensys.service.mapper.RoomMapper;
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
 * Integration tests for the {@link RoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomResourceIT {

    private static final Long DEFAULT_OFFICE_ID = 1L;
    private static final Long UPDATED_OFFICE_ID = 2L;

    private static final Long DEFAULT_FLOOR_ID = 1L;
    private static final Long UPDATED_FLOOR_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PERSONNEL = 1420L;
    private static final Long UPDATED_PERSONNEL = 1419L;

    private static final Long DEFAULT_TOP = 0L;
    private static final Long UPDATED_TOP = 1L;

    private static final Long DEFAULT_LEFT = 0L;
    private static final Long UPDATED_LEFT = 1L;

    private static final Boolean DEFAULT_USE_RESERVATION_TIME = false;
    private static final Boolean UPDATED_USE_RESERVATION_TIME = true;

    private static final String DEFAULT_RESERVATION_AVAILABLE_FROM_TIME = "16:07045113";
    private static final String UPDATED_RESERVATION_AVAILABLE_FROM_TIME = "19:02185643";

    private static final String DEFAULT_RESERVATION_AVAILABLE_TO_TIME = "09:02565713";
    private static final String UPDATED_RESERVATION_AVAILABLE_TO_TIME = "23:3407";

    private static final Boolean DEFAULT_USE_APPROVAL = false;
    private static final Boolean UPDATED_USE_APPROVAL = true;

    private static final Boolean DEFAULT_USE_USER_AVAILABLE = false;
    private static final Boolean UPDATED_USE_USER_AVAILABLE = true;

    private static final Boolean DEFAULT_USE_CHECK_IN_OUT = false;
    private static final Boolean UPDATED_USE_CHECK_IN_OUT = true;

    private static final String DEFAULT_CHECK_IN_OUT_FROM_TIME = "18:29231556";
    private static final String UPDATED_CHECK_IN_OUT_FROM_TIME = "03:3155";

    private static final String DEFAULT_CHECK_IN_OUT_TO_TIME = "21:2936152936";
    private static final String UPDATED_CHECK_IN_OUT_TO_TIME = "22:342332190528";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String ENTITY_API_URL = "/api/rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomMockMvc;

    private Room room;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createEntity(EntityManager em) {
        Room room = new Room()
            .officeId(DEFAULT_OFFICE_ID)
            .floorId(DEFAULT_FLOOR_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .personnel(DEFAULT_PERSONNEL)
            .top(DEFAULT_TOP)
            .left(DEFAULT_LEFT)
            .useReservationTime(DEFAULT_USE_RESERVATION_TIME)
            .reservationAvailableFromTime(DEFAULT_RESERVATION_AVAILABLE_FROM_TIME)
            .reservationAvailableToTime(DEFAULT_RESERVATION_AVAILABLE_TO_TIME)
            .useApproval(DEFAULT_USE_APPROVAL)
            .useUserAvailable(DEFAULT_USE_USER_AVAILABLE)
            .useCheckInOut(DEFAULT_USE_CHECK_IN_OUT)
            .checkInOutFromTime(DEFAULT_CHECK_IN_OUT_FROM_TIME)
            .checkInOutToTime(DEFAULT_CHECK_IN_OUT_TO_TIME)
            .valid(DEFAULT_VALID);
        return room;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createUpdatedEntity(EntityManager em) {
        Room room = new Room()
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .personnel(UPDATED_PERSONNEL)
            .top(UPDATED_TOP)
            .left(UPDATED_LEFT)
            .useReservationTime(UPDATED_USE_RESERVATION_TIME)
            .reservationAvailableFromTime(UPDATED_RESERVATION_AVAILABLE_FROM_TIME)
            .reservationAvailableToTime(UPDATED_RESERVATION_AVAILABLE_TO_TIME)
            .useApproval(UPDATED_USE_APPROVAL)
            .useUserAvailable(UPDATED_USE_USER_AVAILABLE)
            .useCheckInOut(UPDATED_USE_CHECK_IN_OUT)
            .checkInOutFromTime(UPDATED_CHECK_IN_OUT_FROM_TIME)
            .checkInOutToTime(UPDATED_CHECK_IN_OUT_TO_TIME)
            .valid(UPDATED_VALID);
        return room;
    }

    @BeforeEach
    public void initTest() {
        room = createEntity(em);
    }

    @Test
    @Transactional
    void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();
        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);
        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getOfficeId()).isEqualTo(DEFAULT_OFFICE_ID);
        assertThat(testRoom.getFloorId()).isEqualTo(DEFAULT_FLOOR_ID);
        assertThat(testRoom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoom.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoom.getPersonnel()).isEqualTo(DEFAULT_PERSONNEL);
        assertThat(testRoom.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testRoom.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testRoom.getUseReservationTime()).isEqualTo(DEFAULT_USE_RESERVATION_TIME);
        assertThat(testRoom.getReservationAvailableFromTime()).isEqualTo(DEFAULT_RESERVATION_AVAILABLE_FROM_TIME);
        assertThat(testRoom.getReservationAvailableToTime()).isEqualTo(DEFAULT_RESERVATION_AVAILABLE_TO_TIME);
        assertThat(testRoom.getUseApproval()).isEqualTo(DEFAULT_USE_APPROVAL);
        assertThat(testRoom.getUseUserAvailable()).isEqualTo(DEFAULT_USE_USER_AVAILABLE);
        assertThat(testRoom.getUseCheckInOut()).isEqualTo(DEFAULT_USE_CHECK_IN_OUT);
        assertThat(testRoom.getCheckInOutFromTime()).isEqualTo(DEFAULT_CHECK_IN_OUT_FROM_TIME);
        assertThat(testRoom.getCheckInOutToTime()).isEqualTo(DEFAULT_CHECK_IN_OUT_TO_TIME);
        assertThat(testRoom.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void createRoomWithExistingId() throws Exception {
        // Create the Room with an existing ID
        room.setId(1L);
        RoomDTO roomDTO = roomMapper.toDto(room);

        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOfficeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setOfficeId(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFloorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setFloorId(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setName(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseReservationTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setUseReservationTime(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseApprovalIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setUseApproval(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseUserAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setUseUserAvailable(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseCheckInOutIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setUseCheckInOut(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setValid(null);

        // Create the Room, which fails.
        RoomDTO roomDTO = roomMapper.toDto(room);

        restRoomMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].officeId").value(hasItem(DEFAULT_OFFICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].floorId").value(hasItem(DEFAULT_FLOOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].personnel").value(hasItem(DEFAULT_PERSONNEL.intValue())))
            .andExpect(jsonPath("$.[*].top").value(hasItem(DEFAULT_TOP.intValue())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].useReservationTime").value(hasItem(DEFAULT_USE_RESERVATION_TIME.booleanValue())))
            .andExpect(jsonPath("$.[*].reservationAvailableFromTime").value(hasItem(DEFAULT_RESERVATION_AVAILABLE_FROM_TIME)))
            .andExpect(jsonPath("$.[*].reservationAvailableToTime").value(hasItem(DEFAULT_RESERVATION_AVAILABLE_TO_TIME)))
            .andExpect(jsonPath("$.[*].useApproval").value(hasItem(DEFAULT_USE_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].useUserAvailable").value(hasItem(DEFAULT_USE_USER_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].useCheckInOut").value(hasItem(DEFAULT_USE_CHECK_IN_OUT.booleanValue())))
            .andExpect(jsonPath("$.[*].checkInOutFromTime").value(hasItem(DEFAULT_CHECK_IN_OUT_FROM_TIME)))
            .andExpect(jsonPath("$.[*].checkInOutToTime").value(hasItem(DEFAULT_CHECK_IN_OUT_TO_TIME)))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }

    @Test
    @Transactional
    void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.officeId").value(DEFAULT_OFFICE_ID.intValue()))
            .andExpect(jsonPath("$.floorId").value(DEFAULT_FLOOR_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.personnel").value(DEFAULT_PERSONNEL.intValue()))
            .andExpect(jsonPath("$.top").value(DEFAULT_TOP.intValue()))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()))
            .andExpect(jsonPath("$.useReservationTime").value(DEFAULT_USE_RESERVATION_TIME.booleanValue()))
            .andExpect(jsonPath("$.reservationAvailableFromTime").value(DEFAULT_RESERVATION_AVAILABLE_FROM_TIME))
            .andExpect(jsonPath("$.reservationAvailableToTime").value(DEFAULT_RESERVATION_AVAILABLE_TO_TIME))
            .andExpect(jsonPath("$.useApproval").value(DEFAULT_USE_APPROVAL.booleanValue()))
            .andExpect(jsonPath("$.useUserAvailable").value(DEFAULT_USE_USER_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.useCheckInOut").value(DEFAULT_USE_CHECK_IN_OUT.booleanValue()))
            .andExpect(jsonPath("$.checkInOutFromTime").value(DEFAULT_CHECK_IN_OUT_FROM_TIME))
            .andExpect(jsonPath("$.checkInOutToTime").value(DEFAULT_CHECK_IN_OUT_TO_TIME))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        Room updatedRoom = roomRepository.findById(room.getId()).get();
        // Disconnect from session so that the updates on updatedRoom are not directly saved in db
        em.detach(updatedRoom);
        updatedRoom
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .personnel(UPDATED_PERSONNEL)
            .top(UPDATED_TOP)
            .left(UPDATED_LEFT)
            .useReservationTime(UPDATED_USE_RESERVATION_TIME)
            .reservationAvailableFromTime(UPDATED_RESERVATION_AVAILABLE_FROM_TIME)
            .reservationAvailableToTime(UPDATED_RESERVATION_AVAILABLE_TO_TIME)
            .useApproval(UPDATED_USE_APPROVAL)
            .useUserAvailable(UPDATED_USE_USER_AVAILABLE)
            .useCheckInOut(UPDATED_USE_CHECK_IN_OUT)
            .checkInOutFromTime(UPDATED_CHECK_IN_OUT_FROM_TIME)
            .checkInOutToTime(UPDATED_CHECK_IN_OUT_TO_TIME)
            .valid(UPDATED_VALID);
        RoomDTO roomDTO = roomMapper.toDto(updatedRoom);

        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testRoom.getFloorId()).isEqualTo(UPDATED_FLOOR_ID);
        assertThat(testRoom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoom.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoom.getPersonnel()).isEqualTo(UPDATED_PERSONNEL);
        assertThat(testRoom.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testRoom.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testRoom.getUseReservationTime()).isEqualTo(UPDATED_USE_RESERVATION_TIME);
        assertThat(testRoom.getReservationAvailableFromTime()).isEqualTo(UPDATED_RESERVATION_AVAILABLE_FROM_TIME);
        assertThat(testRoom.getReservationAvailableToTime()).isEqualTo(UPDATED_RESERVATION_AVAILABLE_TO_TIME);
        assertThat(testRoom.getUseApproval()).isEqualTo(UPDATED_USE_APPROVAL);
        assertThat(testRoom.getUseUserAvailable()).isEqualTo(UPDATED_USE_USER_AVAILABLE);
        assertThat(testRoom.getUseCheckInOut()).isEqualTo(UPDATED_USE_CHECK_IN_OUT);
        assertThat(testRoom.getCheckInOutFromTime()).isEqualTo(UPDATED_CHECK_IN_OUT_FROM_TIME);
        assertThat(testRoom.getCheckInOutToTime()).isEqualTo(UPDATED_CHECK_IN_OUT_TO_TIME);
        assertThat(testRoom.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void putNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .description(UPDATED_DESCRIPTION)
            .left(UPDATED_LEFT)
            .useReservationTime(UPDATED_USE_RESERVATION_TIME)
            .useApproval(UPDATED_USE_APPROVAL)
            .useUserAvailable(UPDATED_USE_USER_AVAILABLE)
            .useCheckInOut(UPDATED_USE_CHECK_IN_OUT);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testRoom.getFloorId()).isEqualTo(UPDATED_FLOOR_ID);
        assertThat(testRoom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoom.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoom.getPersonnel()).isEqualTo(DEFAULT_PERSONNEL);
        assertThat(testRoom.getTop()).isEqualTo(DEFAULT_TOP);
        assertThat(testRoom.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testRoom.getUseReservationTime()).isEqualTo(UPDATED_USE_RESERVATION_TIME);
        assertThat(testRoom.getReservationAvailableFromTime()).isEqualTo(DEFAULT_RESERVATION_AVAILABLE_FROM_TIME);
        assertThat(testRoom.getReservationAvailableToTime()).isEqualTo(DEFAULT_RESERVATION_AVAILABLE_TO_TIME);
        assertThat(testRoom.getUseApproval()).isEqualTo(UPDATED_USE_APPROVAL);
        assertThat(testRoom.getUseUserAvailable()).isEqualTo(UPDATED_USE_USER_AVAILABLE);
        assertThat(testRoom.getUseCheckInOut()).isEqualTo(UPDATED_USE_CHECK_IN_OUT);
        assertThat(testRoom.getCheckInOutFromTime()).isEqualTo(DEFAULT_CHECK_IN_OUT_FROM_TIME);
        assertThat(testRoom.getCheckInOutToTime()).isEqualTo(DEFAULT_CHECK_IN_OUT_TO_TIME);
        assertThat(testRoom.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void fullUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom
            .officeId(UPDATED_OFFICE_ID)
            .floorId(UPDATED_FLOOR_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .personnel(UPDATED_PERSONNEL)
            .top(UPDATED_TOP)
            .left(UPDATED_LEFT)
            .useReservationTime(UPDATED_USE_RESERVATION_TIME)
            .reservationAvailableFromTime(UPDATED_RESERVATION_AVAILABLE_FROM_TIME)
            .reservationAvailableToTime(UPDATED_RESERVATION_AVAILABLE_TO_TIME)
            .useApproval(UPDATED_USE_APPROVAL)
            .useUserAvailable(UPDATED_USE_USER_AVAILABLE)
            .useCheckInOut(UPDATED_USE_CHECK_IN_OUT)
            .checkInOutFromTime(UPDATED_CHECK_IN_OUT_FROM_TIME)
            .checkInOutToTime(UPDATED_CHECK_IN_OUT_TO_TIME)
            .valid(UPDATED_VALID);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testRoom.getFloorId()).isEqualTo(UPDATED_FLOOR_ID);
        assertThat(testRoom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoom.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoom.getPersonnel()).isEqualTo(UPDATED_PERSONNEL);
        assertThat(testRoom.getTop()).isEqualTo(UPDATED_TOP);
        assertThat(testRoom.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testRoom.getUseReservationTime()).isEqualTo(UPDATED_USE_RESERVATION_TIME);
        assertThat(testRoom.getReservationAvailableFromTime()).isEqualTo(UPDATED_RESERVATION_AVAILABLE_FROM_TIME);
        assertThat(testRoom.getReservationAvailableToTime()).isEqualTo(UPDATED_RESERVATION_AVAILABLE_TO_TIME);
        assertThat(testRoom.getUseApproval()).isEqualTo(UPDATED_USE_APPROVAL);
        assertThat(testRoom.getUseUserAvailable()).isEqualTo(UPDATED_USE_USER_AVAILABLE);
        assertThat(testRoom.getUseCheckInOut()).isEqualTo(UPDATED_USE_CHECK_IN_OUT);
        assertThat(testRoom.getCheckInOutFromTime()).isEqualTo(UPDATED_CHECK_IN_OUT_FROM_TIME);
        assertThat(testRoom.getCheckInOutToTime()).isEqualTo(UPDATED_CHECK_IN_OUT_TO_TIME);
        assertThat(testRoom.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void patchNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Delete the room
        restRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, room.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
