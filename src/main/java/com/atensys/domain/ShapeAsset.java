package com.atensys.domain;

import com.atensys.domain.enumeration.AlignVertical;
import com.atensys.domain.enumeration.ShapeAssetType;
import com.atensys.domain.enumeration.ShapeImageType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 도형자산
 */
@Entity
@Table(name = "tb_shapeasset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShapeAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 도형자산명
     */
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    /**
     * 도형자산유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false)
    private ShapeAssetType assetType;

    /**
     * 도형이미지구분
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shape_image_type", nullable = false)
    private ShapeImageType shapeImageType;

    /**
     * 폭
     */
    @NotNull
    @Min(value = 0L)
    @Column(name = "width", nullable = false)
    private Long width;

    /**
     * 높이
     */
    @NotNull
    @Min(value = 0L)
    @Column(name = "height", nullable = false)
    private Long height;

    /**
     * 세로정렬방법
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "text_align")
    private AlignVertical textAlign;

    /**
     * 도형스타일
     */
    @Size(max = 2048)
    @Column(name = "shape_style", length = 2048)
    private String shapeStyle;

    /**
     * 텍스트스타일
     */
    @Size(max = 2048)
    @Column(name = "text_style", length = 2048)
    private String textStyle;

    /**
     * 크기 조절 여부
     */
    @NotNull
    @Column(name = "scalable", nullable = false)
    private Boolean scalable;

    /**
     * 사용여부
     */
    @Column(name = "use_yn")
    private Boolean useYn;

    @ManyToOne
    private Shape shape;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ShapeAsset id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ShapeAsset name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShapeAssetType getAssetType() {
        return this.assetType;
    }

    public ShapeAsset assetType(ShapeAssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(ShapeAssetType assetType) {
        this.assetType = assetType;
    }

    public ShapeImageType getShapeImageType() {
        return this.shapeImageType;
    }

    public ShapeAsset shapeImageType(ShapeImageType shapeImageType) {
        this.setShapeImageType(shapeImageType);
        return this;
    }

    public void setShapeImageType(ShapeImageType shapeImageType) {
        this.shapeImageType = shapeImageType;
    }

    public Long getWidth() {
        return this.width;
    }

    public ShapeAsset width(Long width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return this.height;
    }

    public ShapeAsset height(Long height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public AlignVertical getTextAlign() {
        return this.textAlign;
    }

    public ShapeAsset textAlign(AlignVertical textAlign) {
        this.setTextAlign(textAlign);
        return this;
    }

    public void setTextAlign(AlignVertical textAlign) {
        this.textAlign = textAlign;
    }

    public String getShapeStyle() {
        return this.shapeStyle;
    }

    public ShapeAsset shapeStyle(String shapeStyle) {
        this.setShapeStyle(shapeStyle);
        return this;
    }

    public void setShapeStyle(String shapeStyle) {
        this.shapeStyle = shapeStyle;
    }

    public String getTextStyle() {
        return this.textStyle;
    }

    public ShapeAsset textStyle(String textStyle) {
        this.setTextStyle(textStyle);
        return this;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public Boolean getScalable() {
        return this.scalable;
    }

    public ShapeAsset scalable(Boolean scalable) {
        this.setScalable(scalable);
        return this;
    }

    public void setScalable(Boolean scalable) {
        this.scalable = scalable;
    }

    public Boolean getUseYn() {
        return this.useYn;
    }

    public ShapeAsset useYn(Boolean useYn) {
        this.setUseYn(useYn);
        return this;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public ShapeAsset shape(Shape shape) {
        this.setShape(shape);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShapeAsset)) {
            return false;
        }
        return id != null && id.equals(((ShapeAsset) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShapeAsset{" +
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
            "}";
    }
}
