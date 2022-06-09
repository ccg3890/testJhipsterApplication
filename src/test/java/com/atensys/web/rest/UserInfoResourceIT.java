package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.UserInfo;
import com.atensys.domain.enumeration.UserStatus;
import com.atensys.repository.UserInfoRepository;
import com.atensys.service.UserInfoService;
import com.atensys.service.dto.UserInfoDTO;
import com.atensys.service.mapper.UserInfoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserInfoResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final UserStatus DEFAULT_USER_STAT = UserStatus.TENURE;
    private static final UserStatus UPDATED_USER_STAT = UserStatus.RETIREMENT;

    private static final String DEFAULT_ORG_CD = "AAAAAAAAAA";
    private static final String UPDATED_ORG_CD = "BBBBBBBBBB";

    private static final String DEFAULT_RANK_CD = "AAAAAAAAAA";
    private static final String UPDATED_RANK_CD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_NO = "AAAAAAAAAA";
    private static final String UPDATED_TEL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MULTI_RESERVATION = false;
    private static final Boolean UPDATED_MULTI_RESERVATION = true;

    private static final Boolean DEFAULT_NEWBIE = false;
    private static final Boolean UPDATED_NEWBIE = true;

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HRSYNC = false;
    private static final Boolean UPDATED_HRSYNC = true;

    private static final String ENTITY_API_URL = "/api/user-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Mock
    private UserInfoRepository userInfoRepositoryMock;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Mock
    private UserInfoService userInfoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .userStat(DEFAULT_USER_STAT)
            .orgCd(DEFAULT_ORG_CD)
            .rankCd(DEFAULT_RANK_CD)
            .email(DEFAULT_EMAIL)
            .mobile(DEFAULT_MOBILE)
            .telNo(DEFAULT_TEL_NO)
            .cardNo(DEFAULT_CARD_NO)
            .multiReservation(DEFAULT_MULTI_RESERVATION)
            .newbie(DEFAULT_NEWBIE)
            .fileId(DEFAULT_FILE_ID)
            .hrsync(DEFAULT_HRSYNC);
        return userInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createUpdatedEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .userStat(UPDATED_USER_STAT)
            .orgCd(UPDATED_ORG_CD)
            .rankCd(UPDATED_RANK_CD)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .telNo(UPDATED_TEL_NO)
            .cardNo(UPDATED_CARD_NO)
            .multiReservation(UPDATED_MULTI_RESERVATION)
            .newbie(UPDATED_NEWBIE)
            .fileId(UPDATED_FILE_ID)
            .hrsync(UPDATED_HRSYNC);
        return userInfo;
    }

    @BeforeEach
    public void initTest() {
        userInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createUserInfo() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();
        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);
        restUserInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserInfo.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserInfo.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserInfo.getUserStat()).isEqualTo(DEFAULT_USER_STAT);
        assertThat(testUserInfo.getOrgCd()).isEqualTo(DEFAULT_ORG_CD);
        assertThat(testUserInfo.getRankCd()).isEqualTo(DEFAULT_RANK_CD);
        assertThat(testUserInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUserInfo.getTelNo()).isEqualTo(DEFAULT_TEL_NO);
        assertThat(testUserInfo.getCardNo()).isEqualTo(DEFAULT_CARD_NO);
        assertThat(testUserInfo.getMultiReservation()).isEqualTo(DEFAULT_MULTI_RESERVATION);
        assertThat(testUserInfo.getNewbie()).isEqualTo(DEFAULT_NEWBIE);
        assertThat(testUserInfo.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testUserInfo.getHrsync()).isEqualTo(DEFAULT_HRSYNC);
    }

    @Test
    @Transactional
    void createUserInfoWithExistingId() throws Exception {
        // Create the UserInfo with an existing ID
        userInfo.setId(1L);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHrsyncIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setHrsync(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        restUserInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].userStat").value(hasItem(DEFAULT_USER_STAT.toString())))
            .andExpect(jsonPath("$.[*].orgCd").value(hasItem(DEFAULT_ORG_CD)))
            .andExpect(jsonPath("$.[*].rankCd").value(hasItem(DEFAULT_RANK_CD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].telNo").value(hasItem(DEFAULT_TEL_NO)))
            .andExpect(jsonPath("$.[*].cardNo").value(hasItem(DEFAULT_CARD_NO)))
            .andExpect(jsonPath("$.[*].multiReservation").value(hasItem(DEFAULT_MULTI_RESERVATION.booleanValue())))
            .andExpect(jsonPath("$.[*].newbie").value(hasItem(DEFAULT_NEWBIE.booleanValue())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].hrsync").value(hasItem(DEFAULT_HRSYNC.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserInfosWithEagerRelationshipsIsEnabled() throws Exception {
        when(userInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserInfoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userInfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserInfosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userInfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserInfoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userInfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get the userInfo
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.userStat").value(DEFAULT_USER_STAT.toString()))
            .andExpect(jsonPath("$.orgCd").value(DEFAULT_ORG_CD))
            .andExpect(jsonPath("$.rankCd").value(DEFAULT_RANK_CD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.telNo").value(DEFAULT_TEL_NO))
            .andExpect(jsonPath("$.cardNo").value(DEFAULT_CARD_NO))
            .andExpect(jsonPath("$.multiReservation").value(DEFAULT_MULTI_RESERVATION.booleanValue()))
            .andExpect(jsonPath("$.newbie").value(DEFAULT_NEWBIE.booleanValue()))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.hrsync").value(DEFAULT_HRSYNC.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findById(userInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserInfo are not directly saved in db
        em.detach(updatedUserInfo);
        updatedUserInfo
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .userStat(UPDATED_USER_STAT)
            .orgCd(UPDATED_ORG_CD)
            .rankCd(UPDATED_RANK_CD)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .telNo(UPDATED_TEL_NO)
            .cardNo(UPDATED_CARD_NO)
            .multiReservation(UPDATED_MULTI_RESERVATION)
            .newbie(UPDATED_NEWBIE)
            .fileId(UPDATED_FILE_ID)
            .hrsync(UPDATED_HRSYNC);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(updatedUserInfo);

        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserInfo.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserInfo.getUserStat()).isEqualTo(UPDATED_USER_STAT);
        assertThat(testUserInfo.getOrgCd()).isEqualTo(UPDATED_ORG_CD);
        assertThat(testUserInfo.getRankCd()).isEqualTo(UPDATED_RANK_CD);
        assertThat(testUserInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserInfo.getTelNo()).isEqualTo(UPDATED_TEL_NO);
        assertThat(testUserInfo.getCardNo()).isEqualTo(UPDATED_CARD_NO);
        assertThat(testUserInfo.getMultiReservation()).isEqualTo(UPDATED_MULTI_RESERVATION);
        assertThat(testUserInfo.getNewbie()).isEqualTo(UPDATED_NEWBIE);
        assertThat(testUserInfo.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testUserInfo.getHrsync()).isEqualTo(UPDATED_HRSYNC);
    }

    @Test
    @Transactional
    void putNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userInfoDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserInfoWithPatch() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo using partial update
        UserInfo partialUpdatedUserInfo = new UserInfo();
        partialUpdatedUserInfo.setId(userInfo.getId());

        partialUpdatedUserInfo
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .userStat(UPDATED_USER_STAT)
            .mobile(UPDATED_MOBILE)
            .telNo(UPDATED_TEL_NO)
            .cardNo(UPDATED_CARD_NO)
            .newbie(UPDATED_NEWBIE);

        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserInfo.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserInfo.getUserStat()).isEqualTo(UPDATED_USER_STAT);
        assertThat(testUserInfo.getOrgCd()).isEqualTo(DEFAULT_ORG_CD);
        assertThat(testUserInfo.getRankCd()).isEqualTo(DEFAULT_RANK_CD);
        assertThat(testUserInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserInfo.getTelNo()).isEqualTo(UPDATED_TEL_NO);
        assertThat(testUserInfo.getCardNo()).isEqualTo(UPDATED_CARD_NO);
        assertThat(testUserInfo.getMultiReservation()).isEqualTo(DEFAULT_MULTI_RESERVATION);
        assertThat(testUserInfo.getNewbie()).isEqualTo(UPDATED_NEWBIE);
        assertThat(testUserInfo.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testUserInfo.getHrsync()).isEqualTo(DEFAULT_HRSYNC);
    }

    @Test
    @Transactional
    void fullUpdateUserInfoWithPatch() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo using partial update
        UserInfo partialUpdatedUserInfo = new UserInfo();
        partialUpdatedUserInfo.setId(userInfo.getId());

        partialUpdatedUserInfo
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .userStat(UPDATED_USER_STAT)
            .orgCd(UPDATED_ORG_CD)
            .rankCd(UPDATED_RANK_CD)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .telNo(UPDATED_TEL_NO)
            .cardNo(UPDATED_CARD_NO)
            .multiReservation(UPDATED_MULTI_RESERVATION)
            .newbie(UPDATED_NEWBIE)
            .fileId(UPDATED_FILE_ID)
            .hrsync(UPDATED_HRSYNC);

        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserInfo.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserInfo.getUserStat()).isEqualTo(UPDATED_USER_STAT);
        assertThat(testUserInfo.getOrgCd()).isEqualTo(UPDATED_ORG_CD);
        assertThat(testUserInfo.getRankCd()).isEqualTo(UPDATED_RANK_CD);
        assertThat(testUserInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserInfo.getTelNo()).isEqualTo(UPDATED_TEL_NO);
        assertThat(testUserInfo.getCardNo()).isEqualTo(UPDATED_CARD_NO);
        assertThat(testUserInfo.getMultiReservation()).isEqualTo(UPDATED_MULTI_RESERVATION);
        assertThat(testUserInfo.getNewbie()).isEqualTo(UPDATED_NEWBIE);
        assertThat(testUserInfo.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testUserInfo.getHrsync()).isEqualTo(UPDATED_HRSYNC);
    }

    @Test
    @Transactional
    void patchNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userInfoDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeDelete = userInfoRepository.findAll().size();

        // Delete the userInfo
        restUserInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, userInfo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
