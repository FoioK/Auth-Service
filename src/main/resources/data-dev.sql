INSERT INTO permission(id, name)
VALUES (100001, 'TEST_ADMIN_PERMISSION');
INSERT INTO permission(id, name)
VALUES (100002, 'TEST_USER_PERMISSION');

INSERT INTO role(id, name)
VALUES (100001, 'admin');
INSERT INTO role(id, name)
VALUES (100002, 'user');

INSERT INTO role_permission(id, permission_id, role_id)
VALUES (100001, 100001, 100001);
INSERT INTO role_permission(id, permission_id, role_id)
VALUES (100002, 100002, 100001);
INSERT INTO role_permission(id, permission_id, role_id)
VALUES (100003, 100002, 100002);

INSERT INTO user(id, code, create_time, email, nickname, password, user_status)
VALUES (100001, '14380d45-9e52-47ea-b12f-e37b4aedb43b', '2019-03-25T18:48:01.05', 'admin@mail.com', 'admin',
        '$2a$10$K8.EwoNmKDmpvTk2I0qc3O0hvyxVItYO3BYdkIkI2KHBwWntqwNge', 3);
INSERT INTO user(id, code, create_time, email, nickname, password, user_status)
VALUES (100002, '109836f4-d9b6-4293-8241-d800130bcd8a', '2019-03-26T18:48:01.05', 'user@mail.com', 'user',
        '$2a$10$dg99W3qCeOnu0YYWJ9733.NrB0XquZwt5Mqo7fKF551dL40k//BwG', 4);

INSERT INTO user(id, code, create_time, email, nickname, password, user_status)
VALUES (100003, '109836f4-d9b6-4293-8241-d800130bcd8a', '2019-03-26T18:48:01.05', 'user@mail.com', 'user',
        '$2a$10$dg99W3qCeOnu0YYWJ9733.NrB0XquZwt5Mqo7fKF551dL40k//BwG', 3);

INSERT INTO user_role(id, user_code, role_id)
VALUES (100000, '14380d45-9e52-47ea-b12f-e37b4aedb43b', 100001);
INSERT INTO user_role(id, user_code, role_id)
VALUES (100001, '109836f4-d9b6-4293-8241-d800130bcd8a', 100002);