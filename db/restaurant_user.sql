-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant_user
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `restaurant_user`
--

DROP TABLE IF EXISTS `restaurant_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `is_deleted` int NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CUSTOMER','STAFF') DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9pmn8vfowbwg1kw9gw2jg3g17` (`username`),
  UNIQUE KEY `UKdak4y8wrsdv9ube6kvfkucn21` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_user`
--

LOCK TABLES `restaurant_user` WRITE;
/*!40000 ALTER TABLE `restaurant_user` DISABLE KEYS */;
INSERT INTO `restaurant_user` VALUES (1,'2025-12-18 21:13:13.048169',0,'c65d17312840ca3e42bc095e39088788','ADMIN','2025-12-21 20:56:19.672682','hexing','addr1','phone1'),(2,'2025-12-18 21:13:53.825986',1,'0c4cee7c9b6648de4dce355b3ca3f9c9','ADMIN','2025-12-18 21:43:39.140396','hexing2','addr1','phone2'),(3,'2025-12-21 20:52:03.525427',0,'e16b2ab8d12314bf4efbd6203906ea6c','CUSTOMER','2025-12-21 20:52:03.525427','testuser3','addr3','19388758172'),(4,'2025-12-23 15:18:13.352337',0,'e16b2ab8d12314bf4efbd6203906ea6c','CUSTOMER','2025-12-23 15:18:13.352337','testuserxxx','addrxxx','19388758173'),(5,'2025-12-26 11:12:43.296134',0,'f35d110c0ea87a18ca034e64c1a5c2f1','ADMIN','2025-12-26 11:12:43.296134','hx','addr3','19388758174');
/*!40000 ALTER TABLE `restaurant_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-28 20:05:49
