INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'fileUploader:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'theme:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (1,'profile:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'profile:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (3,'profile:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (4,'profile:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (5,'profile:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (6,'profile:*');

ALTER TABLE `image` ADD date_created DATETIME;
ALTER TABLE `image` ADD last_updated DATETIME;
update image set last_updated = curdate() where last_updated IS NULL;
update image set date_created = curdate() where date_created IS NULL;
	
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `last_logged_in` datetime NULL,
  `nick_name` longtext,
  `public_name` longtext NOT NULL,
  `last_updated` datetime NULL,
  `image` longblob NOT NULL,
  `date_created` datetime NULL,
  `mime_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE `shiro_user` ADD `profile_id` bigint(20) DEFAULT NULL;

DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(12) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
INSERT INTO `setting` (id, version, name, value) VALUES (1, 0, 'DefaultTheme', 'Default');
