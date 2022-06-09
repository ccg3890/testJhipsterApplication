package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.RoomManager} entity.
 */
@Schema(description = "회의실 관리자")
public class RoomManagerDTO implements Serializable {

    private Long id;

    /**
     * 관리자ID
     */
    @Size(max = 32)
    @Schema(description = "관리자ID")
    private String managerId;

    /**
     * 관리자명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "관리자명", required = true)
    private String name;

    private RoomDTO room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof RoomManagerDTO)) {
            return false;
        }

        RoomManagerDTO roomManagerDTO = (RoomManagerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roomManagerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomManagerDTO{" +
            "id=" + getId() +
            ", managerId='" + getManagerId() + "'" +
            ", name='" + getName() + "'" +
            ", room=" + getRoom() +
            "}";
    }
}
