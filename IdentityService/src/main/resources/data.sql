-- Default roles
INSERT INTO roles (id, name)
VALUES ('01H7X1ZA2P9X1', 'ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (id, name)
VALUES ('01H7X1ZA2P9X2', 'ROLE_USER') ON CONFLICT (name) DO NOTHING;

-- Default admin
INSERT INTO users (id, username, password, email)
VALUES ('01H7X1ZA2P9X3', 'admin', '$2a$10$sC71b5R2uyk2U.k/P8HNaOosNdJ4SDocGSxdGDgVZE3zJ7ShObtzy', 'admin@bookhive.com')
ON CONFLICT (username) DO NOTHING;

INSERT INTO users_roles (user_id, role_id)
SELECT '01H7X1ZA2P9X3', '01H7X1ZA2P9X1'
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = '01H7X1ZA2P9X3' AND role_id = '01H7X1ZA2P9X1'
);
