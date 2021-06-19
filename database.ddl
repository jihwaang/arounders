/*MEMBER*/
CREATE TABLE `MEMBER` (
                          `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                          `EMAIL` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'MEMBER EMAIL' COLLATE 'utf8mb4_0900_ai_ci',
                          `PASSWORD` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'MEMBER PASSWORD' COLLATE 'utf8mb4_0900_ai_ci',
                          `NICKNAME` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'MEMBER NICKNAME' COLLATE 'utf8mb4_0900_ai_ci',
                          `PHONE` VARCHAR(13) NOT NULL DEFAULT '' COMMENT 'MEMBER PHONE' COLLATE 'utf8mb4_0900_ai_ci',
                          `ADDR` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'MEMBER ADDRESS' COLLATE 'utf8mb4_0900_ai_ci',
                          `ADDR_DTL` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'MEMBER ADDRESS DETAIL' COLLATE 'utf8mb4_0900_ai_ci',
                          `CREATED_AT` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'MEMBER CREATED AT',
                          `DROPED_AT` TIMESTAMP NULL DEFAULT NULL COMMENT 'MEMBER DROPED AT',
                          `LAST_LOGIN_AT` TIMESTAMP NULL DEFAULT NULL COMMENT 'MEMBER LAST LOGIN AT',
                          `ROLE_ID` INT(10,0) NOT NULL DEFAULT '1' COMMENT 'MEMBER ROLE ID (0 : ADMIN, 1 : USER)',
                          PRIMARY KEY (`ID`) USING BTREE,
                          INDEX `MEMBER_FK1` (`ROLE_ID`) USING BTREE,
                          CONSTRAINT `MEMBER_FK1` FOREIGN KEY (`ROLE_ID`) REFERENCES `arounders`.`ROLE` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='멤버'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=5
;

/*CATEGORY*/
CREATE TABLE `CATEGORY` (
                            `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                            `TITLE` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                            PRIMARY KEY (`ID`) USING BTREE
)
    COMMENT='카테고리'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=7
;

/*ATTACHMENT*/
CREATE TABLE `ATTACHMENT` (
                              `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                              `NAME` VARCHAR(50) NULL DEFAULT NULL COMMENT 'FILE NAME' COLLATE 'utf8_general_ci',
                              `PATH` VARCHAR(50) NULL DEFAULT NULL COMMENT 'FILE PATH IN SERVER' COLLATE 'utf8_general_ci',
                              `CREATED_AT` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'CREATED DATE',
                              `BOARD_ID` INT(10,0) NULL DEFAULT NULL COMMENT 'BOARD ID',
                              `MEMBER_ID` INT(10,0) NULL DEFAULT NULL COMMENT 'USER ID',
                              PRIMARY KEY (`ID`) USING BTREE,
                              INDEX `ATTACHMENT_FK1` (`BOARD_ID`) USING BTREE,
                              INDEX `ATTACHMENT_FK2` (`MEMBER_ID`) USING BTREE,
                              CONSTRAINT `ATTACHMENT_FK1` FOREIGN KEY (`BOARD_ID`) REFERENCES `arounders`.`BOARD` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION,
                              CONSTRAINT `ATTACHMENT_FK2` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='ATTACHMENT FILES'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

/*BOARD*/
CREATE TABLE `BOARD` (
                         `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                         `TITLE` VARCHAR(50) NULL DEFAULT NULL COMMENT 'TITLE' COLLATE 'utf8_general_ci',
                         `CONTENT` LONGTEXT NULL DEFAULT NULL COMMENT 'CONTENT OF THE BOARD' COLLATE 'utf8_general_ci',
                         `REGION` VARCHAR(50) NULL DEFAULT NULL COMMENT 'REGION OF THE BOARD' COLLATE 'utf8_general_ci',
                         `CREATED_AT` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'CREATED DATE ',
                         `UPDATED_AT` DATETIME NULL DEFAULT NULL COMMENT 'UPDATED DATE',
                         `IS_HIDDEN` INT(10,0) NULL DEFAULT '0' COMMENT '0 : NOT HIDDEN, 1: HIDDEN',
                         `THUMBNAIL_NAME` VARCHAR(50) NULL DEFAULT NULL COMMENT 'THUMBNAIL IMAGE FILE NAME' COLLATE 'utf8_general_ci',
                         `THUMBNAIL_PATH` VARCHAR(50) NULL DEFAULT NULL COMMENT 'THUMBNAIL IMAGE FILE PATH' COLLATE 'utf8_general_ci',
                         `MEMBER_ID` INT(10,0) NULL DEFAULT NULL COMMENT 'USER ID',
                         `CATEGORY_ID` INT(10,0) NULL DEFAULT NULL,
                         `STATUS` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '0: 미종료 1: 종료' COLLATE 'utf8_general_ci',
                         PRIMARY KEY (`ID`) USING BTREE,
                         INDEX `BOARD_FK1` (`MEMBER_ID`) USING BTREE,
                         INDEX `BOARD_FK2_idx` (`CATEGORY_ID`) USING BTREE,
                         CONSTRAINT `BOARD_FK1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION,
                         CONSTRAINT `BOARD_FK2` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `arounders`.`CATEGORY` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='BOARD'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=7
;

/*COMMENT*/
CREATE TABLE `COMMENT` (
                           `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                           `CONTENT` VARCHAR(200) NULL DEFAULT NULL COMMENT 'COMMENT CONTENT' COLLATE 'utf8mb4_0900_ai_ci',
                           `IS_HIDDEN` INT(10,0) NULL DEFAULT '0' COMMENT 'COMMENT IS HIDDEN',
                           `UPPER_ID` INT(10,0) NULL DEFAULT '0' COMMENT 'COMMENT UPPER ID',
                           `UPDATED_AT` TIMESTAMP NULL DEFAULT NULL COMMENT 'COMMENT UPDATED AT',
                           `CREATED_AT` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'COMMENT CREATED AT',
                           `MEMBER_ID` INT(10,0) NOT NULL DEFAULT '0' COMMENT 'COMMENT MEMBER ID',
                           `BOARD_ID` INT(10,0) NOT NULL DEFAULT '0' COMMENT 'COMMENT BOARD ID',
                           PRIMARY KEY (`ID`) USING BTREE,
                           INDEX `COMMENT_FK1` (`MEMBER_ID`) USING BTREE,
                           INDEX `COMMENT_FK2` (`BOARD_ID`) USING BTREE,
                           INDEX `COMMENT_FK3` (`UPPER_ID`) USING BTREE,
                           CONSTRAINT `COMMENT_FK1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION,
                           CONSTRAINT `COMMENT_FK2` FOREIGN KEY (`BOARD_ID`) REFERENCES `arounders`.`BOARD` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='COMMENT'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=10
;

/*REPORT*/
CREATE TABLE `REPORT` (
                          `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                          `IS_FINISHED` INT(10,0) NULL DEFAULT NULL COMMENT '0: NO 1: YES',
                          `CREATED_AT` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                          `MEMBER_ID` INT(10,0) NULL DEFAULT NULL,
                          `BOARD_ID` INT(10,0) NULL DEFAULT NULL,
                          PRIMARY KEY (`ID`) USING BTREE,
                          INDEX `REPORT_FK1` (`MEMBER_ID`) USING BTREE,
                          INDEX `REPORT_FK2` (`BOARD_ID`) USING BTREE,
                          CONSTRAINT `REPORT_FK1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION,
                          CONSTRAINT `REPORT_FK2` FOREIGN KEY (`BOARD_ID`) REFERENCES `arounders`.`BOARD` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='REPORT TABLE'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

/*LIKES*/
CREATE TABLE `LIKES` (
                         `MEMBER_ID` INT(10,0) NULL DEFAULT NULL COMMENT 'LIKES MEMBER ID',
                         `BOARD_ID` INT(10,0) NULL DEFAULT NULL COMMENT 'LIKES BOARD ID',
                         INDEX `LIKES_FK1` (`MEMBER_ID`) USING BTREE,
                         INDEX `LIKES_FK2` (`BOARD_ID`) USING BTREE,
                         CONSTRAINT `LIKES_FK1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION,
                         CONSTRAINT `LIKES_FK2` FOREIGN KEY (`BOARD_ID`) REFERENCES `arounders`.`BOARD` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='LIKES'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

/*REVIEW*/
CREATE TABLE `REVIEW` (
                          `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                          `RATE` INT(10,0) NOT NULL DEFAULT '0' COMMENT 'REVIEW RATE',
                          `CONTENT` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT 'REVIEW CONTENT' COLLATE 'utf8mb4_0900_ai_ci',
                          `CREATED_AT` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'REVIEW CREATED AT',
                          `MEMBER_ID` INT(10,0) NOT NULL DEFAULT '0' COMMENT 'REVIEW MEMBER ID',
                          `BOARD_ID` INT(10,0) NOT NULL DEFAULT '0' COMMENT 'REVIEW BOARD ID',
                          PRIMARY KEY (`ID`) USING BTREE,
                          INDEX `REVIEW_FK1` (`MEMBER_ID`) USING BTREE,
                          INDEX `REVIEW_FK2` (`BOARD_ID`) USING BTREE,
                          CONSTRAINT `REVIEW_FK1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION,
                          CONSTRAINT `REVIEW_FK2` FOREIGN KEY (`BOARD_ID`) REFERENCES `arounders`.`BOARD` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='REVIEW'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

/*ROLE*/
CREATE TABLE `ROLE` (
                        `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                        `NAME` VARCHAR(50) NULL DEFAULT NULL COMMENT 'ROLE NAME' COLLATE 'utf8_general_ci',
                        PRIMARY KEY (`ID`) USING BTREE
)
    COMMENT='ROLE OF USERS'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;

/*CHAT_ROOM*/
CREATE TABLE `CHAT_ROOM` (
                             `ID` INT(10,0) NOT NULL AUTO_INCREMENT,
                             `TITLE` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'CHAT ROOM TITLE' COLLATE 'utf8mb4_0900_ai_ci',
                             `REGION` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'CHAT ROOM REGION' COLLATE 'utf8mb4_0900_ai_ci',
                             `CREATED_AT` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'CHAT ROOM CREATED AT',
                             `FINISHED_AT` TIMESTAMP NOT NULL COMMENT 'CHAT ROOM FINISHED AT',
                             `MEMBER_ID` INT(10,0) NOT NULL DEFAULT '0' COMMENT 'CHAT ROOM MEMBER ID',
                             PRIMARY KEY (`ID`) USING BTREE,
                             INDEX `CHAT_ROOM_FK1` (`MEMBER_ID`) USING BTREE,
                             CONSTRAINT `CHAT_ROOM_FK1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='CHAT ROOM'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

/*INTERESTS*/
CREATE TABLE `INTERESTS` (
                             `MEMBER_ID` INT(10,0) NULL DEFAULT NULL COMMENT 'INTERESTS MEMBER ID',
                             `BOARD_ID` INT(10,0) NULL DEFAULT NULL COMMENT 'INTERESTS BOARD ID',
                             INDEX `INTERESTS_FK1` (`MEMBER_ID`) USING BTREE,
                             INDEX `INTERESTS_FK2` (`BOARD_ID`) USING BTREE,
                             CONSTRAINT `INTERESTS_FK1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `arounders`.`MEMBER` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION,
                             CONSTRAINT `INTERESTS_FK2` FOREIGN KEY (`BOARD_ID`) REFERENCES `arounders`.`BOARD` (`ID`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
    COMMENT='INTERESTS'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

/* COMMENT_VIEW */
CREATE VIEW COMMENT_VIEW AS
SELECT C.*, M.NICKNAME FROM COMMENT C
LEFT JOIN MEMBER M ON C.MEMBER_ID = M.ID
WHERE C.IS_HIDDEN = 0;

/* REPORT_VIEW */
CREATE VIEW REPORT_VIEW AS
SELECT R.*, M1.NICKNAME REPORTER, M2.NICKNAME WRITER, B.TITLE, B.REGION, B.CREATED_AT BOARD_CREATED_AT, B.UPDATED_AT BOARD_UPDATED_AT, B.MEMBER_ID BOARD_MEMBER_ID
FROM REPORT R
         LEFT JOIN MEMBER M1 ON R.MEMBER_ID = M1.ID
         LEFT JOIN BOARD B ON R.BOARD_ID = B.ID
         LEFT JOIN MEMBER M2 ON B.MEMBER_ID = M2.ID;

/* REVIEW_VIEW */
CREATE VIEW REVIEW_VIEW AS
SELECT R.*, M.NICKNAME WRITER, B.TITLE FROM REVIEW R
                                                LEFT JOIN MEMBER M ON R.MEMBER_ID = M.ID
                                                LEFT JOIN BOARD B ON R.BOARD_ID = B.ID;
/* BOARD_VIEW */
CREATE VIEW BOARD_VIEW AS
SELECT B.ID, B.TITLE, B.CONTENT, B.REGION, B.CREATED_AT, B.UPDATED_AT, B.IS_HIDDEN,
       B.THUMBNAIL_NAME, B.THUMBNAIL_PATH, B.MEMBER_ID, B.CATEGORY_ID, B.STATUS, B.CITY_ID,
       COUNT(C.ID) COMMENT_COUNT, COUNT(L.MEMBER_ID) LIKE_COUNT, COUNT(I.MEMBER_ID) INTEREST_COUNT,
       M.NICKNAME WRITER, CT.TITLE CATEGORY_TITLE
FROM BOARD B
         LEFT JOIN MEMBER M ON B.MEMBER_ID = M.ID
         LEFT JOIN LIKES L ON B.ID = L.BOARD_ID
         LEFT JOIN COMMENT C ON B.ID = C.BOARD_ID
         LEFT JOIN INTERESTS I ON B.ID = I.BOARD_ID
         LEFT JOIN CATEGORY CT ON B.CATEGORY_ID = CT.ID
GROUP BY B.ID, B.TITLE, B.CONTENT, B.REGION, B.CREATED_AT, B.UPDATED_AT, B.IS_HIDDEN,
         B.THUMBNAIL_NAME, B.THUMBNAIL_PATH, B.MEMBER_ID, B.CATEGORY_ID, B.STATUS, B.CITY_ID,
         M.NICKNAME, CT.TITLE;