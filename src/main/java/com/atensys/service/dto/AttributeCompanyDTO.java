package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atensys.domain.AttributeCompany} entity.
 */
@Schema(description = "회사속성")
public class AttributeCompanyDTO implements Serializable {

    private Long id;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeCompanyDTO)) {
            return false;
        }

        AttributeCompanyDTO attributeCompanyDTO = (AttributeCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attributeCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeCompanyDTO{" +
            "id=" + getId() +
            ", company=" + getCompany() +
            "}";
    }
}
