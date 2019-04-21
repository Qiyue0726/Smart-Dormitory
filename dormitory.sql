/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : dormitory

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 24/03/2019 01:06:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for devicestatus
-- ----------------------------
DROP TABLE IF EXISTS `devicestatus`;
CREATE TABLE `devicestatus`  (
  `macId` int(11) NOT NULL,
  `room` varchar(4) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `all_status` int(1) NULL DEFAULT NULL COMMENT '1表示打开总开关，0表示关闭总开关',
  `light_status` int(1) NULL DEFAULT NULL COMMENT '1：开灯，0：关灯',
  `window_status` int(1) NULL DEFAULT NULL COMMENT '1：开窗，0：关窗',
  PRIMARY KEY (`macId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of devicestatus
-- ----------------------------
INSERT INTO `devicestatus` VALUES (162, 'R101', 1, 0, 1);
INSERT INTO `devicestatus` VALUES (163, 'R102', 1, 0, 1);
INSERT INTO `devicestatus` VALUES (164, 'R103', 0, 1, 0);
INSERT INTO `devicestatus` VALUES (165, 'M101', 1, 0, 1);
INSERT INTO `devicestatus` VALUES (166, 'M102', 0, 0, 1);
INSERT INTO `devicestatus` VALUES (167, 'M103', 1, 1, 0);
INSERT INTO `devicestatus` VALUES (168, 'T101', 1, 1, 1);
INSERT INTO `devicestatus` VALUES (169, 'T102', 0, 1, 0);
INSERT INTO `devicestatus` VALUES (170, 'T103', 1, 1, 0);
INSERT INTO `devicestatus` VALUES (171, 'R201', 0, 1, 0);
INSERT INTO `devicestatus` VALUES (172, 'R202', 1, 0, 1);
INSERT INTO `devicestatus` VALUES (173, 'R301', 0, 0, 0);
INSERT INTO `devicestatus` VALUES (174, 'R302', 1, 1, 1);
INSERT INTO `devicestatus` VALUES (175, 'R203', 0, 1, 1);
INSERT INTO `devicestatus` VALUES (176, 'R303', 1, 0, 1);

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `userName` varchar(6) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `password` varchar(8) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`userName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('33333', '12345678');
INSERT INTO `manager` VALUES ('sakura', '12345678');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `room` varchar(4) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `macId` int(11) NOT NULL,
  PRIMARY KEY (`room`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('M101', 165);
INSERT INTO `room` VALUES ('M102', 166);
INSERT INTO `room` VALUES ('M103', 167);
INSERT INTO `room` VALUES ('R101', 162);
INSERT INTO `room` VALUES ('R102', 163);
INSERT INTO `room` VALUES ('R103', 164);
INSERT INTO `room` VALUES ('T101', 168);
INSERT INTO `room` VALUES ('T102', 169);
INSERT INTO `room` VALUES ('T103', 170);

-- ----------------------------
-- Table structure for sensordata
-- ----------------------------
DROP TABLE IF EXISTS `sensordata`;
CREATE TABLE `sensordata`  (
  `macId` int(11) NOT NULL,
  `temp` int(3) NULL DEFAULT NULL,
  `hum` int(3) NULL DEFAULT NULL,
  `time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sensordata
-- ----------------------------
INSERT INTO `sensordata` VALUES (162, 27, 95, '2019-02-12 14:28:49');
INSERT INTO `sensordata` VALUES (162, 23, 88, '2019-02-12 09:03:43');
INSERT INTO `sensordata` VALUES (162, 34, 82, '2019-02-12 09:06:36');
INSERT INTO `sensordata` VALUES (162, 24, 79, '2019-02-12 11:03:43');
INSERT INTO `sensordata` VALUES (162, 25, 77, '2019-01-27 22:45:52');
INSERT INTO `sensordata` VALUES (162, 24, 82, '2019-01-28 09:08:44');
INSERT INTO `sensordata` VALUES (162, 23, 86, '2019-01-28 14:28:42');
INSERT INTO `sensordata` VALUES (162, 22, 85, '2019-02-12 20:45:58');
INSERT INTO `sensordata` VALUES (162, 20, 85, '2019-02-12 21:46:05');
INSERT INTO `sensordata` VALUES (162, 23, 87, '2019-02-14 22:38:50');
INSERT INTO `sensordata` VALUES (162, 24, 85, '2019-02-14 14:28:46');
INSERT INTO `sensordata` VALUES (162, 26, 85, '2019-02-14 09:08:49');
INSERT INTO `sensordata` VALUES (162, 25, 85, '2019-02-14 18:45:23');
INSERT INTO `sensordata` VALUES (162, 24, 85, '2019-02-16 00:54:30');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 00:55:30');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 00:56:30');
INSERT INTO `sensordata` VALUES (162, 25, 86, '2019-02-16 00:58:02');
INSERT INTO `sensordata` VALUES (162, 24, 86, '2019-02-16 00:59:02');
INSERT INTO `sensordata` VALUES (162, 24, 85, '2019-02-16 01:17:42');
INSERT INTO `sensordata` VALUES (162, 24, 85, '2019-02-16 01:18:42');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 01:19:42');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 01:20:42');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 01:21:42');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 01:48:43');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 01:48:43');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 01:48:43');
INSERT INTO `sensordata` VALUES (162, 24, 84, '2019-02-16 01:48:43');
INSERT INTO `sensordata` VALUES (162, 24, 83, '2019-02-16 01:49:43');
INSERT INTO `sensordata` VALUES (162, 24, 83, '2019-02-16 01:50:43');
INSERT INTO `sensordata` VALUES (162, 25, 82, '2019-02-16 01:52:43');
INSERT INTO `sensordata` VALUES (162, 25, 82, '2019-02-16 01:52:44');
INSERT INTO `sensordata` VALUES (162, 24, 81, '2019-02-16 02:22:21');
INSERT INTO `sensordata` VALUES (162, 25, 81, '2019-02-16 02:48:25');
INSERT INTO `sensordata` VALUES (162, 25, 81, '2019-02-16 02:48:44');
INSERT INTO `sensordata` VALUES (162, 25, 81, '2019-02-16 02:54:31');
INSERT INTO `sensordata` VALUES (162, 25, 81, '2019-02-16 02:54:44');
INSERT INTO `sensordata` VALUES (162, 25, 95, '2019-02-20 22:19:17');
INSERT INTO `sensordata` VALUES (162, 25, 95, '2019-02-20 22:22:01');
INSERT INTO `sensordata` VALUES (162, 25, 92, '2019-02-20 22:28:26');
INSERT INTO `sensordata` VALUES (162, 25, 93, '2019-02-20 22:30:42');
INSERT INTO `sensordata` VALUES (162, 25, 93, '2019-02-20 22:34:14');
INSERT INTO `sensordata` VALUES (162, 25, 93, '2019-02-20 22:35:14');
INSERT INTO `sensordata` VALUES (162, 25, 93, '2019-02-20 22:37:07');
INSERT INTO `sensordata` VALUES (162, 25, 93, '2019-02-20 22:38:08');
INSERT INTO `sensordata` VALUES (162, 24, 95, '2019-02-20 22:46:17');
INSERT INTO `sensordata` VALUES (163, 25, 85, '2019-02-21 14:43:29');
INSERT INTO `sensordata` VALUES (163, 25, 85, '2019-02-21 14:43:48');
INSERT INTO `sensordata` VALUES (163, 26, 84, '2019-02-21 14:44:00');
INSERT INTO `sensordata` VALUES (163, 24, 86, '2019-02-21 14:44:46');
INSERT INTO `sensordata` VALUES (163, 26, 88, '2019-02-21 14:44:56');
INSERT INTO `sensordata` VALUES (164, 24, 85, '2019-02-21 14:45:04');
INSERT INTO `sensordata` VALUES (164, 26, 85, '2019-02-21 14:45:12');
INSERT INTO `sensordata` VALUES (164, 25, 82, '2019-02-21 14:45:20');
INSERT INTO `sensordata` VALUES (164, 27, 88, '2019-02-21 14:45:29');
INSERT INTO `sensordata` VALUES (165, 27, 88, '2019-02-21 14:45:36');
INSERT INTO `sensordata` VALUES (165, 28, 89, '2019-02-21 14:45:43');
INSERT INTO `sensordata` VALUES (165, 28, 86, '2019-02-21 14:45:52');
INSERT INTO `sensordata` VALUES (165, 27, 85, '2019-02-21 14:45:59');
INSERT INTO `sensordata` VALUES (165, 25, 84, '2019-02-21 14:46:07');
INSERT INTO `sensordata` VALUES (166, 27, 88, '2019-02-21 14:46:16');
INSERT INTO `sensordata` VALUES (166, 24, 85, '2019-02-21 14:46:22');
INSERT INTO `sensordata` VALUES (165, 24, 87, '2019-02-21 14:46:30');
INSERT INTO `sensordata` VALUES (166, 24, 86, '2019-02-21 14:46:42');
INSERT INTO `sensordata` VALUES (166, 27, 79, '2019-02-21 14:46:49');
INSERT INTO `sensordata` VALUES (167, 28, 89, '2019-02-21 14:46:56');
INSERT INTO `sensordata` VALUES (167, 27, 85, '2019-02-21 14:47:05');
INSERT INTO `sensordata` VALUES (167, 28, 87, '2019-02-21 14:47:12');
INSERT INTO `sensordata` VALUES (167, 26, 87, '2019-02-21 14:47:21');
INSERT INTO `sensordata` VALUES (167, 28, 79, '2019-02-21 14:47:30');

-- ----------------------------
-- Table structure for serviceinfo
-- ----------------------------
DROP TABLE IF EXISTS `serviceinfo`;
CREATE TABLE `serviceinfo`  (
  `id` int(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `room` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `serviceType` int(1) NOT NULL COMMENT '1：网络，2：阳台，3：空调，4：其他',
  `serviceTitle` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `serviceData` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `serviceStatus` int(1) NOT NULL COMMENT '1表示未处理可显示，0表示已忽略',
  `systemStatus` int(1) NOT NULL COMMENT '1表示未解决，0表示已解决',
  `chuliTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '处理时间',
  `systemTime` datetime(0) NOT NULL COMMENT '报修时间',
  `reply` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of serviceinfo
-- ----------------------------
INSERT INTO `serviceinfo` VALUES (1, 'R101', 1, '空调坏了', '空调不能制冷了', 1, 1, '2019-03-18 21:11:55', '2019-02-20 11:56:03', '3432');
INSERT INTO `serviceinfo` VALUES (2, 'R102', 2, '3', '234444444444444444444444444444444444444444444444444444433333333333333333333333333333333333333333333333333333333333', 1, 0, '2019-03-18 21:44:02', '2019-02-19 11:56:19', 'dfkj肯定');
INSERT INTO `serviceinfo` VALUES (3, 'R101', 1, '87', '8733333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333', 1, 0, '2019-02-19 21:29:40', '2019-02-19 11:56:07', NULL);
INSERT INTO `serviceinfo` VALUES (4, 'R101', 1, '23', '。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。22222222222222222222222222222222222222222226', 1, 0, '2019-03-15 13:52:17', '2019-02-19 11:56:15', NULL);
INSERT INTO `serviceinfo` VALUES (18, 'R101', 2, '333', '333', 1, 0, '2019-02-19 14:25:03', '2019-02-19 13:36:16', NULL);
INSERT INTO `serviceinfo` VALUES (19, 'R101', 2, '33', 'rich', 0, 1, '2019-03-15 20:41:26', '2019-02-20 18:14:31', 'kk');
INSERT INTO `serviceinfo` VALUES (20, 'R101', 4, '32', 'rew ', 1, 0, '2019-03-15 20:39:34', '2019-02-20 18:15:55', NULL);
INSERT INTO `serviceinfo` VALUES (21, 'R102', 2, '333', 'rree', 0, 1, '2019-03-15 20:43:08', '2019-02-20 18:17:33', 'oo');
INSERT INTO `serviceinfo` VALUES (22, 'R102', 3, 'TFYRTDR', 'FDGSFGDS', 1, 0, '2019-02-21 14:41:15', '2019-02-21 14:41:12', NULL);
INSERT INTO `serviceinfo` VALUES (23, 'R103', 2, 'GDGFDG', 'DGSFSGFG', 0, 1, '2019-02-21 14:41:50', '2019-02-21 14:41:46', NULL);
INSERT INTO `serviceinfo` VALUES (24, 'R203', 1, 'SDFASF', 'WETERFRE', 1, 0, '2019-02-21 14:42:12', '2019-02-21 14:42:08', NULL);
INSERT INTO `serviceinfo` VALUES (25, 'T103', 4, '2RTHFD', 'DGSDFAFSDF', 0, 1, '2019-02-21 14:42:30', '2019-02-21 14:42:27', NULL);
INSERT INTO `serviceinfo` VALUES (26, 'M202', 1, 'DSFSA', 'SDFAS', 1, 1, '2019-02-21 14:42:49', '2019-02-21 14:42:46', NULL);
INSERT INTO `serviceinfo` VALUES (27, 'R101', 2, '7678', '78787', 1, 1, '2019-03-18 21:44:16', '2019-03-18 21:44:16', '正在处理中');
INSERT INTO `serviceinfo` VALUES (28, 'R101', 0, 'wew', 'qweq', 1, 1, '2019-03-18 21:45:17', '2019-03-18 21:45:17', '正在处理中...');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `studentId` int(10) NOT NULL,
  `userName` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` int(11) NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `cardId` bigint(12) NULL DEFAULT NULL,
  `room` varchar(4) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`studentId`) USING BTREE,
  INDEX `room`(`room`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES (102, '张三', 333, NULL, NULL, 'R102');
INSERT INTO `userinfo` VALUES (103, '李四', 333, NULL, NULL, 'R103');
INSERT INTO `userinfo` VALUES (333, 'Sakura', 333, '33', 1626324628, 'R101');
INSERT INTO `userinfo` VALUES (1540707145, '余建浩', 333, '18565568363', 19922923637, 'R101');

SET FOREIGN_KEY_CHECKS = 1;
