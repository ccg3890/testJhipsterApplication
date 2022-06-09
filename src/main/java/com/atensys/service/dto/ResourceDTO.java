package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Resource} entity.
 */
@Schema(description = "자원(좌석,회의실,라커 등)")
public class ResourceDTO implements Serializable {

    private Long id;

    /**
     * 도면아이템명
     */
    @Size(max = 100)
    @Schema(description = "도면아이템명")
    private String name;

    /**
     * 사용가능여부
     */
    @NotNull
    @Schema(description = "사용가능여부", required = true)
    private Boolean useYn;

    private FloorDTO floor;

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

    public Boolean getUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public FloorDTO getFloor() {
        return floor;
    }

    public void setFloor(FloorDTO floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceDTO)) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", floor=" + getFloor() +
            "}";
    }
}
