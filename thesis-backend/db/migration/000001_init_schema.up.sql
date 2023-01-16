CREATE TABLE "account" (
                           "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                           "login" varchar NOT NULL,
                           "pass" varchar NOT NULL,
                           "status" varchar NOT NULL,
                           "email" varchar NOT NULL,
                           "position_id" uuid
);

CREATE TABLE "account_details" (
                                   "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                                   "account_id" uuid NOT NULL,
                                   "name" varchar NOT NULL,
                                   "middle_name" varchar,
                                   "surname" varchar NOT NULL,
                                   "pesel" varchar NOT NULL,
                                   "sex" varchar NOT NULL,
                                   "phone_number" varchar NOT NULL,
                                   "tax_number" varchar NOT NULL,
                                   "id_card_number" varchar,
                                   "street" varchar NOT NULL,
                                   "house_number" varchar,
                                   "apartment_number" varchar,
                                   "postal_code" varchar NOT NULL,
                                   "city" varchar NOT NULL,
                                   "country" varchar,
                                   "private_email" varchar,
                                   "employment_date" timestamp,
                                   "exit_date" timestamp,
                                   "birth_date" timestamp,
                                   "birth_place" varchar,
                                   "image_path" varchar,
                                   "contract_type" varchar,
                                   "working_time" INTEGER,
                                   "wage" INTEGER,
                                   "payday" INTEGER,
                                   "created_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "account_project" (

                                   "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                                   "account_id" uuid NOT NULL,
                                   "project_id" uuid NOT NULL,
                                   "role_id" uuid NOT NULL,
                                   "working_time" INTEGER,
                                   "join_date" timestamp NOT NULL,
                                   "exit_date" timestamp,
                                   "status" varchar NOT NULL
);

CREATE TABLE "position" (
                        "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                        "name" varchar,
                        "description" varchar,
                        "creation_date" timestamp NOT NULL,
                        "archive_date" timestamp,
                        "status" varchar
);

CREATE TABLE "account_role" (
                                "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                                "account_id" uuid NOT NULL,
                                "role_id" uuid NOT NULL
);

CREATE TABLE "role" (
                        "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                        "name" varchar NOT NULL
);

CREATE TABLE "privilege" (
                             "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                             "name" varchar NOT NULL
);

CREATE TABLE "role_privilege" (
                                  "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                                  "role_id" uuid NOT NULL,
                                  "privilege_id" uuid NOT NULL
);

CREATE TABLE "project" (
                           "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                           "name" varchar NOT NULL,
                           "description" varchar,
                           "owner_id" uuid NOT NULL
);

CREATE TABLE "project_details" (
                                   "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                                   "project_id" uuid NOT NULL,
                                   "options" varchar NOT NULL,
                                   "image_path" varchar,
                                   "created_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "task_form" (
                             "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                             "name" varchar NOT NULL,
                             "description" varchar,
                             "id_project" uuid NOT NULL,
                             "archive_date" timestamp,
                             "created_at" timestamp DEFAULT 'now()'
);

CREATE TABLE "task_form_details" (
                                     "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                                     "task_form_id" uuid NOT NULL ,
                                     "description" varchar,
                                     "status" varchar NOT NULL
);

CREATE TABLE "task" (
                        "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                        "account_id" uuid NOT NULL ,
                        "form_id" uuid NOT NULL ,
                        "created_at" timestamp DEFAULT 'now()',
                        "date_from" timestamp NOT NULL,
                        "date_to" timestamp NOT NULL,
                        "name" varchar,
                        "status" varchar NOT NULL
);

CREATE TABLE "account_message" (
                                   "id" uuid DEFAULT gen_random_uuid() PRIMARY KEY,
                                   "data" varchar,
                                   "account_from" uuid,
                                   "account_to" uuid,
                                   "created_at" timestamp DEFAULT 'now()'
);

CREATE INDEX ON "account" ("login");

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
