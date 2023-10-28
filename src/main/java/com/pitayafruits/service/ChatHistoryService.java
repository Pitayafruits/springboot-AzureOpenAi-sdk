package com.pitayafruits.service;

import com.alibaba.fastjson.JSON;
import com.pitayafruits.pojo.vo.ChatMessage;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 聊天消息服务类
 */
@Service
public class ChatHistoryService {
  private final StringRedisTemplate redisTemplate;

  public ChatHistoryService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  // 存储聊天消息
  public void saveMessage(String userId, ChatMessage message) {
    redisTemplate.opsForList().rightPush(userId, JSON.toJSONString(message));
    redisTemplate.expire(userId, Duration.ofHours(1));
  }

  // 检索聊天历史
  public List<ChatMessage> getHistory(String userId) {
    //1.从Redis中检索聊天历史
    List<ChatMessage> chatHistory = redisTemplate.opsForList().range(userId, 0, -1)
        .stream()
        .map(message -> JSON.parseObject(message, ChatMessage.class))
        .collect(Collectors.toList());
    //2.计算聊天历史的令牌数
    int tokenCount = chatHistory.stream().mapToInt(message -> message.getContent().length()).sum();
    //3.如果令牌数超过3800,就删除一些旧的消息
    int index = 0; //保留前XX条预置语料
    while(tokenCount > 3800 && index < chatHistory.size()){
      ChatMessage removedMessage = chatHistory.remove(index);
      tokenCount -= removedMessage.getContent().length();
      //从redis中删除相应的消息
      redisTemplate.opsForList().remove(userId,0,JSON.toJSONString(removedMessage));
    }
    return chatHistory;
  }

  // 清除聊天历史
  public void clearHistory(String userId) {
    redisTemplate.delete(userId);
  }

}
