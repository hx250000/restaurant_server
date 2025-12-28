-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant_dish
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
-- Table structure for table `restaurant_dish`
--

DROP TABLE IF EXISTS `restaurant_dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_dish` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `dishname` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_spicy` bit(1) NOT NULL,
  `price` double NOT NULL,
  `status` int NOT NULL,
  `stock` int NOT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `category` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_dish`
--

LOCK TABLES `restaurant_dish` WRITE;
/*!40000 ALTER TABLE `restaurant_dish` DISABLE KEYS */;
INSERT INTO `restaurant_dish` VALUES (1,'2025-12-19 11:15:23.744817','dish1description','dish1','',_binary '\0',1.1,1,10,'2025-12-22 23:08:56.425785','test'),(2,'2025-12-19 11:16:05.621220','dish2description','dish2','http://www.baidu.com',_binary '\0',10,0,12,'2025-12-19 11:19:49.028845','test'),(3,'2025-12-20 23:13:01.487279','dish3description','dish3','',_binary '\0',25,1,11,'2025-12-21 21:35:12.291580','test'),(4,'2025-12-20 23:13:37.553920','dish3description','dish4','',_binary '\0',25,0,12,'2025-12-20 23:14:00.866224','test'),(5,'2025-12-22 22:59:41.823544','','dish4','',_binary '\0',892.19,1,5,'2025-12-22 23:08:56.357817','test'),(6,'2025-12-23 15:17:42.907632','','dish6','',_binary '\0',892.19,1,98,'2025-12-23 15:19:08.937818','test');
/*!40000 ALTER TABLE `restaurant_dish` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-28 20:06:07
