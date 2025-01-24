DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at datetime,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS events;
CREATE TABLE events (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    date datetime NOT NULL,
    venue VARCHAR(255) NOT NULL,
    capacity BIGINT,
    price BIGINT NOT NULL,
    image VARCHAR(255),
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tickets;
CREATE TABLE tickets (
    id BIGINT NOT NULL AUTO_INCREMENT,
    event_id BIGINT NOT NULL,
    user_id BIGINT,
    payment_id BIGINT,
    qr_code VARBINARY(1024),
--  TODO limit of 1024 test if it works well or there is a possibility to use: qr_code BLOB instead of VARBINARY
--    purchase_date TIMESTAMP WITH TIME ZONE NOT NULL,
    purchase_date TIMESTAMP WITH TIME ZONE,
    valid BOOLEAN,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS payments;
CREATE TABLE payments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    ticket_id BIGINT,
    user_id BIGINT NOT NULL,
    amount BIGINT,
    payment_date datetime,
    payment_method VARCHAR(255) NOT NULL,
    CONSTRAINT payments_ticket_id_fk FOREIGN KEY (ticket_id) REFERENCES tickets (id),
    CONSTRAINT payments_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    PRIMARY KEY (id)
);





