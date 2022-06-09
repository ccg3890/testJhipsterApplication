package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.ReservedDate} entity.
 */
@Schema(description = "예약일시체크를 위한 예약일")
public class ReservedDateDTO implements Serializable {

    private Long id;

    /**
     * 예약일시(시작)
     */
    @NotNull
    @Schema(description = "예약일시(시작)", required = true)
    private LocalDate startDateTime;

    /**
     * 예약일시(종료)
     */
    @NotNull
    @Schema(description = "예약일시(종료)", required = true)
    private LocalDate endDateTime;

    /**
     * 반복 예약으로 발생된 회차번호
     */
    @Schema(description = "반복 예약으로 발생된 회차번호")
    private Integer step;

    private ReservationDTO reservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDate getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservedDateDTO)) {
            return false;
        }

        ReservedDateDTO reservedDateDTO = (ReservedDateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservedDateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservedDateDTO{" +
            "id=" + getId() +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", step=" + getStep() +
            ", reservation=" + getReservation() +
            "}";
    }
}
