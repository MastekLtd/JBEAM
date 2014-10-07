drop table process_req_params;
drop table process_request;
drop table schedule_event_calendar;
drop table process_request_schedule;
drop table request_id_sequence;

CREATE TABLE process_req_params (
	req_id INT NOT NULL,
	param_no INT NOT NULL,
	param_fld VARCHAR(100) NOT NULL,
	param_val VARCHAR(100),
	param_data_type VARCHAR(3) NOT NULL,
	static_dynamic_flag VARCHAR(2),
	PRIMARY KEY (req_id,param_no)
);

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
--	calendar_class_nm VARCHAR(500),
	PRIMARY KEY (sch_id)
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
    retry_times INT,
    retry_time_unit VARCHAR(10) DEFAULT 'MINUTES',
    retry_time int,
    retry_cnt int,
    PRIMARY KEY (req_id)
);

create table schedule_event_calendar (
    sch_id INT NOT NULL,
    serial_no INT NOT NULL,
    category VARCHAR(10) NOT NULL,
    process_class_nm VARCHAR(500),
    PRIMARY KEY (sch_id, serial_no)
);

CREATE TABLE request_id_sequence (
	req_id INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (req_id)
) ENGINE=InnoDB;

CREATE INDEX fk_pr_prs ON process_request (sch_id ASC);

--ALTER TABLE process_request ADD PRIMARY KEY (req_id);

--ALTER TABLE process_request_schedule ADD PRIMARY KEY (sch_id);

ALTER TABLE process_req_params ADD CONSTRAINT process_req_params_ibfk_1 FOREIGN KEY (req_id)
	REFERENCES process_request (req_id);

ALTER TABLE process_request ADD CONSTRAINT process_request_ibfk_1 FOREIGN KEY (sch_id)
	REFERENCES process_request_schedule (sch_id);

--ALTER TABLE process_request_schedule DROP COLUMN calendar_class_nm;
 