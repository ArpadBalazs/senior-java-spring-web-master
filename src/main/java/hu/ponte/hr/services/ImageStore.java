package hu.ponte.hr.services;

import hu.ponte.hr.controller.dto.ImageMeta;
import hu.ponte.hr.controller.dto.ImageRegisterCommand;
import hu.ponte.hr.domain.Image;
import hu.ponte.hr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class ImageStore {

    private ImageRepository imageRepository;
    private final SignService signService;

    @Autowired
    public ImageStore(ImageRepository imageRepository, SignService signService) {
        this.imageRepository = imageRepository;
        this.signService = signService;
    }

    public void saveImage(MultipartFile file, ImageRegisterCommand imageRegisterCommand) throws Exception {
        Path path = new File(file.getName()).toPath();
        String mimeType = Files.probeContentType(path);
        imageRegisterCommand.setMimeType(mimeType);
        imageRegisterCommand.setName(file.getName());
        imageRegisterCommand.setSize(file.getSize());
        imageRegisterCommand.setPhoto(file.getBytes());
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(signService.getPrivateKey());
        signature.update(imageRegisterCommand.getPhoto());
        signature.initVerify(signService.getPublicKey());
        signature.update(imageRegisterCommand.getPhoto());
        String originalInput = signature.toString();
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        imageRegisterCommand.setDigitalSign(encodedString);
        Image image=new Image(imageRegisterCommand);
        imageRepository.save(image);
    }
    public ImageMeta getImageWithGivenId(String id){
        Image image=imageRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
        return ImageMeta.builder().id(image.getId().toString()).name(image.getName()).mimeType(image.getMimeType()).digitalSign(image.getDigitalSign()).photo(image.getPhoto()).build();
    }

    public List<ImageMeta> getAllImages(){
        List<ImageMeta> imageMetas=new ArrayList<>();
        for (Image image : imageRepository.findAll()) {
            imageMetas.add(ImageMeta.builder().id(image.getId().toString()).name(image.getName()).mimeType(image.getMimeType()).digitalSign(image.getDigitalSign()).photo(image.getPhoto()).build());
        }
        return imageMetas;
    }
}
