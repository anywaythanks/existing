package com.web;

import com.model.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.repositories.MessageRepository;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.util.TagUtils.SCOPE_REQUEST;

@Controller
@RequestMapping({"/messages"})
@Scope(SCOPE_REQUEST)
public class MessageController {
    private final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = GET)
    public List<Message> messages(
            @RequestParam(value = "max", defaultValue = Long.MAX_VALUE + "") long max,
            @RequestParam(value = "count", defaultValue = "20") int count) {
        return repository.findMessages(max, count);
    }

    @RequestMapping(value = "/{spittleId}", method = GET)
    public String spittle(@PathVariable long spittleId, Model model) {
        model.addAttribute(repository.findById(spittleId));
        return "message";
    }
}
