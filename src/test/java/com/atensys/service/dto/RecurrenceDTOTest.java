package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecurrenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecurrenceDTO.class);
        RecurrenceDTO recurrenceDTO1 = new RecurrenceDTO();
        recurrenceDTO1.setId(1L);
        RecurrenceDTO recurrenceDTO2 = new RecurrenceDTO();
        assertThat(recurrenceDTO1).isNotEqualTo(recurrenceDTO2);
        recurrenceDTO2.setId(recurrenceDTO1.getId());
        assertThat(recurrenceDTO1).isEqualTo(recurrenceDTO2);
        recurrenceDTO2.setId(2L);
        assertThat(recurrenceDTO1).isNotEqualTo(recurrenceDTO2);
        recurrenceDTO1.setId(null);
        assertThat(recurrenceDTO1).isNotEqualTo(recurrenceDTO2);
    }
}
