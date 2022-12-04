CREATE TABLE "account"
(
    "id"     uuid PRIMARY KEY,
    "login"  varchar NOT NULL,
    "pass"   varchar NOT NULL,
    "status" varchar NOT NULL,
    "email"  varchar NOT NULL
);

CREATE TABLE "account_details"
(
    "id"         uuid PRIMARY KEY,
    "account_id" uuid      NOT NULL,
    "name"       varchar   NOT NULL,
    "surname"    varchar   NOT NULL,
    "pesel"      varchar   NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT '' now()''
);

CREATE TABLE "position"
(
    "id"          uuid PRIMARY KEY,
    "account_id"  uuid,
    "name"        varchar,
    "description" varchar
);

CREATE TABLE "account_role"
(
    "id"         uuid PRIMARY KEY,
    "account_id" uuid NOT NULL,
    "role_id"    uuid NOT NULL
);

CREATE TABLE "role"
(
    "id"   uuid PRIMARY KEY,
    "name" varchar NOT NULL
);

CREATE TABLE "role_privilege"
(
    "id"           uuid PRIMARY KEY,
    "role_id"      uuid NOT NULL,
    "privilege_id" uuid NOT NULL
);

CREATE TABLE "privilege"
(
    "id"   uuid PRIMARY KEY,
    "name" varchar NOT NULL
);

CREATE TABLE "project"
(
    "id"          uuid PRIMARY KEY,
    "name"        varchar NOT NULL,
    "description" varchar,
    "account_id"  uuid    NOT NULL
);

CREATE TABLE "project_details"
(
    "id"         uuid PRIMARY KEY,
    "project_id" uuid      NOT NULL,
    "options"    varchar   NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT '' now()''
);

CREATE TABLE "task_form"
(
    "id"          uuid PRIMARY KEY,
    "name"        varchar NOT NULL,
    "description" varchar,
    "id_project"  uuid
);

CREATE TABLE "task_form_details"
(
    "id"           uuid PRIMARY KEY,
    "task_form_id" uuid,
    "description"  varchar,
    "status"       varchar NOT NULL
);

CREATE TABLE "task"
(
    "id"           uuid PRIMARY KEY,
    "account_id"   uuid,
    "form_id"      uuid,
    "created_at"   timestamp NOT NULL DEFAULT '' now()'',
    "date_from"    timestamp NOT NULL,
    "date_to"      timestamp NOT NULL,
    "name"         varchar,
    "event_status" varchar   NOT NULL
);

CREATE TABLE "account_message"
(
    "id"           uuid PRIMARY KEY,
    "data"         varchar,
    "account_from" uuid,
    "account_to"   uuid,
    "created_at"   timestamp NOT NULL DEFAULT '' now()''
);

CREATE INDEX ON "account" ("login");

CREATE INDEX ON "account" ("email");

CREATE INDEX ON "account_details" ("name");

CREATE INDEX ON "role" ("name");

CREATE INDEX ON "privilege" ("name");

CREATE INDEX ON "project" ("name");

CREATE INDEX ON "task" ("date_from");

CREATE INDEX ON "account_message" ("created_at");

ALTER TABLE "account_details"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "account_role"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "account_role"
    ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "role_privilege"
    ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "role_privilege"
    ADD FOREIGN KEY ("privilege_id") REFERENCES "privilege" ("id");

ALTER TABLE "project"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "project_details"
    ADD FOREIGN KEY ("project_id") REFERENCES "project" ("id");

ALTER TABLE "task_form"
    ADD FOREIGN KEY ("id_project") REFERENCES "project" ("id");

ALTER TABLE "task_form_details"
    ADD FOREIGN KEY ("task_form_id") REFERENCES "task_form" ("id");

ALTER TABLE "task"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "task"
    ADD FOREIGN KEY ("form_id") REFERENCES "task_form" ("id");

ALTER TABLE "position"
    ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "account_message"
    ADD FOREIGN KEY ("account_from") REFERENCES "account" ("id");

ALTER TABLE "account_message"
    ADD FOREIGN KEY ("account_to") REFERENCES "account" ("id");
