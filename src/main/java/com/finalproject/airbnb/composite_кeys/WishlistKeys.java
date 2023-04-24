package com.finalproject.airbnb.composite_кeys;

import com.finalproject.airbnb.model.entities.PropertyEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WishlistKeys implements Serializable {

    private UserEntity user;
    private PropertyEntity property;

}
