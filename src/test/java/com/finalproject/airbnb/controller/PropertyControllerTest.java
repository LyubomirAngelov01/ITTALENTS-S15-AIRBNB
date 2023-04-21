package com.finalproject.airbnb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.airbnb.Utility;
import com.finalproject.airbnb.model.DTOs.*;
import com.finalproject.airbnb.service.PropertyService;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
@ExtendWith(SpringExtension.class)
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PropertyService propertyService;

    @Mock
    private HttpSession session;


    @Test
    @SneakyThrows
    void createProperty() {
        PropertyInfoDTO propertyInfoDTO = new PropertyInfoDTO();
        propertyInfoDTO.setStreetAddress("123 Main St");
        propertyInfoDTO.setRegion("Some Region");
        propertyInfoDTO.setZipCode("12345");
        propertyInfoDTO.setCity("Some City");
        propertyInfoDTO.setCountryNum(1);
        propertyInfoDTO.setBeds(2);
        propertyInfoDTO.setMaxGuests(4);
        propertyInfoDTO.setPrice(100);
        propertyInfoDTO.setBathrooms(1);
        propertyInfoDTO.setBedrooms(2);
        propertyInfoDTO.setDescription("A cozy place to stay");
        propertyInfoDTO.setTitle("Cozy Apartment");
        propertyInfoDTO.setCategoryNum(0);
        propertyInfoDTO.setWifiConnection(true);
        propertyInfoDTO.setPool(false);
        propertyInfoDTO.setTv(true);
        propertyInfoDTO.setParking(true);
        propertyInfoDTO.setAirConditioning(true);
        propertyInfoDTO.setBarbecue(false);
        propertyInfoDTO.setSmokeAlarm(true);
        propertyInfoDTO.setFirstAid(true);
        propertyInfoDTO.setFireExtinguisher(true);
        propertyInfoDTO.setGym(false);
        propertyInfoDTO.setWasher(true);
        propertyInfoDTO.setKitchen(true);

        PropertyViewDTO propertyViewDTO = new PropertyViewDTO();
        propertyViewDTO.setStreetAddress("123 Main St");
        propertyViewDTO.setRegion("Some Region");
        propertyViewDTO.setZipCode("12345");
        propertyViewDTO.setCity("Some City");
        propertyViewDTO.setCountryName("USA");
        propertyViewDTO.setBeds(2);
        propertyViewDTO.setMaxGuests(4);
        propertyViewDTO.setPrice(100);
        propertyViewDTO.setBathrooms(1);
        propertyViewDTO.setBedrooms(2);
        propertyViewDTO.setDescription("A cozy place to stay");
        propertyViewDTO.setTitle("Cozy Apartment");
        propertyViewDTO.setCategoryName("Apartment");
        propertyViewDTO.setWifiConnection(true);
        propertyViewDTO.setPool(false);
        propertyViewDTO.setTv(true);
        propertyViewDTO.setParking(true);
        propertyViewDTO.setAirConditioning(true);
        propertyViewDTO.setBarbecue(false);
        propertyViewDTO.setSmokeAlarm(true);
        propertyViewDTO.setFirstAid(true);
        propertyViewDTO.setFireExtinguisher(true);
        propertyViewDTO.setGym(false);
        propertyViewDTO.setWasher(true);
        propertyViewDTO.setKitchen(true);

        when(session.getAttribute(Utility.LOGGED)).thenReturn(true);
        when(session.getAttribute(Utility.LOGGED_ID)).thenReturn(1);

        when(propertyService.createProperty(any(PropertyInfoDTO.class), any(Integer.class)))
                .thenReturn(propertyViewDTO);


        mockMvc.perform(post("/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyInfoDTO))
                        .sessionAttr(Utility.LOGGED, true)
                        .sessionAttr(Utility.LOGGED_ID, 1))
                .andExpect(status().isOk());

        mockMvc.perform(post("/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyInfoDTO))
                        .sessionAttr(Utility.LOGGED, true)
                        .sessionAttr(Utility.LOGGED_ID, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetAddress").value("123 Main St"))
                .andExpect(jsonPath("$.region").value("Some Region"))
                .andExpect(jsonPath("$.zipCode").value("12345"))
                .andExpect(jsonPath("$.city").value("Some City"))
                .andExpect(jsonPath("$.countryName").value("USA"))
                .andExpect(jsonPath("$.beds").value(2))
                .andExpect(jsonPath("$.maxGuests").value(4))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.bathrooms").value(1))
                .andExpect(jsonPath("$.bedrooms").value(2))
                .andExpect(jsonPath("$.description").value("A cozy place to stay"))
                .andExpect(jsonPath("$.title").value("Cozy Apartment"))
                .andExpect(jsonPath("$.categoryName").value("Apartment"))
                .andExpect(jsonPath("$.wifiConnection").value(true))
                .andExpect(jsonPath("$.pool").value(false))
                .andExpect(jsonPath("$.tv").value(true))
                .andExpect(jsonPath("$.parking").value(true))
                .andExpect(jsonPath("$.airConditioning").value(true))
                .andExpect(jsonPath("$.barbecue").value(false))
                .andExpect(jsonPath("$.smokeAlarm").value(true))
                .andExpect(jsonPath("$.firstAid").value(true))
                .andExpect(jsonPath("$.fireExtinguisher").value(true))
                .andExpect(jsonPath("$.gym").value(false))
                .andExpect(jsonPath("$.washer").value(true))
                .andExpect(jsonPath("$.kitchen").value(true));

    }

    @Test
    void EditProperty() throws Exception {
        PropertyInfoDTO propertyInfoDTO = new PropertyInfoDTO();
        propertyInfoDTO.setStreetAddress("456 Main St");
        propertyInfoDTO.setRegion("New Region");
        propertyInfoDTO.setZipCode("67890");
        propertyInfoDTO.setCity("New City");
        propertyInfoDTO.setCountryNum(2);
        propertyInfoDTO.setBeds(3);
        propertyInfoDTO.setMaxGuests(6);
        propertyInfoDTO.setPrice(150);
        propertyInfoDTO.setBathrooms(2);
        propertyInfoDTO.setBedrooms(3);
        propertyInfoDTO.setDescription("A spacious place to stay");
        propertyInfoDTO.setTitle("Cozy Apartment");
        propertyInfoDTO.setCategoryNum(1);
        propertyInfoDTO.setWifiConnection(true);
        propertyInfoDTO.setPool(false);
        propertyInfoDTO.setTv(true);
        propertyInfoDTO.setParking(true);
        propertyInfoDTO.setAirConditioning(true);
        propertyInfoDTO.setBarbecue(false);
        propertyInfoDTO.setSmokeAlarm(true);
        propertyInfoDTO.setFirstAid(true);
        propertyInfoDTO.setFireExtinguisher(true);
        propertyInfoDTO.setGym(false);
        propertyInfoDTO.setWasher(true);
        propertyInfoDTO.setKitchen(true);

        PropertyViewDTO propertyViewDTO = new PropertyViewDTO();
        propertyViewDTO.setStreetAddress("456 Main St");
        propertyViewDTO.setRegion("New Region");
        propertyViewDTO.setZipCode("67890");
        propertyViewDTO.setCity("New City");
        propertyViewDTO.setCountryName("Canada");
        propertyViewDTO.setBeds(3);
        propertyViewDTO.setMaxGuests(6);
        propertyViewDTO.setPrice(150);
        propertyViewDTO.setBathrooms(2);
        propertyViewDTO.setBedrooms(3);
        propertyViewDTO.setDescription("A cozy place to stay");
        propertyViewDTO.setTitle("Cozy Apartment");
        propertyViewDTO.setCategoryName("Apartment");
        propertyViewDTO.setWifiConnection(true);
        propertyViewDTO.setPool(false);
        propertyViewDTO.setTv(true);
        propertyViewDTO.setParking(true);
        propertyViewDTO.setAirConditioning(true);
        propertyViewDTO.setBarbecue(false);
        propertyViewDTO.setSmokeAlarm(true);
        propertyViewDTO.setFirstAid(true);
        propertyViewDTO.setFireExtinguisher(true);
        propertyViewDTO.setGym(false);
        propertyViewDTO.setWasher(true);
        propertyViewDTO.setKitchen(true);

        when(session.getAttribute(Utility.LOGGED)).thenReturn(true);
        when(session.getAttribute(Utility.LOGGED_ID)).thenReturn(1);

        when(propertyService.editProperty(eq(1), any(PropertyInfoDTO.class), any(Integer.class)))
                .thenReturn(propertyViewDTO);

        mockMvc.perform(put("/properties/{propertyId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyInfoDTO))
                        .sessionAttr(Utility.LOGGED, true)
                        .sessionAttr(Utility.LOGGED_ID, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetAddress").value("456 Main St"))
                .andExpect(jsonPath("$.region").value("New Region"))
                .andExpect(jsonPath("$.zipCode").value("67890"))
                .andExpect(jsonPath("$.city").value("New City"))
                .andExpect(jsonPath("$.countryName").value("Canada"))
                .andExpect(jsonPath("$.beds").value(3))
                .andExpect(jsonPath("$.maxGuests").value(6))
                .andExpect(jsonPath("$.price").value(150))
                .andExpect(jsonPath("$.bathrooms").value(2))
                .andExpect(jsonPath("$.bedrooms").value(3))
                .andExpect(jsonPath("$.description").value("A cozy place to stay"))
                .andExpect(jsonPath("$.title").value("Cozy Apartment"))
                .andExpect(jsonPath("$.categoryName").value("Apartment"))
                .andExpect(jsonPath("$.wifiConnection").value(true))
                .andExpect(jsonPath("$.pool").value(false))
                .andExpect(jsonPath("$.tv").value(true))
                .andExpect(jsonPath("$.parking").value(true))
                .andExpect(jsonPath("$.airConditioning").value(true))
                .andExpect(jsonPath("$.barbecue").value(false))
                .andExpect(jsonPath("$.smokeAlarm").value(true))
                .andExpect(jsonPath("$.firstAid").value(true))
                .andExpect(jsonPath("$.fireExtinguisher").value(true))
                .andExpect(jsonPath("$.gym").value(false))
                .andExpect(jsonPath("$.washer").value(true))
                .andExpect(jsonPath("$.kitchen").value(true));

    }

    @Test
    @SneakyThrows
    void showProperty() {
        // Given
        int propertyId = 1;

        PropertyViewDTO propertyViewDTO = new PropertyViewDTO();
        propertyViewDTO.setStreetAddress("123 Main St");
        propertyViewDTO.setRegion("Some Region");
        propertyViewDTO.setZipCode("12345");
        propertyViewDTO.setCity("Some City");
        propertyViewDTO.setCountryName("USA");
        propertyViewDTO.setBeds(2);
        propertyViewDTO.setMaxGuests(4);
        propertyViewDTO.setPrice(100);
        propertyViewDTO.setBathrooms(1);
        propertyViewDTO.setBedrooms(2);
        propertyViewDTO.setDescription("A cozy place to stay");
        propertyViewDTO.setTitle("Cozy Apartment");
        propertyViewDTO.setCategoryName("Apartment");
        propertyViewDTO.setWifiConnection(true);
        propertyViewDTO.setPool(false);
        propertyViewDTO.setTv(true);
        propertyViewDTO.setParking(true);
        propertyViewDTO.setAirConditioning(true);
        propertyViewDTO.setBarbecue(false);
        propertyViewDTO.setSmokeAlarm(true);
        propertyViewDTO.setFirstAid(true);
        propertyViewDTO.setFireExtinguisher(true);
        propertyViewDTO.setGym(false);
        propertyViewDTO.setWasher(true);
        propertyViewDTO.setKitchen(true);

        when(propertyService.showProperty(1)).thenReturn(propertyViewDTO);

        mockMvc.perform(get("/properties/{id}", propertyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetAddress").value("123 Main St"))
                .andExpect(jsonPath("$.region").value("Some Region"))
                .andExpect(jsonPath("$.zipCode").value("12345"))
                .andExpect(jsonPath("$.city").value("Some City"))
                .andExpect(jsonPath("$.countryName").value("USA"))
                .andExpect(jsonPath("$.beds").value(2))
                .andExpect(jsonPath("$.maxGuests").value(4))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.bathrooms").value(1))
                .andExpect(jsonPath("$.bedrooms").value(2))
                .andExpect(jsonPath("$.description").value("A cozy place to stay"))
                .andExpect(jsonPath("$.title").value("Cozy Apartment"))
                .andExpect(jsonPath("$.categoryName").value("Apartment"))
                .andExpect(jsonPath("$.wifiConnection").value(true))
                .andExpect(jsonPath("$.pool").value(false))
                .andExpect(jsonPath("$.tv").value(true))
                .andExpect(jsonPath("$.parking").value(true))
                .andExpect(jsonPath("$.airConditioning").value(true))
                .andExpect(jsonPath("$.barbecue").value(false))
                .andExpect(jsonPath("$.smokeAlarm").value(true))
                .andExpect(jsonPath("$.firstAid").value(true))
                .andExpect(jsonPath("$.fireExtinguisher").value(true))
                .andExpect(jsonPath("$.gym").value(false))
                .andExpect(jsonPath("$.washer").value(true))
                .andExpect(jsonPath("$.kitchen").value(true));
    }


    @Test
    @SneakyThrows
    public void deletePropertyTest() {

        int testId = 1;
        int testLoggedId = 2;
        DeletedPropertyDTO deletedPropertyDTO = new DeletedPropertyDTO();


        when(propertyService.deleteProperty(testId, testLoggedId)).thenReturn(deletedPropertyDTO);

        MvcResult result = mockMvc.perform(delete("/properties/{id}", testId)
                        .sessionAttr(Utility.LOGGED, true)
                        .sessionAttr(Utility.LOGGED_ID, 2))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        if (!responseContent.equals("PROPERTY HAS BEEN DELETED")) {
            fail("Unexpected response content: " + responseContent);
        }

        verify(propertyService).deleteProperty(testId, testLoggedId);
    }
}