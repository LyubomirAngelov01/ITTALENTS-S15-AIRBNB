package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.ChatDTO;
import com.finalproject.airbnb.model.DTOs.InboxUserDTO;
import com.finalproject.airbnb.model.DTOs.SendMessageDTO;
import com.finalproject.airbnb.service.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@SecurityRequirement(name = "JWT token")
public class MessageController extends AbstractController{


    @Autowired
    private MessageService messageService;


    @PostMapping("/users/messages/{idReceiver}")
    public String sendMessage(@RequestBody SendMessageDTO dto, @PathVariable int idReceiver, HttpServletRequest request){
        int senderId = extractUserIdFromToken(request);
        return messageService.sendMessage(senderId,idReceiver, dto.getText());
    }
    @GetMapping("/users/messages/{receiverId}")
    public Page<ChatDTO> listChatWithAUser(@PathVariable int receiverId, HttpServletRequest request, Pageable pageable){
        int loggedId = extractUserIdFromToken(request);
        return messageService.listChatWithAUser(loggedId,receiverId,pageable);
    }
    @GetMapping("/users/messages")
    public List<InboxUserDTO> getInbox(HttpServletRequest request) {
        int loggedId = extractUserIdFromToken(request);
        return messageService.getInbox(loggedId);
    }

}
