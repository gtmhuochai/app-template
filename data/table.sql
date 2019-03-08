
-- ----------------------------
-- Table structure for sys_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限角色表主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_authority
-- ----------------------------
INSERT INTO `sys_authority` VALUES (1, 'ROLE_ADMIN', '超级管理');
INSERT INTO `sys_authority` VALUES (2, 'ROLE_GENERAL', '普通用户');
INSERT INTO `sys_authority` VALUES (3, 'ROLE_MENU_CONSIGNMENT', '菜单管理');
INSERT INTO `sys_authority` VALUES (4, 'ROLE_USER_ROLE', '用户管理');
INSERT INTO `sys_authority` VALUES (5, 'ROLE_USER_ROLE_ADD', '用户添加');
INSERT INTO `sys_authority` VALUES (6, 'ROLE_USER_ROLE_EDIT', '用户编辑');
INSERT INTO `sys_authority` VALUES (7, 'ROLE_USER_ROLE_DELETE', '用户删除');
INSERT INTO `sys_authority` VALUES (8, 'ROLE_USER_ROLE_QUERY', '用户查询');
INSERT INTO `sys_authority` VALUES (9, 'ROLE_ROLE_AUTHORITY', '角色管理');
INSERT INTO `sys_authority` VALUES (10, 'ROLE_ROLE_AUTHORITY_ADD', '角色添加');
INSERT INTO `sys_authority` VALUES (11, 'ROLE_ROLE_AUTHORITY_EDIT', '角色编辑');
INSERT INTO `sys_authority` VALUES (12, 'ROLE_ROLE_AUTHORITY_DELETE', '角色删除');
INSERT INTO `sys_authority` VALUES (13, 'ROLE_ROLE_AUTHORITY_QUERY', '角色查询');

-- ----------------------------
-- Table structure for sys_login_history
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_history`;
CREATE TABLE `sys_login_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id，外键关联到用户表',
  `login_time` int(12) NULL DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `remark` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录历史' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `enabled` bit(1) NULL DEFAULT NULL COMMENT ' 是否禁用',
  `display_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', b'1', '超级管理员');
INSERT INTO `sys_role` VALUES (2, 'GENERAL', b'1', '普通用户');
INSERT INTO `sys_role` VALUES (3, 'USER', b'1', '用户管理员');
INSERT INTO `sys_role` VALUES (4, 'ROLE', b'1', '角色管理员');

-- ----------------------------
-- Table structure for sys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色Id，外键关联到角色表',
  `authority_id` int(11) NULL DEFAULT NULL COMMENT '权限Id，外键关联到权限表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_authority
-- ----------------------------
INSERT INTO `sys_role_authority` VALUES (1, 1, 1);
INSERT INTO `sys_role_authority` VALUES (2, 2, 2);
INSERT INTO `sys_role_authority` VALUES (3, 3, 4);
INSERT INTO `sys_role_authority` VALUES (4, 3, 5);
INSERT INTO `sys_role_authority` VALUES (5, 3, 6);
INSERT INTO `sys_role_authority` VALUES (6, 3, 7);
INSERT INTO `sys_role_authority` VALUES (7, 3, 8);
INSERT INTO `sys_role_authority` VALUES (8, 4, 9);
INSERT INTO `sys_role_authority` VALUES (9, 4, 10);
INSERT INTO `sys_role_authority` VALUES (10, 4, 11);
INSERT INTO `sys_role_authority` VALUES (11, 4, 12);
INSERT INTO `sys_role_authority` VALUES (12, 4, 13);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `type` tinyint(3) NOT NULL DEFAULT 2 COMMENT '用户类型，外键，关联至用户类型表',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `real_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `birthday` int(11) NULL DEFAULT NULL COMMENT '用户生日',
  `sex` tinyint(2) NULL DEFAULT NULL COMMENT '用户性别',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `enabled` tinyint(4) NULL DEFAULT NULL COMMENT '用户是否有效',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户个人网址',
  `mobile` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户手机，中国手机不带国家代码，国际手机号格式为：国家代码-手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '个性签名',
  `last_password_reset_date` int(11) NULL DEFAULT NULL COMMENT '最后密码重置时间',
  `reg_time` int(11) NULL DEFAULT NULL COMMENT '注册时间',
  `disabled_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '禁用原因',
  `last_login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '最后登录ip',
  `status` tinyint(3) NOT NULL DEFAULT 1 COMMENT '用户状态;0:禁用,1未验证,2:正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`) USING BTREE,
  UNIQUE INDEX `mobile_UNIQUE`(`mobile`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, 'admin', '$2a$10$NPMwNQtdEgk29WG47L7jy.OEyV9zbYIzx6a9w905ZwscL4dsJmUtK', '超级管理员', NULL, NULL, NULL, 'admin@admin.com', 1, NULL, NULL, NULL, '个性签名', 1536508800, NULL, NULL, '172.0.0.1', 1);
INSERT INTO `sys_user` VALUES (2, 2, 'general', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', '普通用户', NULL, NULL, NULL, 'general@user.com', 1, NULL, NULL, NULL, '个性签名', 1536508800, NULL, NULL, '172.0.0.1', 1);
INSERT INTO `sys_user` VALUES (3, 2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', '权限管理员', NULL, NULL, NULL, 'user@user.com', 1, NULL, NULL, NULL, '个性签名', 1536508800, NULL, NULL, '172.0.0.1', 1);
INSERT INTO `sys_user` VALUES (4, 2, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', '禁用用户', NULL, NULL, NULL, 'disabled@user.com', 0, NULL, NULL, NULL, '个性签名', 1536508800, NULL, NULL, '172.0.0.1', 0);

-- -----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户Id，外键关联到用户表',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色Id，外键关联到角色表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 1, 2);
INSERT INTO `sys_user_role` VALUES (3, 2, 2);
INSERT INTO `sys_user_role` VALUES (4, 3, 3);
INSERT INTO `sys_user_role` VALUES (5, 3, 4);
INSERT INTO `sys_user_role` VALUES (6, 4, 5);