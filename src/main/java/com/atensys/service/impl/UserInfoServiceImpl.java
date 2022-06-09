package com.atensys.service.impl;

import com.atensys.domain.UserInfo;
import com.atensys.repository.UserInfoRepository;
import com.atensys.service.UserInfoService;
import com.atensys.service.dto.UserInfoDTO;
import com.atensys.service.mapper.UserInfoMapper;
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
 * Service Implementation for managing {@link UserInfo}.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo = userInfoRepository.save(userInfo);
        return userInfoMapper.toDto(userInfo);
    }

    @Override
    public UserInfoDTO update(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo = userInfoRepository.save(userInfo);
        return userInfoMapper.toDto(userInfo);
    }

    @Override
    public Optional<UserInfoDTO> partialUpdate(UserInfoDTO userInfoDTO) {
        log.debug("Request to partially update UserInfo : {}", userInfoDTO);

        return userInfoRepository
            .findById(userInfoDTO.getId())
            .map(existingUserInfo -> {
                userInfoMapper.partialUpdate(existingUserInfo, userInfoDTO);

                return existingUserInfo;
            })
            .map(userInfoRepository::save)
            .map(userInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInfoDTO> findAll() {
        log.debug("Request to get all UserInfos");
        return userInfoRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(userInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<UserInfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userInfoRepository.findAllWithEagerRelationships(pageable).map(userInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfoDTO> findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        return userInfoRepository.findOneWithEagerRelationships(id).map(userInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.deleteById(id);
    }
}
