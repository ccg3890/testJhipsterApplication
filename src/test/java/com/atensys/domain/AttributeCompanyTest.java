package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeCompany.class);
        AttributeCompany attributeCompany1 = new AttributeCompany();
        attributeCompany1.setId(1L);
        AttributeCompany attributeCompany2 = new AttributeCompany();
        attributeCompany2.setId(attributeCompany1.getId());
        assertThat(attributeCompany1).isEqualTo(attributeCompany2);
        attributeCompany2.setId(2L);
        assertThat(attributeCompany1).isNotEqualTo(attributeCompany2);
        attributeCompany1.setId(null);
        assertThat(attributeCompany1).isNotEqualTo(attributeCompany2);
    }
}
