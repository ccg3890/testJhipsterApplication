package com.atensys.service.dto;

import com.atensys.domain.enumeration.ShapeType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Shape} entity.
 */
@Schema(description = "도형")
public class ShapeDTO implements Serializable {

    private Long id;

    /**
     * 도형명
     */
    @Size(max = 50)
    @Schema(description = "도형명")
    private String name;

    /**
     * 도형종류
     */
    @NotNull
    @Schema(description = "도형종류", required = true)
    private ShapeType shapeType;

    /**
     * SVG Path
     */
    @Size(max = 2048)
    @Schema(description = "SVG Path")
    private String path;

    /**
     * 사용여부
     */
    @NotNull
    @Schema(description = "사용여부", required = true)
    private Boolean valid;

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

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShapeDTO)) {
            return false;
        }

        ShapeDTO shapeDTO = (ShapeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shapeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShapeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shapeType='" + getShapeType() + "'" +
            ", path='" + getPath() + "'" +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
