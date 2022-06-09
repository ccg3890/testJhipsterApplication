package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservationTargetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationTarget.class);
        ReservationTarget reservationTarget1 = new ReservationTarget();
        reservationTarget1.setId(1L);
        ReservationTarget reservationTarget2 = new ReservationTarget();
        reservationTarget2.setId(reservationTarget1.getId());
        assertThat(reservationTarget1).isEqualTo(reservationTarget2);
        reservationTarget2.setId(2L);
        assertThat(reservationTarget1).isNotEqualTo(reservationTarget2);
        reservationTarget1.setId(null);
        assertThat(reservationTarget1).isNotEqualTo(reservationTarget2);
    }
}
