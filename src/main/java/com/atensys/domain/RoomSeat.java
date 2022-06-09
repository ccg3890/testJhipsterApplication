package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 회의실 좌석
 */
@Entity
@Table(name = "tb_roomseat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoomSeat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 회의실 좌석명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * 회의실 좌석 설명
     */
    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

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

    /**
     * 사용가능여부
     */
    @NotNull
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roomManagers", "roomUserGroups", "roomSeats", "attributes", "image" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoomSeat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RoomSeat name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public RoomSeat description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTop() {
        return this.top;
    }

    public RoomSeat top(Long top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getLeft() {
        return this.left;
    }

    public RoomSeat left(Long left) {
        this.setLeft(left);
        return this;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public RoomSeat valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RoomSeat room(Room room) {
        this.setRoom(room);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomSeat)) {
            return false;
        }
        return id != null && id.equals(((RoomSeat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomSeat{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", top=" + getTop() +
            ", left=" + getLeft() +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
