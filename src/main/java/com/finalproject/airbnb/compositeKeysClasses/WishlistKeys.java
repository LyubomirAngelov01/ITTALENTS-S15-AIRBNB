package com.finalproject.airbnb.compositeKeysClasses;

import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import jakarta.persistence.IdClass;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WishlistKeys implements Serializable {

    private User user;
    private Property property;

}
