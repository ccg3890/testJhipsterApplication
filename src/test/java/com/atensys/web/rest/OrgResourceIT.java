package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Org;
import com.atensys.domain.enumeration.UnitType;
import com.atensys.repository.OrgRepository;
import com.atensys.service.dto.OrgDTO;
import com.atensys.service.mapper.OrgMapper;
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
 * Integration tests for the {@link OrgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrgResourceIT {

    private static final String DEFAULT_ORG_CD = "AAAAAAAAAA";
    private static final String UPDATED_ORG_CD = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_ENAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_ENAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_YN = false;
    private static final Boolean UPDATED_USE_YN = true;

    private static final String DEFAULT_UP_ORG_CD = "AAAAAAAAAA";
    private static final String UPDATED_UP_ORG_CD = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_TEL = "AAAAAAAAAA";
    private static final String UPDATED_ORG_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CD = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CD = "BBBBBBBBBB";

    private static final Double DEFAULT_ORDER_NO = 1D;
    private static final Double UPDATED_ORDER_NO = 2D;

    private static final String DEFAULT_HIERACHY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HIERACHY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_HIERACHY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HIERACHY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEAT_LIMIT = 1;
    private static final Integer UPDATED_SEAT_LIMIT = 2;

    private static final UnitType DEFAULT_SEAT_LIMIT_UNIT = UnitType.COUNT;
    private static final UnitType UPDATED_SEAT_LIMIT_UNIT = UnitType.PERCENT;

    private static final String ENTITY_API_URL = "/api/orgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgMockMvc;

    private Org org;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Org createEntity(EntityManager em) {
        Org org = new Org()
            .orgCd(DEFAULT_ORG_CD)
            .orgName(DEFAULT_ORG_NAME)
            .orgEname(DEFAULT_ORG_ENAME)
            .useYn(DEFAULT_USE_YN)
            .upOrgCd(DEFAULT_UP_ORG_CD)
            .orgTel(DEFAULT_ORG_TEL)
            .zipCd(DEFAULT_ZIP_CD)
            .orderNo(DEFAULT_ORDER_NO)
            .hierachyCode(DEFAULT_HIERACHY_CODE)
            .hierachyName(DEFAULT_HIERACHY_NAME)
            .seatLimit(DEFAULT_SEAT_LIMIT)
            .seatLimitUnit(DEFAULT_SEAT_LIMIT_UNIT);
        return org;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Org createUpdatedEntity(EntityManager em) {
        Org org = new Org()
            .orgCd(UPDATED_ORG_CD)
            .orgName(UPDATED_ORG_NAME)
            .orgEname(UPDATED_ORG_ENAME)
            .useYn(UPDATED_USE_YN)
            .upOrgCd(UPDATED_UP_ORG_CD)
            .orgTel(UPDATED_ORG_TEL)
            .zipCd(UPDATED_ZIP_CD)
            .orderNo(UPDATED_ORDER_NO)
            .hierachyCode(UPDATED_HIERACHY_CODE)
            .hierachyName(UPDATED_HIERACHY_NAME)
            .seatLimit(UPDATED_SEAT_LIMIT)
            .seatLimitUnit(UPDATED_SEAT_LIMIT_UNIT);
        return org;
    }

    @BeforeEach
    public void initTest() {
        org = createEntity(em);
    }

    @Test
    @Transactional
    void createOrg() throws Exception {
        int databaseSizeBeforeCreate = orgRepository.findAll().size();
        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);
        restOrgMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeCreate + 1);
        Org testOrg = orgList.get(orgList.size() - 1);
        assertThat(testOrg.getOrgCd()).isEqualTo(DEFAULT_ORG_CD);
        assertThat(testOrg.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testOrg.getOrgEname()).isEqualTo(DEFAULT_ORG_ENAME);
        assertThat(testOrg.getUseYn()).isEqualTo(DEFAULT_USE_YN);
        assertThat(testOrg.getUpOrgCd()).isEqualTo(DEFAULT_UP_ORG_CD);
        assertThat(testOrg.getOrgTel()).isEqualTo(DEFAULT_ORG_TEL);
        assertThat(testOrg.getZipCd()).isEqualTo(DEFAULT_ZIP_CD);
        assertThat(testOrg.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testOrg.getHierachyCode()).isEqualTo(DEFAULT_HIERACHY_CODE);
        assertThat(testOrg.getHierachyName()).isEqualTo(DEFAULT_HIERACHY_NAME);
        assertThat(testOrg.getSeatLimit()).isEqualTo(DEFAULT_SEAT_LIMIT);
        assertThat(testOrg.getSeatLimitUnit()).isEqualTo(DEFAULT_SEAT_LIMIT_UNIT);
    }

    @Test
    @Transactional
    void createOrgWithExistingId() throws Exception {
        // Create the Org with an existing ID
        org.setId(1L);
        OrgDTO orgDTO = orgMapper.toDto(org);

        int databaseSizeBeforeCreate = orgRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrgs() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        // Get all the orgList
        restOrgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(org.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgCd").value(hasItem(DEFAULT_ORG_CD)))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].orgEname").value(hasItem(DEFAULT_ORG_ENAME)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.booleanValue())))
            .andExpect(jsonPath("$.[*].upOrgCd").value(hasItem(DEFAULT_UP_ORG_CD)))
            .andExpect(jsonPath("$.[*].orgTel").value(hasItem(DEFAULT_ORG_TEL)))
            .andExpect(jsonPath("$.[*].zipCd").value(hasItem(DEFAULT_ZIP_CD)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.doubleValue())))
            .andExpect(jsonPath("$.[*].hierachyCode").value(hasItem(DEFAULT_HIERACHY_CODE)))
            .andExpect(jsonPath("$.[*].hierachyName").value(hasItem(DEFAULT_HIERACHY_NAME)))
            .andExpect(jsonPath("$.[*].seatLimit").value(hasItem(DEFAULT_SEAT_LIMIT)))
            .andExpect(jsonPath("$.[*].seatLimitUnit").value(hasItem(DEFAULT_SEAT_LIMIT_UNIT.toString())));
    }

    @Test
    @Transactional
    void getOrg() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        // Get the org
        restOrgMockMvc
            .perform(get(ENTITY_API_URL_ID, org.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(org.getId().intValue()))
            .andExpect(jsonPath("$.orgCd").value(DEFAULT_ORG_CD))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME))
            .andExpect(jsonPath("$.orgEname").value(DEFAULT_ORG_ENAME))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.booleanValue()))
            .andExpect(jsonPath("$.upOrgCd").value(DEFAULT_UP_ORG_CD))
            .andExpect(jsonPath("$.orgTel").value(DEFAULT_ORG_TEL))
            .andExpect(jsonPath("$.zipCd").value(DEFAULT_ZIP_CD))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.doubleValue()))
            .andExpect(jsonPath("$.hierachyCode").value(DEFAULT_HIERACHY_CODE))
            .andExpect(jsonPath("$.hierachyName").value(DEFAULT_HIERACHY_NAME))
            .andExpect(jsonPath("$.seatLimit").value(DEFAULT_SEAT_LIMIT))
            .andExpect(jsonPath("$.seatLimitUnit").value(DEFAULT_SEAT_LIMIT_UNIT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrg() throws Exception {
        // Get the org
        restOrgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrg() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        int databaseSizeBeforeUpdate = orgRepository.findAll().size();

        // Update the org
        Org updatedOrg = orgRepository.findById(org.getId()).get();
        // Disconnect from session so that the updates on updatedOrg are not directly saved in db
        em.detach(updatedOrg);
        updatedOrg
            .orgCd(UPDATED_ORG_CD)
            .orgName(UPDATED_ORG_NAME)
            .orgEname(UPDATED_ORG_ENAME)
            .useYn(UPDATED_USE_YN)
            .upOrgCd(UPDATED_UP_ORG_CD)
            .orgTel(UPDATED_ORG_TEL)
            .zipCd(UPDATED_ZIP_CD)
            .orderNo(UPDATED_ORDER_NO)
            .hierachyCode(UPDATED_HIERACHY_CODE)
            .hierachyName(UPDATED_HIERACHY_NAME)
            .seatLimit(UPDATED_SEAT_LIMIT)
            .seatLimitUnit(UPDATED_SEAT_LIMIT_UNIT);
        OrgDTO orgDTO = orgMapper.toDto(updatedOrg);

        restOrgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isOk());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
        Org testOrg = orgList.get(orgList.size() - 1);
        assertThat(testOrg.getOrgCd()).isEqualTo(UPDATED_ORG_CD);
        assertThat(testOrg.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrg.getOrgEname()).isEqualTo(UPDATED_ORG_ENAME);
        assertThat(testOrg.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testOrg.getUpOrgCd()).isEqualTo(UPDATED_UP_ORG_CD);
        assertThat(testOrg.getOrgTel()).isEqualTo(UPDATED_ORG_TEL);
        assertThat(testOrg.getZipCd()).isEqualTo(UPDATED_ZIP_CD);
        assertThat(testOrg.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testOrg.getHierachyCode()).isEqualTo(UPDATED_HIERACHY_CODE);
        assertThat(testOrg.getHierachyName()).isEqualTo(UPDATED_HIERACHY_NAME);
        assertThat(testOrg.getSeatLimit()).isEqualTo(UPDATED_SEAT_LIMIT);
        assertThat(testOrg.getSeatLimitUnit()).isEqualTo(UPDATED_SEAT_LIMIT_UNIT);
    }

    @Test
    @Transactional
    void putNonExistingOrg() throws Exception {
        int databaseSizeBeforeUpdate = orgRepository.findAll().size();
        org.setId(count.incrementAndGet());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrg() throws Exception {
        int databaseSizeBeforeUpdate = orgRepository.findAll().size();
        org.setId(count.incrementAndGet());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrg() throws Exception {
        int databaseSizeBeforeUpdate = orgRepository.findAll().size();
        org.setId(count.incrementAndGet());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrgWithPatch() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        int databaseSizeBeforeUpdate = orgRepository.findAll().size();

        // Update the org using partial update
        Org partialUpdatedOrg = new Org();
        partialUpdatedOrg.setId(org.getId());

        partialUpdatedOrg.orgCd(UPDATED_ORG_CD).orgEname(UPDATED_ORG_ENAME).useYn(UPDATED_USE_YN).hierachyCode(UPDATED_HIERACHY_CODE);

        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrg.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrg))
            )
            .andExpect(status().isOk());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
        Org testOrg = orgList.get(orgList.size() - 1);
        assertThat(testOrg.getOrgCd()).isEqualTo(UPDATED_ORG_CD);
        assertThat(testOrg.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testOrg.getOrgEname()).isEqualTo(UPDATED_ORG_ENAME);
        assertThat(testOrg.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testOrg.getUpOrgCd()).isEqualTo(DEFAULT_UP_ORG_CD);
        assertThat(testOrg.getOrgTel()).isEqualTo(DEFAULT_ORG_TEL);
        assertThat(testOrg.getZipCd()).isEqualTo(DEFAULT_ZIP_CD);
        assertThat(testOrg.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testOrg.getHierachyCode()).isEqualTo(UPDATED_HIERACHY_CODE);
        assertThat(testOrg.getHierachyName()).isEqualTo(DEFAULT_HIERACHY_NAME);
        assertThat(testOrg.getSeatLimit()).isEqualTo(DEFAULT_SEAT_LIMIT);
        assertThat(testOrg.getSeatLimitUnit()).isEqualTo(DEFAULT_SEAT_LIMIT_UNIT);
    }

    @Test
    @Transactional
    void fullUpdateOrgWithPatch() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        int databaseSizeBeforeUpdate = orgRepository.findAll().size();

        // Update the org using partial update
        Org partialUpdatedOrg = new Org();
        partialUpdatedOrg.setId(org.getId());

        partialUpdatedOrg
            .orgCd(UPDATED_ORG_CD)
            .orgName(UPDATED_ORG_NAME)
            .orgEname(UPDATED_ORG_ENAME)
            .useYn(UPDATED_USE_YN)
            .upOrgCd(UPDATED_UP_ORG_CD)
            .orgTel(UPDATED_ORG_TEL)
            .zipCd(UPDATED_ZIP_CD)
            .orderNo(UPDATED_ORDER_NO)
            .hierachyCode(UPDATED_HIERACHY_CODE)
            .hierachyName(UPDATED_HIERACHY_NAME)
            .seatLimit(UPDATED_SEAT_LIMIT)
            .seatLimitUnit(UPDATED_SEAT_LIMIT_UNIT);

        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrg.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrg))
            )
            .andExpect(status().isOk());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
        Org testOrg = orgList.get(orgList.size() - 1);
        assertThat(testOrg.getOrgCd()).isEqualTo(UPDATED_ORG_CD);
        assertThat(testOrg.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrg.getOrgEname()).isEqualTo(UPDATED_ORG_ENAME);
        assertThat(testOrg.getUseYn()).isEqualTo(UPDATED_USE_YN);
        assertThat(testOrg.getUpOrgCd()).isEqualTo(UPDATED_UP_ORG_CD);
        assertThat(testOrg.getOrgTel()).isEqualTo(UPDATED_ORG_TEL);
        assertThat(testOrg.getZipCd()).isEqualTo(UPDATED_ZIP_CD);
        assertThat(testOrg.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testOrg.getHierachyCode()).isEqualTo(UPDATED_HIERACHY_CODE);
        assertThat(testOrg.getHierachyName()).isEqualTo(UPDATED_HIERACHY_NAME);
        assertThat(testOrg.getSeatLimit()).isEqualTo(UPDATED_SEAT_LIMIT);
        assertThat(testOrg.getSeatLimitUnit()).isEqualTo(UPDATED_SEAT_LIMIT_UNIT);
    }

    @Test
    @Transactional
    void patchNonExistingOrg() throws Exception {
        int databaseSizeBeforeUpdate = orgRepository.findAll().size();
        org.setId(count.incrementAndGet());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrg() throws Exception {
        int databaseSizeBeforeUpdate = orgRepository.findAll().size();
        org.setId(count.incrementAndGet());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrg() throws Exception {
        int databaseSizeBeforeUpdate = orgRepository.findAll().size();
        org.setId(count.incrementAndGet());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrg() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        int databaseSizeBeforeDelete = orgRepository.findAll().size();

        // Delete the org
        restOrgMockMvc
            .perform(delete(ENTITY_API_URL_ID, org.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
