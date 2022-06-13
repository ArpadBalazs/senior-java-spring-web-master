package hu.ponte.hr.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRegisterCommand {
    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;
    private byte[] photo;
}
