package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PenaltyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PenaltyDTO.class);
        PenaltyDTO penaltyDTO1 = new PenaltyDTO();
        penaltyDTO1.setId(1L);
        PenaltyDTO penaltyDTO2 = new PenaltyDTO();
        assertThat(penaltyDTO1).isNotEqualTo(penaltyDTO2);
        penaltyDTO2.setId(penaltyDTO1.getId());
        assertThat(penaltyDTO1).isEqualTo(penaltyDTO2);
        penaltyDTO2.setId(2L);
        assertThat(penaltyDTO1).isNotEqualTo(penaltyDTO2);
        penaltyDTO1.setId(null);
        assertThat(penaltyDTO1).isNotEqualTo(penaltyDTO2);
    }
}
