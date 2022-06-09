package com.atensys.service.dto;

import com.atensys.domain.enumeration.CodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Config} entity.
 */
@Schema(description = "설정")
public class ConfigDTO implements Serializable {

    private Long id;

    /**
     * 설정ID
     */
    @NotNull
    @Schema(description = "설정ID", required = true)
    private Long configId;

    /**
     * 부모ID
     */
    @Schema(description = "부모ID")
    private Long parentId;

    /**
     * 코드
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "코드", required = true)
    private String code;

    /**
     * 코드타입
     */
    @NotNull
    @Schema(description = "코드타입", required = true)
    private CodeType codeType;

    /**
     * 값
     */
    @NotNull
    @Size(max = 2048)
    @Schema(description = "값", required = true)
    private String value;

    /**
     * 설명
     */
    @Size(max = 2048)
    @Schema(description = "설명")
    private String description;

    /**
     * 사용여부
     */
    @Schema(description = "사용여부")
    private Boolean useYn;

    private ConfigDTO parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public ConfigDTO getParent() {
        return parent;
    }

    public void setParent(ConfigDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigDTO)) {
            return false;
        }

        ConfigDTO configDTO = (ConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, configDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigDTO{" +
            "id=" + getId() +
            ", configId=" + getConfigId() +
            ", parentId=" + getParentId() +
            ", code='" + getCode() + "'" +
            ", codeType='" + getCodeType() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
