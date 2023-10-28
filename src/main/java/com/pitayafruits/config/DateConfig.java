package com.pitayafruits.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * mybatis-plus自动插入时间配置类
 */
@Component
public class DateConfig implements MetaObjectHandler {
    /**
     * 使用mp做添加操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //设置属性值
        this.setFieldValByName("gmtCreate",new Timestamp(System.currentTimeMillis()),metaObject);
        this.setFieldValByName("gmtModified",new Timestamp(System.currentTimeMillis()),metaObject);
    }

    /**
     * 使用mp做修改操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Timestamp(System.currentTimeMillis()),metaObject);
    }
}
