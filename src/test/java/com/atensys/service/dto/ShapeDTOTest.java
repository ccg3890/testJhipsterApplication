package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShapeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShapeDTO.class);
        ShapeDTO shapeDTO1 = new ShapeDTO();
        shapeDTO1.setId(1L);
        ShapeDTO shapeDTO2 = new ShapeDTO();
        assertThat(shapeDTO1).isNotEqualTo(shapeDTO2);
        shapeDTO2.setId(shapeDTO1.getId());
        assertThat(shapeDTO1).isEqualTo(shapeDTO2);
        shapeDTO2.setId(2L);
        assertThat(shapeDTO1).isNotEqualTo(shapeDTO2);
        shapeDTO1.setId(null);
        assertThat(shapeDTO1).isNotEqualTo(shapeDTO2);
    }
}
