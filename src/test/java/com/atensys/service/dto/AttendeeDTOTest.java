package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttendeeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendeeDTO.class);
        AttendeeDTO attendeeDTO1 = new AttendeeDTO();
        attendeeDTO1.setId(1L);
        AttendeeDTO attendeeDTO2 = new AttendeeDTO();
        assertThat(attendeeDTO1).isNotEqualTo(attendeeDTO2);
        attendeeDTO2.setId(attendeeDTO1.getId());
        assertThat(attendeeDTO1).isEqualTo(attendeeDTO2);
        attendeeDTO2.setId(2L);
        assertThat(attendeeDTO1).isNotEqualTo(attendeeDTO2);
        attendeeDTO1.setId(null);
        assertThat(attendeeDTO1).isNotEqualTo(attendeeDTO2);
    }
}
