package com.duckad.kadshop.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckad.kadshop.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
