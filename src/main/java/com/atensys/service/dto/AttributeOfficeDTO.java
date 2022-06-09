package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atensys.domain.AttributeOffice} entity.
 */
@Schema(description = "오피스속성")
public class AttributeOfficeDTO implements Serializable {

    private Long id;

    private OfficeDTO office;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfficeDTO getOffice() {
        return office;
    }

    public void setOffice(OfficeDTO office) {
        this.office = office;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeOfficeDTO)) {
            return false;
        }

        AttributeOfficeDTO attributeOfficeDTO = (AttributeOfficeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attributeOfficeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeOfficeDTO{" +
            "id=" + getId() +
            ", office=" + getOffice() +
            "}";
    }
}
