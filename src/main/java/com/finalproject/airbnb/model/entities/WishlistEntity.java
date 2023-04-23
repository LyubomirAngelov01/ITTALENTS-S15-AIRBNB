package com.finalproject.airbnb.model.entities;

import com.finalproject.airbnb.compositeKeysClasses.WishlistKeys;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "wishlist")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(WishlistKeys.class)
public class WishlistEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

}
