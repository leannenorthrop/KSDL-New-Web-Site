-- MySQL dump 10.13  Distrib 5.1.47, for apple-darwin10.3.0 (i386)
--
-- Host: localhost    Database: londonsamyedzong
-- ------------------------------------------------------
-- Server version	5.1.47

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL,
  `post_code` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `place_name` text CHARACTER SET latin1,
  `type` varchar(5) CHARACTER SET latin1 DEFAULT NULL,
  `country` varchar(108) CHARACTER SET latin1 DEFAULT NULL,
  `county` varchar(25) CHARACTER SET latin1 DEFAULT NULL,
  `street_number` int(11) DEFAULT NULL,
  `line1` text CHARACTER SET latin1,
  `post_town` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `line2` text CHARACTER SET latin1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `summary` longtext,
  `image_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  `title` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `FKD458CCF6B776D63` (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `last_updated` datetime NOT NULL,
  `old_value` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `actor` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `uri` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `new_value` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `persisted_object_version` bigint(20) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `class_name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `event_name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `persisted_object_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1192 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `download`
--

DROP TABLE IF EXISTS `download`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `download` (
  `id` bigint(20) NOT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK551AC888D24E14D9` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `type` varchar(5) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` bigint(20) NOT NULL,
  `summary` longtext,
  `title` varchar(254) CHARACTER SET latin1 DEFAULT NULL,
  `leader_id` bigint(20) DEFAULT NULL,
  `is_repeatable` tinyint(1) DEFAULT NULL,
  `organizer_id` bigint(20) DEFAULT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  `venue_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `FK5C6729AB776D63` (`image_id`),
  KEY `FK5C6729A8D6FE8C1` (`leader_id`),
  KEY `FK5C6729A7FAD865A` (`organizer_id`),
  KEY `FK5C6729A7B3A8B2E` (`venue_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `thumbnail` longblob NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `image` longblob NOT NULL,
  `mime_type` varchar(255) CHARACTER SET latin1 NOT NULL,
  `date_created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `link`
--

DROP TABLE IF EXISTS `link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `controller` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `position` int(11) NOT NULL,
  `refid` varchar(255) DEFAULT NULL,
  `section` varchar(1) NOT NULL,
  `type` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menu_category`
--

DROP TABLE IF EXISTS `menu_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `level` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `menu_categories_idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3F1D73DE14F0FE6F` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menu_item`
--

DROP TABLE IF EXISTS `menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA4FAA1F3754B696D` (`parent_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menu_item_sub_menu_items`
--

DROP TABLE IF EXISTS `menu_item_sub_menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_item_sub_menu_items` (
  `menu_item_id` bigint(20) NOT NULL,
  `sub_menu_items__id` bigint(20) NOT NULL,
  PRIMARY KEY (`menu_item_id`,`sub_menu_items__id`),
  KEY `FK5FEE140B9FFFEE57` (`sub_menu_items__id`),
  KEY `FK5FEE140BC44A5F84` (`menu_item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meta`
--

DROP TABLE IF EXISTS `meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `meta_idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK33160514F0FE6F` (`product_id`),
  CONSTRAINT `FK33160514F0FE6F` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_downloadable`
--

DROP TABLE IF EXISTS `non_downloadable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_downloadable` (
  `id` bigint(20) NOT NULL,
  `depth` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pending_email_confirmation`
--

DROP TABLE IF EXISTS `pending_email_confirmation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pending_email_confirmation` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `timestamp` datetime NOT NULL,
  `user_token` text CHARACTER SET latin1,
  `email_address` varchar(80) CHARACTER SET latin1 NOT NULL,
  `confirmation_token` varchar(80) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price`
--

DROP TABLE IF EXISTS `price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `category` varchar(1) CHARACTER SET latin1 NOT NULL,
  `currency` varchar(255) CHARACTER SET latin1 NOT NULL,
  `price` double NOT NULL,
  `class` varchar(255) CHARACTER SET latin1 NOT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `prices_idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK65FB149EF265472` (`event_id`),
  KEY `FK65FB14914F0FE6F` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `content` longtext,
  `isdiscount` bit(1) DEFAULT NULL,
  `isdownloadable` bit(1) DEFAULT NULL,
  `isnew` bit(1) DEFAULT NULL,
  `summary` longtext,
  `title` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_image` (
  `product_images_id` bigint(20) DEFAULT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  `images_idx` int(11) DEFAULT NULL,
  KEY `FK520D2A0BB776D63` (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_meta`
--

DROP TABLE IF EXISTS `product_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_meta` (
  `product_meta_id` bigint(20) DEFAULT NULL,
  `meta_id` bigint(20) DEFAULT NULL,
  `meta_idx` int(11) DEFAULT NULL,
  KEY `FK3C75BF553113F0A5` (`meta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `last_logged_in` datetime DEFAULT NULL,
  `nick_name` longtext CHARACTER SET latin1,
  `public_name` longtext CHARACTER SET latin1 NOT NULL,
  `last_updated` datetime DEFAULT NULL,
  `image` longblob NOT NULL,
  `date_created` datetime DEFAULT NULL,
  `mime_type` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publishable`
--

DROP TABLE IF EXISTS `publishable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publishable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `display_date` tinyint(1) DEFAULT NULL,
  `home` tinyint(1) NOT NULL,
  `date_published` datetime DEFAULT NULL,
  `display_author` tinyint(1) DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `featured` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `category` varchar(1) CHARACTER SET latin1 NOT NULL,
  `publish_state` varchar(21) CHARACTER SET latin1 NOT NULL,
  `date_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDB6312E991252A7E` (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quantity`
--

DROP TABLE IF EXISTS `quantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quantity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `shopping_cart_id` bigint(20) NOT NULL,
  `shopping_item_id` bigint(20) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB368648B29A10B8F` (`shopping_item_id`),
  KEY `FKB368648BCC86F46F` (`shopping_cart_id`),
  CONSTRAINT `FKB368648B29A10B8F` FOREIGN KEY (`shopping_item_id`) REFERENCES `shopping_item` (`id`),
  CONSTRAINT `FKB368648BCC86F46F` FOREIGN KEY (`shopping_cart_id`) REFERENCES `shopping_cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `summary` longtext,
  `for_hire` bit(1) DEFAULT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  `venue_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3580DBB776D63` (`image_id`),
  KEY `FK3580DB7B3A8B2E` (`venue_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedule_rule`
--

DROP TABLE IF EXISTS `schedule_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_rule` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `start_date` datetime NOT NULL,
  `interval` int(11) DEFAULT NULL,
  `end_date` datetime NOT NULL,
  `end_time` time NOT NULL,
  `modifier` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `start_time` time NOT NULL,
  `rule_type` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `duration` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `modifier_type` varchar(2) CHARACTER SET latin1 DEFAULT NULL,
  `is_rule` tinyint(1) NOT NULL,
  `class` varchar(255) CHARACTER SET latin1 NOT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `dates_idx` int(11) DEFAULT NULL,
  `the_interval` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK6400C3A4EF265472` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(12) CHARACTER SET latin1 NOT NULL,
  `value` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shiro_role`
--

DROP TABLE IF EXISTS `shiro_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiro_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shiro_role_permissions`
--

DROP TABLE IF EXISTS `shiro_role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiro_role_permissions` (
  `shiro_role_id` bigint(20) DEFAULT NULL,
  `permissions_string` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  KEY `FK389B46C981060810` (`shiro_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shiro_user`
--

DROP TABLE IF EXISTS `shiro_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiro_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `username` varchar(255) CHARACTER SET latin1 NOT NULL,
  `password_hash` varchar(255) CHARACTER SET latin1 NOT NULL,
  `password_reset` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FKE58845594AB89DE8` (`profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shiro_user_permissions`
--

DROP TABLE IF EXISTS `shiro_user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiro_user_permissions` (
  `shiro_user_id` bigint(20) NOT NULL DEFAULT '0',
  `permissions_string` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`shiro_user_id`),
  KEY `FK34555A9E2630CBF0` (`shiro_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shiro_user_roles`
--

DROP TABLE IF EXISTS `shiro_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiro_user_roles` (
  `shiro_user_id` bigint(20) NOT NULL,
  `shiro_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`shiro_user_id`,`shiro_role_id`),
  KEY `FKBA22105781060810` (`shiro_role_id`),
  KEY `FKBA2210572630CBF0` (`shiro_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shoppable`
--

DROP TABLE IF EXISTS `shoppable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `shopping_item_id` bigint(20) DEFAULT NULL,
  `class` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK83826C9429A10B8F` (`shopping_item_id`),
  CONSTRAINT `FK83826C9429A10B8F` FOREIGN KEY (`shopping_item_id`) REFERENCES `shopping_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `checked_out` bit(1) NOT NULL,
  `date_created` datetime NOT NULL,
  `lasturl` varchar(255) DEFAULT NULL,
  `last_updated` datetime NOT NULL,
  `sessionid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopping_cart_interface_test_product`
--

DROP TABLE IF EXISTS `shopping_cart_interface_test_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_cart_interface_test_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `shopping_item_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4847E93029A10B8F` (`shopping_item_id`),
  CONSTRAINT `FK4847E93029A10B8F` FOREIGN KEY (`shopping_item_id`) REFERENCES `shopping_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopping_cart_shopping_item`
--

DROP TABLE IF EXISTS `shopping_cart_shopping_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_cart_shopping_item` (
  `shopping_cart_items_id` bigint(20) DEFAULT NULL,
  `shopping_item_id` bigint(20) DEFAULT NULL,
  KEY `FKD02304E229A10B8F` (`shopping_item_id`),
  KEY `FKD02304E254937B4E` (`shopping_cart_items_id`),
  CONSTRAINT `FKD02304E229A10B8F` FOREIGN KEY (`shopping_item_id`) REFERENCES `shopping_item` (`id`),
  CONSTRAINT `FKD02304E254937B4E` FOREIGN KEY (`shopping_cart_items_id`) REFERENCES `shopping_cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopping_item`
--

DROP TABLE IF EXISTS `shopping_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_links`
--

DROP TABLE IF EXISTS `tag_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_links` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `tag_ref` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  `type` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7C35D6D45A3B441D` (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL,
  `summary` longtext,
  `image_id` bigint(20) DEFAULT NULL,
  `type` varchar(1) CHARACTER SET latin1 DEFAULT NULL,
  `content` longtext,
  `title` varchar(3) CHARACTER SET latin1 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKAA31CBE2B776D63` (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `telephone`
--

DROP TABLE IF EXISTS `telephone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telephone` (
  `id` bigint(20) NOT NULL,
  `number` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `type` varchar(6) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transport`
--

DROP TABLE IF EXISTS `transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `description` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ufile`
--

DROP TABLE IF EXISTS `ufile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ufile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `extension` varchar(255) CHARACTER SET latin1 NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `path` varchar(255) CHARACTER SET latin1 NOT NULL,
  `date_uploaded` datetime NOT NULL,
  `downloads` int(11) NOT NULL,
  `size` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue`
--

DROP TABLE IF EXISTS `venue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue` (
  `id` bigint(20) NOT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  `access` longtext,
  `content` longtext,
  `name` varchar(128) DEFAULT NULL,
  `facilities` longtext,
  `latitude` decimal(12,9) DEFAULT NULL,
  `longtitude` decimal(12,9) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FK6AE6A6FB776D63` (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_address`
--

DROP TABLE IF EXISTS `venue_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue_address` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_email`
--

DROP TABLE IF EXISTS `venue_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue_email` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_room`
--

DROP TABLE IF EXISTS `venue_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue_room` (
  `venue_rooms_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  KEY `FK9A9DFCAB7B485186` (`room_id`),
  KEY `FK9A9DFCABE524B5D5` (`venue_rooms_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_telephone`
--

DROP TABLE IF EXISTS `venue_telephone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue_telephone` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_venue_address`
--

DROP TABLE IF EXISTS `venue_venue_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue_venue_address` (
  `venue_addresses_id` bigint(20) DEFAULT NULL,
  `venue_address_id` bigint(20) DEFAULT NULL,
  `addresses_idx` int(11) DEFAULT NULL,
  KEY `FKC0159C5420652547` (`venue_address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_venue_email`
--

DROP TABLE IF EXISTS `venue_venue_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue_venue_email` (
  `venue_emails_id` bigint(20) DEFAULT NULL,
  `venue_email_id` bigint(20) DEFAULT NULL,
  `emails_idx` int(11) DEFAULT NULL,
  KEY `FKB084E3FC1FB3C387` (`venue_email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_venue_telephone`
--

DROP TABLE IF EXISTS `venue_venue_telephone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue_venue_telephone` (
  `venue_telephone_numbers_id` bigint(20) DEFAULT NULL,
  `venue_telephone_id` bigint(20) DEFAULT NULL,
  `telephone_numbers_idx` int(11) DEFAULT NULL,
  KEY `FKBAE1C642EB9C07` (`venue_telephone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-07-17 22:09:39
