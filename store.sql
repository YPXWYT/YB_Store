-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema yiban
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema yiban
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `yiban` ;
USE `yiban` ;

-- -----------------------------------------------------
-- Table `yiban`.`store_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yiban`.`store_product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL COMMENT '商品名称',
  `describe` VARCHAR(255) NULL COMMENT '商品描述',
  `number` INT(3) NOT NULL DEFAULT 0 COMMENT '商品数量',
  `img` VARCHAR(255) NULL COMMENT '商品图片',
  `price` INT(8) NOT NULL DEFAULT 1 COMMENT '商品价格',
  `status` INT(1) NOT NULL DEFAULT 1 COMMENT '商品状态\n0. 未上架\n1. 正在出售\n2. 缺货',
  `is_enable` BIT(1) NOT NULL DEFAULT true COMMENT '是否启用\ntrue 是\nfalse 否',
  `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `create_user` VARCHAR(45)  NULL COMMENT '创建者',
  `modify_time` DATETIME NOT NULL DEFAULT now() COMMENT '修改时间',
  `modify_user` VARCHAR(45) NULL COMMENT '修改者',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '网薪商城';


-- -----------------------------------------------------
-- Table `yiban`.`store_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yiban`.`store_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `yb_userid` VARCHAR(45) NOT NULL,
  `yb_username` VARCHAR(45) NOT NULL,
  `yb_usernick` VARCHAR(45) NOT NULL,
  `yb_sex` VARCHAR(45) NOT NULL,
  `yb_money` VARCHAR(45) NOT NULL,
  `status` INT NOT NULL DEFAULT 0 COMMENT '订单状态\n1 已付款\n0 未付款',
  `is_enable` BIT(1) NOT NULL DEFAULT true COMMENT '是否启用\ntrue 启用\nfalse 未启用',
  `create_time` DATETIME NOT NULL DEFAULT now(),
  `create_user` VARCHAR(45) NULL,
  `modify_time` DATETIME NOT NULL DEFAULT now(),
  `modify_user` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '网新商城订单';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
