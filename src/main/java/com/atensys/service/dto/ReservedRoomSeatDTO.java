package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.ReservedRoomSeat} entity.
 */
@Schema(description = "회의실 예약 좌석지정")
public class ReservedRoomSeatDTO implements Serializable {

    private Long id;

    /**
     * 참석자명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "참석자명", required = true)
    private String displayName;

    /**
     * 참석회사명
     */
    @Size(max = 64)
    @Schema(description = "참석회사명")
    private String companyName;

    private ReservationTargetDTO reservationTarget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ReservationTargetDTO getReservationTarget() {
        return reservationTarget;
    }

    public void setReservationTarget(ReservationTargetDTO reservationTarget) {
        this.reservationTarget = reservationTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservedRoomSeatDTO)) {
            return false;
        }

        ReservedRoomSeatDTO reservedRoomSeatDTO = (ReservedRoomSeatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservedRoomSeatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservedRoomSeatDTO{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", reservationTarget=" + getReservationTarget() +
            "}";
    }
}
