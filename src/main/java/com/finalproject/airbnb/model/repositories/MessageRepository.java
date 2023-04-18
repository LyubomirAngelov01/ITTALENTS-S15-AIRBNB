package com.finalproject.airbnb.model.repositories;

import com.finalproject.airbnb.model.entities.Message;
import com.finalproject.airbnb.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {






    List<Message> findAllBySenderAndReceiver(User sender, User receiver);
    List<Message> findDistinctBySender(User sender);
    @Query("SELECT DISTINCT m.receiver.id FROM messages AS m WHERE m.sender.id = :senderId ")
    List<Integer> getAllBySenderId(int senderId);
    @Query("SELECT DISTINCT m.sender.id FROM messages AS m WHERE m.receiver.id = :receiverId ")
    List<Integer> getAllByReceiverId(int receiverId);


}
