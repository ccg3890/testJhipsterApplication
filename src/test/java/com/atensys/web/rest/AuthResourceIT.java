package com.atensys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atensys.IntegrationTest;
import com.atensys.domain.Auth;
import com.atensys.repository.AuthRepository;
import com.atensys.service.AuthService;
import com.atensys.service.dto.AuthDTO;
import com.atensys.service.mapper.AuthMapper;
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
 * Integration tests for the {@link AuthResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AuthResourceIT {

    private static final String DEFAULT_AUTH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_AUTH = false;
    private static final Boolean UPDATED_DEFAULT_AUTH = true;

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String ENTITY_API_URL = "/api/auths";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthRepository authRepository;

    @Mock
    private AuthRepository authRepositoryMock;

    @Autowired
    private AuthMapper authMapper;

    @Mock
    private AuthService authServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthMockMvc;

    private Auth auth;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auth createEntity(EntityManager em) {
        Auth auth = new Auth().authName(DEFAULT_AUTH_NAME).defaultAuth(DEFAULT_DEFAULT_AUTH).valid(DEFAULT_VALID);
        return auth;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auth createUpdatedEntity(EntityManager em) {
        Auth auth = new Auth().authName(UPDATED_AUTH_NAME).defaultAuth(UPDATED_DEFAULT_AUTH).valid(UPDATED_VALID);
        return auth;
    }

    @BeforeEach
    public void initTest() {
        auth = createEntity(em);
    }

    @Test
    @Transactional
    void createAuth() throws Exception {
        int databaseSizeBeforeCreate = authRepository.findAll().size();
        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);
        restAuthMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeCreate + 1);
        Auth testAuth = authList.get(authList.size() - 1);
        assertThat(testAuth.getAuthName()).isEqualTo(DEFAULT_AUTH_NAME);
        assertThat(testAuth.getDefaultAuth()).isEqualTo(DEFAULT_DEFAULT_AUTH);
        assertThat(testAuth.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    void createAuthWithExistingId() throws Exception {
        // Create the Auth with an existing ID
        auth.setId(1L);
        AuthDTO authDTO = authMapper.toDto(auth);

        int databaseSizeBeforeCreate = authRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAuthNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = authRepository.findAll().size();
        // set the field null
        auth.setAuthName(null);

        // Create the Auth, which fails.
        AuthDTO authDTO = authMapper.toDto(auth);

        restAuthMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isBadRequest());

        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = authRepository.findAll().size();
        // set the field null
        auth.setValid(null);

        // Create the Auth, which fails.
        AuthDTO authDTO = authMapper.toDto(auth);

        restAuthMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isBadRequest());

        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuths() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get all the authList
        restAuthMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auth.getId().intValue())))
            .andExpect(jsonPath("$.[*].authName").value(hasItem(DEFAULT_AUTH_NAME)))
            .andExpect(jsonPath("$.[*].defaultAuth").value(hasItem(DEFAULT_DEFAULT_AUTH.booleanValue())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAuthsWithEagerRelationshipsIsEnabled() throws Exception {
        when(authServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAuthMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(authServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAuthsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(authServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAuthMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(authServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getAuth() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        // Get the auth
        restAuthMockMvc
            .perform(get(ENTITY_API_URL_ID, auth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auth.getId().intValue()))
            .andExpect(jsonPath("$.authName").value(DEFAULT_AUTH_NAME))
            .andExpect(jsonPath("$.defaultAuth").value(DEFAULT_DEFAULT_AUTH.booleanValue()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAuth() throws Exception {
        // Get the auth
        restAuthMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuth() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        int databaseSizeBeforeUpdate = authRepository.findAll().size();

        // Update the auth
        Auth updatedAuth = authRepository.findById(auth.getId()).get();
        // Disconnect from session so that the updates on updatedAuth are not directly saved in db
        em.detach(updatedAuth);
        updatedAuth.authName(UPDATED_AUTH_NAME).defaultAuth(UPDATED_DEFAULT_AUTH).valid(UPDATED_VALID);
        AuthDTO authDTO = authMapper.toDto(updatedAuth);

        restAuthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isOk());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
        Auth testAuth = authList.get(authList.size() - 1);
        assertThat(testAuth.getAuthName()).isEqualTo(UPDATED_AUTH_NAME);
        assertThat(testAuth.getDefaultAuth()).isEqualTo(UPDATED_DEFAULT_AUTH);
        assertThat(testAuth.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void putNonExistingAuth() throws Exception {
        int databaseSizeBeforeUpdate = authRepository.findAll().size();
        auth.setId(count.incrementAndGet());

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuth() throws Exception {
        int databaseSizeBeforeUpdate = authRepository.findAll().size();
        auth.setId(count.incrementAndGet());

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuth() throws Exception {
        int databaseSizeBeforeUpdate = authRepository.findAll().size();
        auth.setId(count.incrementAndGet());

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthWithPatch() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        int databaseSizeBeforeUpdate = authRepository.findAll().size();

        // Update the auth using partial update
        Auth partialUpdatedAuth = new Auth();
        partialUpdatedAuth.setId(auth.getId());

        partialUpdatedAuth.authName(UPDATED_AUTH_NAME).valid(UPDATED_VALID);

        restAuthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuth.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuth))
            )
            .andExpect(status().isOk());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
        Auth testAuth = authList.get(authList.size() - 1);
        assertThat(testAuth.getAuthName()).isEqualTo(UPDATED_AUTH_NAME);
        assertThat(testAuth.getDefaultAuth()).isEqualTo(DEFAULT_DEFAULT_AUTH);
        assertThat(testAuth.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void fullUpdateAuthWithPatch() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        int databaseSizeBeforeUpdate = authRepository.findAll().size();

        // Update the auth using partial update
        Auth partialUpdatedAuth = new Auth();
        partialUpdatedAuth.setId(auth.getId());

        partialUpdatedAuth.authName(UPDATED_AUTH_NAME).defaultAuth(UPDATED_DEFAULT_AUTH).valid(UPDATED_VALID);

        restAuthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuth.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuth))
            )
            .andExpect(status().isOk());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
        Auth testAuth = authList.get(authList.size() - 1);
        assertThat(testAuth.getAuthName()).isEqualTo(UPDATED_AUTH_NAME);
        assertThat(testAuth.getDefaultAuth()).isEqualTo(UPDATED_DEFAULT_AUTH);
        assertThat(testAuth.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    void patchNonExistingAuth() throws Exception {
        int databaseSizeBeforeUpdate = authRepository.findAll().size();
        auth.setId(count.incrementAndGet());

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuth() throws Exception {
        int databaseSizeBeforeUpdate = authRepository.findAll().size();
        auth.setId(count.incrementAndGet());

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuth() throws Exception {
        int databaseSizeBeforeUpdate = authRepository.findAll().size();
        auth.setId(count.incrementAndGet());

        // Create the Auth
        AuthDTO authDTO = authMapper.toDto(auth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auth in the database
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuth() throws Exception {
        // Initialize the database
        authRepository.saveAndFlush(auth);

        int databaseSizeBeforeDelete = authRepository.findAll().size();

        // Delete the auth
        restAuthMockMvc
            .perform(delete(ENTITY_API_URL_ID, auth.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auth> authList = authRepository.findAll();
        assertThat(authList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
