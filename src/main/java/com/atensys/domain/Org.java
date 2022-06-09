package com.atensys.domain;

import com.atensys.domain.enumeration.UnitType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 부서
 */
@Entity
@Table(name = "tb_org")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Org implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 부서코드
     */
    @Size(max = 100)
    @Column(name = "org_cd", length = 100, unique = true)
    private String orgCd;

    /**
     * 부서명(ORGTX)
     */
    @Size(max = 200)
    @Column(name = "org_name", length = 200)
    private String orgName;

    /**
     * 부서명(ORGTX_EN, 영문)
     */
    @Size(max = 200)
    @Column(name = "org_ename", length = 200)
    private String orgEname;

    /**
     * 사용여부(USE_YN, 1:사용, 0:미사용)
     */
    @Column(name = "use_yn")
    private Boolean useYn;

    /**
     * 상위부서코드
     */
    @Size(max = 100)
    @Column(name = "up_org_cd", length = 100)
    private String upOrgCd;

    /**
     * 부서 대표번호(ORGTEL)
     */
    @Size(max = 50)
    @Column(name = "org_tel", length = 50)
    private String orgTel;

    /**
     * 근무국가코드(ZREGCODE)
     */
    @Size(max = 10)
    @Column(name = "zip_cd", length = 10)
    private String zipCd;

    /**
     * 정렬 순서(SORT_NO)
     */
    @Column(name = "order_no")
    private Double orderNo;

    /**
     * 계층부서코드
     */
    @Size(max = 256)
    @Column(name = "hierachy_code", length = 256)
    private String hierachyCode;

    /**
     * 계층부서명
     */
    @Size(max = 256)
    @Column(name = "hierachy_name", length = 256)
    private String hierachyName;

    /**
     * 부서 좌석제한 퍼센트 혹은 갯수
     */
    @Column(name = "seat_limit")
    private Integer seatLimit;

    /**
     * 좌석제한단위(PERCENT or NUMBER)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_limit_unit")
    private UnitType seatLimitUnit;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "company" }, allowSetters = true)
    private Set<Org> children = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "children", "parent", "company" }, allowSetters = true)
    private Org parent;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgs", "offices", "attributes" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Org id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgCd() {
        return this.orgCd;
    }

    public Org orgCd(String orgCd) {
        this.setOrgCd(orgCd);
        return this;
    }

    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public Org orgName(String orgName) {
        this.setOrgName(orgName);
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgEname() {
        return this.orgEname;
    }

    public Org orgEname(String orgEname) {
        this.setOrgEname(orgEname);
        return this;
    }

    public void setOrgEname(String orgEname) {
        this.orgEname = orgEname;
    }

    public Boolean getUseYn() {
        return this.useYn;
    }

    public Org useYn(Boolean useYn) {
        this.setUseYn(useYn);
        return this;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public String getUpOrgCd() {
        return this.upOrgCd;
    }

    public Org upOrgCd(String upOrgCd) {
        this.setUpOrgCd(upOrgCd);
        return this;
    }

    public void setUpOrgCd(String upOrgCd) {
        this.upOrgCd = upOrgCd;
    }

    public String getOrgTel() {
        return this.orgTel;
    }

    public Org orgTel(String orgTel) {
        this.setOrgTel(orgTel);
        return this;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getZipCd() {
        return this.zipCd;
    }

    public Org zipCd(String zipCd) {
        this.setZipCd(zipCd);
        return this;
    }

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public Double getOrderNo() {
        return this.orderNo;
    }

    public Org orderNo(Double orderNo) {
        this.setOrderNo(orderNo);
        return this;
    }

    public void setOrderNo(Double orderNo) {
        this.orderNo = orderNo;
    }

    public String getHierachyCode() {
        return this.hierachyCode;
    }

    public Org hierachyCode(String hierachyCode) {
        this.setHierachyCode(hierachyCode);
        return this;
    }

    public void setHierachyCode(String hierachyCode) {
        this.hierachyCode = hierachyCode;
    }

    public String getHierachyName() {
        return this.hierachyName;
    }

    public Org hierachyName(String hierachyName) {
        this.setHierachyName(hierachyName);
        return this;
    }

    public void setHierachyName(String hierachyName) {
        this.hierachyName = hierachyName;
    }

    public Integer getSeatLimit() {
        return this.seatLimit;
    }

    public Org seatLimit(Integer seatLimit) {
        this.setSeatLimit(seatLimit);
        return this;
    }

    public void setSeatLimit(Integer seatLimit) {
        this.seatLimit = seatLimit;
    }

    public UnitType getSeatLimitUnit() {
        return this.seatLimitUnit;
    }

    public Org seatLimitUnit(UnitType seatLimitUnit) {
        this.setSeatLimitUnit(seatLimitUnit);
        return this;
    }

    public void setSeatLimitUnit(UnitType seatLimitUnit) {
        this.seatLimitUnit = seatLimitUnit;
    }

    public Set<Org> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Org> orgs) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (orgs != null) {
            orgs.forEach(i -> i.setParent(this));
        }
        this.children = orgs;
    }

    public Org children(Set<Org> orgs) {
        this.setChildren(orgs);
        return this;
    }

    public Org addChildren(Org org) {
        this.children.add(org);
        org.setParent(this);
        return this;
    }

    public Org removeChildren(Org org) {
        this.children.remove(org);
        org.setParent(null);
        return this;
    }

    public Org getParent() {
        return this.parent;
    }

    public void setParent(Org org) {
        this.parent = org;
    }

    public Org parent(Org org) {
        this.setParent(org);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Org company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Org)) {
            return false;
        }
        return id != null && id.equals(((Org) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Org{" +
            "id=" + getId() +
            ", orgCd='" + getOrgCd() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", orgEname='" + getOrgEname() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", upOrgCd='" + getUpOrgCd() + "'" +
            ", orgTel='" + getOrgTel() + "'" +
            ", zipCd='" + getZipCd() + "'" +
            ", orderNo=" + getOrderNo() +
            ", hierachyCode='" + getHierachyCode() + "'" +
            ", hierachyName='" + getHierachyName() + "'" +
            ", seatLimit=" + getSeatLimit() +
            ", seatLimitUnit='" + getSeatLimitUnit() + "'" +
            "}";
    }
}
