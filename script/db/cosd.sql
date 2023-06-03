/*
 Navicat Premium Data Transfer

 Source Server         : cosd-3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 127.0.0.1:3306
 Source Schema         : cosd

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 03/06/2023 23:13:30
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
  `asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型1-EVIC; 2-SL;3-COSD',
  `asset_status` tinyint(255) DEFAULT NULL COMMENT '资产状态： 0-用户不具有玩星光的资格，1-用户具有玩星光的资格',
  `amount` decimal(25,0) DEFAULT NULL COMMENT '资产数量',
  `asset_attrs` text COLLATE utf8_bin COMMENT '资产属性：保留字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for nft
-- ----------------------------
DROP TABLE IF EXISTS `nft`;
CREATE TABLE `nft` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'NFT 主键id',
  `txid` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '交易id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户主键id',
  `token_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '链上token id',
  `game_type` tinyint(255) DEFAULT NULL COMMENT '游戏类型：0-自走棋',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0-已购买；1-已使用；2-已失效',
  `attr1` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '属性1，编号',
  `attr2` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '属性2，次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `upchain_time` bigint(20) DEFAULT NULL COMMENT '上链时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for pool
-- ----------------------------
DROP TABLE IF EXISTS `pool`;
CREATE TABLE `pool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '质押池 类型：1-defi pool,2-sl pool,3-club',
  `term` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '标注池子是第几期',
  `start_time` bigint(20) DEFAULT NULL COMMENT '池子开始时间',
  `lock_time` bigint(20) DEFAULT NULL COMMENT '池子的锁仓时间/秒',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for pool_user
-- ----------------------------
DROP TABLE IF EXISTS `pool_user`;
CREATE TABLE `pool_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户主键id',
  `pool_id` bigint(20) DEFAULT NULL COMMENT '池子主键id',
  `amount` decimal(15,5) DEFAULT NULL COMMENT '数量，可以是负数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间\n',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for trans_game
-- ----------------------------
DROP TABLE IF EXISTS `trans_game`;
CREATE TABLE `trans_game` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for trans_website
-- ----------------------------
DROP TABLE IF EXISTS `trans_website`;
CREATE TABLE `trans_website` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易id 主键',
  `txid` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '交易id',
  `trans_type` tinyint(2) DEFAULT NULL COMMENT '0-用户使用 USDT 购买 COSD、1-用户质押COSD 到 DEFI、2-用户质押 COSD到星光、3-质押 COSD 到俱乐部老板质押池、4-用户从 defi 提现 COSD、5-用户从 星光池中 提现 COSD、6-用户从 俱乐部老板质押池中 提现 COSD、7-用户使用USDT 购买 EVIC、8-用户提现EVIC、9-用户使用USDT 购买 NFT 盲盒、10-NFT交易\n',
  `from_user_id` int(11) DEFAULT NULL COMMENT '用户主键id',
  `from_asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型 0-USDT；1-COSD；2-NFT；3-EVIC；4-SL',
  `from_amount` decimal(15,5) DEFAULT NULL COMMENT '用户用 100 USDT 购买 COSD，from_amout 就是100',
  `to_asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型 0-USDT；1-COSD；2-NFT；3-EVIC；4-SL',
  `to_user_id` int(11) DEFAULT NULL COMMENT '用户主键id',
  `to_amount` decimal(15,5) DEFAULT NULL COMMENT '用户用 100 USDT 购买 COSD，COSD 单价 0.05USDT，to_amout 就是2000\n\n\n',
  `nft_token_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'nft 的token id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '状态：0-交易已上链；1-success；2-fail；3-用户取消',
  `upchain_time` bigint(20) DEFAULT NULL COMMENT '上链时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `wallet_address` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户钱包地址',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户邮箱',
  `passwd` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `user_type` tinyint(2) DEFAULT NULL COMMENT '用户类型：0-渠道商；1-俱乐部老板；2-普通用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_relation_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户关系表主键id',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_relation`;
CREATE TABLE `user_relation` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `level0` bigint(20) DEFAULT NULL COMMENT '用户主键id，level0 代表渠道商',
  `level1` bigint(20) DEFAULT NULL COMMENT '用户主键id，level1 代表俱乐部老板',
  `level2` bigint(20) DEFAULT NULL COMMENT '用户主键id，level2 代表普通用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
