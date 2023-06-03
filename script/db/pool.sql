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

 Date: 03/06/2023 23:15:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Records of pool
-- ----------------------------
BEGIN;
INSERT INTO `pool` VALUES (1, '1', '1', 1685603888, 3600000, 'defi');
INSERT INTO `pool` VALUES (2, '2', '1', 1684841716, NULL, '星光');
INSERT INTO `pool` VALUES (3, '3', '1', 1684852716, 3600000, '俱乐部');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
