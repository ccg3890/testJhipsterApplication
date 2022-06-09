package com.atensys.domain;

import com.atensys.domain.enumeration.ShapeType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 도형
 */
@Entity
@Table(name = "tb_shape")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Shape implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 도형명
     */
    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    /**
     * 도형종류
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shape_type", nullable = false)
    private ShapeType shapeType;

    /**
     * SVG Path
     */
    @Size(max = 2048)
    @Column(name = "path", length = 2048)
    private String path;

    /**
     * 사용여부
     */
    @NotNull
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Shape id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Shape name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShapeType getShapeType() {
        return this.shapeType;
    }

    public Shape shapeType(ShapeType shapeType) {
        this.setShapeType(shapeType);
        return this;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public String getPath() {
        return this.path;
    }

    public Shape path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public Shape valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shape)) {
            return false;
        }
        return id != null && id.equals(((Shape) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shape{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shapeType='" + getShapeType() + "'" +
            ", path='" + getPath() + "'" +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
