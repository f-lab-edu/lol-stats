CREATE TABLE IF NOT EXISTS `users`
(
    `seq`            BIGINT      NOT NULL AUTO_INCREMENT,
    `id`             BINARY(16)  PRIMARY KEY,
    `password`       VARCHAR(100) NOT NULL,
    `role`           ENUM('ADMIN','USER') NOT NULL,
    `nickname`       VARCHAR(64) NOT NULL,
    `email`          VARCHAR(64) NOT NULL,
    `phone_number`          VARCHAR(255) NOT NULL,
    `zip_code`       VARCHAR(255) NULL DEFAULT NULL,
    `street_adr`     VARCHAR(255) NULL DEFAULT NULL,
    `detail_adr`     VARCHAR(255) NULL DEFAULT NULL,
    `deleted`        BOOLEAN     DEFAULT FALSE,
    `created_at`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `version`        BIGINT      NOT NULL,
    `salt`           BINARY(16)  NOT NULL,

    CONSTRAINT `uk_users_seq`      UNIQUE (`seq`),
    CONSTRAINT `uk_users_email`    UNIQUE (`email`),
    CONSTRAINT `uk_users_phone_number`    UNIQUE (`phone_number`),
    CONSTRAINT `uk_users_identity` UNIQUE (`nickname`, `email`, `phone_number`)
);
