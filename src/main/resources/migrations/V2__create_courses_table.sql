CREATE TABLE IF NOT EXISTS `courses` (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `code` VARCHAR(10) NOT NULL UNIQUE,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(100),
    `status` VARCHAR(7) NOT NULL,
    `creation_date` DATE,
    `inactive_date` DATE,
    `instructor_id` INTEGER NOT NULL
);