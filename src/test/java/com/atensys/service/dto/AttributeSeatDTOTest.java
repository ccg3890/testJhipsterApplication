package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeSeatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeSeatDTO.class);
        AttributeSeatDTO attributeSeatDTO1 = new AttributeSeatDTO();
        attributeSeatDTO1.setId(1L);
        AttributeSeatDTO attributeSeatDTO2 = new AttributeSeatDTO();
        assertThat(attributeSeatDTO1).isNotEqualTo(attributeSeatDTO2);
        attributeSeatDTO2.setId(attributeSeatDTO1.getId());
        assertThat(attributeSeatDTO1).isEqualTo(attributeSeatDTO2);
        attributeSeatDTO2.setId(2L);
        assertThat(attributeSeatDTO1).isNotEqualTo(attributeSeatDTO2);
        attributeSeatDTO1.setId(null);
        assertThat(attributeSeatDTO1).isNotEqualTo(attributeSeatDTO2);
    }
}
