package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeOfficeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeOfficeDTO.class);
        AttributeOfficeDTO attributeOfficeDTO1 = new AttributeOfficeDTO();
        attributeOfficeDTO1.setId(1L);
        AttributeOfficeDTO attributeOfficeDTO2 = new AttributeOfficeDTO();
        assertThat(attributeOfficeDTO1).isNotEqualTo(attributeOfficeDTO2);
        attributeOfficeDTO2.setId(attributeOfficeDTO1.getId());
        assertThat(attributeOfficeDTO1).isEqualTo(attributeOfficeDTO2);
        attributeOfficeDTO2.setId(2L);
        assertThat(attributeOfficeDTO1).isNotEqualTo(attributeOfficeDTO2);
        attributeOfficeDTO1.setId(null);
        assertThat(attributeOfficeDTO1).isNotEqualTo(attributeOfficeDTO2);
    }
}
