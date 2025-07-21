package com.example.service;


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

    public Optional<Message> addMessage(Message newMessage){
        Optional<Account> account = accountRepository.findById(newMessage.getPostedBy());
                
        if(account.isPresent() && isValidatedMessage(newMessage)){
          return Optional.of( messageRepository.save(newMessage));
        }
        return Optional.empty();
    }

    public Optional<Message> findMessageById(int message_id){
        return messageRepository.findById(message_id);
    }

    public Optional<List <Message>> findAllMessages(){
        return Optional.of(messageRepository.findAll());
    }

    public Optional<List<Message>> findAllByPostedBy(int postedBy){
        return Optional.of(messageRepository.findAllByPostedBy(postedBy));
    }

    public Optional<Integer> deleteMessage(int id){
        Optional<Message> message = messageRepository.findById(id);
        if(message.isPresent()){
            return Optional.of(1);
        }
        return  Optional.empty();
    }

    public Optional<Integer> updateMessage(int id, Message newMessage){
        Optional<Integer> message = messageRepository.updateByMessageId(id,newMessage.getMessageText());
        if (message.get() == 1 && isValidatedMessage(newMessage)) {
            return Optional.of(1);
       }
        return Optional.empty();
    }

    public boolean isValidatedMessage(Message message){
        return !message.getMessageText().isBlank() && message.getMessageText().length() < 255;
    }
 }
