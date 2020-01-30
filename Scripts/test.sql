select user(), database ();

select deptno, deptname, floor from department;
desc employee;

select empno, empname, title, manager, salary, dno 
	  from employee
	  where dno = 1;
select empno, empname, title, manager, salary, dno from employee;
