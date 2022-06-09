package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 회의실 사용자 그룹
 */
@Entity
@Table(name = "tb_roomusergroup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoomUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 그룹(조직)ID
     */
    @NotNull
    @Size(max = 32)
    @Column(name = "group_id", length = 32, nullable = false)
    private String groupId;

    /**
     * 그룹(조직)명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roomManagers", "roomUserGroups", "roomSeats", "attributes", "image" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RoomUserGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public RoomUserGroup groupId(String groupId) {
        this.setGroupId(groupId);
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return this.name;
    }

    public RoomUserGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RoomUserGroup room(Room room) {
        this.setRoom(room);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomUserGroup)) {
            return false;
        }
        return id != null && id.equals(((RoomUserGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomUserGroup{" +
            "id=" + getId() +
            ", groupId='" + getGroupId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
