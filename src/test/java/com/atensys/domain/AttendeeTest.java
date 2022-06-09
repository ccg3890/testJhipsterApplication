package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttendeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendee.class);
        Attendee attendee1 = new Attendee();
        attendee1.setId(1L);
        Attendee attendee2 = new Attendee();
        attendee2.setId(attendee1.getId());
        assertThat(attendee1).isEqualTo(attendee2);
        attendee2.setId(2L);
        assertThat(attendee1).isNotEqualTo(attendee2);
        attendee1.setId(null);
        assertThat(attendee1).isNotEqualTo(attendee2);
    }
}
