package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoomManagerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomManager.class);
        RoomManager roomManager1 = new RoomManager();
        roomManager1.setId(1L);
        RoomManager roomManager2 = new RoomManager();
        roomManager2.setId(roomManager1.getId());
        assertThat(roomManager1).isEqualTo(roomManager2);
        roomManager2.setId(2L);
        assertThat(roomManager1).isNotEqualTo(roomManager2);
        roomManager1.setId(null);
        assertThat(roomManager1).isNotEqualTo(roomManager2);
    }
}
