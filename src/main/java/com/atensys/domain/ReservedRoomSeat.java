package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 회의실 예약 좌석지정
 */
@Entity
@Table(name = "tb_reservedroomseat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReservedRoomSeat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 참석자명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "display_name", length = 64, nullable = false)
    private String displayName;

    /**
     * 참석회사명
     */
    @Size(max = 64)
    @Column(name = "company_name", length = 64)
    private String companyName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "resource", "attendees", "reservedRoomSeats", "reservation" }, allowSetters = true)
    private ReservationTarget reservationTarget;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReservedRoomSeat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ReservedRoomSeat displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public ReservedRoomSeat companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ReservationTarget getReservationTarget() {
        return this.reservationTarget;
    }

    public void setReservationTarget(ReservationTarget reservationTarget) {
        this.reservationTarget = reservationTarget;
    }

    public ReservedRoomSeat reservationTarget(ReservationTarget reservationTarget) {
        this.setReservationTarget(reservationTarget);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservedRoomSeat)) {
            return false;
        }
        return id != null && id.equals(((ReservedRoomSeat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservedRoomSeat{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
