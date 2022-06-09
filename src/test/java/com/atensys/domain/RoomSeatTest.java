package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomSeatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomSeat.class);
        RoomSeat roomSeat1 = new RoomSeat();
        roomSeat1.setId(1L);
        RoomSeat roomSeat2 = new RoomSeat();
        roomSeat2.setId(roomSeat1.getId());
        assertThat(roomSeat1).isEqualTo(roomSeat2);
        roomSeat2.setId(2L);
        assertThat(roomSeat1).isNotEqualTo(roomSeat2);
        roomSeat1.setId(null);
        assertThat(roomSeat1).isNotEqualTo(roomSeat2);
    }
}
