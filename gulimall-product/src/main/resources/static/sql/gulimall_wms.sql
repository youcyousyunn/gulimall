-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: gulimall_wms
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

--
-- Table structure for table `wms_purchase`
--

DROP TABLE IF EXISTS `wms_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '采购单id',
  `assignee_id` bigint(20) DEFAULT NULL COMMENT '采购人id',
  `assignee_name` varchar(255) DEFAULT NULL COMMENT '采购人名',
  `phone` char(13) DEFAULT NULL COMMENT '联系方式',
  `priority` int(4) DEFAULT NULL COMMENT '优先级',
  `status` int(4) DEFAULT NULL COMMENT '状态',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `amount` decimal(18,4) DEFAULT NULL COMMENT '总金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_purchase`
--

LOCK TABLES `wms_purchase` WRITE;
/*!40000 ALTER TABLE `wms_purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_purchase_detail`
--

DROP TABLE IF EXISTS `wms_purchase_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_purchase_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int(11) DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `status` int(11) DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_purchase_detail`
--

LOCK TABLES `wms_purchase_detail` WRITE;
/*!40000 ALTER TABLE `wms_purchase_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_purchase_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware`
--

DROP TABLE IF EXISTS `wms_ware`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_ware` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='仓库信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware`
--

LOCK TABLES `wms_ware` WRITE;
/*!40000 ALTER TABLE `wms_ware` DISABLE KEYS */;
INSERT INTO `wms_ware` VALUES (1,'北京仓','昌平区宏福科技园综合楼5层','200'),(2,'上海仓','松江区大江商厦6层','100'),(3,'深圳仓','宝安区西部硅谷大厦B座','300');
/*!40000 ALTER TABLE `wms_ware` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_info`
--

DROP TABLE IF EXISTS `wms_ware_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_ware_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_info`
--

LOCK TABLES `wms_ware_info` WRITE;
/*!40000 ALTER TABLE `wms_ware_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_ware_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_order_bill`
--

DROP TABLE IF EXISTS `wms_ware_order_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_ware_order_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint(2) DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存工作单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_order_bill`
--

LOCK TABLES `wms_ware_order_bill` WRITE;
/*!40000 ALTER TABLE `wms_ware_order_bill` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_ware_order_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_order_bill_detail`
--

DROP TABLE IF EXISTS `wms_ware_order_bill_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_ware_order_bill_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint(20) DEFAULT NULL COMMENT '工作单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存工作单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_order_bill_detail`
--

LOCK TABLES `wms_ware_order_bill_detail` WRITE;
/*!40000 ALTER TABLE `wms_ware_order_bill_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_ware_order_bill_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_order_task`
--

DROP TABLE IF EXISTS `wms_ware_order_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_ware_order_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint(2) DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='库存工作单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_order_task`
--

LOCK TABLES `wms_ware_order_task` WRITE;
/*!40000 ALTER TABLE `wms_ware_order_task` DISABLE KEYS */;
INSERT INTO `wms_ware_order_task` VALUES (21,NULL,'202011012028556211322878284702941186',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-11-01 20:29:02',NULL,NULL),(22,NULL,'202011012050229851322883684307304449',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-11-01 20:50:38',NULL,NULL),(23,NULL,'202011012111385351322889034356015105',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-11-01 21:11:42',NULL,NULL);
/*!40000 ALTER TABLE `wms_ware_order_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_order_task_detail`
--

DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_ware_order_task_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint(20) DEFAULT NULL COMMENT '工作单id',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `lock_status` int(1) DEFAULT NULL COMMENT '1-已锁定  2-已解锁  3-扣减',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='库存工作单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_order_task_detail`
--

LOCK TABLES `wms_ware_order_task_detail` WRITE;
/*!40000 ALTER TABLE `wms_ware_order_task_detail` DISABLE KEYS */;
INSERT INTO `wms_ware_order_task_detail` VALUES (21,1,'',10,21,1,1),(22,1,'',10,22,1,2),(23,1,'',10,23,1,2),(24,4,'',5,23,2,2);
/*!40000 ALTER TABLE `wms_ware_order_task_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_sku`
--

DROP TABLE IF EXISTS `wms_ware_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wms_ware_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint(20) DEFAULT NULL COMMENT '仓库id',
  `stock` int(11) DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int(11) DEFAULT NULL COMMENT '锁定库存',
  `sales` bigint(20) DEFAULT NULL COMMENT '销量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='商品库存';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_sku`
--

LOCK TABLES `wms_ware_sku` WRITE;
/*!40000 ALTER TABLE `wms_ware_sku` DISABLE KEYS */;
INSERT INTO `wms_ware_sku` VALUES (1,1,1,100,'华为 HUAWEI Mate 30 Pro 星河银 8GB+256GB',0,0),(2,2,2,200,'华为 HUAWEI Mate 30 Pro 星河银 8GB+128GB',0,0),(3,3,1,100,'华为 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB',0,0),(4,4,2,200,'华为 HUAWEI Mate 30 Pro 亮黑色 8GB+128GB',0,0),(5,5,1,100,'华为 HUAWEI Mate 30 Pro 翡冷翠 8GB+256GB',0,0),(6,6,2,200,'华为 HUAWEI Mate 30 Pro 翡冷翠 8GB+128GB',0,0),(7,7,1,100,'华为 HUAWEI Mate 30 Pro 罗兰紫 8GB+256GB',0,0),(8,8,2,200,'华为 HUAWEI Mate 30 Pro 罗兰紫 8GB+128GB',0,0),(9,9,1,100,' Apple iPhone 11 (A2223)  黑色 128GB ',0,0),(10,10,2,200,' Apple iPhone 11 (A2223)  黑色 256GB',0,0);
/*!40000 ALTER TABLE `wms_ware_sku` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-10 11:32:57
