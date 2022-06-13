package hu.ponte.hr.controller;


import hu.ponte.hr.controller.dto.ImageMeta;
import hu.ponte.hr.domain.Image;
import hu.ponte.hr.services.ImageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

    private final ImageStore imageStore;

    @Autowired
    public ImagesController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
        //	return Collections.emptyList();
        return imageStore.getAllImages();
    }

    @GetMapping("preview/{id}")
    public byte[] getImage(@PathVariable("id") String id, HttpServletResponse response) {
        Image image=imageStore.getImageWithGivenId(id);
        byte[] bytes=image.getPhoto();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(image.getMimeType());
        return bytes;
    }
}
