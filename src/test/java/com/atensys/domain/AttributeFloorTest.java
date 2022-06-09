package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeFloorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeFloor.class);
        AttributeFloor attributeFloor1 = new AttributeFloor();
        attributeFloor1.setId(1L);
        AttributeFloor attributeFloor2 = new AttributeFloor();
        attributeFloor2.setId(attributeFloor1.getId());
        assertThat(attributeFloor1).isEqualTo(attributeFloor2);
        attributeFloor2.setId(2L);
        assertThat(attributeFloor1).isNotEqualTo(attributeFloor2);
        attributeFloor1.setId(null);
        assertThat(attributeFloor1).isNotEqualTo(attributeFloor2);
    }
}
