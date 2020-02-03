/*select user(), database ();*/

-- 내 스키마
DROP SCHEMA IF EXISTS native_jdbc_study;

-- 내 스키마
CREATE SCHEMA native_jdbc_study;

-- 명함
CREATE TABLE native_jdbc_study.customer (
	nameCard INTEGER     NOT NULL COMMENT '명함번호', -- 명함번호
	name     VARCHAR(50) NOT NULL COMMENT '고객 이름', -- 고객 이름
	dept     VARCHAR(50) NULL     COMMENT '부서 이름', -- 부서 이름
	title    VARCHAR(50) NULL     COMMENT '직급', -- 직급
	tel      VARCHAR(30) NULL     COMMENT '전화번호', -- 전화번호
	fax      VARCHAR(30) NULL     COMMENT 'fax 번호', -- fax 번호
	email    VARCHAR(40) NOT NULL COMMENT '이메일', -- 이메일
	conum    INTEGER     NULL     COMMENT '회사번호' -- 회사번호
)
COMMENT '명함';

-- 명함
ALTER TABLE native_jdbc_study.customer
	ADD CONSTRAINT PK_customer -- 명함 기본키
		PRIMARY KEY (
			nameCard -- 명함번호
		);

-- 회사
CREATE TABLE native_jdbc_study.company (
	conum   INTEGER      NOT NULL COMMENT '회사번호', -- 회사번호
	comname VARCHAR(50)  NULL     COMMENT '회사 이름', -- 회사 이름
	comadd  VARCHAR(255) NULL     COMMENT '회사 주소', -- 회사 주소
	comrep  VARCHAR(50)  NULL     COMMENT '대표 이름' -- 대표 이름
)
COMMENT '회사';

-- 회사
ALTER TABLE native_jdbc_study.company
	ADD CONSTRAINT PK_company -- 회사 기본키
		PRIMARY KEY (
			conum -- 회사번호
		);

-- 명함
ALTER TABLE native_jdbc_study.customer
	ADD CONSTRAINT FK_company_TO_customer -- 회사 -> 명함
		FOREIGN KEY (
			conum -- 회사번호
		)
		REFERENCES native_jdbc_study.company ( -- 회사
			conum -- 회사번호
		);
	
-- create user
drop user if exists 'user_native_jdbc_study'@'localhost';
grant all privileges on native_jdbc_study.* to 'user_native_jdbc_study'@'localhost' identified by 'rootroot';
flush privileges;