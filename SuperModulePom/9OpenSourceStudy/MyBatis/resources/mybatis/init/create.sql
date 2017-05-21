CREATE TABLE IF NOT EXISTS start.student (
  sId   INT(10)      NOT NULL AUTO_INCREMENT,
  sName VARCHAR(100) NOT NULL,
  sSex  VARCHAR(6)   NOT NULL,
  PRIMARY KEY (sId)
);

CREATE TABLE IF NOT EXISTS start.course (
  cId   INT(10)     NOT NULL AUTO_INCREMENT,
  cName VARCHAR(10) NOT NULL UNIQUE,
  PRIMARY KEY (cId)
);

CREATE TABLE IF NOT EXISTS start.student_course (
  cId INT(10),
  sId INT(10),
  CONSTRAINT `student_course_fk_1` FOREIGN KEY (`cId`) REFERENCES course (`cId`),
  CONSTRAINT `student_course_fk_2` FOREIGN KEY (`sId`) REFERENCES student (`sId`)
);
