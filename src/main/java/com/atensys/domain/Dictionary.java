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
 * 키워드사전 (키워드 등록 대상 식별은 special/general 여부로 판단)
 */
@Entity
@Table(name = "tb_dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * key
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "jhi_key", length = 128, nullable = false, unique = true)
    private String key;

    /**
     * 키워드명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * 설명
     */
    @Size(max = 1024)
    @Column(name = "discription", length = 1024)
    private String discription;

    /**
     * 체크로직(regx)
     */
    @Size(max = 512)
    @Column(name = "check_pattern", length = 512)
    private String checkPattern;

    /**
     * default
     */
    @Size(max = 256)
    @Column(name = "default_value", length = 256)
    private String defaultValue;

    /**
     * 순서
     */
    @Min(value = 0)
    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 사용여부
     */
    @Column(name = "valid")
    private Boolean valid;

    @ManyToMany(mappedBy = "dictionaries")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dictionaries" }, allowSetters = true)
    private Set<Archetype> archetypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dictionary id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public Dictionary key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public Dictionary name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return this.discription;
    }

    public Dictionary discription(String discription) {
        this.setDiscription(discription);
        return this;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getCheckPattern() {
        return this.checkPattern;
    }

    public Dictionary checkPattern(String checkPattern) {
        this.setCheckPattern(checkPattern);
        return this;
    }

    public void setCheckPattern(String checkPattern) {
        this.checkPattern = checkPattern;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public Dictionary defaultValue(String defaultValue) {
        this.setDefaultValue(defaultValue);
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public Dictionary orderNo(Integer orderNo) {
        this.setOrderNo(orderNo);
        return this;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public Dictionary valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Set<Archetype> getArchetypes() {
        return this.archetypes;
    }

    public void setArchetypes(Set<Archetype> archetypes) {
        if (this.archetypes != null) {
            this.archetypes.forEach(i -> i.removeDictionary(this));
        }
        if (archetypes != null) {
            archetypes.forEach(i -> i.addDictionary(this));
        }
        this.archetypes = archetypes;
    }

    public Dictionary archetypes(Set<Archetype> archetypes) {
        this.setArchetypes(archetypes);
        return this;
    }

    public Dictionary addArchetype(Archetype archetype) {
        this.archetypes.add(archetype);
        archetype.getDictionaries().add(this);
        return this;
    }

    public Dictionary removeArchetype(Archetype archetype) {
        this.archetypes.remove(archetype);
        archetype.getDictionaries().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dictionary)) {
            return false;
        }
        return id != null && id.equals(((Dictionary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dictionary{" +
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
