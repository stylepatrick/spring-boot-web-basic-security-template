create table users
(
    login           varchar(256),
    hashed_password varchar(256),
    role            varchar(256)
);

INSERT INTO users (login, hashed_password, role) VALUES ('user1', '{bcrypt}$2a$12$ArYp3fxC2yfHbg6B6SdcSOjlMDd3zCgIG.3MGGmuTCoeQ2Q2s7TEu', 'TOPSECRET');
INSERT INTO users (login, hashed_password, role) VALUES ('user', '{noop}test', 'USER');