INSERT INTO `munchies`.`employee`
(`employee_firstname`,
 `employee_lastname`,
 `employee_email`,
 `employee_password`,
 `employee_role`)
VALUES ('admin', 'admin', 'admin@admin.com', '$2a$12$nt5T/OeUNC8Oasl.Q4fZceexWVb7DknbZtUHKBAyyeOGyuDBsEbxy', 1);

INSERT INTO `munchies`.`employee`
(`employee_firstname`,
 `employee_lastname`,
 `employee_email`,
 `employee_password`,
 `employee_role`)
VALUES ('user', 'user', 'user@user.com', '$2a$12$nt5T/OeUNC8Oasl.Q4fZceexWVb7DknbZtUHKBAyyeOGyuDBsEbxy', 0);