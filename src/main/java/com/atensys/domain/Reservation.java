package com.atensys.domain;

import com.atensys.domain.enumeration.ResvStatusType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 예약정보
 */
@Entity
@Table(name = "tb_reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 예약명
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "title", length = 128, nullable = false)
    private String title;

    /**
     * 예약설명
     */
    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    /**
     * 예약상태
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ResvStatusType status;

    /**
     * 주최자ID
     */
    @NotNull
    @Size(max = 32)
    @Column(name = "organizer_id", length = 32, nullable = false)
    private String organizerId;

    /**
     * 주최자명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "organizer_name", length = 64, nullable = false)
    private String organizerName;

    /**
     * 예약시작일시
     */
    @NotNull
    @Column(name = "start_date_time", nullable = false)
    private LocalDate startDateTime;

    /**
     * 예약종료일시
     */
    @NotNull
    @Column(name = "end_date_time", nullable = false)
    private LocalDate endDateTime;

    /**
     * 온종일 사용 여부
     */
    @NotNull
    @Column(name = "use_all_day", nullable = false)
    private Boolean useAllDay;

    /**
     * 반복사용여부
     */
    @NotNull
    @Column(name = "use_recurrence", nullable = false)
    private Boolean useRecurrence;

    /**
     * 참석여부응답 사용여부
     */
    @NotNull
    @Column(name = "use_response", nullable = false)
    private Boolean useResponse;

    /**
     * 회의실 PIN 번호
     */
    @Size(max = 128)
    @Column(name = "pin", length = 128)
    private String pin;

    @OneToOne
    @JoinColumn(unique = true)
    private Recurrence recurrence;

    @OneToMany(mappedBy = "reservation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource", "attendees", "reservedRoomSeats", "reservation" }, allowSetters = true)
    private Set<ReservationTarget> reservationTargets = new HashSet<>();

    @OneToMany(mappedBy = "reservation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reservation" }, allowSetters = true)
    private Set<ReservedDate> reservedDates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Reservation title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Reservation description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResvStatusType getStatus() {
        return this.status;
    }

    public Reservation status(ResvStatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ResvStatusType status) {
        this.status = status;
    }

    public String getOrganizerId() {
        return this.organizerId;
    }

    public Reservation organizerId(String organizerId) {
        this.setOrganizerId(organizerId);
        return this;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizerName() {
        return this.organizerName;
    }

    public Reservation organizerName(String organizerName) {
        this.setOrganizerName(organizerName);
        return this;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public LocalDate getStartDateTime() {
        return this.startDateTime;
    }

    public Reservation startDateTime(LocalDate startDateTime) {
        this.setStartDateTime(startDateTime);
        return this;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDate getEndDateTime() {
        return this.endDateTime;
    }

    public Reservation endDateTime(LocalDate endDateTime) {
        this.setEndDateTime(endDateTime);
        return this;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean getUseAllDay() {
        return this.useAllDay;
    }

    public Reservation useAllDay(Boolean useAllDay) {
        this.setUseAllDay(useAllDay);
        return this;
    }

    public void setUseAllDay(Boolean useAllDay) {
        this.useAllDay = useAllDay;
    }

    public Boolean getUseRecurrence() {
        return this.useRecurrence;
    }

    public Reservation useRecurrence(Boolean useRecurrence) {
        this.setUseRecurrence(useRecurrence);
        return this;
    }

    public void setUseRecurrence(Boolean useRecurrence) {
        this.useRecurrence = useRecurrence;
    }

    public Boolean getUseResponse() {
        return this.useResponse;
    }

    public Reservation useResponse(Boolean useResponse) {
        this.setUseResponse(useResponse);
        return this;
    }

    public void setUseResponse(Boolean useResponse) {
        this.useResponse = useResponse;
    }

    public String getPin() {
        return this.pin;
    }

    public Reservation pin(String pin) {
        this.setPin(pin);
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Recurrence getRecurrence() {
        return this.recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Reservation recurrence(Recurrence recurrence) {
        this.setRecurrence(recurrence);
        return this;
    }

    public Set<ReservationTarget> getReservationTargets() {
        return this.reservationTargets;
    }

    public void setReservationTargets(Set<ReservationTarget> reservationTargets) {
        if (this.reservationTargets != null) {
            this.reservationTargets.forEach(i -> i.setReservation(null));
        }
        if (reservationTargets != null) {
            reservationTargets.forEach(i -> i.setReservation(this));
        }
        this.reservationTargets = reservationTargets;
    }

    public Reservation reservationTargets(Set<ReservationTarget> reservationTargets) {
        this.setReservationTargets(reservationTargets);
        return this;
    }

    public Reservation addReservationTargets(ReservationTarget reservationTarget) {
        this.reservationTargets.add(reservationTarget);
        reservationTarget.setReservation(this);
        return this;
    }

    public Reservation removeReservationTargets(ReservationTarget reservationTarget) {
        this.reservationTargets.remove(reservationTarget);
        reservationTarget.setReservation(null);
        return this;
    }

    public Set<ReservedDate> getReservedDates() {
        return this.reservedDates;
    }

    public void setReservedDates(Set<ReservedDate> reservedDates) {
        if (this.reservedDates != null) {
            this.reservedDates.forEach(i -> i.setReservation(null));
        }
        if (reservedDates != null) {
            reservedDates.forEach(i -> i.setReservation(this));
        }
        this.reservedDates = reservedDates;
    }

    public Reservation reservedDates(Set<ReservedDate> reservedDates) {
        this.setReservedDates(reservedDates);
        return this;
    }

    public Reservation addReservedDates(ReservedDate reservedDate) {
        this.reservedDates.add(reservedDate);
        reservedDate.setReservation(this);
        return this;
    }

    public Reservation removeReservedDates(ReservedDate reservedDate) {
        this.reservedDates.remove(reservedDate);
        reservedDate.setReservation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return id != null && id.equals(((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", organizerId='" + getOrganizerId() + "'" +
            ", organizerName='" + getOrganizerName() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", useAllDay='" + getUseAllDay() + "'" +
            ", useRecurrence='" + getUseRecurrence() + "'" +
            ", useResponse='" + getUseResponse() + "'" +
            ", pin='" + getPin() + "'" +
            "}";
    }
}
