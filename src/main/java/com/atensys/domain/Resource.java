package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 자원(좌석,회의실,라커 등)
 */
@Entity
@Table(name = "tb_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource implements Serializable {

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
     * 사용가능여부
     */
    @NotNull
    @Column(name = "use_yn", nullable = false)
    private Boolean useYn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "resources", "attributes", "webImg", "kioskImg", "miniImg", "drawing", "office" }, allowSetters = true)
    private Floor floor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Resource id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Resource name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUseYn() {
        return this.useYn;
    }

    public Resource useYn(Boolean useYn) {
        this.setUseYn(useYn);
        return this;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Resource floor(Floor floor) {
        this.setFloor(floor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
