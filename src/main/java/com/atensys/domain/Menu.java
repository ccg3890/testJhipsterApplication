package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 메뉴
 */
@Entity
@Table(name = "tb_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 메뉴레벨
     */
    @NotNull
    @Min(value = 0)
    @Column(name = "menu_depth", nullable = false)
    private Integer menuDepth;

    /**
     * 메뉴명
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "menu_name", length = 50, nullable = false)
    private String menuName;

    /**
     * 메뉴 URL
     */
    @Size(max = 256)
    @Column(name = "menu_url", length = 256)
    private String menuUrl;

    /**
     * 메뉴정렬순서
     */
    @Min(value = 0)
    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 사용여부
     */
    @NotNull
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    /**
     * 메뉴 이미지
     */
    @Size(max = 256)
    @Column(name = "menu_image_id", length = 256)
    private String menuImageId;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "auths" }, allowSetters = true)
    private Set<Menu> children = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "children", "parent", "auths" }, allowSetters = true)
    private Menu parent;

    @ManyToMany(mappedBy = "menus")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "menus", "userInfos" }, allowSetters = true)
    private Set<Auth> auths = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Menu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMenuDepth() {
        return this.menuDepth;
    }

    public Menu menuDepth(Integer menuDepth) {
        this.setMenuDepth(menuDepth);
        return this;
    }

    public void setMenuDepth(Integer menuDepth) {
        this.menuDepth = menuDepth;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public Menu menuName(String menuName) {
        this.setMenuName(menuName);
        return this;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return this.menuUrl;
    }

    public Menu menuUrl(String menuUrl) {
        this.setMenuUrl(menuUrl);
        return this;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public Menu orderNo(Integer orderNo) {
        this.setOrderNo(orderNo);
        return this;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public Menu valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getMenuImageId() {
        return this.menuImageId;
    }

    public Menu menuImageId(String menuImageId) {
        this.setMenuImageId(menuImageId);
        return this;
    }

    public void setMenuImageId(String menuImageId) {
        this.menuImageId = menuImageId;
    }

    public Set<Menu> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Menu> menus) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (menus != null) {
            menus.forEach(i -> i.setParent(this));
        }
        this.children = menus;
    }

    public Menu children(Set<Menu> menus) {
        this.setChildren(menus);
        return this;
    }

    public Menu addChildren(Menu menu) {
        this.children.add(menu);
        menu.setParent(this);
        return this;
    }

    public Menu removeChildren(Menu menu) {
        this.children.remove(menu);
        menu.setParent(null);
        return this;
    }

    public Menu getParent() {
        return this.parent;
    }

    public void setParent(Menu menu) {
        this.parent = menu;
    }

    public Menu parent(Menu menu) {
        this.setParent(menu);
        return this;
    }

    public Set<Auth> getAuths() {
        return this.auths;
    }

    public void setAuths(Set<Auth> auths) {
        if (this.auths != null) {
            this.auths.forEach(i -> i.removeMenu(this));
        }
        if (auths != null) {
            auths.forEach(i -> i.addMenu(this));
        }
        this.auths = auths;
    }

    public Menu auths(Set<Auth> auths) {
        this.setAuths(auths);
        return this;
    }

    public Menu addAuth(Auth auth) {
        this.auths.add(auth);
        auth.getMenus().add(this);
        return this;
    }

    public Menu removeAuth(Auth auth) {
        this.auths.remove(auth);
        auth.getMenus().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", menuDepth=" + getMenuDepth() +
            ", menuName='" + getMenuName() + "'" +
            ", menuUrl='" + getMenuUrl() + "'" +
            ", orderNo=" + getOrderNo() +
            ", valid='" + getValid() + "'" +
            ", menuImageId='" + getMenuImageId() + "'" +
            "}";
    }
}
