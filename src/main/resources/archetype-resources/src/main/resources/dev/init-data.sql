INSERT INTO user (id, first_name, last_name, employee_number, login, password, activated, version, date_created, last_updated, created_by, updated_by, role, language)
VALUES (1 ,'admin', 'admin', '1', 'admin','$2a$10$HYtUIYRUOGqOrb3u8sGUh.fuDESsJfdMnseKd60p.cmnKEPp0KfqC', 1, 0, curdate(), curdate(), 'admin', 'admin', 'ROLE_ADMIN', 'EN')
ON DUPLICATE KEY UPDATE login = 'admin';