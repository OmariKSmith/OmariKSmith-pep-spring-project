package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.example.entity.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{
    List<Message> findAll();
    List<Message> findAllByPostedBy(int postedBy);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM message m WHERE m.messageId = :messageId", nativeQuery = true)
    int deleteById(@Param("messageId") int messageId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE message m SET m.messageText = :messageText WHERE m.messageId = :messageId",nativeQuery = true)
    int updateByMessageId(@Param("messageId") int messageId, @Param("messageText") String messageText);

   


}
