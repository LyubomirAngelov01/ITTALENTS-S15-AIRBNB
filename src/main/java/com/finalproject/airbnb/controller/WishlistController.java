//package com.finalproject.airbnb.controller;
//
//import com.finalproject.airbnb.Utility;
//import com.finalproject.airbnb.model.entities.Property;
//import com.finalproject.airbnb.model.entities.User;
////import com.finalproject.airbnb.model.entities.Wishlist;
//import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
//import com.finalproject.airbnb.model.repositories.UserRepository;
////import com.finalproject.airbnb.model.repositories.WishlistRepository;
//import com.finalproject.airbnb.service.WishlistService;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//@RestController
//@RequiredArgsConstructor
//public class WishlistController extends AbstractController{
//
//    private final WishlistService wishlistService;
//    private final UserRepository userRepository;
////    @GetMapping("/airbnb/users/wishlist/")
////    public List<Property> wishlist(HttpSession session){
////        User u = userRepository.findById(getLoggedId(session)).orElseThrow(() -> new UnauthorizedException("you have to login"));
////        return wishlistService.takeWishlistOfUser(u);
////    }
//
//
//}
