SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for access_token
-- ----------------------------
DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token` (
  `token_id` char(32) NOT NULL COMMENT '主键id',
  `user_id` char(32) NOT NULL COMMENT '用户id',
  `is_invalid` tinyint(1) NOT NULL COMMENT '是否已失效(0:否;1:是)',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `expired_date` datetime NOT NULL COMMENT '过期时间',
  `platform` char(2) DEFAULT '' COMMENT '平台(1:安卓;2:IOS)',
  `token_type` char(1) DEFAULT '' COMMENT 'token类型(1:APP;2:微信小程序;3:web)',
  `user_type` char(1) NOT NULL DEFAULT '' COMMENT '用户类型(2:普通用户;1:小程序用户)',
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_user` char(32) DEFAULT NULL COMMENT '创建人id',
  `updated_user` char(32) DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`token_id`) USING BTREE,
  KEY `idx_id_user` (`user_id`) USING BTREE COMMENT '用户id索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='accessToken记录表';

-- ----------------------------
-- Table structure for auth_client
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client` (
  `id` char(32) NOT NULL COMMENT '主键id',
  `client_code` varchar(255) NOT NULL COMMENT '服务编码',
  `client_secret` varchar(255) NOT NULL COMMENT '服务密钥',
  `service_name` varchar(255) NOT NULL COMMENT '服务名',
  `is_locked` tinyint(1) NOT NULL COMMENT '是否锁定',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `date_created` datetime DEFAULT NULL COMMENT '创建时间',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_user_name` varchar(100) DEFAULT NULL COMMENT '创建人姓名',
  `date_updated` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_user_name` varchar(100) DEFAULT NULL COMMENT '更新姓名',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除(0:否;1:是)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_code` (`client_code`(191)) USING BTREE COMMENT '服务code索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='认证服务';

-- ----------------------------
-- Table structure for auth_client_service
-- ----------------------------
DROP TABLE IF EXISTS `auth_client_service`;
CREATE TABLE `auth_client_service` (
  `id` char(32) NOT NULL COMMENT '主键',
  `client_id` char(32) NOT NULL COMMENT '客户端id',
  `service_id` char(32) NOT NULL COMMENT '服务id',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  `created_user` varchar(32) NOT NULL COMMENT '创建者id',
  `created_user_name` varchar(100) NOT NULL COMMENT '创建者名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='客户端可访问服务关系表';


-- ----------------------------
-- Table structure for data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `data_dictionary`;
CREATE TABLE `data_dictionary` (
  `dictionary_id` varchar(32) NOT NULL COMMENT '系统ID',
  `code` varchar(50) DEFAULT NULL COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `describes` varchar(500) DEFAULT NULL COMMENT '描述',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常   99:删除',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_date` datetime DEFAULT NULL COMMENT '更新时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`dictionary_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='数据字典表 ';

-- ----------------------------
-- Table structure for data_dictionary_details
-- ----------------------------
DROP TABLE IF EXISTS `data_dictionary_details`;
CREATE TABLE `data_dictionary_details` (
  `details_id` varchar(32) NOT NULL COMMENT '系统ID',
  `dictionary_id` varchar(32) DEFAULT NULL COMMENT '数据字典ID',
  `code` varchar(50) DEFAULT NULL COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `describes` varchar(100) DEFAULT NULL COMMENT '描述',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常   99:删除',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_date` datetime DEFAULT NULL COMMENT '更新时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`details_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='数据字典明细表 ';

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `file_id` varchar(32) NOT NULL COMMENT '文件ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `alias_name` varchar(50) NOT NULL COMMENT '别名 上传后系统定义名称',
  `model` varchar(50) NOT NULL COMMENT '分类 存文件存放的文件夹英文名称，例如头像文件夹、模板文件夹等',
  `type` varchar(50) NOT NULL COMMENT '类型',
  `url` varchar(100) NOT NULL COMMENT '地址',
  `size` bigint(20) NOT NULL COMMENT '大小 单位B',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常   99:删除',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_date` datetime DEFAULT NULL COMMENT '更新时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`file_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='文件信息表 ';

-- ----------------------------
-- Table structure for jurisdiction
-- ----------------------------
DROP TABLE IF EXISTS `jurisdiction`;
CREATE TABLE `jurisdiction` (
  `jurisdiction_id` varchar(32) NOT NULL COMMENT '系统ID',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `code` varchar(50) DEFAULT NULL COMMENT '权限编号',
  `type` varchar(50) DEFAULT NULL COMMENT '权限类型',
  `url` varchar(50) DEFAULT NULL COMMENT '权限URL',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常   99:删除',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_date` datetime DEFAULT NULL COMMENT '更新时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`jurisdiction_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='权限表 ';

-- ----------------------------
-- Table structure for rest_operator_log
-- ----------------------------
DROP TABLE IF EXISTS `rest_operator_log`;
CREATE TABLE `rest_operator_log` (
  `id_log` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `id_request` char(32) NOT NULL COMMENT '请求id',
  `req_url` varchar(1000) NOT NULL COMMENT '请求地址',
  `req_method` varchar(10) NOT NULL DEFAULT '' COMMENT '请求方式',
  `req_param` varchar(4000) NOT NULL DEFAULT '' COMMENT '请求参数',
  `req_header` varchar(4000) NOT NULL DEFAULT '' COMMENT '请求头',
  `req_body` text COMMENT '请求内容',
  `req_ip` varchar(255) NOT NULL DEFAULT '' COMMENT '请求ip',
  `user_ip` varchar(255) NOT NULL DEFAULT '' COMMENT '用户的IP',
  `req_date` datetime NOT NULL COMMENT '请求时间',
  `id_user` varchar(32) NOT NULL DEFAULT '' COMMENT '用户id',
  `service_name` char(255) NOT NULL COMMENT '应用名称',
  `system_code` varchar(255) NOT NULL COMMENT '系统code',
  `module` char(20) NOT NULL COMMENT '模块',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '接口描述',
  `id_client` char(32) NOT NULL DEFAULT '' COMMENT '客户端ID',
  `date_created` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id_log`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='http接口请求日志记录表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(32) NOT NULL COMMENT '系统ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常   99:删除',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`role_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='角色表 ';

-- ----------------------------
-- Table structure for union_role_jurisdiction
-- ----------------------------
DROP TABLE IF EXISTS `union_role_jurisdiction`;
CREATE TABLE `union_role_jurisdiction` (
  `role_id` varchar(32) NOT NULL COMMENT '角色系统ID',
  `jurisdiction_id` varchar(32) NOT NULL COMMENT '权限系统ID',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_date` datetime DEFAULT NULL COMMENT '更新时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`role_id`,`jurisdiction_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='角色权限关联表 ';

-- ----------------------------
-- Table structure for union_user_role
-- ----------------------------
DROP TABLE IF EXISTS `union_user_role`;
CREATE TABLE `union_user_role` (
  `user_id` varchar(32) NOT NULL COMMENT '账户表系统ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色表系统ID',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_date` datetime DEFAULT NULL COMMENT '更新时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='账号角色关联表 ';

-- ----------------------------
-- Table structure for union_user_third
-- ----------------------------
DROP TABLE IF EXISTS `union_user_third`;
CREATE TABLE `union_user_third` (
  `user_id` varchar(32) NOT NULL COMMENT '账户表系统ID',
  `third_type` int(11) NOT NULL COMMENT '关联类型 1:微信 2:QQ 3:微博',
  `third_uuid` varchar(50) NOT NULL COMMENT '三方信息ID',
  `third_url` varchar(500) DEFAULT NULL COMMENT '三方头像',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户关联第三方 ';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(32) NOT NULL COMMENT '系统ID',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `type` varchar(50) NOT NULL COMMENT '用户类型（WECHAT：小程序用户）',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1:正常   99:删除',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `image_url` varchar(50) DEFAULT NULL COMMENT '头像',
  `created_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `serial_number` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号标识 自增',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户表 ';
