package com.atensys.service.dto;

import com.atensys.domain.enumeration.SeatSubType;
import com.atensys.domain.enumeration.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Seat} entity.
 */
@Schema(description = "좌석")
public class SeatDTO implements Serializable {

    private Long id;

    /**
     * 건물
     */
    @NotNull
    @Schema(description = "건물", required = true)
    private Long officeId;

    /**
     * 층
     */
    @NotNull
    @Schema(description = "층", required = true)
    private Long floorId;

    /**
     * 좌석유형
     */
    @NotNull
    @Schema(description = "좌석유형", required = true)
    private SeatType seatType;

    /**
     * 좌석명
     */
    @NotNull
    @Size(max = 20)
    @Schema(description = "좌석명", required = true)
    private String seatName;

    /**
     * 좌석부가유형
     */
    @NotNull
    @Schema(description = "좌석부가유형", required = true)
    private SeatSubType seatSubType;

    /**
     * 사용가능여부
     */
    @NotNull
    @Schema(description = "사용가능여부", required = true)
    private Boolean useYn;

    /**
     * Y좌표
     */
    @Min(value = 0L)
    @Schema(description = "Y좌표")
    private Long top;

    /**
     * X좌표
     */
    @Min(value = 0L)
    @Schema(description = "X좌표")
    private Long left;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public SeatSubType getSeatSubType() {
        return seatSubType;
    }

    public void setSeatSubType(SeatSubType seatSubType) {
        this.seatSubType = seatSubType;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public Long getTop() {
        return top;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeatDTO)) {
            return false;
        }

        SeatDTO seatDTO = (SeatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, seatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeatDTO{" +
            "id=" + getId() +
            ", officeId=" + getOfficeId() +
            ", floorId=" + getFloorId() +
            ", seatType='" + getSeatType() + "'" +
            ", seatName='" + getSeatName() + "'" +
            ", seatSubType='" + getSeatSubType() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", top=" + getTop() +
            ", left=" + getLeft() +
            "}";
    }
}
