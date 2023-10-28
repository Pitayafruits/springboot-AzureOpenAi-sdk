package com.pitayafruits.pojo.vo;

import lombok.Data;

@Data
public class ChatMessage {
  private String role;
  private String content;

  public ChatMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  @SuppressWarnings("InterfaceIsType")
  public interface Role {
    String USER = "user";
    String SYSTEM = "system";
    String ASSISTANT = "assistant";
  }
}
