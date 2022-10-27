-- Adminer 4.8.1 MySQL 8.0.31 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS `footballapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `footballapp`;

DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `uuid` varchar(198) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `age` int NOT NULL,
  `position` varchar(198) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `end_contract` date NOT NULL,
  `team` varchar(198) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `position` (`position`),
  KEY `team` (`team`),
  CONSTRAINT `player_ibfk_1` FOREIGN KEY (`position`) REFERENCES `position` (`uuid`),
  CONSTRAINT `player_ibfk_2` FOREIGN KEY (`team`) REFERENCES `team` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `player` (`uuid`, `name`, `surname`, `age`, `position`, `end_contract`, `team`) VALUES
('2d90e928-550e-11ed-966a-0242ac140002',	'Lionel',	'Messi',	30,	'f8a3c84c-550d-11ed-966a-0242ac140002',	'2022-10-26',	'11ed0d72-550e-11ed-966a-0242ac140002');

DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `uuid` varchar(198) NOT NULL,
  `name` varchar(30) NOT NULL,
  `site` varchar(30) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `position` (`uuid`, `name`, `site`) VALUES
('83345f63-550d-11ed-966a-0242ac140002',	'Goalkeeper',	'none'),
('9c28ed76-550d-11ed-966a-0242ac140002',	'Defender',	'Centerback'),
('b8beca4c-550d-11ed-966a-0242ac140002',	'Defender',	'Sweeper'),
('c18ca28d-550d-11ed-966a-0242ac140002',	'Defender',	'Fullback'),
('ccba20e0-550d-11ed-966a-0242ac140002',	'Defender',	'Wingback'),
('dd2da770-550d-11ed-966a-0242ac140002',	'Midfield',	'Central'),
('e5548fd0-550d-11ed-966a-0242ac140002',	'Midfield',	'Defensive'),
('f8a3c84c-550d-11ed-966a-0242ac140002',	'Midfield',	'Attacking');

DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `uuid` varchar(198) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `team` (`uuid`, `name`) VALUES
('11ed0d72-550e-11ed-966a-0242ac140002',	'FC Barcelona');

-- 2022-10-26 09:47:04