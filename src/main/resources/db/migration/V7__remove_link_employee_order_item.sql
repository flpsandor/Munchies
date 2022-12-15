
ALTER TABLE `order_item`
    DROP FOREIGN KEY order_item_ibfk_2;

ALTER TABLE `munchies`.`order_item`
    MODIFY order_item_employee_id VARCHAR(255) NOT NULL;
