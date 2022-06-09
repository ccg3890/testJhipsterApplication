package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Room} entity.
 */
@Schema(description = "회의실")
public class RoomDTO implements Serializable {

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
     * 회의실명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "회의실명", required = true)
    private String name;

    /**
     * 회의실 설명
     */
    @Size(max = 1024)
    @Schema(description = "회의실 설명")
    private String description;

    /**
     * 최대예약시간(분)
     */
    @Max(value = 1420L)
    @Schema(description = "최대예약시간(분)")
    private Long personnel;

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

    /**
     * 예약가능시간 사용여부
     */
    @NotNull
    @Schema(description = "예약가능시간 사용여부", required = true)
    private Boolean useReservationTime;

    /**
     * 예약가능 시간(from)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Schema(description = "예약가능 시간(from)")
    private String reservationAvailableFromTime;

    /**
     * 예약가능 시간(to)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Schema(description = "예약가능 시간(to)")
    private String reservationAvailableToTime;

    /**
     * 예약관리여부
     */
    @NotNull
    @Schema(description = "예약관리여부", required = true)
    private Boolean useApproval;

    /**
     * 사용자예약여부
     */
    @NotNull
    @Schema(description = "사용자예약여부", required = true)
    private Boolean useUserAvailable;

    /**
     * 최대예약시간사용여부
     */
    @NotNull
    @Schema(description = "최대예약시간사용여부", required = true)
    private Boolean useCheckInOut;

    /**
     * 입퇴실관리 시간(from)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Schema(description = "입퇴실관리 시간(from)")
    private String checkInOutFromTime;

    /**
     * 입퇴실관리 시간(to)
     */
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])+$")
    @Schema(description = "입퇴실관리 시간(to)")
    private String checkInOutToTime;

    /**
     * 사용가능여부
     */
    @NotNull
    @Schema(description = "사용가능여부", required = true)
    private Boolean valid;

    private FileinfoDTO image;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Long personnel) {
        this.personnel = personnel;
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

    public Boolean getUseReservationTime() {
        return useReservationTime;
    }

    public void setUseReservationTime(Boolean useReservationTime) {
        this.useReservationTime = useReservationTime;
    }

    public String getReservationAvailableFromTime() {
        return reservationAvailableFromTime;
    }

    public void setReservationAvailableFromTime(String reservationAvailableFromTime) {
        this.reservationAvailableFromTime = reservationAvailableFromTime;
    }

    public String getReservationAvailableToTime() {
        return reservationAvailableToTime;
    }

    public void setReservationAvailableToTime(String reservationAvailableToTime) {
        this.reservationAvailableToTime = reservationAvailableToTime;
    }

    public Boolean getUseApproval() {
        return useApproval;
    }

    public void setUseApproval(Boolean useApproval) {
        this.useApproval = useApproval;
    }

    public Boolean getUseUserAvailable() {
        return useUserAvailable;
    }

    public void setUseUserAvailable(Boolean useUserAvailable) {
        this.useUserAvailable = useUserAvailable;
    }

    public Boolean getUseCheckInOut() {
        return useCheckInOut;
    }

    public void setUseCheckInOut(Boolean useCheckInOut) {
        this.useCheckInOut = useCheckInOut;
    }

    public String getCheckInOutFromTime() {
        return checkInOutFromTime;
    }

    public void setCheckInOutFromTime(String checkInOutFromTime) {
        this.checkInOutFromTime = checkInOutFromTime;
    }

    public String getCheckInOutToTime() {
        return checkInOutToTime;
    }

    public void setCheckInOutToTime(String checkInOutToTime) {
        this.checkInOutToTime = checkInOutToTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public FileinfoDTO getImage() {
        return image;
    }

    public void setImage(FileinfoDTO image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomDTO)) {
            return false;
        }

        RoomDTO roomDTO = (RoomDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomDTO{" +
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
            ", image=" + getImage() +
            "}";
    }
}
