CREATE TABLE school.Student (
	id varchar(100) NOT NULL,
	firstName varchar(100) NOT NULL,
	lastName varchar(100) NOT NULL,
	CONSTRAINT student_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

CREATE TABLE school.Grade (
	id INT auto_increment NOT NULL,
	student_id varchar(100) NOT NULL,
	subject_id varchar(100) NOT NULL,
	grade INT NOT NULL,
	CONSTRAINT Grade_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

CREATE TABLE school.Subject (
	id varchar(100) NOT NULL,
	name varchar(100) NOT NULL,
	factor INT NOT NULL,
	CONSTRAINT Subject_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;


