package com.atensys.domain;

import com.atensys.domain.enumeration.EndConditionType;
import com.atensys.domain.enumeration.FrequenceType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 반복구조
 */
@Entity
@Table(name = "tb_recurrence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recurrence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 반복유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "freq", nullable = false)
    private FrequenceType freq;

    /**
     * WEEKLY, MONTHLY\nMO, TU, WE, TH, FR, SA, SU
     */
    @Size(max = 32)
    @Column(name = "days_of_week", length = 32)
    private String daysOfWeek;

    /**
     * 매월 특정주\n이경우 daysOfWeek의 특정 요일과 같이 사용해야 함
     */
    @Min(value = 0)
    @Max(value = 4)
    @Column(name = "week_of_month")
    private Integer weekOfMonth;

    /**
     * 매월 특정일
     */
    @Min(value = 1)
    @Max(value = 31)
    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    /**
     * YEARLY
     */
    @Min(value = 1)
    @Max(value = 365)
    @Column(name = "day_of_year")
    private Integer dayOfYear;

    /**
     * 반복종료유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "end_condition", nullable = false)
    private EndConditionType endCondition;

    /**
     * 반복종료일자
     */
    @Column(name = "end_date_time")
    private LocalDate endDateTime;

    /**
     * 반복회수
     */
    @Min(value = 1)
    @Max(value = 99)
    @Column(name = "num_of_step")
    private Integer numOfStep;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recurrence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FrequenceType getFreq() {
        return this.freq;
    }

    public Recurrence freq(FrequenceType freq) {
        this.setFreq(freq);
        return this;
    }

    public void setFreq(FrequenceType freq) {
        this.freq = freq;
    }

    public String getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public Recurrence daysOfWeek(String daysOfWeek) {
        this.setDaysOfWeek(daysOfWeek);
        return this;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Integer getWeekOfMonth() {
        return this.weekOfMonth;
    }

    public Recurrence weekOfMonth(Integer weekOfMonth) {
        this.setWeekOfMonth(weekOfMonth);
        return this;
    }

    public void setWeekOfMonth(Integer weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

    public Integer getDayOfMonth() {
        return this.dayOfMonth;
    }

    public Recurrence dayOfMonth(Integer dayOfMonth) {
        this.setDayOfMonth(dayOfMonth);
        return this;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getDayOfYear() {
        return this.dayOfYear;
    }

    public Recurrence dayOfYear(Integer dayOfYear) {
        this.setDayOfYear(dayOfYear);
        return this;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public EndConditionType getEndCondition() {
        return this.endCondition;
    }

    public Recurrence endCondition(EndConditionType endCondition) {
        this.setEndCondition(endCondition);
        return this;
    }

    public void setEndCondition(EndConditionType endCondition) {
        this.endCondition = endCondition;
    }

    public LocalDate getEndDateTime() {
        return this.endDateTime;
    }

    public Recurrence endDateTime(LocalDate endDateTime) {
        this.setEndDateTime(endDateTime);
        return this;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getNumOfStep() {
        return this.numOfStep;
    }

    public Recurrence numOfStep(Integer numOfStep) {
        this.setNumOfStep(numOfStep);
        return this;
    }

    public void setNumOfStep(Integer numOfStep) {
        this.numOfStep = numOfStep;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recurrence)) {
            return false;
        }
        return id != null && id.equals(((Recurrence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recurrence{" +
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
