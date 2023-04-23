package com.finalproject.airbnb.controller;

import com.finalproject.airbnb.model.DTOs.ChatDTO;
import com.finalproject.airbnb.model.entities.MessageEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.repositories.MessageRepository;
import com.finalproject.airbnb.model.repositories.ReservationRepository;
import com.finalproject.airbnb.model.repositories.UserRepository;
import com.finalproject.airbnb.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage() {
        int senderId = 1;
        int receiverId = 3;
        String text = "Hello!";
        UserEntity sender = new UserEntity();
        sender.setId(senderId);
        UserEntity receiver = new UserEntity();
        receiver.setId(receiverId);

        doReturn(Optional.of(sender)).when(userRepository).findById(senderId);
        doReturn(Optional.of(receiver)).when(userRepository).findById(receiverId);
        doReturn(1).when(reservationRepository).reservationsBetweenUsers(receiverId, senderId);


        messageService.sendMessage(senderId, receiverId, text);

        verify(userRepository, Mockito.times(1)).findById(receiverId);
        verify(reservationRepository, Mockito.times(1)).reservationsBetweenUsers(receiverId, senderId);
        verify(messageRepository, Mockito.times(1)).save(Mockito.any(MessageEntity.class));
    }

    @Test
    void testListChatWithAUser() {
        UserEntity sender = new UserEntity();
        sender.setId(1);
        sender.setFirstName("sender");

        UserEntity receiver = new UserEntity();
        receiver.setId(2);
        receiver.setFirstName("receiver");

        List<MessageEntity> messages = new ArrayList<>();
        MessageEntity message1 = new MessageEntity();
        message1.setId(1);
        message1.setMessage("Hello");
        message1.setTimeSent(LocalDateTime.now());
        message1.setSender(sender);
        message1.setReceiver(receiver);
        messages.add(message1);

        MessageEntity message2 = new MessageEntity();
        message2.setId(2);
        message2.setMessage("Hi");
        message2.setTimeSent(LocalDateTime.now());
        message2.setSender(receiver);
        message2.setReceiver(sender);
        messages.add(message2);

        int loggedId = 1;
        int receiverId = 2;
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findById(loggedId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
        Page<MessageEntity> page = new PageImpl<>(messages, pageable, messages.size());
        when(messageRepository.listAChat(loggedId, receiverId, pageable)).thenReturn(page);
        ChatDTO chatDTO1 = new ChatDTO();
        chatDTO1.setMessage("Hello");
        chatDTO1.setTimeSent(messages.get(0).getTimeSent());
        chatDTO1.setSenderId(messages.get(0).getSender().getId());
        chatDTO1.setReceiverId(messages.get(0).getReceiver().getId());
        ChatDTO chatDTO2 = new ChatDTO();
        chatDTO2.setMessage("Hi");
        chatDTO2.setTimeSent(messages.get(1).getTimeSent());
        chatDTO2.setSenderId(messages.get(1).getSender().getId());
        chatDTO2.setReceiverId(messages.get(1).getReceiver().getId());
        when(mapper.map(messages.get(0), ChatDTO.class)).thenReturn(chatDTO1);
        when(mapper.map(messages.get(1), ChatDTO.class)).thenReturn(chatDTO2);

        Page<ChatDTO> result = messageService.listChatWithAUser(loggedId, receiverId, pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getNumberOfElements());
        assertEquals(chatDTO1, result.getContent().get(0));
        assertEquals(chatDTO2, result.getContent().get(1));
    }
}


