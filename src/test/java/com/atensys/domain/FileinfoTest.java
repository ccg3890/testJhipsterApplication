package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileinfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fileinfo.class);
        Fileinfo fileinfo1 = new Fileinfo();
        fileinfo1.setId(1L);
        Fileinfo fileinfo2 = new Fileinfo();
        fileinfo2.setId(fileinfo1.getId());
        assertThat(fileinfo1).isEqualTo(fileinfo2);
        fileinfo2.setId(2L);
        assertThat(fileinfo1).isNotEqualTo(fileinfo2);
        fileinfo1.setId(null);
        assertThat(fileinfo1).isNotEqualTo(fileinfo2);
    }
}
