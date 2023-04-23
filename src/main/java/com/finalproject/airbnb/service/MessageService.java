package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.ChatDTO;
import com.finalproject.airbnb.model.DTOs.InboxUserDTO;
import com.finalproject.airbnb.model.entities.MessageEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.MessageRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public String sendMessage(int senderId, int receiverId, String text) {
        UserEntity sender = getUserById(senderId);
        UserEntity receiver = getUserById(receiverId);

        if (!(reservationRepository.reservationsBetweenUsers(receiverId, senderId) > 0)) {
            throw new BadRequestException("you dont have a reservation with that user");
        }

        messageRepository.save(new MessageEntity(text, sender, receiver, LocalDateTime.now()));
        return text;
    }

    public Page<ChatDTO> listChatWithAUser(int loggedId, int receiverId, Pageable pageable) {
        UserEntity sender = userRepository.findById(loggedId).orElseThrow(() -> new UnauthorizedException("log in first"));
        UserEntity receiver = userRepository.findById(receiverId).orElseThrow(() -> new NotFoundException("user not found"));
        Page<MessageEntity> chats = messageRepository.listAChat(loggedId, receiverId, pageable);
        Page<ChatDTO> messages = chats.map(messageEntity -> mapper.map(messageEntity, ChatDTO.class));


        return messages;
    }

    public List<InboxUserDTO> getInbox(int loggedId) {

        List<Integer> contactsIds = messageRepository.getAllBySenderId(loggedId);
        contactsIds.addAll(messageRepository.getAllByReceiverId(loggedId));

        contactsIds = contactsIds.stream().distinct().collect(Collectors.toList());

        List<InboxUserDTO> inbox = new ArrayList<>();
        for (Integer id : contactsIds) {
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("user not found"));

            inbox.add(new InboxUserDTO(user.getFirstName(), user.getLastName(), user.getId()));

        }
        return inbox;
    }
}
