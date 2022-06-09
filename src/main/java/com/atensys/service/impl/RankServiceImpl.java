package com.atensys.service.impl;

import com.atensys.domain.Rank;
import com.atensys.repository.RankRepository;
import com.atensys.service.RankService;
import com.atensys.service.dto.RankDTO;
import com.atensys.service.mapper.RankMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rank}.
 */
@Service
@Transactional
public class RankServiceImpl implements RankService {

    private final Logger log = LoggerFactory.getLogger(RankServiceImpl.class);

    private final RankRepository rankRepository;

    private final RankMapper rankMapper;

    public RankServiceImpl(RankRepository rankRepository, RankMapper rankMapper) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
    }

    @Override
    public RankDTO save(RankDTO rankDTO) {
        log.debug("Request to save Rank : {}", rankDTO);
        Rank rank = rankMapper.toEntity(rankDTO);
        rank = rankRepository.save(rank);
        return rankMapper.toDto(rank);
    }

    @Override
    public RankDTO update(RankDTO rankDTO) {
        log.debug("Request to save Rank : {}", rankDTO);
        Rank rank = rankMapper.toEntity(rankDTO);
        rank = rankRepository.save(rank);
        return rankMapper.toDto(rank);
    }

    @Override
    public Optional<RankDTO> partialUpdate(RankDTO rankDTO) {
        log.debug("Request to partially update Rank : {}", rankDTO);

        return rankRepository
            .findById(rankDTO.getId())
            .map(existingRank -> {
                rankMapper.partialUpdate(existingRank, rankDTO);

                return existingRank;
            })
            .map(rankRepository::save)
            .map(rankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RankDTO> findAll() {
        log.debug("Request to get all Ranks");
        return rankRepository.findAll().stream().map(rankMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RankDTO> findOne(Long id) {
        log.debug("Request to get Rank : {}", id);
        return rankRepository.findById(id).map(rankMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rank : {}", id);
        rankRepository.deleteById(id);
    }
}
