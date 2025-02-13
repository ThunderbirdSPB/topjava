DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER DEFAULT nextval('global_seq')  PRIMARY KEY ,
    name             TEXT                                   NOT NULL,
    email            TEXT                                   NOT NULL,
    password         TEXT                                   NOT NULL,
    registered       TIMESTAMP           DEFAULT now()      NOT NULL,
    enabled          BOOLEAN             DEFAULT TRUE       NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    TEXT,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
    id                  INTEGER         PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id             INTEGER         NOT NULL,
    date_time           TIMESTAMP       NOT NULL,
    description         TEXT            NOT NULL,
    calories            INT             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_user_datetime_idx ON meals (user_id, date_time);

CREATE TABLE steps_per_day
(
    id                  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id             INTEGER   NOT NULL,
    date                TIMESTAMP NOT NULL,
    number_of_steps            INTEGER   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX steps_unique_user_date_idx ON steps_per_day (user_id, date);