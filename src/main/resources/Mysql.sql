#创建用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` VARCHAR(255) NOT NULL COMMENT '登录用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `email` VARCHAR(255) DEFAULT NULL COMMENT '邮件地址',
  `telephone` VARCHAR(255) DEFAULT NULL COMMENT '联系电话',
  `portrait` VARCHAR(512) DEFAULT NULL COMMENT '头像文件路径',
  `status` INT(2) DEFAULT 0 COMMENT '帐户状态: 0-待审核 1-正常 2-锁定',
  `regist_time` DATE DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT = Compact;

#创建角色表
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` VARCHAR(255) NOT NULL COMMENT '角色名称',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT = Compact;

#创建权限表
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission` VARCHAR(255) NOT NULL COMMENT '权限路径',
  `type` INT(2) NOT NULL COMMENT '权限类型: 0-菜单，1-方法',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT = Compact;


#设置用户角色关联表
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_role_role_1` (`role_id`),
  KEY `fk_user_role_user_1` (`user_id`),
  CONSTRAINT `fk_user_role_role_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

#设置用户角色关联表
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` INT(11) NOT NULL,
  `permission_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_role_permission_role_1` (`role_id`),
  KEY `fk_role_permission_permission_1` (`permission_id`),
  CONSTRAINT `fk_role_permission_role_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_role_permission_permission_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);



#设置检查外键
SET FOREIGN_KEY_CHECKS = 1;