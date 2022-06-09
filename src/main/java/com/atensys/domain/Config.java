package com.atensys.domain;

import com.atensys.domain.enumeration.CodeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 설정
 */
@Entity
@Table(name = "tb_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 설정ID
     */
    @NotNull
    @Column(name = "config_id", nullable = false, unique = true)
    private Long configId;

    /**
     * 부모ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 코드
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    /**
     * 코드타입
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "code_type", nullable = false)
    private CodeType codeType;

    /**
     * 값
     */
    @NotNull
    @Size(max = 2048)
    @Column(name = "value", length = 2048, nullable = false)
    private String value;

    /**
     * 설명
     */
    @Size(max = 2048)
    @Column(name = "description", length = 2048)
    private String description;

    /**
     * 사용여부
     */
    @Column(name = "use_yn")
    private Boolean useYn;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent" }, allowSetters = true)
    private Set<Config> children = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "children", "parent" }, allowSetters = true)
    private Config parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Config id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConfigId() {
        return this.configId;
    }

    public Config configId(Long configId) {
        this.setConfigId(configId);
        return this;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Config parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return this.code;
    }

    public Config code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CodeType getCodeType() {
        return this.codeType;
    }

    public Config codeType(CodeType codeType) {
        this.setCodeType(codeType);
        return this;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public String getValue() {
        return this.value;
    }

    public Config value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public Config description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUseYn() {
        return this.useYn;
    }

    public Config useYn(Boolean useYn) {
        this.setUseYn(useYn);
        return this;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public Set<Config> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Config> configs) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (configs != null) {
            configs.forEach(i -> i.setParent(this));
        }
        this.children = configs;
    }

    public Config children(Set<Config> configs) {
        this.setChildren(configs);
        return this;
    }

    public Config addChildren(Config config) {
        this.children.add(config);
        config.setParent(this);
        return this;
    }

    public Config removeChildren(Config config) {
        this.children.remove(config);
        config.setParent(null);
        return this;
    }

    public Config getParent() {
        return this.parent;
    }

    public void setParent(Config config) {
        this.parent = config;
    }

    public Config parent(Config config) {
        this.setParent(config);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Config)) {
            return false;
        }
        return id != null && id.equals(((Config) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Config{" +
            "id=" + getId() +
            ", configId=" + getConfigId() +
            ", parentId=" + getParentId() +
            ", code='" + getCode() + "'" +
            ", codeType='" + getCodeType() + "'" +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
