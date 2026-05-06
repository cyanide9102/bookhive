-- 1. Insert Roles (Long IDs)
INSERT INTO roles (id, name)
VALUES (RANDOM_UUID(), 'ROLE_ADMIN');
INSERT INTO roles (id, name)
VALUES (RANDOM_UUID(), 'ROLE_USER');
