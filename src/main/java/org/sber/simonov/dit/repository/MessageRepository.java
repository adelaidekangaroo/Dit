package org.sber.simonov.dit.repository;

import org.sber.simonov.dit.model.Message;

import java.util.List;

public interface MessageRepository {

    Message save(Message message);

    Message getFirst();

    Message getLast();

    Message getFirstAndRemove();

    List<Message> getAll();
}
