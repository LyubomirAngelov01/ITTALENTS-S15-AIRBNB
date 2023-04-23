package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.DeleteAllPhotosDTO;
import com.finalproject.airbnb.model.DTOs.DeletePhotoDTO;
import com.finalproject.airbnb.model.DTOs.PhotoDTO;
import com.finalproject.airbnb.service.PhotoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.nio.file.Files;
import java.util.List;

@RestController
@SecurityRequirement(name = "JWT token")
public class PhotoController extends AbstractController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/properties/{id}/photos")
    public List<PhotoDTO> uploadPhoto(@PathVariable int id, @RequestParam("files") MultipartFile[] files, HttpServletRequest s) {
        int userId = extractUserIdFromToken(s);
        return photoService.upload(id, files, userId);
    }


    @GetMapping("/properties/{id}/photos")
    public List<PhotoDTO> viewAllPhotos(@PathVariable int id) {
        return photoService.listAllPhotos(id);
    }

    @SneakyThrows
    @GetMapping("/photos/{fileName}")
    public void viewPhoto(@PathVariable String fileName, HttpServletResponse resp) {
        File f = photoService.viewPhoto(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
    }

    @DeleteMapping("/photos/{id}")
    public DeletePhotoDTO deletePhotoById(@PathVariable("id") int id, HttpServletRequest s) {
        int userId = extractUserIdFromToken(s);
        return photoService.deletePhotoById(id, userId);
    }

    @DeleteMapping("photos/{id}/all")
    public DeleteAllPhotosDTO deleteAllPhotos(@PathVariable("id") int id, HttpServletRequest s) {
        int userId = extractUserIdFromToken(s);
        return photoService.deleteAllPhotos(id, userId);
    }
}
