ALTER TABLE `munchies`.`employee`
    MODIFY employee_id int NOT NULL AUTO_INCREMENT,
    MODIFY employee_firstname varchar(255) NOT NULL,
    MODIFY employee_lastname varchar(255) NOT NULL,
    MODIFY employee_email varchar(255) NOT NULL,
    MODIFY employee_password varchar(255) NOT NULL,
    MODIFY employee_role varchar(255) NOT NULL;

ALTER TABLE `munchies`.`restaurant`
    MODIFY restaurant_id int NOT NULL AUTO_INCREMENT,
    MODIFY restaurant_name VARCHAR(255) NOT NULL,
    MODIFY restaurant_short_name VARCHAR(255) NOT NULL,
    MODIFY restaurant_address VARCHAR(255) NOT NULL,
    MODIFY restaurant_phone_number VARCHAR(255) NOT NULL,
    MODIFY restaurant_menu_url VARCHAR(255) NOT NULL;

ALTER TABLE `munchies`.`delivery_info`
    MODIFY delivery_info_id int NOT NULL AUTO_INCREMENT,
    MODIFY delivery_info_restaurant_id int NOT NULL,
    MODIFY delivery_info_time int NOT NULL,
    MODIFY delivery_info_additional_charges double NOT NULL;

ALTER TABLE `munchies`.`order_item`
    MODIFY order_item_id int NOT NULL AUTO_INCREMENT,
    MODIFY order_item_group_order_id int NOT NULL,
    MODIFY order_item_employee_id int NOT NULL,
    MODIFY order_item_description varchar(255) NOT NULL,
    MODIFY order_item_quantity int NOT NULL,
    MODIFY order_item_price double NOT NULL;

ALTER TABLE `munchies`.`group_order`
    MODIFY group_order_id int NOT NULL AUTO_INCREMENT,
    MODIFY group_order_restaurant_id int NOT NULL,
    MODIFY group_order_employee_id int NOT NULL;

ALTER TABLE `delivery_info`
    ADD FOREIGN KEY (`delivery_info_restaurant_id`) REFERENCES `restaurant` (`restaurant_id`);

ALTER TABLE `order_item`
    ADD FOREIGN KEY (`order_item_group_order_id`) REFERENCES `group_order` (`group_order_id`);

ALTER TABLE `order_item`
    ADD FOREIGN KEY (`order_item_employee_id`) REFERENCES `employee` (`employee_id`);

ALTER TABLE `group_order`
    ADD FOREIGN KEY (`group_order_restaurant_id`) REFERENCES `restaurant` (`restaurant_id`);

ALTER TABLE `group_order`
    ADD FOREIGN KEY (`group_order_employee_id`) REFERENCES `employee` (`employee_id`);