package com.atensys.service.dto;

import com.atensys.domain.enumeration.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Org} entity.
 */
@Schema(description = "부서")
public class OrgDTO implements Serializable {

    private Long id;

    /**
     * 부서코드
     */
    @Size(max = 100)
    @Schema(description = "부서코드")
    private String orgCd;

    /**
     * 부서명(ORGTX)
     */
    @Size(max = 200)
    @Schema(description = "부서명(ORGTX)")
    private String orgName;

    /**
     * 부서명(ORGTX_EN, 영문)
     */
    @Size(max = 200)
    @Schema(description = "부서명(ORGTX_EN, 영문)")
    private String orgEname;

    /**
     * 사용여부(USE_YN, 1:사용, 0:미사용)
     */
    @Schema(description = "사용여부(USE_YN, 1:사용, 0:미사용)")
    private Boolean useYn;

    /**
     * 상위부서코드
     */
    @Size(max = 100)
    @Schema(description = "상위부서코드")
    private String upOrgCd;

    /**
     * 부서 대표번호(ORGTEL)
     */
    @Size(max = 50)
    @Schema(description = "부서 대표번호(ORGTEL)")
    private String orgTel;

    /**
     * 근무국가코드(ZREGCODE)
     */
    @Size(max = 10)
    @Schema(description = "근무국가코드(ZREGCODE)")
    private String zipCd;

    /**
     * 정렬 순서(SORT_NO)
     */
    @Schema(description = "정렬 순서(SORT_NO)")
    private Double orderNo;

    /**
     * 계층부서코드
     */
    @Size(max = 256)
    @Schema(description = "계층부서코드")
    private String hierachyCode;

    /**
     * 계층부서명
     */
    @Size(max = 256)
    @Schema(description = "계층부서명")
    private String hierachyName;

    /**
     * 부서 좌석제한 퍼센트 혹은 갯수
     */
    @Schema(description = "부서 좌석제한 퍼센트 혹은 갯수")
    private Integer seatLimit;

    /**
     * 좌석제한단위(PERCENT or NUMBER)
     */
    @Schema(description = "좌석제한단위(PERCENT or NUMBER)")
    private UnitType seatLimitUnit;

    private OrgDTO parent;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgCd() {
        return orgCd;
    }

    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgEname() {
        return orgEname;
    }

    public void setOrgEname(String orgEname) {
        this.orgEname = orgEname;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public String getUpOrgCd() {
        return upOrgCd;
    }

    public void setUpOrgCd(String upOrgCd) {
        this.upOrgCd = upOrgCd;
    }

    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    public String getZipCd() {
        return zipCd;
    }

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public Double getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Double orderNo) {
        this.orderNo = orderNo;
    }

    public String getHierachyCode() {
        return hierachyCode;
    }

    public void setHierachyCode(String hierachyCode) {
        this.hierachyCode = hierachyCode;
    }

    public String getHierachyName() {
        return hierachyName;
    }

    public void setHierachyName(String hierachyName) {
        this.hierachyName = hierachyName;
    }

    public Integer getSeatLimit() {
        return seatLimit;
    }

    public void setSeatLimit(Integer seatLimit) {
        this.seatLimit = seatLimit;
    }

    public UnitType getSeatLimitUnit() {
        return seatLimitUnit;
    }

    public void setSeatLimitUnit(UnitType seatLimitUnit) {
        this.seatLimitUnit = seatLimitUnit;
    }

    public OrgDTO getParent() {
        return parent;
    }

    public void setParent(OrgDTO parent) {
        this.parent = parent;
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
        if (!(o instanceof OrgDTO)) {
            return false;
        }

        OrgDTO orgDTO = (OrgDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orgDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgDTO{" +
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
            ", parent=" + getParent() +
            ", company=" + getCompany() +
            "}";
    }
}
