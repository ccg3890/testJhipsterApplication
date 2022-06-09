package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservedRoomSeatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservedRoomSeat.class);
        ReservedRoomSeat reservedRoomSeat1 = new ReservedRoomSeat();
        reservedRoomSeat1.setId(1L);
        ReservedRoomSeat reservedRoomSeat2 = new ReservedRoomSeat();
        reservedRoomSeat2.setId(reservedRoomSeat1.getId());
        assertThat(reservedRoomSeat1).isEqualTo(reservedRoomSeat2);
        reservedRoomSeat2.setId(2L);
        assertThat(reservedRoomSeat1).isNotEqualTo(reservedRoomSeat2);
        reservedRoomSeat1.setId(null);
        assertThat(reservedRoomSeat1).isNotEqualTo(reservedRoomSeat2);
    }
}
