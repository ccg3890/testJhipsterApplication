package com.atensys.service.dto;

import com.atensys.domain.enumeration.EndConditionType;
import com.atensys.domain.enumeration.FrequenceType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Recurrence} entity.
 */
@Schema(description = "반복구조")
public class RecurrenceDTO implements Serializable {

    private Long id;

    /**
     * 반복유형
     */
    @NotNull
    @Schema(description = "반복유형", required = true)
    private FrequenceType freq;

    /**
     * WEEKLY, MONTHLY\nMO, TU, WE, TH, FR, SA, SU
     */
    @Size(max = 32)
    @Schema(description = "WEEKLY, MONTHLY\nMO, TU, WE, TH, FR, SA, SU")
    private String daysOfWeek;

    /**
     * 매월 특정주\n이경우 daysOfWeek의 특정 요일과 같이 사용해야 함
     */
    @Min(value = 0)
    @Max(value = 4)
    @Schema(description = "매월 특정주\n이경우 daysOfWeek의 특정 요일과 같이 사용해야 함")
    private Integer weekOfMonth;

    /**
     * 매월 특정일
     */
    @Min(value = 1)
    @Max(value = 31)
    @Schema(description = "매월 특정일")
    private Integer dayOfMonth;

    /**
     * YEARLY
     */
    @Min(value = 1)
    @Max(value = 365)
    @Schema(description = "YEARLY")
    private Integer dayOfYear;

    /**
     * 반복종료유형
     */
    @NotNull
    @Schema(description = "반복종료유형", required = true)
    private EndConditionType endCondition;

    /**
     * 반복종료일자
     */
    @Schema(description = "반복종료일자")
    private LocalDate endDateTime;

    /**
     * 반복회수
     */
    @Min(value = 1)
    @Max(value = 99)
    @Schema(description = "반복회수")
    private Integer numOfStep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FrequenceType getFreq() {
        return freq;
    }

    public void setFreq(FrequenceType freq) {
        this.freq = freq;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Integer getWeekOfMonth() {
        return weekOfMonth;
    }

    public void setWeekOfMonth(Integer weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public EndConditionType getEndCondition() {
        return endCondition;
    }

    public void setEndCondition(EndConditionType endCondition) {
        this.endCondition = endCondition;
    }

    public LocalDate getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getNumOfStep() {
        return numOfStep;
    }

    public void setNumOfStep(Integer numOfStep) {
        this.numOfStep = numOfStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecurrenceDTO)) {
            return false;
        }

        RecurrenceDTO recurrenceDTO = (RecurrenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recurrenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecurrenceDTO{" +
            "id=" + getId() +
            ", freq='" + getFreq() + "'" +
            ", daysOfWeek='" + getDaysOfWeek() + "'" +
            ", weekOfMonth=" + getWeekOfMonth() +
            ", dayOfMonth=" + getDayOfMonth() +
            ", dayOfYear=" + getDayOfYear() +
            ", endCondition='" + getEndCondition() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", numOfStep=" + getNumOfStep() +
            "}";
    }
}
