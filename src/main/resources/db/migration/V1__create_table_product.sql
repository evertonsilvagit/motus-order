CREATE TABLE tb_product (
  `id` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `dt_created` datetime NOT NULL,
  `dt_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_product_UN` (`code`)
);

CREATE TABLE `tb_order` (
  `id` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `dt_created` datetime NOT NULL,
  `dt_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tb_order_product` (
  `order_id` varchar(100) NOT NULL,
  `product_id` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `tb_order_product_FK_1` (`product_id`),
  CONSTRAINT `tb_order_product_FK` FOREIGN KEY (`order_id`) REFERENCES `tb_order` (`id`),
  CONSTRAINT `tb_order_product_FK_1` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`id`)
);
