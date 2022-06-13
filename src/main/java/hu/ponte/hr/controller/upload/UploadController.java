package hu.ponte.hr.controller.upload;

import hu.ponte.hr.controller.dto.ImageRegisterCommand;
import hu.ponte.hr.services.ImageStore;
import hu.ponte.hr.services.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.util.Base64;

@RestController
@Component
@RequestMapping("api/file")
public class UploadController
{

    private final ImageStore imageStore;

    @Autowired
    public UploadController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(@RequestParam("file") MultipartFile file, ImageRegisterCommand imageRegisterCommand) throws Exception {
            imageStore.saveImage(file, imageRegisterCommand);
            return "ok";

    }
}
