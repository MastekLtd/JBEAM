set pages 9999
set lines 260
set colsep |
set numwidth 12
col menu_id form a71 head "Job"
col cnt     form 9999999         head "count#"
col elp_min form 9999999999.9999 head "Elapsed (min)"
col elp_sec form 9999999999.9999 head "Elapsed (sec)"
--break on frm_form_name skip 1
--compute count avg sum min max of elp_sec on frm_form_name
--compute count avg sum min max of elp_min on frm_form_name


select frm_form_name, req_id, req_start_dt, req_end_dt, scheduled_time, (req_end_dt - req_start_dt) * 24*60*60 elp_sec, to_char(scheduled_time, 'mm/dd/yyyy')
 "scheduled_time"
from process_request, print_executor, forms_master
where menu_id = 'Alw Output Form'
and req_id = prx_request_id
and prx_program_name = frm_file
and req_stat = 'S'
order by frm_form_name, req_id
/

select frm_form_name, to_char(scheduled_time, 'mm/dd/yyyy') "scheduled_date", count(*) "cnt", min(req_start_dt) req_start_time, max(req_end_dt) req_end_time,
 round((max(req_end_dt) - min(req_start_dt)) * 24*60*60, 2) "elp_sec", round((max(req_end_dt) - min(req_start_dt)) * 24*60, 2) "elp_min"
from process_request, print_executor, forms_master
where menu_id = 'Alw Output Form'
and req_id = prx_request_id
and prx_program_name = frm_file
and req_stat = 'S'
and prx_print_option = 'P'
group by frm_form_name, to_char(scheduled_time, 'mm/dd/yyyy')
order by frm_form_name, to_char(scheduled_time, 'mm/dd/yyyy')
/

select frm_form_name, req_dt, sum(cnt_start) cnt_start, sum(cnt_end) cnt_end
from (
select frm_form_name, to_char(req_start_dt, 'mm/dd/yyyy hh24:mi') req_dt, count(*) cnt_start, 0 cnt_end
from process_request, print_executor, forms_master
where menu_id = 'Alw Output Form'
and req_id = prx_request_id
and prx_program_name = frm_file
and req_stat = 'S'
and prx_print_option = 'P'
group by frm_form_name, to_char(req_start_dt, 'mm/dd/yyyy hh24:mi')
UNION ALL
select frm_form_name, to_char(req_end_dt, 'mm/dd/yyyy hh24:mi') req_dt, 0 cnt_start, count(*) cnt_end
from process_request, print_executor, forms_master
where menu_id = 'Alw Output Form'
and req_id = prx_request_id
and prx_program_name = frm_file
and req_stat = 'S'
and prx_print_option = 'P'
group by frm_form_name, to_char(req_end_dt, 'mm/dd/yyyy hh24:mi')
)
group by frm_form_name, req_dt
order by frm_form_name, req_dt
/

@set.sql
