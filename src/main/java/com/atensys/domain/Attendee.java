package com.atensys.domain;

import com.atensys.domain.enumeration.AttendeeResponseStatus;
import com.atensys.domain.enumeration.AttendeeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 참석자
 */
@Entity
@Table(name = "tb_attendee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 참석자유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "attendee_type", nullable = false)
    private AttendeeType attendeeType;

    /**
     * 참석자명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "display_name", length = 64, nullable = false)
    private String displayName;

    /**
     * 참석회사명
     */
    @Size(max = 64)
    @Column(name = "company_name", length = 64)
    private String companyName;

    /**
     * 참석자ID(사내참석자)
     */
    @Size(max = 32)
    @Column(name = "attendee_id", length = 32)
    private String attendeeId;

    /**
     * 휴대전화번호
     */
    @NotNull
    @Size(max = 32)
    @Column(name = "mobile_no", length = 32, nullable = false)
    private String mobileNo;

    /**
     * 이메일
     */
    @Size(max = 128)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", length = 128)
    private String email;

    /**
     * 참석확인제외 여부
     */
    @Column(name = "optional")
    private Boolean optional;

    /**
     * 참석자여부응답 코멘트
     */
    @Size(max = 256)
    @Column(name = "comment", length = 256)
    private String comment;

    /**
     * 참석여부응답상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "response_status")
    private AttendeeResponseStatus responseStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "resource", "attendees", "reservedRoomSeats", "reservation" }, allowSetters = true)
    private ReservationTarget reservationTarget;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attendee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendeeType getAttendeeType() {
        return this.attendeeType;
    }

    public Attendee attendeeType(AttendeeType attendeeType) {
        this.setAttendeeType(attendeeType);
        return this;
    }

    public void setAttendeeType(AttendeeType attendeeType) {
        this.attendeeType = attendeeType;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Attendee displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Attendee companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAttendeeId() {
        return this.attendeeId;
    }

    public Attendee attendeeId(String attendeeId) {
        this.setAttendeeId(attendeeId);
        return this;
    }

    public void setAttendeeId(String attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Attendee mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return this.email;
    }

    public Attendee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOptional() {
        return this.optional;
    }

    public Attendee optional(Boolean optional) {
        this.setOptional(optional);
        return this;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public String getComment() {
        return this.comment;
    }

    public Attendee comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AttendeeResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    public Attendee responseStatus(AttendeeResponseStatus responseStatus) {
        this.setResponseStatus(responseStatus);
        return this;
    }

    public void setResponseStatus(AttendeeResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ReservationTarget getReservationTarget() {
        return this.reservationTarget;
    }

    public void setReservationTarget(ReservationTarget reservationTarget) {
        this.reservationTarget = reservationTarget;
    }

    public Attendee reservationTarget(ReservationTarget reservationTarget) {
        this.setReservationTarget(reservationTarget);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendee)) {
            return false;
        }
        return id != null && id.equals(((Attendee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendee{" +
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
            "}";
    }
}
