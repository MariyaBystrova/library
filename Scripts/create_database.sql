CREATE DATABASE `book_library` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `book_library`;

CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `publish_year` int(11) NOT NULL,
  `author` varchar(255) NOT NULL,
  `brief` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `employee_book` (
  `book_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`book_id`,`employee_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_EmployeeBook_Employee_idx` (`employee_id`),
  CONSTRAINT `FK_EmployeeBook_Book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_EmployeeBook_Employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
