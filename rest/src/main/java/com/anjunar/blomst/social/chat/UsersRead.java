package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.ApplicationWebSocketMessage;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("chat-users-read")
public class UsersRead extends ApplicationWebSocketMessage { }
