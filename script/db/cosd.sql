/*
 Navicat Premium Data Transfer

 Source Server         : cosd-3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.2.150:3306
 Source Schema         : cosd

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 13/05/2023 11:33:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for asset
-- ----------------------------
DROP TABLE IF EXISTS `asset`;
CREATE TABLE `asset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资产主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户主键id',
  `asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型 0-USDT；1-COSD；2-NFT；3-EVIC',
  `asset_status` tinyint(255) DEFAULT NULL COMMENT '资产状态：0-盲盒NFT已购买；1-盲盒NFT已使用',
  `asset_attrs` text COLLATE utf8_bin COMMENT '资产属性：NFT的基本属性，数据以json字符串存放',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for transaction
-- ----------------------------
DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易id 主键',
  `txid` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易id',
  `trans_type` tinyint(2) DEFAULT NULL COMMENT 'trans_type：0-用户使用 USDT 购买 COSD、1-用户质押COSD 到 DEFI、2-用户质押 COSD到星光、3-用户提现 COSD、4-用户使用USDT 购买 EVIC、5-用户提现EVIC、6-用户使用USDT 购买 NFT 盲盒、7-NFT交易\n',
  `from_user_id` int(11) DEFAULT NULL COMMENT '用户主键id',
  `from_asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型 0-USDT；1-COSD；2-NFT；3-EVIC',
  `from_amout` decimal(15,5) DEFAULT NULL COMMENT '用户用 100 USDT 购买 COSD，from_amout 就是100',
  `to_asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型 0-USDT；1-COSD；2-NFT；3-EVIC',
  `to_user_id` int(11) DEFAULT NULL COMMENT '用户主键id',
  `to_amout` decimal(15,5) DEFAULT NULL COMMENT '用户用 100 USDT 购买 COSD，COSD 单价 0.05USDT，to_amout 就是2000\n\n\n',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `wallet_address` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户钱包地址',
  `email` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户邮箱',
  `passwd` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `user_type` tinyint(2) NOT NULL COMMENT '用户类型：0-渠道商；1-俱乐部老板；2-普通用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_relation`;
CREATE TABLE `user_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level0` bigint(20) DEFAULT NULL COMMENT '用户主键id，level0 代表渠道商',
  `level1` bigint(20) DEFAULT NULL COMMENT '用户主键id，level1 代表俱乐部老板',
  `level2` bigint(20) DEFAULT NULL COMMENT '用户主键id，level2 代表普通用户',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
