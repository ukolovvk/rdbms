DROP SEQUENCE IF EXISTS positions_id_seq;
DROP SEQUENCE IF EXISTS employees_id_seq;

DROP TABLE IF EXISTS positions;
DROP TABLE IF EXISTS employees;

CREATE SEQUENCE positions_id_seq
    INCREMENT 1
    START 1;

CREATE SEQUENCE employees_id_seq
    INCREMENT 1
    START 1;

CREATE TABLE positions (
       id INTEGER PRIMARY KEY,
       position VARCHAR(50) NOT NULL
);

CREATE TABLE employees (
       id INTEGER PRIMARY KEY,
       first_name VARCHAR(40) NOT NULL,
       last_name VARCHAR(40) NOT NULL,
       position_id INTEGER NOT NULL,
       salary NUMERIC NOT NULL,
       manager_id INTEGER,
       FOREIGN KEY (manager_id) REFERENCES employees(id),
       FOREIGN KEY (position_id) REFERENCES positions(id)
);

INSERT INTO positions VALUES (nextval('positions_id_seq'),'dev');
INSERT INTO positions VALUES (nextval('positions_id_seq'),'qa');
INSERT INTO positions VALUES (nextval('positions_id_seq'),'analysist');

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_1',
     'first_last_name_1',
     1,
     100,
     null
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_2',
     'last_name_2',
     1,
     50,
     1
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_3',
     'last_name_3',
     2,
     40,
     1
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_4',
     'last_name_4',
     3,
     60,
     2
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_5',
     'last_name_5',
     1,
     55,
     2
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_6',
     'last_name_6',
     2,
     35,
     1
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_7',
     'last_name_7',
     1,
     65,
     2
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_8',
     'last_name_8',
     3,
     40,
     3
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_9',
     'last_name_9',
     1,
     60,
     1
 );

INSERT INTO employees VALUES (
     nextval('employees_id_seq'),
     'first_name_10',
     'last_name_10',
     2,
     45,
     3
 );