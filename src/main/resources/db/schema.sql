DROP TABLE IF EXISTS kpis;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS rules;

create table kpis
(
    id            integer                           not null primary key,
    "phase"       text COLLATE pg_catalog."default",
    "session_id"  text COLLATE pg_catalog."default" not null,
    "timestamp"   integer,
    "failed"      boolean,
    "location_id" integer,
    "latency"     double precision
);

create table locations
(
    id     integer not null primary key,
    "code" text COLLATE pg_catalog."default"
);

create table rules
(
    id                 integer not null primary key,
    "content"          text COLLATE pg_catalog."default",
    "create_time"      text COLLATE pg_catalog."default",
    "last_modify_time" text COLLATE pg_catalog."default",
    "rule_key"         text COLLATE pg_catalog."default",
    "version"          text COLLATE pg_catalog."default"
);

create table alerts
(
    id                 integer not null primary key,
    "agent_id"          text COLLATE pg_catalog."default",
    "alert_id"          text COLLATE pg_catalog."default",
    "agent_test_name"    text COLLATE pg_catalog."default",
    "test_session_id"    text COLLATE pg_catalog."default",
    "test_id"           text COLLATE pg_catalog."default",
    "agent_test_id"      text COLLATE pg_catalog."default",
    "workflow_id"       text COLLATE pg_catalog."default",
    "overlay_id"        text COLLATE pg_catalog."default",
    "network_element_id" text COLLATE pg_catalog."default",
    "category"         text COLLATE pg_catalog."default",
    "package"          text COLLATE pg_catalog."default",
    "test_name"             text COLLATE pg_catalog."default",
    "alert_name"             text COLLATE pg_catalog."default",
    "level"            text COLLATE pg_catalog."default",
    "timestamp"        text COLLATE pg_catalog."default",
    CONSTRAINT alert_id_unique UNIQUE ("alert_id")

);

create table failed_kpis
(
    id          integer not null primary key,
    "latency"   text COLLATE pg_catalog."default",
    "threshold" text COLLATE pg_catalog."default"
    "alert_id"  integer not null,
    CONSTRAINT alert_id_fk FOREIGN KEY (alert_id)
        REFERENCES public.alerts (id)

);

ALTER TABLE IF EXISTS public.rules
    ADD CONSTRAINT "rule key" UNIQUE (rule_key);