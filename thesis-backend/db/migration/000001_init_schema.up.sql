CREATE TABLE "account"
(
    "id"     bigserial PRIMARY KEY,
    "login"  varchar NOT NULL,
    "pass"   varchar NOT NULL,
    "status" varchar NOT NULL
);

CREATE TABLE "account_details"
(
    "id"         bigserial PRIMARY KEY,
    "account_id" bigint     NOT NULL,
    "name"       varchar    NOT NULL,
    "surname"    varchar    NOT NULL,
    "age"        int        NOT NULL,
    "pesel"      varchar    NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT now()
);

CREATE TABLE "account_roles"
(
    "id"         bigserial PRIMARY KEY,
    "account_id" bigint NOT NULL,
    "role_id"    bigint NOT NULL
);

CREATE TABLE "role"
(
    "id"   bigserial PRIMARY KEY,
    "name" varchar NOT NULL,
    "type" varchar NOT NULL
);

CREATE TABLE "roles_privileges"
(
    "id"           bigserial PRIMARY KEY,
    "role_id"      bigint NOT NULL,
    "privilege_id" bigint NOT NULL
);

CREATE TABLE "privilege"
(
    "id"          bigserial PRIMARY KEY,
    "description" varchar NOT NULL,
    "type"        varchar NOT NULL
);

CREATE TABLE "project"
(
    "id"          bigserial PRIMARY KEY,
    "name"        varchar NOT NULL,
    "description" varchar,
    "owner"       bigint  NOT NULL,
    "options"     varchar
);

CREATE TABLE "task_type"
(
    "id"          bigserial PRIMARY KEY,
    "name"        varchar NOT NULL,
    "description" varchar,
    "id_project"  bigint
);

CREATE TABLE "task_details"
(
    "id"          bigserial PRIMARY KEY,
    "task_id"     bigint,
    "description" varchar,
    "status"      varchar NOT NULL
);

CREATE TABLE "task"
(
    "id"         bigserial PRIMARY KEY,
    "account_id" bigint,
    "type_id"    bigint,
    "created_at" timestamp NOT NULL DEFAULT now(),
    "date_from"  timestamp   NOT NULL,
    "date_to"    timestamp   NOT NULL,
    "name"       varchar
);

CREATE TABLE "account_message"
(
    "id"           bigserial PRIMARY KEY,
    "data"         varchar,
    "account_from" bigint,
    "account_to"   bigint,
    "created_at"   timestamp NOT NULL DEFAULT now()
);

CREATE INDEX ON "account" ("login");

CREATE INDEX ON "account_details" ("name");

CREATE INDEX ON "role" ("name");

CREATE INDEX ON "privilege" ("description");

CREATE INDEX ON "project" ("name");

CREATE INDEX ON "task" ("date_from");

CREATE INDEX ON "account_message" ("created_at");

ALTER TABLE "account_details"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "account_roles"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "account_roles"
    ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "roles_privileges"
    ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "roles_privileges"
    ADD FOREIGN KEY ("privilege_id") REFERENCES "privilege" ("id");

ALTER TABLE "project"
    ADD FOREIGN KEY ("owner") REFERENCES "account" ("id");

ALTER TABLE "task_type"
    ADD FOREIGN KEY ("id_project") REFERENCES "project" ("id");

ALTER TABLE "task_details"
    ADD FOREIGN KEY ("task_id") REFERENCES "task" ("id");

ALTER TABLE "task"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "task"
    ADD FOREIGN KEY ("type_id") REFERENCES "task_type" ("id");

ALTER TABLE "account_message"
    ADD FOREIGN KEY ("account_from") REFERENCES "account" ("id");

ALTER TABLE "account_message"
    ADD FOREIGN KEY ("account_to") REFERENCES "account" ("id");
