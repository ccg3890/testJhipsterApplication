package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservedDateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservedDateDTO.class);
        ReservedDateDTO reservedDateDTO1 = new ReservedDateDTO();
        reservedDateDTO1.setId(1L);
        ReservedDateDTO reservedDateDTO2 = new ReservedDateDTO();
        assertThat(reservedDateDTO1).isNotEqualTo(reservedDateDTO2);
        reservedDateDTO2.setId(reservedDateDTO1.getId());
        assertThat(reservedDateDTO1).isEqualTo(reservedDateDTO2);
        reservedDateDTO2.setId(2L);
        assertThat(reservedDateDTO1).isNotEqualTo(reservedDateDTO2);
        reservedDateDTO1.setId(null);
        assertThat(reservedDateDTO1).isNotEqualTo(reservedDateDTO2);
    }
}
