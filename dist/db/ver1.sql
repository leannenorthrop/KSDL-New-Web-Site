INSERT INTO `londonsamyedzong`.`shiro_role_permissions` (shiro_role_id,permissions_string) VALUES (2,'fileUploader:*');
ALTER TABLE `image` ADD date_created DATETIME;
ALTER TABLE `image` ADD last_updated DATETIME;