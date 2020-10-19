/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.28-log : Database - rabbitmq_example
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rabbitmq_example` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rabbitmq_example`;

/*Data for the table `order_record` */

insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (7,'10010','物流2','2018-07-31 20:59:18','2018-07-31 23:35:43');
insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (8,'10011','供应商3','2018-07-31 20:59:30','2018-07-31 23:34:56');
insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (9,'10012','采购2','2018-07-22 20:59:36','2018-07-23 21:06:47');
insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (12,'10013','测试类型1','2018-07-22 21:02:38','2018-07-30 23:34:41');
insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (13,'10014','测试类型1','2018-07-23 21:02:50','2018-07-30 23:34:44');
insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (14,'10015','测试类型3','2018-07-23 21:06:30','2018-07-31 23:34:45');
insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (15,'10016','测试类型4','2018-07-30 20:53:39','2018-07-31 23:34:47');
insert  into `order_record`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (16,'orderNo_20180821001','物流12','2018-08-22 21:12:46',NULL);

/*Data for the table `order_report` */

insert  into `order_report`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (28,'10010','物流2','2018-07-31 20:59:18','2018-07-31 23:35:43');
insert  into `order_report`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (29,'10011','供应商3','2018-07-31 20:59:30','2018-07-31 23:34:56');
insert  into `order_report`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (30,'10015','测试类型3','2018-07-23 21:06:30','2018-07-31 23:34:45');
insert  into `order_report`(`id`,`order_no`,`order_type`,`create_time`,`update_time`) values (31,'10016','测试类型4','2018-07-30 20:53:39','2018-07-31 23:34:47');

/*Data for the table `product` */

insert  into `product`(`id`,`product_no`,`total`,`create_time`,`update_time`) values (1,'product_10010',1000,'2018-08-24 21:16:20','2018-08-25 17:59:57');

/*Data for the table `product_bak` */

insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (1,'戴尔笔记本',100,'2018-06-01','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (2,'华硕笔记本',200,'2018-07-10','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (3,'联想小新I',15,'2018-05-10','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (4,'暗影精灵',35,'2018-07-19','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (5,'外星人I',1000,'2018-07-11','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (6,'戴尔XPS超极本',200,'2018-02-07','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (7,'联想台式机',123,'2018-07-12','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (8,'戴尔笔记本-二代',100,'2018-06-01','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (9,'华硕笔记本',200,'2018-07-10','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (10,'联想小新I',15,'2018-05-10','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (11,'暗影精灵II',35,'2018-06-12','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (12,'外星人II',1000,'2018-07-11','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (13,'惠普战系列笔记本',200,'2018-02-07','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (14,'海信笔记本',123,'2018-06-19','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (15,'组装机',100,'2018-06-01','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (16,'宏碁台式机',200,'2018-07-10','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (17,'东芝笔记本',15,'2018-05-10','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (18,'神州战船',35,'2018-07-19','2018-07-21 11:28:50','2018-07-21 11:28:50',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (19,'Mac笔记本',150,NULL,'2018-07-21 21:43:30','2018-07-21 21:43:30',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (20,'华硕笔记本2',1520,'2018-07-01','2018-07-21 21:46:14','2018-07-21 22:08:52',0);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (21,'acer笔记本22',1522,'2018-02-01','2018-07-30 21:42:07','2018-07-30 21:45:36',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (22,'acer笔记本2',152,'2018-01-01','2018-07-30 21:44:00','2018-07-30 21:44:00',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (23,'',152,'2018-01-01','2018-07-30 21:49:37','2018-07-30 21:49:37',1);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (24,'联想笔记本1010',152,'2018-01-01','2018-07-30 21:55:05','2018-07-30 21:55:45',0);
insert  into `product_bak`(`id`,`name`,`stock`,`purchase_date`,`create_time`,`update_time`,`is_active`) values (25,'外星人第四代',152,'2018-03-01','2018-07-30 21:58:20','2018-07-30 22:00:08',0);

/*Data for the table `product_robbing_record` */

/*Data for the table `user` */

insert  into `user`(`id`,`user_name`,`password`,`sex`,`is_active`,`create_time`,`update_time`) values (1,'debug','linsen',1,1,'2018-07-22 16:48:25',NULL);
insert  into `user`(`id`,`user_name`,`password`,`sex`,`is_active`,`create_time`,`update_time`) values (2,'jack','123456',1,1,'2018-07-22 16:48:36',NULL);

/*Data for the table `user_log` */

insert  into `user_log`(`id`,`user_name`,`module`,`operation`,`data`,`create_time`,`update_time`) values (1,'jack','Login','login','{\"id\":2,\"userName\":\"jack\",\"password\":\"123456\",\"sex\":1,\"isActive\":1,\"createTime\":1532249316000,\"updateTime\":null}','2018-08-30 23:22:10',NULL);
insert  into `user_log`(`id`,`user_name`,`module`,`operation`,`data`,`create_time`,`update_time`) values (2,'jack','Login','login','{\"id\":2,\"userName\":\"jack\",\"password\":\"123456\",\"sex\":1,\"isActive\":1,\"createTime\":1532249316000,\"updateTime\":null}','2018-08-30 23:29:04',NULL);
insert  into `user_log`(`id`,`user_name`,`module`,`operation`,`data`,`create_time`,`update_time`) values (3,'debug','Login','login','{\"id\":1,\"userName\":\"debug\",\"password\":\"linsen\",\"sex\":1,\"isActive\":1,\"createTime\":1532249305000,\"updateTime\":null}','2018-08-30 23:31:13',NULL);
insert  into `user_log`(`id`,`user_name`,`module`,`operation`,`data`,`create_time`,`update_time`) values (4,'debug','Login','login','{\"id\":1,\"userName\":\"debug\",\"password\":\"linsen\",\"sex\":1,\"isActive\":1,\"createTime\":1532249305000,\"updateTime\":null}','2018-09-01 09:26:54',NULL);
insert  into `user_log`(`id`,`user_name`,`module`,`operation`,`data`,`create_time`,`update_time`) values (5,'debug','Login','login','{\"id\":1,\"userName\":\"debug\",\"password\":\"linsen\",\"sex\":1,\"isActive\":1,\"createTime\":1532249305000,\"updateTime\":null}','2018-09-01 09:28:03',NULL);
insert  into `user_log`(`id`,`user_name`,`module`,`operation`,`data`,`create_time`,`update_time`) values (6,'debug','Login','login','{\"id\":1,\"userName\":\"debug\",\"password\":\"linsen\",\"sex\":1,\"isActive\":1,\"createTime\":1532249305000,\"updateTime\":null}','2018-09-01 09:29:29',NULL);

/*Data for the table `user_order` */

insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (1,'10010',1,1,'2018-08-30 22:29:15','2018-08-30 22:29:43');
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (2,'10011',1,1,'2018-08-30 22:29:54',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (3,'10012',1,1,'2018-08-30 22:41:15',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (4,'10013',2,1,'2018-08-30 22:51:35',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (5,'10014',3,1,'2018-08-30 22:52:08',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (6,'10015',4,1,'2018-08-30 22:53:43',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (7,'order_10010',108,1,'2018-09-01 16:18:14',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (8,'order_10011',109,1,'2018-09-01 16:35:24',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (9,'order_10011',109,1,'2018-09-01 16:36:28',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (10,'order_10012',121,3,'2018-09-01 16:44:57','2018-09-01 16:45:07');
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (11,'order_10013',122,2,'2018-09-01 16:45:32','2018-09-01 16:45:38');
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (12,'order_10014',126,3,'2018-09-01 16:55:14','2018-09-01 16:55:25');
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (13,'order_10015',128,3,'2018-09-01 16:56:02','2018-09-01 16:56:13');
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (14,'order_10015',128,1,'2018-09-01 16:57:45',NULL);
insert  into `user_order`(`id`,`order_no`,`user_id`,`status`,`create_time`,`update_time`) values (15,'order_10016',129,1,'2018-09-01 17:01:33',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
