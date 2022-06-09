package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Penalty} entity.
 */
@Schema(description = "페널티")
public class PenaltyDTO implements Serializable {

    private Long id;

    /**
     * 사용자ID
     */
    @Size(max = 20)
    @Schema(description = "사용자ID")
    private String userId;

    /**
     * 오피스ID
     */
    @Size(max = 20)
    @Schema(description = "오피스ID")
    private String officeId;

    /**
     * 좌석예약 제한 시작일
     */
    @Schema(description = "좌석예약 제한 시작일")
    private LocalDate startDate;

    /**
     * 좌석예약 제한 종료일
     */
    @Schema(description = "좌석예약 제한 종료일")
    private LocalDate endDate;

    private UserInfoDTO userInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PenaltyDTO)) {
            return false;
        }

        PenaltyDTO penaltyDTO = (PenaltyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, penaltyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PenaltyDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", officeId='" + getOfficeId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", userInfo=" + getUserInfo() +
            "}";
    }
}
