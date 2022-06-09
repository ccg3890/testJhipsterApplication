package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.ReservationTarget;
import com.atensys.repository.ReservationTargetRepository;
import com.atensys.service.dto.ReservationTargetDTO;
import com.atensys.service.mapper.ReservationTargetMapper;
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
 * Integration tests for the {@link ReservationTargetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservationTargetResourceIT {

    private static final String ENTITY_API_URL = "/api/reservation-targets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReservationTargetRepository reservationTargetRepository;

    @Autowired
    private ReservationTargetMapper reservationTargetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationTargetMockMvc;

    private ReservationTarget reservationTarget;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTarget createEntity(EntityManager em) {
        ReservationTarget reservationTarget = new ReservationTarget();
        return reservationTarget;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTarget createUpdatedEntity(EntityManager em) {
        ReservationTarget reservationTarget = new ReservationTarget();
        return reservationTarget;
    }

    @BeforeEach
    public void initTest() {
        reservationTarget = createEntity(em);
    }

    @Test
    @Transactional
    void createReservationTarget() throws Exception {
        int databaseSizeBeforeCreate = reservationTargetRepository.findAll().size();
        // Create the ReservationTarget
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);
        restReservationTargetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeCreate + 1);
        ReservationTarget testReservationTarget = reservationTargetList.get(reservationTargetList.size() - 1);
    }

    @Test
    @Transactional
    void createReservationTargetWithExistingId() throws Exception {
        // Create the ReservationTarget with an existing ID
        reservationTarget.setId(1L);
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);

        int databaseSizeBeforeCreate = reservationTargetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationTargetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReservationTargets() throws Exception {
        // Initialize the database
        reservationTargetRepository.saveAndFlush(reservationTarget);

        // Get all the reservationTargetList
        restReservationTargetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservationTarget.getId().intValue())));
    }

    @Test
    @Transactional
    void getReservationTarget() throws Exception {
        // Initialize the database
        reservationTargetRepository.saveAndFlush(reservationTarget);

        // Get the reservationTarget
        restReservationTargetMockMvc
            .perform(get(ENTITY_API_URL_ID, reservationTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservationTarget.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingReservationTarget() throws Exception {
        // Get the reservationTarget
        restReservationTargetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReservationTarget() throws Exception {
        // Initialize the database
        reservationTargetRepository.saveAndFlush(reservationTarget);

        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();

        // Update the reservationTarget
        ReservationTarget updatedReservationTarget = reservationTargetRepository.findById(reservationTarget.getId()).get();
        // Disconnect from session so that the updates on updatedReservationTarget are not directly saved in db
        em.detach(updatedReservationTarget);
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(updatedReservationTarget);

        restReservationTargetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationTargetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
        ReservationTarget testReservationTarget = reservationTargetList.get(reservationTargetList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingReservationTarget() throws Exception {
        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();
        reservationTarget.setId(count.incrementAndGet());

        // Create the ReservationTarget
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationTargetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationTargetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservationTarget() throws Exception {
        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();
        reservationTarget.setId(count.incrementAndGet());

        // Create the ReservationTarget
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTargetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservationTarget() throws Exception {
        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();
        reservationTarget.setId(count.incrementAndGet());

        // Create the ReservationTarget
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTargetMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservationTargetWithPatch() throws Exception {
        // Initialize the database
        reservationTargetRepository.saveAndFlush(reservationTarget);

        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();

        // Update the reservationTarget using partial update
        ReservationTarget partialUpdatedReservationTarget = new ReservationTarget();
        partialUpdatedReservationTarget.setId(reservationTarget.getId());

        restReservationTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservationTarget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservationTarget))
            )
            .andExpect(status().isOk());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
        ReservationTarget testReservationTarget = reservationTargetList.get(reservationTargetList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateReservationTargetWithPatch() throws Exception {
        // Initialize the database
        reservationTargetRepository.saveAndFlush(reservationTarget);

        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();

        // Update the reservationTarget using partial update
        ReservationTarget partialUpdatedReservationTarget = new ReservationTarget();
        partialUpdatedReservationTarget.setId(reservationTarget.getId());

        restReservationTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservationTarget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservationTarget))
            )
            .andExpect(status().isOk());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
        ReservationTarget testReservationTarget = reservationTargetList.get(reservationTargetList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingReservationTarget() throws Exception {
        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();
        reservationTarget.setId(count.incrementAndGet());

        // Create the ReservationTarget
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservationTargetDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservationTarget() throws Exception {
        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();
        reservationTarget.setId(count.incrementAndGet());

        // Create the ReservationTarget
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservationTarget() throws Exception {
        int databaseSizeBeforeUpdate = reservationTargetRepository.findAll().size();
        reservationTarget.setId(count.incrementAndGet());

        // Create the ReservationTarget
        ReservationTargetDTO reservationTargetDTO = reservationTargetMapper.toDto(reservationTarget);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTargetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationTargetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservationTarget in the database
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservationTarget() throws Exception {
        // Initialize the database
        reservationTargetRepository.saveAndFlush(reservationTarget);

        int databaseSizeBeforeDelete = reservationTargetRepository.findAll().size();

        // Delete the reservationTarget
        restReservationTargetMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservationTarget.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservationTarget> reservationTargetList = reservationTargetRepository.findAll();
        assertThat(reservationTargetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
