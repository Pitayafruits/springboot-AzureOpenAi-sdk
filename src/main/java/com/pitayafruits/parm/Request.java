package com.pitayafruits.parm;

import lombok.Data;

/**
 * 请求类
 */
@Data
public class Request {

  //用户输入的问题
  private String userContent;

  //用户id
  private String userId;

}
