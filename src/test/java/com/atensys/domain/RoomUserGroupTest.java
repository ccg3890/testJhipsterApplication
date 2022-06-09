package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomUserGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomUserGroup.class);
        RoomUserGroup roomUserGroup1 = new RoomUserGroup();
        roomUserGroup1.setId(1L);
        RoomUserGroup roomUserGroup2 = new RoomUserGroup();
        roomUserGroup2.setId(roomUserGroup1.getId());
        assertThat(roomUserGroup1).isEqualTo(roomUserGroup2);
        roomUserGroup2.setId(2L);
        assertThat(roomUserGroup1).isNotEqualTo(roomUserGroup2);
        roomUserGroup1.setId(null);
        assertThat(roomUserGroup1).isNotEqualTo(roomUserGroup2);
    }
}
