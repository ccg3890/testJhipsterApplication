package com.atensys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atensys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArchetypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArchetypeDTO.class);
        ArchetypeDTO archetypeDTO1 = new ArchetypeDTO();
        archetypeDTO1.setId(1L);
        ArchetypeDTO archetypeDTO2 = new ArchetypeDTO();
        assertThat(archetypeDTO1).isNotEqualTo(archetypeDTO2);
        archetypeDTO2.setId(archetypeDTO1.getId());
        assertThat(archetypeDTO1).isEqualTo(archetypeDTO2);
        archetypeDTO2.setId(2L);
        assertThat(archetypeDTO1).isNotEqualTo(archetypeDTO2);
        archetypeDTO1.setId(null);
        assertThat(archetypeDTO1).isNotEqualTo(archetypeDTO2);
    }
}
