DROP TABLE IF EXISTS kpis;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS rules;

create table kpis
(
    id integer not null primary key,
    "phase" text COLLATE pg_catalog."default",
    "timestamp" timestamp without time zone,
    "failed" boolean,
    "location_id" integer
);

create table locations
(
    id integer not null primary key,
    "code" text COLLATE pg_catalog."default"
);

create table rules
(
    id integer not null primary key,
    "content" text COLLATE pg_catalog."default",
    "create_time" text COLLATE pg_catalog."default",
    "last_modify_time" text COLLATE pg_catalog."default",
    "rule_key" text COLLATE pg_catalog."default",
    "version" text COLLATE pg_catalog."default"
);

ALTER TABLE IF EXISTS public.rules
    ADD CONSTRAINT "rule key" UNIQUE (rule_key);