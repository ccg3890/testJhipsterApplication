package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DrawingItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrawingItemDTO.class);
        DrawingItemDTO drawingItemDTO1 = new DrawingItemDTO();
        drawingItemDTO1.setId(1L);
        DrawingItemDTO drawingItemDTO2 = new DrawingItemDTO();
        assertThat(drawingItemDTO1).isNotEqualTo(drawingItemDTO2);
        drawingItemDTO2.setId(drawingItemDTO1.getId());
        assertThat(drawingItemDTO1).isEqualTo(drawingItemDTO2);
        drawingItemDTO2.setId(2L);
        assertThat(drawingItemDTO1).isNotEqualTo(drawingItemDTO2);
        drawingItemDTO1.setId(null);
        assertThat(drawingItemDTO1).isNotEqualTo(drawingItemDTO2);
    }
}
