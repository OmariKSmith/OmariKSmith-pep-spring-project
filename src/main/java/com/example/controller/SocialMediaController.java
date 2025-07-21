package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;
import java.util.Optional;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/")
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount){
        return accountService.register(newAccount)
                .map(acc -> ResponseEntity
                .status(200)
                .body(acc))
                .orElseGet(()-> ResponseEntity.status(409).body(newAccount));
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account userAccount){
        Optional<Account> account = accountService.login(userAccount);
        return account.map(acc -> ResponseEntity
                        .status(200)
                        .body(acc))
                        .orElseGet(() -> ResponseEntity.status(401).body(userAccount));
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
        Optional<Message> message = messageService.addMessage(newMessage);
        return  message.map(msg -> ResponseEntity
                .status(200)
                .body(msg))
                .orElseGet(()-> ResponseEntity.status(400).body(newMessage));
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> findAllMessages(){
        Optional<List<Message>> messages = messageService.findAllMessages();
      return messages.map(msgs -> ResponseEntity
                .status(200)
                .body(msgs))
                .orElseGet(()-> ResponseEntity.status(200).build());
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> findMessagesById(@PathVariable int message_id){
        Optional<Message> message = messageService.findMessageById(message_id);
        return message.map(mg -> ResponseEntity
                .status(200)
                .body(mg))
                .orElseGet(()-> ResponseEntity.status(200).build());

    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> findMessagesByPoster(@PathVariable int account_id){
        return messageService.findAllByPostedBy(account_id)
                .map(msg -> ResponseEntity
                .status(200)
                .body(msg))
                .orElseGet(()-> ResponseEntity.status(200).build());
    }

    @DeleteMapping("messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int id){
        return messageService.deleteMessage(id)
                .map(msg -> ResponseEntity
                .status(200)
                .body(msg))
                .orElseGet(()-> ResponseEntity.status(200).build());
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int message_id, @RequestBody Message newMessage){
        return messageService.updateMessage(message_id,newMessage)
                .map(msg-> ResponseEntity
                        .status(200)
                        .body(msg))
                .orElseGet(()-> ResponseEntity.status(400).build());
    }

}
