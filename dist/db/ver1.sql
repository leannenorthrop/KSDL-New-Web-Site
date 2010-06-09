-- Create Settings Table
CREATE TABLE `setting` (`id` BIGINT NOT NULL, `version` BIGINT NOT NULL, `name` VARCHAR(32) NOT NULL, `value` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`));
INSERT INTO `setting` (id, version, name, value) VALUES (1, 0, 'DefaultTheme', 'Default');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'fileUploader:*');
INSERT INTO `shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'theme:*');
ALTER TABLE `image` ADD date_created DATETIME;
ALTER TABLE `image` ADD last_updated DATETIME;