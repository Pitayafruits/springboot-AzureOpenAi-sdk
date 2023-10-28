package com.pitayafruits.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户使用AI提问的问题实体
 */
@Data
@TableName("tb_user_question")
public class UserUseAiQuestion implements Serializable {

    //主键
    @TableId(type = IdType.AUTO)
    private Long id;

    //用户id
    private Long userId;

    //用户的问题
    private String userQuestion;

    //问题的时间
    private Timestamp questionTime;

    //状态： 0 已删除 1 正常 2 禁用
    private Integer status;

    //创建时间
    @TableField(fill = FieldFill.INSERT) //创建时自动填充
    private Timestamp gmtCreate;

    //最近一次修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)//创建与修改时自动填充
    private Timestamp gmtModified;
}
