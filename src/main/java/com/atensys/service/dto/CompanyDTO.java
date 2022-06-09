package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Company} entity.
 */
@Schema(description = "회사")
public class CompanyDTO implements Serializable {

    private Long id;

    /**
     * 회사명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "회사명", required = true)
    private String name;

    /**
     * 회사영문명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "회사영문명", required = true)
    private String engName;

    /**
     * 회사 설명
     */
    @Size(max = 1024)
    @Schema(description = "회사 설명")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
