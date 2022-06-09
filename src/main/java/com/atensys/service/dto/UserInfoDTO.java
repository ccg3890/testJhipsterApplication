package com.atensys.service.dto;

import com.atensys.domain.enumeration.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.UserInfo} entity.
 */
@Schema(description = "사용자")
public class UserInfoDTO implements Serializable {

    private Long id;

    /**
     * 사용자 아이디(PERNR)
     */
    @Size(max = 20)
    @Schema(description = "사용자 아이디(PERNR)")
    private String userId;

    /**
     * 성명(ENAME)
     */
    @Size(max = 50)
    @Schema(description = "성명(ENAME)")
    private String userName;

    /**
     * 패스워드(PWD)
     */
    @Size(max = 200)
    @Schema(description = "패스워드(PWD)")
    private String password;

    /**
     * 재직상태(PERSG, 1:재직 / 2:퇴직 / 6휴직 / 9:외부용역직)
     */
    @Schema(description = "재직상태(PERSG, 1:재직 / 2:퇴직 / 6휴직 / 9:외부용역직)")
    private UserStatus userStat;

    /**
     * 부서코드(ORGEH)
     */
    @Size(max = 50)
    @Schema(description = "부서코드(ORGEH)")
    private String orgCd;

    /**
     * 직위 코드(LPOSI)
     */
    @Size(max = 50)
    @Schema(description = "직위 코드(LPOSI)")
    private String rankCd;

    /**
     * 이메일
     */
    @Size(max = 100)
    @Schema(description = "이메일")
    private String email;

    /**
     * 휴대전화번호(MOBILE)
     */
    @Size(max = 50)
    @Schema(description = "휴대전화번호(MOBILE)")
    private String mobile;

    /**
     * 전화번호
     */
    @Size(max = 50)
    @Schema(description = "전화번호")
    private String telNo;

    /**
     * 카드번호
     */
    @Size(max = 10)
    @Schema(description = "카드번호")
    private String cardNo;

    /**
     * 다중예약가능여부 (Y or N)
     */
    @Schema(description = "다중예약가능여부 (Y or N)")
    private Boolean multiReservation;

    /**
     * 신규 사용자 여부 (Y or N)
     */
    @Schema(description = "신규 사용자 여부 (Y or N)")
    private Boolean newbie;

    /**
     * 파일ID
     */
    @Size(max = 50)
    @Schema(description = "파일ID")
    private String fileId;

    /**
     * 인사연동대상자여부
     */
    @NotNull
    @Schema(description = "인사연동대상자여부", required = true)
    private Boolean hrsync;

    private CompanyDTO company;

    private OrgDTO org;

    private RankDTO rank;

    private FileinfoDTO file;

    private Set<AuthDTO> auths = new HashSet<>();

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStat() {
        return userStat;
    }

    public void setUserStat(UserStatus userStat) {
        this.userStat = userStat;
    }

    public String getOrgCd() {
        return orgCd;
    }

    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
    }

    public String getRankCd() {
        return rankCd;
    }

    public void setRankCd(String rankCd) {
        this.rankCd = rankCd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Boolean getMultiReservation() {
        return multiReservation;
    }

    public void setMultiReservation(Boolean multiReservation) {
        this.multiReservation = multiReservation;
    }

    public Boolean getNewbie() {
        return newbie;
    }

    public void setNewbie(Boolean newbie) {
        this.newbie = newbie;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Boolean getHrsync() {
        return hrsync;
    }

    public void setHrsync(Boolean hrsync) {
        this.hrsync = hrsync;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public OrgDTO getOrg() {
        return org;
    }

    public void setOrg(OrgDTO org) {
        this.org = org;
    }

    public RankDTO getRank() {
        return rank;
    }

    public void setRank(RankDTO rank) {
        this.rank = rank;
    }

    public FileinfoDTO getFile() {
        return file;
    }

    public void setFile(FileinfoDTO file) {
        this.file = file;
    }

    public Set<AuthDTO> getAuths() {
        return auths;
    }

    public void setAuths(Set<AuthDTO> auths) {
        this.auths = auths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfoDTO)) {
            return false;
        }

        UserInfoDTO userInfoDTO = (UserInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", userStat='" + getUserStat() + "'" +
            ", orgCd='" + getOrgCd() + "'" +
            ", rankCd='" + getRankCd() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", telNo='" + getTelNo() + "'" +
            ", cardNo='" + getCardNo() + "'" +
            ", multiReservation='" + getMultiReservation() + "'" +
            ", newbie='" + getNewbie() + "'" +
            ", fileId='" + getFileId() + "'" +
            ", hrsync='" + getHrsync() + "'" +
            ", company=" + getCompany() +
            ", org=" + getOrg() +
            ", rank=" + getRank() +
            ", file=" + getFile() +
            ", auths=" + getAuths() +
            "}";
    }
}
