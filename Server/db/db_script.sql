-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema storyboard
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema storyboard
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `storyboard` DEFAULT CHARACTER SET utf8 ;
USE `storyboard` ;

-- -----------------------------------------------------
-- Table `storyboard`.`user_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`user_info` (
  `user_id` VARCHAR(10) NOT NULL,
  `name` CHAR(10) NOT NULL,
  `password` CHAR(45) NOT NULL,
  `email` CHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`app_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`app_info` (
  `app_num` INT(11) NOT NULL AUTO_INCREMENT,
  `app_name` CHAR(45) NOT NULL,
  `total_time` INT(11) NULL DEFAULT NULL,
  `user_id` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`app_num`),
  INDEX `fk_app_info_user_info_idx` (`user_id` ASC),
  CONSTRAINT `fk_app_info_user_info`
    FOREIGN KEY (`user_id`)
    REFERENCES `storyboard`.`user_info` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`activity_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`activity_info` (
  `activity_num` INT(11) NOT NULL AUTO_INCREMENT,
  `activity_name` CHAR(45) NOT NULL,
  `total_time` INT(11) NULL DEFAULT NULL,
  `app_num` INT(11) NOT NULL,
  PRIMARY KEY (`activity_num`),
  INDEX `fk_activity_info_app_info1_idx` (`app_num` ASC),
  CONSTRAINT `fk_activity_info_app_info1`
    FOREIGN KEY (`app_num`)
    REFERENCES `storyboard`.`app_info` (`app_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`activity_use_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`activity_use_info` (
  `activity_use_num` INT(11) NOT NULL AUTO_INCREMENT,
  `during_time_start` TIMESTAMP NULL DEFAULT NULL,
  `during_time_end` TIMESTAMP NULL DEFAULT NULL,
  `activity_num` INT(11) NOT NULL,
  PRIMARY KEY (`activity_use_num`),
  INDEX `fk_activity_use__info_activity_info1_idx` (`activity_num` ASC),
  CONSTRAINT `fk_activity_use__info_activity_info1`
    FOREIGN KEY (`activity_num`)
    REFERENCES `storyboard`.`activity_info` (`activity_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`app_use_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`app_use_info` (
  `app_use_num` INT(11) NOT NULL AUTO_INCREMENT,
  `during_time_start` TIMESTAMP NULL DEFAULT NULL,
  `during_time_end` TIMESTAMP NULL DEFAULT NULL,
  `app_num` INT(11) NOT NULL,
  PRIMARY KEY (`app_use_num`),
  INDEX `fk_app_use_info_app_info1_idx1` (`app_num` ASC),
  CONSTRAINT `fk_app_use_info_app_info1`
    FOREIGN KEY (`app_num`)
    REFERENCES `storyboard`.`app_info` (`app_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`image` (
  `image_num` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NULL DEFAULT NULL,
  `image_name` VARCHAR(45) NULL DEFAULT NULL,
  `image_url` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`image_num`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`object_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`object_info` (
  `object_num` INT(11) NOT NULL AUTO_INCREMENT,
  `object_name` CHAR(45) NOT NULL,
  `object_frequency` INT(11) NOT NULL,
  `location_x` INT(11) NULL DEFAULT NULL,
  `location_y` INT(11) NULL,
  `size_width` INT(11) NULL DEFAULT NULL,
  `size_height` INT(11) NULL DEFAULT NULL,
  `text` CHAR(45) NULL DEFAULT NULL,
  `type` CHAR(45) NULL DEFAULT NULL,
  `color` CHAR(45) NULL DEFAULT NULL,
  `activity_num` INT(11) NOT NULL,
  `image_num` INT(11) NOT NULL,
  `error_frequency` INT(11) NOT NULL,
  PRIMARY KEY (`object_num`),
  INDEX `fk_object_info_activity_info1_idx` (`activity_num` ASC),
  INDEX `fk_object_info_image1_idx` (`image_num` ASC),
  CONSTRAINT `fk_object_info_activity_info1`
    FOREIGN KEY (`activity_num`)
    REFERENCES `storyboard`.`activity_info` (`activity_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_object_info_image1`
    FOREIGN KEY (`image_num`)
    REFERENCES `storyboard`.`image` (`image_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`error_use_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`error_use_info` (
  `error_use_num` INT(11) NOT NULL AUTO_INCREMENT,
  `occur_time` TIMESTAMP NULL DEFAULT NULL,
  `object_num` INT(11) NOT NULL,
  PRIMARY KEY (`error_use_num`),
  INDEX `fk_error_use_info_object_info1_idx` (`object_num` ASC),
  CONSTRAINT `fk_error_use_info_object_info1`
    FOREIGN KEY (`object_num`)
    REFERENCES `storyboard`.`object_info` (`object_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`next_activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`next_activity` (
  `object_num` INT(11) NOT NULL AUTO_INCREMENT,
  `activity_num` INT(11) NOT NULL,
  INDEX `fk_next_activity_object_info_idx` (`object_num` ASC),
  INDEX `fk_next_activity_activity_info1_idx` (`activity_num` ASC),
  CONSTRAINT `fk_next_activity_activity_info1`
    FOREIGN KEY (`activity_num`)
    REFERENCES `storyboard`.`activity_info` (`activity_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_next_activity_object_info`
    FOREIGN KEY (`object_num`)
    REFERENCES `storyboard`.`object_info` (`object_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `storyboard`.`object_use_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `storyboard`.`object_use_info` (
  `object_use_num` INT(11) NOT NULL AUTO_INCREMENT,
  `occur_time` DATE NULL DEFAULT NULL,
  `event_type` VARCHAR(45) NULL DEFAULT NULL,
  `object_num` INT(11) NOT NULL,
  PRIMARY KEY (`object_use_num`),
  INDEX `fk_object_use_info_object_info1_idx` (`object_num` ASC),
  CONSTRAINT `fk_object_use_info_object_info1`
    FOREIGN KEY (`object_num`)
    REFERENCES `storyboard`.`object_info` (`object_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
