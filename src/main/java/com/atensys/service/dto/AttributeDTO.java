package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Attribute} entity.
 */
@Schema(description = "속성(전역,회사,오피스,층,좌석,회의실,라커 등)")
public class AttributeDTO implements Serializable {

    private Long id;

    /**
     * 속성명
     */
    @NotNull
    @Size(max = 128)
    @Schema(description = "속성명", required = true)
    private String key;

    /**
     * 속성값
     */
    @NotNull
    @Size(max = 256)
    @Schema(description = "속성값", required = true)
    private String value;

    private DictionaryDTO dictionary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DictionaryDTO getDictionary() {
        return dictionary;
    }

    public void setDictionary(DictionaryDTO dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeDTO)) {
            return false;
        }

        AttributeDTO attributeDTO = (AttributeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attributeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            ", dictionary=" + getDictionary() +
            "}";
    }
}
