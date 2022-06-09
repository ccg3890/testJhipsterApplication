package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rank.class);
        Rank rank1 = new Rank();
        rank1.setId(1L);
        Rank rank2 = new Rank();
        rank2.setId(rank1.getId());
        assertThat(rank1).isEqualTo(rank2);
        rank2.setId(2L);
        assertThat(rank1).isNotEqualTo(rank2);
        rank1.setId(null);
        assertThat(rank1).isNotEqualTo(rank2);
    }
}
