package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Auth} entity.
 */
@Schema(description = "권한")
public class AuthDTO implements Serializable {

    private Long id;

    /**
     * 권한명
     */
    @NotNull
    @Size(max = 100)
    @Schema(description = "권한명", required = true)
    private String authName;

    /**
     * 기본권한여부
     */
    @Schema(description = "기본권한여부")
    private Boolean defaultAuth;

    /**
     * 사용여부
     */
    @NotNull
    @Schema(description = "사용여부", required = true)
    private Boolean valid;

    private Set<MenuDTO> menus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Boolean getDefaultAuth() {
        return defaultAuth;
    }

    public void setDefaultAuth(Boolean defaultAuth) {
        this.defaultAuth = defaultAuth;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Set<MenuDTO> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenuDTO> menus) {
        this.menus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthDTO)) {
            return false;
        }

        AuthDTO authDTO = (AuthDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, authDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthDTO{" +
            "id=" + getId() +
            ", authName='" + getAuthName() + "'" +
            ", defaultAuth='" + getDefaultAuth() + "'" +
            ", valid='" + getValid() + "'" +
            ", menus=" + getMenus() +
            "}";
    }
}
