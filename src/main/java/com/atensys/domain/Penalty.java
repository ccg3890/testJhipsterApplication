package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 페널티
 */
@Entity
@Table(name = "tb_penalty")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Penalty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 사용자ID
     */
    @Size(max = 20)
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 오피스ID
     */
    @Size(max = 20)
    @Column(name = "office_id", length = 20)
    private String officeId;

    /**
     * 좌석예약 제한 시작일
     */
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * 좌석예약 제한 종료일
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "penalties", "company", "org", "rank", "file", "auths" }, allowSetters = true)
    private UserInfo userInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Penalty id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Penalty userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfficeId() {
        return this.officeId;
    }

    public Penalty officeId(String officeId) {
        this.setOfficeId(officeId);
        return this;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Penalty startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Penalty endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Penalty userInfo(UserInfo userInfo) {
        this.setUserInfo(userInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Penalty)) {
            return false;
        }
        return id != null && id.equals(((Penalty) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Penalty{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", officeId='" + getOfficeId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
