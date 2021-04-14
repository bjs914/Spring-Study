CREATE TABLE `users` (
`username` varchar(16) NOT NULL,
`password` varchar(32) NOT NULL,
`email` varchar(255) DEFAULT NULL,
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8