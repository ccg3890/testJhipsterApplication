package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.RoomSeat} entity.
 */
@Schema(description = "회의실 좌석")
public class RoomSeatDTO implements Serializable {

    private Long id;

    /**
     * 회의실 좌석명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "회의실 좌석명", required = true)
    private String name;

    /**
     * 회의실 좌석 설명
     */
    @Size(max = 1024)
    @Schema(description = "회의실 좌석 설명")
    private String description;

    /**
     * Y좌표
     */
    @Min(value = 0L)
    @Schema(description = "Y좌표")
    private Long top;

    /**
     * X좌표
     */
    @Min(value = 0L)
    @Schema(description = "X좌표")
    private Long left;

    /**
     * 사용가능여부
     */
    @NotNull
    @Schema(description = "사용가능여부", required = true)
    private Boolean valid;

    private RoomDTO room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTop() {
        return top;
    }

    public void setTop(Long top) {
        this.top = top;
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
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
        if (!(o instanceof RoomSeatDTO)) {
            return false;
        }

        RoomSeatDTO roomSeatDTO = (RoomSeatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roomSeatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomSeatDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", top=" + getTop() +
            ", left=" + getLeft() +
            ", valid='" + getValid() + "'" +
            ", room=" + getRoom() +
            "}";
    }
}
