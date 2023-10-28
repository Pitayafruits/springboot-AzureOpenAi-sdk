package com.pitayafruits.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pitayafruits.constants.ChatConstant;
import com.pitayafruits.parm.Request;
import com.pitayafruits.pojo.vo.ChatMessage;
import com.pitayafruits.pojo.vo.ChatRequest;
import com.pitayafruits.service.AzureOpenAIService;
import com.pitayafruits.service.ChatHistoryService;
import java.util.List;

import com.pitayafruits.service.UserUseAiQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;


/**
 * AI Controller
 */
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ChatController {

  @Value("${azure.apiKey}")
  private String apiKey;

  @Value("${azure.deployment}")
  private String deployment;

  @Value("${azure.endpoint}")
  private String endpoint;

  @Value("${azure.apiVersion}")
  private String apiVersion;

  private final ChatHistoryService chatHistoryService;

  private final UserUseAiQuestionService userUseAiQuestionService;


  /**
   * 对话
   */
  @PostMapping("/chat")
  public ResponseEntity<JSONObject> chat(@RequestBody Request request){
    //1.从请求体中获取用户的消息、用户ID
    String userContent = request.getUserContent();
    String userId = request.getUserId();
    //2.创建一个AzureOpenAIService实例
    AzureOpenAIService azureOpenAIService =
        new AzureOpenAIService(apiKey, deployment, endpoint,apiVersion);
    //3.创建一个新的ChatMessage对象，表示用户的消息
    ChatMessage userMessage = new ChatMessage(ChatMessage.Role.USER,userContent);
    //4.从chatHistories中获取与该用户ID关联的对话历史
    //List<ChatMessage> chatHistory = chatHistories.get(userId);
    List<ChatMessage> chatHistory = chatHistoryService.getHistory(userId);
    //5.如果对话历史不存在，则创建一个新的对话历史并且把当前对话内容加进去
    if (chatHistory.size() == 0) {
      chatHistory = ChatConstant.generateSystemMessages();
      //chatHistories.put(userId, chatHistory);
      for (ChatMessage chatMessage : chatHistory) {
        chatHistoryService.saveMessage(userId,chatMessage);
      }
    }
    //6.将用户的消息添加到对话历史中
    chatHistory.add(userMessage);
    chatHistoryService.saveMessage(userId, userMessage);
    //7.创建一个新的ChatRequest对象，并设置温度参数
    ChatRequest chatRequest = new ChatRequest();
    chatRequest.setTemperature(0.9);
    //8.将对话历史设置为ChatRequest的消息
    chatRequest.setMessages(chatHistory);
    //9.调用AzureOpenAIService的chatCompletion方法，发送请求并获取API的回复
    String responseString = azureOpenAIService.chatCompletion(chatRequest);
    JSONObject responseJson = JSON.parseObject(responseString);
    //从JSON对象中提取出助手的回复内容
    String reply = responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    //10.将助手的回复添加到对话历史中
    ChatMessage assistantMessage = new ChatMessage(ChatMessage.Role.ASSISTANT, reply);
    chatHistory.add(new ChatMessage(ChatMessage.Role.ASSISTANT, reply));
    chatHistoryService.saveMessage(userId, assistantMessage);
    //11.创建一个新的JSON对象来存储回复内容
    JSONObject replyJson = new JSONObject();
    replyJson.put("content", reply);
    //12.存储用户提出的问题
    userUseAiQuestionService.saveUserQuestion(userId,userContent);
    //13.返回这个JSON对象
    return ResponseEntity.ok(replyJson);
  }

  /**
   * 清除对话历史
   */
  @PostMapping("/cleanChatHistory")
  public void clearChatHistory(@RequestBody Request request){
    chatHistoryService.clearHistory(request.getUserId());
  }

}
