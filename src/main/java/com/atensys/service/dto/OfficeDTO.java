package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Office} entity.
 */
@Schema(description = "오피스")
public class OfficeDTO implements Serializable {

    private Long id;

    /**
     * 오피스 이름
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "오피스 이름", required = true)
    private String officeName;

    /**
     * 별칭
     */
    @Size(max = 50)
    @Schema(description = "별칭")
    private String alias;

    /**
     * 별칭 접두사
     */
    @Size(max = 50)
    @Schema(description = "별칭 접두사")
    private String aliasPrefix;

    /**
     * 별칭 접미사
     */
    @Size(max = 50)
    @Schema(description = "별칭 접미사")
    private String aliasSuffix;

    /**
     * 근무시간 설정여부
     */
    @Schema(description = "근무시간 설정여부")
    private Boolean workTimeConfigYn;

    /**
     * 근무시작시간
     */
    @Pattern(regexp = "^([01][0-9]|2[0-3])([0-5][0-9])+$")
    @Schema(description = "근무시작시간")
    private String workStartTime;

    /**
     * 근무종료시간
     */
    @Pattern(regexp = "^([01][0-9]|2[0-3])([0-5][0-9])+$")
    @Schema(description = "근무종료시간")
    private String workEndTime;

    /**
     * 노쇼방지 사용여부 (Y or N)
     */
    @Schema(description = "노쇼방지 사용여부 (Y or N)")
    private Boolean noShowYn;

    /**
     * 좌석예약 노쇼방지 여부
     */
    @Schema(description = "좌석예약 노쇼방지 여부")
    private Boolean noShowPenaltyYn;

    /**
     * 노쇼 타이머(분)
     */
    @Schema(description = "노쇼 타이머(분)")
    private Integer noShowTimerMinutes;

    /**
     * 노쇼 패널티 기간
     */
    @Schema(description = "노쇼 패널티 기간")
    private Integer noShowPenaltyDays;

    /**
     * 예약신청 알림 여부
     */
    @Schema(description = "예약신청 알림 여부")
    private Boolean applyReservationAlarmYn;

    /**
     * 예약신청 알림 예시문
     */
    @Size(max = 2000)
    @Schema(description = "예약신청 알림 예시문")
    private String applyReservationAlarmTemplate;

    /**
     * 사전입실 알림 여부
     */
    @Schema(description = "사전입실 알림 여부")
    private Boolean preEntranceAlarmYn;

    /**
     * 사전입실 알림 간격(분)
     */
    @Schema(description = "사전입실 알림 간격(분)")
    private Integer preEntranceAlarmInterval;

    /**
     * 사전입실 알림 예시문
     */
    @Size(max = 2000)
    @Schema(description = "사전입실 알림 예시문")
    private String preEntranceAlarmTemplate;

    /**
     * 노쇼 알림 여부
     */
    @Schema(description = "노쇼 알림 여부")
    private Boolean noShowAlarmYn;

    /**
     * 노쇼 알림 예시문
     */
    @Size(max = 2000)
    @Schema(description = "노쇼 알림 예시문")
    private String noShowAlarmTemplate;

    /**
     * Y or N
     */
    @Schema(description = "Y or N")
    private Boolean exclusive;

    /**
     * 복무정보적용여부 (적용: Y, 미적용: N)
     */
    @Schema(description = "복무정보적용여부 (적용: Y, 미적용: N)")
    private Boolean useDutyType;

    /**
     * 임직원검색 사용여부 (Y or N)
     */
    @Schema(description = "임직원검색 사용여부 (Y or N)")
    private Boolean useUserSearch;

    /**
     * Y or N
     */
    @Schema(description = "Y or N")
    private Boolean kioskZoomEnable;

    /**
     * 예약제한일수 (기본값: 0 무제한)
     */
    @Schema(description = "예약제한일수 (기본값: 0 무제한)")
    private Integer reservationLimitDay;

    /**
     * 일반 사용자 이용가능 좌석타입들 (see: tb_seat.seat_type)
     */
    @Size(max = 100)
    @Schema(description = "일반 사용자 이용가능 좌석타입들 (see: tb_seat.seat_type)")
    private String availableSeatType;

    /**
     * 자동 체크인 적용여부 (적용: Y, 미적용: N)
     */
    @Schema(description = "자동 체크인 적용여부 (적용: Y, 미적용: N)")
    private Boolean autoCheckInYn;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliasPrefix() {
        return aliasPrefix;
    }

    public void setAliasPrefix(String aliasPrefix) {
        this.aliasPrefix = aliasPrefix;
    }

    public String getAliasSuffix() {
        return aliasSuffix;
    }

    public void setAliasSuffix(String aliasSuffix) {
        this.aliasSuffix = aliasSuffix;
    }

    public Boolean getWorkTimeConfigYn() {
        return workTimeConfigYn;
    }

    public void setWorkTimeConfigYn(Boolean workTimeConfigYn) {
        this.workTimeConfigYn = workTimeConfigYn;
    }

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
    }

    public Boolean getNoShowYn() {
        return noShowYn;
    }

    public void setNoShowYn(Boolean noShowYn) {
        this.noShowYn = noShowYn;
    }

    public Boolean getNoShowPenaltyYn() {
        return noShowPenaltyYn;
    }

    public void setNoShowPenaltyYn(Boolean noShowPenaltyYn) {
        this.noShowPenaltyYn = noShowPenaltyYn;
    }

    public Integer getNoShowTimerMinutes() {
        return noShowTimerMinutes;
    }

    public void setNoShowTimerMinutes(Integer noShowTimerMinutes) {
        this.noShowTimerMinutes = noShowTimerMinutes;
    }

    public Integer getNoShowPenaltyDays() {
        return noShowPenaltyDays;
    }

    public void setNoShowPenaltyDays(Integer noShowPenaltyDays) {
        this.noShowPenaltyDays = noShowPenaltyDays;
    }

    public Boolean getApplyReservationAlarmYn() {
        return applyReservationAlarmYn;
    }

    public void setApplyReservationAlarmYn(Boolean applyReservationAlarmYn) {
        this.applyReservationAlarmYn = applyReservationAlarmYn;
    }

    public String getApplyReservationAlarmTemplate() {
        return applyReservationAlarmTemplate;
    }

    public void setApplyReservationAlarmTemplate(String applyReservationAlarmTemplate) {
        this.applyReservationAlarmTemplate = applyReservationAlarmTemplate;
    }

    public Boolean getPreEntranceAlarmYn() {
        return preEntranceAlarmYn;
    }

    public void setPreEntranceAlarmYn(Boolean preEntranceAlarmYn) {
        this.preEntranceAlarmYn = preEntranceAlarmYn;
    }

    public Integer getPreEntranceAlarmInterval() {
        return preEntranceAlarmInterval;
    }

    public void setPreEntranceAlarmInterval(Integer preEntranceAlarmInterval) {
        this.preEntranceAlarmInterval = preEntranceAlarmInterval;
    }

    public String getPreEntranceAlarmTemplate() {
        return preEntranceAlarmTemplate;
    }

    public void setPreEntranceAlarmTemplate(String preEntranceAlarmTemplate) {
        this.preEntranceAlarmTemplate = preEntranceAlarmTemplate;
    }

    public Boolean getNoShowAlarmYn() {
        return noShowAlarmYn;
    }

    public void setNoShowAlarmYn(Boolean noShowAlarmYn) {
        this.noShowAlarmYn = noShowAlarmYn;
    }

    public String getNoShowAlarmTemplate() {
        return noShowAlarmTemplate;
    }

    public void setNoShowAlarmTemplate(String noShowAlarmTemplate) {
        this.noShowAlarmTemplate = noShowAlarmTemplate;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public Boolean getUseDutyType() {
        return useDutyType;
    }

    public void setUseDutyType(Boolean useDutyType) {
        this.useDutyType = useDutyType;
    }

    public Boolean getUseUserSearch() {
        return useUserSearch;
    }

    public void setUseUserSearch(Boolean useUserSearch) {
        this.useUserSearch = useUserSearch;
    }

    public Boolean getKioskZoomEnable() {
        return kioskZoomEnable;
    }

    public void setKioskZoomEnable(Boolean kioskZoomEnable) {
        this.kioskZoomEnable = kioskZoomEnable;
    }

    public Integer getReservationLimitDay() {
        return reservationLimitDay;
    }

    public void setReservationLimitDay(Integer reservationLimitDay) {
        this.reservationLimitDay = reservationLimitDay;
    }

    public String getAvailableSeatType() {
        return availableSeatType;
    }

    public void setAvailableSeatType(String availableSeatType) {
        this.availableSeatType = availableSeatType;
    }

    public Boolean getAutoCheckInYn() {
        return autoCheckInYn;
    }

    public void setAutoCheckInYn(Boolean autoCheckInYn) {
        this.autoCheckInYn = autoCheckInYn;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeDTO)) {
            return false;
        }

        OfficeDTO officeDTO = (OfficeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, officeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficeDTO{" +
            "id=" + getId() +
            ", officeName='" + getOfficeName() + "'" +
            ", alias='" + getAlias() + "'" +
            ", aliasPrefix='" + getAliasPrefix() + "'" +
            ", aliasSuffix='" + getAliasSuffix() + "'" +
            ", workTimeConfigYn='" + getWorkTimeConfigYn() + "'" +
            ", workStartTime='" + getWorkStartTime() + "'" +
            ", workEndTime='" + getWorkEndTime() + "'" +
            ", noShowYn='" + getNoShowYn() + "'" +
            ", noShowPenaltyYn='" + getNoShowPenaltyYn() + "'" +
            ", noShowTimerMinutes=" + getNoShowTimerMinutes() +
            ", noShowPenaltyDays=" + getNoShowPenaltyDays() +
            ", applyReservationAlarmYn='" + getApplyReservationAlarmYn() + "'" +
            ", applyReservationAlarmTemplate='" + getApplyReservationAlarmTemplate() + "'" +
            ", preEntranceAlarmYn='" + getPreEntranceAlarmYn() + "'" +
            ", preEntranceAlarmInterval=" + getPreEntranceAlarmInterval() +
            ", preEntranceAlarmTemplate='" + getPreEntranceAlarmTemplate() + "'" +
            ", noShowAlarmYn='" + getNoShowAlarmYn() + "'" +
            ", noShowAlarmTemplate='" + getNoShowAlarmTemplate() + "'" +
            ", exclusive='" + getExclusive() + "'" +
            ", useDutyType='" + getUseDutyType() + "'" +
            ", useUserSearch='" + getUseUserSearch() + "'" +
            ", kioskZoomEnable='" + getKioskZoomEnable() + "'" +
            ", reservationLimitDay=" + getReservationLimitDay() +
            ", availableSeatType='" + getAvailableSeatType() + "'" +
            ", autoCheckInYn='" + getAutoCheckInYn() + "'" +
            ", company=" + getCompany() +
            "}";
    }
}
