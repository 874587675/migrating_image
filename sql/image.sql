/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80046 (8.0.46)
 Source Host           : 43.139.194.60:3306
 Source Schema         : print

 Target Server Type    : MySQL
 Target Server Version : 80046 (8.0.46)
 File Encoding         : 65001

 Date: 20/11/2026 14:42:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config`  (
  `config_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置ID',
  `config_key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置KEY',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置名称',
  `config_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '配置值',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态（0-禁用 1-启用）',
  `config_type` tinyint(1) NULL DEFAULT NULL COMMENT '配置类型（1=图片，2=富文本，3=文字）',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除 1-已删除）',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '配置表';

-- ----------------------------
-- Records of t_config
-- ----------------------------
BEGIN;
INSERT INTO `t_config` (`config_id`, `config_key`, `config_name`, `config_value`, `sort`, `status`, `config_type`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_deleted`, `delete_time`) VALUES ('1', 'USER_DEFAULT_AVATAR', '用户默认头像', NULL, 0, 1, 1, NULL, '2025-12-17 00:03:57', NULL, '2026-03-14 11:52:03', 0, NULL), ('2', 'USER_DEFAULT_NAME', '用户默认名称', NULL, 0, 1, 3, NULL, '2025-12-17 00:04:47', NULL, '2026-03-14 11:52:03', 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu`  (
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '父菜单ID',
  `menu_sort` int NULL DEFAULT 0 COMMENT '显示顺序',
  `menu_type` tinyint(1) NOT NULL COMMENT '菜单类型（1-目录 2-菜单 3-按钮）',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `route_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '菜单显示（0隐藏 1显示）',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '菜单状态（0停用 1正常）',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `is_frame` tinyint(1) NULL DEFAULT NULL COMMENT '外链地址（0否 1是）',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除 1-已删除）',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单表';

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_menu` (`menu_id`, `menu_name`, `parent_id`, `menu_sort`, `menu_type`, `path`, `component`, `route_name`, `visible`, `status`, `icon`, `is_frame`, `permission`, `remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_deleted`, `delete_time`) VALUES ('1', '权限管理', '0', 1, 1, '/permission', NULL, 'Permission', 1, 1, 'Lock', 0, 'permission', NULL, NULL, '2025-09-21 04:44:38', NULL, '2026-07-14 06:27:12', 0, NULL), ('11', '前台用户管理', '2', 0, 2, '/permission/user/front', 'FrontUserVue', 'FrontUser', 1, 1, 'Avatar', 0, 'permission:user:front', NULL, NULL, '2026-07-06 12:16:28', NULL, '2026-07-10 01:33:39', 0, NULL), ('12', '后台用户管理', '2', 0, 2, '/permission/user/back', 'BackUserVue', 'BackUser', 1, 1, 'Avatar', 0, 'permission:user:back', NULL, NULL, '2026-07-06 12:16:28', NULL, '2026-07-10 01:33:39', 0, NULL), ('2', '用户管理', '1', 1, 2, '/permission/user', NULL, 'User', 1, 1, 'Avatar', 0, 'permission:user', NULL, NULL, '2025-11-10 22:29:12', NULL, '2026-07-14 06:27:16', 0, NULL), ('2076993525102665730', '2', '11', 3, 1, '2', '2', '2', 1, 0, 'AlarmClock', 1, '111', '', NULL, '2026-07-14 19:33:40', NULL, '2026-07-14 19:35:45', 1, NULL), ('3', '角色管理', '1', 2, 2, '/permission/role', 'RoleVue', 'Role', 1, 1, 'UserFilled', 0, 'permission:role', NULL, NULL, '2025-11-10 22:46:44', NULL, '2026-07-06 08:03:39', 0, NULL), ('4', '菜单管理', '1', 3, 2, '/permission/menu', 'MenuVue', 'Menu', 1, 1, 'Menu', 0, 'permission:menu', NULL, NULL, '2025-11-10 23:31:46', NULL, '2026-06-26 03:14:11', 0, NULL), ('5', '后台用户详情', '12', 1, 3, '/permission/user/back/query', NULL, NULL, 1, 1, NULL, 0, 'permission:user:back:query', NULL, NULL, '2025-11-17 20:02:24', NULL, '2026-07-14 06:33:37', 0, NULL), ('6', '添加后台用户', '12', 2, 3, '/permission/user/back/add', NULL, NULL, 1, 1, NULL, 0, 'permission:user:back:add', NULL, NULL, '2025-11-17 20:08:11', NULL, '2026-07-14 06:33:40', 0, NULL), ('7', '修改后台用户', '12', 3, 3, '/permission/user/back/update', NULL, NULL, 1, 1, NULL, 0, 'permission:user:back:update', NULL, NULL, '2025-11-17 20:08:54', NULL, '2026-07-14 06:33:45', 0, NULL), ('8', '删除后台用户', '12', 4, 3, '/permission/user/back/delete', NULL, NULL, 1, 1, NULL, 0, 'permission:user:back:delete', NULL, NULL, '2025-11-17 20:09:16', NULL, '2026-07-14 06:33:48', 0, NULL), ('9', '后台用户列表', '12', 5, 3, '/permission/user/back/list', NULL, NULL, 1, 1, NULL, 0, 'permission:user:back:list', NULL, NULL, '2025-11-17 20:09:46', NULL, '2026-07-14 06:33:51', 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`  (
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限',
  `role_sort` int NULL DEFAULT 0 COMMENT '顺序',
  `data_scope` tinyint(1) NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：仅本人数据权限）',
  `role_status` tinyint(1) NULL DEFAULT 0 COMMENT '角色状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除 1-已删除）',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `role_status`, `remark`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_deleted`, `delete_time`) VALUES ('1', '管理员', 'admin', 1, 1, 0, '', NULL, '2025-09-21 04:17:12', NULL, '2026-07-14 10:23:52', 0, NULL), ('2', '普通用户', 'common', 2, 1, 0, '', NULL, '2025-09-21 04:17:12', NULL, '2026-07-14 10:08:39', 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu`  (
  `role_menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色菜单ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单ID',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除 1-已删除）',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色菜单关联表';

-- ----------------------------
-- Records of t_sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role_menu` (`role_menu_id`, `role_id`, `menu_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_deleted`, `delete_time`) VALUES ('1', '1', '1', NULL, '2025-09-21 04:44:10', NULL, '2026-07-14 01:59:59', 1, NULL), ('10', '1', '8', NULL, '2026-07-13 09:58:00', NULL, '2026-07-14 01:59:59', 1, NULL), ('11', '1', '9', NULL, '2026-07-13 09:58:04', NULL, '2026-07-13 11:25:18', 1, NULL), ('2', '1', '2', NULL, '2025-11-04 23:00:35', NULL, '2026-07-14 01:59:59', 1, NULL), ('2076849155229093890', '1', '11', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093891', '1', '1', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093892', '1', '12', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093893', '1', '2', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093894', '1', '3', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093895', '1', '4', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093896', '1', '5', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093897', '1', '6', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093898', '1', '7', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093899', '1', '8', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076849155229093900', '1', '9', NULL, '2026-07-14 09:59:59', NULL, '2026-07-14 02:04:37', 1, NULL), ('2076850322432593922', '1', '12', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593923', '1', '1', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593924', '1', '2', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593925', '1', '3', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593926', '1', '4', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593927', '1', '5', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593928', '1', '6', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593929', '1', '7', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593930', '1', '8', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076850322432593931', '1', '9', NULL, '2026-07-14 10:04:38', NULL, '2026-07-14 02:23:51', 1, NULL), ('2076855163570847746', '1', '11', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847747', '1', '1', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847748', '1', '12', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847749', '1', '2', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847750', '1', '3', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847751', '1', '4', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847752', '1', '5', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847753', '1', '6', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847754', '1', '7', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847755', '1', '8', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076855163570847756', '1', '9', NULL, '2026-07-14 10:23:52', NULL, '2026-07-14 10:23:52', 0, NULL), ('2076989947874942977', '2076989082220929025', '11', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947874942978', '2076989082220929025', '1', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137281', '2076989082220929025', '12', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137282', '2076989082220929025', '2', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137283', '2076989082220929025', '3', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137284', '2076989082220929025', '4', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137285', '2076989082220929025', '5', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137286', '2076989082220929025', '6', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137287', '2076989082220929025', '7', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137288', '2076989082220929025', '8', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076989947879137289', '2076989082220929025', '9', '1', '2026-07-14 19:19:27', '1', '2026-07-14 19:19:27', 0, NULL), ('2076993196655108097', '2076993196302786562', '11', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302401', '2076993196302786562', '5', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302402', '2076993196302786562', '6', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302403', '2076993196302786562', '7', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302404', '2076993196302786562', '8', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302405', '2076993196302786562', '3', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302406', '2076993196302786562', '4', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302407', '2076993196302786562', '1', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302408', '2076993196302786562', '2', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('2076993196659302409', '2076993196302786562', '12', NULL, '2026-07-14 19:32:21', NULL, '2026-07-14 19:32:21', 0, NULL), ('3', '1', '3', NULL, '2025-11-10 23:01:52', NULL, '2026-07-14 01:59:59', 1, NULL), ('4', '1', '4', NULL, '2025-11-10 23:32:09', NULL, '2026-07-14 01:59:59', 1, NULL), ('5', '1', '11', NULL, '2025-11-17 20:16:58', NULL, '2026-07-14 01:59:59', 1, NULL), ('6', '1', '12', NULL, '2026-07-06 12:22:51', NULL, '2026-07-14 01:59:59', 1, NULL), ('7', '1', '5', NULL, '2026-07-13 09:57:32', NULL, '2026-07-14 01:59:59', 1, NULL), ('8', '1', '6', NULL, '2026-07-13 09:57:40', NULL, '2026-07-14 01:59:59', 1, NULL), ('9', '1', '7', NULL, '2026-07-13 09:57:56', NULL, '2026-07-14 01:59:59', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '后台用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '后台用户名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '后台用户密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '后台用户头像',
  `sex` tinyint(1) NULL DEFAULT 3 COMMENT '后台用户性别（1-男 2-女 3-未知）',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '后台用户昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '后台用户邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '后台用户手机号',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态（1-正常 2-冻结）',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP地址',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除 1-已删除）',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_user_name`(`user_name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '后台用户表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user` (`user_id`, `user_name`, `password`, `avatar`, `sex`, `nick_name`, `email`, `phone`, `status`, `last_login_time`, `last_ip_address`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_deleted`, `delete_time`) VALUES ('1', 'admin', '$2a$10$/Q8FBb.J25sNKCcDAWeSf.laYsZJMozxFEMY1j9EBwqeg3UvjwZFW', 'http://43.139.194.60:9000/print/images/2026-07-14/logo.png', 1, 'Admin', '874587675@qq.com', '16692371201', 1, '2026-11-10 20:30:04', '127.0.0.1', NULL, '2025-09-21 03:52:04', NULL, '2026-11-10 20:46:05', 0, NULL), ('2', 'user', '$2a$10$/Q8FBb.J25sNKCcDAWeSf.laYsZJMozxFEMY1j9EBwqeg3UvjwZFW', 'http://43.139.194.60:9000/print/images/2026-06-22/logo.png', 1, 'user', '874587675@qq.com', '16692371201', 1, '2025-09-23 22:50:25', NULL, NULL, '2025-09-21 03:52:04', NULL, '2026-07-13 15:52:11', 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role`  (
  `user_role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户角色关联ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除 1-已删除）',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色关联表';

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user_role` (`user_role_id`, `user_id`, `role_id`, `create_user_id`, `create_time`, `update_user_id`, `update_time`, `is_deleted`, `delete_time`) VALUES ('1', '1', '1', NULL, '2025-09-21 04:17:30', NULL, '2026-07-14 11:41:51', 1, NULL), ('2', '1', '2', NULL, '2025-09-23 22:49:32', NULL, '2026-07-14 11:41:51', 1, NULL), ('2076485983806099457', '2', '2', NULL, '2026-07-13 09:56:52', NULL, '2026-07-13 02:11:21', 1, NULL), ('2076485983806099458', '2', '1', NULL, '2026-07-13 09:56:52', NULL, '2026-07-13 02:11:21', 1, NULL), ('2076489628383645698', '2', '2', NULL, '2026-07-13 10:11:21', NULL, '2026-07-13 02:11:28', 1, NULL), ('2076489659530547201', '2', '2', NULL, '2026-07-13 10:11:29', NULL, '2026-07-13 07:13:09', 1, NULL), ('2076489659530547202', '2', '1', NULL, '2026-07-13 10:11:29', NULL, '2026-07-13 07:13:09', 1, NULL), ('2076494163440676866', '2076494163356790786', '2', NULL, '2026-07-13 10:29:23', NULL, '2026-07-13 10:29:23', 0, NULL), ('2076564254639370241', '2076564254316408834', '2', NULL, '2026-07-13 15:07:54', NULL, '2026-07-13 15:07:54', 0, NULL), ('2076565578802114561', '2', '1', NULL, '2026-07-13 15:13:09', NULL, '2026-07-13 07:43:28', 1, NULL), ('2076565578802114562', '2', '2', NULL, '2026-07-13 15:13:09', NULL, '2026-07-13 07:43:28', 1, NULL), ('2076573206797742081', '2', '1', NULL, '2026-07-13 15:43:28', NULL, '2026-07-13 07:52:11', 1, NULL), ('2076573206797742082', '2', '2', NULL, '2026-07-13 15:43:28', NULL, '2026-07-13 07:52:11', 1, NULL), ('2076575402330468353', '2', '1', NULL, '2026-07-13 15:52:11', NULL, '2026-07-13 15:52:11', 0, NULL), ('2076575402330468354', '2', '2', NULL, '2026-07-13 15:52:11', NULL, '2026-07-13 15:52:11', 0, NULL), ('2076995586372395010', '1', '1', NULL, '2026-07-14 19:41:51', NULL, '2026-07-14 11:42:41', 1, NULL), ('2076995586372395011', '1', '2', NULL, '2026-07-14 19:41:51', NULL, '2026-07-14 11:42:41', 1, NULL), ('2076995799447232514', '1', '1', NULL, '2026-07-14 19:42:42', NULL, '2026-07-14 19:42:42', 0, NULL), ('2076995799447232515', '1', '2', NULL, '2026-07-14 19:42:42', NULL, '2026-07-14 19:42:42', 0, NULL), ('3', '2', '2', NULL, '2026-07-11 08:38:55', NULL, '2026-07-13 01:56:52', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `open_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'openID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录密码',
  `sex` tinyint(1) NULL DEFAULT 1 COMMENT '用户性别（1-男 2-女 3-未知）',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '后台用户邮箱',
  `birthday` date NULL DEFAULT NULL COMMENT '用户生日',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态（1-正常 2-冻结）',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP地址',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0-未删除 1-已删除）',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`user_id`, `open_id`, `user_name`, `password`, `sex`, `nick_name`, `avatar`, `email`, `birthday`, `phone`, `status`, `last_login_time`, `last_ip_address`, `is_deleted`, `delete_time`, `create_time`, `update_time`) VALUES ('1969471680183033857', NULL, 'admin', '$2a$10$/Q8FBb.J25sNKCcDAWeSf.laYsZJMozxFEMY1j9EBwqeg3UvjwZFW', 1, '小白成', 'http://43.139.194.60:9000/print/images/2026-06-22/logo.png', NULL, '1998-12-01', '16692371201', 1, '2026-04-24 09:56:23', '0:0:0:0:0:0:0:1', 0, NULL, '2025-09-21 02:40:14', '2026-07-13 06:58:18'), ('2070423041993072641', 'o2RUG7KTaiIKYGNtEgibXB9ytOBI', NULL, NULL, 1, NULL, 'http://43.139.194.60:9000/print/images/2026-06-22/logo.png', NULL, NULL, NULL, 1, '2026-06-26 16:24:54', '123.149.1.208', 0, NULL, '2026-06-26 16:24:54', '2026-07-13 06:58:19');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
