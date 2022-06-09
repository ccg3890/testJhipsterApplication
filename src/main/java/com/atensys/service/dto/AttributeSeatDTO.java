package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atensys.domain.AttributeSeat} entity.
 */
@Schema(description = "좌석속성")
public class AttributeSeatDTO implements Serializable {

    private Long id;

    private SeatDTO seat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeatDTO getSeat() {
        return seat;
    }

    public void setSeat(SeatDTO seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeSeatDTO)) {
            return false;
        }

        AttributeSeatDTO attributeSeatDTO = (AttributeSeatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attributeSeatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeSeatDTO{" +
            "id=" + getId() +
            ", seat=" + getSeat() +
            "}";
    }
}
