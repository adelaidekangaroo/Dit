package org.sber.simonov.dit.controller;

import org.sber.simonov.dit.model.Message;
import org.sber.simonov.dit.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = MessageRestController.REST_URL, produces = APPLICATION_JSON_VALUE)
public class MessageRestController {

    public static final String REST_URL = "/rest/messages";

    private final MessageService service;

    public MessageRestController(MessageService service) {
        this.service = service;
    }

    @ResponseStatus(value = CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Message offer(@RequestBody Message message) {
        return service.offer(message);
    }

    @GetMapping(value = "/peek", produces = APPLICATION_JSON_VALUE)
    public Message peek() {
        return service.peek();
    }

    @GetMapping(value = "/peekmax", produces = APPLICATION_JSON_VALUE)
    public Message peekMax() {
        return service.peekMax();
    }

    @GetMapping(value = "/poll", produces = APPLICATION_JSON_VALUE)
    public Message poll() {
        return service.poll();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Message> all() {
        return service.all();
    }
}