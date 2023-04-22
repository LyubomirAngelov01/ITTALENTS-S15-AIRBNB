package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.ChatDTO;
import com.finalproject.airbnb.model.DTOs.InboxUserDTO;
import com.finalproject.airbnb.model.DTOs.MessageWithUserDTO;
import com.finalproject.airbnb.model.DTOs.SendMessageDTO;
import com.finalproject.airbnb.model.entities.MessageEntity;
import com.finalproject.airbnb.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MessageController extends AbstractController{


    private final MessageService messageService;


    @PostMapping("/users/messages/{idReceiver}")
    public void sendMessage(@RequestBody SendMessageDTO dto, @PathVariable int idReceiver, HttpServletRequest request){
        int senderId = extractUserIdFromToken(request);
        messageService.sendMessage(senderId,idReceiver, dto.getText());
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
