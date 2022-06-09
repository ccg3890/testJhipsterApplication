package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservationTargetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationTargetDTO.class);
        ReservationTargetDTO reservationTargetDTO1 = new ReservationTargetDTO();
        reservationTargetDTO1.setId(1L);
        ReservationTargetDTO reservationTargetDTO2 = new ReservationTargetDTO();
        assertThat(reservationTargetDTO1).isNotEqualTo(reservationTargetDTO2);
        reservationTargetDTO2.setId(reservationTargetDTO1.getId());
        assertThat(reservationTargetDTO1).isEqualTo(reservationTargetDTO2);
        reservationTargetDTO2.setId(2L);
        assertThat(reservationTargetDTO1).isNotEqualTo(reservationTargetDTO2);
        reservationTargetDTO1.setId(null);
        assertThat(reservationTargetDTO1).isNotEqualTo(reservationTargetDTO2);
    }
}
