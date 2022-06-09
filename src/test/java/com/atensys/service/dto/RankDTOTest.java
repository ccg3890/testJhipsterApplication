package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RankDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RankDTO.class);
        RankDTO rankDTO1 = new RankDTO();
        rankDTO1.setId(1L);
        RankDTO rankDTO2 = new RankDTO();
        assertThat(rankDTO1).isNotEqualTo(rankDTO2);
        rankDTO2.setId(rankDTO1.getId());
        assertThat(rankDTO1).isEqualTo(rankDTO2);
        rankDTO2.setId(2L);
        assertThat(rankDTO1).isNotEqualTo(rankDTO2);
        rankDTO1.setId(null);
        assertThat(rankDTO1).isNotEqualTo(rankDTO2);
    }
}
