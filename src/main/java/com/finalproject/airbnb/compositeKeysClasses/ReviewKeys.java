package com.finalproject.airbnb.compositeKeysClasses;

import com.finalproject.airbnb.model.entities.Property;

import java.io.Serializable;

public class ReviewKeys implements Serializable {

    private Property property;
    private User owner;
}
