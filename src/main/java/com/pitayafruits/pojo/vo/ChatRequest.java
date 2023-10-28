package com.pitayafruits.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * 请求参数类
 */
@Data
public class ChatRequest {

  //消息列表
  private List<ChatMessage> messages;

  //生成文本的最大长度。
  @JSONField(name = "max_tokens")
  private int maxTokens = 2400;

  //生成文本的随机性，取值从0到1，较高的“温度”值意味着模型将冒更多的风险。0表示随机性最低，创造性最差。
  private double temperature = 0.75;

  //使用词频惩罚。较高的频率惩罚将阻止模型重复。
  @JSONField(name = "frequency_penalty")
  private double frequencyPenalty = 0.1;

  //使用存在惩罚。较高的存在惩罚将鼓励模型专注于输入提示本身。
  @JSONField(name = "presence_penalty")
  private double presencePenalty = 0.1;

  //从模型预测中选择概率最高的标记，直到达到指定的总概率。默认为1。也就是说，一旦该分布超过top_p值，就会停止生成文本。例如，top_p为0.3表示仅考虑组成前30％概率质量的标记。
  @JSONField(name = "top_p")
  private double topP = 0.95;

  //停止生成文本，当模型生成某些指定字符时，就停止不在生成。默认为空。
  private String stop;
}
