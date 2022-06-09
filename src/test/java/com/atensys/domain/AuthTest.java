package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auth.class);
        Auth auth1 = new Auth();
        auth1.setId(1L);
        Auth auth2 = new Auth();
        auth2.setId(auth1.getId());
        assertThat(auth1).isEqualTo(auth2);
        auth2.setId(2L);
        assertThat(auth1).isNotEqualTo(auth2);
        auth1.setId(null);
        assertThat(auth1).isNotEqualTo(auth2);
    }
}
