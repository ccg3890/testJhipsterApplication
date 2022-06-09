package com.atensys.domain;

import com.atensys.domain.enumeration.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 사용자
 */
@Entity
@Table(name = "tb_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 사용자 아이디(PERNR)
     */
    @Size(max = 20)
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 성명(ENAME)
     */
    @Size(max = 50)
    @Column(name = "user_name", length = 50)
    private String userName;

    /**
     * 패스워드(PWD)
     */
    @Size(max = 200)
    @Column(name = "password", length = 200)
    private String password;

    /**
     * 재직상태(PERSG, 1:재직 / 2:퇴직 / 6휴직 / 9:외부용역직)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_stat")
    private UserStatus userStat;

    /**
     * 부서코드(ORGEH)
     */
    @Size(max = 50)
    @Column(name = "org_cd", length = 50)
    private String orgCd;

    /**
     * 직위 코드(LPOSI)
     */
    @Size(max = 50)
    @Column(name = "rank_cd", length = 50)
    private String rankCd;

    /**
     * 이메일
     */
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 휴대전화번호(MOBILE)
     */
    @Size(max = 50)
    @Column(name = "mobile", length = 50)
    private String mobile;

    /**
     * 전화번호
     */
    @Size(max = 50)
    @Column(name = "tel_no", length = 50)
    private String telNo;

    /**
     * 카드번호
     */
    @Size(max = 10)
    @Column(name = "card_no", length = 10)
    private String cardNo;

    /**
     * 다중예약가능여부 (Y or N)
     */
    @Column(name = "multi_reservation")
    private Boolean multiReservation;

    /**
     * 신규 사용자 여부 (Y or N)
     */
    @Column(name = "newbie")
    private Boolean newbie;

    /**
     * 파일ID
     */
    @Size(max = 50)
    @Column(name = "file_id", length = 50)
    private String fileId;

    /**
     * 인사연동대상자여부
     */
    @NotNull
    @Column(name = "hrsync", nullable = false)
    private Boolean hrsync;

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo" }, allowSetters = true)
    private Set<Penalty> penalties = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgs", "offices", "attributes" }, allowSetters = true)
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties(value = { "children", "parent", "company" }, allowSetters = true)
    private Org org;

    @ManyToOne
    private Rank rank;

    @ManyToOne
    private Fileinfo file;

    @ManyToMany
    @JoinTable(
        name = "rel_tb_user__auth",
        joinColumns = @JoinColumn(name = "tb_user_id"),
        inverseJoinColumns = @JoinColumn(name = "auth_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "menus", "userInfos" }, allowSetters = true)
    private Set<Auth> auths = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public UserInfo userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public UserInfo userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public UserInfo password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStat() {
        return this.userStat;
    }

    public UserInfo userStat(UserStatus userStat) {
        this.setUserStat(userStat);
        return this;
    }

    public void setUserStat(UserStatus userStat) {
        this.userStat = userStat;
    }

    public String getOrgCd() {
        return this.orgCd;
    }

    public UserInfo orgCd(String orgCd) {
        this.setOrgCd(orgCd);
        return this;
    }

    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
    }

    public String getRankCd() {
        return this.rankCd;
    }

    public UserInfo rankCd(String rankCd) {
        this.setRankCd(rankCd);
        return this;
    }

    public void setRankCd(String rankCd) {
        this.rankCd = rankCd;
    }

    public String getEmail() {
        return this.email;
    }

    public UserInfo email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public UserInfo mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelNo() {
        return this.telNo;
    }

    public UserInfo telNo(String telNo) {
        this.setTelNo(telNo);
        return this;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public UserInfo cardNo(String cardNo) {
        this.setCardNo(cardNo);
        return this;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Boolean getMultiReservation() {
        return this.multiReservation;
    }

    public UserInfo multiReservation(Boolean multiReservation) {
        this.setMultiReservation(multiReservation);
        return this;
    }

    public void setMultiReservation(Boolean multiReservation) {
        this.multiReservation = multiReservation;
    }

    public Boolean getNewbie() {
        return this.newbie;
    }

    public UserInfo newbie(Boolean newbie) {
        this.setNewbie(newbie);
        return this;
    }

    public void setNewbie(Boolean newbie) {
        this.newbie = newbie;
    }

    public String getFileId() {
        return this.fileId;
    }

    public UserInfo fileId(String fileId) {
        this.setFileId(fileId);
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Boolean getHrsync() {
        return this.hrsync;
    }

    public UserInfo hrsync(Boolean hrsync) {
        this.setHrsync(hrsync);
        return this;
    }

    public void setHrsync(Boolean hrsync) {
        this.hrsync = hrsync;
    }

    public Set<Penalty> getPenalties() {
        return this.penalties;
    }

    public void setPenalties(Set<Penalty> penalties) {
        if (this.penalties != null) {
            this.penalties.forEach(i -> i.setUserInfo(null));
        }
        if (penalties != null) {
            penalties.forEach(i -> i.setUserInfo(this));
        }
        this.penalties = penalties;
    }

    public UserInfo penalties(Set<Penalty> penalties) {
        this.setPenalties(penalties);
        return this;
    }

    public UserInfo addPenalties(Penalty penalty) {
        this.penalties.add(penalty);
        penalty.setUserInfo(this);
        return this;
    }

    public UserInfo removePenalties(Penalty penalty) {
        this.penalties.remove(penalty);
        penalty.setUserInfo(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public UserInfo company(Company company) {
        this.setCompany(company);
        return this;
    }

    public Org getOrg() {
        return this.org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public UserInfo org(Org org) {
        this.setOrg(org);
        return this;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public UserInfo rank(Rank rank) {
        this.setRank(rank);
        return this;
    }

    public Fileinfo getFile() {
        return this.file;
    }

    public void setFile(Fileinfo fileinfo) {
        this.file = fileinfo;
    }

    public UserInfo file(Fileinfo fileinfo) {
        this.setFile(fileinfo);
        return this;
    }

    public Set<Auth> getAuths() {
        return this.auths;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
    }

    public UserInfo auths(Set<Auth> auths) {
        this.setAuths(auths);
        return this;
    }

    public UserInfo addAuth(Auth auth) {
        this.auths.add(auth);
        auth.getUserInfos().add(this);
        return this;
    }

    public UserInfo removeAuth(Auth auth) {
        this.auths.remove(auth);
        auth.getUserInfos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfo{" +
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
            "}";
    }
}
