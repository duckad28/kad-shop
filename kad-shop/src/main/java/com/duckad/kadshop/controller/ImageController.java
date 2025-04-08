package com.duckad.kadshop.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.duckad.kadshop.dto.ImageDto;
import com.duckad.kadshop.model.Image;
import com.duckad.kadshop.response.ApiResponse;
import com.duckad.kadshop.service.image.IImageService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> postImages(@RequestParam List<MultipartFile> files,@RequestParam Long productId) {
        try {List<ImageDto> imagesDto = imageService.saveImages(files, productId);
            if (imagesDto.size() > 0) return ResponseEntity.ok(new ApiResponse("Upload success", imagesDto));
            else throw new Exception("Upload failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }
    }

    @GetMapping("image/{id}")
    public ResponseEntity<ApiResponse> getImage(@PathVariable Long id) {
        try {
            Image img = imageService.getImageById(id);
            return ResponseEntity.ok(new ApiResponse("Download Image success", img));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Download failed", e.getMessage()));
        }
    }

    @GetMapping("image/{id}/download")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) {
        Image img = imageService.getImageById(id);
        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(img.getImage().getBytes(1,(int) img.getImage().length()));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(img.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + img.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("image/{id}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long id, @RequestBody MultipartFile file) {
        try {
            imageService.updateImage(file, id);
            return ResponseEntity.ok(new ApiResponse("Update success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", e.getMessage()));
        }
    }

    @DeleteMapping("image/{id}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long id) {
        try {
            imageService.deleteImage(id);
            return ResponseEntity.ok(new ApiResponse("delete success", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed", e.getMessage()));
        }
    }
}
