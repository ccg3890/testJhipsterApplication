package com.atensys.service.impl;

import com.atensys.domain.Attendee;
import com.atensys.repository.AttendeeRepository;
import com.atensys.service.AttendeeService;
import com.atensys.service.dto.AttendeeDTO;
import com.atensys.service.mapper.AttendeeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Attendee}.
 */
@Service
@Transactional
public class AttendeeServiceImpl implements AttendeeService {

    private final Logger log = LoggerFactory.getLogger(AttendeeServiceImpl.class);

    private final AttendeeRepository attendeeRepository;

    private final AttendeeMapper attendeeMapper;

    public AttendeeServiceImpl(AttendeeRepository attendeeRepository, AttendeeMapper attendeeMapper) {
        this.attendeeRepository = attendeeRepository;
        this.attendeeMapper = attendeeMapper;
    }

    @Override
    public AttendeeDTO save(AttendeeDTO attendeeDTO) {
        log.debug("Request to save Attendee : {}", attendeeDTO);
        Attendee attendee = attendeeMapper.toEntity(attendeeDTO);
        attendee = attendeeRepository.save(attendee);
        return attendeeMapper.toDto(attendee);
    }

    @Override
    public AttendeeDTO update(AttendeeDTO attendeeDTO) {
        log.debug("Request to save Attendee : {}", attendeeDTO);
        Attendee attendee = attendeeMapper.toEntity(attendeeDTO);
        attendee = attendeeRepository.save(attendee);
        return attendeeMapper.toDto(attendee);
    }

    @Override
    public Optional<AttendeeDTO> partialUpdate(AttendeeDTO attendeeDTO) {
        log.debug("Request to partially update Attendee : {}", attendeeDTO);

        return attendeeRepository
            .findById(attendeeDTO.getId())
            .map(existingAttendee -> {
                attendeeMapper.partialUpdate(existingAttendee, attendeeDTO);

                return existingAttendee;
            })
            .map(attendeeRepository::save)
            .map(attendeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendeeDTO> findAll() {
        log.debug("Request to get all Attendees");
        return attendeeRepository.findAll().stream().map(attendeeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttendeeDTO> findOne(Long id) {
        log.debug("Request to get Attendee : {}", id);
        return attendeeRepository.findById(id).map(attendeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attendee : {}", id);
        attendeeRepository.deleteById(id);
    }
}
