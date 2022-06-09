package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShapeAssetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShapeAssetDTO.class);
        ShapeAssetDTO shapeAssetDTO1 = new ShapeAssetDTO();
        shapeAssetDTO1.setId(1L);
        ShapeAssetDTO shapeAssetDTO2 = new ShapeAssetDTO();
        assertThat(shapeAssetDTO1).isNotEqualTo(shapeAssetDTO2);
        shapeAssetDTO2.setId(shapeAssetDTO1.getId());
        assertThat(shapeAssetDTO1).isEqualTo(shapeAssetDTO2);
        shapeAssetDTO2.setId(2L);
        assertThat(shapeAssetDTO1).isNotEqualTo(shapeAssetDTO2);
        shapeAssetDTO1.setId(null);
        assertThat(shapeAssetDTO1).isNotEqualTo(shapeAssetDTO2);
    }
}
