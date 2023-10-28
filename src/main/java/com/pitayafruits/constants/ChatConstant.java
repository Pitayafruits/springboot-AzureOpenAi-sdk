package com.pitayafruits.constants;

import com.pitayafruits.pojo.vo.ChatMessage;

import java.util.ArrayList;
import java.util.List;
/**
 * AI身份信息常量类
 */
public class ChatConstant {

    public static final String[] MESSAGES = {
            "预置语料",
    };

    public static List<ChatMessage> generateSystemMessages() {
        List<ChatMessage> messages = new ArrayList<>();
        for (String content : MESSAGES) {
            messages.add(new ChatMessage(ChatMessage.Role.SYSTEM, content));
        }
        return messages;
    }
}

