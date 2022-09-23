package com.anjunar.blomst.social.chat;

import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("social/chat/messages")
public class ChatMessagesResource implements ListResourceTemplate<ChatMessageForm, ChatMessagesSearch> {

    private final ChatMessagesService service;

    private final ResourceEntityMapper entityMapper;

    @Inject
    public ChatMessagesResource(ChatMessagesService service, ResourceEntityMapper entityMapper) {
        this.service = service;
        this.entityMapper = entityMapper;
    }

    public ChatMessagesResource() {
        this(null, null);
    }

    @Override
    public Table<ChatMessageForm> list(ChatMessagesSearch search) {
        long count = service.count(search);
        List<ChatMessage> chatMessages = service.find(search);
        List<ChatMessageForm> resources = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageForm form = entityMapper.map(chatMessage, ChatMessageForm.class);

            resources.add(form);
        }

        return new Table<>(resources, count) {};
    }
}
