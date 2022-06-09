package com.atensys.service.dto;

import com.atensys.domain.enumeration.ResvStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Reservation} entity.
 */
@Schema(description = "예약정보")
public class ReservationDTO implements Serializable {

    private Long id;

    /**
     * 예약명
     */
    @NotNull
    @Size(max = 128)
    @Schema(description = "예약명", required = true)
    private String title;

    /**
     * 예약설명
     */
    @Size(max = 1024)
    @Schema(description = "예약설명")
    private String description;

    /**
     * 예약상태
     */
    @NotNull
    @Schema(description = "예약상태", required = true)
    private ResvStatusType status;

    /**
     * 주최자ID
     */
    @NotNull
    @Size(max = 32)
    @Schema(description = "주최자ID", required = true)
    private String organizerId;

    /**
     * 주최자명
     */
    @NotNull
    @Size(max = 64)
    @Schema(description = "주최자명", required = true)
    private String organizerName;

    /**
     * 예약시작일시
     */
    @NotNull
    @Schema(description = "예약시작일시", required = true)
    private LocalDate startDateTime;

    /**
     * 예약종료일시
     */
    @NotNull
    @Schema(description = "예약종료일시", required = true)
    private LocalDate endDateTime;

    /**
     * 온종일 사용 여부
     */
    @NotNull
    @Schema(description = "온종일 사용 여부", required = true)
    private Boolean useAllDay;

    /**
     * 반복사용여부
     */
    @NotNull
    @Schema(description = "반복사용여부", required = true)
    private Boolean useRecurrence;

    /**
     * 참석여부응답 사용여부
     */
    @NotNull
    @Schema(description = "참석여부응답 사용여부", required = true)
    private Boolean useResponse;

    /**
     * 회의실 PIN 번호
     */
    @Size(max = 128)
    @Schema(description = "회의실 PIN 번호")
    private String pin;

    private RecurrenceDTO recurrence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResvStatusType getStatus() {
        return status;
    }

    public void setStatus(ResvStatusType status) {
        this.status = status;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public LocalDate getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDate getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean getUseAllDay() {
        return useAllDay;
    }

    public void setUseAllDay(Boolean useAllDay) {
        this.useAllDay = useAllDay;
    }

    public Boolean getUseRecurrence() {
        return useRecurrence;
    }

    public void setUseRecurrence(Boolean useRecurrence) {
        this.useRecurrence = useRecurrence;
    }

    public Boolean getUseResponse() {
        return useResponse;
    }

    public void setUseResponse(Boolean useResponse) {
        this.useResponse = useResponse;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public RecurrenceDTO getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(RecurrenceDTO recurrence) {
        this.recurrence = recurrence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDTO)) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", organizerId='" + getOrganizerId() + "'" +
            ", organizerName='" + getOrganizerName() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", useAllDay='" + getUseAllDay() + "'" +
            ", useRecurrence='" + getUseRecurrence() + "'" +
            ", useResponse='" + getUseResponse() + "'" +
            ", pin='" + getPin() + "'" +
            ", recurrence=" + getRecurrence() +
            "}";
    }
}
