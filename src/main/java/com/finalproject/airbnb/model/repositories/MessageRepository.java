package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.MessageEntity;
import com.finalproject.airbnb.model.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Integer> {


    @Query(value = "SELECT DISTINCT m.sender_id AS sender FROM messages m WHERE m.receiver_id = :loggedId " +
            "UNION " +
            "SELECT DISTINCT d.receiver_id as receiver FROM messages d WHERE d.sender_id = :loggedId", nativeQuery = true)
    Page<Integer> getInbox(int loggedId,Pageable pageable);

    @Query(value = "SELECT m.id,m.message,m.sender_id,m.receiver_id,m.time_sent FROM messages m WHERE (sender_id = :senderId AND receiver_id = :receiverId) " +
            "OR (sender_id = :receiverId AND receiver_id = :senderId)", nativeQuery = true)
    Page<MessageEntity> listAChat(int senderId, int receiverId, Pageable pageable);

    void deleteAllBySender(UserEntity user);
    void deleteAllByReceiver(UserEntity user);

}
