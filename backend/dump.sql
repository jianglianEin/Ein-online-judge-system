-- MySQL dump 10.17  Distrib 10.3.12-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: oj_system
-- ------------------------------------------------------
-- Server version	10.3.12-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BBS_db`
--

DROP TABLE IF EXISTS `BBS_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BBS_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Lz_studentId` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `discription` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `startDate` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `BBS_db_user_db_studentId_fk` (`Lz_studentId`),
  CONSTRAINT `BBS_db_user_db_studentId_fk` FOREIGN KEY (`Lz_studentId`) REFERENCES `user_db` (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BBS_db`
--

LOCK TABLES `BBS_db` WRITE;
/*!40000 ALTER TABLE `BBS_db` DISABLE KEYS */;
INSERT INTO `BBS_db` VALUES (8,'201613150620','右上角的名言','2017-08-11 16:38:43','Tim Ducan 的blog搭建好了'),(9,'201613150621','jiangliantest','2019-1-20 18:47:38','test');
/*!40000 ALTER TABLE `BBS_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_db`
--

DROP TABLE IF EXISTS `competition_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sponsor_studentId` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `startDate` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `endDate` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isOpen` tinyint(1) DEFAULT NULL,
  `languageType` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `discription` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `competition_db_user_db_studentId_fk` (`sponsor_studentId`),
  CONSTRAINT `competition_db_user_db_studentId_fk` FOREIGN KEY (`sponsor_studentId`) REFERENCES `user_db` (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_db`
--

LOCK TABLES `competition_db` WRITE;
/*!40000 ALTER TABLE `competition_db` DISABLE KEYS */;
INSERT INTO `competition_db` VALUES (1,'201613150620','21届训练5','2019-01-08 17:20:29','2019-01-10 17:20:29',1,'none','欢迎神犇虐场~，18届学弟/学妹(真的有这个物种么...)娱乐赛。难度在普及组以下'),(3,'201613150620','18届学弟/学妹训练赛','2016-03-04 19:15:00','2016-03-14 19:15:00',0,'java','主要内容：高效率找素数，字符和字符数组的操作方法。以及体会问题如何由简单到复杂的，分解较复杂问题的模块化思路。');
/*!40000 ALTER TABLE `competition_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_problem_db`
--

DROP TABLE IF EXISTS `competition_problem_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_problem_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) NOT NULL,
  `competition_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `problem_id` (`problem_id`),
  KEY `competition_id` (`competition_id`),
  CONSTRAINT `competition_problem_db_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `problem_db` (`id`),
  CONSTRAINT `competition_problem_db_ibfk_2` FOREIGN KEY (`competition_id`) REFERENCES `competition_db` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_problem_db`
--

LOCK TABLES `competition_problem_db` WRITE;
/*!40000 ALTER TABLE `competition_problem_db` DISABLE KEYS */;
INSERT INTO `competition_problem_db` VALUES (1,1,1),(2,1,3);
/*!40000 ALTER TABLE `competition_problem_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_db`
--

DROP TABLE IF EXISTS `problem_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `discription` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `inputData` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `outputData` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `example` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `commit` int(11) DEFAULT NULL,
  `pass` int(11) DEFAULT NULL,
  `testData` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_db`
--

LOCK TABLES `problem_db` WRITE;
/*!40000 ALTER TABLE `problem_db` DISABLE KEYS */;
INSERT INTO `problem_db` VALUES (1,'A+B Problem','给你两个整数，求两个整数之和。','一行，两个整数a,b。','一行，a+b的值.','{\"inputdata\":\"6,6\",\"outputdata\":\"12\"}',44,25,'none');
/*!40000 ALTER TABLE `problem_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solutionOfCompetition_db`
--

DROP TABLE IF EXISTS `solutionOfCompetition_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solutionOfCompetition_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `languageType` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `problem_id` int(11) DEFAULT NULL,
  `competition_id` int(11) DEFAULT NULL,
  `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `problem_id` (`problem_id`),
  KEY `competition_id` (`competition_id`),
  CONSTRAINT `solutionOfCompetition_db_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `problem_db` (`id`),
  CONSTRAINT `solutionOfCompetition_db_ibfk_2` FOREIGN KEY (`competition_id`) REFERENCES `competition_db` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solutionOfCompetition_db`
--

LOCK TABLES `solutionOfCompetition_db` WRITE;
/*!40000 ALTER TABLE `solutionOfCompetition_db` DISABLE KEYS */;
INSERT INTO `solutionOfCompetition_db` VALUES (1,'/home/ein/IdeaProjects/oj/target/oj/CompetitionCode/language/java/jianglianEin/method1.java','java',1,1,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}'),(2,'/home/ein/IdeaProjects/oj/target/oj/CompetitionCode/language/cpp/jianglianEin/method1.cpp','cpp',1,3,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}');
/*!40000 ALTER TABLE `solutionOfCompetition_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solution_db`
--

DROP TABLE IF EXISTS `solution_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solution_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `languageType` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `problem_id` int(11) DEFAULT NULL,
  `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `problem_id` (`problem_id`),
  CONSTRAINT `solution_db_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `problem_db` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solution_db`
--

LOCK TABLES `solution_db` WRITE;
/*!40000 ALTER TABLE `solution_db` DISABLE KEYS */;
INSERT INTO `solution_db` VALUES (1,'/home/ein/IdeaProjects/oj/target/oj/code/language/java/jianglianEin/method1.java','java',1,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}'),(9,'/home/ein/IdeaProjects/oj/target/oj/code/language/cpp/jianglianEin/method1.cpp','cpp',1,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}');
/*!40000 ALTER TABLE `solution_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_BBS_db`
--

DROP TABLE IF EXISTS `user_BBS_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_BBS_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `BBS_id` int(11) NOT NULL,
  `msg` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `postDate` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_BBS_db_ibfk_1` (`user_id`),
  KEY `user_BBS_db_ibfk_2` (`BBS_id`),
  CONSTRAINT `user_BBS_db_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_db` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_BBS_db_ibfk_2` FOREIGN KEY (`BBS_id`) REFERENCES `BBS_db` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_BBS_db`
--

LOCK TABLES `user_BBS_db` WRITE;
/*!40000 ALTER TABLE `user_BBS_db` DISABLE KEYS */;
INSERT INTO `user_BBS_db` VALUES (4,1,9,'不同用户的回复测试，．．．．．','2019-1-21 3:16:27'),(5,1,9,'不同用户的回复测试222，．．．．．','2019-1-21 3:17:21'),(6,1,9,'不同用户的回复测试22233333，．．．．．','2019-1-21 3:18:54'),(7,1,9,'jisajf;skkkkkkkkkkkkkkkkkkkkkkmmmmmmmmmmmmmmmmString 类的 split 方法示例：定义一个 String 字符串类型变量 str，一个 String[] buff 数组，将\"小学，初中，高中，大专，本科，研究生，博士\"赋值给 str，用 , 分割 str 字符串，并且将分割后的字符串数组赋值给 buff。','2019-2-6 1:17:56');
/*!40000 ALTER TABLE `user_BBS_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_competition_db`
--

DROP TABLE IF EXISTS `user_competition_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_competition_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `competition_id` int(11) NOT NULL,
  `passNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `competition_id` (`competition_id`),
  CONSTRAINT `user_competition_db_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_db` (`id`),
  CONSTRAINT `user_competition_db_ibfk_2` FOREIGN KEY (`competition_id`) REFERENCES `competition_db` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_competition_db`
--

LOCK TABLES `user_competition_db` WRITE;
/*!40000 ALTER TABLE `user_competition_db` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_competition_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_db`
--

DROP TABLE IF EXISTS `user_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rights` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `passNum` int(11) DEFAULT NULL,
  `studentId` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `major` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `grade` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `QQ` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `discription` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `studentId` (`studentId`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_db`
--

LOCK TABLES `user_db` WRITE;
/*!40000 ALTER TABLE `user_db` DISABLE KEYS */;
INSERT INTO `user_db` VALUES (1,'root',401,'201613150620','jianglianEin','ft1998226','img/user_icon/2019011921342854990995_p0.png','树莓','大三','913057041','jianglianEin@gmail.com',0,'yes'),(2,'undefined',22,'201613150621','jianglianZwei','ft1998226','img/user_icon/2019012016032755576550_p0.jpg','软功','大二','tetetetetet','teteetetete.@qq.com',0,'23333333333333'),(3,NULL,33,'201613150622','jianglianTri','ft1998226',NULL,NULL,NULL,NULL,NULL,0,NULL),(4,NULL,44,'201613150623','jianglianForth','ft1998226',NULL,NULL,NULL,NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `user_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_solutionOfCompetition_db`
--

DROP TABLE IF EXISTS `user_solutionOfCompetition_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_solutionOfCompetition_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `solutionOfCompetition_id` int(11) NOT NULL,
  `postDate` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `solutionOfCompetition_id` (`solutionOfCompetition_id`),
  CONSTRAINT `user_solutionOfCompetition_db_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_db` (`id`),
  CONSTRAINT `user_solutionOfCompetition_db_ibfk_2` FOREIGN KEY (`solutionOfCompetition_id`) REFERENCES `solutionOfCompetition_db` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_solutionOfCompetition_db`
--

LOCK TABLES `user_solutionOfCompetition_db` WRITE;
/*!40000 ALTER TABLE `user_solutionOfCompetition_db` DISABLE KEYS */;
INSERT INTO `user_solutionOfCompetition_db` VALUES (1,1,1,'2019-02-14 02:56:46',4,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}'),(2,1,2,'2019-02-14 02:56:57',5,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}');
/*!40000 ALTER TABLE `user_solutionOfCompetition_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_solution_db`
--

DROP TABLE IF EXISTS `user_solution_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_solution_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `solution_id` int(11) NOT NULL,
  `postDate` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'NULL',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `solution_id` (`solution_id`),
  CONSTRAINT `user_solution_db_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_db` (`id`),
  CONSTRAINT `user_solution_db_ibfk_2` FOREIGN KEY (`solution_id`) REFERENCES `solution_db` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_solution_db`
--

LOCK TABLES `user_solution_db` WRITE;
/*!40000 ALTER TABLE `user_solution_db` DISABLE KEYS */;
INSERT INTO `user_solution_db` VALUES (1,1,1,'2019-02-06 10:01:09',69,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}'),(2,1,9,'2019-02-06 10:34:37',2,'{\"isPass\":true,\"faildNum\":-1,\"state\":\"Accept\"}');
/*!40000 ALTER TABLE `user_solution_db` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-25 20:37:31
