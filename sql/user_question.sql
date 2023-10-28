CREATE TABLE `tb_user_question`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       bigint   NOT NULL COMMENT '用户的id',
    `user_question` text     NOT NULL COMMENT '用户的问题',
    `question_time` datetime NOT NULL COMMENT '问题的时间',
    `status`        tinyint  NOT NULL COMMENT '状态： 0 已删除 1 正常 2 禁用',
    `gmt_create`    datetime NOT NULL COMMENT '创建时间',
    `gmt_modified`  datetime NOT NULL COMMENT '最近一次修改时间',
    PRIMARY KEY (`id`)
) COMMENT = '使用AI的问题记录表'