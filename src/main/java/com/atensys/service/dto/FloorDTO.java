package com.atensys.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.atensys.domain.Floor} entity.
 */
@Schema(description = "층")
public class FloorDTO implements Serializable {

    private Long id;

    /**
     * 층 이름
     */
    @Size(max = 50)
    @Schema(description = "층 이름")
    private String floorName;

    /**
     * 층 설명
     */
    @Size(max = 500)
    @Schema(description = "층 설명")
    private String floorDesc;

    /**
     * 사용유무
     */
    @Schema(description = "사용유무")
    private Boolean useYn;

    /**
     * 정렬 순서
     */
    @Schema(description = "정렬 순서")
    private Integer orderNo;

    /**
     * 도면 이미지
     */
    @Size(max = 36)
    @Schema(description = "도면 이미지")
    private String backgroundImageId;

    /**
     * SVG 이미지
     */
    @Size(max = 36)
    @Schema(description = "SVG 이미지")
    private String svgImage;

    private FileinfoDTO webImg;

    private FileinfoDTO kioskImg;

    private FileinfoDTO miniImg;

    private DrawingDTO drawing;

    private OfficeDTO office;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorDesc() {
        return floorDesc;
    }

    public void setFloorDesc(String floorDesc) {
        this.floorDesc = floorDesc;
    }

    public Boolean getUseYn() {
        return useYn;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(String backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
    }

    public String getSvgImage() {
        return svgImage;
    }

    public void setSvgImage(String svgImage) {
        this.svgImage = svgImage;
    }

    public FileinfoDTO getWebImg() {
        return webImg;
    }

    public void setWebImg(FileinfoDTO webImg) {
        this.webImg = webImg;
    }

    public FileinfoDTO getKioskImg() {
        return kioskImg;
    }

    public void setKioskImg(FileinfoDTO kioskImg) {
        this.kioskImg = kioskImg;
    }

    public FileinfoDTO getMiniImg() {
        return miniImg;
    }

    public void setMiniImg(FileinfoDTO miniImg) {
        this.miniImg = miniImg;
    }

    public DrawingDTO getDrawing() {
        return drawing;
    }

    public void setDrawing(DrawingDTO drawing) {
        this.drawing = drawing;
    }

    public OfficeDTO getOffice() {
        return office;
    }

    public void setOffice(OfficeDTO office) {
        this.office = office;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloorDTO)) {
            return false;
        }

        FloorDTO floorDTO = (FloorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, floorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloorDTO{" +
            "id=" + getId() +
            ", floorName='" + getFloorName() + "'" +
            ", floorDesc='" + getFloorDesc() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", orderNo=" + getOrderNo() +
            ", backgroundImageId='" + getBackgroundImageId() + "'" +
            ", svgImage='" + getSvgImage() + "'" +
            ", webImg=" + getWebImg() +
            ", kioskImg=" + getKioskImg() +
            ", miniImg=" + getMiniImg() +
            ", drawing=" + getDrawing() +
            ", office=" + getOffice() +
            "}";
    }
}
