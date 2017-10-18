--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.9
-- Dumped by pg_dump version 9.5.9

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: camas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE camas (
    id integer NOT NULL,
    numcama text NOT NULL,
    id_cuarto integer NOT NULL,
    id_paciente integer
);


ALTER TABLE camas OWNER TO postgres;

--
-- Name: camas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE camas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE camas_id_seq OWNER TO postgres;

--
-- Name: camas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE camas_id_seq OWNED BY camas.id;


--
-- Name: cuartos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE cuartos (
    id integer NOT NULL,
    numcuarto text NOT NULL
);


ALTER TABLE cuartos OWNER TO postgres;

--
-- Name: cuartos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cuartos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cuartos_id_seq OWNER TO postgres;

--
-- Name: cuartos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cuartos_id_seq OWNED BY cuartos.id;


--
-- Name: enfermeros; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE enfermeros (
    id integer NOT NULL,
    nombre text NOT NULL
);


ALTER TABLE enfermeros OWNER TO postgres;

--
-- Name: enfermeros_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE enfermeros_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE enfermeros_id_seq OWNER TO postgres;

--
-- Name: enfermeros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE enfermeros_id_seq OWNED BY enfermeros.id;


--
-- Name: envasesmedicina; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE envasesmedicina (
    id integer NOT NULL,
    nombrecomercial text NOT NULL,
    fechasurtimiento date NOT NULL,
    presentacion text NOT NULL,
    cantidad double precision NOT NULL,
    id_medicamento integer,
    id_paciente_dueno integer
);


ALTER TABLE envasesmedicina OWNER TO postgres;

--
-- Name: envasesmedicina_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE envasesmedicina_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE envasesmedicina_id_seq OWNER TO postgres;

--
-- Name: envasesmedicina_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE envasesmedicina_id_seq OWNED BY envasesmedicina.id;


--
-- Name: eventos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE eventos (
    id integer NOT NULL,
    descripcion text DEFAULT ' '::text,
    asunto text DEFAULT ' '::text,
    fecha date NOT NULL,
    id_enfermero integer,
    id_paciente integer
);


ALTER TABLE eventos OWNER TO postgres;

--
-- Name: eventos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE eventos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE eventos_id_seq OWNER TO postgres;

--
-- Name: eventos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE eventos_id_seq OWNED BY eventos.id;


--
-- Name: familiaresresponsables; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE familiaresresponsables (
    id integer NOT NULL,
    nombre text NOT NULL,
    relacion text NOT NULL,
    telefono text NOT NULL,
    id_paciente integer
);


ALTER TABLE familiaresresponsables OWNER TO postgres;

--
-- Name: familiaresresponsables_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE familiaresresponsables_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE familiaresresponsables_id_seq OWNER TO postgres;

--
-- Name: familiaresresponsables_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE familiaresresponsables_id_seq OWNED BY familiaresresponsables.id;


--
-- Name: medicamentos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE medicamentos (
    id integer NOT NULL,
    nombregenerico text NOT NULL,
    dosis double precision NOT NULL
);


ALTER TABLE medicamentos OWNER TO postgres;

--
-- Name: medicamentos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE medicamentos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE medicamentos_id_seq OWNER TO postgres;

--
-- Name: medicamentos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE medicamentos_id_seq OWNED BY medicamentos.id;


--
-- Name: pacientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pacientes (
    id integer NOT NULL,
    nombre text NOT NULL,
    numpoliza text NOT NULL,
    fechanacimiento date NOT NULL,
    id_servicio_emergencia integer
);


ALTER TABLE pacientes OWNER TO postgres;

--
-- Name: pacientes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pacientes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pacientes_id_seq OWNER TO postgres;

--
-- Name: pacientes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pacientes_id_seq OWNED BY pacientes.id;


--
-- Name: pacientetomamedicamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pacientetomamedicamento (
    id_paciente integer NOT NULL,
    id_medicamento integer NOT NULL,
    horatomada timestamp without time zone NOT NULL,
    fechainicio date NOT NULL,
    duracion double precision NOT NULL
);


ALTER TABLE pacientetomamedicamento OWNER TO postgres;

--
-- Name: padecimientos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE padecimientos (
    id integer NOT NULL,
    nombrepadecimiento text NOT NULL
);


ALTER TABLE padecimientos OWNER TO postgres;

--
-- Name: padecimientos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE padecimientos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE padecimientos_id_seq OWNER TO postgres;

--
-- Name: padecimientos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE padecimientos_id_seq OWNED BY padecimientos.id;


--
-- Name: padecimientos_pacientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE padecimientos_pacientes (
    id_padecimiento integer NOT NULL,
    id_paciente integer NOT NULL
);


ALTER TABLE padecimientos_pacientes OWNER TO postgres;

--
-- Name: servicio_de_emergencia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE servicio_de_emergencia (
    id integer NOT NULL,
    nombre text NOT NULL,
    direccion text NOT NULL,
    telefono text NOT NULL
);


ALTER TABLE servicio_de_emergencia OWNER TO postgres;

--
-- Name: servicio_de_emergencia_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE servicio_de_emergencia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE servicio_de_emergencia_id_seq OWNER TO postgres;

--
-- Name: servicio_de_emergencia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE servicio_de_emergencia_id_seq OWNED BY servicio_de_emergencia.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY camas ALTER COLUMN id SET DEFAULT nextval('camas_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cuartos ALTER COLUMN id SET DEFAULT nextval('cuartos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enfermeros ALTER COLUMN id SET DEFAULT nextval('enfermeros_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY envasesmedicina ALTER COLUMN id SET DEFAULT nextval('envasesmedicina_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eventos ALTER COLUMN id SET DEFAULT nextval('eventos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familiaresresponsables ALTER COLUMN id SET DEFAULT nextval('familiaresresponsables_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY medicamentos ALTER COLUMN id SET DEFAULT nextval('medicamentos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pacientes ALTER COLUMN id SET DEFAULT nextval('pacientes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY padecimientos ALTER COLUMN id SET DEFAULT nextval('padecimientos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY servicio_de_emergencia ALTER COLUMN id SET DEFAULT nextval('servicio_de_emergencia_id_seq'::regclass);


--
-- Data for Name: camas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY camas (id, numcama, id_cuarto, id_paciente) FROM stdin;
\.


--
-- Name: camas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('camas_id_seq', 1, false);


--
-- Data for Name: cuartos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cuartos (id, numcuarto) FROM stdin;
\.


--
-- Name: cuartos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cuartos_id_seq', 1, false);


--
-- Data for Name: enfermeros; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY enfermeros (id, nombre) FROM stdin;
\.


--
-- Name: enfermeros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('enfermeros_id_seq', 1, false);


--
-- Data for Name: envasesmedicina; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY envasesmedicina (id, nombrecomercial, fechasurtimiento, presentacion, cantidad, id_medicamento, id_paciente_dueno) FROM stdin;
\.


--
-- Name: envasesmedicina_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('envasesmedicina_id_seq', 1, false);


--
-- Data for Name: eventos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY eventos (id, descripcion, asunto, fecha, id_enfermero, id_paciente) FROM stdin;
\.


--
-- Name: eventos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('eventos_id_seq', 1, false);


--
-- Data for Name: familiaresresponsables; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY familiaresresponsables (id, nombre, relacion, telefono, id_paciente) FROM stdin;
\.


--
-- Name: familiaresresponsables_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('familiaresresponsables_id_seq', 1, false);


--
-- Data for Name: medicamentos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY medicamentos (id, nombregenerico, dosis) FROM stdin;
\.


--
-- Name: medicamentos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('medicamentos_id_seq', 1, false);


--
-- Data for Name: pacientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pacientes (id, nombre, numpoliza, fechanacimiento, id_servicio_emergencia) FROM stdin;
\.


--
-- Name: pacientes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pacientes_id_seq', 1, false);


--
-- Data for Name: pacientetomamedicamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pacientetomamedicamento (id_paciente, id_medicamento, horatomada, fechainicio, duracion) FROM stdin;
\.


--
-- Data for Name: padecimientos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY padecimientos (id, nombrepadecimiento) FROM stdin;
\.


--
-- Name: padecimientos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('padecimientos_id_seq', 1, false);


--
-- Data for Name: padecimientos_pacientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY padecimientos_pacientes (id_padecimiento, id_paciente) FROM stdin;
\.


--
-- Data for Name: servicio_de_emergencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY servicio_de_emergencia (id, nombre, direccion, telefono) FROM stdin;
\.


--
-- Name: servicio_de_emergencia_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('servicio_de_emergencia_id_seq', 1, false);


--
-- Name: camas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY camas
    ADD CONSTRAINT camas_pkey PRIMARY KEY (id);


--
-- Name: cuartos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cuartos
    ADD CONSTRAINT cuartos_pkey PRIMARY KEY (id);


--
-- Name: enfermeros_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY enfermeros
    ADD CONSTRAINT enfermeros_pkey PRIMARY KEY (id);


--
-- Name: envasesmedicina_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY envasesmedicina
    ADD CONSTRAINT envasesmedicina_pkey PRIMARY KEY (id);


--
-- Name: eventos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eventos
    ADD CONSTRAINT eventos_pkey PRIMARY KEY (id);


--
-- Name: familiaresresponsables_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familiaresresponsables
    ADD CONSTRAINT familiaresresponsables_pkey PRIMARY KEY (id);


--
-- Name: medicamentos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY medicamentos
    ADD CONSTRAINT medicamentos_pkey PRIMARY KEY (id);


--
-- Name: pacientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pacientes
    ADD CONSTRAINT pacientes_pkey PRIMARY KEY (id);


--
-- Name: pacientetomamedicamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pacientetomamedicamento
    ADD CONSTRAINT pacientetomamedicamento_pkey PRIMARY KEY (id_paciente, id_medicamento);


--
-- Name: padecimientos_pacientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY padecimientos_pacientes
    ADD CONSTRAINT padecimientos_pacientes_pkey PRIMARY KEY (id_padecimiento, id_paciente);


--
-- Name: padecimientos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY padecimientos
    ADD CONSTRAINT padecimientos_pkey PRIMARY KEY (id);


--
-- Name: servicio_de_emergencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY servicio_de_emergencia
    ADD CONSTRAINT servicio_de_emergencia_pkey PRIMARY KEY (id);


--
-- Name: camas_id_cuarto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY camas
    ADD CONSTRAINT camas_id_cuarto_fkey FOREIGN KEY (id_cuarto) REFERENCES cuartos(id);


--
-- Name: camas_id_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY camas
    ADD CONSTRAINT camas_id_paciente_fkey FOREIGN KEY (id_paciente) REFERENCES pacientes(id);


--
-- Name: envasesmedicina_id_medicamento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY envasesmedicina
    ADD CONSTRAINT envasesmedicina_id_medicamento_fkey FOREIGN KEY (id_medicamento) REFERENCES medicamentos(id);


--
-- Name: envasesmedicina_id_paciente_dueno_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY envasesmedicina
    ADD CONSTRAINT envasesmedicina_id_paciente_dueno_fkey FOREIGN KEY (id_paciente_dueno) REFERENCES pacientes(id);


--
-- Name: eventos_id_enfermero_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eventos
    ADD CONSTRAINT eventos_id_enfermero_fkey FOREIGN KEY (id_enfermero) REFERENCES enfermeros(id);


--
-- Name: eventos_id_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eventos
    ADD CONSTRAINT eventos_id_paciente_fkey FOREIGN KEY (id_paciente) REFERENCES pacientes(id);


--
-- Name: familiaresresponsables_id_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY familiaresresponsables
    ADD CONSTRAINT familiaresresponsables_id_paciente_fkey FOREIGN KEY (id_paciente) REFERENCES pacientes(id);


--
-- Name: pacientes_id_servicio_emergencia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pacientes
    ADD CONSTRAINT pacientes_id_servicio_emergencia_fkey FOREIGN KEY (id_servicio_emergencia) REFERENCES servicio_de_emergencia(id);


--
-- Name: pacientetomamedicamento_id_medicamento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pacientetomamedicamento
    ADD CONSTRAINT pacientetomamedicamento_id_medicamento_fkey FOREIGN KEY (id_medicamento) REFERENCES medicamentos(id);


--
-- Name: pacientetomamedicamento_id_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pacientetomamedicamento
    ADD CONSTRAINT pacientetomamedicamento_id_paciente_fkey FOREIGN KEY (id_paciente) REFERENCES pacientes(id);


--
-- Name: padecimientos_pacientes_id_paciente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY padecimientos_pacientes
    ADD CONSTRAINT padecimientos_pacientes_id_paciente_fkey FOREIGN KEY (id_paciente) REFERENCES pacientes(id);


--
-- Name: padecimientos_pacientes_id_padecimiento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY padecimientos_pacientes
    ADD CONSTRAINT padecimientos_pacientes_id_padecimiento_fkey FOREIGN KEY (id_padecimiento) REFERENCES padecimientos(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

