package org.sber.simonov.dit.service;

import org.sber.simonov.dit.model.Message;

import java.util.List;

public interface MessageService {

    Message offer(Message message);

    Message peek();

    Message peekMax();

    Message poll();

    List<Message> all();
}
