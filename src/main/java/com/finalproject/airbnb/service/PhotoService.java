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
import org.springframework.web.bind.annotation.RequestParam;
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
    @Transactional
    public List<PhotoDTO> upload(int id, MultipartFile[] files, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
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

    public List<PhotoDTO> listAllPhotos(int id){
        if(propertyRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Property not found!");
        }
        List<PhotoDTO> photos = photosRepository.findAllByPropertyId(id)
                .stream()
                .map(p -> mapper.map(p, PhotoDTO.class))
                .collect(Collectors.toList());
        return photos;
    }



    public File viewPhoto(int id, String name) {
        if (propertyRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Property not found!");
        }
        name = "uploads" + File.separator + name;
        File f = new File(name);
        if (f.exists()) {
            return f;
        }
        throw new NotFoundException("File not found");
    }

    public DeletePhotoDTO deletePhotoById(int id, int loggedId) {
        User u = getUserById(loggedId);
        Photos photo = photosRepository.findById(id).orElseThrow(() -> new NotFoundException("Photo not found"));
        List<Property> properties = propertyRepository.findAllByOwner(u);
        if (properties.isEmpty()) {
            throw new NotFoundException("No property found for this user!");
        }
        for (Property property : properties) {
            List<Photos> photos = photosRepository.findByProperty(property);
            for (Photos p : photos)
                if (photo.equals(p)) {
                    photosRepository.deleteById(id);
                    return new DeletePhotoDTO();
                }
        }
        throw new BadRequestException("Photo does not belong to the property");
    }

    @Transactional
    public DeleteAllPhotosDTO deleteAllPhotos(int id, int loggedId) {
        User u = getUserById(loggedId);
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
        if (!propertyRepository.userOwnsProperty(u.getId(), property.getId())) {
            throw new UnauthorizedException("Property is not owned by the user!");
        }
        if(photosRepository.findAllByPropertyId(id).isEmpty()){
            throw new NotFoundException("Photos do not exist!");
        }
        photosRepository.deleteAllByPropertyId(id);
        return new DeleteAllPhotosDTO();
    }
}