package com.atensys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecurrenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recurrence.class);
        Recurrence recurrence1 = new Recurrence();
        recurrence1.setId(1L);
        Recurrence recurrence2 = new Recurrence();
        recurrence2.setId(recurrence1.getId());
        assertThat(recurrence1).isEqualTo(recurrence2);
        recurrence2.setId(2L);
        assertThat(recurrence1).isNotEqualTo(recurrence2);
        recurrence1.setId(null);
        assertThat(recurrence1).isNotEqualTo(recurrence2);
    }
}
