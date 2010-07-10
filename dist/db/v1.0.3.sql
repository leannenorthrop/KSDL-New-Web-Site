INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (4,'file:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (4,'fileuploader:*');
insert into `setting` values(5, 0, 'Logo', '1');

DROP TABLE IF EXISTS `venue_address`;
DROP TABLE IF EXISTS `venue_email`;
DROP TABLE IF EXISTS `venue_room`;
DROP TABLE IF EXISTS `venue_telephone`;

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
`id` bigint(20) NOT NULL,
`summary` longtext,
`for_hire` bit(1) DEFAULT NULL,
`image_id` bigint(20) DEFAULT NULL,
`venue_id` bigint(20) DEFAULT NULL,
`content` longtext,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `FK3580DBB776D63` (`image_id`),
KEY `FK3580DB7B3A8B2E` (`venue_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `venue`;
CREATE TABLE `venue` (
`id` bigint(20) NOT NULL,
`image_id` bigint(20) DEFAULT NULL,
`access` longtext,
`content` longtext,
`name` varchar(128) DEFAULT NULL,
`facilities` longtext,
`latitude` decimal(12,9) DEFAULT NULL,
`longtitude` decimal(12,9) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `name` (`name`),
KEY `FK6AE6A6FB776D63` (`image_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
insert into venue values(1, null, '', '', 'Manor Place', '',51.488614,-0.098543);
insert into venue values(2, null, '', '', 'Spa Road', '', 51.495448,-0.074664);
