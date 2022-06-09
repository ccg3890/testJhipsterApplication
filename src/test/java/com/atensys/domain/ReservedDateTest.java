package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservedDateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservedDate.class);
        ReservedDate reservedDate1 = new ReservedDate();
        reservedDate1.setId(1L);
        ReservedDate reservedDate2 = new ReservedDate();
        reservedDate2.setId(reservedDate1.getId());
        assertThat(reservedDate1).isEqualTo(reservedDate2);
        reservedDate2.setId(2L);
        assertThat(reservedDate1).isNotEqualTo(reservedDate2);
        reservedDate1.setId(null);
        assertThat(reservedDate1).isNotEqualTo(reservedDate2);
    }
}
