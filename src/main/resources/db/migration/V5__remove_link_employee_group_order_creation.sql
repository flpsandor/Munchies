
ALTER TABLE `group_order`
    DROP FOREIGN KEY group_order_ibfk_2;

ALTER TABLE `munchies`.`group_order`
    MODIFY group_order_employee_id VARCHAR(255) NOT NULL;
