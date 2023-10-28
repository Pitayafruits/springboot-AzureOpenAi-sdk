package com.pitayafruits.service;


import com.alibaba.fastjson.JSON;
import com.pitayafruits.pojo.vo.ChatRequest;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;

import java.util.Objects;

/**
 * AzureOpenAI 服务类
 */
public class AzureOpenAIService {

  public static final String API_VERSION_CHAT = "2023-03-15-preview";
  private final Logger logger = LoggerFactory.getLogger(AzureOpenAIService.class);
  private final String apiKey;
  private final String endpoint;
  private final String deployment;

  //创建一个OkHttpClient实例，设置连接、写入和读取的超时时间
  OkHttpClient client = new OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.MINUTES)
      .writeTimeout(1,TimeUnit.MINUTES)
      .readTimeout(1,TimeUnit.MINUTES)
      .build();

  //定义Azure客户端
  private OpenAIClient azureClient = null;

  //构造函数，初始化apiKey、endpoint和deployment
  public AzureOpenAIService(String apiKey, String deployment, String endpoint,
                            String apiVersion) {
    this.apiKey = apiKey == null ? "" : apiKey;
    this.endpoint = endpoint == null ? "" : endpoint.endsWith("/") ? endpoint.substring(0, endpoint.length() - 1) : endpoint;
    this.deployment = deployment == null ? "" : deployment;
    if (!apiKey.isEmpty()) {
      this.azureClient = new OpenAIClientBuilder()
          .endpoint(endpoint)
          .credential(new AzureKeyCredential(apiKey))
          .buildClient();
    }
  }

  /**
   * 聊天完成方法
   */
  public String chatCompletion(ChatRequest request) {
    return callAzureOpenAIAPI("chat/completions", JSON.toJSONString(request), API_VERSION_CHAT);
  }

  /**
   * 调用AzureOpenAI API的方法
   */
  private String callAzureOpenAIAPI(String operation, String requestBodyString, String apiVersion) {
    MediaType mediaType = MediaType.parse("application/json");
    //1.构造URL
    String url = String.format("%s/openai/deployments/%s/%s?api-version=%s", endpoint, deployment, operation, apiVersion);
    logger.info("请求体: {}", requestBodyString);
    //2.构造HTTP请求
    RequestBody body = RequestBody.create(requestBodyString, mediaType);
    Request httpRequest = new Request.Builder().url(url).post(body)
        .addHeader("api-key", apiKey).build();
    try (Response response = client.newCall(httpRequest).execute()) {
      if (!response.isSuccessful()) {
        throw new RuntimeException("异常响应: " + response);
      }
      //3.返回响应体的字符串形式
      return Objects.requireNonNull(response.body()).string();
    } catch (Exception e) {
      throw new RuntimeException("调用Azure OpenAI API发生的错误: " + e.getMessage(), e);
    }
  }

}
