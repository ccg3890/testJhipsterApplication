package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 도면
 */
@Entity
@Table(name = "tb_drawing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Drawing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 도면명
     */
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "drawing")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "shapeAsset", "resource", "drawing" }, allowSetters = true)
    private Set<DrawingItem> drawingItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Drawing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Drawing name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DrawingItem> getDrawingItems() {
        return this.drawingItems;
    }

    public void setDrawingItems(Set<DrawingItem> drawingItems) {
        if (this.drawingItems != null) {
            this.drawingItems.forEach(i -> i.setDrawing(null));
        }
        if (drawingItems != null) {
            drawingItems.forEach(i -> i.setDrawing(this));
        }
        this.drawingItems = drawingItems;
    }

    public Drawing drawingItems(Set<DrawingItem> drawingItems) {
        this.setDrawingItems(drawingItems);
        return this;
    }

    public Drawing addDrawingItems(DrawingItem drawingItem) {
        this.drawingItems.add(drawingItem);
        drawingItem.setDrawing(this);
        return this;
    }

    public Drawing removeDrawingItems(DrawingItem drawingItem) {
        this.drawingItems.remove(drawingItem);
        drawingItem.setDrawing(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drawing)) {
            return false;
        }
        return id != null && id.equals(((Drawing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drawing{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
