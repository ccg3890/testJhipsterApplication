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
 * 권한
 */
@Entity
@Table(name = "tb_auth")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Auth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 권한명
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "auth_name", length = 100, nullable = false)
    private String authName;

    /**
     * 기본권한여부
     */
    @Column(name = "default_auth")
    private Boolean defaultAuth;

    /**
     * 사용여부
     */
    @NotNull
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    @ManyToMany
    @JoinTable(
        name = "rel_tb_auth__menu",
        joinColumns = @JoinColumn(name = "tb_auth_id"),
        inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "auths" }, allowSetters = true)
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany(mappedBy = "auths")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "penalties", "company", "org", "rank", "file", "auths" }, allowSetters = true)
    private Set<UserInfo> userInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Auth id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthName() {
        return this.authName;
    }

    public Auth authName(String authName) {
        this.setAuthName(authName);
        return this;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Boolean getDefaultAuth() {
        return this.defaultAuth;
    }

    public Auth defaultAuth(Boolean defaultAuth) {
        this.setDefaultAuth(defaultAuth);
        return this;
    }

    public void setDefaultAuth(Boolean defaultAuth) {
        this.defaultAuth = defaultAuth;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public Auth valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Set<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Auth menus(Set<Menu> menus) {
        this.setMenus(menus);
        return this;
    }

    public Auth addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getAuths().add(this);
        return this;
    }

    public Auth removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getAuths().remove(this);
        return this;
    }

    public Set<UserInfo> getUserInfos() {
        return this.userInfos;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        if (this.userInfos != null) {
            this.userInfos.forEach(i -> i.removeAuth(this));
        }
        if (userInfos != null) {
            userInfos.forEach(i -> i.addAuth(this));
        }
        this.userInfos = userInfos;
    }

    public Auth userInfos(Set<UserInfo> userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public Auth addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.getAuths().add(this);
        return this;
    }

    public Auth removeUserInfo(UserInfo userInfo) {
        this.userInfos.remove(userInfo);
        userInfo.getAuths().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auth)) {
            return false;
        }
        return id != null && id.equals(((Auth) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Auth{" +
            "id=" + getId() +
            ", authName='" + getAuthName() + "'" +
            ", defaultAuth='" + getDefaultAuth() + "'" +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
