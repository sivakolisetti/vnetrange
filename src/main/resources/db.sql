create TABLE `ipamgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

create TABLE `vnetspoke` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `cidr` int(10) NOT NULL,
  `total_vnet_ip_count` int(10) NOT NULL,
  `start_ip` varchar(100) NOT NULL,
  `end_ip` varchar(100) NOT NULL,
  `subscription_name` varchar(50) NOT NULL,
  `status` ENUM('NEW','USED'),
  `ipamgroup_id` int(11),
    PRIMARY KEY (id),
   FOREIGN KEY (ipamgroup_id) REFERENCES ipamgroup(id)
);

create TABLE `subnet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `name` varchar(50),
  `cidr_subnet` int(10) NOT NULL,
  `total_subnet_ip_count` int(10) NOT NULL,
  `start_ip` varchar(100) NOT NULL,
  `end_ip` varchar(100) NOT NULL,
  `status` ENUM('NEW','USED'),
  `vnet_spoke_id` int(11),
  PRIMARY KEY (`id`),
   FOREIGN KEY (vnet_spoke_id) REFERENCES vnetspoke(id)
);
