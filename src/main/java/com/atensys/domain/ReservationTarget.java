package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 예약대상(회의실, 좌석, 등등)
 */
@Entity
@Table(name = "tb_reservationtarget")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReservationTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "floor" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Resource resource;

    @OneToMany(mappedBy = "reservationTarget")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reservationTarget" }, allowSetters = true)
    private Set<Attendee> attendees = new HashSet<>();

    @OneToMany(mappedBy = "reservationTarget")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reservationTarget" }, allowSetters = true)
    private Set<ReservedRoomSeat> reservedRoomSeats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "recurrence", "reservationTargets", "reservedDates" }, allowSetters = true)
    private Reservation reservation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReservationTarget id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ReservationTarget resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    public Set<Attendee> getAttendees() {
        return this.attendees;
    }

    public void setAttendees(Set<Attendee> attendees) {
        if (this.attendees != null) {
            this.attendees.forEach(i -> i.setReservationTarget(null));
        }
        if (attendees != null) {
            attendees.forEach(i -> i.setReservationTarget(this));
        }
        this.attendees = attendees;
    }

    public ReservationTarget attendees(Set<Attendee> attendees) {
        this.setAttendees(attendees);
        return this;
    }

    public ReservationTarget addAttendees(Attendee attendee) {
        this.attendees.add(attendee);
        attendee.setReservationTarget(this);
        return this;
    }

    public ReservationTarget removeAttendees(Attendee attendee) {
        this.attendees.remove(attendee);
        attendee.setReservationTarget(null);
        return this;
    }

    public Set<ReservedRoomSeat> getReservedRoomSeats() {
        return this.reservedRoomSeats;
    }

    public void setReservedRoomSeats(Set<ReservedRoomSeat> reservedRoomSeats) {
        if (this.reservedRoomSeats != null) {
            this.reservedRoomSeats.forEach(i -> i.setReservationTarget(null));
        }
        if (reservedRoomSeats != null) {
            reservedRoomSeats.forEach(i -> i.setReservationTarget(this));
        }
        this.reservedRoomSeats = reservedRoomSeats;
    }

    public ReservationTarget reservedRoomSeats(Set<ReservedRoomSeat> reservedRoomSeats) {
        this.setReservedRoomSeats(reservedRoomSeats);
        return this;
    }

    public ReservationTarget addReservedRoomSeats(ReservedRoomSeat reservedRoomSeat) {
        this.reservedRoomSeats.add(reservedRoomSeat);
        reservedRoomSeat.setReservationTarget(this);
        return this;
    }

    public ReservationTarget removeReservedRoomSeats(ReservedRoomSeat reservedRoomSeat) {
        this.reservedRoomSeats.remove(reservedRoomSeat);
        reservedRoomSeat.setReservationTarget(null);
        return this;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public ReservationTarget reservation(Reservation reservation) {
        this.setReservation(reservation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationTarget)) {
            return false;
        }
        return id != null && id.equals(((ReservationTarget) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationTarget{" +
            "id=" + getId() +
            "}";
    }
}
