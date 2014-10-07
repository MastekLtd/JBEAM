---------------------------------------------------------------------------------------
-- $Revision: 2382 $
---------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------
--Sample data for executing the CHelloWorld through PRE.
---------------------------------------------------------------------------------------
Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
1, 'GENERAL', 'SHELL', '2008-01-06 00:00:00', 'Q', 
'com.myapp.preimpl.CHelloWorld', 'S', null, 
null, 0, 0, null, 
'1.0001.898', 'Hello World', '2008-01-01 00:00:00', null, 
1, 2, 0, 'kedar.raybagkar@stgmastek.com', null, '2008-01-01 00:00:00'
);

-----------------------------------------------------------------------------------------
--Sample data for exeucting the  CHelloWorldWithParameter
-----------------------------------------------------------------------------------------
Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
2, 'GENERAL', 'SHELL', '2008-01-06 00:00:00', 'Q', 
'com.myapp.preimpl.CHelloWorldWithParameter', 'S', null, 
null, 0, 0, null, 
'1.0001.900', 'Hello World with parameter', '2008-01-01 00:00:00', null, 
1, 2, 0, 'kedar.raybagkar@stgmastek.com', null, '2008-01-01 00:00:00'
);



Insert Into process_req_params(req_id, param_no, param_fld, param_val, param_data_type, 
static_dynamic_flag) values ( 
2, 1, 'date1', '01/01/2008 00:00:00|01/01/2008 12:00:00', 'DTA', 'D');

Insert Into process_req_params(req_id, param_no, param_fld, param_val, param_data_type, 
static_dynamic_flag) values ( 
2, 2, 'date2', '31/10/1990 00:00:00|31/01/2008 18:00:00', 'DTA', 'D');

---------------------------------------------------------------------------------------
--Associating a schedule with CHelloWorldWithParameter

--change the request id generator to generate req_id greater than 7 so that the remaining sql 
--examples will work as provided. In case the request id generator relies on a sequence then 
--increment the sequence to the 8. 
---------------------------------------------------------------------------------------
Insert Into process_request_schedule(sch_id, freq_type, recur, start_dt, sch_stat, 
user_id, on_week_day, end_dt, end_occur, entry_dt, modify_id, modify_dt, req_stat, 
occur_counter, process_class_nm, start_time, end_time, future_scheduling_only, fixed_date, 
email_ids, skip_flag, weekday_check_flag) values ( 
1, 'MONTH', 1, '2008-01-01 00:00:00', 'A', 'KEDAR', 
'0000000', '2008-01-06 00:00:00', null, '2008-06-15 00:00:00', 
'KEDAR', '2008-06-15 00:00:00', 'Q', 1, 'stg.pr.engine.scheduler.CRequestScheduler', 
null, null, 'N', 'N', '', 'D+', 'Y');

Insert Into schedule_event_calendar(sch_id, serial_no, category, process_class_nm
) values ( 
1, 1, 'CALENDAR', 'com.myapp.preimpl.MyOwnCalendar');

Insert Into schedule_event_calendar(sch_id, serial_no, category, process_class_nm
) values ( 
1, 2, 'CALENDAR', 'com.myapp.preimpl.MyOwnCalendar2');

Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time, text1, text2, num1, num2) values ( 
3, 'GENERAL', 'Kedar', '2008-01-06 00:00:00', 'Q', 
'com.myapp.preimpl.CHelloWorldWithParameter', 'S', null, null, 0, 0, null, '1.0001.819', 
'Test PRE functionality', '2008-01-01 00:00:00', 
1, 1, 2, 0, 'kedar.raybagkar@stgmastek.com', null, '2008-01-01 00:00:00', 
null, null, '0.00', '0.00');



Insert Into process_req_params(req_id, param_no, param_fld, param_val, param_data_type, 
static_dynamic_flag) values ( 
3, 1, 'date1', '01/01/2008 00:00:00|01/01/2008 12:00:00', 'DTA', 'D');

Insert Into process_req_params(req_id, param_no, param_fld, param_val, param_data_type, 
static_dynamic_flag) values ( 
3, 2, 'date2', '31/01/1990 00:00:00|31/01/2008 18:00:00', 'DTA', 'D');


---------------------------------------------------------------------------------------
-- Example of a grouped request.
---------------------------------------------------------------------------------------

Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
4, 'GENERAL', 'SHELL', '2008-03-14 00:00:00', 'S', 
'com.myapp.preimpl.CHelloWorld', 'G', null, 
null, 2, 1, null, 
'1.0001.898', 'Hello World', '2008-01-01 00:00:00', null, 
1, 2, 0, null, null, null);

Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
5, 'GENERAL', 'SHELL', '2008-03-14 00:00:00', 'S', 
'com.myapp.preimpl.CHelloWorld', 'G', null, 
null, 2, 2, null, 
'1.0001.898', 'Hello World', '1990-01-01 00:00:00', null, 
1, 2, 0, null, null, null);

---------------------------------------------------------------------------------------
--Reboot the engine
---------------------------------------------------------------------------------------
Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
6, 'GENERAL', 'SHELL', '2008-03-18 00:00:00', 'S', 
'stg.pr.engine.startstop.CRebootEngine', 'S', null, 
null, 0, 0, null, 
'0.0000.001', 'Reboot Engine', '2008-03-18 00:00:00', null, 
1, 2, 0, null, null, null);


---------------------------------------------------------------------------------------
--Stop the engine
---------------------------------------------------------------------------------------
Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
7, 'GENERAL', 'SHELL', '2008-03-18 00:00:00', 'S', 
'stg.pr.engine.startstop.CStopEngine', 'S', '2008-07-10 00:00:00', 
'2008-07-10 00:00:00', 0, 0, null, 
'0.0000.002', 'Stop Engine', '2008-03-18 00:00:00', null, 
1, 2, 0, null, null, null);

---------------------------------------------------------------------------------------
--Logical Termination
---------------------------------------------------------------------------------------
Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
8, 'GENERAL', 'SHELL', '2008-03-18 00:00:00', 'S', 
'com.myapp.preimpl.TestLogicalStop', 'S', '2008-07-10 00:00:00', 
'2008-07-10 00:00:00', 0, 0, null, 
'1.0002.001', 'Test Logical STOP', '2008-03-18 00:00:00', null, 
20, 60, 0, null, null, null);

---------------------------------------------------------------------------------------
--Test Kill
---------------------------------------------------------------------------------------
Insert Into process_request(req_id, req_type, user_id, req_dt, req_stat, process_class_nm, 
grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, req_logfile_nm, job_id, 
job_name, scheduled_time, sch_id, stuck_thread_limit, stuck_thread_max_limit, priority, 
email_ids, verbose_time_elapsed, cal_scheduled_time) values ( 
9, 'GENERAL', 'SHELL', '2008-03-18 00:00:00', 'S', 
'com.myapp.preimpl.TestKill', 'S', '2008-07-10 00:00:00', 
'2008-07-10 00:00:00', 0, 0, null, 
'1.0002.003', 'Test Logical STOP', '2008-03-18 00:00:00', null, 
20, 60, 0, null, null, null);

