package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.entities.Property;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.repositories.WishlistRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class WishlistService extends AbstractService{
    private final WishlistRepository wishlistRepository;

    public List<Property> takeWishlistOfUser(User user){
        return wishlistRepository.findAllByUser(user);
    }

}
