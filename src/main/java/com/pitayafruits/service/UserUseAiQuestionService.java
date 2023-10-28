package com.pitayafruits.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitayafruits.pojo.po.UserUseAiQuestion;

/**
 * 用户使用AI提问的问题service
 */
public interface UserUseAiQuestionService extends IService<UserUseAiQuestion> {

    //保存用户的问题
    void saveUserQuestion(String userId,String question);
}
