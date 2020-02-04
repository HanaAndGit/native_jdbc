select user(), database ();

select * from product p;

-- jdbc_coffeeTest
DROP SCHEMA IF EXISTS MY_SCHEMA;

-- jdbc_coffeeTest
CREATE SCHEMA MY_SCHEMA;

-- 제품
CREATE TABLE study.product (
	code VARCHAR(4)  NOT NULL COMMENT '제품코드', -- 제품코드
	name VARCHAR(10) NULL     COMMENT '제품이름' -- 제품이름
)
COMMENT '제품';



-- 제품
ALTER TABLE study.product
	ADD CONSTRAINT PK_product -- 제품 기본키
		PRIMARY KEY (
			code -- 제품코드
		);

-- 판매
CREATE TABLE study.sale (
	no         INTEGER    NOT NULL COMMENT '번호', -- 번호
	s_code     VARCHAR(4) NOT NULL COMMENT '제품코드', -- 제품코드
	price      INTEGER    NULL     COMMENT '제품가격', -- 제품가격
	salecnt    INTEGER    NULL     COMMENT '판매량', -- 판매량
	marginRate INTEGER    NULL     COMMENT '마진' -- 마진
)
COMMENT '판매';

-- 판매
ALTER TABLE study.sale
	ADD CONSTRAINT PK_sale -- 판매 기본키
		PRIMARY KEY (
			no,     -- 번호
			s_code  -- 제품코드
		);

ALTER TABLE study.sale
	MODIFY COLUMN no INTEGER NOT NULL AUTO_INCREMENT COMMENT '번호';

ALTER TABLE study.sale
	AUTO_INCREMENT = 1;

-- 판매
ALTER TABLE study.sale
	ADD CONSTRAINT FK_product_TO_sale -- 제품 -> 판매
		FOREIGN KEY (
			s_code -- 제품코드
		)
		REFERENCES MY_SCHEMA.product ( -- 제품
			code -- 제품코드
		);