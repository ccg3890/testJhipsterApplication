package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeRoomDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeRoomDTO.class);
        AttributeRoomDTO attributeRoomDTO1 = new AttributeRoomDTO();
        attributeRoomDTO1.setId(1L);
        AttributeRoomDTO attributeRoomDTO2 = new AttributeRoomDTO();
        assertThat(attributeRoomDTO1).isNotEqualTo(attributeRoomDTO2);
        attributeRoomDTO2.setId(attributeRoomDTO1.getId());
        assertThat(attributeRoomDTO1).isEqualTo(attributeRoomDTO2);
        attributeRoomDTO2.setId(2L);
        assertThat(attributeRoomDTO1).isNotEqualTo(attributeRoomDTO2);
        attributeRoomDTO1.setId(null);
        assertThat(attributeRoomDTO1).isNotEqualTo(attributeRoomDTO2);
    }
}
