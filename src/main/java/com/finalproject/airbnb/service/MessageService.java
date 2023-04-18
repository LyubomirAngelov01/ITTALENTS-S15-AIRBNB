package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.InboxUserDTO;
import com.finalproject.airbnb.model.entities.Message;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.MessageRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends AbstractService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;
    public void sendMessage(int senderId, int idReceiver,String text) {
        User sender = getUserById(senderId);
        User receiver = userRepository.findById(idReceiver).orElseThrow(()-> new NotFoundException("receiver not found"));
        messageRepository.save(new Message(text, sender, receiver, LocalDateTime.now()));
    }

    public List<Message> listChatWithAUser(int loggedId, int receiverId) {
        User sender = userRepository.findById(loggedId).orElseThrow(()-> new UnauthorizedException("log in first"));
        User receiver = userRepository.findById(receiverId).orElseThrow(()-> new NotFoundException("user not found"));
        List<Message> messages = messageRepository.findAllBySenderAndReceiver(sender,receiver);
        messages.addAll(messageRepository.findAllBySenderAndReceiver(receiver,sender));
        return messages;
    }

    public List<InboxUserDTO> getInbox(int loggedId) {
        User sender = getUserById(loggedId);
        List <Message> messages = messageRepository.findAllBySender(sender);
        List<User> contacts = messages.stream()
                .map(message -> message.getReceiver())
                .collect(Collectors.toList());
        List<InboxUserDTO> inbox = contacts.stream()
                .map(user -> mapper.map(user,InboxUserDTO.class))
                .collect(Collectors.toList());

        return inbox;
    }
}
