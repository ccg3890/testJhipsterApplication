package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeOfficeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeOffice.class);
        AttributeOffice attributeOffice1 = new AttributeOffice();
        attributeOffice1.setId(1L);
        AttributeOffice attributeOffice2 = new AttributeOffice();
        attributeOffice2.setId(attributeOffice1.getId());
        assertThat(attributeOffice1).isEqualTo(attributeOffice2);
        attributeOffice2.setId(2L);
        assertThat(attributeOffice1).isNotEqualTo(attributeOffice2);
        attributeOffice1.setId(null);
        assertThat(attributeOffice1).isNotEqualTo(attributeOffice2);
    }
}
