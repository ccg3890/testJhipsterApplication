package com.cocoluluk.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cocoluluk.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoutineMainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoutineMain.class);
        RoutineMain routineMain1 = new RoutineMain();
        routineMain1.setId("id1");
        RoutineMain routineMain2 = new RoutineMain();
        routineMain2.setId(routineMain1.getId());
        assertThat(routineMain1).isEqualTo(routineMain2);
        routineMain2.setId("id2");
        assertThat(routineMain1).isNotEqualTo(routineMain2);
        routineMain1.setId(null);
        assertThat(routineMain1).isNotEqualTo(routineMain2);
    }
}
