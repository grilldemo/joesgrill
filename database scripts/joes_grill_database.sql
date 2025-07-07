-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema joes_grill
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `joes_grill` ;

-- -----------------------------------------------------
-- Schema joes_grill
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `joes_grill` DEFAULT CHARACTER SET utf8 ;
USE `joes_grill` ;

-- -----------------------------------------------------
-- Table `joes_grill`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joes_grill`.`categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `joes_grill`.`menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joes_grill`.`menu` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `item_name` VARCHAR(45) NOT NULL,
  `item_description` VARCHAR(255) NOT NULL,
  `item_price` DECIMAL(5,2) NOT NULL,
  `image` VARCHAR(45) NULL,
  `category_id` INT NULL,
  PRIMARY KEY (`item_id`),
  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `joes_grill`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `joes_grill`.`admin_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joes_grill`.`admin_board` (
  `admin_account` VARCHAR(45) NOT NULL,
  `admin_password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`admin_account`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `joes_grill`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joes_grill`.`order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `order_cost` DECIMAL(5,2) NOT NULL,
  `finished` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `joes_grill`.`order_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `joes_grill`.`order_items` (
  `order_item_key` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `item_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`order_item_key`),
  INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
  INDEX `item_id_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `joes_grill`.`order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `item_id`
    FOREIGN KEY (`item_id`)
    REFERENCES `joes_grill`.`menu` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
