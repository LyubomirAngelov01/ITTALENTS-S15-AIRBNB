package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.MessageWithUserDTO;
import com.finalproject.airbnb.model.entities.MessageEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.repositories.MessageRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MessageControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage() {
        // Arrange
        int senderId = 1;
        int receiverId = 3;
        String text = "Hello!";
        UserEntity sender = new UserEntity();
        sender.setId(senderId);
        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);

        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);

        messageService.sendMessage(senderId, receiverId, text);

        verify(userRepository, Mockito.times(1)).findById(receiverId);
        verify(messageRepository, Mockito.times(1)).save(Mockito.any(MessageEntity.class));
    }

    @Test
    void testListChatWithAUser() {
        UserEntity sender = new UserEntity();
        sender.setId(1);
        sender.setFirstName("John");
        sender.setLastName("Doe");
        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.of(sender));

        UserEntity receiver = new UserEntity();
        receiver.setId(2);
        receiver.setFirstName("Jane");
        receiver.setLastName("Doe");
        Mockito.when(userRepository.findById(2)).thenReturn(java.util.Optional.of(receiver));

        List<MessageEntity> messages = new ArrayList<>();
        MessageEntity message1 = new MessageEntity();
        message1.setSender(sender);
        message1.setReceiver(receiver);
        message1.setMessage("Hello");
        message1.setTimeSent(LocalDateTime.of(2022, 1, 1, 10, 0));
        messages.add(message1);

        Mockito.when(messageRepository.findAllBySenderAndReceiver(sender, receiver)).thenReturn(messages);

        List<MessageWithUserDTO> messagesWithUser = messageService.listChatWithAUser(1, 2);

        Assertions.assertEquals(1, messagesWithUser.size());
        Assertions.assertEquals(1, messagesWithUser.get(0).getSenderId());
        Assertions.assertEquals("John", messagesWithUser.get(0).getSenderFirstName());
        Assertions.assertEquals("Doe", messagesWithUser.get(0).getSenderLastName());
        Assertions.assertEquals("Hello", messagesWithUser.get(0).getMessage());
        Assertions.assertEquals(LocalDateTime.of(2022, 1, 1, 10, 0), messagesWithUser.get(0).getTimeSent());
    }



}
