package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DrawingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrawingDTO.class);
        DrawingDTO drawingDTO1 = new DrawingDTO();
        drawingDTO1.setId(1L);
        DrawingDTO drawingDTO2 = new DrawingDTO();
        assertThat(drawingDTO1).isNotEqualTo(drawingDTO2);
        drawingDTO2.setId(drawingDTO1.getId());
        assertThat(drawingDTO1).isEqualTo(drawingDTO2);
        drawingDTO2.setId(2L);
        assertThat(drawingDTO1).isNotEqualTo(drawingDTO2);
        drawingDTO1.setId(null);
        assertThat(drawingDTO1).isNotEqualTo(drawingDTO2);
    }
}
