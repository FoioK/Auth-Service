INSERT INTO permission(id, name)
VALUES (1, 'TEST_ADMIN_PERMISSION');
INSERT INTO permission(id, name)
VALUES (2, 'TEST_USER_PERMISSION');

INSERT INTO role(id, name)
VALUES (1, 'admin');
INSERT INTO role(id, name)
VALUES (2, 'user');

INSERT INTO role_permission(id, permission_id, role_id)
VALUES (1, 1, 1);
INSERT INTO role_permission(id, permission_id, role_id)
VALUES (2, 2, 1);
INSERT INTO role_permission(id, permission_id, role_id)
VALUES (3, 2, 2);

INSERT INTO user(id, code, create_time, email, nickname, password, user_status)
VALUES (1, 1, '2019-03-25T18:48:01.05', 'admin@mail.com', 'admin',
        '$2a$10$K8.EwoNmKDmpvTk2I0qc3O0hvyxVItYO3BYdkIkI2KHBwWntqwNge', 3);
INSERT INTO user(id, code, create_time, email, nickname, password, user_status)
VALUES (2, 2, '2019-03-26T18:48:01.05', 'user@mail.com', 'user',
        '$2a$10$dg99W3qCeOnu0YYWJ9733.NrB0XquZwt5Mqo7fKF551dL40k//BwG', 4);

INSERT INTO user(id, code, create_time, email, nickname, password, user_status)
VALUES (3, 2, '2019-03-26T18:48:01.05', 'user@mail.com', 'user',
        '$2a$10$dg99W3qCeOnu0YYWJ9733.NrB0XquZwt5Mqo7fKF551dL40k//BwG', 3);

INSERT INTO user_role(id, user_code, role_id)
VALUES (1, 1, 1);
INSERT INTO user_role(id, user_code, role_id)
VALUES (2, 2, 2);