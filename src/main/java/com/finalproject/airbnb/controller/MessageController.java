package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.InboxUserDTO;
import com.finalproject.airbnb.model.DTOs.MessageWithUserDTO;
import com.finalproject.airbnb.model.DTOs.SendMessageDTO;
import com.finalproject.airbnb.model.entities.Message;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.service.MessageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MessageController extends AbstractController{


    private final MessageService messageService;


    @PostMapping("/users/messages/{idReceiver}")
    public void sendMessage(@RequestBody SendMessageDTO dto, @PathVariable int idReceiver, HttpSession session){
        int senderId = getLoggedId(session);
        messageService.sendMessage(senderId,idReceiver, dto.getText());
    }
    @GetMapping("/users/messages/{receiverId}")
    public List<MessageWithUserDTO> listChatWithAUser(@PathVariable int receiverId, HttpSession session){

        return messageService.listChatWithAUser(getLoggedId(session),receiverId);
    }
    @GetMapping("/users/messages")
    public List<InboxUserDTO> getInbox(HttpSession session) {
        return messageService.getInbox(getLoggedId(session));
    }

}
