package com.finalproject.airbnb.compositeKeysClasses;

import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;

import java.io.Serializable;

public class WishlistKeys implements Serializable {

    private Property property;
    private User owner;
}
