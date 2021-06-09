package org.sber.simonov.dit.repository;

import org.sber.simonov.dit.model.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Message save(Message message) {
        em.persist(message);
        return message;
    }

    @Override
    public Message getFirst() {
        return em.createNamedQuery(Message.FIND_FIRST, Message.class)
                .getSingleResult();
    }

    @Override
    public Message getLast() {
        return em.createNamedQuery(Message.FIND_LAST, Message.class)
                .getSingleResult();
    }

    @Override
    @Transactional
    public Message getFirstAndRemove() {
        Message message = em.createNamedQuery(Message.FIND_FIRST, Message.class)
                .getSingleResult();
        em.remove(message);
        return message;
    }

    @Override
    public List<Message> getAll() {
        return em.createNamedQuery(Message.GET_ALL, Message.class)
                .getResultList();
    }
}
