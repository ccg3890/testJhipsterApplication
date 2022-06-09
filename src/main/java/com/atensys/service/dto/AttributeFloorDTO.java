package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atensys.domain.AttributeFloor} entity.
 */
@Schema(description = "층속성")
public class AttributeFloorDTO implements Serializable {

    private Long id;

    private FloorDTO floor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof AttributeFloorDTO)) {
            return false;
        }

        AttributeFloorDTO attributeFloorDTO = (AttributeFloorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attributeFloorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeFloorDTO{" +
            "id=" + getId() +
            ", floor=" + getFloor() +
            "}";
    }
}
