package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomSeatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomSeatDTO.class);
        RoomSeatDTO roomSeatDTO1 = new RoomSeatDTO();
        roomSeatDTO1.setId(1L);
        RoomSeatDTO roomSeatDTO2 = new RoomSeatDTO();
        assertThat(roomSeatDTO1).isNotEqualTo(roomSeatDTO2);
        roomSeatDTO2.setId(roomSeatDTO1.getId());
        assertThat(roomSeatDTO1).isEqualTo(roomSeatDTO2);
        roomSeatDTO2.setId(2L);
        assertThat(roomSeatDTO1).isNotEqualTo(roomSeatDTO2);
        roomSeatDTO1.setId(null);
        assertThat(roomSeatDTO1).isNotEqualTo(roomSeatDTO2);
    }
}
