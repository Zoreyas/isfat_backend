CREATE TABLE IF NOT EXISTS u_roles
(
    id         bigserial PRIMARY KEY,
    name       text      NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp
);

CREATE TABLE IF NOT EXISTS users
(
    id         bigserial PRIMARY KEY,
    username   text      NOT NULL UNIQUE,
    email      text      NOT NULL UNIQUE,
    name       text      NOT NULL,
    surname    text,
    password   text      NOT NULL,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp
);

CREATE TABLE IF NOT EXISTS m2m_users_roles
(
    user_id bigserial NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id bigserial NOT NULL REFERENCES u_roles (id)
);

CREATE TABLE IF NOT EXISTS drivers
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        bigint REFERENCES users (id) ON DELETE CASCADE,
    license_number text      NOT NULL UNIQUE,
    phone          text,
    created_at     timestamp NOT NULL DEFAULT now(),
    updated_at     timestamp
);

CREATE TABLE IF NOT EXISTS notifications
(
    id         bigserial PRIMARY KEY,
    user_id    bigint    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    message    text      NOT NULL,
    is_read    boolean   NOT NULL DEFAULT false,
    created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS cars
(
    id                 bigserial PRIMARY KEY,
    name               text      NOT NULL,
    integrity          integer   NOT NULL DEFAULT 100,
    max_fuel_tank      float     NOT NULL,
    current_fuel       float     NOT NULL DEFAULT 0,
    max_cargo_capacity float     NOT NULL,
    current_cargo_load float     NOT NULL DEFAULT 0,
    created_at         timestamp NOT NULL DEFAULT now(),
    updated_at         timestamp
);

CREATE TABLE IF NOT EXISTS car_driver_history
(
    id         bigserial PRIMARY KEY,
    car_id     bigint    NOT NULL REFERENCES cars (id) ON DELETE CASCADE,
    driver_id  bigint    NOT NULL REFERENCES drivers (id) ON DELETE CASCADE,
    created_at timestamp NOT NULL DEFAULT now(),
    ended_at   timestamp
);

CREATE TABLE IF NOT EXISTS car_notes
(
    id         BIGSERIAL PRIMARY KEY,
    car_id     BIGINT    NOT NULL REFERENCES cars (id) ON DELETE CASCADE,
    note       TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS car_repair_history
(
    id             BIGSERIAL PRIMARY KEY,
    car_id         BIGINT    NOT NULL REFERENCES cars (id) ON DELETE CASCADE,
    mechanic_id    BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    repair_details TEXT      NOT NULL,
    repaired_at    TIMESTAMP NOT NULL DEFAULT now()
);
