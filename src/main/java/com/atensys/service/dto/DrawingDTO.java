package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Drawing} entity.
 */
@Schema(description = "도면")
public class DrawingDTO implements Serializable {

    private Long id;

    /**
     * 도면명
     */
    @Size(max = 100)
    @Schema(description = "도면명")
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrawingDTO)) {
            return false;
        }

        DrawingDTO drawingDTO = (DrawingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, drawingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrawingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
