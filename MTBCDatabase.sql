-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: mtbc
-- ------------------------------------------------------
-- Server version	5.6.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `accountNo` int(11) NOT NULL,
  `balance` double DEFAULT NULL,
  `dt_modified` datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `account_type` tinyint(1) NOT NULL DEFAULT '0',
  `dt_created` datetime DEFAULT CURRENT_TIMESTAMP,
  `isLocked` tinyint(1) NOT NULL DEFAULT '0',
  `username` varchar(45) NOT NULL,
  `bankername` varchar(45) NOT NULL,
  PRIMARY KEY (`accountNo`),
  UNIQUE KEY `accountNo_UNIQUE` (`accountNo`),
  KEY `acc_username_idx` (`username`),
  KEY `acc_bankername_idx` (`bankername`),
  CONSTRAINT `acc_bankername` FOREIGN KEY (`bankername`) REFERENCES `user_profile` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `acc_username` FOREIGN KEY (`username`) REFERENCES `user_profile` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1000,2988,'0000-00-00 00:00:00',0,'2015-10-22 22:00:26',1,'rohit','banker'),(1234,90806,'0000-00-00 00:00:00',1,'2015-10-22 22:42:21',1,'arjun','banker'),(2000,9866,'0000-00-00 00:00:00',0,'2015-10-22 22:00:26',1,'arjun','banker');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorities` (
  `username` varchar(10) NOT NULL,
  `authority` varchar(45) NOT NULL,
  `dt_modified` datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `dt_created` datetime DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `username_UNIQUE` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `user_profile` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otp` (
  `mailID` varchar(50) NOT NULL,
  `otpValue` varchar(45) NOT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `attempts` int(11) DEFAULT '0',
  `dt_modified` datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `dt_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`mailID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
INSERT INTO `otp` VALUES ('khanjan.ce@gmail.com','JF1KKEVF','Khanjan',1,'0000-00-00 00:00:00','2015-10-18 15:28:20');
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pii`
--

DROP TABLE IF EXISTS `pii`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pii` (
  `oldSSN` int(10) DEFAULT NULL,
  `newSSN` int(10) DEFAULT NULL,
  `isApproved` tinyint(1) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  KEY `username_idx` (`username`),
  KEY `pii_username_idx` (`username`),
  CONSTRAINT `pii_username` FOREIGN KEY (`username`) REFERENCES `user_profile` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pii`
--

LOCK TABLES `pii` WRITE;
/*!40000 ALTER TABLE `pii` DISABLE KEYS */;
/*!40000 ALTER TABLE `pii` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sitekey`
--

DROP TABLE IF EXISTS `sitekey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sitekey` (
  `siteKeyId` int(11) NOT NULL AUTO_INCREMENT,
  `siteKeyName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`siteKeyId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sitekey`
--

LOCK TABLES `sitekey` WRITE;
/*!40000 ALTER TABLE `sitekey` DISABLE KEYS */;
INSERT INTO `sitekey` VALUES (1,'ferrai'),(2,'TeddyBear');
/*!40000 ALTER TABLE `sitekey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `transactionId` int(11) NOT NULL,
  `accountNo` int(11) NOT NULL,
  `transactionType` varchar(45) NOT NULL,
  `transactionAmount` double NOT NULL,
  `status` varchar(45) NOT NULL,
  `isCritical` tinyint(1) NOT NULL,
  `dt_modified` datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `dt_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transactionId`,`accountNo`),
  KEY `accountNo_idx` (`accountNo`),
  KEY `accountNo` (`accountNo`),
  CONSTRAINT `accountNo` FOREIGN KEY (`accountNo`) REFERENCES `accounts` (`accountNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (501,1234,'Debit',123,'pending',0,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(501,2000,'Credit',123,'pending',0,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(503,1234,'Debit',12,'pending',0,NULL,NULL),(503,2000,'Credit',12,'pending',0,NULL,NULL);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `dob` varchar(10) NOT NULL,
  `SSN` varchar(9) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `Addr1` varchar(45) NOT NULL,
  `Addr2` varchar(45) NOT NULL,
  `City` varchar(45) NOT NULL,
  `State` varchar(45) NOT NULL,
  `Zip` varchar(5) NOT NULL,
  `reset_pass_token` varchar(50) DEFAULT NULL,
  `isnewuser` tinyint(1) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES ('arjun','patil','03/17/1990','898989898','8989898989','kjsdghjsdg','jsdgfdhsjg','hgsdhjsdg','shdvghjdf','89898',NULL,0,0,'arjun','mallikarjunbpbp@gmail.com'),('banker','asd','03/17/1990','898989898','898989898','shdvghjdf','shdvghjdf','shdvghjdf','shdvghjdf','89898',NULL,0,0,'banker',''),('nitin','karda','03/17/1990','898989898','8989898989','kjsdghjsdg','jsdgfdhsjg','hgsdhjsdg','shdvghjdf','89898',NULL,0,0,'nitin','karda@nitin.com'),('rohit','asd','03/17/1990','898989898','898989898','shdvghjdf','shdvghjdf','hgsdhjsdg','shdvghjdf','89898',NULL,0,0,'rohit','banker@gmail.com');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `accountNonExpired` tinyint(1) NOT NULL,
  `accountNonLocked` tinyint(1) NOT NULL,
  `credentialsNonExpired` tinyint(1) NOT NULL,
  `dt_modified` datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `dt_created` datetime DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(45) DEFAULT NULL,
  `siteKeyID` int(11) DEFAULT NULL,
  `q1` varchar(45) DEFAULT NULL,
  `q2` varchar(45) DEFAULT NULL,
  `q3` varchar(45) DEFAULT NULL,
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `siteKeyID_idx` (`siteKeyID`),
  CONSTRAINT `siteKeyID` FOREIGN KEY (`siteKeyID`) REFERENCES `sitekey` (`siteKeyId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_username` FOREIGN KEY (`username`) REFERENCES `user_profile` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('arjun','12345',1,1,1,1,'0000-00-00 00:00:00','2015-10-22 22:52:19','kdhbqwilu@gmail.com',1,'liughiu','oiuhiuh',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-23 23:56:11
