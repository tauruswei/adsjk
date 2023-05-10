CREATE DATABASE IF NOT EXISTS nacos_config DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use nacos_config;
/*
 Navicat Premium Data Transfer

 Source Server         : privacy
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.85.131:3306
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 14/10/2022 16:53:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
FLUSH PRIVILEGES;
-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`) VALUES (101, 'database.yaml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    sql-script-encoding: utf-8\n    druid:\n      primary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: cosd\n        url: jdbc:mysql://192.168.2.150:3306/cosd?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: cosd@123.com\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n      secondary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: platform\n        url: jdbc:mysql://192.168.2.150:3306/privacy?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: platform@123\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n      resourcePrimary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: platform\n        url: jdbc:mysql://192.168.2.150:3306/resource?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: platform@123\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n      resourceSecondary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: platform\n        url: jdbc:mysql://192.168.2.150:3306/resource?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: platform@123\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n', 'bc1fbb8795a977bcbe1f3ca854d8941d', '2022-06-16 09:01:01', '2022-06-17 09:05:06', 'nacos', '10.244.0.0', '', '46b6b568-e6ae-45ca-baa1-819932fc8947', '', '', '', 'yaml', '');
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`) VALUES (102, 'base.json', 'DEFAULT_GROUP', '{\n    \"tokenValidateUriBlackList\":[\n      \"/user/login\",\n      \"/user/register\",\n      \"/user/sendVerificationCode\",\n      \"/user/forgetPassword\",\n      \"/captcha/get\",\n      \"/captcha/check\",\n      \"/oauth/getAuthList\",\n      \"/oauth/authLogin\",\n      \"/oauth/authRegister\",\n      \"/oauth/github/render\",\n      \"/oauth/github/callback\",\n      \"/common/getValidatePublicKey\",\n      \"/common/getTrackingID\",\n      \"/shareData/syncProject\",\n      \"/shareData/syncModel\",\n      \"/organ/getHomepage\"\n    ],\n    \"needSignUriList\":[\n\n    ],\n    \"authConfigs\":{\n        \"github\": {\n            \"redirectUrl\":\"\",\n            \"authEnable\": 1,\n            \"clientId\": \"\",\n            \"clientSecret\": \"\",\n            \"redirectUri\": \"\"\n        }\n    },\n    \"lokiConfig\": {\n        \"address\":\"172.28.1.30:3100\",\n        \"job\":\"false\",\n        \"container\":\"node0\"\n    },\n    \"adminUserIds\": [1],\n    \"defaultPassword\":\"123456\",\n    \"defaultPasswordVector\":\"excalibur\",\n    \"OfficalService\": \"\",\n    \"grpcClient\":{\n        \"grpcClientAddress\":\"192.168.2.150\",\n        \"grpcClientPort\":50050,\n        \"grpcServerPorts\":[6666],\n        \"useTls\":false,\n        \"trustCertFilePath\":\"/data/javaclientcert/ca.crt\",\n        \"keyCertChainFile\":\"/data/javaclientcert/client.crt\",\n        \"keyFile\":\"/data/javaclientcert/client8.key\"\n    },\n    \"grpcServerPort\": 9090,\n    \"uploadUrlDirPrefix\": \"/tmp/data/upload/\",\n    \"resultUrlDirPrefix\": \"/tmp/data/result/\",\n    \"runModelFileUrlDirPrefix\": \"/tmp/data/result/runModel/\",\n    \"usefulToken\": \"excalibur_forever_ABCDEFGHIJKLMN\",\n    \"systemDomainName\":\"\",\n    \"mailProperties\":{},\n  \"model_components\": [\n        {\n            \"component_code\": \"start\",\n            \"component_name\": \"开始\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"taskName\",\n                    \"type_name\": \"任务名称\",\n                    \"input_type\": \"text\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"taskDesc\",\n                    \"type_name\": \"任务描述\",\n                    \"input_type\": \"textarea\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },\n        {\n            \"component_code\": \"dataSet\",\n            \"component_name\": \"选择数据集\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },  \n        {\n            \"component_code\": \"localData\",\n            \"component_name\": \"选择数据集\",\n            \"sub_component_name\": \"本地数据\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },  \n        {\n            \"component_code\": \"fusionData\",\n            \"component_name\": \"选择数据集\",\n            \"sub_component_name\": \"联邦数据\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },  \n        {\n            \"component_code\": \"federalData\",\n            \"component_name\": \"选择数据集\",\n            \"sub_component_name\": \"数据融合\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },\n        {\n            \"component_code\": \"dataAlign\",\n            \"component_name\": \"数据对齐\",\n            \"is_show\": 0,\n            \"is_mandatory\": 1,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"dataAlign\",\n                    \"type_name\": \"数据对齐\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"样本对齐\"\n                        },\n                        {\n                            \"key\": \"2\",\n                            \"val\": \"特征对齐\"\n                        }\n                    ]\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"MultipleSelected\",\n                    \"type_name\": \"可多选特征\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"encryption\",\n                    \"type_name\": \"加密方式\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"1\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"MD5\"\n                        }\n                    ]\n                }\n            ]\n        },\n        {\n            \"component_code\": \"exception\",\n            \"component_name\": \"异常值处理\",\n            \"is_show\": 0,\n            \"is_mandatory\": 1,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"exception\",\n                    \"type_name\": \"异常值处理\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"1\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"异常值处理\"\n                        }\n                    ]\n                }\n            ]\n        },\n        {\n            \"component_code\": \"featureCoding\",\n            \"component_name\": \"特征编码\",\n            \"is_show\": 0,\n            \"is_mandatory\": 1,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"featureCoding\",\n                    \"type_name\": \"特征编码\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"标签编码\"\n                        },\n                        {\n                            \"key\": \"2\",\n                            \"val\": \"哈希编码\"\n                        },\n                        {\n                            \"key\": \"3\",\n                            \"val\": \"独热编码\"\n                        },\n                        {\n                            \"key\": \"4\",\n                            \"val\": \"计数编码\"\n                        },\n                        {\n                            \"key\": \"5\",\n                            \"val\": \"直方图编码\"\n                        },\n                        {\n                            \"key\": \"6\",\n                            \"val\": \"WOE编码\"\n                        },\n                        {\n                            \"key\": \"7\",\n                            \"val\": \"目标编码\"\n                        },\n                        {\n                            \"key\": \"8\",\n                            \"val\": \"平均编码\"\n                        },\n                        {\n                            \"key\": \"9\",\n                            \"val\": \"模型编码\"\n                        }\n                    ]\n                }\n            ]\n        },\n        {\n            \"component_code\": \"model\",\n            \"component_name\": \"模型选择\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"modelType\",\n                    \"type_name\": \"模型选择\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"2\",\n                            \"val\": \"纵向-XGBoost\"\n                        },\n                        {\n                            \"key\": \"3\",\n                            \"val\": \"横向-LR\"\n                        }\n                    ]\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"modelName\",\n                    \"type_name\": \"模型名称\",\n                    \"input_type\": \"text\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"modelDesc\",\n                    \"type_name\": \"模型描述\",\n                    \"input_type\": \"textarea\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"arbiterOrgan\",\n                    \"type_name\": \"可信第三方选择\",\n                    \"input_type\": \"button\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },\n        {\n            \"component_code\": \"assessment\",\n            \"component_name\":\"评估模型\",\n            \"is_show\":0,\n            \"is_mandatory\":1,\n            \"component_types\":[\n\n            ]\n        }\n    ]\n}', '45c9b8ff64b316cbe45e4bb5ebce7b47', '2022-06-16 09:26:31', '2023-01-16 23:58:41', 'nacos', '103.85.177.130', '', '46b6b568-e6ae-45ca-baa1-819932fc8947', '', '', '', 'json', '');
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`) VALUES (103, 'redis.yaml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    redis:\n      primary:\n        hostName: 192.168.2.150\n        port: 6379\n        password: platform\n        database: 0\n        minIdle: 0\n        maxIdle: 10\n        maxTotal: 100\n        lifo: false\n        maxWaitMillis: 1000\n        minEvictableIdleTimeMillis: 1800000\n        softMinEvictableIdleTimeMillis: 1800000', 'c7410257a901774ad260e6fa8740f87a', '2022-06-16 09:28:22', '2022-06-17 09:18:27', 'nacos', '10.244.0.0', '', '46b6b568-e6ae-45ca-baa1-819932fc8947', '', '', '', 'yaml', '');
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`) VALUES (106, 'database.yaml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    sql-script-encoding: utf-8\n    druid:\n      primary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: cosd\n        url: jdbc:mysql://192.168.2.150:3307/cosd?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: cose@123.com\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n      secondary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: platform\n        url: jdbc:mysql://192.168.2.150:3307/privacy?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: platform@123\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n      resourcePrimary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: platform\n        url: jdbc:mysql://192.168.2.150:3307/resource?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: platform@123\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n      resourceSecondary:\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        username: platform\n        url: jdbc:mysql://192.168.2.150:3307/resource?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT&useSSL=false\n        password: platform@123\n        connection-properties: config.decrypt=false;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJI/xqbyvpVttxfAKulKeSTIb7tZAGaFcPyTnE2r7AHTQ8kOnqKXDda4u59umt9XBFxi7db28KxeVooB138zuRUCAwEAAQ==\n        filter:\n          config:\n            enabled: true\n        initial-size: 3\n        min-idle: 3\n        max-active: 20\n        max-wait: 60000\n        test-while-idle: true\n        time-between-connect-error-millis: 60000\n        min-evictable-idle-time-millis: 30000\n        validationQuery: select \'x\'\n        test-on-borrow: false\n        test-on-return: false\n        pool-prepared-statements: true\n        max-pool-prepared-statement-per-connection-size: 20\n        use-global-data-source-stat: false\n        filters: stat,wall,slf4j\n        connect-properties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000\n        time-between-log-stats-millis: 300000\n        web-stat-filter:\n          enabled: true\n          url-pattern: \'/*\'\n          exclusions: \'*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*\'\n        stat-view-servlet:\n          enabled: true\n          url-pattern: \'/druid/*\'\n          reset-enable: false\n          login-username: admin\n          login-password: admin\n', 'de333f9c4e8fccdf735752d168bd416d', '2022-06-16 09:29:29', '2022-06-17 09:20:37', 'nacos', '10.244.0.0', '', '71843998-b60a-42ed-81d7-c3c9940d11c0', '', '', '', 'yaml', '');
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`) VALUES (107, 'base.json', 'DEFAULT_GROUP', '{\n    \"tokenValidateUriBlackList\":[\n      \"/user/login\",\n      \"/user/register\",\n      \"/user/sendVerificationCode\",\n      \"/user/forgetPassword\",\n      \"/captcha/get\",\n      \"/captcha/check\",\n      \"/oauth/getAuthList\",\n      \"/oauth/authLogin\",\n      \"/oauth/authRegister\",\n      \"/oauth/github/render\",\n      \"/oauth/github/callback\",\n      \"/common/getValidatePublicKey\",\n      \"/common/getTrackingID\",\n      \"/shareData/syncProject\",\n      \"/shareData/syncModel\",\n      \"/organ/getHomepage\"\n    ],\n    \"needSignUriList\":[\n\n    ],\n    \"authConfigs\":{\n        \"github\": {\n            \"redirectUrl\":\"\",\n            \"authEnable\": 1,\n            \"clientId\": \"\",\n            \"clientSecret\": \"\",\n            \"redirectUri\": \"\"\n        }\n    },\n    \"lokiConfig\": {\n        \"address\":\"172.28.1.30:3100\",\n        \"job\":\"false\",\n        \"container\":\"node1\"\n    },\n    \"adminUserIds\": [1],\n    \"defaultPassword\":\"123456\",\n    \"defaultPasswordVector\":\"excalibur\",\n    \"OfficalService\": \"\",\n    \"grpcClient\":{\n        \"grpcClientAddress\":\"192.168.2.150\",\n        \"grpcClientPort\":50051,\n        \"grpcServerPorts\":[6666],\n        \"useTls\":false,\n        \"trustCertFilePath\":\"/data/javaclientcert/ca.crt\",\n        \"keyCertChainFile\":\"/data/javaclientcert/client.crt\",\n        \"keyFile\":\"/data/javaclientcert/client8.key\"\n    },\n    \"grpcServerPort\": 9090,\n    \"uploadUrlDirPrefix\": \"/tmp/data/upload/\",\n    \"resultUrlDirPrefix\": \"/tmp/data/result/\",\n    \"runModelFileUrlDirPrefix\": \"/tmp/data/result/runModel/\",\n    \"usefulToken\": \"excalibur_forever_ABCDEFGHIJKLMN\",\n    \"systemDomainName\":\"\",\n    \"mailProperties\":{},\n  \"model_components\": [\n        {\n            \"component_code\": \"start\",\n            \"component_name\": \"开始\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"taskName\",\n                    \"type_name\": \"任务名称\",\n                    \"input_type\": \"text\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"taskDesc\",\n                    \"type_name\": \"任务描述\",\n                    \"input_type\": \"textarea\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },\n        {\n            \"component_code\": \"dataSet\",\n            \"component_name\": \"选择数据集\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },  \n        {\n            \"component_code\": \"localData\",\n            \"component_name\": \"选择数据集\",\n            \"sub_component_name\": \"本地数据\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },  \n        {\n            \"component_code\": \"fusionData\",\n            \"component_name\": \"选择数据集\",\n            \"sub_component_name\": \"联邦数据\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },  \n        {\n            \"component_code\": \"federalData\",\n            \"component_name\": \"选择数据集\",\n            \"sub_component_name\": \"数据融合\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"selectData\",\n                    \"type_name\": \"选择数据\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },\n        {\n            \"component_code\": \"dataAlign\",\n            \"component_name\": \"数据对齐\",\n            \"is_show\": 0,\n            \"is_mandatory\": 1,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"dataAlign\",\n                    \"type_name\": \"数据对齐\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"样本对齐\"\n                        },\n                        {\n                            \"key\": \"2\",\n                            \"val\": \"特征对齐\"\n                        }\n                    ]\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"MultipleSelected\",\n                    \"type_name\": \"可多选特征\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"encryption\",\n                    \"type_name\": \"加密方式\",\n                    \"input_type\": \"none\",\n                    \"input_value\": \"1\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"MD5\"\n                        }\n                    ]\n                }\n            ]\n        },\n        {\n            \"component_code\": \"exception\",\n            \"component_name\": \"异常值处理\",\n            \"is_show\": 0,\n            \"is_mandatory\": 1,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"exception\",\n                    \"type_name\": \"异常值处理\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"1\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"异常值处理\"\n                        }\n                    ]\n                }\n            ]\n        },\n        {\n            \"component_code\": \"featureCoding\",\n            \"component_name\": \"特征编码\",\n            \"is_show\": 0,\n            \"is_mandatory\": 1,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"featureCoding\",\n                    \"type_name\": \"特征编码\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"1\",\n                            \"val\": \"标签编码\"\n                        },\n                        {\n                            \"key\": \"2\",\n                            \"val\": \"哈希编码\"\n                        },\n                        {\n                            \"key\": \"3\",\n                            \"val\": \"独热编码\"\n                        },\n                        {\n                            \"key\": \"4\",\n                            \"val\": \"计数编码\"\n                        },\n                        {\n                            \"key\": \"5\",\n                            \"val\": \"直方图编码\"\n                        },\n                        {\n                            \"key\": \"6\",\n                            \"val\": \"WOE编码\"\n                        },\n                        {\n                            \"key\": \"7\",\n                            \"val\": \"目标编码\"\n                        },\n                        {\n                            \"key\": \"8\",\n                            \"val\": \"平均编码\"\n                        },\n                        {\n                            \"key\": \"9\",\n                            \"val\": \"模型编码\"\n                        }\n                    ]\n                }\n            ]\n        },\n        {\n            \"component_code\": \"model\",\n            \"component_name\": \"模型选择\",\n            \"is_show\": 0,\n            \"is_mandatory\": 0,\n            \"component_types\": [\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"modelType\",\n                    \"type_name\": \"模型选择\",\n                    \"input_type\": \"select\",\n                    \"input_value\": \"\",\n                    \"input_values\": [\n                        {\n                            \"key\": \"2\",\n                            \"val\": \"纵向-XGBoost\"\n                        },\n                        {\n                            \"key\": \"3\",\n                            \"val\": \"横向-LR\"\n                        }\n                    ]\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 1,\n                    \"type_code\": \"modelName\",\n                    \"type_name\": \"模型名称\",\n                    \"input_type\": \"text\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"modelDesc\",\n                    \"type_name\": \"模型描述\",\n                    \"input_type\": \"textarea\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                },\n                {\n                    \"is_show\": 0,\n                    \"is_required\": 0,\n                    \"type_code\": \"arbiterOrgan\",\n                    \"type_name\": \"可信第三方选择\",\n                    \"input_type\": \"button\",\n                    \"input_value\": \"\",\n                    \"input_values\": []\n                }\n            ]\n        },\n        {\n            \"component_code\": \"assessment\",\n            \"component_name\": \"评估模型\",\n            \"is_show\": 0,\n            \"is_mandatory\": 1,\n            \"component_types\": []\n        }\n    ]\n}', '0bdf32547051c410ac8729744ad39cc1', '2022-06-16 09:29:29', '2023-01-05 09:02:54', 'nacos', '103.85.177.130', '', '71843998-b60a-42ed-81d7-c3c9940d11c0', '', '', '', 'json', '');
INSERT INTO `config_info` (`id`, `data_id`, `group_id`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `app_name`, `tenant_id`, `c_desc`, `c_use`, `effect`, `type`, `c_schema`) VALUES (108, 'redis.yaml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    redis:\n      primary:\n        hostName: 192.168.2.150\n        port: 6379\n        password: platform\n        database: 1\n        minIdle: 0\n        maxIdle: 10\n        maxTotal: 100\n        lifo: false\n        maxWaitMillis: 1000\n        minEvictableIdleTimeMillis: 1800000\n        softMinEvictableIdleTimeMillis: 1800000', 'cac51f9a54a982b8210c36f1708fe486', '2022-06-16 09:29:29', '2022-06-17 09:21:22', 'nacos', '10.244.0.0', '', '71843998-b60a-42ed-81d7-c3c9940d11c0', '', '', '', 'yaml', '');

/*!40000 ALTER TABLE `config_info` ENABLE KEYS */;
UNLOCK TABLES;


-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'Group' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
INSERT INTO `tenant_info` VALUES (3,'1','46b6b568-e6ae-45ca-baa1-819932fc8947','dev','dev','nacos',1655369938742,1655369938742),(4,'1','71843998-b60a-42ed-81d7-c3c9940d11c0','product','product','nacos',1655369947525,1655369947525);
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;
-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
