CREATE TABLE "account" (
                           "id" BIGSERIAL PRIMARY KEY,
                           /*"login" varchar NOT NULL,*/
                           "pass" varchar NOT NULL,
                           "status" varchar NOT NULL,
                           "email" varchar NOT NULL,
                           "position_id" BIGINT
);

CREATE TABLE "account_details" (
                                   "id" BIGSERIAL PRIMARY KEY,
                                   "account_id" BIGINT NOT NULL,
                                   "name" varchar NOT NULL,
                                   "middle_name" varchar,
                                   "surname" varchar NOT NULL,
                                   "pesel" varchar NOT NULL,
                                   "sex_id" INTEGER NOT NULL,
                                   "phone_number" varchar NOT NULL,
                                   "tax_number" varchar NOT NULL,
                                   "id_card_number" varchar,
                                   "street" varchar NOT NULL,
                                   "house_number" varchar,
                                   "apartment_number" varchar,
                                   "postal_code" varchar NOT NULL,
                                   "city" varchar NOT NULL,
                                   "country_id" INTEGER NOT NULL,
                                   "private_email" varchar,
                                   "employment_date" timestamp,
                                   "exit_date" timestamp,
                                   "birth_date" timestamp,
                                   "birth_place" varchar,
                                   "image_path" varchar,
                                   "contract_type_id" INTEGER NOT NULL,
                                   "working_time" INTEGER,
                                   "wage" INTEGER,
                                   "payday" INTEGER,
                                   "created_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "sex" (
                                   "id" SERIAL PRIMARY KEY,
                                   "name" varchar NOT NULL
);
CREATE TABLE "contract_type" (
                                   "id" SERIAL PRIMARY KEY,
                                   "name" varchar NOT NULL
);
CREATE TABLE "country" (
                                   "serial" SERIAL PRIMARY KEY,
                                   "id" INTEGER NOT NULL,
                                   "name" varchar NOT NULL
);
CREATE TABLE "billing_period" (
                                   "id" SERIAL PRIMARY KEY,
                                   "name" varchar NOT NULL
);

CREATE TABLE "account_project" (

                                   "id" BIGSERIAL PRIMARY KEY,
                                   "account_id" BIGINT NOT NULL,
                                   "project_id" BIGINT NOT NULL,
                                   "role_id" BIGINT NOT NULL,
                                   "working_time" INTEGER,
                                   "modifier" INTEGER,
                                   "join_date" timestamp NOT NULL,
                                   "exit_date" timestamp,
                                   "status" varchar NOT NULL
);

CREATE TABLE "position" (
                        "id" BIGSERIAL PRIMARY KEY,
                        "name" varchar,
                        "description" varchar,
                        "creation_date" timestamp NOT NULL,
                        "archive_date" timestamp,
                        "status" varchar
);

CREATE TABLE "account_role" (
                                "id" BIGSERIAL PRIMARY KEY,
                                "account_id" BIGINT NOT NULL,
                                "role_id" BIGINT NOT NULL
);

CREATE TABLE "role" (
                        "id" BIGSERIAL PRIMARY KEY,
                        "name" varchar NOT NULL
);

CREATE TABLE "privilege" (
                             "id" BIGSERIAL PRIMARY KEY,
                             "name" varchar NOT NULL
);

CREATE TABLE "role_privilege" (
                                  "id" BIGSERIAL PRIMARY KEY,
                                  "role_id" BIGINT NOT NULL,
                                  "privilege_id" BIGINT NOT NULL
);

CREATE TABLE "project" (
                           "id" BIGSERIAL PRIMARY KEY,
                           "name" varchar NOT NULL,
                           "description" varchar,
                           "owner_id" BIGINT NOT NULL,
                           "status" varchar
);

CREATE TABLE "project_details" (
                                   "id" BIGSERIAL PRIMARY KEY,
                                   "project_id" BIGINT NOT NULL,
                                   "billing_period_id" INTEGER NOT NULL,
                                   "archive_date" timestamp,
                                   "overtime_modifier" INTEGER,
                                   "bonus_modifier" INTEGER,
                                   "night_modifier" INTEGER,
                                   "holiday_modifier" INTEGER,
                                   "image_path" varchar,
                                   "created_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "task_form" (
                             "id" BIGSERIAL PRIMARY KEY,
                             "name" varchar NOT NULL,
                             "description" varchar,
                             "id_project" BIGINT NOT NULL,
                             "archive_date" timestamp,
                             "created_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "task_form_details" (
                                     "id" BIGSERIAL PRIMARY KEY,
                                     "task_form_id" BIGINT NOT NULL ,
                                     "description" varchar,
                                     "status" varchar NOT NULL
);

CREATE TABLE "task" (
                        "id" BIGSERIAL PRIMARY KEY,
                        "account_id" BIGINT NOT NULL ,
                        "form_id" BIGINT NOT NULL ,
                        "created_at" timestamp DEFAULT 'now()',
                        "date_from" timestamp NOT NULL,
                        "date_to" timestamp NOT NULL,
                        "name" varchar,
                        "status" varchar NOT NULL
);

CREATE TABLE "account_message" (
                                   "id" BIGSERIAL PRIMARY KEY,
                                   "data" varchar,
                                   "account_from" BIGINT,
                                   "account_to" BIGINT,
                                   "created_at" timestamp DEFAULT 'now()'
);

/*CREATE INDEX ON "account" ("login");*/

CREATE INDEX ON "account" ("email");

CREATE INDEX ON "account_details" ("name");

CREATE INDEX ON "role" ("name");

CREATE INDEX ON "privilege" ("name");

CREATE INDEX ON "project" ("name");

CREATE INDEX ON "task" ("date_from");

CREATE INDEX ON "account_message" ("created_at");

ALTER TABLE "account_details" ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "account_role" ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "account_role" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "role_privilege" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "role_privilege" ADD FOREIGN KEY ("privilege_id") REFERENCES "privilege" ("id");

ALTER TABLE "project" ADD FOREIGN KEY ("owner_id") REFERENCES "account" ("id");

ALTER TABLE "project_details" ADD FOREIGN KEY ("project_id") REFERENCES "project" ("id");

ALTER TABLE "account_project" ADD FOREIGN KEY ("project_id") REFERENCES "project"("id");

ALTER TABLE "account_project" ADD FOREIGN KEY ("account_id") REFERENCES "account"("id");

ALTER TABLE "account_project" ADD FOREIGN KEY ("role_id") REFERENCES "role"("id");

ALTER TABLE "task_form" ADD FOREIGN KEY ("id_project") REFERENCES "project" ("id");

ALTER TABLE "task_form_details" ADD FOREIGN KEY ("task_form_id") REFERENCES "task_form" ("id");

ALTER TABLE "task" ADD FOREIGN KEY ("account_id") REFERENCES "account" ("id");

ALTER TABLE "task" ADD FOREIGN KEY ("form_id") REFERENCES "task_form" ("id");

ALTER TABLE "account_message" ADD FOREIGN KEY ("account_from") REFERENCES "account" ("id");

ALTER TABLE "account_message" ADD FOREIGN KEY ("account_to") REFERENCES "account" ("id");

ALTER TABLE "account" ADD FOREIGN KEY ("position_id") REFERENCES "position" ("id");

ALTER TABLE "account_details" ADD FOREIGN KEY ("sex_id") REFERENCES "sex" ("id");

ALTER TABLE "account_details" ADD FOREIGN KEY ("country_id") REFERENCES "country" ("serial");

ALTER TABLE "account_details" ADD FOREIGN KEY ("contract_type_id") REFERENCES "contract_type" ("id");

ALTER TABLE "project_details" ADD FOREIGN KEY ("billing_period_id") REFERENCES "billing_period" ("id");
