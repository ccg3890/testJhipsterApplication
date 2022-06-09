package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationTargetMapperTest {

    private ReservationTargetMapper reservationTargetMapper;

    @BeforeEach
    public void setUp() {
        reservationTargetMapper = new ReservationTargetMapperImpl();
    }
}
