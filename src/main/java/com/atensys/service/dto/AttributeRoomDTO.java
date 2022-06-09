package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atensys.domain.AttributeRoom} entity.
 */
@Schema(description = "회의실속성")
public class AttributeRoomDTO implements Serializable {

    private Long id;

    private RoomDTO room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeRoomDTO)) {
            return false;
        }

        AttributeRoomDTO attributeRoomDTO = (AttributeRoomDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attributeRoomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeRoomDTO{" +
            "id=" + getId() +
            ", room=" + getRoom() +
            "}";
    }
}
