package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.DTOs.InboxUserDTO;
import com.finalproject.airbnb.model.DTOs.MessageWithUserDTO;
import com.finalproject.airbnb.model.entities.MessageEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.exceptions.BadRequestException;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
import com.finalproject.airbnb.model.repositories.MessageRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends AbstractService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;
    public void sendMessage(int senderId, int idReceiver,String text) {
        UserEntity sender = getUserById(senderId);
        UserEntity receiver = userRepository.findById(idReceiver).orElseThrow(()-> new NotFoundException("receiver not found"));
        messageRepository.save(new MessageEntity(text, sender, receiver, LocalDateTime.now()));
    }

    public List<MessageWithUserDTO> listChatWithAUser(int loggedId, int receiverId) {
        UserEntity sender = userRepository.findById(loggedId).orElseThrow(()-> new UnauthorizedException("log in first"));
        UserEntity receiver = userRepository.findById(receiverId).orElseThrow(()-> new NotFoundException("user not found"));
        List<MessageEntity> messages = messageRepository.findAllBySenderAndReceiver(sender,receiver);
        messages.addAll(messageRepository.findAllBySenderAndReceiver(receiver,sender));
        List<MessageWithUserDTO> messagesWithUser = new ArrayList();
        for (MessageEntity message: messages) {
            messagesWithUser.add(new MessageWithUserDTO(message.getSender().getId(),message.getSender().getFirstName(),
                    message.getSender().getLastName(),message.getMessage(),message.getTimeSent()));
        }
        messagesWithUser = messagesWithUser.stream()
                .sorted(Comparator.comparing(MessageWithUserDTO::getTimeSent))
                .collect(Collectors.toList());
        return messagesWithUser;
    }

    public List<InboxUserDTO> getInbox(int loggedId) {


        List<Integer> contactsIds = messageRepository.getAllBySenderId(loggedId);
        contactsIds.addAll(messageRepository.getAllByReceiverId(loggedId));

        contactsIds = contactsIds.stream().distinct().collect(Collectors.toList());

        List<InboxUserDTO> inbox = new ArrayList<>();
        for (Integer id: contactsIds) {
            UserEntity user = userRepository.findById(id).orElseThrow(()-> new BadRequestException("user not found"));

            inbox.add(new InboxUserDTO(user.getFirstName(),user.getLastName(),user.getId()));

        }

        return inbox;
    }
}
