CREATE TABLE IF NOT EXISTS `rates` (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `user_id` INTEGER NOT NULL,
    `course_id` INTEGER NOT NULL,
    `rate` DOUBLE NOT NULL,
    `reason` TEXT(50)
);