DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `summary` longtext,
  `image_id` bigint(20) DEFAULT NULL,
  `venue_id` bigint(20) DEFAULT NULL,
  `content` longtext,
  `name` varchar(255) DEFAULT NULL,
  `for_hire` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3580DBB776D63` (`image_id`),
  KEY `FK3580DB7B3A8B2E` (`venue_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `venue`;
CREATE TABLE `venue` (
  `id` bigint(20) NOT NULL,
  `image_id` bigint(20) DEFAULT NULL,
  `access` longtext,
  `description` longtext,
  `name` varchar(128) DEFAULT NULL,
  `facilities` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FK6AE6A6FB776D63` (`image_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
insert into venue values(1, null, '', '', 'Manor Place', '');
insert into venue values(2, null, '', '', 'Spa Road', '');