package com.repositories;

import com.model.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findMessages(long begin, long end);

    Message findById(long id);

    Message saveMessage(Message message);

    Message addMessage(Message message);
}
