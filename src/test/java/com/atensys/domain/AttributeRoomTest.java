package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeRoomTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeRoom.class);
        AttributeRoom attributeRoom1 = new AttributeRoom();
        attributeRoom1.setId(1L);
        AttributeRoom attributeRoom2 = new AttributeRoom();
        attributeRoom2.setId(attributeRoom1.getId());
        assertThat(attributeRoom1).isEqualTo(attributeRoom2);
        attributeRoom2.setId(2L);
        assertThat(attributeRoom1).isNotEqualTo(attributeRoom2);
        attributeRoom1.setId(null);
        assertThat(attributeRoom1).isNotEqualTo(attributeRoom2);
    }
}
