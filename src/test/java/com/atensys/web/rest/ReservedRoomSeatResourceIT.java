package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.ReservedRoomSeat;
import com.atensys.repository.ReservedRoomSeatRepository;
import com.atensys.service.dto.ReservedRoomSeatDTO;
import com.atensys.service.mapper.ReservedRoomSeatMapper;
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
 * Integration tests for the {@link ReservedRoomSeatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservedRoomSeatResourceIT {

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reserved-room-seats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReservedRoomSeatRepository reservedRoomSeatRepository;

    @Autowired
    private ReservedRoomSeatMapper reservedRoomSeatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservedRoomSeatMockMvc;

    private ReservedRoomSeat reservedRoomSeat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservedRoomSeat createEntity(EntityManager em) {
        ReservedRoomSeat reservedRoomSeat = new ReservedRoomSeat().displayName(DEFAULT_DISPLAY_NAME).companyName(DEFAULT_COMPANY_NAME);
        return reservedRoomSeat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservedRoomSeat createUpdatedEntity(EntityManager em) {
        ReservedRoomSeat reservedRoomSeat = new ReservedRoomSeat().displayName(UPDATED_DISPLAY_NAME).companyName(UPDATED_COMPANY_NAME);
        return reservedRoomSeat;
    }

    @BeforeEach
    public void initTest() {
        reservedRoomSeat = createEntity(em);
    }

    @Test
    @Transactional
    void createReservedRoomSeat() throws Exception {
        int databaseSizeBeforeCreate = reservedRoomSeatRepository.findAll().size();
        // Create the ReservedRoomSeat
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);
        restReservedRoomSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeCreate + 1);
        ReservedRoomSeat testReservedRoomSeat = reservedRoomSeatList.get(reservedRoomSeatList.size() - 1);
        assertThat(testReservedRoomSeat.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testReservedRoomSeat.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
    }

    @Test
    @Transactional
    void createReservedRoomSeatWithExistingId() throws Exception {
        // Create the ReservedRoomSeat with an existing ID
        reservedRoomSeat.setId(1L);
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        int databaseSizeBeforeCreate = reservedRoomSeatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservedRoomSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservedRoomSeatRepository.findAll().size();
        // set the field null
        reservedRoomSeat.setDisplayName(null);

        // Create the ReservedRoomSeat, which fails.
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        restReservedRoomSeatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReservedRoomSeats() throws Exception {
        // Initialize the database
        reservedRoomSeatRepository.saveAndFlush(reservedRoomSeat);

        // Get all the reservedRoomSeatList
        restReservedRoomSeatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservedRoomSeat.getId().intValue())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)));
    }

    @Test
    @Transactional
    void getReservedRoomSeat() throws Exception {
        // Initialize the database
        reservedRoomSeatRepository.saveAndFlush(reservedRoomSeat);

        // Get the reservedRoomSeat
        restReservedRoomSeatMockMvc
            .perform(get(ENTITY_API_URL_ID, reservedRoomSeat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservedRoomSeat.getId().intValue()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME));
    }

    @Test
    @Transactional
    void getNonExistingReservedRoomSeat() throws Exception {
        // Get the reservedRoomSeat
        restReservedRoomSeatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReservedRoomSeat() throws Exception {
        // Initialize the database
        reservedRoomSeatRepository.saveAndFlush(reservedRoomSeat);

        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();

        // Update the reservedRoomSeat
        ReservedRoomSeat updatedReservedRoomSeat = reservedRoomSeatRepository.findById(reservedRoomSeat.getId()).get();
        // Disconnect from session so that the updates on updatedReservedRoomSeat are not directly saved in db
        em.detach(updatedReservedRoomSeat);
        updatedReservedRoomSeat.displayName(UPDATED_DISPLAY_NAME).companyName(UPDATED_COMPANY_NAME);
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(updatedReservedRoomSeat);

        restReservedRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservedRoomSeatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
        ReservedRoomSeat testReservedRoomSeat = reservedRoomSeatList.get(reservedRoomSeatList.size() - 1);
        assertThat(testReservedRoomSeat.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testReservedRoomSeat.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void putNonExistingReservedRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();
        reservedRoomSeat.setId(count.incrementAndGet());

        // Create the ReservedRoomSeat
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservedRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservedRoomSeatDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservedRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();
        reservedRoomSeat.setId(count.incrementAndGet());

        // Create the ReservedRoomSeat
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservedRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();
        reservedRoomSeat.setId(count.incrementAndGet());

        // Create the ReservedRoomSeat
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedRoomSeatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservedRoomSeatWithPatch() throws Exception {
        // Initialize the database
        reservedRoomSeatRepository.saveAndFlush(reservedRoomSeat);

        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();

        // Update the reservedRoomSeat using partial update
        ReservedRoomSeat partialUpdatedReservedRoomSeat = new ReservedRoomSeat();
        partialUpdatedReservedRoomSeat.setId(reservedRoomSeat.getId());

        partialUpdatedReservedRoomSeat.companyName(UPDATED_COMPANY_NAME);

        restReservedRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservedRoomSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservedRoomSeat))
            )
            .andExpect(status().isOk());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
        ReservedRoomSeat testReservedRoomSeat = reservedRoomSeatList.get(reservedRoomSeatList.size() - 1);
        assertThat(testReservedRoomSeat.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testReservedRoomSeat.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void fullUpdateReservedRoomSeatWithPatch() throws Exception {
        // Initialize the database
        reservedRoomSeatRepository.saveAndFlush(reservedRoomSeat);

        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();

        // Update the reservedRoomSeat using partial update
        ReservedRoomSeat partialUpdatedReservedRoomSeat = new ReservedRoomSeat();
        partialUpdatedReservedRoomSeat.setId(reservedRoomSeat.getId());

        partialUpdatedReservedRoomSeat.displayName(UPDATED_DISPLAY_NAME).companyName(UPDATED_COMPANY_NAME);

        restReservedRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservedRoomSeat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservedRoomSeat))
            )
            .andExpect(status().isOk());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
        ReservedRoomSeat testReservedRoomSeat = reservedRoomSeatList.get(reservedRoomSeatList.size() - 1);
        assertThat(testReservedRoomSeat.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testReservedRoomSeat.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingReservedRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();
        reservedRoomSeat.setId(count.incrementAndGet());

        // Create the ReservedRoomSeat
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservedRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservedRoomSeatDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservedRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();
        reservedRoomSeat.setId(count.incrementAndGet());

        // Create the ReservedRoomSeat
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservedRoomSeat() throws Exception {
        int databaseSizeBeforeUpdate = reservedRoomSeatRepository.findAll().size();
        reservedRoomSeat.setId(count.incrementAndGet());

        // Create the ReservedRoomSeat
        ReservedRoomSeatDTO reservedRoomSeatDTO = reservedRoomSeatMapper.toDto(reservedRoomSeat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservedRoomSeatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservedRoomSeatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservedRoomSeat in the database
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservedRoomSeat() throws Exception {
        // Initialize the database
        reservedRoomSeatRepository.saveAndFlush(reservedRoomSeat);

        int databaseSizeBeforeDelete = reservedRoomSeatRepository.findAll().size();

        // Delete the reservedRoomSeat
        restReservedRoomSeatMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservedRoomSeat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservedRoomSeat> reservedRoomSeatList = reservedRoomSeatRepository.findAll();
        assertThat(reservedRoomSeatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
