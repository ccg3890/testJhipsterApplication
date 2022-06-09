package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomManagerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomManagerDTO.class);
        RoomManagerDTO roomManagerDTO1 = new RoomManagerDTO();
        roomManagerDTO1.setId(1L);
        RoomManagerDTO roomManagerDTO2 = new RoomManagerDTO();
        assertThat(roomManagerDTO1).isNotEqualTo(roomManagerDTO2);
        roomManagerDTO2.setId(roomManagerDTO1.getId());
        assertThat(roomManagerDTO1).isEqualTo(roomManagerDTO2);
        roomManagerDTO2.setId(2L);
        assertThat(roomManagerDTO1).isNotEqualTo(roomManagerDTO2);
        roomManagerDTO1.setId(null);
        assertThat(roomManagerDTO1).isNotEqualTo(roomManagerDTO2);
    }
}
