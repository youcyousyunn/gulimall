-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: gulimall_ums
-- ------------------------------------------------------
-- Server version	5.7.31-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ums_growth_change_history`
--

DROP TABLE IF EXISTS `ums_growth_change_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_growth_change_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `change_count` int(11) DEFAULT NULL COMMENT '改变的值（正负计数）',
  `note` varchar(0) DEFAULT NULL COMMENT '备注',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '积分来源[0-购物，1-管理员修改]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长值变化历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_growth_change_history`
--

LOCK TABLES `ums_growth_change_history` WRITE;
/*!40000 ALTER TABLE `ums_growth_change_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_growth_change_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_growth_history`
--

DROP TABLE IF EXISTS `ums_growth_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_growth_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `count` int(11) DEFAULT NULL COMMENT '变动数量',
  `remark` varchar(0) DEFAULT NULL COMMENT '备注',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '来源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成长积分记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_growth_history`
--

LOCK TABLES `ums_growth_history` WRITE;
/*!40000 ALTER TABLE `ums_growth_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_growth_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_integration_change_history`
--

DROP TABLE IF EXISTS `ums_integration_change_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_integration_change_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `change_count` int(11) DEFAULT NULL COMMENT '变化的值',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `source_tyoe` tinyint(4) DEFAULT NULL COMMENT '来源[0->购物；1->管理员修改;2->活动]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分变化历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_integration_change_history`
--

LOCK TABLES `ums_integration_change_history` WRITE;
/*!40000 ALTER TABLE `ums_integration_change_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_integration_change_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_integration_history`
--

DROP TABLE IF EXISTS `ums_integration_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_integration_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `count` int(11) DEFAULT NULL COMMENT '变动数量',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '来源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物积分记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_integration_history`
--

LOCK TABLES `ums_integration_history` WRITE;
/*!40000 ALTER TABLE `ums_integration_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_integration_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member`
--

DROP TABLE IF EXISTS `ums_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level_id` bigint(20) DEFAULT NULL COMMENT '会员等级id',
  `username` char(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `header` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '生日',
  `city` varchar(500) DEFAULT NULL COMMENT '所在城市',
  `job` varchar(255) DEFAULT NULL COMMENT '职业',
  `sign` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '用户来源',
  `integration` int(11) DEFAULT NULL COMMENT '积分',
  `growth` int(11) DEFAULT NULL COMMENT '成长值',
  `status` tinyint(4) DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `social_uid` varchar(255) DEFAULT NULL COMMENT '社交用户的唯一id',
  `access_token` varchar(255) DEFAULT NULL COMMENT '访问令牌',
  `expires_in` varchar(255) DEFAULT NULL COMMENT '访问令牌的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='会员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member`
--

LOCK TABLES `ums_member` WRITE;
/*!40000 ALTER TABLE `ums_member` DISABLE KEYS */;
INSERT INTO `ums_member` VALUES (1,1,'太阳晒屁股了','$2a$10$MeRi/kWlTS2eCjrhisrCxesXoU5vwsw0QVcx/irTeCZUhCzmi2yTi','太阳晒屁股了','13041391048',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-07 18:45:15',NULL,NULL,'0'),(2,NULL,NULL,NULL,'codporter',NULL,NULL,'https://tvax3.sinaimg.cn/crop.0.0.1006.1006.50/006Itfnyly8gcqf9hx83aj30ry0ryq73.jpg?KID=imgbed,tva&Expires=1602139017&ssig=HNAz73wRmM',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-10-08 11:36:57','6153926408','2.00Y4PTiGzjrAJBf70c772e47P_gjuD','157679999');
/*!40000 ALTER TABLE `ums_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member_collect_spu`
--

DROP TABLE IF EXISTS `ums_member_collect_spu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member_collect_spu` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(500) DEFAULT NULL COMMENT 'spu_name',
  `spu_img` varchar(500) DEFAULT NULL COMMENT 'spu_img',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员收藏的商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member_collect_spu`
--

LOCK TABLES `ums_member_collect_spu` WRITE;
/*!40000 ALTER TABLE `ums_member_collect_spu` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_member_collect_spu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member_collect_subject`
--

DROP TABLE IF EXISTS `ums_member_collect_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member_collect_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `subject_id` bigint(20) DEFAULT NULL COMMENT 'subject_id',
  `subject_name` varchar(255) DEFAULT NULL COMMENT 'subject_name',
  `subject_img` varchar(500) DEFAULT NULL COMMENT 'subject_img',
  `subject_urll` varchar(500) DEFAULT NULL COMMENT '活动url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员收藏的专题活动';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member_collect_subject`
--

LOCK TABLES `ums_member_collect_subject` WRITE;
/*!40000 ALTER TABLE `ums_member_collect_subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_member_collect_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member_level`
--

DROP TABLE IF EXISTS `ums_member_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT '等级名称',
  `growth_point` int(11) DEFAULT NULL COMMENT '等级需要的成长值',
  `default_status` tinyint(4) DEFAULT NULL COMMENT '是否为默认等级[0->不是；1->是]',
  `free_freight_point` decimal(18,4) DEFAULT NULL COMMENT '免运费标准',
  `comment_growth_point` int(11) DEFAULT NULL COMMENT '每次评价获取的成长值',
  `priviledge_free_freight` tinyint(4) DEFAULT NULL COMMENT '是否有免邮特权',
  `priviledge_member_price` tinyint(4) DEFAULT NULL COMMENT '是否有会员价格特权',
  `priviledge_birthday` tinyint(4) DEFAULT NULL COMMENT '是否有生日特权',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='会员等级';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member_level`
--

LOCK TABLES `ums_member_level` WRITE;
/*!40000 ALTER TABLE `ums_member_level` DISABLE KEYS */;
INSERT INTO `ums_member_level` VALUES (1,'青铜会员',1,1,50.0000,10,0,0,1,'初级会员');
/*!40000 ALTER TABLE `ums_member_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member_login_log`
--

DROP TABLE IF EXISTS `ums_member_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(64) DEFAULT NULL COMMENT 'ip',
  `city` varchar(64) DEFAULT NULL COMMENT 'city',
  `login_type` tinyint(1) DEFAULT NULL COMMENT '登录类型[1-web，2-app]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员登录记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member_login_log`
--

LOCK TABLES `ums_member_login_log` WRITE;
/*!40000 ALTER TABLE `ums_member_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_member_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member_receive_address`
--

DROP TABLE IF EXISTS `ums_member_receive_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member_receive_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(64) DEFAULT NULL COMMENT '电话',
  `post_code` varchar(64) DEFAULT NULL COMMENT '邮政编码',
  `province` varchar(100) DEFAULT NULL COMMENT '省份/直辖市',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `region` varchar(100) DEFAULT NULL COMMENT '区',
  `detail_address` varchar(255) DEFAULT NULL COMMENT '详细地址(街道)',
  `areacode` varchar(15) DEFAULT NULL COMMENT '省市区代码',
  `default_status` tinyint(1) DEFAULT NULL COMMENT '是否默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='会员收货地址';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member_receive_address`
--

LOCK TABLES `ums_member_receive_address` WRITE;
/*!40000 ALTER TABLE `ums_member_receive_address` DISABLE KEYS */;
INSERT INTO `ums_member_receive_address` VALUES (1,2,'阳光','13041391046','42200','湖南省','长沙市','雨花区','高桥小区C98栋2单元',NULL,1);
/*!40000 ALTER TABLE `ums_member_receive_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_member_statistics_info`
--

DROP TABLE IF EXISTS `ums_member_statistics_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_member_statistics_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `consume_amount` decimal(18,4) DEFAULT NULL COMMENT '累计消费金额',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '累计优惠金额',
  `order_count` int(11) DEFAULT NULL COMMENT '订单数量',
  `coupon_count` int(11) DEFAULT NULL COMMENT '优惠券数量',
  `comment_count` int(11) DEFAULT NULL COMMENT '评价数',
  `return_order_count` int(11) DEFAULT NULL COMMENT '退货数量',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `attend_count` int(11) DEFAULT NULL COMMENT '关注数量',
  `fans_count` int(11) DEFAULT NULL COMMENT '粉丝数量',
  `collect_product_count` int(11) DEFAULT NULL COMMENT '收藏的商品数量',
  `collect_subject_count` int(11) DEFAULT NULL COMMENT '收藏的专题活动数量',
  `collect_comment_count` int(11) DEFAULT NULL COMMENT '收藏的评论数量',
  `invite_friend_count` int(11) DEFAULT NULL COMMENT '邀请的朋友数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员统计信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_member_statistics_info`
--

LOCK TABLES `ums_member_statistics_info` WRITE;
/*!40000 ALTER TABLE `ums_member_statistics_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_member_statistics_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user`
--

DROP TABLE IF EXISTS `ums_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level_id` bigint(20) DEFAULT NULL COMMENT '��Ա�ȼ�id',
  `username` char(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) DEFAULT NULL COMMENT '盐',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `header` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(500) DEFAULT NULL COMMENT '城市',
  `job` varchar(255) DEFAULT NULL COMMENT '职业',
  `sign` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '来源',
  `integration` int(11) DEFAULT NULL COMMENT '购物积分',
  `growth` int(11) DEFAULT NULL COMMENT '赠送积分',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user`
--

LOCK TABLES `ums_user` WRITE;
/*!40000 ALTER TABLE `ums_user` DISABLE KEYS */;
INSERT INTO `ums_user` VALUES (1,1,'liuyan','123456',NULL,'柳岩','13888888888','88@qq.com',NULL,1,'2020-04-05','上海','服务员',NULL,1,1000,2000,1,'2020-04-05 20:03:25'),(2,1,'xiaolu','c2be06f23d08fa4d7a5ef214887ab3c4','74a85273dfec463386411f35647ee948','无名氏','18666666666','66@qq.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,2000,2000,1,'2020-04-05 20:24:08');
/*!40000 ALTER TABLE `ums_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user_address`
--

DROP TABLE IF EXISTS `ums_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `name` varchar(255) DEFAULT NULL COMMENT '收货人',
  `phone` varchar(64) DEFAULT NULL COMMENT '电话',
  `post_code` varchar(64) DEFAULT NULL COMMENT '右边',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `region` varchar(100) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `default_status` tinyint(1) DEFAULT NULL COMMENT '是否默认地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='收货地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user_address`
--

LOCK TABLES `ums_user_address` WRITE;
/*!40000 ALTER TABLE `ums_user_address` DISABLE KEYS */;
INSERT INTO `ums_user_address` VALUES (1,2,'柳岩','13812345678','200122','上海','上海市','松江区','大江商厦6层',1),(2,2,'锋哥','18612345678','200112','北京','北京市','昌平区','宏福科技园',0);
/*!40000 ALTER TABLE `ums_user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user_collect_shop`
--

DROP TABLE IF EXISTS `ums_user_collect_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user_collect_shop` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `shop_name` varchar(50) DEFAULT NULL COMMENT '店铺名',
  `shop_logo` varchar(100) DEFAULT NULL COMMENT '店铺logo',
  `createTime` datetime DEFAULT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注店铺表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user_collect_shop`
--

LOCK TABLES `ums_user_collect_shop` WRITE;
/*!40000 ALTER TABLE `ums_user_collect_shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_user_collect_shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user_collect_sku`
--

DROP TABLE IF EXISTS `ums_user_collect_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user_collect_sku` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `sku_title` varchar(500) DEFAULT NULL COMMENT 'sku标题',
  `sku_image` varchar(500) DEFAULT NULL COMMENT 'sku默认图片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user_collect_sku`
--

LOCK TABLES `ums_user_collect_sku` WRITE;
/*!40000 ALTER TABLE `ums_user_collect_sku` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_user_collect_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user_collect_subject`
--

DROP TABLE IF EXISTS `ums_user_collect_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user_collect_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `subject_id` bigint(20) DEFAULT NULL COMMENT '活动id',
  `subject_name` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `subject_image` varchar(500) DEFAULT NULL COMMENT '活动默认图片',
  `subject_url` varchar(500) DEFAULT NULL COMMENT '活动链接',
  `create_time` datetime DEFAULT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注活动表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user_collect_subject`
--

LOCK TABLES `ums_user_collect_subject` WRITE;
/*!40000 ALTER TABLE `ums_user_collect_subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_user_collect_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user_level`
--

DROP TABLE IF EXISTS `ums_user_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT '等级名称',
  `growth_point` int(11) DEFAULT NULL COMMENT '等级需要的成长值',
  `default_status` tinyint(4) DEFAULT NULL COMMENT '默认等级',
  `free_freight_point` decimal(18,4) DEFAULT NULL COMMENT '免运费标准',
  `comment_growth_point` int(11) DEFAULT NULL COMMENT '每次评价获取的成长值',
  `priviledge_free_freight` tinyint(4) DEFAULT NULL COMMENT '是否有免邮特权',
  `priviledge_member_price` tinyint(4) DEFAULT NULL COMMENT '是否有会员价格特权',
  `priviledge_birthday` tinyint(4) DEFAULT NULL COMMENT '是否有生日特权',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员等级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user_level`
--

LOCK TABLES `ums_user_level` WRITE;
/*!40000 ALTER TABLE `ums_user_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_user_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user_login_log`
--

DROP TABLE IF EXISTS `ums_user_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '登陆时间',
  `ip` varchar(64) DEFAULT NULL COMMENT '登录ip',
  `city` varchar(64) DEFAULT NULL COMMENT '登录城市',
  `type` tinyint(1) DEFAULT NULL COMMENT '登录类型【0-web，1-移动】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登陆记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user_login_log`
--

LOCK TABLES `ums_user_login_log` WRITE;
/*!40000 ALTER TABLE `ums_user_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_user_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ums_user_statistics`
--

DROP TABLE IF EXISTS `ums_user_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `consume_amount` decimal(18,4) DEFAULT NULL COMMENT '累计消费金额',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '累计优惠金额',
  `order_count` int(11) DEFAULT NULL COMMENT '订单数量',
  `coupon_count` int(11) DEFAULT NULL COMMENT '优惠券数量',
  `comment_count` int(11) DEFAULT NULL COMMENT '评价数',
  `return_order_count` int(11) DEFAULT NULL COMMENT '退货数量',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `attend_count` int(11) DEFAULT NULL COMMENT '关注数量',
  `fans_count` int(11) DEFAULT NULL COMMENT '粉丝数量',
  `collect_product_count` int(11) DEFAULT NULL COMMENT '收藏的商品数量',
  `collect_subject_count` int(11) DEFAULT NULL COMMENT '收藏的专题活动数量',
  `collect_comment_count` int(11) DEFAULT NULL COMMENT '收藏的评论数量',
  `invite_friend_count` int(11) DEFAULT NULL COMMENT '邀请的朋友数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统计信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user_statistics`
--

LOCK TABLES `ums_user_statistics` WRITE;
/*!40000 ALTER TABLE `ums_user_statistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `ums_user_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-10 11:32:24
