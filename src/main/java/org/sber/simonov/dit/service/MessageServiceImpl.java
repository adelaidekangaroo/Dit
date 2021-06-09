package org.sber.simonov.dit.service;

import org.sber.simonov.dit.exception.StorageException;
import org.sber.simonov.dit.model.Message;
import org.sber.simonov.dit.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static org.sber.simonov.dit.util.ValidationUtil.checkNew;
import static org.sber.simonov.dit.util.ValidationUtil.checkNotFound;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message offer(Message message) {
        return switchException(() -> {
            requireNonNull(message);
            checkNew(message);
            return repository.save(message);
        });
    }

    @Override
    public Message peek() {
        return switchException(() -> checkNotFound(repository.getFirst(), Message.class));
    }

    @Override
    public Message peekMax() {
        return switchException(() -> checkNotFound(repository.getLast(), Message.class));
    }

    @Override
    public Message poll() {
        return switchException(() -> checkNotFound(repository.getFirstAndRemove(), Message.class));
    }

    @Override
    public List<Message> all() {
        return switchException(repository::getAll);
    }

    public static <T> T switchException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new StorageException(e);
        }
    }
}