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
-- Current Database: `restaurant_user`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `restaurant_user` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `restaurant_user`;

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

--
-- Current Database: `restaurant_dish`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `restaurant_dish` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `restaurant_dish`;

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

--
-- Current Database: `restaurant_order`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `restaurant_order` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `restaurant_order`;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dish_id` bigint NOT NULL,
  `dish_name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `subtotal` double NOT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,3,'dish3',25,1,25,1),(2,1,'dish1',1.1,1,1.1,1),(3,3,'dish3',25,2,50,2),(4,1,'dish1',1.1,1,1.1,2),(5,3,'dish3',25,2,50,3),(6,1,'dish1',1.1,1,1.1,3),(7,5,'dish4',892.19,2,1784.38,4),(8,1,'dish1',1.1,1,1.1,4),(9,6,'dish6',892.19,2,1784.38,5);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `order_no` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` tinyint NOT NULL,
  `total_amount` double NOT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKg8pohnngqi5x1nask7nff2u7w` (`order_no`),
  CONSTRAINT `orders_chk_1` CHECK ((`status` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2025-12-21 15:07:14.933425','ORDSun Dec 21 15:07:14 HKT 20251','',0,26.1,'2025-12-21 15:07:14.933425',1),(2,'2025-12-21 15:18:34.867658','ORD5ZP9sw1','',0,51.1,'2025-12-21 15:18:34.867658',1),(3,'2025-12-21 21:35:12.367620','ORD61lY6k3','',1,51.1,'2025-12-21 22:07:30.657322',3),(4,'2025-12-22 23:08:56.483783','ORD67A5Me3','',0,1785.48,'2025-12-22 23:08:56.483783',3),(5,'2025-12-23 15:19:09.007930','ORD6bwlvP4','',0,1784.38,'2025-12-23 15:19:09.007930',4);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-28 18:59:30
