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
  `name` varchar(255) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `menu_categories_idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3F1D73DE14F0FE6F` (`product_id`),
  CONSTRAINT `FK3F1D73DE14F0FE6F` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);
ALTER TABLE `menu_category` ENGINE=InnoDB;
ALTER TABLE `menu_category` CHARACTER SET utf8;

--
-- Table structure for table `download`
--

DROP TABLE IF EXISTS `download`;
CREATE TABLE `download` (
  `id` bigint(20) NOT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK551AC888D24E14D9` (`file_id`),
  CONSTRAINT `FK551AC888D24E14D9` FOREIGN KEY (`file_id`) REFERENCES `ufile` (`id`)
);
ALTER TABLE `download` ENGINE=InnoDB;
ALTER TABLE `download` CHARACTER SET utf8;

--
-- Table structure for table `meta`
--

DROP TABLE IF EXISTS `meta`;
CREATE TABLE `meta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `type` varchar(254) NOT NULL,
  `value` varchar(254) NOT NULL,  
  PRIMARY KEY (`id`)
);
ALTER TABLE `meta` ENGINE=InnoDB;
ALTER TABLE `meta` CHARACTER SET utf8;


--
-- Table structure for table `non_downloadable`
--

DROP TABLE IF EXISTS `non_downloadable`;
CREATE TABLE `non_downloadable` (
  `id` bigint(20) NOT NULL,
  `depth` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
ALTER TABLE `non_downloadable` ENGINE=InnoDB;
ALTER TABLE `non_downloadable` CHARACTER SET utf8;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `content` longtext,
  `is_discount` bit(1) DEFAULT NULL,
  `is_new` bit(1) DEFAULT NULL,
  `summary` longtext,
  `title` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
);
ALTER TABLE `product` ENGINE=InnoDB;
ALTER TABLE `product` CHARACTER SET utf8;
ALTER TABLE product MODIFY content longtext CHARACTER SET utf8;
ALTER TABLE product MODIFY summary longtext CHARACTER SET utf8;


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
  KEY `FK520D2A0BB776D63` (`image_id`),
  CONSTRAINT `FK520D2A0BB776D63` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
);
ALTER TABLE `product_image` ENGINE=InnoDB;
ALTER TABLE `product_image` CHARACTER SET utf8;

--
-- Table structure for table `product_meta`
--

DROP TABLE IF EXISTS `product_meta`;
CREATE TABLE `product_meta` (
  `product_meta_id` bigint(20) DEFAULT NULL,
  `meta_id` bigint(20) DEFAULT NULL,
  `meta_idx` int(11) DEFAULT NULL,
  KEY `FK3C75BF553113F0A5` (`meta_id`),
  CONSTRAINT `FK3C75BF553113F0A5` FOREIGN KEY (`meta_id`) REFERENCES `meta` (`id`)
);
ALTER TABLE `product_meta` ENGINE=InnoDB;
ALTER TABLE `product_meta` CHARACTER SET utf8;

DROP TABLE IF EXISTS `price`;
CREATE TABLE `price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `category` varchar(1) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `class` varchar(255) NOT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `prices_idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK65FB149EF265472` (`event_id`),
  KEY `FK65FB14914F0FE6F` (`product_id`),
  CONSTRAINT `FK65FB14914F0FE6F` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK65FB149EF265472` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
);
ALTER TABLE `price` ENGINE=InnoDB;
ALTER TABLE `price` CHARACTER SET utf8;

INSERT INTO `shiro_role` (`id`, `name`, `version`) VALUES (7, 'ShopManager', 0);
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'image:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'shop:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'article:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'manageSite:home');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'manageSite:error');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'manageSite:info');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'manageSite:preview');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'manageSite:textile');

INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (1,'slideshow:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'slideshow:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (3,'slideshow:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (4,'slideshow:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (5,'slideshow:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (6,'slideshow:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'slideshow:*');