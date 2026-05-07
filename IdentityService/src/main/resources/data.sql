-- Default roles
INSERT INTO roles (id, name)
VALUES (gen_random_uuid(), 'ROLE_USER') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (id, name)
VALUES (gen_random_uuid(), 'ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;

-- Default admin
INSERT INTO users (id, username, password, email)
VALUES (gen_random_uuid(), 'admin', '$2a$10$sC71b5R2uyk2U.k/P8HNaOosNdJ4SDocGSxdGDgVZE3zJ7ShObtzy',
        'admin@bookhive.com') ON CONFLICT (username) DO NOTHING;

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.username = 'admin'
  AND r.name = 'ROLE_ADMIN' ON CONFLICT DO NOTHING;
