package com.atensys.service.dto;

import com.atensys.domain.enumeration.AttendeeResponseStatus;
import com.atensys.domain.enumeration.AttendeeType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Attendee} entity.
 */
@Schema(description = "참석자")
public class AttendeeDTO implements Serializable {

    private Long id;

    /**
     * 참석자유형
     */
    @NotNull
    @Schema(description = "참석자유형", required = true)
    private AttendeeType attendeeType;

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

    /**
     * 참석자ID(사내참석자)
     */
    @Size(max = 32)
    @Schema(description = "참석자ID(사내참석자)")
    private String attendeeId;

    /**
     * 휴대전화번호
     */
    @NotNull
    @Size(max = 32)
    @Schema(description = "휴대전화번호", required = true)
    private String mobileNo;

    /**
     * 이메일
     */
    @Size(max = 128)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Schema(description = "이메일")
    private String email;

    /**
     * 참석확인제외 여부
     */
    @Schema(description = "참석확인제외 여부")
    private Boolean optional;

    /**
     * 참석자여부응답 코멘트
     */
    @Size(max = 256)
    @Schema(description = "참석자여부응답 코멘트")
    private String comment;

    /**
     * 참석여부응답상태
     */
    @Schema(description = "참석여부응답상태")
    private AttendeeResponseStatus responseStatus;

    private ReservationTargetDTO reservationTarget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendeeType getAttendeeType() {
        return attendeeType;
    }

    public void setAttendeeType(AttendeeType attendeeType) {
        this.attendeeType = attendeeType;
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

    public String getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(String attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AttendeeResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(AttendeeResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
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
        if (!(o instanceof AttendeeDTO)) {
            return false;
        }

        AttendeeDTO attendeeDTO = (AttendeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attendeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendeeDTO{" +
            "id=" + getId() +
            ", attendeeType='" + getAttendeeType() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", attendeeId='" + getAttendeeId() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", optional='" + getOptional() + "'" +
            ", comment='" + getComment() + "'" +
            ", responseStatus='" + getResponseStatus() + "'" +
            ", reservationTarget=" + getReservationTarget() +
            "}";
    }
}
