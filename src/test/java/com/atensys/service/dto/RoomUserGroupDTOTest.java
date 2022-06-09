package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomUserGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomUserGroupDTO.class);
        RoomUserGroupDTO roomUserGroupDTO1 = new RoomUserGroupDTO();
        roomUserGroupDTO1.setId(1L);
        RoomUserGroupDTO roomUserGroupDTO2 = new RoomUserGroupDTO();
        assertThat(roomUserGroupDTO1).isNotEqualTo(roomUserGroupDTO2);
        roomUserGroupDTO2.setId(roomUserGroupDTO1.getId());
        assertThat(roomUserGroupDTO1).isEqualTo(roomUserGroupDTO2);
        roomUserGroupDTO2.setId(2L);
        assertThat(roomUserGroupDTO1).isNotEqualTo(roomUserGroupDTO2);
        roomUserGroupDTO1.setId(null);
        assertThat(roomUserGroupDTO1).isNotEqualTo(roomUserGroupDTO2);
    }
}
