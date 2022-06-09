package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.DrawingItem} entity.
 */
@Schema(description = "도면아이템")
public class DrawingItemDTO implements Serializable {

    private Long id;

    /**
     * 도면아이템명
     */
    @Size(max = 100)
    @Schema(description = "도면아이템명")
    private String name;

    /**
     * Y좌표
     */
    @Min(value = 0L)
    @Schema(description = "Y좌표")
    private Long top;

    /**
     * X좌표
     */
    @Min(value = 0L)
    @Schema(description = "X좌표")
    private Long left;

    private ShapeAssetDTO shapeAsset;

    private ResourceDTO resource;

    private DrawingDTO drawing;

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

    public Long getTop() {
        return top;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public ShapeAssetDTO getShapeAsset() {
        return shapeAsset;
    }

    public void setShapeAsset(ShapeAssetDTO shapeAsset) {
        this.shapeAsset = shapeAsset;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    public DrawingDTO getDrawing() {
        return drawing;
    }

    public void setDrawing(DrawingDTO drawing) {
        this.drawing = drawing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrawingItemDTO)) {
            return false;
        }

        DrawingItemDTO drawingItemDTO = (DrawingItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, drawingItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrawingItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", top=" + getTop() +
            ", left=" + getLeft() +
            ", shapeAsset=" + getShapeAsset() +
            ", resource=" + getResource() +
            ", drawing=" + getDrawing() +
            "}";
    }
}
