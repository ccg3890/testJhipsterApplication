package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Rank;
import com.atensys.repository.RankRepository;
import com.atensys.service.dto.RankDTO;
import com.atensys.service.mapper.RankMapper;
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
 * Integration tests for the {@link RankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RankResourceIT {

    private static final String DEFAULT_RANK_CD = "AAAAAAAAAA";
    private static final String UPDATED_RANK_CD = "BBBBBBBBBB";

    private static final String DEFAULT_RANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RANK_ENAME = "AAAAAAAAAA";
    private static final String UPDATED_RANK_ENAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_YN = false;
    private static final Boolean UPDATED_USE_YN = true;

    private static final String ENTITY_API_URL = "/api/ranks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRankMockMvc;

    private Rank rank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rank createEntity(EntityManager em) {
        Rank rank = new Rank().rankCd(DEFAULT_RANK_CD).rankName(DEFAULT_RANK_NAME).rankEname(DEFAULT_RANK_ENAME).useYn(DEFAULT_USE_YN);
        return rank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rank createUpdatedEntity(EntityManager em) {
        Rank rank = new Rank().rankCd(UPDATED_RANK_CD).rankName(UPDATED_RANK_NAME).rankEname(UPDATED_RANK_ENAME).useYn(UPDATED_USE_YN);
        return rank;
    }

    @BeforeEach
    public void initTest() {
        rank = createEntity(em);
    }

    @Test
    @Transactional
    void createRank() throws Exception {
        int databaseSizeBeforeCreate = rankRepository.findAll().size();
        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);
        restRankMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeCreate + 1);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getRankCd()).isEqualTo(DEFAULT_RANK_CD);
        assertThat(testRank.getRankName()).isEqualTo(DEFAULT_RANK_NAME);
        assertThat(testRank.getRankEname()).isEqualTo(DEFAULT_RANK_ENAME);
        assertThat(testRank.getUseYn()).isEqualTo(DEFAULT_USE_YN);
    }

    @Test
    @Transactional
    void createRankWithExistingId() throws Exception {
        // Create the Rank with an existing ID
        rank.setId(1L);
        RankDTO rankDTO = rankMapper.toDto(rank);

        int databaseSizeBeforeCreate = rankRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRankMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRanks() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList
        restRankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rank.getId().intValue())))
            .andExpect(jsonPath("$.[*].rankCd").value(hasItem(DEFAULT_RANK_CD)))
            .andExpect(jsonPath("$.[*].rankName").value(hasItem(DEFAULT_RANK_NAME)))
            .andExpect(jsonPath("$.[*].rankEname").value(hasItem(DEFAULT_RANK_ENAME)))
            .andExpect(jsonPath("$.[*].useYn").value(hasItem(DEFAULT_USE_YN.booleanValue())));
    }

    @Test
    @Transactional
    void getRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get the rank
        restRankMockMvc
            .perform(get(ENTITY_API_URL_ID, rank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rank.getId().intValue()))
            .andExpect(jsonPath("$.rankCd").value(DEFAULT_RANK_CD))
            .andExpect(jsonPath("$.rankName").value(DEFAULT_RANK_NAME))
            .andExpect(jsonPath("$.rankEname").value(DEFAULT_RANK_ENAME))
            .andExpect(jsonPath("$.useYn").value(DEFAULT_USE_YN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRank() throws Exception {
        // Get the rank
        restRankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Update the rank
        Rank updatedRank = rankRepository.findById(rank.getId()).get();
        // Disconnect from session so that the updates on updatedRank are not directly saved in db
        em.detach(updatedRank);
        updatedRank.rankCd(UPDATED_RANK_CD).rankName(UPDATED_RANK_NAME).rankEname(UPDATED_RANK_ENAME).useYn(UPDATED_USE_YN);
        RankDTO rankDTO = rankMapper.toDto(updatedRank);

        restRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rankDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getRankCd()).isEqualTo(UPDATED_RANK_CD);
        assertThat(testRank.getRankName()).isEqualTo(UPDATED_RANK_NAME);
        assertThat(testRank.getRankEname()).isEqualTo(UPDATED_RANK_ENAME);
        assertThat(testRank.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void putNonExistingRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(count.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rankDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(count.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(count.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRankWithPatch() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Update the rank using partial update
        Rank partialUpdatedRank = new Rank();
        partialUpdatedRank.setId(rank.getId());

        partialUpdatedRank.rankName(UPDATED_RANK_NAME).rankEname(UPDATED_RANK_ENAME).useYn(UPDATED_USE_YN);

        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRank.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRank))
            )
            .andExpect(status().isOk());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getRankCd()).isEqualTo(DEFAULT_RANK_CD);
        assertThat(testRank.getRankName()).isEqualTo(UPDATED_RANK_NAME);
        assertThat(testRank.getRankEname()).isEqualTo(UPDATED_RANK_ENAME);
        assertThat(testRank.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void fullUpdateRankWithPatch() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Update the rank using partial update
        Rank partialUpdatedRank = new Rank();
        partialUpdatedRank.setId(rank.getId());

        partialUpdatedRank.rankCd(UPDATED_RANK_CD).rankName(UPDATED_RANK_NAME).rankEname(UPDATED_RANK_ENAME).useYn(UPDATED_USE_YN);

        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRank.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRank))
            )
            .andExpect(status().isOk());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getRankCd()).isEqualTo(UPDATED_RANK_CD);
        assertThat(testRank.getRankName()).isEqualTo(UPDATED_RANK_NAME);
        assertThat(testRank.getRankEname()).isEqualTo(UPDATED_RANK_ENAME);
        assertThat(testRank.getUseYn()).isEqualTo(UPDATED_USE_YN);
    }

    @Test
    @Transactional
    void patchNonExistingRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(count.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rankDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(count.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();
        rank.setId(count.incrementAndGet());

        // Create the Rank
        RankDTO rankDTO = rankMapper.toDto(rank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRankMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rankDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        int databaseSizeBeforeDelete = rankRepository.findAll().size();

        // Delete the rank
        restRankMockMvc
            .perform(delete(ENTITY_API_URL_ID, rank.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
