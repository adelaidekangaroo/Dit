package org.sber.simonov.dit.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = "messages")
@NamedQueries({
        @NamedQuery(name = Message.FIND_FIRST,
                query = "SELECT m FROM Message m WHERE m.id = (SELECT MIN(id) FROM m)"),
        @NamedQuery(name = Message.FIND_LAST,
                query = "SELECT m FROM Message m WHERE m.id = (SELECT MAX(id) FROM m)"),
        @NamedQuery(name = Message.GET_ALL,
                query = "SELECT m FROM Message m ORDER BY m.id")
})
public class Message {

    public static final String FIND_FIRST = "MESSAGE.FIND_FIRST";
    public static final String FIND_LAST = "MESSAGE.FIND_LAST";
    public static final String GET_ALL = "MESSAGE.GET_ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 4000)
    @Column(name = "content", nullable = false)
    private String content;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public Message(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
