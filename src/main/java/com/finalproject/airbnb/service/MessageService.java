package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.entities.Message;
import com.finalproject.airbnb.model.entities.User;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.MessageRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import lombok.Setter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    MessageRepository messageRepository;
    UserRepository userRepository;
    public void sendMessage(int senderId,int idReceiver,String text) {

        User receiver = userRepository.findById(idReceiver).orElseThrow(()-> new NotFoundException("receiver not found"));
        User sender = userRepository.findById(senderId).orElseThrow(()->new UnauthorizedException("not logged in"));


        messageRepository.save(new Message(text,sender,receiver, LocalDateTime.now()));
    }

    public List<Message> listChatWithAUser(int loggedId, int receiverId) {
        User sender = userRepository.findById(loggedId).orElseThrow(()-> new UnauthorizedException("log in first"));
        User receiver = userRepository.findById(receiverId).orElseThrow(()-> new NotFoundException("user not found"));
        List<Message> messages = messageRepository.findAllBySenderAndReceiver(sender,receiver);
        messages.addAll(messageRepository.findAllBySenderAndReceiver(receiver,sender));
        return messages;
    }

    public List<User> getInbox(int loggedId) {
        User sender = userRepository.findById(loggedId).orElseThrow(()->new UnauthorizedException("you have to log in"));
        List <Message> messages = messageRepository.findAllBySender(sender);
        List<User> contacts = new ArrayList();
        for (Message message: messages) {
            contacts.add(message.getReceiver());
        }
        return contacts;
    }
}
