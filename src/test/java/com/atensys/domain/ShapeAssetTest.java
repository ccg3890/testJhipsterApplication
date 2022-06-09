package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShapeAssetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShapeAsset.class);
        ShapeAsset shapeAsset1 = new ShapeAsset();
        shapeAsset1.setId(1L);
        ShapeAsset shapeAsset2 = new ShapeAsset();
        shapeAsset2.setId(shapeAsset1.getId());
        assertThat(shapeAsset1).isEqualTo(shapeAsset2);
        shapeAsset2.setId(2L);
        assertThat(shapeAsset1).isNotEqualTo(shapeAsset2);
        shapeAsset1.setId(null);
        assertThat(shapeAsset1).isNotEqualTo(shapeAsset2);
    }
}
