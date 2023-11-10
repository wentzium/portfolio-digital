/*
Navicat MySQL Data Transfer

Source Server         : 192.168.247.128_leica
Source Server Version : 50556
Source Host           : 192.168.247.128:3306
Source Database       : leica

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2019-08-14 22:10:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comm_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `comm_sequence`;
CREATE TABLE `comm_sequence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(10) DEFAULT '0',
  `name` varchar(50) NOT NULL,
  `current_value` bigint(20) unsigned NOT NULL DEFAULT '0',
  `increment` int(11) NOT NULL DEFAULT '1',
  `is_valid` int(10) DEFAULT '0',
  `create_by` varchar(30) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sequence_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='公共序列表';

-- ----------------------------
-- Records of comm_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for `t_apply`
-- ----------------------------
DROP TABLE IF EXISTS `t_apply`;
CREATE TABLE `t_apply` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `apply_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '申请单号',
  `patient_id` bigint(11) DEFAULT NULL COMMENT '病人ID',
  `pathology_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病理号',
  `source` int(3) DEFAULT NULL COMMENT '申请单来源 1-门诊站点 2-手术站点 3-预约站点',
  `site_id` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`) USING BTREE,
  UNIQUE KEY `apply_no_UNIQUE` (`apply_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `t_appointment_apply`
-- ----------------------------
DROP TABLE IF EXISTS `t_appointment_apply`;
CREATE TABLE `t_appointment_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apply_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预约申请单号',
  `operation_room_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手术室名称',
  `operation_room_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手术室编号',
  `operation_room_tel` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手术室电话',
  `operation_type` int(11) DEFAULT NULL COMMENT '手术类型',
  `operation_doctor` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手术医生',
  `patient_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病人姓名',
  `operation_time` timestamp NULL DEFAULT NULL COMMENT '手术日期',
  `expect_delivery_sample_time` timestamp NULL DEFAULT NULL COMMENT '预计送样日期',
  `site_id` bigint(20) DEFAULT NULL COMMENT '预约站点id',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='预约冰冻申请单';

-- ----------------------------
-- Records of t_appointment_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `t_delivery_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_delivery_info`;
CREATE TABLE `t_delivery_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `sample_id` bigint(11) DEFAULT NULL COMMENT '样本主键',
  `sample_no` varchar(50) DEFAULT NULL COMMENT '样本编号',
  `desc` varchar(100) DEFAULT NULL COMMENT '物流信息描述',
  `create_at` timestamp NULL DEFAULT NULL,
  `creat_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_delivery_info
-- ----------------------------

-- ----------------------------
-- Table structure for `t_dic`
-- ----------------------------
DROP TABLE IF EXISTS `t_dic`;
CREATE TABLE `t_dic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `item_code` varchar(45) DEFAULT NULL COMMENT '编码',
  `item_name` varchar(45) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_dic
-- ----------------------------

-- ----------------------------
-- Table structure for `t_dic_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_dic_item`;
CREATE TABLE `t_dic_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dic_id` bigint(20) DEFAULT NULL,
  `item_code` varchar(45) DEFAULT NULL,
  `item_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项';

-- ----------------------------
-- Records of t_dic_item
-- ----------------------------

-- ----------------------------
-- Table structure for `t_image`
-- ----------------------------
DROP TABLE IF EXISTS `t_image`;
CREATE TABLE `t_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `url` varchar(200) DEFAULT NULL COMMENT 'url路径',
  `type` int(3) DEFAULT '1' COMMENT '图片类型 1-样本图片',
  `sample_id` bigint(11) DEFAULT NULL COMMENT '样本编号',
  `sample_no` varchar(50) DEFAULT NULL COMMENT '样本编号',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片信息';

-- ----------------------------
-- Records of t_image
-- ----------------------------

-- ----------------------------
-- Table structure for `t_login_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_login_user`;
CREATE TABLE `t_login_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT '' COMMENT '密码加密盐值',
  `id_code` varchar(100) DEFAULT '' COMMENT '唯一标识码',
  `status` int(5) DEFAULT '1' COMMENT '用户状态 1-正常 2-锁定',
  `login_times` int(11) DEFAULT '0' COMMENT '登录次数',
  `password_expire_time` timestamp NULL DEFAULT NULL COMMENT '密码过期时间',
  `last_login_ip` varchar(45) DEFAULT '' COMMENT '上次登录IP',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of t_login_user
-- ----------------------------

-- ----------------------------
-- Table structure for `t_patient_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_patient_info`;
CREATE TABLE `t_patient_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '病人ID',
  `patient_no` varchar(20) NOT NULL COMMENT '病人编号',
  `name` varchar(100) DEFAULT NULL COMMENT '病人姓名',
  `sex` varchar(20) DEFAULT NULL COMMENT '性别',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `marriage_status` varchar(20) DEFAULT NULL COMMENT '婚姻状态',
  `birth_place` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `occupation` varchar(100) DEFAULT NULL COMMENT '职业',
  `address` varchar(300) DEFAULT NULL COMMENT '地址',
  `inspection_hospital` varchar(200) DEFAULT NULL COMMENT '送检医院',
  `department` varchar(100) DEFAULT NULL COMMENT '科别',
  `outpatient_no` varchar(50) DEFAULT NULL COMMENT '门诊号',
  `hospital_no` varchar(50) DEFAULT NULL COMMENT '住院号',
  `sickroom` varchar(50) DEFAULT NULL COMMENT '病房',
  `bed_no` varchar(50) DEFAULT NULL COMMENT '病床',
  `medical_history_summary` varchar(500) DEFAULT NULL COMMENT '病史摘要及临床检查所见',
  `operation_summary` varchar(200) DEFAULT NULL COMMENT '手术名称及手术所见',
  `clinical_diagnosis` varchar(200) DEFAULT NULL COMMENT '临床诊断（丢弃）',
  `pregnancy` varchar(45) DEFAULT NULL COMMENT '妊次',
  `parity` varchar(45) DEFAULT NULL COMMENT '产次',
  `last_pregnancy` varchar(45) DEFAULT NULL COMMENT '末次妊娠',
  `menstrual_history` varchar(45) DEFAULT NULL COMMENT '月经史',
  `first_menstruation` varchar(45) DEFAULT NULL COMMENT '初经',
  `period` varchar(45) DEFAULT NULL COMMENT '周期',
  `pre_menstrual` varchar(45) DEFAULT NULL COMMENT '前次月经',
  `last_menstrual` varchar(45) DEFAULT NULL COMMENT '末次月经',
  `endocrine_therapy_flag` int(2) DEFAULT '0' COMMENT '是否内分泌治疗 0-否 1-是',
  `treatment_date` timestamp NULL DEFAULT NULL COMMENT '治疗日期',
  `dose` varchar(45) DEFAULT NULL COMMENT '剂量',
  `dc_or_sampling_date` timestamp NULL DEFAULT NULL COMMENT '刮宫或采样日期',
  `tumor_site` varchar(45) DEFAULT NULL COMMENT '肿瘤部位',
  `tumor_size_and_shape` varchar(100) DEFAULT NULL COMMENT '肿瘤大小形状',
  `activity_degree` varchar(45) DEFAULT NULL COMMENT '活动度',
  `tumor_growth_rate` varchar(45) DEFAULT NULL COMMENT '肿瘤生长速度',
  `firmness` varchar(45) DEFAULT NULL COMMENT '肿瘤坚度',
  `tumor_discovery_date` timestamp NULL DEFAULT NULL COMMENT '肿瘤发现日期',
  `transfer_location` varchar(100) DEFAULT NULL COMMENT '转移位置',
  `operation_time` timestamp NULL DEFAULT NULL COMMENT '预约冰冻-手术日期',
  `estimated_freezing_time` varchar(45) DEFAULT NULL COMMENT '预约冰冻-预计冰冻时间',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='病人信息';

-- ----------------------------
-- Records of t_patient_info
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sample`
-- ----------------------------
DROP TABLE IF EXISTS `t_sample`;
CREATE TABLE `t_sample` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `apply_id` bigint(20) DEFAULT NULL COMMENT '申请单ID',
  `apply_no` varchar(100) DEFAULT NULL COMMENT '申请单号',
  `patient_id` bigint(20) DEFAULT NULL COMMENT '病人ID',
  `sample_no` varchar(100) DEFAULT NULL COMMENT '样本编号',
  `separation_time` timestamp NULL DEFAULT NULL COMMENT '离体时间',
  `collection_location_dic_id` bigint(11) DEFAULT NULL COMMENT '采集部位字典值ID',
  `collection_location_desc` varchar(100) DEFAULT NULL COMMENT '采集部位 描述',
  `type_dic_id` bigint(20) DEFAULT NULL COMMENT '样本类型-字典值ID',
  `type_desc` varchar(100) DEFAULT NULL COMMENT '样本类型 描述',
  `num` int(3) DEFAULT NULL COMMENT '样本组织块数',
  `desc` varchar(100) DEFAULT NULL COMMENT '样本说明',
  `available_status` int(3) DEFAULT '0' COMMENT '可用状态  0-可用 1-作废',
  `separation_status` int(3) DEFAULT '0' COMMENT '离体状态 0-未离体 1-已离体',
  `fixed_status` int(3) DEFAULT '0' COMMENT '固定状态 0-未固定 1-已固定',
  `logistics_status` int(3) DEFAULT '0' COMMENT '物流状态 0-未送出 1-运送中 2-已签收 3-拒收',
  `tag_status` int(3) DEFAULT '0' COMMENT '标签状态 0-未打印 1-已打印',
  `photo_status` int(3) DEFAULT NULL COMMENT '拍照状态 0-未拍照 1-已拍照',
  `fixed_time` timestamp NULL DEFAULT NULL COMMENT '固定时间',
  `fixative_type` int(3) DEFAULT NULL COMMENT '固定液类型 存放字典值ID',
  `sample_weight` decimal(5,2) DEFAULT NULL COMMENT '样品重量',
  `fixative_volume` decimal(5,2) DEFAULT NULL COMMENT '固定液体积',
  `transfer_container` decimal(5,2) DEFAULT NULL COMMENT '转运容器',
  `reject_reason` varchar(100) DEFAULT NULL COMMENT '拒收原因',
  `create_at` timestamp NULL DEFAULT NULL COMMENT '创建人',
  `create_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `operation_site_operator` varchar(45) DEFAULT NULL COMMENT '手术站点操作人',
  `operation_site_operate_time` timestamp NULL DEFAULT NULL COMMENT '手术站点操作时间',
  `fix_by` varchar(45) DEFAULT NULL COMMENT '固定人',
  `fix_at` timestamp NULL DEFAULT NULL COMMENT '固定时间',
  `accept_by` varchar(45) DEFAULT NULL COMMENT '接收人',
  `accept_at` timestamp NULL DEFAULT NULL COMMENT '接受时间',
  `revoke_reason` varchar(200) DEFAULT NULL COMMENT '作废原因',
  `delivery_site_operator` varchar(45) DEFAULT NULL COMMENT '运送站点操作人',
  `delivery_site_operate_time` timestamp NULL DEFAULT NULL COMMENT '运行站点操作时间',
  `source` int(3) DEFAULT NULL COMMENT '样本来源 1-门诊站点 2-手术站点 3-预约站点',
  `lastest_site_id` bigint(20) DEFAULT NULL COMMENT '样本最后一次操作的站点ID 门诊站点/手术站点/预约站点/固定站点/转运站点/接收站点',
  `new_sample_no` varchar(100) DEFAULT NULL COMMENT '新样本编号-根据病理号重新生成的样本编号',
  `flow_status` int(4) DEFAULT '1' COMMENT '流程状态 1-待固定 2-待转运 3-待接收 4-已接收',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `sample_no_UNIQUE` (`sample_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='样本信息';

-- ----------------------------
-- Records of t_sample
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sample_operate_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_sample_operate_record`;
CREATE TABLE `t_sample_operate_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `sample_id` bigint(11) DEFAULT NULL COMMENT '样本主键',
  `sample_no` varchar(50) DEFAULT NULL,
  `operate_type` int(5) DEFAULT NULL COMMENT '操作类型 0-新建 1-修改 2-作废 3-固定 4-接收 5-退回 6-转运',
  `operate_desc` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `site_id` bigint(11) DEFAULT NULL COMMENT '操作站点ID（在哪个站点操作的）',
  `batch_no` varchar(100) DEFAULT '0' COMMENT '批次号（针对批量操作）',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='样本操作记录表';

-- ----------------------------
-- Records of t_sample_operate_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_site`
-- ----------------------------
DROP TABLE IF EXISTS `t_site`;
CREATE TABLE `t_site` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(100) DEFAULT NULL COMMENT '站点名称',
  `site_type` int(5) DEFAULT NULL COMMENT '站点类型 1-手术站点 2-预约站点 3-门诊站点 4-固定站点 5-运送站点 6-接收站点',
  `site_desc` varchar(100) DEFAULT NULL COMMENT '站点描述',
  `ip` varchar(100) DEFAULT NULL COMMENT '站点IP地址',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站点表';

-- ----------------------------
-- Records of t_site
-- ----------------------------

-- ----------------------------
-- Table structure for `t_site_map`
-- ----------------------------
DROP TABLE IF EXISTS `t_site_map`;
CREATE TABLE `t_site_map` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) DEFAULT NULL COMMENT '站点id',
  `to_site_id` bigint(20) DEFAULT NULL COMMENT '被映射的站点ID',
  `to_site_type` int(3) DEFAULT NULL COMMENT '被映射站点类型 1-门诊站点 2-手术站点 3-固定的站点 4-运送站点 5-接收站点 6-预约站点',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='站点映射关系';

-- ----------------------------
-- Records of t_site_map
-- ----------------------------

-- ----------------------------
-- Table structure for `t_third_request_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_third_request_log`;
CREATE TABLE `t_third_request_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `third_name` varchar(100) DEFAULT NULL COMMENT '第三方名称 HIS、手麻系统等',
  `request_url` varchar(200) DEFAULT NULL,
  `request_content` varchar(3000) DEFAULT NULL,
  `response_content` varchar(3000) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方接口请求日志表';

-- ----------------------------
-- Records of t_third_request_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_transfer_relation`
-- ----------------------------
DROP TABLE IF EXISTS `t_transfer_relation`;
CREATE TABLE `t_transfer_relation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `transfer_no` varchar(45) DEFAULT NULL COMMENT '转运箱编号',
  `sample_id` bigint(11) DEFAULT NULL COMMENT '样本ID',
  `sample_no` varchar(50) DEFAULT NULL,
  `transfer_status` int(5) DEFAULT '0' COMMENT '转运状态 0-正常 1-已转箱',
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='转运箱和样本关系表';

-- ----------------------------
-- Records of t_transfer_relation
-- ----------------------------

-- ----------------------------
-- Function structure for `seq_currval`
-- ----------------------------
DROP FUNCTION IF EXISTS `seq_currval`;
DELIMITER ;;
CREATE DEFINER=`leica`@`%` FUNCTION `seq_currval`(seq_name VARCHAR(50)) RETURNS bigint(20)
BEGIN
         DECLARE value BIGINT;
         SELECT current_value INTO value
         FROM comm_sequence
         WHERE upper(name) = upper(seq_name);
         RETURN value;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `seq_nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `seq_nextval`;
DELIMITER ;;
CREATE DEFINER=`leica`@`%` FUNCTION `seq_nextval`(seq_name VARCHAR(50)) RETURNS bigint(20)
BEGIN  
         DECLARE value BIGINT;
         UPDATE comm_sequence  
         SET current_value = current_value + increment  
         WHERE upper(name) = upper(seq_name);
         RETURN seq_currval(seq_name);  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `seq_setval`
-- ----------------------------
DROP FUNCTION IF EXISTS `seq_setval`;
DELIMITER ;;
CREATE DEFINER=`leica`@`%` FUNCTION `seq_setval`(seq_name VARCHAR(50), value BIGINT) RETURNS bigint(20)
BEGIN 
         UPDATE comm_sequence  
         SET current_value = value  
         WHERE upper(name) = upper(seq_name);  
         RETURN seq_currval(seq_name);  
END
;;
DELIMITER ;
