package com.atensys.service.dto;

import com.atensys.domain.enumeration.AlignVertical;
import com.atensys.domain.enumeration.ShapeAssetType;
import com.atensys.domain.enumeration.ShapeImageType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.ShapeAsset} entity.
 */
@Schema(description = "도형자산")
public class ShapeAssetDTO implements Serializable {

    private Long id;

    /**
     * 도형자산명
     */
    @Size(max = 100)
    @Schema(description = "도형자산명")
    private String name;

    /**
     * 도형자산유형
     */
    @NotNull
    @Schema(description = "도형자산유형", required = true)
    private ShapeAssetType assetType;

    /**
     * 도형이미지구분
     */
    @NotNull
    @Schema(description = "도형이미지구분", required = true)
    private ShapeImageType shapeImageType;

    /**
     * 폭
     */
    @NotNull
    @Min(value = 0L)
    @Schema(description = "폭", required = true)
    private Long width;

    /**
     * 높이
     */
    @NotNull
    @Min(value = 0L)
    @Schema(description = "높이", required = true)
    private Long height;

    /**
     * 세로정렬방법
     */
    @Schema(description = "세로정렬방법")
    private AlignVertical textAlign;

    /**
     * 도형스타일
     */
    @Size(max = 2048)
    @Schema(description = "도형스타일")
    private String shapeStyle;

    /**
     * 텍스트스타일
     */
    @Size(max = 2048)
    @Schema(description = "텍스트스타일")
    private String textStyle;

    /**
     * 크기 조절 여부
     */
    @NotNull
    @Schema(description = "크기 조절 여부", required = true)
    private Boolean scalable;

    /**
     * 사용여부
     */
    @Schema(description = "사용여부")
    private Boolean useYn;

    private ShapeDTO shape;

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

    public ShapeAssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(ShapeAssetType assetType) {
        this.assetType = assetType;
    }

    public ShapeImageType getShapeImageType() {
        return shapeImageType;
    }

    public void setShapeImageType(ShapeImageType shapeImageType) {
        this.shapeImageType = shapeImageType;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public AlignVertical getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(AlignVertical textAlign) {
        this.textAlign = textAlign;
    }

    public String getShapeStyle() {
        return shapeStyle;
    }

    public void setShapeStyle(String shapeStyle) {
        this.shapeStyle = shapeStyle;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public Boolean getScalable() {
        return scalable;
    }

    public void setScalable(Boolean scalable) {
        this.scalable = scalable;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public ShapeDTO getShape() {
        return shape;
    }

    public void setShape(ShapeDTO shape) {
        this.shape = shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShapeAssetDTO)) {
            return false;
        }

        ShapeAssetDTO shapeAssetDTO = (ShapeAssetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shapeAssetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShapeAssetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", assetType='" + getAssetType() + "'" +
            ", shapeImageType='" + getShapeImageType() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", textAlign='" + getTextAlign() + "'" +
            ", shapeStyle='" + getShapeStyle() + "'" +
            ", textStyle='" + getTextStyle() + "'" +
            ", scalable='" + getScalable() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", shape=" + getShape() +
            "}";
    }
}
