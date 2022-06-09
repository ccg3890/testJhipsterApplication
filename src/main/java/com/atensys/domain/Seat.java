package com.atensys.domain;

import com.atensys.domain.enumeration.SeatSubType;
import com.atensys.domain.enumeration.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 좌석
 */
@Entity
@Table(name = "tb_seat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 건물
     */
    @NotNull
    @Column(name = "office_id", nullable = false)
    private Long officeId;

    /**
     * 층
     */
    @NotNull
    @Column(name = "floor_id", nullable = false)
    private Long floorId;

    /**
     * 좌석유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false)
    private SeatType seatType;

    /**
     * 좌석명
     */
    @NotNull
    @Size(max = 20)
    @Column(name = "seat_name", length = 20, nullable = false)
    private String seatName;

    /**
     * 좌석부가유형
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_sub_type", nullable = false)
    private SeatSubType seatSubType;

    /**
     * 사용가능여부
     */
    @NotNull
    @Column(name = "use_yn", nullable = false)
    private Boolean useYn;

    /**
     * Y좌표
     */
    @Min(value = 0L)
    @Column(name = "top")
    private Long top;

    /**
     * X좌표
     */
    @Min(value = 0L)
    @Column(name = "jhi_left")
    private Long left;

    @OneToMany(mappedBy = "seat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "seat" }, allowSetters = true)
    private Set<AttributeSeat> attributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Seat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfficeId() {
        return this.officeId;
    }

    public Seat officeId(Long officeId) {
        this.setOfficeId(officeId);
        return this;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public Long getFloorId() {
        return this.floorId;
    }

    public Seat floorId(Long floorId) {
        this.setFloorId(floorId);
        return this;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public SeatType getSeatType() {
        return this.seatType;
    }

    public Seat seatType(SeatType seatType) {
        this.setSeatType(seatType);
        return this;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public String getSeatName() {
        return this.seatName;
    }

    public Seat seatName(String seatName) {
        this.setSeatName(seatName);
        return this;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public SeatSubType getSeatSubType() {
        return this.seatSubType;
    }

    public Seat seatSubType(SeatSubType seatSubType) {
        this.setSeatSubType(seatSubType);
        return this;
    }

    public void setSeatSubType(SeatSubType seatSubType) {
        this.seatSubType = seatSubType;
    }

    public Boolean getUseYn() {
        return this.useYn;
    }

    public Seat useYn(Boolean useYn) {
        this.setUseYn(useYn);
        return this;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public Long getTop() {
        return this.top;
    }

    public Seat top(Long top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getLeft() {
        return this.left;
    }

    public Seat left(Long left) {
        this.setLeft(left);
        return this;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Set<AttributeSeat> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<AttributeSeat> attributeSeats) {
        if (this.attributes != null) {
            this.attributes.forEach(i -> i.setSeat(null));
        }
        if (attributeSeats != null) {
            attributeSeats.forEach(i -> i.setSeat(this));
        }
        this.attributes = attributeSeats;
    }

    public Seat attributes(Set<AttributeSeat> attributeSeats) {
        this.setAttributes(attributeSeats);
        return this;
    }

    public Seat addAttributes(AttributeSeat attributeSeat) {
        this.attributes.add(attributeSeat);
        attributeSeat.setSeat(this);
        return this;
    }

    public Seat removeAttributes(AttributeSeat attributeSeat) {
        this.attributes.remove(attributeSeat);
        attributeSeat.setSeat(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seat)) {
            return false;
        }
        return id != null && id.equals(((Seat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seat{" +
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
