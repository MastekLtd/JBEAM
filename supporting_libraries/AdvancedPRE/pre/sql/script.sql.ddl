CREATE TABLE request_id_sequence (
	req_id NUMBER(12) NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (req_id)
);

CREATE TABLE process_request (
	req_id NUMBER(12) NOT NULL,
	req_type VARCHAR2(15) DEFAULT general NOT NULL,
	user_id VARCHAR2(15) NOT NULL,
	req_dt TIMESTAMP NOT NULL,
	req_stat VARCHAR2(2) NOT NULL,
	process_class_nm VARCHAR2(500) NOT NULL,
	grp_st_ind VARCHAR2(2) NOT NULL,
	req_start_dt TIMESTAMP,
	req_end_dt TIMESTAMP,
	grp_id NUMBER(12),
	grp_req_seq_no NUMBER(3),
	req_logfile_nm VARCHAR2(500),
	job_id   VARCHAR2(100),
	job_name VARCHAR2(100),
	scheduled_time TIMESTAMP,
	sch_id NUMBER(12),
	stuck_thread_limit NUMBER(5),
	stuck_thread_max_limit NUMBER(5),
	priority NUMBER DEFAULT 99 NOT NULL,
	email_ids VARCHAR2(2000),
	verbose_time_elapsed VARCHAR2(100),
	cal_scheduled_time TIMESTAMP,
	PRIMARY KEY (req_id)
);

CREATE TABLE process_request_schedule (
	sch_id NUMBER NOT NULL,
	freq_type VARCHAR2(10) NOT NULL,
	recur NUMBER NOT NULL,
	start_dt TIMESTAMP NOT NULL,
	sch_stat VARCHAR2(2) NOT NULL,
	user_id VARCHAR2(15) NOT NULL,
	on_week_day VARCHAR2(7),
	end_dt TIMESTAMP,
	end_occur NUMBER,
	entry_dt TIMESTAMP,
	modify_id VARCHAR2(10),
	modify_dt TIMESTAMP,
	req_stat VARCHAR2(2),
	occur_counter NUMBER,
	process_class_nm VARCHAR2(500),
	start_time TIMESTAMP,
	end_time TIMESTAMP,
	future_scheduling_only VARCHAR2(1),
	fixed_date VARCHAR2(1) DEFAULT n NOT NULL,
	email_ids VARCHAR2(2000),
	skip_flag VARCHAR2(2),
	weekday_check_flag VARCHAR2(2),
	calendar_class_nm VARCHAR2(500),
	PRIMARY KEY (sch_id)
) ;

CREATE TABLE process_req_params (
	req_id NUMBER(12) NOT NULL,
	param_no NUMBER(3) NOT NULL,
	param_fld VARCHAR2(100) NOT NULL,
	param_val VARCHAR2(100),
	param_data_type VARCHAR2(3) NOT NULL,
	static_dynamic_flag VARCHAR2(2),
	PRIMARY KEY (req_id,param_no)
);

CREATE INDEX fk_pr_prs ON process_request (sch_id ASC);

ALTER TABLE process_request ADD CONSTRAINT process_request_ibfk_1 FOREIGN KEY (sch_id)
	REFERENCES process_request_schedule (sch_id);

ALTER TABLE process_req_params ADD CONSTRAINT process_req_params_ibfk_1 FOREIGN KEY (req_id)
	REFERENCES process_request (req_id);

alter table process_request change column menu_id job_id varchar(100);
alter table process_request add column job_name varchar(500) after job_id;