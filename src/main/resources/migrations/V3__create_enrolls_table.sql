CREATE TABLE IF NOT EXISTS `enrolls` (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `user_id` INTEGER NOT NULL,
    `course_id` INTEGER NOT NULL,
    `enroll_date` DATE NOT NULL
);