/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-10-07 22:45:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for album
-- ----------------------------
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album` (
  `album_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `is_recommend` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `thumb_path` varchar(100) NOT NULL,
  `add_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`album_id`),
  KEY `add_time` (`add_time`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of album
-- ----------------------------
INSERT INTO `album` VALUES ('1', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('2', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('3', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('4', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('5', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('6', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('7', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('8', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('9', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('10', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('11', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('12', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('13', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('14', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('15', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('16', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('17', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('18', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('19', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('20', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('21', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('22', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('23', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('24', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('25', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('26', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('27', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('28', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('29', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('30', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('31', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('32', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('33', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('34', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('35', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('36', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('37', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('38', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('39', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('40', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('41', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('42', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('43', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('44', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('45', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('46', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('47', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('48', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('49', '美食', '美食图片', '1', '/images/1.jpg', '0');
INSERT INTO `album` VALUES ('50', '美食', '美食图片', '1', '/images/1.jpg', '0');

-- ----------------------------
-- Table structure for album_img
-- ----------------------------
DROP TABLE IF EXISTS `album_img`;
CREATE TABLE `album_img` (
  `img_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `album_id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `path` varchar(100) NOT NULL,
  PRIMARY KEY (`img_id`),
  KEY `album_img_ibfk_1` (`album_id`),
  CONSTRAINT `album_img_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`album_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of album_img
-- ----------------------------
INSERT INTO `album_img` VALUES ('1', '1', '饺子', '/images/1.jpg');
INSERT INTO `album_img` VALUES ('2', '1', 'dfv', '/images/2.jpg');
INSERT INTO `album_img` VALUES ('3', '1', 'sdvvd', '/images/3.jpg');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `type_id` int(10) unsigned NOT NULL,
  `key_words` varchar(100) NOT NULL,
  `description` varchar(255) NOT NULL,
  `is_recommend` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `thumb_path` varchar(100) NOT NULL,
  `add_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`article_id`),
  KEY `article_ibfk_1` (`type_id`),
  KEY `key_words` (`key_words`),
  KEY `add_time` (`add_time`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `article_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', 'java原生web服务器', '1', 'java web http服务', 'java原生web服务器java原生web服务器，java原生web服务器', '1', '', '0');

-- ----------------------------
-- Table structure for article_content
-- ----------------------------
DROP TABLE IF EXISTS `article_content`;
CREATE TABLE `article_content` (
  `article_id` int(10) unsigned NOT NULL,
  `content` text,
  PRIMARY KEY (`article_id`),
  CONSTRAINT `article_content_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_content
-- ----------------------------

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type` (
  `type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_type
-- ----------------------------
INSERT INTO `article_type` VALUES ('1', 'java');
INSERT INTO `article_type` VALUES ('2', 'php');
INSERT INTO `article_type` VALUES ('3', 'nodejs');
INSERT INTO `article_type` VALUES ('4', 'js前端');
INSERT INTO `article_type` VALUES ('5', 'H5');
INSERT INTO `article_type` VALUES ('6', 'python');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `head_img` varchar(100) NOT NULL,
  `shanre_code_img` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '杨青，一个80后草根女站长！09年入行。一直潜心研究web前端技术，一边工作一边积累经验，分享一些个人博客模板，以及SEO优化等心得。', '/images/4.jpg', '/images/wx.jpg');
