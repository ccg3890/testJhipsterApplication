package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeFloorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeFloorDTO.class);
        AttributeFloorDTO attributeFloorDTO1 = new AttributeFloorDTO();
        attributeFloorDTO1.setId(1L);
        AttributeFloorDTO attributeFloorDTO2 = new AttributeFloorDTO();
        assertThat(attributeFloorDTO1).isNotEqualTo(attributeFloorDTO2);
        attributeFloorDTO2.setId(attributeFloorDTO1.getId());
        assertThat(attributeFloorDTO1).isEqualTo(attributeFloorDTO2);
        attributeFloorDTO2.setId(2L);
        assertThat(attributeFloorDTO1).isNotEqualTo(attributeFloorDTO2);
        attributeFloorDTO1.setId(null);
        assertThat(attributeFloorDTO1).isNotEqualTo(attributeFloorDTO2);
    }
}
