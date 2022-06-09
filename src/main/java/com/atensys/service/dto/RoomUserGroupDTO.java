package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.RoomUserGroup} entity.
 */
@Schema(description = "회의실 사용자 그룹")
public class RoomUserGroupDTO implements Serializable {

    private Long id;

    /**
     * 그룹(조직)ID
     */
    @NotNull
    @Size(max = 32)
    @Schema(description = "그룹(조직)ID", required = true)
    private String groupId;

    /**
     * 그룹(조직)명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "그룹(조직)명", required = true)
    private String name;

    private RoomDTO room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
        if (!(o instanceof RoomUserGroupDTO)) {
            return false;
        }

        RoomUserGroupDTO roomUserGroupDTO = (RoomUserGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roomUserGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomUserGroupDTO{" +
            "id=" + getId() +
            ", groupId='" + getGroupId() + "'" +
            ", name='" + getName() + "'" +
            ", room=" + getRoom() +
            "}";
    }
}
