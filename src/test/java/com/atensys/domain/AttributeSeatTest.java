package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeSeatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeSeat.class);
        AttributeSeat attributeSeat1 = new AttributeSeat();
        attributeSeat1.setId(1L);
        AttributeSeat attributeSeat2 = new AttributeSeat();
        attributeSeat2.setId(attributeSeat1.getId());
        assertThat(attributeSeat1).isEqualTo(attributeSeat2);
        attributeSeat2.setId(2L);
        assertThat(attributeSeat1).isNotEqualTo(attributeSeat2);
        attributeSeat1.setId(null);
        assertThat(attributeSeat1).isNotEqualTo(attributeSeat2);
    }
}
