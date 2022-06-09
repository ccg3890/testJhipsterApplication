package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Fileinfo} entity.
 */
@Schema(description = "파일정보")
public class FileinfoDTO implements Serializable {

    private Long id;

    /**
     * 원래 파일명
     */
    @NotNull
    @Size(max = 256)
    @Schema(description = "원래 파일명", required = true)
    private String origFileName;

    /**
     * 저장된 파일명
     */
    @NotNull
    @Size(max = 256)
    @Schema(description = "저장된 파일명", required = true)
    private String fileName;

    /**
     * 파일 저장 위치
     */
    @NotNull
    @Size(max = 1024)
    @Schema(description = "파일 저장 위치", required = true)
    private String filePath;

    /**
     * Multipurpose Internet Mail Extensions 종류
     */
    @Size(max = 128)
    @Schema(description = "Multipurpose Internet Mail Extensions 종류")
    private String mimeType;

    /**
     * size
     */
    @Min(value = 0L)
    @Schema(description = "size")
    private Long fileSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigFileName() {
        return origFileName;
    }

    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileinfoDTO)) {
            return false;
        }

        FileinfoDTO fileinfoDTO = (FileinfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileinfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileinfoDTO{" +
            "id=" + getId() +
            ", origFileName='" + getOrigFileName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", fileSize=" + getFileSize() +
            "}";
    }
}
