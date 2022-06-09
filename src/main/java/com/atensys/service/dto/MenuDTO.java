package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Menu} entity.
 */
@Schema(description = "메뉴")
public class MenuDTO implements Serializable {

    private Long id;

    /**
     * 메뉴레벨
     */
    @NotNull
    @Min(value = 0)
    @Schema(description = "메뉴레벨", required = true)
    private Integer menuDepth;

    /**
     * 메뉴명
     */
    @NotNull
    @Size(max = 50)
    @Schema(description = "메뉴명", required = true)
    private String menuName;

    /**
     * 메뉴 URL
     */
    @Size(max = 256)
    @Schema(description = "메뉴 URL")
    private String menuUrl;

    /**
     * 메뉴정렬순서
     */
    @Min(value = 0)
    @Schema(description = "메뉴정렬순서")
    private Integer orderNo;

    /**
     * 사용여부
     */
    @NotNull
    @Schema(description = "사용여부", required = true)
    private Boolean valid;

    /**
     * 메뉴 이미지
     */
    @Size(max = 256)
    @Schema(description = "메뉴 이미지")
    private String menuImageId;

    private MenuDTO parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMenuDepth() {
        return menuDepth;
    }

    public void setMenuDepth(Integer menuDepth) {
        this.menuDepth = menuDepth;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
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

    public String getMenuImageId() {
        return menuImageId;
    }

    public void setMenuImageId(String menuImageId) {
        this.menuImageId = menuImageId;
    }

    public MenuDTO getParent() {
        return parent;
    }

    public void setParent(MenuDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuDTO)) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + getId() +
            ", menuDepth=" + getMenuDepth() +
            ", menuName='" + getMenuName() + "'" +
            ", menuUrl='" + getMenuUrl() + "'" +
            ", orderNo=" + getOrderNo() +
            ", valid='" + getValid() + "'" +
            ", menuImageId='" + getMenuImageId() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
