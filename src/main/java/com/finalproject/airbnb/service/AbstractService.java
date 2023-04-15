package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.PropertyInfoDTO;
import com.finalproject.airbnb.model.entities.Photos;
import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.CountryRepository;
import com.finalproject.airbnb.model.repositories.PropertyRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class AbstractService {

    @Autowired
    protected ModelMapper mapper;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PropertyRepository propertyRepository;

    @Autowired
    protected CountryRepository countryRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @SneakyThrows
    protected Property uploadPhotos(Property property, MultipartFile[] files){
            for (MultipartFile file : files){
                Photos photo = new Photos();
                String name = UUID.randomUUID().toString();
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());

                name = name + "." + ext;
                File dir = new File("uploads");
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File f = new File(dir, name);
                Files.copy(file.getInputStream(), f.toPath());
                String path = dir.getName() + File.separator + f.getName();
                photo.setPhotoUrl(path);
                property.getPhotos().add(photo);
            }
            if(property.getPhotos().size() < 5) {
                throw new BadRequestException("Minimum of 5 photos!");
            }
            return property;
    }
}
