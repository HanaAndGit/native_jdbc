use native_jdbc_study;
select user(), database ();
select * from employee;
select * from department;

update employee set empno = 1004, empname = '서현진', title = '사원', manager = 1003, salary = 1500000, dno = 1 
	where empno = 1004;
/*
SELECT * FROM department;
select deptno, deptname, floor from department where deptno =3;
SELECT * FROM employee;
*/


select deptno, deptname, floor from department;
desc employee;

select empno, empname, title, manager, salary, dno 
	  from employee
	  where dno = 1;
select empno, empname, title, manager, salary, dno from employee;

insert into department values
(1, '영업', 8),
(2, '기획', 10),
(3, '개발', 9),
(4, '총무', 7);

-- employee

insert into employee(empno, empname, title, manager, salary, dno) values
(4377, '이성래', '사장', null, 5000000, 2),
(3426, '박영권', '과장', 4377, 3000000, 1),
(1003, '조민희', '과장', 4377, 3000000, 2),
(3011, '이수민', '부장', 4377, 4000000, 3),
(1365, '김상원', '사원', 3426, 1500000, 1),
(2106, '김창섭', '대리', 1003, 2500000, 2),
(3427, '최종철', '사원', 3011, 1500000, 3);

select * from department;

insert into department values(5, "마케팅", 8);
update department set deptname = "마케팅2", floor = 7 where deptno = 0;
delete from department where deptno = 7;
select * from employee;




drop procedure if exists native_jdbc_study.procedure01;

delimiter $$
$$
create procedure native_jdbc_study.procedure01(
	in in_dno int
	)
begin
	select empno, empname, title, manager, salary, dno
		from employee
	where dno = in_dno;
end$$
delimiter ;

