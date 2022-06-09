package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 도면아이템
 */
@Entity
@Table(name = "tb_drawingitem")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DrawingItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 도면아이템명
     */
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    /**
     * Y좌표
     */
    @Min(value = 0L)
    @Column(name = "top")
    private Long top;

    /**
     * X좌표
     */
    @Min(value = 0L)
    @Column(name = "jhi_left")
    private Long left;

    @ManyToOne
    @JsonIgnoreProperties(value = { "shape" }, allowSetters = true)
    private ShapeAsset shapeAsset;

    @ManyToOne
    @JsonIgnoreProperties(value = { "floor" }, allowSetters = true)
    private Resource resource;

    @ManyToOne
    @JsonIgnoreProperties(value = { "drawingItems" }, allowSetters = true)
    private Drawing drawing;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DrawingItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DrawingItem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTop() {
        return this.top;
    }

    public DrawingItem top(Long top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getLeft() {
        return this.left;
    }

    public DrawingItem left(Long left) {
        this.setLeft(left);
        return this;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public ShapeAsset getShapeAsset() {
        return this.shapeAsset;
    }

    public void setShapeAsset(ShapeAsset shapeAsset) {
        this.shapeAsset = shapeAsset;
    }

    public DrawingItem shapeAsset(ShapeAsset shapeAsset) {
        this.setShapeAsset(shapeAsset);
        return this;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public DrawingItem resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    public Drawing getDrawing() {
        return this.drawing;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public DrawingItem drawing(Drawing drawing) {
        this.setDrawing(drawing);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrawingItem)) {
            return false;
        }
        return id != null && id.equals(((DrawingItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DrawingItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", top=" + getTop() +
            ", left=" + getLeft() +
            "}";
    }
}
