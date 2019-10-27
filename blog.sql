/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-10-27 21:43:21
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
  `like_num` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
  `add_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`album_id`),
  KEY `add_time` (`add_time`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of album
-- ----------------------------
INSERT INTO `album` VALUES ('1', 'yyyy', '美食图片', '1', '', '1', '0');
INSERT INTO `album` VALUES ('2', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('3', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('4', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('5', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('6', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('7', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('8', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('9', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('10', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('11', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('12', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('13', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('14', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('15', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('16', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('17', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('18', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('19', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('20', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('21', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('22', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('23', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('24', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('25', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('26', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('27', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('28', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('29', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('30', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('31', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('32', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('33', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('34', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('35', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('36', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('37', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('38', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('39', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('40', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('41', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('42', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('43', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('44', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('45', '美食', '美食图片', '1', '/images/1.jpg', '1', '0');
INSERT INTO `album` VALUES ('46', '美食', '美食图片', '1', '/images/1.jpg', '1', '0');
INSERT INTO `album` VALUES ('47', '美食', '美食图片', '1', '/images/1.jpg', '1', '0');
INSERT INTO `album` VALUES ('48', '美食', '美食图片', '1', '/images/1.jpg', '0', '0');
INSERT INTO `album` VALUES ('49', '美食', '美食图片', '1', '', '1', '0');
INSERT INTO `album` VALUES ('50', '美食', '美食图片', '1', '/albumThumb/2019/1027/a87a15e9f87a7a9a328114629c7ab93b.jpg', '0', '0');
INSERT INTO `album` VALUES ('51', 'qq', 'sgf4s6fs', '0', '/albumThumb/2019/1027/9d973012006b1b5cb9e9a90ae5eb5951.jpg', '0', '1572180777');
INSERT INTO `album` VALUES ('52', 'ttffrrggrgggb', '', '0', '/albumThumb/2019/1027/bc3184c06a85d94c6598423c096f3550.jpg', '0', '1572180859');

-- ----------------------------
-- Table structure for album_comment
-- ----------------------------
DROP TABLE IF EXISTS `album_comment`;
CREATE TABLE `album_comment` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `album_id` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `connection` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `ip` int(10) unsigned NOT NULL,
  `add_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `album_id` (`album_id`) USING BTREE,
  CONSTRAINT `album_comment_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`album_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of album_comment
-- ----------------------------
INSERT INTO `album_comment` VALUES ('3', '1', '', '', 'fdddd5ds465g4g', '14454651', '41654649');
INSERT INTO `album_comment` VALUES ('4', '1', '', '', 'dvdfbdbdbbdb', '1311262', '4564494');
INSERT INTO `album_comment` VALUES ('5', '1', '', '', '261654d6516gdbbbd', '11113116', '23226');
INSERT INTO `album_comment` VALUES ('6', '1', '', '', 'dfbdbdbdb', '23226521', '51561651');
INSERT INTO `album_comment` VALUES ('7', '1', '', '', '<p>1244</p>', '2130706433', '1572102130');
INSERT INTO `album_comment` VALUES ('8', '1', '', '', '<p>1</p>', '2130706433', '1572102305');
INSERT INTO `album_comment` VALUES ('9', '1', '', '', '<p>2</p>', '2130706433', '1572102549');
INSERT INTO `album_comment` VALUES ('10', '1', '', '', '<p>3</p>', '2130706433', '1572102557');
INSERT INTO `album_comment` VALUES ('11', '1', '', '', '<p>4</p>', '2130706433', '1572102564');
INSERT INTO `album_comment` VALUES ('12', '1', '', '', '<p>5</p>', '2130706433', '1572102686');
INSERT INTO `album_comment` VALUES ('13', '1', 'ben', '905713861@qq.cc', '<p>6<br/></p>', '2130706433', '1572102706');
INSERT INTO `album_comment` VALUES ('15', '2', '', '', '<p>1</p>', '2130706433', '1572103121');
INSERT INTO `album_comment` VALUES ('16', '1', '', '', '<p>11</p>', '2130706433', '1572104193');
INSERT INTO `album_comment` VALUES ('17', '1', 'ben', '5f4g641561', '<p>相册留言</p>', '2130706433', '1572104213');
INSERT INTO `album_comment` VALUES ('18', '46', 'admin', '', '<p>123</p>', '2130706433', '1572104317');
INSERT INTO `album_comment` VALUES ('19', '49', '', '', '<p>dfsfsdf</p>', '2130706433', '1572104367');
INSERT INTO `album_comment` VALUES ('20', '49', '', '', '<p>gehyrhyh<br/></p>', '2130706433', '1572104370');
INSERT INTO `album_comment` VALUES ('23', '49', '', '', '<p><img src=\"http://img.baidu.com/hi/jx2/j_0015.gif\"/>kuuk</p>', '2130706433', '1572139265');

-- ----------------------------
-- Table structure for album_img
-- ----------------------------
DROP TABLE IF EXISTS `album_img`;
CREATE TABLE `album_img` (
  `img_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `album_id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `path` varchar(100) NOT NULL,
  PRIMARY KEY (`img_id`),
  KEY `album_img_ibfk_1` (`album_id`),
  CONSTRAINT `album_img_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`album_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of album_img
-- ----------------------------
INSERT INTO `album_img` VALUES ('1', '1', '饺子', '/images/1.jpg');
INSERT INTO `album_img` VALUES ('2', '1', 'dfv', '/images/2.jpg');
INSERT INTO `album_img` VALUES ('3', '1', 'sdvvd', '/images/3.jpg');
INSERT INTO `album_img` VALUES ('4', '50', '4.jpg', '/album/2019/1027/e6ebc3dcf19041c6d258017239a1b657.jpg');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `type_id` int(10) unsigned NOT NULL,
  `is_recommend` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `key_words` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `thumb_path` varchar(100) DEFAULT NULL,
  `like_num` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `add_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`article_id`),
  KEY `article_ibfk_1` (`type_id`),
  KEY `key_words` (`key_words`),
  KEY `add_time` (`add_time`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `article_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', 'java原生web服务器', '1', '1', 'java web http服务', 'java原生web服务器java原生web服务器，java原生web服务器', '/articleThumb/2019/1027/fff4e89df68357bd14b950f1872c0a68.jpg', '1', '1571066854');
INSERT INTO `article` VALUES ('2', 'qq', '5', '1', 'dfv', 'juugerg56dg494er9', '/articleThumb/2019/1027/730d2bdb83dfcb8384455479cf4b0387.jpg', '0', '1571064726');
INSERT INTO `article` VALUES ('4', 'ttff', '1', '0', 'ff', 'dhggggggggggggggsrrffrfrfre', null, '6', '1571066982');
INSERT INTO `article` VALUES ('5', 'img', '1', '0', 'fff', 'edgttrhdhrtthrdthrdthrth', null, '1', '1571462508');
INSERT INTO `article` VALUES ('6', 'hhh', '1', '0', '', 'rehserthrhrthsergh8h6trgh46rs5th4h4', '/articleThumb/2019/1027/e1438317729edb5c556ae31ca03ac66e.jpg', '0', '1571543998');
INSERT INTO `article` VALUES ('7', 'ttff', '5', '0', '', 'jytjty', '/articleThumb/2019/1027/8569f30deb35f24b49b04a2973972051.jpg', '0', '1572180262');

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `connection` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `ip` int(10) unsigned NOT NULL,
  `add_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `article_comment_ibfk_1` (`article_id`),
  CONSTRAINT `article_comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_comment
-- ----------------------------
INSERT INTO `article_comment` VALUES ('3', '1', '', '', 'fdddd5ds465g4g', '14454651', '41654649');
INSERT INTO `article_comment` VALUES ('4', '1', '', '', 'dvdfbdbdbbdb', '1311262', '4564494');
INSERT INTO `article_comment` VALUES ('5', '4', '', '', '261654d6516gdbbbd', '11113116', '23226');
INSERT INTO `article_comment` VALUES ('6', '1', '', '', 'dfbdbdbdb', '23226521', '51561651');
INSERT INTO `article_comment` VALUES ('7', '1', '', '', '<p>1244</p>', '2130706433', '1572102130');
INSERT INTO `article_comment` VALUES ('8', '5', '', '', '<p>1</p>', '2130706433', '1572102305');
INSERT INTO `article_comment` VALUES ('11', '1', '', '', '<p>4</p>', '2130706433', '1572102564');
INSERT INTO `article_comment` VALUES ('13', '1', 'ben', '905713861@qq.cc', '<p>6<br/></p>', '2130706433', '1572102706');
INSERT INTO `article_comment` VALUES ('16', '1', '', '', '<p><img src=\"http://img.baidu.com/hi/jx2/j_0003.gif\"/>4354864</p>', '2130706433', '1572104406');
INSERT INTO `article_comment` VALUES ('17', '1', '', '', '<p>ddgd<img src=\"http://img.baidu.com/hi/jx2/j_0002.gif\"/>b131</p>', '2130706433', '1572104725');

-- ----------------------------
-- Table structure for article_content
-- ----------------------------
DROP TABLE IF EXISTS `article_content`;
CREATE TABLE `article_content` (
  `article_id` int(10) unsigned NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`article_id`),
  CONSTRAINT `article_content_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_content
-- ----------------------------
INSERT INTO `article_content` VALUES ('1', '<p>123156g<br/></p>');
INSERT INTO `article_content` VALUES ('2', '<p>123156ghnhnyyy<br/></p><p>tgg</p>');
INSERT INTO `article_content` VALUES ('4', '<p>1</p>');
INSERT INTO `article_content` VALUES ('5', '<p style=\"line-height: 16px;\"><img src=\"http://admin.blog.dev/static/js/plugins/ueditor/dialogs/attachment/fileTypeImages/icon_doc.gif\"/><a style=\"font-size:12px; color:#0066cc;\" href=\"http://file.blog.dev/article/2019/1019/655a900391fe47e08c7f8fa706a30c50.docx\" title=\"养老保险转出情况说明.docx\">养老保险转出情况说明.docx</a></p><p style=\"line-height: 16px;\"><img src=\"http://admin.blog.dev/static/js/plugins/ueditor/dialogs/attachment/fileTypeImages/icon_txt.gif\"/><a style=\"font-size:12px; color:#0066cc;\" href=\"http://file.blog.dev/article/2019/1019/61297e65333e1b18ed8276911d8db8fe.txt\" title=\"支付接口参数.txt\">支付接口参数.txt</a></p><p><img src=\"http://file.blog.dev/article/2019/1019/faf8da8146f42b6df04b509a3a7a8afa.jpg\" title=\"faf8da8146f42b6df04b509a3a7a8afa.jpg\" alt=\"1.jpg\"/></p>');
INSERT INTO `article_content` VALUES ('6', '<p>,,h<br/></p>');
INSERT INTO `article_content` VALUES ('7', '<p>jytjty<br/></p>');

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type` (
  `type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  `sort` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_type
-- ----------------------------
INSERT INTO `article_type` VALUES ('1', 'java', '0');
INSERT INTO `article_type` VALUES ('2', 'php', '0');
INSERT INTO `article_type` VALUES ('3', 'nodejs', '0');
INSERT INTO `article_type` VALUES ('4', 'js前端', '0');
INSERT INTO `article_type` VALUES ('5', 'H5', '0');
INSERT INTO `article_type` VALUES ('6', 'python', '0');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `connection` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `ip` int(10) unsigned NOT NULL,
  `add_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('3', '', '', 'fdddd5ds465g4g', '14454651', '41654649');
INSERT INTO `comment` VALUES ('4', '', '', 'dvdfbdbdbbdb', '1311262', '4564494');
INSERT INTO `comment` VALUES ('5', '', '', '261654d6516gdbbbd', '11113116', '23226');
INSERT INTO `comment` VALUES ('6', '', '', 'dfbdbdbdb', '23226521', '51561651');
INSERT INTO `comment` VALUES ('7', '', '', '<p>1244</p>', '2130706433', '1572102130');
INSERT INTO `comment` VALUES ('8', '', '', '<p>1</p>', '2130706433', '1572102305');
INSERT INTO `comment` VALUES ('9', '', '', '<p>2</p>', '2130706433', '1572102549');
INSERT INTO `comment` VALUES ('10', '', '', '<p>3</p>', '2130706433', '1572102557');
INSERT INTO `comment` VALUES ('11', '', '', '<p>4</p>', '2130706433', '1572102564');
INSERT INTO `comment` VALUES ('12', '', '', '<p>5</p>', '2130706433', '1572102686');
INSERT INTO `comment` VALUES ('13', 'ben', '905713861@qq.cc', '<p>6<br/></p>', '2130706433', '1572102706');
INSERT INTO `comment` VALUES ('15', '', '', '<p>1</p>', '2130706433', '1572103121');
INSERT INTO `comment` VALUES ('16', '', '', '<p><img src=\"http://img.baidu.com/hi/jx2/j_0003.gif\"/>4354864</p><p><img src=\"http://img.baidu.com/hi/jx2/j_0003.gif\"/>4354864</p>', '2130706433', '1572104406');
INSERT INTO `comment` VALUES ('17', '', '', '<p>ddgd<img src=\"http://img.baidu.com/hi/jx2/j_0002.gif\"/>b131</p>', '2130706433', '1572104725');
INSERT INTO `comment` VALUES ('18', '', '', '<p>hhh</p>', '2130706433', '1572146583');
INSERT INTO `comment` VALUES ('20', 'ben', '5f4g641561', '<p>gggg<br/></p>', '2130706433', '1572147318');

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `manager_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password` char(32) NOT NULL,
  `real_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` char(11) DEFAULT NULL,
  `last_login` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`manager_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', 'ben', 'e10adc3949ba59abbe56e057f20f883e', '吴小彬', '905713861@qq.com', '13535169384', null);

-- ----------------------------
-- Table structure for website
-- ----------------------------
DROP TABLE IF EXISTS `website`;
CREATE TABLE `website` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `website_name` varchar(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `head_img_path` varchar(100) NOT NULL,
  `share_code_img_path` varchar(100) NOT NULL,
  `aboutme` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of website
-- ----------------------------
INSERT INTO `website` VALUES ('1', '小彬的个人博客', '杨青，一个80后草根女站长！09年入行。一直潜心研究web前端技术，一边工作一边积累经验，分享一些个人博客模板，以及SEO优化等心得。', '/headImg/2019/1027/2b53e0b0b9a238f4a720191e7ff578ac.jpg', '/shareCodeImg/2019/1027/a0de0185904f55e713fdd70fc6eff5c0.jpg', '<p>yuykuyuyk</p><p><br/></p>');
