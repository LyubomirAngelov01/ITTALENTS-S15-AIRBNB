package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Integer> {


    @Query("SELECT DISTINCT m.receiver.id FROM messages AS m WHERE m.sender.id = :senderId ")
    List<Integer> getAllBySenderId(int senderId);

    @Query("SELECT DISTINCT m.sender.id FROM messages AS m WHERE m.receiver.id = :receiverId ")
    List<Integer> getAllByReceiverId(int receiverId);

    @Query(value = "SELECT m.id,m.message,m.sender_id,m.receiver_id,m.time_sent FROM messages m WHERE (sender_id = :senderId AND receiver_id = :receiverId) " +
            "OR (sender_id = :receiverId AND receiver_id = :senderId)", nativeQuery = true)
    Page<MessageEntity> listAChat(int senderId, int receiverId, Pageable pageable);

}
