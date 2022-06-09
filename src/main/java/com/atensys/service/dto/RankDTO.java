package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Rank} entity.
 */
@Schema(description = "직급")
public class RankDTO implements Serializable {

    private Long id;

    /**
     * 직책코드
     */
    @Size(max = 50)
    @Schema(description = "직책코드")
    private String rankCd;

    /**
     * 직책 명
     */
    @Size(max = 200)
    @Schema(description = "직책 명")
    private String rankName;

    /**
     * 직책 영문명
     */
    @Size(max = 200)
    @Schema(description = "직책 영문명")
    private String rankEname;

    /**
     * 사용여부
     */
    @Schema(description = "사용여부")
    private Boolean useYn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRankCd() {
        return rankCd;
    }

    public void setRankCd(String rankCd) {
        this.rankCd = rankCd;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankEname() {
        return rankEname;
    }

    public void setRankEname(String rankEname) {
        this.rankEname = rankEname;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RankDTO)) {
            return false;
        }

        RankDTO rankDTO = (RankDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rankDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RankDTO{" +
            "id=" + getId() +
            ", rankCd='" + getRankCd() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", rankEname='" + getRankEname() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
