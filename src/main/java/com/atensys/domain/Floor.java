package com.atensys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 층
 */
@Entity
@Table(name = "tb_floor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Floor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 층 이름
     */
    @Size(max = 50)
    @Column(name = "floor_name", length = 50)
    private String floorName;

    /**
     * 층 설명
     */
    @Size(max = 500)
    @Column(name = "floor_desc", length = 500)
    private String floorDesc;

    /**
     * 사용유무
     */
    @Column(name = "use_yn")
    private Boolean useYn;

    /**
     * 정렬 순서
     */
    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 도면 이미지
     */
    @Size(max = 36)
    @Column(name = "background_image_id", length = 36)
    private String backgroundImageId;

    /**
     * SVG 이미지
     */
    @Size(max = 36)
    @Column(name = "svg_image", length = 36)
    private String svgImage;

    @OneToMany(mappedBy = "floor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "floor" }, allowSetters = true)
    private Set<Resource> resources = new HashSet<>();

    @OneToMany(mappedBy = "floor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "floor" }, allowSetters = true)
    private Set<AttributeFloor> attributes = new HashSet<>();

    @ManyToOne
    private Fileinfo webImg;

    @ManyToOne
    private Fileinfo kioskImg;

    @ManyToOne
    private Fileinfo miniImg;

    @ManyToOne
    @JsonIgnoreProperties(value = { "drawingItems" }, allowSetters = true)
    private Drawing drawing;

    @ManyToOne
    @JsonIgnoreProperties(value = { "floors", "attributes", "company" }, allowSetters = true)
    private Office office;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Floor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorName() {
        return this.floorName;
    }

    public Floor floorName(String floorName) {
        this.setFloorName(floorName);
        return this;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorDesc() {
        return this.floorDesc;
    }

    public Floor floorDesc(String floorDesc) {
        this.setFloorDesc(floorDesc);
        return this;
    }

    public void setFloorDesc(String floorDesc) {
        this.floorDesc = floorDesc;
    }

    public Boolean getUseYn() {
        return this.useYn;
    }

    public Floor useYn(Boolean useYn) {
        this.setUseYn(useYn);
        return this;
    }

    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public Floor orderNo(Integer orderNo) {
        this.setOrderNo(orderNo);
        return this;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getBackgroundImageId() {
        return this.backgroundImageId;
    }

    public Floor backgroundImageId(String backgroundImageId) {
        this.setBackgroundImageId(backgroundImageId);
        return this;
    }

    public void setBackgroundImageId(String backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
    }

    public String getSvgImage() {
        return this.svgImage;
    }

    public Floor svgImage(String svgImage) {
        this.setSvgImage(svgImage);
        return this;
    }

    public void setSvgImage(String svgImage) {
        this.svgImage = svgImage;
    }

    public Set<Resource> getResources() {
        return this.resources;
    }

    public void setResources(Set<Resource> resources) {
        if (this.resources != null) {
            this.resources.forEach(i -> i.setFloor(null));
        }
        if (resources != null) {
            resources.forEach(i -> i.setFloor(this));
        }
        this.resources = resources;
    }

    public Floor resources(Set<Resource> resources) {
        this.setResources(resources);
        return this;
    }

    public Floor addResource(Resource resource) {
        this.resources.add(resource);
        resource.setFloor(this);
        return this;
    }

    public Floor removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.setFloor(null);
        return this;
    }

    public Set<AttributeFloor> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<AttributeFloor> attributeFloors) {
        if (this.attributes != null) {
            this.attributes.forEach(i -> i.setFloor(null));
        }
        if (attributeFloors != null) {
            attributeFloors.forEach(i -> i.setFloor(this));
        }
        this.attributes = attributeFloors;
    }

    public Floor attributes(Set<AttributeFloor> attributeFloors) {
        this.setAttributes(attributeFloors);
        return this;
    }

    public Floor addAttributes(AttributeFloor attributeFloor) {
        this.attributes.add(attributeFloor);
        attributeFloor.setFloor(this);
        return this;
    }

    public Floor removeAttributes(AttributeFloor attributeFloor) {
        this.attributes.remove(attributeFloor);
        attributeFloor.setFloor(null);
        return this;
    }

    public Fileinfo getWebImg() {
        return this.webImg;
    }

    public void setWebImg(Fileinfo fileinfo) {
        this.webImg = fileinfo;
    }

    public Floor webImg(Fileinfo fileinfo) {
        this.setWebImg(fileinfo);
        return this;
    }

    public Fileinfo getKioskImg() {
        return this.kioskImg;
    }

    public void setKioskImg(Fileinfo fileinfo) {
        this.kioskImg = fileinfo;
    }

    public Floor kioskImg(Fileinfo fileinfo) {
        this.setKioskImg(fileinfo);
        return this;
    }

    public Fileinfo getMiniImg() {
        return this.miniImg;
    }

    public void setMiniImg(Fileinfo fileinfo) {
        this.miniImg = fileinfo;
    }

    public Floor miniImg(Fileinfo fileinfo) {
        this.setMiniImg(fileinfo);
        return this;
    }

    public Drawing getDrawing() {
        return this.drawing;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public Floor drawing(Drawing drawing) {
        this.setDrawing(drawing);
        return this;
    }

    public Office getOffice() {
        return this.office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Floor office(Office office) {
        this.setOffice(office);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Floor)) {
            return false;
        }
        return id != null && id.equals(((Floor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Floor{" +
            "id=" + getId() +
            ", floorName='" + getFloorName() + "'" +
            ", floorDesc='" + getFloorDesc() + "'" +
            ", useYn='" + getUseYn() + "'" +
            ", orderNo=" + getOrderNo() +
            ", backgroundImageId='" + getBackgroundImageId() + "'" +
            ", svgImage='" + getSvgImage() + "'" +
            "}";
    }
}
