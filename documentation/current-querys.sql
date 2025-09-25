SELECT * FROM users;
SELECT * FROM positions;
SELECT * FROM messages;
SELECT * FROM chat_individual;
SELECT * FROM individual_has_users;
SELECT * FROM individual_messages;

INSERT INTO positions(position_name) VALUES ('Asesor de Atención al Cliente');

INSERT INTO users (
    creation_date,
    email,
    is_enabled,
    last_modification,
    lastnames,
    names,
    password,
    role,
    url_photo,
    fk_id_position
) VALUES (
    NOW(),
    'juan.perez@empresa.com',
    true,
    NOW(),
    'Pérez',
    'Juan',
    '$2a$12$9ds5Pn.1TLCNrQQ2wqjgfe8OPqBs36z/BUawmQPKn9J6oD/HqtFNq',
    'ADMIN',
    'https://res.cloudinary.com/dgsgtffmx/image/upload/v1758739956/icon-7797704_640_uzbata.png',
    1
);

INSERT INTO users (
    creation_date,
    email,
    is_enabled,
    last_modification,
    lastnames,
    names,
    password,
    role,
    url_photo,
    fk_id_position
) VALUES (
    NOW(),
    'luis.io12@empresa.com',
    true,
    NOW(),
    'Sandoval',
    'Luis',
    '$2a$12$9ds5Pn.1TLCNrQQ2wqjgfe8OPqBs36z/BUawmQPKn9J6oD/HqtFNq',
    'USER',
    'https://res.cloudinary.com/dgsgtffmx/image/upload/v1758739956/icon-7797704_640_uzbata.png',
    1
);


SELECT ihu1.fk_id_user AS user1,
       ihu2.fk_id_user AS user2,
       ihu1.fk_id_individual AS chat_id
FROM individual_has_users ihu1
JOIN individual_has_users ihu2 ON ihu1.fk_id_individual = ihu2.fk_id_individual
WHERE ihu1.fk_id_user = UNHEX('ID_USER_1')   -- primer usuario
  AND ihu2.fk_id_user = UNHEX('ID_USER_2');  -- segundo usuario




SELECT * FROM messages;





