package com.atensys.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 파일정보
 */
@Entity
@Table(name = "tb_fileinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fileinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 원래 파일명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "orig_file_name", length = 256, nullable = false)
    private String origFileName;

    /**
     * 저장된 파일명
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "file_name", length = 256, nullable = false)
    private String fileName;

    /**
     * 파일 저장 위치
     */
    @NotNull
    @Size(max = 1024)
    @Column(name = "file_path", length = 1024, nullable = false)
    private String filePath;

    /**
     * Multipurpose Internet Mail Extensions 종류
     */
    @Size(max = 128)
    @Column(name = "mime_type", length = 128)
    private String mimeType;

    /**
     * size
     */
    @Min(value = 0L)
    @Column(name = "file_size")
    private Long fileSize;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fileinfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigFileName() {
        return this.origFileName;
    }

    public Fileinfo origFileName(String origFileName) {
        this.setOrigFileName(origFileName);
        return this;
    }

    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Fileinfo fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Fileinfo filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Fileinfo mimeType(String mimeType) {
        this.setMimeType(mimeType);
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Fileinfo fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fileinfo)) {
            return false;
        }
        return id != null && id.equals(((Fileinfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fileinfo{" +
            "id=" + getId() +
            ", origFileName='" + getOrigFileName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", fileSize=" + getFileSize() +
            "}";
    }
}
