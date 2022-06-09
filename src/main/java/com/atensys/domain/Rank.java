package com.atensys.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 직급
 */
@Entity
@Table(name = "tb_rank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 직책코드
     */
    @Size(max = 50)
    @Column(name = "rank_cd", length = 50, unique = true)
    private String rankCd;

    /**
     * 직책 명
     */
    @Size(max = 200)
    @Column(name = "rank_name", length = 200)
    private String rankName;

    /**
     * 직책 영문명
     */
    @Size(max = 200)
    @Column(name = "rank_ename", length = 200)
    private String rankEname;

    /**
     * 사용여부
     */
    @Column(name = "use_yn")
    private Boolean useYn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rank id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRankCd() {
        return this.rankCd;
    }

    public Rank rankCd(String rankCd) {
        this.setRankCd(rankCd);
        return this;
    }

    public void setRankCd(String rankCd) {
        this.rankCd = rankCd;
    }

    public String getRankName() {
        return this.rankName;
    }

    public Rank rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankEname() {
        return this.rankEname;
    }

    public Rank rankEname(String rankEname) {
        this.setRankEname(rankEname);
        return this;
    }

    public void setRankEname(String rankEname) {
        this.rankEname = rankEname;
    }

    public Boolean getUseYn() {
        return this.useYn;
    }

    public Rank useYn(Boolean useYn) {
        this.setUseYn(useYn);
        return this;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rank)) {
            return false;
        }
        return id != null && id.equals(((Rank) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rank{" +
            "id=" + getId() +
            ", rankCd='" + getRankCd() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", rankEname='" + getRankEname() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
