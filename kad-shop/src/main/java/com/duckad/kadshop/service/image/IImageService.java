package com.duckad.kadshop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.duckad.kadshop.dto.ImageDto;
import com.duckad.kadshop.model.Image;

public interface IImageService {
    List<ImageDto> saveImages(List<MultipartFile> image, Long productId);
    void deleteImage(Long id);
    Image getImageById(Long id);
    void updateImage(MultipartFile image, Long imageId);
}
