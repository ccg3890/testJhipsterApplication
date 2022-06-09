package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DrawingItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrawingItem.class);
        DrawingItem drawingItem1 = new DrawingItem();
        drawingItem1.setId(1L);
        DrawingItem drawingItem2 = new DrawingItem();
        drawingItem2.setId(drawingItem1.getId());
        assertThat(drawingItem1).isEqualTo(drawingItem2);
        drawingItem2.setId(2L);
        assertThat(drawingItem1).isNotEqualTo(drawingItem2);
        drawingItem1.setId(null);
        assertThat(drawingItem1).isNotEqualTo(drawingItem2);
    }
}
