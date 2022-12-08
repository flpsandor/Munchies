CREATE TABLE `employee`
(
    `employee_id`        int PRIMARY KEY AUTO_INCREMENT,
    `employee_firstname` varchar(255),
    `employee_lastname`  varchar(255),
    `employee_email`     varchar(255),
    `employee_password`  varchar(255),
    `employee_role`      int,
    `employee_created`   datetime,
    `employee_updated`   datetime
);

CREATE TABLE `restaurant`
(
    `restaurant_id`           int PRIMARY KEY AUTO_INCREMENT,
    `restaurant_name`         varchar(255),
    `restaurant_address`      varchar(255),
    `restaurant_phone_number` varchar(255),
    `restaurant_menu_url`     varchar(255),
    `restaurant_created`      datetime,
    `restaurant_updated`      datetime
);

CREATE TABLE `delivery_info`
(
    `delivery_info_id`                 int PRIMARY KEY AUTO_INCREMENT,
    `delivery_info_restaurant_id`      int,
    `delivery_info_time`               int,
    `delivery_info_additional_charges` double,
    `delivery_info_created`            datetime,
    `delivery_info_updated`            datetime
);

CREATE TABLE `order_item`
(
    `order_item_id`             int PRIMARY KEY AUTO_INCREMENT,
    `order_item_group_order_id` int,
    `order_item_employee_id`    int,
    `order_item_description`    varchar(255),
    `order_item_quantity`       int,
    `order_item_price`          double,
    `order_item_created`        datetime,
    `order_item_updated`        datetime
);

CREATE TABLE `group_order`
(
    `group_order_id`            int PRIMARY KEY AUTO_INCREMENT,
    `group_order_restaurant_id` int,
    `group_order_employee_id`   int,
    `group_order_created`       datetime,
    `group_order_updated`       datetime
);


