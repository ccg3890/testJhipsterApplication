package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 오피스
 */
@Entity
@Table(name = "tb_office")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 오피스 이름
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "office_name", length = 50, nullable = false)
    private String officeName;

    /**
     * 별칭
     */
    @Size(max = 50)
    @Column(name = "alias", length = 50)
    private String alias;

    /**
     * 별칭 접두사
     */
    @Size(max = 50)
    @Column(name = "alias_prefix", length = 50)
    private String aliasPrefix;

    /**
     * 별칭 접미사
     */
    @Size(max = 50)
    @Column(name = "alias_suffix", length = 50)
    private String aliasSuffix;

    /**
     * 근무시간 설정여부
     */
    @Column(name = "work_time_config_yn")
    private Boolean workTimeConfigYn;

    /**
     * 근무시작시간
     */
    @Pattern(regexp = "^([01][0-9]|2[0-3])([0-5][0-9])+$")
    @Column(name = "work_start_time")
    private String workStartTime;

    /**
     * 근무종료시간
     */
    @Pattern(regexp = "^([01][0-9]|2[0-3])([0-5][0-9])+$")
    @Column(name = "work_end_time")
    private String workEndTime;

    /**
     * 노쇼방지 사용여부 (Y or N)
     */
    @Column(name = "no_show_yn")
    private Boolean noShowYn;

    /**
     * 좌석예약 노쇼방지 여부
     */
    @Column(name = "no_show_penalty_yn")
    private Boolean noShowPenaltyYn;

    /**
     * 노쇼 타이머(분)
     */
    @Column(name = "no_show_timer_minutes")
    private Integer noShowTimerMinutes;

    /**
     * 노쇼 패널티 기간
     */
    @Column(name = "no_show_penalty_days")
    private Integer noShowPenaltyDays;

    /**
     * 예약신청 알림 여부
     */
    @Column(name = "apply_reservation_alarm_yn")
    private Boolean applyReservationAlarmYn;

    /**
     * 예약신청 알림 예시문
     */
    @Size(max = 2000)
    @Column(name = "apply_reservation_alarm_template", length = 2000)
    private String applyReservationAlarmTemplate;

    /**
     * 사전입실 알림 여부
     */
    @Column(name = "pre_entrance_alarm_yn")
    private Boolean preEntranceAlarmYn;

    /**
     * 사전입실 알림 간격(분)
     */
    @Column(name = "pre_entrance_alarm_interval")
    private Integer preEntranceAlarmInterval;

    /**
     * 사전입실 알림 예시문
     */
    @Size(max = 2000)
    @Column(name = "pre_entrance_alarm_template", length = 2000)
    private String preEntranceAlarmTemplate;

    /**
     * 노쇼 알림 여부
     */
    @Column(name = "no_show_alarm_yn")
    private Boolean noShowAlarmYn;

    /**
     * 노쇼 알림 예시문
     */
    @Size(max = 2000)
    @Column(name = "no_show_alarm_template", length = 2000)
    private String noShowAlarmTemplate;

    /**
     * Y or N
     */
    @Column(name = "exclusive")
    private Boolean exclusive;

    /**
     * 복무정보적용여부 (적용: Y, 미적용: N)
     */
    @Column(name = "use_duty_type")
    private Boolean useDutyType;

    /**
     * 임직원검색 사용여부 (Y or N)
     */
    @Column(name = "use_user_search")
    private Boolean useUserSearch;

    /**
     * Y or N
     */
    @Column(name = "kiosk_zoom_enable")
    private Boolean kioskZoomEnable;

    /**
     * 예약제한일수 (기본값: 0 무제한)
     */
    @Column(name = "reservation_limit_day")
    private Integer reservationLimitDay;

    /**
     * 일반 사용자 이용가능 좌석타입들 (see: tb_seat.seat_type)
     */
    @Size(max = 100)
    @Column(name = "available_seat_type", length = 100)
    private String availableSeatType;

    /**
     * 자동 체크인 적용여부 (적용: Y, 미적용: N)
     */
    @Column(name = "auto_check_in_yn")
    private Boolean autoCheckInYn;

    @OneToMany(mappedBy = "office")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resources", "attributes", "webImg", "kioskImg", "miniImg", "drawing", "office" }, allowSetters = true)
    private Set<Floor> floors = new HashSet<>();

    @OneToMany(mappedBy = "office")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "office" }, allowSetters = true)
    private Set<AttributeOffice> attributes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgs", "offices", "attributes" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Office id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public Office officeName(String officeName) {
        this.setOfficeName(officeName);
        return this;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getAlias() {
        return this.alias;
    }

    public Office alias(String alias) {
        this.setAlias(alias);
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliasPrefix() {
        return this.aliasPrefix;
    }

    public Office aliasPrefix(String aliasPrefix) {
        this.setAliasPrefix(aliasPrefix);
        return this;
    }

    public void setAliasPrefix(String aliasPrefix) {
        this.aliasPrefix = aliasPrefix;
    }

    public String getAliasSuffix() {
        return this.aliasSuffix;
    }

    public Office aliasSuffix(String aliasSuffix) {
        this.setAliasSuffix(aliasSuffix);
        return this;
    }

    public void setAliasSuffix(String aliasSuffix) {
        this.aliasSuffix = aliasSuffix;
    }

    public Boolean getWorkTimeConfigYn() {
        return this.workTimeConfigYn;
    }

    public Office workTimeConfigYn(Boolean workTimeConfigYn) {
        this.setWorkTimeConfigYn(workTimeConfigYn);
        return this;
    }

    public void setWorkTimeConfigYn(Boolean workTimeConfigYn) {
        this.workTimeConfigYn = workTimeConfigYn;
    }

    public String getWorkStartTime() {
        return this.workStartTime;
    }

    public Office workStartTime(String workStartTime) {
        this.setWorkStartTime(workStartTime);
        return this;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getWorkEndTime() {
        return this.workEndTime;
    }

    public Office workEndTime(String workEndTime) {
        this.setWorkEndTime(workEndTime);
        return this;
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
    }

    public Boolean getNoShowYn() {
        return this.noShowYn;
    }

    public Office noShowYn(Boolean noShowYn) {
        this.setNoShowYn(noShowYn);
        return this;
    }

    public void setNoShowYn(Boolean noShowYn) {
        this.noShowYn = noShowYn;
    }

    public Boolean getNoShowPenaltyYn() {
        return this.noShowPenaltyYn;
    }

    public Office noShowPenaltyYn(Boolean noShowPenaltyYn) {
        this.setNoShowPenaltyYn(noShowPenaltyYn);
        return this;
    }

    public void setNoShowPenaltyYn(Boolean noShowPenaltyYn) {
        this.noShowPenaltyYn = noShowPenaltyYn;
    }

    public Integer getNoShowTimerMinutes() {
        return this.noShowTimerMinutes;
    }

    public Office noShowTimerMinutes(Integer noShowTimerMinutes) {
        this.setNoShowTimerMinutes(noShowTimerMinutes);
        return this;
    }

    public void setNoShowTimerMinutes(Integer noShowTimerMinutes) {
        this.noShowTimerMinutes = noShowTimerMinutes;
    }

    public Integer getNoShowPenaltyDays() {
        return this.noShowPenaltyDays;
    }

    public Office noShowPenaltyDays(Integer noShowPenaltyDays) {
        this.setNoShowPenaltyDays(noShowPenaltyDays);
        return this;
    }

    public void setNoShowPenaltyDays(Integer noShowPenaltyDays) {
        this.noShowPenaltyDays = noShowPenaltyDays;
    }

    public Boolean getApplyReservationAlarmYn() {
        return this.applyReservationAlarmYn;
    }

    public Office applyReservationAlarmYn(Boolean applyReservationAlarmYn) {
        this.setApplyReservationAlarmYn(applyReservationAlarmYn);
        return this;
    }

    public void setApplyReservationAlarmYn(Boolean applyReservationAlarmYn) {
        this.applyReservationAlarmYn = applyReservationAlarmYn;
    }

    public String getApplyReservationAlarmTemplate() {
        return this.applyReservationAlarmTemplate;
    }

    public Office applyReservationAlarmTemplate(String applyReservationAlarmTemplate) {
        this.setApplyReservationAlarmTemplate(applyReservationAlarmTemplate);
        return this;
    }

    public void setApplyReservationAlarmTemplate(String applyReservationAlarmTemplate) {
        this.applyReservationAlarmTemplate = applyReservationAlarmTemplate;
    }

    public Boolean getPreEntranceAlarmYn() {
        return this.preEntranceAlarmYn;
    }

    public Office preEntranceAlarmYn(Boolean preEntranceAlarmYn) {
        this.setPreEntranceAlarmYn(preEntranceAlarmYn);
        return this;
    }

    public void setPreEntranceAlarmYn(Boolean preEntranceAlarmYn) {
        this.preEntranceAlarmYn = preEntranceAlarmYn;
    }

    public Integer getPreEntranceAlarmInterval() {
        return this.preEntranceAlarmInterval;
    }

    public Office preEntranceAlarmInterval(Integer preEntranceAlarmInterval) {
        this.setPreEntranceAlarmInterval(preEntranceAlarmInterval);
        return this;
    }

    public void setPreEntranceAlarmInterval(Integer preEntranceAlarmInterval) {
        this.preEntranceAlarmInterval = preEntranceAlarmInterval;
    }

    public String getPreEntranceAlarmTemplate() {
        return this.preEntranceAlarmTemplate;
    }

    public Office preEntranceAlarmTemplate(String preEntranceAlarmTemplate) {
        this.setPreEntranceAlarmTemplate(preEntranceAlarmTemplate);
        return this;
    }

    public void setPreEntranceAlarmTemplate(String preEntranceAlarmTemplate) {
        this.preEntranceAlarmTemplate = preEntranceAlarmTemplate;
    }

    public Boolean getNoShowAlarmYn() {
        return this.noShowAlarmYn;
    }

    public Office noShowAlarmYn(Boolean noShowAlarmYn) {
        this.setNoShowAlarmYn(noShowAlarmYn);
        return this;
    }

    public void setNoShowAlarmYn(Boolean noShowAlarmYn) {
        this.noShowAlarmYn = noShowAlarmYn;
    }

    public String getNoShowAlarmTemplate() {
        return this.noShowAlarmTemplate;
    }

    public Office noShowAlarmTemplate(String noShowAlarmTemplate) {
        this.setNoShowAlarmTemplate(noShowAlarmTemplate);
        return this;
    }

    public void setNoShowAlarmTemplate(String noShowAlarmTemplate) {
        this.noShowAlarmTemplate = noShowAlarmTemplate;
    }

    public Boolean getExclusive() {
        return this.exclusive;
    }

    public Office exclusive(Boolean exclusive) {
        this.setExclusive(exclusive);
        return this;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public Boolean getUseDutyType() {
        return this.useDutyType;
    }

    public Office useDutyType(Boolean useDutyType) {
        this.setUseDutyType(useDutyType);
        return this;
    }

    public void setUseDutyType(Boolean useDutyType) {
        this.useDutyType = useDutyType;
    }

    public Boolean getUseUserSearch() {
        return this.useUserSearch;
    }

    public Office useUserSearch(Boolean useUserSearch) {
        this.setUseUserSearch(useUserSearch);
        return this;
    }

    public void setUseUserSearch(Boolean useUserSearch) {
        this.useUserSearch = useUserSearch;
    }

    public Boolean getKioskZoomEnable() {
        return this.kioskZoomEnable;
    }

    public Office kioskZoomEnable(Boolean kioskZoomEnable) {
        this.setKioskZoomEnable(kioskZoomEnable);
        return this;
    }

    public void setKioskZoomEnable(Boolean kioskZoomEnable) {
        this.kioskZoomEnable = kioskZoomEnable;
    }

    public Integer getReservationLimitDay() {
        return this.reservationLimitDay;
    }

    public Office reservationLimitDay(Integer reservationLimitDay) {
        this.setReservationLimitDay(reservationLimitDay);
        return this;
    }

    public void setReservationLimitDay(Integer reservationLimitDay) {
        this.reservationLimitDay = reservationLimitDay;
    }

    public String getAvailableSeatType() {
        return this.availableSeatType;
    }

    public Office availableSeatType(String availableSeatType) {
        this.setAvailableSeatType(availableSeatType);
        return this;
    }

    public void setAvailableSeatType(String availableSeatType) {
        this.availableSeatType = availableSeatType;
    }

    public Boolean getAutoCheckInYn() {
        return this.autoCheckInYn;
    }

    public Office autoCheckInYn(Boolean autoCheckInYn) {
        this.setAutoCheckInYn(autoCheckInYn);
        return this;
    }

    public void setAutoCheckInYn(Boolean autoCheckInYn) {
        this.autoCheckInYn = autoCheckInYn;
    }

    public Set<Floor> getFloors() {
        return this.floors;
    }

    public void setFloors(Set<Floor> floors) {
        if (this.floors != null) {
            this.floors.forEach(i -> i.setOffice(null));
        }
        if (floors != null) {
            floors.forEach(i -> i.setOffice(this));
        }
        this.floors = floors;
    }

    public Office floors(Set<Floor> floors) {
        this.setFloors(floors);
        return this;
    }

    public Office addFloors(Floor floor) {
        this.floors.add(floor);
        floor.setOffice(this);
        return this;
    }

    public Office removeFloors(Floor floor) {
        this.floors.remove(floor);
        floor.setOffice(null);
        return this;
    }

    public Set<AttributeOffice> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<AttributeOffice> attributeOffices) {
        if (this.attributes != null) {
            this.attributes.forEach(i -> i.setOffice(null));
        }
        if (attributeOffices != null) {
            attributeOffices.forEach(i -> i.setOffice(this));
        }
        this.attributes = attributeOffices;
    }

    public Office attributes(Set<AttributeOffice> attributeOffices) {
        this.setAttributes(attributeOffices);
        return this;
    }

    public Office addAttributes(AttributeOffice attributeOffice) {
        this.attributes.add(attributeOffice);
        attributeOffice.setOffice(this);
        return this;
    }

    public Office removeAttributes(AttributeOffice attributeOffice) {
        this.attributes.remove(attributeOffice);
        attributeOffice.setOffice(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Office company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Office)) {
            return false;
        }
        return id != null && id.equals(((Office) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Office{" +
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
            "}";
    }
}
