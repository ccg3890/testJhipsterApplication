package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DrawingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drawing.class);
        Drawing drawing1 = new Drawing();
        drawing1.setId(1L);
        Drawing drawing2 = new Drawing();
        drawing2.setId(drawing1.getId());
        assertThat(drawing1).isEqualTo(drawing2);
        drawing2.setId(2L);
        assertThat(drawing1).isNotEqualTo(drawing2);
        drawing1.setId(null);
        assertThat(drawing1).isNotEqualTo(drawing2);
    }
}
