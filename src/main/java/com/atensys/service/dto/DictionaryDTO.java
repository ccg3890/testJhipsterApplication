package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Dictionary} entity.
 */
@Schema(description = "키워드사전 (키워드 등록 대상 식별은 special/general 여부로 판단)")
public class DictionaryDTO implements Serializable {

    private Long id;

    /**
     * key
     */
    @NotNull
    @Size(max = 128)
    @Schema(description = "key", required = true)
    private String key;

    /**
     * 키워드명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "키워드명", required = true)
    private String name;

    /**
     * 설명
     */
    @Size(max = 1024)
    @Schema(description = "설명")
    private String discription;

    /**
     * 체크로직(regx)
     */
    @Size(max = 512)
    @Schema(description = "체크로직(regx)")
    private String checkPattern;

    /**
     * default
     */
    @Size(max = 256)
    @Schema(description = "default")
    private String defaultValue;

    /**
     * 순서
     */
    @Min(value = 0)
    @Schema(description = "순서")
    private Integer orderNo;

    /**
     * 사용여부
     */
    @Schema(description = "사용여부")
    private Boolean valid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getCheckPattern() {
        return checkPattern;
    }

    public void setCheckPattern(String checkPattern) {
        this.checkPattern = checkPattern;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DictionaryDTO)) {
            return false;
        }

        DictionaryDTO dictionaryDTO = (DictionaryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dictionaryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DictionaryDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", name='" + getName() + "'" +
            ", discription='" + getDiscription() + "'" +
            ", checkPattern='" + getCheckPattern() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", orderNo=" + getOrderNo() +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
