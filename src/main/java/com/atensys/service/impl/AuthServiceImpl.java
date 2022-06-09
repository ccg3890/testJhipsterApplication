package com.atensys.service.impl;

import com.atensys.domain.Auth;
import com.atensys.repository.AuthRepository;
import com.atensys.service.AuthService;
import com.atensys.service.dto.AuthDTO;
import com.atensys.service.mapper.AuthMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Auth}.
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthRepository authRepository;

    private final AuthMapper authMapper;

    public AuthServiceImpl(AuthRepository authRepository, AuthMapper authMapper) {
        this.authRepository = authRepository;
        this.authMapper = authMapper;
    }

    @Override
    public AuthDTO save(AuthDTO authDTO) {
        log.debug("Request to save Auth : {}", authDTO);
        Auth auth = authMapper.toEntity(authDTO);
        auth = authRepository.save(auth);
        return authMapper.toDto(auth);
    }

    @Override
    public AuthDTO update(AuthDTO authDTO) {
        log.debug("Request to save Auth : {}", authDTO);
        Auth auth = authMapper.toEntity(authDTO);
        auth = authRepository.save(auth);
        return authMapper.toDto(auth);
    }

    @Override
    public Optional<AuthDTO> partialUpdate(AuthDTO authDTO) {
        log.debug("Request to partially update Auth : {}", authDTO);

        return authRepository
            .findById(authDTO.getId())
            .map(existingAuth -> {
                authMapper.partialUpdate(existingAuth, authDTO);

                return existingAuth;
            })
            .map(authRepository::save)
            .map(authMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthDTO> findAll() {
        log.debug("Request to get all Auths");
        return authRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(authMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<AuthDTO> findAllWithEagerRelationships(Pageable pageable) {
        return authRepository.findAllWithEagerRelationships(pageable).map(authMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthDTO> findOne(Long id) {
        log.debug("Request to get Auth : {}", id);
        return authRepository.findOneWithEagerRelationships(id).map(authMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Auth : {}", id);
        authRepository.deleteById(id);
    }
}
