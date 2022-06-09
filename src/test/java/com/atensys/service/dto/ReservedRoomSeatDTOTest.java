package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservedRoomSeatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservedRoomSeatDTO.class);
        ReservedRoomSeatDTO reservedRoomSeatDTO1 = new ReservedRoomSeatDTO();
        reservedRoomSeatDTO1.setId(1L);
        ReservedRoomSeatDTO reservedRoomSeatDTO2 = new ReservedRoomSeatDTO();
        assertThat(reservedRoomSeatDTO1).isNotEqualTo(reservedRoomSeatDTO2);
        reservedRoomSeatDTO2.setId(reservedRoomSeatDTO1.getId());
        assertThat(reservedRoomSeatDTO1).isEqualTo(reservedRoomSeatDTO2);
        reservedRoomSeatDTO2.setId(2L);
        assertThat(reservedRoomSeatDTO1).isNotEqualTo(reservedRoomSeatDTO2);
        reservedRoomSeatDTO1.setId(null);
        assertThat(reservedRoomSeatDTO1).isNotEqualTo(reservedRoomSeatDTO2);
    }
}
