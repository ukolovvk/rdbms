CREATE TABLE groups (
    group_id Int64,
    group_number String
)
ENGINE = MergeTree()
ORDER BY group_id;

INSERT INTO groups VALUES (1, '1111-1111');
INSERT INTO groups VALUES (2, '1122-1122');
INSERT INTO groups VALUES (3, '1133-1133');

CREATE TABLE students (
  student_id Int64,
  first_name String,
  last_name String,
  average_estimate Float64,
  group_id Int64
)
ENGINE = MergeTree()
ORDER BY  student_id;

INSERT INTO students VALUES (1, 'fname1', 'lname1', 4.32, 1);
INSERT INTO students VALUES (2, 'fname2', 'lname2', 4.21, 1);
INSERT INTO students VALUES (3, 'fname3', 'lname3', 3.82, 1);
INSERT INTO students VALUES (4, 'fname4', 'lname4', 4.92, 1);
INSERT INTO students VALUES (5, 'fname5', 'lname5', 3.15, 1);
INSERT INTO students VALUES (6, 'fname6', 'lname6', 2.98, 1);
INSERT INTO students VALUES (7, 'fname7', 'lname7', 4.08, 1);
INSERT INTO students VALUES (8, 'fname8', 'lname8', 3.54, 1);
INSERT INTO students VALUES (9, 'fname9', 'lname9', 4.79, 1);
INSERT INTO students VALUES (10, 'fname10', 'lname10', 3.46, 1);

INSERT INTO students VALUES (11, 'fname11', 'lname11', 4.24, 2);
INSERT INTO students VALUES (12, 'fname12', 'lname12', 3.18, 2);
INSERT INTO students VALUES (13, 'fname13', 'lname13', 4.04, 2);
INSERT INTO students VALUES (14, 'fname14', 'lname14', 4.09, 2);
INSERT INTO students VALUES (15, 'fname15', 'lname15', 3.91, 2);
INSERT INTO students VALUES (16, 'fname16', 'lname16', 3.77, 2);
INSERT INTO students VALUES (17, 'fname17', 'lname17', 4.78, 2);
INSERT INTO students VALUES (18, 'fname18', 'lname18', 2.54, 2);
INSERT INTO students VALUES (19, 'fname19', 'lname19', 2.79, 2);
INSERT INTO students VALUES (20, 'fname20', 'lname20', 3.55, 2);

INSERT INTO students VALUES (21, 'fname21', 'lname21', 4.00, 3);
INSERT INTO students VALUES (22, 'fname22', 'lname22', 4.12, 3);
INSERT INTO students VALUES (23, 'fname23', 'lname23', 5.00, 3);
INSERT INTO students VALUES (24, 'fname24', 'lname24', 4.99, 3);
INSERT INTO students VALUES (25, 'fname25', 'lname25', 3.69, 3);
INSERT INTO students VALUES (26, 'fname26', 'lname26', 2.66, 3);
INSERT INTO students VALUES (27, 'fname27', 'lname27', 4.27, 3);
INSERT INTO students VALUES (28, 'fname28', 'lname28', 3.32, 3);
INSERT INTO students VALUES (29, 'fname29', 'lname29', 2.21, 3);
INSERT INTO students VALUES (30, 'fname30', 'lname30', 4.47, 3);

select first_name, last_name, average_estimate, group_number from students
inner join groups g on students.group_id = g.group_id order by average_estimate DESC
limit 5 by group_id;




drop table if exists replacing_ex_table;

create table replacing_ex_table (
    key Int64,
    first_field String,
    second_field String
)
ENGINE = ReplacingMergeTree()
ORDER BY key;

INSERT INTO replacing_ex_table VALUES (1, 'first_field_1', 'second_field_1');
INSERT INTO replacing_ex_table VALUES (1, 'first_field_2', 'second_field_2');
INSERT INTO replacing_ex_table VALUES (1, 'first_field_2', 'second_field_3');
INSERT INTO replacing_ex_table VALUES (1, 'first_field_4', 'second_field_5');
INSERT INTO replacing_ex_table VALUES (1, 'first_field_4', 'second_field_5');

SELECT * FROM replacing_ex_table FINAL;




drop table if exists collapsing_ex_table;

create table collapsing_ex_table (
     key Int64,
     field String,
     sign Int8
)
ENGINE = CollapsingMergeTree(sign)
ORDER BY key;

INSERT INTO collapsing_ex_table VALUES (1, 'field_1', 1);
INSERT INTO collapsing_ex_table VALUES (1, 'field_1', -1);
INSERT INTO collapsing_ex_table VALUES (1, 'field_2', 1);
INSERT INTO collapsing_ex_table VALUES (1, 'field_2', -1);
INSERT INTO collapsing_ex_table VALUES (1, 'res_field', 1);

SELECT * FROM collapsing_ex_table FINAL;