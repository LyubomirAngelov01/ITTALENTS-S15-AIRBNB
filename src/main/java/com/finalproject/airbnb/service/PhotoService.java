package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.DeleteAllPhotosDTO;
import com.finalproject.airbnb.model.DTOs.DeletePhotoDTO;
import com.finalproject.airbnb.model.DTOs.PhotoDTO;
import com.finalproject.airbnb.model.entities.Photos;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhotoService extends AbstractService {

    @SneakyThrows
    public List<PhotoDTO> upload(int id, MultipartFile[] files, int loggedId) {
        User u = getUserById(loggedId);
        Property property = validatePhoto(id, u);
        if (photosRepository.findAllByPropertyId(id).size() > 10) {
            List <Photos> photos = photosRepository.findAllByPropertyId(id);
            return photos.stream()
                    .map(p -> mapper.map(p, PhotoDTO.class))
                    .collect(Collectors.toList());
        }
            if (files.length < 5) {
                throw new BadRequestException("Minimum of 5 pictures!");
            }
            for (MultipartFile photo : files) {
                String contentType = photo.getContentType();
                if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                    throw new BadRequestException("Only JPEG and PNG files are allowed.");
                }
            }
            List<Photos> photoEntities = new ArrayList<>();
            for (MultipartFile photo : files) {
                Photos photos = new Photos();
                String name = UUID.randomUUID().toString();
                String ext = FilenameUtils.getExtension(photo.getOriginalFilename());

                name = name + "." + ext;
                File dir = new File("uploads");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File f = new File(dir, name);
                Files.copy(photo.getInputStream(), f.toPath());

                String path = dir.getName() + File.separator + f.getName();
                photos.setPhotoUrl(path);
                photos.setProperty(property);
                photosRepository.save(photos);
                photoEntities.add(photos);
            }
            return photoEntities.stream()
                    .map(p -> mapper.map(p, PhotoDTO.class))
                    .collect(Collectors.toList());
        }


    public File viewPhoto(int id, String name) {
        if(propertyRepository.findById(id).isEmpty()){
            throw new NotFoundException("Property not found!");
        }
        name = "uploads" + File.separator + name;
        File f = new File(name);
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }

    public DeletePhotoDTO deletePhotoById(int id, int id2, int loggedId) {
        User u = getUserById(loggedId);
        validatePhoto(id, u);
        if(photosRepository.findById(id2).isEmpty()){
            throw new NotFoundException("Photo does not exist!");
        }
        photosRepository.deleteById(id2);
        return new DeletePhotoDTO();
    }

    @Transactional
    public DeleteAllPhotosDTO deleteAllPhotos(int id, int loggedId){
        User u = getUserById(loggedId);
        validatePhoto(id, u);
        if(photosRepository.findAllByPropertyId(id).isEmpty()){
            throw new NotFoundException("Photos do not exist!");
        }
        photosRepository.deleteAllByPropertyId(id);
        return new DeleteAllPhotosDTO();
    }

    public Property validatePhoto(int id, User u){
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
        return property;
    }


}
