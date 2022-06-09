package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Attendee;
import com.atensys.domain.enumeration.AttendeeResponseStatus;
import com.atensys.domain.enumeration.AttendeeType;
import com.atensys.repository.AttendeeRepository;
import com.atensys.service.dto.AttendeeDTO;
import com.atensys.service.mapper.AttendeeMapper;
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
 * Integration tests for the {@link AttendeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttendeeResourceIT {

    private static final AttendeeType DEFAULT_ATTENDEE_TYPE = AttendeeType.INNER;
    private static final AttendeeType UPDATED_ATTENDEE_TYPE = AttendeeType.OUTTER;

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTENDEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "]aD@A6A).PtauL";
    private static final String UPDATED_EMAIL = "\"|WH\\@+K.4]I";

    private static final Boolean DEFAULT_OPTIONAL = false;
    private static final Boolean UPDATED_OPTIONAL = true;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final AttendeeResponseStatus DEFAULT_RESPONSE_STATUS = AttendeeResponseStatus.NEEDS_ACTION;
    private static final AttendeeResponseStatus UPDATED_RESPONSE_STATUS = AttendeeResponseStatus.DECLINED;

    private static final String ENTITY_API_URL = "/api/attendees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private AttendeeMapper attendeeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendeeMockMvc;

    private Attendee attendee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendee createEntity(EntityManager em) {
        Attendee attendee = new Attendee()
            .attendeeType(DEFAULT_ATTENDEE_TYPE)
            .displayName(DEFAULT_DISPLAY_NAME)
            .companyName(DEFAULT_COMPANY_NAME)
            .attendeeId(DEFAULT_ATTENDEE_ID)
            .mobileNo(DEFAULT_MOBILE_NO)
            .email(DEFAULT_EMAIL)
            .optional(DEFAULT_OPTIONAL)
            .comment(DEFAULT_COMMENT)
            .responseStatus(DEFAULT_RESPONSE_STATUS);
        return attendee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendee createUpdatedEntity(EntityManager em) {
        Attendee attendee = new Attendee()
            .attendeeType(UPDATED_ATTENDEE_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .attendeeId(UPDATED_ATTENDEE_ID)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .optional(UPDATED_OPTIONAL)
            .comment(UPDATED_COMMENT)
            .responseStatus(UPDATED_RESPONSE_STATUS);
        return attendee;
    }

    @BeforeEach
    public void initTest() {
        attendee = createEntity(em);
    }

    @Test
    @Transactional
    void createAttendee() throws Exception {
        int databaseSizeBeforeCreate = attendeeRepository.findAll().size();
        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);
        restAttendeeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeCreate + 1);
        Attendee testAttendee = attendeeList.get(attendeeList.size() - 1);
        assertThat(testAttendee.getAttendeeType()).isEqualTo(DEFAULT_ATTENDEE_TYPE);
        assertThat(testAttendee.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testAttendee.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testAttendee.getAttendeeId()).isEqualTo(DEFAULT_ATTENDEE_ID);
        assertThat(testAttendee.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testAttendee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAttendee.getOptional()).isEqualTo(DEFAULT_OPTIONAL);
        assertThat(testAttendee.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testAttendee.getResponseStatus()).isEqualTo(DEFAULT_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    void createAttendeeWithExistingId() throws Exception {
        // Create the Attendee with an existing ID
        attendee.setId(1L);
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        int databaseSizeBeforeCreate = attendeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendeeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttendeeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendeeRepository.findAll().size();
        // set the field null
        attendee.setAttendeeType(null);

        // Create the Attendee, which fails.
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        restAttendeeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendeeRepository.findAll().size();
        // set the field null
        attendee.setDisplayName(null);

        // Create the Attendee, which fails.
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        restAttendeeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendeeRepository.findAll().size();
        // set the field null
        attendee.setMobileNo(null);

        // Create the Attendee, which fails.
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        restAttendeeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttendees() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        // Get all the attendeeList
        restAttendeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendee.getId().intValue())))
            .andExpect(jsonPath("$.[*].attendeeType").value(hasItem(DEFAULT_ATTENDEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].attendeeId").value(hasItem(DEFAULT_ATTENDEE_ID)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].optional").value(hasItem(DEFAULT_OPTIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].responseStatus").value(hasItem(DEFAULT_RESPONSE_STATUS.toString())));
    }

    @Test
    @Transactional
    void getAttendee() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        // Get the attendee
        restAttendeeMockMvc
            .perform(get(ENTITY_API_URL_ID, attendee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendee.getId().intValue()))
            .andExpect(jsonPath("$.attendeeType").value(DEFAULT_ATTENDEE_TYPE.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.attendeeId").value(DEFAULT_ATTENDEE_ID))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.optional").value(DEFAULT_OPTIONAL.booleanValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.responseStatus").value(DEFAULT_RESPONSE_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAttendee() throws Exception {
        // Get the attendee
        restAttendeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttendee() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();

        // Update the attendee
        Attendee updatedAttendee = attendeeRepository.findById(attendee.getId()).get();
        // Disconnect from session so that the updates on updatedAttendee are not directly saved in db
        em.detach(updatedAttendee);
        updatedAttendee
            .attendeeType(UPDATED_ATTENDEE_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .attendeeId(UPDATED_ATTENDEE_ID)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .optional(UPDATED_OPTIONAL)
            .comment(UPDATED_COMMENT)
            .responseStatus(UPDATED_RESPONSE_STATUS);
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(updatedAttendee);

        restAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendeeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
        Attendee testAttendee = attendeeList.get(attendeeList.size() - 1);
        assertThat(testAttendee.getAttendeeType()).isEqualTo(UPDATED_ATTENDEE_TYPE);
        assertThat(testAttendee.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testAttendee.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testAttendee.getAttendeeId()).isEqualTo(UPDATED_ATTENDEE_ID);
        assertThat(testAttendee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testAttendee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAttendee.getOptional()).isEqualTo(UPDATED_OPTIONAL);
        assertThat(testAttendee.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testAttendee.getResponseStatus()).isEqualTo(UPDATED_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAttendee() throws Exception {
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();
        attendee.setId(count.incrementAndGet());

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendeeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttendee() throws Exception {
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();
        attendee.setId(count.incrementAndGet());

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttendee() throws Exception {
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();
        attendee.setId(count.incrementAndGet());

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttendeeWithPatch() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();

        // Update the attendee using partial update
        Attendee partialUpdatedAttendee = new Attendee();
        partialUpdatedAttendee.setId(attendee.getId());

        partialUpdatedAttendee
            .attendeeType(UPDATED_ATTENDEE_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .attendeeId(UPDATED_ATTENDEE_ID)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .optional(UPDATED_OPTIONAL)
            .responseStatus(UPDATED_RESPONSE_STATUS);

        restAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendee.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendee))
            )
            .andExpect(status().isOk());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
        Attendee testAttendee = attendeeList.get(attendeeList.size() - 1);
        assertThat(testAttendee.getAttendeeType()).isEqualTo(UPDATED_ATTENDEE_TYPE);
        assertThat(testAttendee.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testAttendee.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testAttendee.getAttendeeId()).isEqualTo(UPDATED_ATTENDEE_ID);
        assertThat(testAttendee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testAttendee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAttendee.getOptional()).isEqualTo(UPDATED_OPTIONAL);
        assertThat(testAttendee.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testAttendee.getResponseStatus()).isEqualTo(UPDATED_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAttendeeWithPatch() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();

        // Update the attendee using partial update
        Attendee partialUpdatedAttendee = new Attendee();
        partialUpdatedAttendee.setId(attendee.getId());

        partialUpdatedAttendee
            .attendeeType(UPDATED_ATTENDEE_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .attendeeId(UPDATED_ATTENDEE_ID)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .optional(UPDATED_OPTIONAL)
            .comment(UPDATED_COMMENT)
            .responseStatus(UPDATED_RESPONSE_STATUS);

        restAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendee.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendee))
            )
            .andExpect(status().isOk());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
        Attendee testAttendee = attendeeList.get(attendeeList.size() - 1);
        assertThat(testAttendee.getAttendeeType()).isEqualTo(UPDATED_ATTENDEE_TYPE);
        assertThat(testAttendee.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testAttendee.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testAttendee.getAttendeeId()).isEqualTo(UPDATED_ATTENDEE_ID);
        assertThat(testAttendee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testAttendee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAttendee.getOptional()).isEqualTo(UPDATED_OPTIONAL);
        assertThat(testAttendee.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testAttendee.getResponseStatus()).isEqualTo(UPDATED_RESPONSE_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAttendee() throws Exception {
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();
        attendee.setId(count.incrementAndGet());

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attendeeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttendee() throws Exception {
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();
        attendee.setId(count.incrementAndGet());

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttendee() throws Exception {
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();
        attendee.setId(count.incrementAndGet());

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttendee() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        int databaseSizeBeforeDelete = attendeeRepository.findAll().size();

        // Delete the attendee
        restAttendeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, attendee.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
