SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';
SET default_table_access_method = heap;

-- Table: users
CREATE TABLE public.users (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    fio character varying(150) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    emergency_contact character varying(150),
    role character varying(20) NOT NULL,
    CONSTRAINT chk_user_role CHECK (((role)::text = ANY ((ARRAY['Пациент'::character varying, 'Родственник'::character varying, 'Сиделка'::character varying])::text[])))
);
ALTER TABLE public.users OWNER TO postgres;

-- Table: devices
CREATE TABLE public.devices (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    device_name character varying(100) NOT NULL,
    last_session timestamp with time zone NOT NULL,
    user_id uuid NOT NULL
);
ALTER TABLE public.devices OWNER TO postgres;

-- Table: health_metrics
CREATE TABLE public.health_metrics (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    measured_at timestamp with time zone NOT NULL,
    temperature numeric(4,2),
    pulse integer,
    user_id uuid NOT NULL,
    CONSTRAINT chk_metrics_pulse CHECK (((pulse >= 30) AND (pulse <= 200))),
    CONSTRAINT chk_metrics_temperature CHECK (((temperature >= 32.0) AND (temperature <= 43.0)))
);
ALTER TABLE public.health_metrics OWNER TO postgres;

-- Table: medications
CREATE TABLE public.medications (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(100) NOT NULL
);
ALTER TABLE public.medications OWNER TO postgres;

-- Table: medication_plans
CREATE TABLE public.medication_plans (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    dosage character varying(50) NOT NULL,
    schedule character varying(150) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    is_tolerated boolean DEFAULT true,
    user_id uuid NOT NULL,
    medication_id uuid NOT NULL


);
ALTER TABLE public.medication_plans OWNER TO postgres;

-- Table: medication_history
CREATE TABLE public.medication_history (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    taken_at timestamp with time zone NOT NULL,
    status character varying(20) NOT NULL,
    plan_id uuid NOT NULL,
    CONSTRAINT chk_status CHECK (((status)::text = ANY ((ARRAY['Принято'::character varying, 'Пропущено'::character varying])::text[])))
);
ALTER TABLE public.medication_history OWNER TO postgres;

-- Constraints
ALTER TABLE ONLY public.devices ADD CONSTRAINT devices_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.health_metrics ADD CONSTRAINT health_metrics_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.medication_history ADD CONSTRAINT medication_history_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.medication_plans ADD CONSTRAINT medication_plans_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.medications ADD CONSTRAINT medications_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.medications ADD CONSTRAINT uq_medication_name UNIQUE (name);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_email_key UNIQUE (email);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_pkey PRIMARY KEY (id);

-- Foreign Keys
ALTER TABLE ONLY public.devices ADD CONSTRAINT fk_devices_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.health_metrics ADD CONSTRAINT fk_metrics_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.medication_history ADD CONSTRAINT fk_plan_id FOREIGN KEY (plan_id) REFERENCES public.medication_plans(id) ON DELETE CASCADE;
ALTER TABLE ONLY public.medication_plans ADD CONSTRAINT fk_plan_medication FOREIGN KEY (medication_id) REFERENCES public.medications(id) ON DELETE RESTRICT;
ALTER TABLE ONLY public.medication_plans ADD CONSTRAINT fk_plan_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
