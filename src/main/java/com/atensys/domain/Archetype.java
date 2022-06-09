package com.atensys.domain;

import com.atensys.domain.enumeration.AttributeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 원형
 */
@Entity
@Table(name = "tb_archetype")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Archetype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 속성종류
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "attribute_type", nullable = false, unique = true)
    private AttributeType attributeType;

    /**
     * 필수여부
     */
    @NotNull
    @Column(name = "mandantory", nullable = false)
    private Boolean mandantory;

    /**
     * 태그
     */
    @Size(max = 256)
    @Column(name = "tag", length = 256)
    private String tag;

    @ManyToMany
    @JoinTable(
        name = "rel_tb_archetype__dictionary",
        joinColumns = @JoinColumn(name = "tb_archetype_id"),
        inverseJoinColumns = @JoinColumn(name = "dictionary_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "archetypes" }, allowSetters = true)
    private Set<Dictionary> dictionaries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Archetype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttributeType getAttributeType() {
        return this.attributeType;
    }

    public Archetype attributeType(AttributeType attributeType) {
        this.setAttributeType(attributeType);
        return this;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public Boolean getMandantory() {
        return this.mandantory;
    }

    public Archetype mandantory(Boolean mandantory) {
        this.setMandantory(mandantory);
        return this;
    }

    public void setMandantory(Boolean mandantory) {
        this.mandantory = mandantory;
    }

    public String getTag() {
        return this.tag;
    }

    public Archetype tag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<Dictionary> getDictionaries() {
        return this.dictionaries;
    }

    public void setDictionaries(Set<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public Archetype dictionaries(Set<Dictionary> dictionaries) {
        this.setDictionaries(dictionaries);
        return this;
    }

    public Archetype addDictionary(Dictionary dictionary) {
        this.dictionaries.add(dictionary);
        dictionary.getArchetypes().add(this);
        return this;
    }

    public Archetype removeDictionary(Dictionary dictionary) {
        this.dictionaries.remove(dictionary);
        dictionary.getArchetypes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Archetype)) {
            return false;
        }
        return id != null && id.equals(((Archetype) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Archetype{" +
            "id=" + getId() +
            ", attributeType='" + getAttributeType() + "'" +
            ", mandantory='" + getMandantory() + "'" +
            ", tag='" + getTag() + "'" +
            "}";
    }
}
