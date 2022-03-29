DROP TABLE IF EXISTS course;

CREATE TABLE course (
                        id INT AUTO_INCREMENT  PRIMARY KEY,
                        name VARCHAR(250) NOT NULL,
                        max_students INT NOT NULL DEFAULT 0,
                        current_students INT NOT NULL DEFAULT 0
);