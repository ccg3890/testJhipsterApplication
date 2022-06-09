package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 예약일시체크를 위한 예약일
 */
@Entity
@Table(name = "tb_reserveddate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReservedDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 예약일시(시작)
     */
    @NotNull
    @Column(name = "start_date_time", nullable = false)
    private LocalDate startDateTime;

    /**
     * 예약일시(종료)
     */
    @NotNull
    @Column(name = "end_date_time", nullable = false)
    private LocalDate endDateTime;

    /**
     * 반복 예약으로 발생된 회차번호
     */
    @Column(name = "step")
    private Integer step;

    @ManyToOne
    @JsonIgnoreProperties(value = { "recurrence", "reservationTargets", "reservedDates" }, allowSetters = true)
    private Reservation reservation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReservedDate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDateTime() {
        return this.startDateTime;
    }

    public ReservedDate startDateTime(LocalDate startDateTime) {
        this.setStartDateTime(startDateTime);
        return this;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDate getEndDateTime() {
        return this.endDateTime;
    }

    public ReservedDate endDateTime(LocalDate endDateTime) {
        this.setEndDateTime(endDateTime);
        return this;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getStep() {
        return this.step;
    }

    public ReservedDate step(Integer step) {
        this.setStep(step);
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public ReservedDate reservation(Reservation reservation) {
        this.setReservation(reservation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservedDate)) {
            return false;
        }
        return id != null && id.equals(((ReservedDate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservedDate{" +
            "id=" + getId() +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", step=" + getStep() +
            "}";
    }
}
