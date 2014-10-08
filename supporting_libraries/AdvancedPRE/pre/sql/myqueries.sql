select * from process_request_schedule where sch_id = 3;
select * from process_request where sch_id =3;
select a.* from process_req_params a, process_request b where a.req_id = b.req_id and b.sch_id = 3;

select a.req_id, a.cal_scheduled_time, a.scheduled_time
from process_request a, process_request_schedule b, process_req_params c
where a.sch_id = b.sch_id
and   a.req_id = c.req_id
and   c.param_data_type IN ('DT', 'TS')
and   a.sch_id = 6
order by a.scheduled_time asc;

select req_id, cal_scheduled_time, scheduled_time
from process_request
where  sch_id = 6;

select * from process_request_schedule;
select * from process_request where req_stat = 'Q';

select * from process_request where sch_id = 3;

delete from process_req_params where req_id in (select req_id from process_request where req_id != 1 and sch_id = 2);
delete from process_request where req_id != 1 and sch_id = 2;

delete from process_req_params where req_id > 7;
delete from process_request where req_id > 7;

update process_request_schedule
set    sch_stat = 'A'
     , occur_counter = 1
     , skip_flag = 'D+'
where sch_id = 6;

update process_request_schedule
set    calendar_class_nm = 'com.myapp.preimpl.CHelloWorld';

update process_request
set req_stat = 'Q', sch_id = 6, cal_scheduled_time = '2008/01/01 00:00:00', scheduled_time = '2008/01/01 00:00:00'
where req_id = 1;

insert into process_request as (select 2, )
-- stop engine
update process_request
set req_stat = 'Q' where req_id = 3;
commit;
update process_request_schedule
set    weekday_check_flag = 'N';

INSERT INTO process_request_schedule
(SELECT 5,
      'DAY'
     , 2
     , '2008/01/01 00:00:00'
     , 'A'
     , user_id
     , '0000000'
     , end_dt
     , end_occur
     , entry_dt
     , modify_id
     , modify_dt
     , req_stat
     , 1
     , process_class_nm
     , null
     , null
     , 'N'
     , 'N'
     , null
     , 'D-'
     , 'Y'
     , calendar_class_nm 
 FROM process_request_schedule
 WHERE sch_id = 4
)
     
update process_request_schedule set end_dt = null, end_occur = 50 where sch_id = 5;

alter table process_req_params modify param_data_type varchar(3) not null


update process_request set process_class_nm = 'com.myapp.preimpl.CHelloWorldWithParameter'


Alter table process_request add column text1 varchar(50);
Alter table process_request add column text2 varchar(50);
Alter table process_request add column (num1 double(12,2), num2 double(12,2))





CREATE TABLE process_request_schedule (
    sch_id INT NOT NULL,
    freq_type VARCHAR(10) NOT NULL,
    recur INT NOT NULL,
    start_dt DATETIME NOT NULL,
    sch_stat VARCHAR(2) NOT NULL,
    user_id VARCHAR(15) NOT NULL,
    on_week_day VARCHAR(7),
    end_dt DATETIME,
    end_occur INT,
    entry_dt DATETIME,
    modify_id VARCHAR(10),
    modify_dt DATETIME,
    req_stat VARCHAR(2),
    occur_counter INT,
    process_class_nm VARCHAR(500),
    start_time DATETIME,
    end_time DATETIME,
    future_scheduling_only VARCHAR(1),
    fixed_date VARCHAR(1) DEFAULT 'N' NOT NULL,
    email_ids VARCHAR(2000),
    skip_flag VARCHAR(2),
    weekday_check_flag VARCHAR(2),
    PRIMARY KEY (sch_id)
);

CREATE TABLE schedule_event_calendar (
    sch_id INT NOT NULL,
    serial_no INT NOT NULL,
    category VARCHAR(10) NOT NULL,
    process_class_nm VARCHAR(500),
    PRIMARY KEY (sch_id,serial_no)
);


CREATE TABLE process_request (
    req_id INT NOT NULL,
    req_type VARCHAR(15) DEFAULT 'GENERAL' NOT NULL,
    user_id VARCHAR(15) NOT NULL,
    req_dt DATETIME NOT NULL,
    req_stat VARCHAR(2) NOT NULL,
    process_class_nm VARCHAR(500) NOT NULL,
    grp_st_ind VARCHAR(2) NOT NULL,
    req_start_dt DATETIME,
    req_end_dt DATETIME,
    grp_id INT,
    grp_req_seq_no INT,
    req_logfile_nm VARCHAR(500),
    job_id VARCHAR(100),
    job_name VARCHAR(500),
    scheduled_time DATETIME,
    sch_id INT,
    stuck_thread_limit INT,
    stuck_thread_max_limit INT,
    priority INT DEFAULT 99 NOT NULL,
    email_ids VARCHAR(2000),
    verbose_time_elapsed VARCHAR(100),
    cal_scheduled_time DATETIME,
    text1 VARCHAR(50),
    text2 VARCHAR(50),
    num1 DOUBLE(12,2),
    num2 DOUBLE(12,2),
    PRIMARY KEY (req_id)
);


CREATE TABLE process_req_params (
    req_id INT NOT NULL,
    param_no INT NOT NULL,
    param_fld VARCHAR(100) NOT NULL,
    param_val VARCHAR(100),
    param_data_type VARCHAR(3) NOT NULL,
    static_dynamic_flag VARCHAR(2),
    PRIMARY KEY (req_id,param_no)
);

CREATE TABLE request_id_sequence (
    req_id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (req_id)
);


ALTER TABLE process_req_params ADD CONSTRAINT process_req_params_ibfk_1 FOREIGN KEY (req_id)
    REFERENCES process_request (req_id);

ALTER TABLE process_request ADD CONSTRAINT process_request_ibfk_1 FOREIGN KEY (sch_id)
    REFERENCES process_request_schedule (sch_id);

    drop table process_req_params;
    drop table process_request;
    drop table schedule_event_calendar;
    drop table process_request_schedule;
    
//============================================

insert into process_request (select
7, req_type, user_id, req_dt, req_stat, 'com.myapp.preimpl.CHelloWorld', 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time, text1, text2, num1, num2 
from process_request where req_id = 1)


//======================================================


select * from PROCESS_REQUEST where process_class_nm like '%Processor'

update process_request set req_stat = 'Q' where req_id = 17208;
commit

select to_char(sysdate, 'mm/dd/yyyy hh24:mi:ss') from dual

select * from PROCESS_REQUEST where req_id = 17208


select * from configuration

create table conf as select * from configuration

update meta_data set exp_date = null where task_name = 'PostEventJob'

select * from meta_data

select * from object_map where object_type = 'JV'

select * from batch_executor where be_pre_post = 'POST'

select id id, object_name, object_type, default_values, on_fail_exit 
from object_map where sysdate between eff_date and nvl(exp_date, sysdate)

update meta_data set task_name = upper(task_name)

select * from meta_data

commit


        select  prx_sequence,
        	prx_parameters,
		prx_program_name,
		prx_dir_name,
		prx_output_dir_name||'/'||to_char(to_date(sysdate, 'dd-mon-yyyy hh24:mi:ss'),'yyyymmdd')||DECODE(prx_print_option,'NP','/NOPRINT') OUTPUT_PATH,
		prx_output_file_name OUTPUT_FILE, prx_connect
        from    print_executor
        where   --prx_execution_date <= 
				--to_date(sysdate, 'dd-mon-yyyy hh24:mi:ss')
        prx_status != 'CO'
		and		( prx_print_type is null
				or
				prx_print_type != 'ALW')
        order by prx_priority_code_1,prx_policy_no,prx_policy_renew_no,
                 prx_priority_code_2,prx_sequence;

                 
	SELECT pl_printbatch_no
	FROM   print_log
	WHERE  pl_trans_id = '03'
	AND    pl_batch_entry = 'Y';

	select substr('ABCD.PDF', instr('ABCD.PDF', '.',1)) from dual
	