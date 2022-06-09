package com.cocoluluk.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.domain.Persistable;

/**
 * A Routine.
 */
@Entity
@Table(name = "routine")
public class Routine implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "register", nullable = false)
    private String register;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "jhi_desc")
    private String desc;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Routine id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegister() {
        return this.register;
    }

    public Routine register(String register) {
        this.setRegister(register);
        return this;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getType() {
        return this.type;
    }

    public Routine type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return this.desc;
    }

    public Routine desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Routine setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Routine)) {
            return false;
        }
        return id != null && id.equals(((Routine) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Routine{" +
            "id=" + getId() +
            ", register='" + getRegister() + "'" +
            ", type='" + getType() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
