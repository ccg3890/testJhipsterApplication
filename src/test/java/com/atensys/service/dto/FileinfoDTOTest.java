package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileinfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileinfoDTO.class);
        FileinfoDTO fileinfoDTO1 = new FileinfoDTO();
        fileinfoDTO1.setId(1L);
        FileinfoDTO fileinfoDTO2 = new FileinfoDTO();
        assertThat(fileinfoDTO1).isNotEqualTo(fileinfoDTO2);
        fileinfoDTO2.setId(fileinfoDTO1.getId());
        assertThat(fileinfoDTO1).isEqualTo(fileinfoDTO2);
        fileinfoDTO2.setId(2L);
        assertThat(fileinfoDTO1).isNotEqualTo(fileinfoDTO2);
        fileinfoDTO1.setId(null);
        assertThat(fileinfoDTO1).isNotEqualTo(fileinfoDTO2);
    }
}
