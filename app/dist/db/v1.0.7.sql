INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (1,'links:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'links:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (3,'links:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (4,'links:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (5,'links:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (6,'links:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (7,'links:*');

DROP TABLE IF EXISTS `link`;

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;