package hu.ponte.hr.domain;

import hu.ponte.hr.controller.dto.ImageRegisterCommand;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "mimeType")
    private String mimeType;
    @Column(name = "size")
    private long size;
    @Column(name = "digitalSign")
    private String digitalSign;
    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    private byte[] photo;

    public Image() {
    }

    public Image(ImageRegisterCommand imageRegisterCommand) {
        this.name = imageRegisterCommand.getName();
        this.mimeType = imageRegisterCommand.getMimeType();
        this.size = imageRegisterCommand.getSize();
        this.digitalSign = imageRegisterCommand.getDigitalSign();
        this.photo = imageRegisterCommand.getPhoto();
    }

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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDigitalSign() {
        return digitalSign;
    }

    public void setDigitalSign(String digitalSign) {
        this.digitalSign = digitalSign;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
