INSERT INTO user (first_name, last_name, employee_number, login, password, activated, version, date_created, last_updated, created_by, updated_by)
VALUES ('admin', 'admin', '1', 'admin','$2a$10$HYtUIYRUOGqOrb3u8sGUh.fuDESsJfdMnseKd60p.cmnKEPp0KfqC', 1, 0, curdate(), curdate(), 'admin', 'admin')
ON DUPLICATE KEY UPDATE login = 'admin';