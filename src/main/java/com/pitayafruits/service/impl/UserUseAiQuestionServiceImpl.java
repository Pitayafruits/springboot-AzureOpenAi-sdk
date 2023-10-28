package com.pitayafruits.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitayafruits.pojo.po.UserUseAiQuestion;
import com.pitayafruits.mapper.UserUseAiQuestionMapper;
import com.pitayafruits.service.UserUseAiQuestionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserUseAiQuestionServiceImpl extends ServiceImpl<UserUseAiQuestionMapper, UserUseAiQuestion> implements UserUseAiQuestionService {

    //保存用户的问题
    @Override
    public void saveUserQuestion(String userId, String question) {
        //1.定义用户提问实体
        UserUseAiQuestion userUseAiQuestion = new UserUseAiQuestion();
        //2.设置值
        userUseAiQuestion.setUserId(Long.parseLong(userId));
        userUseAiQuestion.setUserQuestion(question);
        userUseAiQuestion.setQuestionTime(new Timestamp(System.currentTimeMillis()));
        userUseAiQuestion.setStatus(1);
        //3.保存
        save(userUseAiQuestion);
    }

}
