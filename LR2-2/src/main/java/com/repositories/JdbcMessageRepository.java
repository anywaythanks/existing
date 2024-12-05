package com.repositories;

import com.model.Message;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMessageRepository implements MessageRepository {
    private final JdbcOperations jdbcOperations;
    private static final String
            SELECT_MESSAGE_BY_ID = "select * from messages where id = ?;",
            FIND_MESSAGE_BY_IDS = "select * from messages where :begin <= id and  id <= :end;",
            INSERT_MESSAGE = "insert into messages(users, message, postedtime) values(:users, :message, :postedtime);";

    private static class MessageMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Message(
                    rs.getLong("id"),
                    rs.getLong("users"),
                    rs.getString("message"),
                    rs.getDate("postedtime"));
        }
    }

    public JdbcMessageRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Message> findMessages(long begin, long end) {
        if (begin <= 0 || end <= 0) throw new IllegalArgumentException();
        if (begin > end) throw new IllegalArgumentException();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("begin", begin);
        paramMap.put("end", end);
        return jdbcOperations.query(FIND_MESSAGE_BY_IDS, new MessageMapper(), paramMap);
    }

    @Override
    public Message findById(long id) {
        if (id <= 0) throw new IllegalArgumentException();
        return jdbcOperations.queryForObject(SELECT_MESSAGE_BY_ID, new MessageMapper(), id);
    }

    @Override
    public Message saveMessage(Message message) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("users", message.getUsers());
        paramMap.put("message", message.getMessage());
        paramMap.put("postedtime", message.getDate());
        long id = jdbcOperations.update(INSERT_MESSAGE, paramMap);
        return new Message(id,
                message.getUsers(),
                message.getMessage(),
                message.getDate());
    }

    @Override
    public Message addMessage(Message message) {
        return null;
    }
}
