package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthDTO.class);
        AuthDTO authDTO1 = new AuthDTO();
        authDTO1.setId(1L);
        AuthDTO authDTO2 = new AuthDTO();
        assertThat(authDTO1).isNotEqualTo(authDTO2);
        authDTO2.setId(authDTO1.getId());
        assertThat(authDTO1).isEqualTo(authDTO2);
        authDTO2.setId(2L);
        assertThat(authDTO1).isNotEqualTo(authDTO2);
        authDTO1.setId(null);
        assertThat(authDTO1).isNotEqualTo(authDTO2);
    }
}
