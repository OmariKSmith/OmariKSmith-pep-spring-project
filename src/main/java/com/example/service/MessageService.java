package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Message;
import java.util.List;
@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Message> addMessage(Message newMessage){
        Optional<Account> account = accountRepository.findById(newMessage.getPostedBy());
                
        if(account.isPresent() && isValidatedMessage(newMessage)){
            messageRepository.save(newMessage);
            return ResponseEntity.status(200)
                    .body(newMessage);
        }
        return ResponseEntity.status(400)
                .build();
    }

    public ResponseEntity<Message> findMessageById(int message_id){
        Optional<Message> message = messageRepository.findById(message_id);
                
        if(message.isPresent()){
            return ResponseEntity.status(200)
                    .body(message.get());
        }
        return ResponseEntity.status(200)
                .build();
    }

    public ResponseEntity<List<Message>> findAllMessages(){
        List<Message> messageOp =  messageRepository.findAll();
        return ResponseEntity.status(200)
                .body(messageOp);
    }

    public ResponseEntity<List<Message>> findAllByPostedBy(int postedBy){
        List<Message> messageOp =  messageRepository.findAllByPostedBy(postedBy);
        return ResponseEntity.status(200)
                .body(messageOp);
    }

    public ResponseEntity<Integer> deleteMessage(int id){
        Optional<Message> message = messageRepository.findById(id);
        
        if (message.isPresent()) {
            return ResponseEntity.status(200)
                .body(messageRepository.deleteById(id));
        }
        return  ResponseEntity.status(200).build();
    }

    public ResponseEntity<Integer> updateMessage(int id, Message newMessage){
        Optional<Message> message = messageRepository.findById(id);

        if (message.isPresent() && isValidatedMessage(newMessage)) {
            return ResponseEntity.status(200).body(messageRepository.updateByMessageId(id, newMessage.getMessageText()));
       }
        return  ResponseEntity.status(400).build();
    }

    private boolean isValidatedMessage(Message message){
        return !message.getMessageText().isBlank() && message.getMessageText().length() < 255;
    }
   

}
