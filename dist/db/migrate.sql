-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations/changelog.xml
-- Ran at: 3/6/10 10:58 PM
-- Against: londonsamyedzong@jdbc:mysql://localhost:3306/londonsamyedzong
-- LiquiBase version: 1.9.5
-- *********************************************************************

-- Create Database Lock Table
CREATE TABLE `DATABASECHANGELOGLOCK` (`ID` INT NOT NULL, `LOCKED` TINYINT(1) NOT NULL, `LOCKGRANTED` DATETIME, `LOCKEDBY` VARCHAR(255), CONSTRAINT `PK_DATABASECHANGELOGLOCK` PRIMARY KEY (`ID`));

INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`) VALUES (1, 0);

SELECT LOCKED FROM `DATABASECHANGELOGLOCK` WHERE `ID`=1;

-- Lock Database
UPDATE `DATABASECHANGELOGLOCK` SET `LOCKEDBY` = '172.16.56.1 (172.16.56.1)', `LOCKGRANTED` = '2010-03-06 22:58:17.399', `LOCKED` = 1 WHERE ID  = 1;

-- Create Database Change Log Table
CREATE TABLE `DATABASECHANGELOG` (`ID` VARCHAR(63) NOT NULL, `AUTHOR` VARCHAR(63) NOT NULL, `FILENAME` VARCHAR(200) NOT NULL, `DATEEXECUTED` DATETIME NOT NULL, `MD5SUM` VARCHAR(32), `DESCRIPTION` VARCHAR(255), `COMMENTS` VARCHAR(255), `TAG` VARCHAR(255), `LIQUIBASE` VARCHAR(10), CONSTRAINT `PK_DATABASECHANGELOG` PRIMARY KEY (`ID`, `AUTHOR`, `FILENAME`));

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-1::(MD5Sum: b71cd12a5a55d3b871764bc31aca91c)
CREATE TABLE `address` (`id` BIGINT NOT NULL, `post_code` VARCHAR(10), `place_name` TEXT, `type` VARCHAR(5), `country` VARCHAR(108), `county` VARCHAR(25), `street_number` INT, `line1` TEXT, `post_town` VARCHAR(255), `line2` TEXT, CONSTRAINT `PK_ADDRESS` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-1', '1.9.5', 'Create Table', '', 'b71cd12a5a55d3b871764bc31aca91c', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-2::(MD5Sum: 6a769ea2ddab9a6be7675d7a96b6321d)
CREATE TABLE `article` (`id` BIGINT NOT NULL, `summary` LONGTEXT, `image_id` BIGINT, `content` LONGTEXT, `title` VARCHAR(255), CONSTRAINT `PK_ARTICLE` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-2', '1.9.5', 'Create Table', '', '6a769ea2ddab9a6be7675d7a96b6321d', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-3::(MD5Sum: a8d51f95506d71d9f2a8d16ea7785e)
CREATE TABLE `audit_log` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `property_name` VARCHAR(255), `last_updated` DATETIME NOT NULL, `old_value` VARCHAR(255), `actor` VARCHAR(255), `uri` VARCHAR(255), `new_value` VARCHAR(255), `persisted_object_version` BIGINT, `date_created` DATETIME NOT NULL, `class_name` VARCHAR(255), `event_name` VARCHAR(255), `persisted_object_id` BIGINT, CONSTRAINT `PK_AUDIT_LOG` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-3', '1.9.5', 'Create Table', '', 'a8d51f95506d71d9f2a8d16ea7785e', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-4::(MD5Sum: 22f9baee652d9e60656e23b1fefca357)
CREATE TABLE `contact` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `name` VARCHAR(255) NOT NULL, CONSTRAINT `PK_CONTACT` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-4', '1.9.5', 'Create Table', '', '22f9baee652d9e60656e23b1fefca357', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-5::(MD5Sum: 6ff8e87d767f9fd692d335a27487e9)
CREATE TABLE `email` (`id` BIGINT NOT NULL, `address` VARCHAR(255), `type` VARCHAR(5), CONSTRAINT `PK_EMAIL` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-5', '1.9.5', 'Create Table', '', '6ff8e87d767f9fd692d335a27487e9', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-6::(MD5Sum: b4b5f670deb68ee889a22682806fb529)
CREATE TABLE `event` (`id` BIGINT NOT NULL, `summary` LONGTEXT, `title` VARCHAR(254), `leader_id` BIGINT, `is_repeatable` TINYINT(1), `organizer_id` BIGINT, `image_id` BIGINT, `venue_id` BIGINT, `content` LONGTEXT, CONSTRAINT `PK_EVENT` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-6', '1.9.5', 'Create Table', '', 'b4b5f670deb68ee889a22682806fb529', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-7::(MD5Sum: 44389bb71bfcce54b3e888219571378)
CREATE TABLE `image` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `thumbnail` LONGBLOB NOT NULL, `name` VARCHAR(255) NOT NULL, `image` LONGBLOB NOT NULL, `mime_type` VARCHAR(255) NOT NULL, CONSTRAINT `PK_IMAGE` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-7', '1.9.5', 'Create Table', '', '44389bb71bfcce54b3e888219571378', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-51::(MD5Sum: 3a1990148b1417e3d2f87fbc5efbc077)
CREATE TABLE `pending_email_confirmation` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `timestamp` DATETIME NOT NULL, `user_token` TEXT, `email_address` VARCHAR(80) NOT NULL, `confirmation_token` VARCHAR(80) NOT NULL, CONSTRAINT `SYS_IDX_48` PRIMARY KEY (`ID`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-51', '1.9.5', 'Create Table', '', '3a1990148b1417e3d2f87fbc5efbc077', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-8::(MD5Sum: 99ac2425e88af761de6921479ef76ed4)
CREATE TABLE `price` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `category` VARCHAR(1) NOT NULL, `price` DOUBLE NOT NULL, `currency` VARCHAR(255) NOT NULL, `class` VARCHAR(255) NOT NULL, `event_id` BIGINT, `prices_idx` INT, CONSTRAINT `PK_PRICE` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-8', '1.9.5', 'Create Table', '', '99ac2425e88af761de6921479ef76ed4', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-9::(MD5Sum: e71bd05ec5bb2ef168fbe6f6e5941ff)
CREATE TABLE `publishable` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `display_date` TINYINT(1), `home` TINYINT(1) NOT NULL, `date_published` DATETIME, `display_author` TINYINT(1), `last_updated` DATETIME, `featured` TINYINT(1) NOT NULL, `deleted` TINYINT(1) NOT NULL, `author_id` BIGINT, `category` VARCHAR(1) NOT NULL, `publish_state` VARCHAR(21) NOT NULL, `date_created` DATETIME, CONSTRAINT `PK_PUBLISHABLE` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-9', '1.9.5', 'Create Table', '', 'e71bd05ec5bb2ef168fbe6f6e5941ff', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-10::(MD5Sum: ad52ec56abdb6146e5b35cc9525b16e8)
CREATE TABLE `room` (`id` BIGINT NOT NULL, `image_id` BIGINT, `description` VARCHAR(255), `name` VARCHAR(255), CONSTRAINT `PK_ROOM` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-10', '1.9.5', 'Create Table', '', 'ad52ec56abdb6146e5b35cc9525b16e8', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-52::(MD5Sum: 1afa409f36a5e48f8963d699a5205f69)
CREATE TABLE `schedule_rule` (`ID` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `start_date` DATETIME NOT NULL, `the_interval` INT, `end_date` DATETIME NOT NULL, `end_time` TIME NOT NULL, `modifier` VARCHAR(255), `start_time` TIME NOT NULL, `rule_type` VARCHAR(255), `duration` VARCHAR(255), `modifier_type` VARCHAR(2), `is_rule` TINYINT(1) NOT NULL, `class` VARCHAR(255) NOT NULL, `event_id` BIGINT, `dates_idx` INT, CONSTRAINT `SYS_IDX_165` PRIMARY KEY (`ID`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-52', '1.9.5', 'Create Table', '', '1afa409f36a5e48f8963d699a5205f69', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-11::(MD5Sum: d343283e8877e9f33292848a5cb89b0)
CREATE TABLE `shiro_role` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `name` VARCHAR(255) NOT NULL, CONSTRAINT `PK_SHIRO_ROLE` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-11', '1.9.5', 'Create Table', '', 'd343283e8877e9f33292848a5cb89b0', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-12::(MD5Sum: cde77e39d7b51cf27ecc72ba6d0e49)
CREATE TABLE `shiro_role_permissions` (`shiro_role_id` BIGINT, `permissions_string` VARCHAR(255));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-12', '1.9.5', 'Create Table', '', 'cde77e39d7b51cf27ecc72ba6d0e49', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-13::(MD5Sum: f86cfe42934d4b07be1702046f43760)
CREATE TABLE `shiro_user` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `username` VARCHAR(255) NOT NULL, `password_hash` VARCHAR(255) NOT NULL, `password_reset` VARCHAR(255), CONSTRAINT `PK_SHIRO_USER` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-13', '1.9.5', 'Create Table', '', 'f86cfe42934d4b07be1702046f43760', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-14::(MD5Sum: c3442cd6a133143d2e18bf756d5c8)
CREATE TABLE `shiro_user_permissions` (`shiro_user_id` BIGINT, `permissions_string` VARCHAR(255));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-14', '1.9.5', 'Create Table', '', 'c3442cd6a133143d2e18bf756d5c8', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-15::(MD5Sum: 68bde63d581c5dc630c0aaaea7f952de)
CREATE TABLE `shiro_user_roles` (`shiro_user_id` BIGINT NOT NULL, `shiro_role_id` BIGINT NOT NULL);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-15', '1.9.5', 'Create Table', '', '68bde63d581c5dc630c0aaaea7f952de', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-16::(MD5Sum: 2c84d9eb766e67f19c441a02fdf213d)
CREATE TABLE `tag_links` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `tag_ref` BIGINT NOT NULL, `tag_id` BIGINT NOT NULL, `type` VARCHAR(255) NOT NULL, CONSTRAINT `PK_TAG_LINKS` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-16', '1.9.5', 'Create Table', '', '2c84d9eb766e67f19c441a02fdf213d', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-17::(MD5Sum: 89315f2efadaaba5731e76c93819d29b)
CREATE TABLE `tags` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `name` VARCHAR(255) NOT NULL, CONSTRAINT `PK_TAGS` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-17', '1.9.5', 'Create Table', '', '89315f2efadaaba5731e76c93819d29b', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-18::(MD5Sum: 164754e913a51a9f97f4677697d2934)
CREATE TABLE `teacher` (`id` BIGINT NOT NULL, `summary` LONGTEXT, `image_id` BIGINT, `type` VARCHAR(1), `content` LONGTEXT, `title` VARCHAR(3), `name` VARCHAR(255), CONSTRAINT `PK_TEACHER` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-18', '1.9.5', 'Create Table', '', '164754e913a51a9f97f4677697d2934', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-19::(MD5Sum: be73d9e83a7e17cb575872dac417983)
CREATE TABLE `telephone` (`id` BIGINT NOT NULL, `number` VARCHAR(255), `type` VARCHAR(6), CONSTRAINT `PK_TELEPHONE` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-19', '1.9.5', 'Create Table', '', 'be73d9e83a7e17cb575872dac417983', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-20::(MD5Sum: d0d9b338442f3d792589cebedc1b57b5)
CREATE TABLE `transport` (`id` BIGINT AUTO_INCREMENT  NOT NULL, `version` BIGINT NOT NULL, `description` VARCHAR(255) NOT NULL, CONSTRAINT `PK_TRANSPORT` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-20', '1.9.5', 'Create Table', '', 'd0d9b338442f3d792589cebedc1b57b5', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-53::(MD5Sum: 7eaafdb2d14874eb6a1fe9aa7388b61)
CREATE TABLE `venue` (`id` BIGINT NOT NULL, `image_id` BIGINT, `access` VARCHAR(255), `description` VARCHAR(32000), `name` VARCHAR(512), `facilities` VARCHAR(255), CONSTRAINT `SYS_IDX_200` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-53', '1.9.5', 'Create Table', '', '7eaafdb2d14874eb6a1fe9aa7388b61', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-21::(MD5Sum: 2df2cf799d0d2e196b259eab0d3612b)
CREATE TABLE `venue_address` (`id` BIGINT NOT NULL, CONSTRAINT `PK_VENUE_ADDRESS` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-21', '1.9.5', 'Create Table', '', '2df2cf799d0d2e196b259eab0d3612b', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-22::(MD5Sum: a7f2a0964c95ad74b855d484aa148de)
CREATE TABLE `venue_email` (`id` BIGINT NOT NULL, CONSTRAINT `PK_VENUE_EMAIL` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-22', '1.9.5', 'Create Table', '', 'a7f2a0964c95ad74b855d484aa148de', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-23::(MD5Sum: 7b82f6369d57ec2c1225c247379b68a)
CREATE TABLE `venue_room` (`venue_rooms_id` BIGINT, `room_id` BIGINT);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-23', '1.9.5', 'Create Table', '', '7b82f6369d57ec2c1225c247379b68a', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-24::(MD5Sum: 5e617565c5211a6b174af91156e85ba0)
CREATE TABLE `venue_telephone` (`id` BIGINT NOT NULL, CONSTRAINT `PK_VENUE_TELEPHONE` PRIMARY KEY (`id`));

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-24', '1.9.5', 'Create Table', '', '5e617565c5211a6b174af91156e85ba0', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-25::(MD5Sum: a24ee6f4376ea87466b9d1dc73d493)
ALTER TABLE `shiro_user_roles` ADD PRIMARY KEY (`shiro_user_id`, `shiro_role_id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-25', '1.9.5', 'Add Primary Key', '', 'a24ee6f4376ea87466b9d1dc73d493', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-26::(MD5Sum: 69804c78ca952e4503d62bbc52e)
CREATE UNIQUE INDEX `title` ON `article`(`title`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-26', '1.9.5', 'Create Index', '', '69804c78ca952e4503d62bbc52e', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-27::(MD5Sum: 45ec8c3ff0d5697fd77183376e613124)
CREATE UNIQUE INDEX `title` ON `event`(`title`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-27', '1.9.5', 'Create Index', '', '45ec8c3ff0d5697fd77183376e613124', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-28::(MD5Sum: a5962ddc49b94e3235974a7a80bc88b4)
CREATE UNIQUE INDEX `name` ON `image`(`name`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-28', '1.9.5', 'Create Index', '', 'a5962ddc49b94e3235974a7a80bc88b4', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-29::(MD5Sum: 6b7c35c97f69c82af77cf7159199d3f)
CREATE UNIQUE INDEX `name` ON `shiro_role`(`name`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-29', '1.9.5', 'Create Index', '', '6b7c35c97f69c82af77cf7159199d3f', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-30::(MD5Sum: f0798c3312fc2933aa8b8cf8c9122)
CREATE UNIQUE INDEX `username` ON `shiro_user`(`username`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-30', '1.9.5', 'Create Index', '', 'f0798c3312fc2933aa8b8cf8c9122', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-31::(MD5Sum: 6cd6e48fa8353df06912645ad1b9d843)
CREATE UNIQUE INDEX `name` ON `tags`(`name`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-31', '1.9.5', 'Create Index', '', '6cd6e48fa8353df06912645ad1b9d843', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-32::(MD5Sum: 22d970fed098cdabf12dfb1754faf8a)
CREATE UNIQUE INDEX `name` ON `teacher`(`name`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-32', '1.9.5', 'Create Index', '', '22d970fed098cdabf12dfb1754faf8a', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-33::(MD5Sum: d0ae955347308ca05664fda98b65ef26)
ALTER TABLE `article` ADD CONSTRAINT `FKD458CCF6B776D63` FOREIGN KEY (`image_id`) REFERENCES `image`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-33', '1.9.5', 'Add Foreign Key Constraint', '', 'd0ae955347308ca05664fda98b65ef26', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-34::(MD5Sum: db5edad27ad8c4e973467bf35b8e14)
ALTER TABLE `event` ADD CONSTRAINT `FK5C6729AB776D63` FOREIGN KEY (`image_id`) REFERENCES `image`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-34', '1.9.5', 'Add Foreign Key Constraint', '', 'db5edad27ad8c4e973467bf35b8e14', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-35::(MD5Sum: 8b867e7b25fd2da36d4d7477daf8983d)
ALTER TABLE `event` ADD CONSTRAINT `FK5C6729A8D6FE8C1` FOREIGN KEY (`leader_id`) REFERENCES `teacher`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-35', '1.9.5', 'Add Foreign Key Constraint', '', '8b867e7b25fd2da36d4d7477daf8983d', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-36::(MD5Sum: 148eb1779538d28b72bbd228c62ed6f5)
ALTER TABLE `event` ADD CONSTRAINT `FK5C6729A7FAD865A` FOREIGN KEY (`organizer_id`) REFERENCES `shiro_user`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-36', '1.9.5', 'Add Foreign Key Constraint', '', '148eb1779538d28b72bbd228c62ed6f5', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-37::(MD5Sum: 61d23d160d3b7d4f3fc448968fccf7f)
ALTER TABLE `event` ADD CONSTRAINT `FK5C6729A7B3A8B2E` FOREIGN KEY (`venue_id`) REFERENCES `venue`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-37', '1.9.5', 'Add Foreign Key Constraint', '', '61d23d160d3b7d4f3fc448968fccf7f', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-38::(MD5Sum: dce5eabe0456029953cd98a4df0b2d)
ALTER TABLE `price` ADD CONSTRAINT `FK65FB149EF265472` FOREIGN KEY (`event_id`) REFERENCES `event`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-38', '1.9.5', 'Add Foreign Key Constraint', '', 'dce5eabe0456029953cd98a4df0b2d', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-39::(MD5Sum: 4df9925cc1d121926c01cb6897d6f9b)
ALTER TABLE `publishable` ADD CONSTRAINT `FKDB6312E991252A7E` FOREIGN KEY (`author_id`) REFERENCES `shiro_user`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-39', '1.9.5', 'Add Foreign Key Constraint', '', '4df9925cc1d121926c01cb6897d6f9b', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-40::(MD5Sum: 7c3264e7a9c61c50f7f738a6313366b2)
ALTER TABLE `room` ADD CONSTRAINT `FK3580DBB776D63` FOREIGN KEY (`image_id`) REFERENCES `image`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-40', '1.9.5', 'Add Foreign Key Constraint', '', '7c3264e7a9c61c50f7f738a6313366b2', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-41::(MD5Sum: 7aa559c52d453556da771f50eeb4ebdc)
ALTER TABLE `schedule_rule` ADD CONSTRAINT `FK6400C3A4EF265472` FOREIGN KEY (`event_id`) REFERENCES `event`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-41', '1.9.5', 'Add Foreign Key Constraint', '', '7aa559c52d453556da771f50eeb4ebdc', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-42::(MD5Sum: 7b00a28ca0ca1be09711f7a68c27)
ALTER TABLE `shiro_role_permissions` ADD CONSTRAINT `FK389B46C981060810` FOREIGN KEY (`shiro_role_id`) REFERENCES `shiro_role`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-42', '1.9.5', 'Add Foreign Key Constraint', '', '7b00a28ca0ca1be09711f7a68c27', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-43::(MD5Sum: 9510f4831c84ca7bc62e8fa9c479affe)
ALTER TABLE `shiro_user_permissions` ADD CONSTRAINT `FK34555A9E2630CBF0` FOREIGN KEY (`shiro_user_id`) REFERENCES `shiro_user`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-43', '1.9.5', 'Add Foreign Key Constraint', '', '9510f4831c84ca7bc62e8fa9c479affe', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-44::(MD5Sum: ad82657452da480609fa6c4e7762db0)
ALTER TABLE `shiro_user_roles` ADD CONSTRAINT `FKBA22105781060810` FOREIGN KEY (`shiro_role_id`) REFERENCES `shiro_role`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-44', '1.9.5', 'Add Foreign Key Constraint', '', 'ad82657452da480609fa6c4e7762db0', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-45::(MD5Sum: 2e5f4a7fe28376f75b51357e2446045)
ALTER TABLE `shiro_user_roles` ADD CONSTRAINT `FKBA2210572630CBF0` FOREIGN KEY (`shiro_user_id`) REFERENCES `shiro_user`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-45', '1.9.5', 'Add Foreign Key Constraint', '', '2e5f4a7fe28376f75b51357e2446045', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-46::(MD5Sum: cf0caa9af8c3a274b5410efd18b93fb)
ALTER TABLE `tag_links` ADD CONSTRAINT `FK7C35D6D45A3B441D` FOREIGN KEY (`tag_id`) REFERENCES `tags`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-46', '1.9.5', 'Add Foreign Key Constraint', '', 'cf0caa9af8c3a274b5410efd18b93fb', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-47::(MD5Sum: 2f1ee2603a50e183d2883f4e742949af)
ALTER TABLE `teacher` ADD CONSTRAINT `FKAA31CBE2B776D63` FOREIGN KEY (`image_id`) REFERENCES `image`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-47', '1.9.5', 'Add Foreign Key Constraint', '', '2f1ee2603a50e183d2883f4e742949af', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-48::(MD5Sum: c797cb58848fd228fbcfa4135181cef)
ALTER TABLE `venue` ADD CONSTRAINT `FK6AE6A6FB776D63` FOREIGN KEY (`image_id`) REFERENCES `image`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-48', '1.9.5', 'Add Foreign Key Constraint', '', 'c797cb58848fd228fbcfa4135181cef', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-49::(MD5Sum: 9843d4cf0686fdacd51666b15d8274)
ALTER TABLE `venue_room` ADD CONSTRAINT `FK9A9DFCAB7B485186` FOREIGN KEY (`room_id`) REFERENCES `room`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-49', '1.9.5', 'Add Foreign Key Constraint', '', '9843d4cf0686fdacd51666b15d8274', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml::Leanne Northrop::v001-schema-50::(MD5Sum: a266ee27ce89d2416f2cbbd24554bb55)
ALTER TABLE `venue_room` ADD CONSTRAINT `FK9A9DFCABE524B5D5` FOREIGN KEY (`venue_rooms_id`) REFERENCES `venue`(`id`);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'v001-schema-50', '1.9.5', 'Add Foreign Key Constraint', '', 'a266ee27ce89d2416f2cbbd24554bb55', 'Leanne Northrop', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-ddl.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml::v001-data-01::northrl::(MD5Sum: 49cbe84c8fcb3044e2a041a02a2fd365)
INSERT INTO `shiro_role` (`id`, `name`, `version`) VALUES (1, 'Admin', 1);

INSERT INTO `shiro_role` (`id`, `name`, `version`) VALUES (2, 'Administrator', 1);

INSERT INTO `shiro_role` (`id`, `name`, `version`) VALUES (3, 'Editor', 0);

INSERT INTO `shiro_role` (`id`, `name`, `version`) VALUES (4, 'Author', 0);

INSERT INTO `shiro_role` (`id`, `name`, `version`) VALUES (5, 'EventOrganiser', 1);

INSERT INTO `shiro_role` (`id`, `name`, `version`) VALUES (6, 'VenueManager', 0);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'northrl', '1.9.5', 'Load Data', '', '49cbe84c8fcb3044e2a041a02a2fd365', 'v001-data-01', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml::v001-data-02::northrl::(MD5Sum: ac8cc29dec6576be52188e93c8ca3ff)
INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('*:*', '1');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('admin:*', '2');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:*', '2');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('auth:*', '2');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:home', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('article:*', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('teacher:*', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('image:*', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('article:*', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:home', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('teacher:*', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:home', '5');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('event:*', '5');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:home', '6');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('room:*', '6');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('venue:*', '6');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('event:*', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:error', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:info', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:textile', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:preview', '3');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:error', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:info', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:textile', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:preview', '4');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:error', '5');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:info', '5');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:textile', '5');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:preview', '5');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:error', '6');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:info', '6');

INSERT INTO `shiro_role_permissions` (`permissions_string`, `SHIRO_ROLE_ID`) VALUES ('manageSite:preview', '6');

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'northrl', '1.9.5', 'Load Data', '', 'ac8cc29dec6576be52188e93c8ca3ff', 'v001-data-02', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml::v001-data-03::northrl::(MD5Sum: b820b54defb9888b55fb7403deeb8)
INSERT INTO `shiro_user` (`id`, `username`, `password_hash`, `version`) VALUES (1, 'leanne.northrop@googlemail.com', 'f86c09c1159dc2082ee27f7aef08f1ed6a5be03d', 0);

INSERT INTO `shiro_user` (`id`, `username`, `password_hash`, `version`) VALUES (2, 'site@londonsamyedzong.org', 'f86c09c1159dc2082ee27f7aef08f1ed6a5be03d', 0);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'northrl', '1.9.5', 'Load Data', '', 'b820b54defb9888b55fb7403deeb8', 'v001-data-03', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml::v001-data-04::northrl::(MD5Sum: e53e6a9cf042b390d385f949987d33)
INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'northrl', '1.9.5', 'Load Data', '', 'e53e6a9cf042b390d385f949987d33', 'v001-data-04', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml::v001-data-05::northrl::(MD5Sum: 51e07644451d817f57f2d3c2243efdfb)
INSERT INTO `shiro_user_roles` (`shiro_user_id`, `shiro_role_id`) VALUES (1, 1);

INSERT INTO `shiro_user_roles` (`shiro_user_id`, `shiro_role_id`) VALUES (2, 2);

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'northrl', '1.9.5', 'Load Data', '', '51e07644451d817f57f2d3c2243efdfb', 'v001-data-05', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml');

-- Changeset /Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml::v001-data-06::northrl::(MD5Sum: 96e8d0271365bd0fcead41f1413b90)
INSERT INTO `publishable` (`home`, `display_date`, `featured`, `date_created`, `display_author`, `deleted`, `version`, `id`, `category`, `last_updated`, `author_id`, `date_published`, `publish_state`) VALUES (0, 0, 0, '2010-01-15 15:46:55.576', 0, 0, 1, 1, 'V', '2010-02-18 15:15:19.615', 2, NULL, 'Unpublished');

INSERT INTO `publishable` (`home`, `display_date`, `featured`, `date_created`, `display_author`, `deleted`, `version`, `id`, `category`, `last_updated`, `author_id`, `date_published`, `publish_state`) VALUES (0, 0, 0, '2010-01-15 15:46:55.576', 0, 0, 1, 2, 'V', '2010-02-18 15:15:19.615', 2, NULL, 'Unpublished');

insert into venue values(1, null, '', '', 'Manor Place', '');

            insert into venue values(2, null, '', '', 'Spa Road', '');

INSERT INTO `DATABASECHANGELOG` (`DATEEXECUTED`, `AUTHOR`, `LIQUIBASE`, `DESCRIPTION`, `COMMENTS`, `MD5SUM`, `ID`, `FILENAME`) VALUES (NOW(), 'northrl', '1.9.5', 'Load Data, Custom SQL', '', '96e8d0271365bd0fcead41f1413b90', 'v001-data-06', '/Users/northrl/Documents/LSD/dev/lsd/website/main/grails-app/migrations//v001/v001-data.xml');

-- Release Database Lock
UPDATE `DATABASECHANGELOGLOCK` SET `LOCKEDBY` = NULL, `LOCKGRANTED` = NULL, `LOCKED` = 0 WHERE  ID = 1;

-- Release Database Lock
UPDATE `DATABASECHANGELOGLOCK` SET `LOCKEDBY` = NULL, `LOCKGRANTED` = NULL, `LOCKED` = 0 WHERE  ID = 1;

