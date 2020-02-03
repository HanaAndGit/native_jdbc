-- 우리회사
DROP SCHEMA IF EXISTS native_jdbc_study;

-- 우리회사
CREATE SCHEMA native_jdbc_study;

-- 부서
CREATE TABLE native_jdbc_study.department (
	deptno   INT(11)  NOT NULL COMMENT '부서번호', -- 부서번호
	deptname CHAR(10) NULL     COMMENT '부서명', -- 부서명
	floor    INT(11)  NULL     DEFAULT 0 COMMENT '위치' -- 위치
)
COMMENT '부서';

-- 부서
ALTER TABLE native_jdbc_study.department
	ADD CONSTRAINT
		PRIMARY KEY (
			deptno -- 부서번호
		);

ALTER TABLE native_jdbc_study.department
	MODIFY COLUMN deptno INT(11) NOT NULL AUTO_INCREMENT COMMENT '부서번호';

ALTER TABLE native_jdbc_study.department
	AUTO_INCREMENT = 6;

-- 사원
CREATE TABLE native_jdbc_study.employee (
	empno   INT(11)     NOT NULL COMMENT '사원번호', -- 사원번호
	empname VARCHAR(20) NULL     COMMENT '사원명', -- 사원명
	title   VARCHAR(10) NULL     DEFAULT '사원' COMMENT '직책', -- 직책
	manager INT(11)     NULL     COMMENT '직속상사', -- 직속상사
	salary  INT(11)     NULL     COMMENT '급여', -- 급여
	dno     INT(11)     NULL     DEFAULT 1 COMMENT '부서', -- 부서
	pic     LONGBLOB    NULL     COMMENT '증명사진' -- 증명사진
)
COMMENT '사원';

-- 사원
ALTER TABLE native_jdbc_study.employee
	ADD CONSTRAINT
		PRIMARY KEY (
			empno -- 사원번호
		);

-- empname
CREATE UNIQUE INDEX empname
	ON native_jdbc_study.employee ( -- 사원
		empname -- 사원명
	);

-- 사원
ALTER TABLE native_jdbc_study.employee
	ADD CONSTRAINT employee_ibfk_1 -- employee_ibfk_1
		FOREIGN KEY (
			manager -- 직속상사
		)
		REFERENCES native_jdbc_study.employee ( -- 사원
			empno -- 사원번호
		),
	ADD INDEX manager (
		manager -- 직속상사
	);

-- 사원
ALTER TABLE native_jdbc_study.employee
	ADD CONSTRAINT employee_ibfk_2 -- employee_ibfk_2
		FOREIGN KEY (
			dno -- 부서
		)
		REFERENCES native_jdbc_study.department ( -- 부서
			deptno -- 부서번호
		),
	ADD INDEX dno (
		dno -- 부서
	);