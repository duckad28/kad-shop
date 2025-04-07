package com.duckad.kadshop.service.image;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.duckad.kadshop.dto.ImageDto;
import com.duckad.kadshop.exception.ResourceNotFoundException;
import com.duckad.kadshop.model.Image;
import com.duckad.kadshop.model.Product;
import com.duckad.kadshop.repository.image.ImageRepository;
import com.duckad.kadshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {throw new ResourceNotFoundException("Image not found");});
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }


    @Override
    public void updateImage(MultipartFile image, Long imageId) {
        Image img = getImageById(imageId);
        try {
            img.setFileName(image.getOriginalFilename());
            img.setFileType(image.getContentType());
            img.setImage(new SerialBlob(image.getBytes()));
            imageRepository.save(img);
        } catch (Exception e) {

        }
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> image, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> res = new ArrayList<>();
        for (MultipartFile file : image) {
            try {
                Image img = new Image();
                img.setFileName(file.getOriginalFilename());
                img.setFileType(file.getContentType());
                img.setImage(new SerialBlob(file.getBytes()));
                img.setProduct(product);

                String downloadUrl = "/api/v1/images/image/download/";

                img.setDownloadUrl(downloadUrl);
                Image savedImg = imageRepository.save(img);
                savedImg.setDownloadUrl(downloadUrl + savedImg.getId());
                imageRepository.save(savedImg);

                ImageDto imgDto = new ImageDto();
                imgDto.setId(savedImg.getId());
                imgDto.setDownloadUrl(savedImg.getDownloadUrl());
                imgDto.setName(savedImg.getFileName());

                res.add(imgDto);
            } catch (Exception e) {

            }
        }
        return res;
    }


}
