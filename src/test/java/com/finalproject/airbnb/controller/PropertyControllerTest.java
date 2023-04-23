package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.model.entities.*;
import com.finalproject.airbnb.model.repositories.*;
import com.finalproject.airbnb.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {

    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private AmenitiesRepository amenitiesRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PhotosRepository photosRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void createProperty() {
        int loggedId = 1;

        PropertyInfoDTO propertyInfoDTO = new PropertyInfoDTO();
        propertyInfoDTO.setStreetAddress("123 Main St");
        propertyInfoDTO.setRegion("California");
        propertyInfoDTO.setZipCode("90001");
        propertyInfoDTO.setCity("Los Angeles");
        propertyInfoDTO.setCountryNum(1);
        propertyInfoDTO.setBeds(2);
        propertyInfoDTO.setMaxGuests(4);
        propertyInfoDTO.setPrice(100);
        propertyInfoDTO.setBathrooms(1);
        propertyInfoDTO.setBedrooms(2);
        propertyInfoDTO.setDescription("Beautiful house");
        propertyInfoDTO.setTitle("Great place to stay");
        propertyInfoDTO.setCategoryNum(1);
        propertyInfoDTO.setWifiConnection(true);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(loggedId);
        userEntity.setHost(true);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setCategoryName("Apartment");

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(1);
        countryEntity.setCountryName("United States");

        PropertyEntity propertyEntity = new PropertyEntity();

        AmenitiesEntity amenitiesEntity = new AmenitiesEntity();

        PropertyViewDTO expectedPropertyViewDTO = new PropertyViewDTO();

        when(userRepository.findById(loggedId)).thenReturn(Optional.of(userEntity));
        when(mapper.map(propertyInfoDTO, PropertyEntity.class)).thenReturn(propertyEntity);
        when(mapper.map(propertyInfoDTO, AmenitiesEntity.class)).thenReturn(amenitiesEntity);
        when(categoryRepository.findById(propertyInfoDTO.getCategoryNum())).thenReturn(Optional.of(categoryEntity));
        when(countryRepository.findById(propertyInfoDTO.getCountryNum())).thenReturn(Optional.of(countryEntity));
        when(mapper.map(propertyInfoDTO, PropertyViewDTO.class)).thenReturn(expectedPropertyViewDTO);

        PropertyViewDTO propertyViewDTO = propertyService.createProperty(propertyInfoDTO, loggedId);

        assertNotNull(propertyViewDTO);
        verify(userRepository).findById(loggedId);
        verify(propertyRepository).save(propertyEntity);
        verify(amenitiesRepository).save(amenitiesEntity);
        verify(countryRepository).save(countryEntity);
        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    void editProperty() {
        int id = 1;
        PropertyInfoDTO dto = new PropertyInfoDTO();
        int loggedId = 2;
        UserEntity u = new UserEntity();
        u.setId(loggedId);
        PropertyEntity property = new PropertyEntity();
        property.setId(id);
        property.setOwner(u);
        CategoryEntity category = new CategoryEntity();
        dto.setCategoryNum(1);
        lenient().when(categoryRepository.findById(dto.getCategoryNum())).thenReturn(Optional.of(category));
        CountryEntity country = new CountryEntity();
        dto.setCountryNum(1);
        lenient().when(countryRepository.findById(dto.getCountryNum())).thenReturn(Optional.of(country));
        AmenitiesEntity amenities = new AmenitiesEntity();
        lenient().when(amenitiesRepository.getByProperty(property)).thenReturn(amenities);
        PropertyViewDTO propertyViewDTO = new PropertyViewDTO();
        mapper.map(dto, amenities);
        mapper.map(dto, property);
        mapper.map(dto, propertyViewDTO);
        lenient().when(propertyRepository.findById(id)).thenReturn(Optional.of(property));
        lenient().when(propertyRepository.userOwnsProperty(loggedId, id)).thenReturn(true);
    }

    @Test
    public void showProperty() {
        int propertyId = 123;
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId(propertyId);
        propertyEntity.setStreetAddress("123 Main St");
        propertyEntity.setRegion("Region");
        propertyEntity.setZipCode("12345");
        propertyEntity.setCity("City");
        propertyEntity.setCountry(new CountryEntity("Country"));
        propertyEntity.setBeds(2);
        propertyEntity.setMaxGuests(4);
        propertyEntity.setPrice(100.0);
        propertyEntity.setBathrooms(2);
        propertyEntity.setBedrooms(2);
        propertyEntity.setDescription("Description");
        propertyEntity.setTitle("Title");
        propertyEntity.setCategory(new CategoryEntity());

        PropertyViewDTO propertyViewDTO = new PropertyViewDTO();
        propertyViewDTO.setStreetAddress("123 Main St");
        propertyViewDTO.setRegion("Region");
        propertyViewDTO.setZipCode("12345");
        propertyViewDTO.setCity("City");
        propertyViewDTO.setCountryName("Country");
        propertyViewDTO.setBeds(2);
        propertyViewDTO.setMaxGuests(4);
        propertyViewDTO.setPrice(100.0);
        propertyViewDTO.setBathrooms(2);
        propertyViewDTO.setBedrooms(2);
        propertyViewDTO.setDescription("Description");
        propertyViewDTO.setTitle("Title");
        propertyViewDTO.setCategoryName("Category");
        propertyViewDTO.setWifiConnection(true);
        propertyViewDTO.setPool(false);
        propertyViewDTO.setTv(true);
        propertyViewDTO.setParking(false);
        propertyViewDTO.setAirConditioning(true);
        propertyViewDTO.setBarbecue(false);
        propertyViewDTO.setSmokeAlarm(true);
        propertyViewDTO.setFirstAid(false);
        propertyViewDTO.setFireExtinguisher(true);
        propertyViewDTO.setGym(false);
        propertyViewDTO.setWasher(true);
        propertyViewDTO.setKitchen(false);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(propertyEntity));
        when(mapper.map(propertyEntity, PropertyViewDTO.class)).thenReturn(propertyViewDTO);

        PropertyViewDTO result = propertyService.showProperty(propertyId);

        assertEquals(propertyViewDTO, result);
        verify(propertyRepository, times(1)).findById(propertyId);
        verify(mapper, times(1)).map(propertyEntity, PropertyViewDTO.class);
    }

    @Test
    public void deleteProperty() {
        int userId = 1;
        int propertyId = 2;
        int amenitiesId = 3;

        UserEntity user = new UserEntity();
        user.setId(userId);

        PropertyEntity property = new PropertyEntity();
        property.setId(propertyId);

        AmenitiesEntity amenities = new AmenitiesEntity();
        amenities.setId(amenitiesId);
        property.setAmenities(amenities);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(propertyRepository.userOwnsProperty(userId, propertyId)).thenReturn(true);
        doNothing().when(photosRepository).deleteAllByPropertyId(propertyId);
        doNothing().when(amenitiesRepository).deleteById(amenitiesId);
        doNothing().when(propertyRepository).delete(property);

        DeletedPropertyDTO result = propertyService.deleteProperty(propertyId, userId);

        assertEquals("PROPERTY HAS BEEN DELETED", result.getMsg());
    }

    @Test
    void checkReviewsSuccess() {
        PropertyEntity property = new PropertyEntity();
        property.setId(1);

        ReviewEntity review1 = new ReviewEntity();
        review1.setComment("Nice place!");
        review1.setRating(4.5);

        ReviewEntity review2 = new ReviewEntity();
        review2.setComment("Great experience!");
        review2.setRating(5.0);

        List<ReviewEntity> reviews = Arrays.asList(review1, review2);

        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        when(reviewRepository.findAllByProperty(property, PageRequest.of(0, 5))).thenReturn(reviews);
        when(mapper.map(review1, ReviewInfoDTO.class)).thenReturn(new ReviewInfoDTO("Nice place!", 4.5));
        when(mapper.map(review2, ReviewInfoDTO.class)).thenReturn(new ReviewInfoDTO("Great experience!", 5.0));

        List<ReviewInfoDTO> result = propertyService.checkReviews(1, 1);

        assertEquals(reviews.size(), result.size());
        for (int i = 0; i < reviews.size(); i++) {
            assertEquals(reviews.get(i).getComment(), result.get(i).getComment());
            assertEquals(reviews.get(i).getRating(), result.get(i).getRating());
        }
    }

    @Test
    void searchSuccess() {
        PropertySearchDTO searchDTO = new PropertySearchDTO();

        PropertyEntity property1 = new PropertyEntity();

        PropertyEntity property2 = new PropertyEntity();

        List<PropertyEntity> properties = Arrays.asList(property1, property2);
        Page<PropertyEntity> propertyPage = new PageImpl<>(properties);

        when(propertyRepository.findBySearchParams(
                searchDTO.getCity(),
                searchDTO.getMaxGuests(),
                searchDTO.getPrice(),
                searchDTO.getBathrooms(),
                searchDTO.getBedrooms(),
                searchDTO.getBeds(),
                searchDTO.getCategoryNum(),
                PageRequest.of(0, 5)))
                .thenReturn(propertyPage);

        when(mapper.map(property1, PropertyViewDTO.class)).thenReturn(new PropertyViewDTO(/* Set values for the first PropertyViewDTO */));
        when(mapper.map(property2, PropertyViewDTO.class)).thenReturn(new PropertyViewDTO(/* Set values for the second PropertyViewDTO */));

        List<PropertyViewDTO> result = propertyService.search(searchDTO, 1);

        List<PropertyViewDTO> expected = properties.stream()
                .map(p -> mapper.map(p, PropertyViewDTO.class))
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }
}

