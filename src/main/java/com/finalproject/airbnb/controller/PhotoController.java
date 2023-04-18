package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.DeleteAllPhotosDTO;
import com.finalproject.airbnb.model.DTOs.DeletePhotoDTO;
import com.finalproject.airbnb.model.DTOs.PhotoDTO;
import com.finalproject.airbnb.service.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
public class PhotoController extends AbstractController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/properties/{id}/photos")
    public List<PhotoDTO> uploadPhoto(@PathVariable int id, @RequestParam("files") MultipartFile[] files, HttpSession s){
        return photoService.upload(id, files, getLoggedId(s));
    }

    @SneakyThrows
    @GetMapping("/properties/{id}/photos/{fileName}")
    public void viewPhoto(@PathVariable("id") int id, @PathVariable String fileName, HttpServletResponse resp){
            File f = photoService.viewPhoto(id, fileName);
            Files.copy(f.toPath(), resp.getOutputStream());
    }

//    @DeleteMapping("/properties/{id}/photos/{id2}") without properties
    public DeletePhotoDTO deletePhotoById(@PathVariable("id") int id, @PathVariable("id2") int id2, HttpSession s){
        return  photoService.deletePhotoById(id, id2, getLoggedId(s));
    }

//    @DeleteMapping("/properties/{id}/photos/all") without properties
    public DeleteAllPhotosDTO deleteAllPhotos(@PathVariable("id") int id, HttpSession s){
        return  photoService.deleteAllPhotos(id, getLoggedId(s));
    }
}
