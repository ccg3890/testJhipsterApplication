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
 * 회사
 */
@Entity
@Table(name = "tb_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 회사명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * 회사영문명
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "eng_name", length = 64, nullable = false)
    private String engName;

    /**
     * 회사 설명
     */
    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "company" }, allowSetters = true)
    private Set<Org> orgs = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "floors", "attributes", "company" }, allowSetters = true)
    private Set<Office> offices = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Set<AttributeCompany> attributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return this.engName;
    }

    public Company engName(String engName) {
        this.setEngName(engName);
        return this;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getDescription() {
        return this.description;
    }

    public Company description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Org> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(Set<Org> orgs) {
        if (this.orgs != null) {
            this.orgs.forEach(i -> i.setCompany(null));
        }
        if (orgs != null) {
            orgs.forEach(i -> i.setCompany(this));
        }
        this.orgs = orgs;
    }

    public Company orgs(Set<Org> orgs) {
        this.setOrgs(orgs);
        return this;
    }

    public Company addOrgs(Org org) {
        this.orgs.add(org);
        org.setCompany(this);
        return this;
    }

    public Company removeOrgs(Org org) {
        this.orgs.remove(org);
        org.setCompany(null);
        return this;
    }

    public Set<Office> getOffices() {
        return this.offices;
    }

    public void setOffices(Set<Office> offices) {
        if (this.offices != null) {
            this.offices.forEach(i -> i.setCompany(null));
        }
        if (offices != null) {
            offices.forEach(i -> i.setCompany(this));
        }
        this.offices = offices;
    }

    public Company offices(Set<Office> offices) {
        this.setOffices(offices);
        return this;
    }

    public Company addOffices(Office office) {
        this.offices.add(office);
        office.setCompany(this);
        return this;
    }

    public Company removeOffices(Office office) {
        this.offices.remove(office);
        office.setCompany(null);
        return this;
    }

    public Set<AttributeCompany> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<AttributeCompany> attributeCompanies) {
        if (this.attributes != null) {
            this.attributes.forEach(i -> i.setCompany(null));
        }
        if (attributeCompanies != null) {
            attributeCompanies.forEach(i -> i.setCompany(this));
        }
        this.attributes = attributeCompanies;
    }

    public Company attributes(Set<AttributeCompany> attributeCompanies) {
        this.setAttributes(attributeCompanies);
        return this;
    }

    public Company addAttributes(AttributeCompany attributeCompany) {
        this.attributes.add(attributeCompany);
        attributeCompany.setCompany(this);
        return this;
    }

    public Company removeAttributes(AttributeCompany attributeCompany) {
        this.attributes.remove(attributeCompany);
        attributeCompany.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
