package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShapeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shape.class);
        Shape shape1 = new Shape();
        shape1.setId(1L);
        Shape shape2 = new Shape();
        shape2.setId(shape1.getId());
        assertThat(shape1).isEqualTo(shape2);
        shape2.setId(2L);
        assertThat(shape1).isNotEqualTo(shape2);
        shape1.setId(null);
        assertThat(shape1).isNotEqualTo(shape2);
    }
}
