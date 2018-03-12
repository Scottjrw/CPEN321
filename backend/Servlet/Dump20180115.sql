-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: study_buddy
-- ------------------------------------------------------
-- Server version	5.6.35-log

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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(45) NOT NULL,
  `course_num` int(11) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'CPEN',321,'Engineering practices for the development of non-trivial software-intensive systems including requirements specification, software architecture, implementation, verification, and maintenance. Iterative development. Recognized standards, guidelines, and models. Credit will only be granted for only one of CPSC 310, EECE 310, or CPEN 321.'),(2,'CPEN',331,'Operating systems, their design and their implementation. Process concurrency, synchronization, communication and scheduling. Device drivers, memory management, virtual memory, file systems, networking and security.'),(3,'ELEC',221,'Complex numbers, LTI systems, convolution sum, discrete-time Fourier series and transforms, z-transform, sampling, introduction to filtering and modulation, feedback systems, stability.'),(4,'CPEN',311,'Advanced combinational and sequential electronic system design. Hardware specification, modeling, and simulation using hardware description languages (HDLs) and CAD tools. Design with programmable logic including FPGA\'s. Applications include complex state machines, microcontrollers, arithmetic circuits, and interface units. Credit will be granted for only one of EECE 353, CPEN 311 and EECE 379.'),(5,'CPEN',281,'Written and oral communication in engineering. Technical description, report preparation, business correspondence, and oral presentation of technical material. Restricted to students in second year of Electrical and Computer Engineering programs. Credit will be granted for only one of ELEC 281, CPEN 281, BMEG 201 or APSC 201'),(6,'CPSC',221,'Design and analysis of basic algorithms and data structures; algorithm analysis methods, searching and sorting algorithms, basic data structures, graphs and concurrency.'),(7,'ELEC',201,'The fundamentals of analysis of lumped linear time-invariant circuits; network theorems; operational amplifiers; first order circuits; DC analysis of diodes, BJT, and FET circuits.'),(8,'ELEC',202,'Phasor analysis and AC three phase systems power; transfer functions; Bode plots; filters and resonance; Laplace transforms; transformers; two-port networks. First and second order circuits.'),(9,'CPSC',261,'Software architecture, operating systems, and I/O architectures. Relationships between application software, operating systems, and computing hardware; critical sections, deadlock avoidance, and performance; principles and operation of disks and networks.'),(10,'CPSC',304,'Overview of database systems, ER models, logical database design and normalization, formal relational query languages, SQL and other commercial languages,data warehouses, special topics.'),(11,'CPSC',320,'Systematic study of basic concepts and techniques in the design and analysis of algorithms, illustrated from various problem areas. Topics include: models of computation; choice of data structures; graph-theoretic, algebraic, and text processing algorithms');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_user`
--

DROP TABLE IF EXISTS `course_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_user`
--

LOCK TABLES `course_user` WRITE;
/*!40000 ALTER TABLE `course_user` DISABLE KEYS */;
INSERT INTO `course_user` VALUES (22,'admin',8),(23,'admin',1);
/*!40000 ALTER TABLE `course_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `is_private` bit(1) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `invite_code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (47,'First ELEC 202 Group','admin','','',8,'202'),(48,'test1','admin','','',1,'000');
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_content`
--

DROP TABLE IF EXISTS `group_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(45) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `post` varchar(500) DEFAULT NULL,
  `is_announcement` bit(1) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_content`
--

LOCK TABLES `group_content` WRITE;
/*!40000 ALTER TABLE `group_content` DISABLE KEYS */;
INSERT INTO `group_content` VALUES (1,'First ELEC 202 Group','2017-11-26 14:48:41.000000','hhhh','\0','admin'),(2,'test1','2017-11-26 14:55:57.000000','test group first!','\0','admin');
/*!40000 ALTER TABLE `group_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `student_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usercol_UNIQUE` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (23,'admin','admin','admin','admin@gmail.com','fb9d9ae4-4c8e-4372-bc1f-958493414bf8'),(24,'student1','123456','Emily','Emily@gmail.com','8b87ccf5-d0b4-4b2c-91d9-ecd51a1b0d32'),(25,'student2','123456','Tom','Tom@gmail.com',NULL),(26,'studentLucy','123456','Lucy','Lucy@gmail.com',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-15  9:51:55
