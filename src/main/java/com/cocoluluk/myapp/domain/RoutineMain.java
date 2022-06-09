package com.cocoluluk.myapp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.domain.Persistable;

/**
 * Rutine esay
 */
@Schema(description = "Rutine esay")
@Entity
@Table(name = "routine_main")
public class RoutineMain implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "registerid", nullable = false)
    private String registerid;

    @Column(name = "description")
    private String description;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public RoutineMain id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterid() {
        return this.registerid;
    }

    public RoutineMain registerid(String registerid) {
        this.setRegisterid(registerid);
        return this;
    }

    public void setRegisterid(String registerid) {
        this.registerid = registerid;
    }

    public String getDescription() {
        return this.description;
    }

    public RoutineMain description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public RoutineMain setIsPersisted() {
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
        if (!(o instanceof RoutineMain)) {
            return false;
        }
        return id != null && id.equals(((RoutineMain) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoutineMain{" +
            "id=" + getId() +
            ", registerid='" + getRegisterid() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
