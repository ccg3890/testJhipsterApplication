package com.atensys.service.dto;

import com.atensys.domain.enumeration.AttributeType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Archetype} entity.
 */
@Schema(description = "원형")
public class ArchetypeDTO implements Serializable {

    private Long id;

    /**
     * 속성종류
     */
    @NotNull
    @Schema(description = "속성종류", required = true)
    private AttributeType attributeType;

    /**
     * 필수여부
     */
    @NotNull
    @Schema(description = "필수여부", required = true)
    private Boolean mandantory;

    /**
     * 태그
     */
    @Size(max = 256)
    @Schema(description = "태그")
    private String tag;

    private Set<DictionaryDTO> dictionaries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public Boolean getMandantory() {
        return mandantory;
    }

    public void setMandantory(Boolean mandantory) {
        this.mandantory = mandantory;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<DictionaryDTO> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Set<DictionaryDTO> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArchetypeDTO)) {
            return false;
        }

        ArchetypeDTO archetypeDTO = (ArchetypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, archetypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArchetypeDTO{" +
            "id=" + getId() +
            ", attributeType='" + getAttributeType() + "'" +
            ", mandantory='" + getMandantory() + "'" +
            ", tag='" + getTag() + "'" +
            ", dictionaries=" + getDictionaries() +
            "}";
    }
}
