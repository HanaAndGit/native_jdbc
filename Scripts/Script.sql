select user(), database();
select * from department where deptno = 1;
select * from employee where dno = 1;

select e.empno, e.empname, e.title, m.empno as manager_no, m.empname as manager_name, e.salary, e.dno, d.deptname
	  from employee e left join employee m on e.manager = m.empno join department d on e.dno = d.deptno
	  where deptno = 1;
	  
select dno
	from employee where empname = "이성래";

select * from department d2 ;	
insert into department values(6, "총무", 0);
update department set deptname = '총무2', floor = 10 
	where deptno = 6;
delete from department where deptno = 6;

insert into department values(6, '총무', 10);
