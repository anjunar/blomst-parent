package com.anjunar.blomst.social.chat.messages;

import com.anjunar.blomst.social.chat.ChatMessage;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("social/chat/messages")
public class ChatMessagesResource implements ListResourceTemplate<ChatMessageRow, ChatMessagesSearch> {

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
    @RolesAllowed({"Administrator", "User"})
    public Table<ChatMessageRow> list(ChatMessagesSearch search) {
        long count = service.count(search);
        List<ChatMessage> chatMessages = service.find(search);
        List<ChatMessageRow> resources = new ArrayList<>();
        Table<ChatMessageRow> table = new Table<>(resources, count) {};

        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageRow form = entityMapper.map(chatMessage, ChatMessageRow.class, table);
            resources.add(form);
        }

        table.visible("text", "owner");

        return table;
    }
}
