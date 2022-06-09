package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeCompanyDTO.class);
        AttributeCompanyDTO attributeCompanyDTO1 = new AttributeCompanyDTO();
        attributeCompanyDTO1.setId(1L);
        AttributeCompanyDTO attributeCompanyDTO2 = new AttributeCompanyDTO();
        assertThat(attributeCompanyDTO1).isNotEqualTo(attributeCompanyDTO2);
        attributeCompanyDTO2.setId(attributeCompanyDTO1.getId());
        assertThat(attributeCompanyDTO1).isEqualTo(attributeCompanyDTO2);
        attributeCompanyDTO2.setId(2L);
        assertThat(attributeCompanyDTO1).isNotEqualTo(attributeCompanyDTO2);
        attributeCompanyDTO1.setId(null);
        assertThat(attributeCompanyDTO1).isNotEqualTo(attributeCompanyDTO2);
    }
}
