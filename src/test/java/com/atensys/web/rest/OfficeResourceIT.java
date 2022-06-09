package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Office;
import com.atensys.repository.OfficeRepository;
import com.atensys.service.dto.OfficeDTO;
import com.atensys.service.mapper.OfficeMapper;
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
 * Integration tests for the {@link OfficeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfficeResourceIT {

    private static final String DEFAULT_OFFICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS_SUFFIX = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WORK_TIME_CONFIG_YN = false;
    private static final Boolean UPDATED_WORK_TIME_CONFIG_YN = true;

    private static final String DEFAULT_WORK_START_TIME = "1527301800";
    private static final String UPDATED_WORK_START_TIME = "15491834";

    private static final String DEFAULT_WORK_END_TIME = "0754121104";
    private static final String UPDATED_WORK_END_TIME = "22332219";

    private static final Boolean DEFAULT_NO_SHOW_YN = false;
    private static final Boolean UPDATED_NO_SHOW_YN = true;

    private static final Boolean DEFAULT_NO_SHOW_PENALTY_YN = false;
    private static final Boolean UPDATED_NO_SHOW_PENALTY_YN = true;

    private static final Integer DEFAULT_NO_SHOW_TIMER_MINUTES = 1;
    private static final Integer UPDATED_NO_SHOW_TIMER_MINUTES = 2;

    private static final Integer DEFAULT_NO_SHOW_PENALTY_DAYS = 1;
    private static final Integer UPDATED_NO_SHOW_PENALTY_DAYS = 2;

    private static final Boolean DEFAULT_APPLY_RESERVATION_ALARM_YN = false;
    private static final Boolean UPDATED_APPLY_RESERVATION_ALARM_YN = true;

    private static final String DEFAULT_APPLY_RESERVATION_ALARM_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_APPLY_RESERVATION_ALARM_TEMPLATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRE_ENTRANCE_ALARM_YN = false;
    private static final Boolean UPDATED_PRE_ENTRANCE_ALARM_YN = true;

    private static final Integer DEFAULT_PRE_ENTRANCE_ALARM_INTERVAL = 1;
    private static final Integer UPDATED_PRE_ENTRANCE_ALARM_INTERVAL = 2;

    private static final String DEFAULT_PRE_ENTRANCE_ALARM_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_PRE_ENTRANCE_ALARM_TEMPLATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NO_SHOW_ALARM_YN = false;
    private static final Boolean UPDATED_NO_SHOW_ALARM_YN = true;

    private static final String DEFAULT_NO_SHOW_ALARM_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_NO_SHOW_ALARM_TEMPLATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXCLUSIVE = false;
    private static final Boolean UPDATED_EXCLUSIVE = true;

    private static final Boolean DEFAULT_USE_DUTY_TYPE = false;
    private static final Boolean UPDATED_USE_DUTY_TYPE = true;

    private static final Boolean DEFAULT_USE_USER_SEARCH = false;
    private static final Boolean UPDATED_USE_USER_SEARCH = true;

    private static final Boolean DEFAULT_KIOSK_ZOOM_ENABLE = false;
    private static final Boolean UPDATED_KIOSK_ZOOM_ENABLE = true;

    private static final Integer DEFAULT_RESERVATION_LIMIT_DAY = 1;
    private static final Integer UPDATED_RESERVATION_LIMIT_DAY = 2;

    private static final String DEFAULT_AVAILABLE_SEAT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AVAILABLE_SEAT_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTO_CHECK_IN_YN = false;
    private static final Boolean UPDATED_AUTO_CHECK_IN_YN = true;

    private static final String ENTITY_API_URL = "/api/offices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private OfficeMapper officeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfficeMockMvc;

    private Office office;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Office createEntity(EntityManager em) {
        Office office = new Office()
            .officeName(DEFAULT_OFFICE_NAME)
            .alias(DEFAULT_ALIAS)
            .aliasPrefix(DEFAULT_ALIAS_PREFIX)
            .aliasSuffix(DEFAULT_ALIAS_SUFFIX)
            .workTimeConfigYn(DEFAULT_WORK_TIME_CONFIG_YN)
            .workStartTime(DEFAULT_WORK_START_TIME)
            .workEndTime(DEFAULT_WORK_END_TIME)
            .noShowYn(DEFAULT_NO_SHOW_YN)
            .noShowPenaltyYn(DEFAULT_NO_SHOW_PENALTY_YN)
            .noShowTimerMinutes(DEFAULT_NO_SHOW_TIMER_MINUTES)
            .noShowPenaltyDays(DEFAULT_NO_SHOW_PENALTY_DAYS)
            .applyReservationAlarmYn(DEFAULT_APPLY_RESERVATION_ALARM_YN)
            .applyReservationAlarmTemplate(DEFAULT_APPLY_RESERVATION_ALARM_TEMPLATE)
            .preEntranceAlarmYn(DEFAULT_PRE_ENTRANCE_ALARM_YN)
            .preEntranceAlarmInterval(DEFAULT_PRE_ENTRANCE_ALARM_INTERVAL)
            .preEntranceAlarmTemplate(DEFAULT_PRE_ENTRANCE_ALARM_TEMPLATE)
            .noShowAlarmYn(DEFAULT_NO_SHOW_ALARM_YN)
            .noShowAlarmTemplate(DEFAULT_NO_SHOW_ALARM_TEMPLATE)
            .exclusive(DEFAULT_EXCLUSIVE)
            .useDutyType(DEFAULT_USE_DUTY_TYPE)
            .useUserSearch(DEFAULT_USE_USER_SEARCH)
            .kioskZoomEnable(DEFAULT_KIOSK_ZOOM_ENABLE)
            .reservationLimitDay(DEFAULT_RESERVATION_LIMIT_DAY)
            .availableSeatType(DEFAULT_AVAILABLE_SEAT_TYPE)
            .autoCheckInYn(DEFAULT_AUTO_CHECK_IN_YN);
        return office;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Office createUpdatedEntity(EntityManager em) {
        Office office = new Office()
            .officeName(UPDATED_OFFICE_NAME)
            .alias(UPDATED_ALIAS)
            .aliasPrefix(UPDATED_ALIAS_PREFIX)
            .aliasSuffix(UPDATED_ALIAS_SUFFIX)
            .workTimeConfigYn(UPDATED_WORK_TIME_CONFIG_YN)
            .workStartTime(UPDATED_WORK_START_TIME)
            .workEndTime(UPDATED_WORK_END_TIME)
            .noShowYn(UPDATED_NO_SHOW_YN)
            .noShowPenaltyYn(UPDATED_NO_SHOW_PENALTY_YN)
            .noShowTimerMinutes(UPDATED_NO_SHOW_TIMER_MINUTES)
            .noShowPenaltyDays(UPDATED_NO_SHOW_PENALTY_DAYS)
            .applyReservationAlarmYn(UPDATED_APPLY_RESERVATION_ALARM_YN)
            .applyReservationAlarmTemplate(UPDATED_APPLY_RESERVATION_ALARM_TEMPLATE)
            .preEntranceAlarmYn(UPDATED_PRE_ENTRANCE_ALARM_YN)
            .preEntranceAlarmInterval(UPDATED_PRE_ENTRANCE_ALARM_INTERVAL)
            .preEntranceAlarmTemplate(UPDATED_PRE_ENTRANCE_ALARM_TEMPLATE)
            .noShowAlarmYn(UPDATED_NO_SHOW_ALARM_YN)
            .noShowAlarmTemplate(UPDATED_NO_SHOW_ALARM_TEMPLATE)
            .exclusive(UPDATED_EXCLUSIVE)
            .useDutyType(UPDATED_USE_DUTY_TYPE)
            .useUserSearch(UPDATED_USE_USER_SEARCH)
            .kioskZoomEnable(UPDATED_KIOSK_ZOOM_ENABLE)
            .reservationLimitDay(UPDATED_RESERVATION_LIMIT_DAY)
            .availableSeatType(UPDATED_AVAILABLE_SEAT_TYPE)
            .autoCheckInYn(UPDATED_AUTO_CHECK_IN_YN);
        return office;
    }

    @BeforeEach
    public void initTest() {
        office = createEntity(em);
    }

    @Test
    @Transactional
    void createOffice() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();
        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);
        restOfficeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate + 1);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(DEFAULT_OFFICE_NAME);
        assertThat(testOffice.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testOffice.getAliasPrefix()).isEqualTo(DEFAULT_ALIAS_PREFIX);
        assertThat(testOffice.getAliasSuffix()).isEqualTo(DEFAULT_ALIAS_SUFFIX);
        assertThat(testOffice.getWorkTimeConfigYn()).isEqualTo(DEFAULT_WORK_TIME_CONFIG_YN);
        assertThat(testOffice.getWorkStartTime()).isEqualTo(DEFAULT_WORK_START_TIME);
        assertThat(testOffice.getWorkEndTime()).isEqualTo(DEFAULT_WORK_END_TIME);
        assertThat(testOffice.getNoShowYn()).isEqualTo(DEFAULT_NO_SHOW_YN);
        assertThat(testOffice.getNoShowPenaltyYn()).isEqualTo(DEFAULT_NO_SHOW_PENALTY_YN);
        assertThat(testOffice.getNoShowTimerMinutes()).isEqualTo(DEFAULT_NO_SHOW_TIMER_MINUTES);
        assertThat(testOffice.getNoShowPenaltyDays()).isEqualTo(DEFAULT_NO_SHOW_PENALTY_DAYS);
        assertThat(testOffice.getApplyReservationAlarmYn()).isEqualTo(DEFAULT_APPLY_RESERVATION_ALARM_YN);
        assertThat(testOffice.getApplyReservationAlarmTemplate()).isEqualTo(DEFAULT_APPLY_RESERVATION_ALARM_TEMPLATE);
        assertThat(testOffice.getPreEntranceAlarmYn()).isEqualTo(DEFAULT_PRE_ENTRANCE_ALARM_YN);
        assertThat(testOffice.getPreEntranceAlarmInterval()).isEqualTo(DEFAULT_PRE_ENTRANCE_ALARM_INTERVAL);
        assertThat(testOffice.getPreEntranceAlarmTemplate()).isEqualTo(DEFAULT_PRE_ENTRANCE_ALARM_TEMPLATE);
        assertThat(testOffice.getNoShowAlarmYn()).isEqualTo(DEFAULT_NO_SHOW_ALARM_YN);
        assertThat(testOffice.getNoShowAlarmTemplate()).isEqualTo(DEFAULT_NO_SHOW_ALARM_TEMPLATE);
        assertThat(testOffice.getExclusive()).isEqualTo(DEFAULT_EXCLUSIVE);
        assertThat(testOffice.getUseDutyType()).isEqualTo(DEFAULT_USE_DUTY_TYPE);
        assertThat(testOffice.getUseUserSearch()).isEqualTo(DEFAULT_USE_USER_SEARCH);
        assertThat(testOffice.getKioskZoomEnable()).isEqualTo(DEFAULT_KIOSK_ZOOM_ENABLE);
        assertThat(testOffice.getReservationLimitDay()).isEqualTo(DEFAULT_RESERVATION_LIMIT_DAY);
        assertThat(testOffice.getAvailableSeatType()).isEqualTo(DEFAULT_AVAILABLE_SEAT_TYPE);
        assertThat(testOffice.getAutoCheckInYn()).isEqualTo(DEFAULT_AUTO_CHECK_IN_YN);
    }

    @Test
    @Transactional
    void createOfficeWithExistingId() throws Exception {
        // Create the Office with an existing ID
        office.setId(1L);
        OfficeDTO officeDTO = officeMapper.toDto(office);

        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOfficeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeRepository.findAll().size();
        // set the field null
        office.setOfficeName(null);

        // Create the Office, which fails.
        OfficeDTO officeDTO = officeMapper.toDto(office);

        restOfficeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOffices() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList
        restOfficeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].officeName").value(hasItem(DEFAULT_OFFICE_NAME)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].aliasPrefix").value(hasItem(DEFAULT_ALIAS_PREFIX)))
            .andExpect(jsonPath("$.[*].aliasSuffix").value(hasItem(DEFAULT_ALIAS_SUFFIX)))
            .andExpect(jsonPath("$.[*].workTimeConfigYn").value(hasItem(DEFAULT_WORK_TIME_CONFIG_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].workStartTime").value(hasItem(DEFAULT_WORK_START_TIME)))
            .andExpect(jsonPath("$.[*].workEndTime").value(hasItem(DEFAULT_WORK_END_TIME)))
            .andExpect(jsonPath("$.[*].noShowYn").value(hasItem(DEFAULT_NO_SHOW_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].noShowPenaltyYn").value(hasItem(DEFAULT_NO_SHOW_PENALTY_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].noShowTimerMinutes").value(hasItem(DEFAULT_NO_SHOW_TIMER_MINUTES)))
            .andExpect(jsonPath("$.[*].noShowPenaltyDays").value(hasItem(DEFAULT_NO_SHOW_PENALTY_DAYS)))
            .andExpect(jsonPath("$.[*].applyReservationAlarmYn").value(hasItem(DEFAULT_APPLY_RESERVATION_ALARM_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].applyReservationAlarmTemplate").value(hasItem(DEFAULT_APPLY_RESERVATION_ALARM_TEMPLATE)))
            .andExpect(jsonPath("$.[*].preEntranceAlarmYn").value(hasItem(DEFAULT_PRE_ENTRANCE_ALARM_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].preEntranceAlarmInterval").value(hasItem(DEFAULT_PRE_ENTRANCE_ALARM_INTERVAL)))
            .andExpect(jsonPath("$.[*].preEntranceAlarmTemplate").value(hasItem(DEFAULT_PRE_ENTRANCE_ALARM_TEMPLATE)))
            .andExpect(jsonPath("$.[*].noShowAlarmYn").value(hasItem(DEFAULT_NO_SHOW_ALARM_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].noShowAlarmTemplate").value(hasItem(DEFAULT_NO_SHOW_ALARM_TEMPLATE)))
            .andExpect(jsonPath("$.[*].exclusive").value(hasItem(DEFAULT_EXCLUSIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].useDutyType").value(hasItem(DEFAULT_USE_DUTY_TYPE.booleanValue())))
            .andExpect(jsonPath("$.[*].useUserSearch").value(hasItem(DEFAULT_USE_USER_SEARCH.booleanValue())))
            .andExpect(jsonPath("$.[*].kioskZoomEnable").value(hasItem(DEFAULT_KIOSK_ZOOM_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].reservationLimitDay").value(hasItem(DEFAULT_RESERVATION_LIMIT_DAY)))
            .andExpect(jsonPath("$.[*].availableSeatType").value(hasItem(DEFAULT_AVAILABLE_SEAT_TYPE)))
            .andExpect(jsonPath("$.[*].autoCheckInYn").value(hasItem(DEFAULT_AUTO_CHECK_IN_YN.booleanValue())));
    }

    @Test
    @Transactional
    void getOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get the office
        restOfficeMockMvc
            .perform(get(ENTITY_API_URL_ID, office.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(office.getId().intValue()))
            .andExpect(jsonPath("$.officeName").value(DEFAULT_OFFICE_NAME))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS))
            .andExpect(jsonPath("$.aliasPrefix").value(DEFAULT_ALIAS_PREFIX))
            .andExpect(jsonPath("$.aliasSuffix").value(DEFAULT_ALIAS_SUFFIX))
            .andExpect(jsonPath("$.workTimeConfigYn").value(DEFAULT_WORK_TIME_CONFIG_YN.booleanValue()))
            .andExpect(jsonPath("$.workStartTime").value(DEFAULT_WORK_START_TIME))
            .andExpect(jsonPath("$.workEndTime").value(DEFAULT_WORK_END_TIME))
            .andExpect(jsonPath("$.noShowYn").value(DEFAULT_NO_SHOW_YN.booleanValue()))
            .andExpect(jsonPath("$.noShowPenaltyYn").value(DEFAULT_NO_SHOW_PENALTY_YN.booleanValue()))
            .andExpect(jsonPath("$.noShowTimerMinutes").value(DEFAULT_NO_SHOW_TIMER_MINUTES))
            .andExpect(jsonPath("$.noShowPenaltyDays").value(DEFAULT_NO_SHOW_PENALTY_DAYS))
            .andExpect(jsonPath("$.applyReservationAlarmYn").value(DEFAULT_APPLY_RESERVATION_ALARM_YN.booleanValue()))
            .andExpect(jsonPath("$.applyReservationAlarmTemplate").value(DEFAULT_APPLY_RESERVATION_ALARM_TEMPLATE))
            .andExpect(jsonPath("$.preEntranceAlarmYn").value(DEFAULT_PRE_ENTRANCE_ALARM_YN.booleanValue()))
            .andExpect(jsonPath("$.preEntranceAlarmInterval").value(DEFAULT_PRE_ENTRANCE_ALARM_INTERVAL))
            .andExpect(jsonPath("$.preEntranceAlarmTemplate").value(DEFAULT_PRE_ENTRANCE_ALARM_TEMPLATE))
            .andExpect(jsonPath("$.noShowAlarmYn").value(DEFAULT_NO_SHOW_ALARM_YN.booleanValue()))
            .andExpect(jsonPath("$.noShowAlarmTemplate").value(DEFAULT_NO_SHOW_ALARM_TEMPLATE))
            .andExpect(jsonPath("$.exclusive").value(DEFAULT_EXCLUSIVE.booleanValue()))
            .andExpect(jsonPath("$.useDutyType").value(DEFAULT_USE_DUTY_TYPE.booleanValue()))
            .andExpect(jsonPath("$.useUserSearch").value(DEFAULT_USE_USER_SEARCH.booleanValue()))
            .andExpect(jsonPath("$.kioskZoomEnable").value(DEFAULT_KIOSK_ZOOM_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.reservationLimitDay").value(DEFAULT_RESERVATION_LIMIT_DAY))
            .andExpect(jsonPath("$.availableSeatType").value(DEFAULT_AVAILABLE_SEAT_TYPE))
            .andExpect(jsonPath("$.autoCheckInYn").value(DEFAULT_AUTO_CHECK_IN_YN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingOffice() throws Exception {
        // Get the office
        restOfficeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office
        Office updatedOffice = officeRepository.findById(office.getId()).get();
        // Disconnect from session so that the updates on updatedOffice are not directly saved in db
        em.detach(updatedOffice);
        updatedOffice
            .officeName(UPDATED_OFFICE_NAME)
            .alias(UPDATED_ALIAS)
            .aliasPrefix(UPDATED_ALIAS_PREFIX)
            .aliasSuffix(UPDATED_ALIAS_SUFFIX)
            .workTimeConfigYn(UPDATED_WORK_TIME_CONFIG_YN)
            .workStartTime(UPDATED_WORK_START_TIME)
            .workEndTime(UPDATED_WORK_END_TIME)
            .noShowYn(UPDATED_NO_SHOW_YN)
            .noShowPenaltyYn(UPDATED_NO_SHOW_PENALTY_YN)
            .noShowTimerMinutes(UPDATED_NO_SHOW_TIMER_MINUTES)
            .noShowPenaltyDays(UPDATED_NO_SHOW_PENALTY_DAYS)
            .applyReservationAlarmYn(UPDATED_APPLY_RESERVATION_ALARM_YN)
            .applyReservationAlarmTemplate(UPDATED_APPLY_RESERVATION_ALARM_TEMPLATE)
            .preEntranceAlarmYn(UPDATED_PRE_ENTRANCE_ALARM_YN)
            .preEntranceAlarmInterval(UPDATED_PRE_ENTRANCE_ALARM_INTERVAL)
            .preEntranceAlarmTemplate(UPDATED_PRE_ENTRANCE_ALARM_TEMPLATE)
            .noShowAlarmYn(UPDATED_NO_SHOW_ALARM_YN)
            .noShowAlarmTemplate(UPDATED_NO_SHOW_ALARM_TEMPLATE)
            .exclusive(UPDATED_EXCLUSIVE)
            .useDutyType(UPDATED_USE_DUTY_TYPE)
            .useUserSearch(UPDATED_USE_USER_SEARCH)
            .kioskZoomEnable(UPDATED_KIOSK_ZOOM_ENABLE)
            .reservationLimitDay(UPDATED_RESERVATION_LIMIT_DAY)
            .availableSeatType(UPDATED_AVAILABLE_SEAT_TYPE)
            .autoCheckInYn(UPDATED_AUTO_CHECK_IN_YN);
        OfficeDTO officeDTO = officeMapper.toDto(updatedOffice);

        restOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, officeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(UPDATED_OFFICE_NAME);
        assertThat(testOffice.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testOffice.getAliasPrefix()).isEqualTo(UPDATED_ALIAS_PREFIX);
        assertThat(testOffice.getAliasSuffix()).isEqualTo(UPDATED_ALIAS_SUFFIX);
        assertThat(testOffice.getWorkTimeConfigYn()).isEqualTo(UPDATED_WORK_TIME_CONFIG_YN);
        assertThat(testOffice.getWorkStartTime()).isEqualTo(UPDATED_WORK_START_TIME);
        assertThat(testOffice.getWorkEndTime()).isEqualTo(UPDATED_WORK_END_TIME);
        assertThat(testOffice.getNoShowYn()).isEqualTo(UPDATED_NO_SHOW_YN);
        assertThat(testOffice.getNoShowPenaltyYn()).isEqualTo(UPDATED_NO_SHOW_PENALTY_YN);
        assertThat(testOffice.getNoShowTimerMinutes()).isEqualTo(UPDATED_NO_SHOW_TIMER_MINUTES);
        assertThat(testOffice.getNoShowPenaltyDays()).isEqualTo(UPDATED_NO_SHOW_PENALTY_DAYS);
        assertThat(testOffice.getApplyReservationAlarmYn()).isEqualTo(UPDATED_APPLY_RESERVATION_ALARM_YN);
        assertThat(testOffice.getApplyReservationAlarmTemplate()).isEqualTo(UPDATED_APPLY_RESERVATION_ALARM_TEMPLATE);
        assertThat(testOffice.getPreEntranceAlarmYn()).isEqualTo(UPDATED_PRE_ENTRANCE_ALARM_YN);
        assertThat(testOffice.getPreEntranceAlarmInterval()).isEqualTo(UPDATED_PRE_ENTRANCE_ALARM_INTERVAL);
        assertThat(testOffice.getPreEntranceAlarmTemplate()).isEqualTo(UPDATED_PRE_ENTRANCE_ALARM_TEMPLATE);
        assertThat(testOffice.getNoShowAlarmYn()).isEqualTo(UPDATED_NO_SHOW_ALARM_YN);
        assertThat(testOffice.getNoShowAlarmTemplate()).isEqualTo(UPDATED_NO_SHOW_ALARM_TEMPLATE);
        assertThat(testOffice.getExclusive()).isEqualTo(UPDATED_EXCLUSIVE);
        assertThat(testOffice.getUseDutyType()).isEqualTo(UPDATED_USE_DUTY_TYPE);
        assertThat(testOffice.getUseUserSearch()).isEqualTo(UPDATED_USE_USER_SEARCH);
        assertThat(testOffice.getKioskZoomEnable()).isEqualTo(UPDATED_KIOSK_ZOOM_ENABLE);
        assertThat(testOffice.getReservationLimitDay()).isEqualTo(UPDATED_RESERVATION_LIMIT_DAY);
        assertThat(testOffice.getAvailableSeatType()).isEqualTo(UPDATED_AVAILABLE_SEAT_TYPE);
        assertThat(testOffice.getAutoCheckInYn()).isEqualTo(UPDATED_AUTO_CHECK_IN_YN);
    }

    @Test
    @Transactional
    void putNonExistingOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(count.incrementAndGet());

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, officeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(count.incrementAndGet());

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(count.incrementAndGet());

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfficeWithPatch() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office using partial update
        Office partialUpdatedOffice = new Office();
        partialUpdatedOffice.setId(office.getId());

        partialUpdatedOffice
            .officeName(UPDATED_OFFICE_NAME)
            .aliasSuffix(UPDATED_ALIAS_SUFFIX)
            .workStartTime(UPDATED_WORK_START_TIME)
            .workEndTime(UPDATED_WORK_END_TIME)
            .noShowYn(UPDATED_NO_SHOW_YN)
            .preEntranceAlarmYn(UPDATED_PRE_ENTRANCE_ALARM_YN)
            .noShowAlarmTemplate(UPDATED_NO_SHOW_ALARM_TEMPLATE)
            .exclusive(UPDATED_EXCLUSIVE)
            .useUserSearch(UPDATED_USE_USER_SEARCH);

        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffice))
            )
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(UPDATED_OFFICE_NAME);
        assertThat(testOffice.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testOffice.getAliasPrefix()).isEqualTo(DEFAULT_ALIAS_PREFIX);
        assertThat(testOffice.getAliasSuffix()).isEqualTo(UPDATED_ALIAS_SUFFIX);
        assertThat(testOffice.getWorkTimeConfigYn()).isEqualTo(DEFAULT_WORK_TIME_CONFIG_YN);
        assertThat(testOffice.getWorkStartTime()).isEqualTo(UPDATED_WORK_START_TIME);
        assertThat(testOffice.getWorkEndTime()).isEqualTo(UPDATED_WORK_END_TIME);
        assertThat(testOffice.getNoShowYn()).isEqualTo(UPDATED_NO_SHOW_YN);
        assertThat(testOffice.getNoShowPenaltyYn()).isEqualTo(DEFAULT_NO_SHOW_PENALTY_YN);
        assertThat(testOffice.getNoShowTimerMinutes()).isEqualTo(DEFAULT_NO_SHOW_TIMER_MINUTES);
        assertThat(testOffice.getNoShowPenaltyDays()).isEqualTo(DEFAULT_NO_SHOW_PENALTY_DAYS);
        assertThat(testOffice.getApplyReservationAlarmYn()).isEqualTo(DEFAULT_APPLY_RESERVATION_ALARM_YN);
        assertThat(testOffice.getApplyReservationAlarmTemplate()).isEqualTo(DEFAULT_APPLY_RESERVATION_ALARM_TEMPLATE);
        assertThat(testOffice.getPreEntranceAlarmYn()).isEqualTo(UPDATED_PRE_ENTRANCE_ALARM_YN);
        assertThat(testOffice.getPreEntranceAlarmInterval()).isEqualTo(DEFAULT_PRE_ENTRANCE_ALARM_INTERVAL);
        assertThat(testOffice.getPreEntranceAlarmTemplate()).isEqualTo(DEFAULT_PRE_ENTRANCE_ALARM_TEMPLATE);
        assertThat(testOffice.getNoShowAlarmYn()).isEqualTo(DEFAULT_NO_SHOW_ALARM_YN);
        assertThat(testOffice.getNoShowAlarmTemplate()).isEqualTo(UPDATED_NO_SHOW_ALARM_TEMPLATE);
        assertThat(testOffice.getExclusive()).isEqualTo(UPDATED_EXCLUSIVE);
        assertThat(testOffice.getUseDutyType()).isEqualTo(DEFAULT_USE_DUTY_TYPE);
        assertThat(testOffice.getUseUserSearch()).isEqualTo(UPDATED_USE_USER_SEARCH);
        assertThat(testOffice.getKioskZoomEnable()).isEqualTo(DEFAULT_KIOSK_ZOOM_ENABLE);
        assertThat(testOffice.getReservationLimitDay()).isEqualTo(DEFAULT_RESERVATION_LIMIT_DAY);
        assertThat(testOffice.getAvailableSeatType()).isEqualTo(DEFAULT_AVAILABLE_SEAT_TYPE);
        assertThat(testOffice.getAutoCheckInYn()).isEqualTo(DEFAULT_AUTO_CHECK_IN_YN);
    }

    @Test
    @Transactional
    void fullUpdateOfficeWithPatch() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office using partial update
        Office partialUpdatedOffice = new Office();
        partialUpdatedOffice.setId(office.getId());

        partialUpdatedOffice
            .officeName(UPDATED_OFFICE_NAME)
            .alias(UPDATED_ALIAS)
            .aliasPrefix(UPDATED_ALIAS_PREFIX)
            .aliasSuffix(UPDATED_ALIAS_SUFFIX)
            .workTimeConfigYn(UPDATED_WORK_TIME_CONFIG_YN)
            .workStartTime(UPDATED_WORK_START_TIME)
            .workEndTime(UPDATED_WORK_END_TIME)
            .noShowYn(UPDATED_NO_SHOW_YN)
            .noShowPenaltyYn(UPDATED_NO_SHOW_PENALTY_YN)
            .noShowTimerMinutes(UPDATED_NO_SHOW_TIMER_MINUTES)
            .noShowPenaltyDays(UPDATED_NO_SHOW_PENALTY_DAYS)
            .applyReservationAlarmYn(UPDATED_APPLY_RESERVATION_ALARM_YN)
            .applyReservationAlarmTemplate(UPDATED_APPLY_RESERVATION_ALARM_TEMPLATE)
            .preEntranceAlarmYn(UPDATED_PRE_ENTRANCE_ALARM_YN)
            .preEntranceAlarmInterval(UPDATED_PRE_ENTRANCE_ALARM_INTERVAL)
            .preEntranceAlarmTemplate(UPDATED_PRE_ENTRANCE_ALARM_TEMPLATE)
            .noShowAlarmYn(UPDATED_NO_SHOW_ALARM_YN)
            .noShowAlarmTemplate(UPDATED_NO_SHOW_ALARM_TEMPLATE)
            .exclusive(UPDATED_EXCLUSIVE)
            .useDutyType(UPDATED_USE_DUTY_TYPE)
            .useUserSearch(UPDATED_USE_USER_SEARCH)
            .kioskZoomEnable(UPDATED_KIOSK_ZOOM_ENABLE)
            .reservationLimitDay(UPDATED_RESERVATION_LIMIT_DAY)
            .availableSeatType(UPDATED_AVAILABLE_SEAT_TYPE)
            .autoCheckInYn(UPDATED_AUTO_CHECK_IN_YN);

        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffice))
            )
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getOfficeName()).isEqualTo(UPDATED_OFFICE_NAME);
        assertThat(testOffice.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testOffice.getAliasPrefix()).isEqualTo(UPDATED_ALIAS_PREFIX);
        assertThat(testOffice.getAliasSuffix()).isEqualTo(UPDATED_ALIAS_SUFFIX);
        assertThat(testOffice.getWorkTimeConfigYn()).isEqualTo(UPDATED_WORK_TIME_CONFIG_YN);
        assertThat(testOffice.getWorkStartTime()).isEqualTo(UPDATED_WORK_START_TIME);
        assertThat(testOffice.getWorkEndTime()).isEqualTo(UPDATED_WORK_END_TIME);
        assertThat(testOffice.getNoShowYn()).isEqualTo(UPDATED_NO_SHOW_YN);
        assertThat(testOffice.getNoShowPenaltyYn()).isEqualTo(UPDATED_NO_SHOW_PENALTY_YN);
        assertThat(testOffice.getNoShowTimerMinutes()).isEqualTo(UPDATED_NO_SHOW_TIMER_MINUTES);
        assertThat(testOffice.getNoShowPenaltyDays()).isEqualTo(UPDATED_NO_SHOW_PENALTY_DAYS);
        assertThat(testOffice.getApplyReservationAlarmYn()).isEqualTo(UPDATED_APPLY_RESERVATION_ALARM_YN);
        assertThat(testOffice.getApplyReservationAlarmTemplate()).isEqualTo(UPDATED_APPLY_RESERVATION_ALARM_TEMPLATE);
        assertThat(testOffice.getPreEntranceAlarmYn()).isEqualTo(UPDATED_PRE_ENTRANCE_ALARM_YN);
        assertThat(testOffice.getPreEntranceAlarmInterval()).isEqualTo(UPDATED_PRE_ENTRANCE_ALARM_INTERVAL);
        assertThat(testOffice.getPreEntranceAlarmTemplate()).isEqualTo(UPDATED_PRE_ENTRANCE_ALARM_TEMPLATE);
        assertThat(testOffice.getNoShowAlarmYn()).isEqualTo(UPDATED_NO_SHOW_ALARM_YN);
        assertThat(testOffice.getNoShowAlarmTemplate()).isEqualTo(UPDATED_NO_SHOW_ALARM_TEMPLATE);
        assertThat(testOffice.getExclusive()).isEqualTo(UPDATED_EXCLUSIVE);
        assertThat(testOffice.getUseDutyType()).isEqualTo(UPDATED_USE_DUTY_TYPE);
        assertThat(testOffice.getUseUserSearch()).isEqualTo(UPDATED_USE_USER_SEARCH);
        assertThat(testOffice.getKioskZoomEnable()).isEqualTo(UPDATED_KIOSK_ZOOM_ENABLE);
        assertThat(testOffice.getReservationLimitDay()).isEqualTo(UPDATED_RESERVATION_LIMIT_DAY);
        assertThat(testOffice.getAvailableSeatType()).isEqualTo(UPDATED_AVAILABLE_SEAT_TYPE);
        assertThat(testOffice.getAutoCheckInYn()).isEqualTo(UPDATED_AUTO_CHECK_IN_YN);
    }

    @Test
    @Transactional
    void patchNonExistingOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(count.incrementAndGet());

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, officeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(count.incrementAndGet());

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();
        office.setId(count.incrementAndGet());

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfficeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(officeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeDelete = officeRepository.findAll().size();

        // Delete the office
        restOfficeMockMvc
            .perform(delete(ENTITY_API_URL_ID, office.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
