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

@Service
public class MessageService extends AbstractService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public String sendMessage(int senderId, int receiverId, String text) {
        if (senderId == receiverId){
            throw new BadRequestException("you can't send message to yourself");
        }
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
        if (!(reservationRepository.reservationsBetweenUsers(receiverId, loggedId) > 0)) {
            throw new BadRequestException("You cannot open a chat with that user, because you do not have an active" +
                    " reservation on any of his properties!");
        }
        Page<MessageEntity> chats = messageRepository.listAChat(sender.getId(), receiver.getId(), pageable);
        Page<ChatDTO> messages = chats.map(messageEntity -> mapper.map(messageEntity, ChatDTO.class));


        return messages;
    }

    public Page<InboxUserDTO> getInbox(int loggedId,Pageable pageable) {

        Page<Integer> contactsIds = messageRepository.getInbox(loggedId,pageable);
        Page<UserEntity> contacts = contactsIds.map(integer -> getUserById(integer));

        Page<InboxUserDTO> inbox = contacts.map(user -> new InboxUserDTO(user.getFirstName(),user.getLastName(),user.getId()));

        return inbox;
    }
}
