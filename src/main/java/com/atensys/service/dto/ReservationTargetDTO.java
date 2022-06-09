package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atensys.domain.ReservationTarget} entity.
 */
@Schema(description = "예약대상(회의실, 좌석, 등등)")
public class ReservationTargetDTO implements Serializable {

    private Long id;

    private ResourceDTO resource;

    private ReservationDTO reservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
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
        if (!(o instanceof ReservationTargetDTO)) {
            return false;
        }

        ReservationTargetDTO reservationTargetDTO = (ReservationTargetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservationTargetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationTargetDTO{" +
            "id=" + getId() +
            ", resource=" + getResource() +
            ", reservation=" + getReservation() +
            "}";
    }
}
