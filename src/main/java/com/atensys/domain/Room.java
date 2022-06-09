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
 * 회의실
 */
@Entity
@Table(name = "tb_room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 건물
     */
    @NotNull
    @Column(name = "office_id", nullable = false)
    private Long officeId;

    /**
     * 층
     */
    @NotNull
    @Column(name = "floor_id", nullable = false)
    private Long floorId;

    /**
     * 회의실명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * 회의실 설명
     */
    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    /**
     * 최대예약시간(분)
     */
    @Max(value = 1420L)
    @Column(name = "personnel")
    private Long personnel;

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
     * 예약가능시간 사용여부
     */
    @NotNull
    @Column(name = "use_reservation_time", nullable = false)
    private Boolean useReservationTime;

    /**
     * 예약가능 시간(from)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Column(name = "reservation_available_from_time", length = 5)
    private String reservationAvailableFromTime;

    /**
     * 예약가능 시간(to)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Column(name = "reservation_available_to_time", length = 5)
    private String reservationAvailableToTime;

    /**
     * 예약관리여부
     */
    @NotNull
    @Column(name = "use_approval", nullable = false)
    private Boolean useApproval;

    /**
     * 사용자예약여부
     */
    @NotNull
    @Column(name = "use_user_available", nullable = false)
    private Boolean useUserAvailable;

    /**
     * 최대예약시간사용여부
     */
    @NotNull
    @Column(name = "use_check_in_out", nullable = false)
    private Boolean useCheckInOut;

    /**
     * 입퇴실관리 시간(from)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Column(name = "check_in_out_from_time", length = 5)
    private String checkInOutFromTime;

    /**
     * 입퇴실관리 시간(to)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Column(name = "check_in_out_to_time", length = 5)
    private String checkInOutToTime;

    /**
     * 사용가능여부
     */
    @NotNull
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    @OneToMany(mappedBy = "room")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "room" }, allowSetters = true)
    private Set<RoomManager> roomManagers = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "room" }, allowSetters = true)
    private Set<RoomUserGroup> roomUserGroups = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "room" }, allowSetters = true)
    private Set<RoomSeat> roomSeats = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "room" }, allowSetters = true)
    private Set<AttributeRoom> attributes = new HashSet<>();

    @ManyToOne
    private Fileinfo image;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Room id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfficeId() {
        return this.officeId;
    }

    public Room officeId(Long officeId) {
        this.setOfficeId(officeId);
        return this;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public Long getFloorId() {
        return this.floorId;
    }

    public Room floorId(Long floorId) {
        this.setFloorId(floorId);
        return this;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getName() {
        return this.name;
    }

    public Room name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Room description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPersonnel() {
        return this.personnel;
    }

    public Room personnel(Long personnel) {
        this.setPersonnel(personnel);
        return this;
    }

    public void setPersonnel(Long personnel) {
        this.personnel = personnel;
    }

    public Long getTop() {
        return this.top;
    }

    public Room top(Long top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getLeft() {
        return this.left;
    }

    public Room left(Long left) {
        this.setLeft(left);
        return this;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Boolean getUseReservationTime() {
        return this.useReservationTime;
    }

    public Room useReservationTime(Boolean useReservationTime) {
        this.setUseReservationTime(useReservationTime);
        return this;
    }

    public void setUseReservationTime(Boolean useReservationTime) {
        this.useReservationTime = useReservationTime;
    }

    public String getReservationAvailableFromTime() {
        return this.reservationAvailableFromTime;
    }

    public Room reservationAvailableFromTime(String reservationAvailableFromTime) {
        this.setReservationAvailableFromTime(reservationAvailableFromTime);
        return this;
    }

    public void setReservationAvailableFromTime(String reservationAvailableFromTime) {
        this.reservationAvailableFromTime = reservationAvailableFromTime;
    }

    public String getReservationAvailableToTime() {
        return this.reservationAvailableToTime;
    }

    public Room reservationAvailableToTime(String reservationAvailableToTime) {
        this.setReservationAvailableToTime(reservationAvailableToTime);
        return this;
    }

    public void setReservationAvailableToTime(String reservationAvailableToTime) {
        this.reservationAvailableToTime = reservationAvailableToTime;
    }

    public Boolean getUseApproval() {
        return this.useApproval;
    }

    public Room useApproval(Boolean useApproval) {
        this.setUseApproval(useApproval);
        return this;
    }

    public void setUseApproval(Boolean useApproval) {
        this.useApproval = useApproval;
    }

    public Boolean getUseUserAvailable() {
        return this.useUserAvailable;
    }

    public Room useUserAvailable(Boolean useUserAvailable) {
        this.setUseUserAvailable(useUserAvailable);
        return this;
    }

    public void setUseUserAvailable(Boolean useUserAvailable) {
        this.useUserAvailable = useUserAvailable;
    }

    public Boolean getUseCheckInOut() {
        return this.useCheckInOut;
    }

    public Room useCheckInOut(Boolean useCheckInOut) {
        this.setUseCheckInOut(useCheckInOut);
        return this;
    }

    public void setUseCheckInOut(Boolean useCheckInOut) {
        this.useCheckInOut = useCheckInOut;
    }

    public String getCheckInOutFromTime() {
        return this.checkInOutFromTime;
    }

    public Room checkInOutFromTime(String checkInOutFromTime) {
        this.setCheckInOutFromTime(checkInOutFromTime);
        return this;
    }

    public void setCheckInOutFromTime(String checkInOutFromTime) {
        this.checkInOutFromTime = checkInOutFromTime;
    }

    public String getCheckInOutToTime() {
        return this.checkInOutToTime;
    }

    public Room checkInOutToTime(String checkInOutToTime) {
        this.setCheckInOutToTime(checkInOutToTime);
        return this;
    }

    public void setCheckInOutToTime(String checkInOutToTime) {
        this.checkInOutToTime = checkInOutToTime;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public Room valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Set<RoomManager> getRoomManagers() {
        return this.roomManagers;
    }

    public void setRoomManagers(Set<RoomManager> roomManagers) {
        if (this.roomManagers != null) {
            this.roomManagers.forEach(i -> i.setRoom(null));
        }
        if (roomManagers != null) {
            roomManagers.forEach(i -> i.setRoom(this));
        }
        this.roomManagers = roomManagers;
    }

    public Room roomManagers(Set<RoomManager> roomManagers) {
        this.setRoomManagers(roomManagers);
        return this;
    }

    public Room addRoomManagers(RoomManager roomManager) {
        this.roomManagers.add(roomManager);
        roomManager.setRoom(this);
        return this;
    }

    public Room removeRoomManagers(RoomManager roomManager) {
        this.roomManagers.remove(roomManager);
        roomManager.setRoom(null);
        return this;
    }

    public Set<RoomUserGroup> getRoomUserGroups() {
        return this.roomUserGroups;
    }

    public void setRoomUserGroups(Set<RoomUserGroup> roomUserGroups) {
        if (this.roomUserGroups != null) {
            this.roomUserGroups.forEach(i -> i.setRoom(null));
        }
        if (roomUserGroups != null) {
            roomUserGroups.forEach(i -> i.setRoom(this));
        }
        this.roomUserGroups = roomUserGroups;
    }

    public Room roomUserGroups(Set<RoomUserGroup> roomUserGroups) {
        this.setRoomUserGroups(roomUserGroups);
        return this;
    }

    public Room addRoomUserGroups(RoomUserGroup roomUserGroup) {
        this.roomUserGroups.add(roomUserGroup);
        roomUserGroup.setRoom(this);
        return this;
    }

    public Room removeRoomUserGroups(RoomUserGroup roomUserGroup) {
        this.roomUserGroups.remove(roomUserGroup);
        roomUserGroup.setRoom(null);
        return this;
    }

    public Set<RoomSeat> getRoomSeats() {
        return this.roomSeats;
    }

    public void setRoomSeats(Set<RoomSeat> roomSeats) {
        if (this.roomSeats != null) {
            this.roomSeats.forEach(i -> i.setRoom(null));
        }
        if (roomSeats != null) {
            roomSeats.forEach(i -> i.setRoom(this));
        }
        this.roomSeats = roomSeats;
    }

    public Room roomSeats(Set<RoomSeat> roomSeats) {
        this.setRoomSeats(roomSeats);
        return this;
    }

    public Room addRoomSeats(RoomSeat roomSeat) {
        this.roomSeats.add(roomSeat);
        roomSeat.setRoom(this);
        return this;
    }

    public Room removeRoomSeats(RoomSeat roomSeat) {
        this.roomSeats.remove(roomSeat);
        roomSeat.setRoom(null);
        return this;
    }

    public Set<AttributeRoom> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<AttributeRoom> attributeRooms) {
        if (this.attributes != null) {
            this.attributes.forEach(i -> i.setRoom(null));
        }
        if (attributeRooms != null) {
            attributeRooms.forEach(i -> i.setRoom(this));
        }
        this.attributes = attributeRooms;
    }

    public Room attributes(Set<AttributeRoom> attributeRooms) {
        this.setAttributes(attributeRooms);
        return this;
    }

    public Room addAttributes(AttributeRoom attributeRoom) {
        this.attributes.add(attributeRoom);
        attributeRoom.setRoom(this);
        return this;
    }

    public Room removeAttributes(AttributeRoom attributeRoom) {
        this.attributes.remove(attributeRoom);
        attributeRoom.setRoom(null);
        return this;
    }

    public Fileinfo getImage() {
        return this.image;
    }

    public void setImage(Fileinfo fileinfo) {
        this.image = fileinfo;
    }

    public Room image(Fileinfo fileinfo) {
        this.setImage(fileinfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return id != null && id.equals(((Room) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", officeId=" + getOfficeId() +
            ", floorId=" + getFloorId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", personnel=" + getPersonnel() +
            ", top=" + getTop() +
            ", left=" + getLeft() +
            ", useReservationTime='" + getUseReservationTime() + "'" +
            ", reservationAvailableFromTime='" + getReservationAvailableFromTime() + "'" +
            ", reservationAvailableToTime='" + getReservationAvailableToTime() + "'" +
            ", useApproval='" + getUseApproval() + "'" +
            ", useUserAvailable='" + getUseUserAvailable() + "'" +
            ", useCheckInOut='" + getUseCheckInOut() + "'" +
            ", checkInOutFromTime='" + getCheckInOutFromTime() + "'" +
            ", checkInOutToTime='" + getCheckInOutToTime() + "'" +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
