--------------------------------------------------------
--  File created - Thursday-August-05-2010   
--------------------------------------------------------
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Sequence BATCH_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "BATCH_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence I_QUEUE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "I_QUEUE_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence LOG_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "LOG_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence O_QUEUE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "O_QUEUE_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PROCESS_REQ_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PROCESS_REQ_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table BATCH
--------------------------------------------------------

  CREATE TABLE "BATCH" 
   (	"BATCH_NO" NUMBER(10,0), 
	"BATCH_REV_NO" NUMBER(3,0), 
	"BATCH_NAME" VARCHAR2(25), 
	"BATCH_TYPE" VARCHAR2(10), 
	"EXEC_START_TIME" TIMESTAMP (3), 
	"EXEC_END_TIME" TIMESTAMP (3), 
	"BATCH_START_USER" VARCHAR2(30), 
	"BATCH_END_USER" VARCHAR2(30), 
	"PROCESS_ID" NUMBER(12,0), 
	"BATCH_END_REASON" VARCHAR2(25), 
	"FAILED_OVER" VARCHAR2(1) DEFAULT 'N', 
	"INSTRUCTION_SEQ_NO" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table BATCH_LOCK
--------------------------------------------------------

  CREATE TABLE "BATCH_LOCK" 
   (	"REQ_ID" NUMBER, 
	"INDICATOR" VARCHAR2(1), 
	"LOCK_TIME" TIMESTAMP (3)
   ) ;
--------------------------------------------------------
--  DDL for Table CALENDAR_LOG
--------------------------------------------------------

  CREATE TABLE "CALENDAR_LOG" 
   (	"CALENDAR_NAME" VARCHAR2(30), 
	"YEAR" VARCHAR2(4), 
	"NON_WORKING_DATE" DATE, 
	"REMARK" VARCHAR2(50), 
	"USER_ID" VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table COLUMN_MAP
--------------------------------------------------------

  CREATE TABLE "COLUMN_MAP" 
   (	"ENTITY" VARCHAR2(20), 
	"LOOKUP_COLUMN" VARCHAR2(30), 
	"LOOKUP_VALUE" VARCHAR2(30), 
	"VALUE_COLUMN" VARCHAR2(30), 
	"PRECEDENCE_ORDER" NUMBER(3,0), 
	"ON_ERROR_FAIL_ALL" VARCHAR2(1)
   ) ;
--------------------------------------------------------
--  DDL for Table CONFIGURATION
--------------------------------------------------------

  CREATE TABLE "CONFIGURATION" 
   (	"CODE1" VARCHAR2(20), 
	"CODE2" VARCHAR2(20), 
	"CODE3" VARCHAR2(25), 
	"VALUE" VARCHAR2(4000), 
	"VALUE_TYPE" VARCHAR2(2), 
	"DESCRIPTION" VARCHAR2(1000)
   ) ;
--------------------------------------------------------
--  DDL for Table DEAD_MESSAGE_QUEUE
--------------------------------------------------------

  CREATE TABLE "DEAD_MESSAGE_QUEUE" 
   (	"ID" NUMBER(10,0), 
	"I_O_MODE" VARCHAR2(1), 
	"MESSAGE" VARCHAR2(10), 
	"PARAM" VARCHAR2(100), 
	"ERROR_DESCRIPTION" VARCHAR2(4000)
   ) ;
--------------------------------------------------------
--  DDL for Table FUNCTION_MASTER
--------------------------------------------------------

  CREATE TABLE "FUNCTION_MASTER" 
   (	"FUNCTION_ID" VARCHAR2(30), 
	"FUNCTION_NAME" VARCHAR2(30), 
	"FUNCTION_INFO" VARCHAR2(500), 
	"EFF_DATE" DATE, 
	"EXP_DATE" DATE, 
	"CREATED_ON" DATE, 
	"CREATED_BY" VARCHAR2(30)
   ) ;
--------------------------------------------------------
--  DDL for Table FUNCTION_ROLE_MASTER
--------------------------------------------------------

  CREATE TABLE "FUNCTION_ROLE_MASTER" 
   (	"ROLE_ID" VARCHAR2(30), 
	"FUNCTION_ID" VARCHAR2(30), 
	"EFF_DATE" DATE, 
	"EXP_DATE" DATE, 
	"CREATED_ON" DATE, 
	"CREATED_BY" VARCHAR2(30)
   ) ;
--------------------------------------------------------
--  DDL for Table INSTRUCTION_LOG
--------------------------------------------------------

  CREATE TABLE "INSTRUCTION_LOG" 
   (	"SEQ_NO" NUMBER(10,0), 
	"BATCH_NO" NUMBER(10,0), 
	"BATCH_REV_NO" NUMBER(3,0), 
	"MESSAGE" VARCHAR2(10), 
	"MESSAGE_PARAM" VARCHAR2(100), 
	"INSTRUCTING_USER" VARCHAR2(30), 
	"INSTRUCTION_TIME" TIMESTAMP (3), 
	"BATCH_ACTION" VARCHAR2(500), 
	"BATCH_ACTION_TIME" TIMESTAMP (3)
   ) ;
--------------------------------------------------------
--  DDL for Table INSTRUCTION_PARAMETERS
--------------------------------------------------------

  CREATE TABLE "INSTRUCTION_PARAMETERS" 
   (	"INSTRUCTION_LOG_NO" NUMBER(10,0), 
	"SL_NO" NUMBER(3,0), 
	"NAME" VARCHAR2(25), 
	"VALUE" VARCHAR2(100), 
	"TYPE" VARCHAR2(5)
   ) ;
--------------------------------------------------------
--  DDL for Table I_QUEUE
--------------------------------------------------------

  CREATE TABLE "I_QUEUE" 
   (	"ID" NUMBER(10,0), 
	"MESSAGE" VARCHAR2(10), 
	"PARAM" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table LOG
--------------------------------------------------------

  CREATE TABLE "LOG" 
   (	"SEQ_NO" NUMBER, 
	"BATCH_NO" NUMBER(10,0), 
	"BATCH_REV_NO" NUMBER(3,0), 
	"BE_SEQ_NO" VARCHAR2(12), 
	"TASK_NAME" VARCHAR2(2000), 
	"OBJ_EXEC_START_TIME" TIMESTAMP (3), 
	"OBJ_EXEC_END_TIME" TIMESTAMP (3), 
	"STATUS" VARCHAR2(2), 
	"SYS_ACT_NO" VARCHAR2(25), 
	"USER_PRIORITY" VARCHAR2(2), 
	"PRIORITY_CODE1" NUMBER(10,0), 
	"PRIORITY_CODE2" NUMBER(10,0), 
	"PRE_POST" VARCHAR2(20), 
	"JOB_TYPE" VARCHAR2(2), 
	"LINE" VARCHAR2(10), 
	"SUBLINE" VARCHAR2(10), 
	"BROKER" VARCHAR2(20), 
	"POLICY_NO" VARCHAR2(20), 
	"POLICY_RENEW_NO" VARCHAR2(2), 
	"VEH_REF_NO" VARCHAR2(12), 
	"CASH_BATCH_NO" VARCHAR2(20), 
	"CASH_BATCH_REV_NO" VARCHAR2(2), 
	"GBI_BILL_NO" VARCHAR2(24), 
	"PRINT_FORM_NO" VARCHAR2(8), 
	"NOTIFY_ERROR_TO" VARCHAR2(10), 
	"DATE_GENERATE" DATE, 
	"GENERATE_BY" VARCHAR2(30), 
	"REC_MESSAGE" VARCHAR2(80), 
	"JOB_DESC" VARCHAR2(80), 
	"OBJECT_NAME" VARCHAR2(100), 
	"DATE_EXECUTED" DATE, 
	"LIST_IND" NUMBER(3,0), 
	"ENTITY_TYPE" VARCHAR2(15), 
	"ENTITY_CODE" VARCHAR2(20), 
	"REF_SYSTEM_ACTIVITY_NO" VARCHAR2(25), 
	"ERROR_TYPE" VARCHAR2(25), 
	"ERROR_DESCRIPTION" VARCHAR2(4000), 
	"CYCLE_NO" NUMBER(10,0), 
	"USED_MEMORY_BEFORE" NUMBER(19,0), 
	"USED_MEMORY_AFTER" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table META_DATA
--------------------------------------------------------

  CREATE TABLE "META_DATA" 
   (	"SEQ_NO" VARCHAR2(12), 
	"TASK_NAME" VARCHAR2(2000), 
	"EFF_DATE" DATE, 
	"EXP_DATE" DATE, 
	"ON_FAIL_EXIT" CHAR(1), 
	"PRIORITY_CODE1" NUMBER(10,0), 
	"PRIORITY_CODE2" NUMBER(10,0), 
	"PRE_POST" VARCHAR2(5), 
	"JOB_TYPE" VARCHAR2(2), 
	"LINE" VARCHAR2(10), 
	"SUBLINE" VARCHAR2(10), 
	"DATE_GENERATE" DATE, 
	"GENERATE_BY" VARCHAR2(30), 
	"JOB_DESC" VARCHAR2(80), 
	"OBJECT_NAME" VARCHAR2(30)
   ) ;
--------------------------------------------------------
--  DDL for Table OBJECT_MAP
--------------------------------------------------------

  CREATE TABLE "OBJECT_MAP" 
   (	"ID" VARCHAR2(50), 
	"OBJECT_NAME" VARCHAR2(4000), 
	"OBJECT_TYPE" VARCHAR2(2), 
	"EFF_DATE" DATE, 
	"EXP_DATE" DATE, 
	"DEFAULT_VALUES" VARCHAR2(4000), 
	"ON_FAIL_EXIT" VARCHAR2(1), 
	"ON_FAIL_EMAIL" VARCHAR2(2), 
	"MIN_TIME" NUMBER(10,0), 
	"AVG_TIME" NUMBER(10,0), 
	"MAX_TIME" NUMBER(10,0), 
	"MIN_TIME_ESCL" VARCHAR2(1), 
	"ESCALATION_LEVEL" VARCHAR2(20),
	"CASE_DATA" VARCHAR2(4000)
   ) ;
--------------------------------------------------------
--  DDL for Table O_QUEUE
--------------------------------------------------------

  CREATE TABLE "O_QUEUE" 
   (	"ID" NUMBER(10,0), 
	"MESSAGE" VARCHAR2(10), 
	"PARAM" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table PROCESS_REQUEST
--------------------------------------------------------

  CREATE TABLE "PROCESS_REQUEST" 
   (	"REQ_ID" NUMBER(12,0), 
	"USER_ID" VARCHAR2(15), 
	"REQ_DT" DATE, 
	"REQ_STAT" VARCHAR2(2), 
	"PROCESS_CLASS_NM" VARCHAR2(500), 
	"GRP_ST_IND" VARCHAR2(2), 
	"REQ_START_DT" DATE, 
	"REQ_END_DT" DATE, 
	"GRP_ID" NUMBER(12,0), 
	"GRP_REQ_SEQ_NO" NUMBER(12,0), 
	"REQ_LOGFILE_NM" VARCHAR2(500), 
	"JOB_ID" VARCHAR2(100), 
	"SCHEDULED_TIME" DATE DEFAULT sysdate, 
	"SCH_ID" NUMBER(12,0), 
	"STUCK_THREAD_LIMIT" NUMBER(10,0) DEFAULT 2, 
	"STUCK_THREAD_MAX_LIMIT" NUMBER(10,0) DEFAULT 10, 
	"REQ_TYPE" VARCHAR2(20) DEFAULT 'GENERAL', 
	"PRIORITY" NUMBER(3,0) DEFAULT 999, 
	"EMAIL_IDS" VARCHAR2(2000), 
	"VERBOSE_TIME_ELAPSED" VARCHAR2(200), 
	"CAL_SCHEDULED_TIME" DATE, 
	"JOB_NAME" VARCHAR2(100), 
	"TEXT1" VARCHAR2(50), 
	"TEXT2" VARCHAR2(50), 
	"NUM1" NUMBER(12,2), 
	"NUM2" NUMBER(12,2), 
	"RETRY_TIMES" NUMBER(10,0) DEFAULT 0, 
	"RETRY_TIME_UNIT" VARCHAR2(10) DEFAULT 'MINUTES', 
	"RETRY_TIME" NUMBER(*,0) DEFAULT 0, 
	"RETRY_CNT" NUMBER(*,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table PROCESS_REQUEST_SCHEDULE
--------------------------------------------------------

  CREATE TABLE "PROCESS_REQUEST_SCHEDULE" 
   (	"SCH_ID" NUMBER(12,0), 
	"FREQ_TYPE" VARCHAR2(10), 
	"RECUR" NUMBER(10,0), 
	"START_DT" DATE, 
	"SCH_STAT" VARCHAR2(2), 
	"USER_ID" VARCHAR2(15), 
	"ON_WEEK_DAY" VARCHAR2(7), 
	"END_DT" DATE, 
	"END_OCCUR" NUMBER(12,0), 
	"ENTRY_DT" DATE, 
	"MODIFY_ID" VARCHAR2(10), 
	"MODIFY_DT" DATE, 
	"REQ_STAT" VARCHAR2(2), 
	"OCCUR_COUNTER" NUMBER(12,0), 
	"PROCESS_CLASS_NM" VARCHAR2(500), 
	"START_TIME" DATE, 
	"END_TIME" DATE, 
	"FUTURE_SCHEDULING_ONLY" VARCHAR2(1) DEFAULT 'Y', 
	"FIXED_DATE" VARCHAR2(1) DEFAULT 'N', 
	"EMAIL_IDS" VARCHAR2(2000), 
	"SKIP_FLAG" VARCHAR2(2) DEFAULT NULL, 
	"WEEKDAY_CHECK_FLAG" VARCHAR2(2) DEFAULT NULL,
	"END_REASON" VARCHAR2(2000) DEFAULT NULL,
	"KEEP_ALIVE" VARCHAR2(1) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Table PROCESS_REQ_PARAMS
--------------------------------------------------------

  CREATE TABLE "PROCESS_REQ_PARAMS" 
   (	"REQ_ID" NUMBER(12,0), 
	"PARAM_NO" NUMBER(12,0), 
	"PARAM_FLD" VARCHAR2(100), 
	"PARAM_VAL" VARCHAR2(1000), 
	"PARAM_DATA_TYPE" VARCHAR2(2), 
	"STATIC_DYNAMIC_FLAG" VARCHAR2(1) DEFAULT 'S'
   ) ;
--------------------------------------------------------
--  DDL for Table PROGRESS_LEVEL
--------------------------------------------------------

  CREATE TABLE "PROGRESS_LEVEL" 
   (	"BATCH_NO" NUMBER(10,0), 
	"BATCH_REV_NO" NUMBER(3,0), 
	"INDICATOR_NO" NUMBER(4,0), 
	"PRG_LEVEL_TYPE" VARCHAR2(10), 
	"PRG_ACTIVITY_TYPE" VARCHAR2(20), 
	"CYCLE_NO" NUMBER(3,0), 
	"STATUS" VARCHAR2(2), 
	"START_DATETIME" TIMESTAMP (3), 
	"END_DATETIME" TIMESTAMP (6), 
	"ERROR_DESC" VARCHAR2(100), 
	"FAILED_OVER" VARCHAR2(1)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE_MASTER
--------------------------------------------------------

  CREATE TABLE "ROLE_MASTER" 
   (	"ROLE_ID" VARCHAR2(30), 
	"ROLE_NAME" VARCHAR2(50), 
	"EFF_DATE" DATE, 
	"EXP_DATE" DATE, 
	"CREATED_ON" DATE, 
	"CREATED_BY" VARCHAR2(30)
   ) ;
--------------------------------------------------------
--  DDL for Table SCHEDULE_EVENT_CALENDAR
--------------------------------------------------------

  CREATE TABLE "SCHEDULE_EVENT_CALENDAR" 
   (	"SCH_ID" NUMBER, 
	"SERIAL_NO" NUMBER, 
	"CATEGORY" VARCHAR2(10), 
	"PROCESS_CLASS_NM" VARCHAR2(500)
   ) ;
--------------------------------------------------------
--  DDL for Table SYSTEM_INFO
--------------------------------------------------------

  CREATE TABLE "SYSTEM_INFO" 
   (	"BATCH_NO" NUMBER(10,0), 
	"BATCH_REV_NO" NUMBER(3,0), 
	"JAVA_VERSION" VARCHAR2(20), 
	"PRE_VERSION" VARCHAR2(50), 
	"OS_CONFIG" VARCHAR2(100), 
	"OUTPUT_DIR_PATH" VARCHAR2(500), 
	"OUTPUT_DIR_FREE_MEM" VARCHAR2(50), 
	"MAX_MEMORY" NUMBER(19,0), 
	"USED_MEMORY" NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_MASTER
--------------------------------------------------------

  CREATE TABLE "USER_MASTER" 
   (	"USER_ID" VARCHAR2(30), 
	"USER_NAME" VARCHAR2(50), 
	"TELEPHONE_NO" VARCHAR2(15), 
	"FAX_NO" VARCHAR2(15), 
	"EMAIL_ID" VARCHAR2(50), 
	"EFF_DATE" DATE, 
	"EXP_DATE" DATE, 
	"CREATED_ON" DATE, 
	"CREATED_BY" VARCHAR2(30), 
	"PASSWORD" VARCHAR2(30), 
	"ASSIGNED_ROLE" VARCHAR2(30)
   ) ;

---------------------------------------------------
--   DATA FOR TABLE USER_MASTER
--   FILTER = none used
---------------------------------------------------
CREATE TABLE ORDERBY_MAP 
(
  	"ENTITY" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	"ORDER_BY_COLUMN" VARCHAR2(2000 BYTE)
);

REM INSERTING into ORDERBY_MAP
Insert into ORDERBY_MAP (ENTITY,ORDER_BY_COLUMN) values ('ACCOUNT','account_system_code, priority_code_1, priority_code_2');
Insert into ORDERBY_MAP (ENTITY,ORDER_BY_COLUMN) values ('POLICY','policy_no, priority_code_1, priority_code_2');
   
REM INSERTING into USER_MASTER
Insert into USER_MASTER (USER_ID,USER_NAME,TELEPHONE_NO,FAX_NO,EMAIL_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PASSWORD,ASSIGNED_ROLE) values ('ADMIN','ADMIN','5445464','4654646','jbeam@mastek.com',to_timestamp('01-JAN-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),null,to_timestamp('12-AUG-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'MANDAR',null,'ADMIN');

---------------------------------------------------
--   END DATA FOR TABLE USER_MASTER
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE ROLE_MASTER
--   FILTER = none used
---------------------------------------------------
REM INSERTING into ROLE_MASTER
Insert into ROLE_MASTER (ROLE_ID,ROLE_NAME,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY) values ('USER','USER',to_timestamp('01-JAN-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),null,to_timestamp('01-JAN-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'BPMS');
Insert into ROLE_MASTER (ROLE_ID,ROLE_NAME,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY) values ('ADMIN','ADMIN',to_timestamp('01-JAN-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),null,to_timestamp('01-JAN-09 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'BPMS');

---------------------------------------------------
--   END DATA FOR TABLE ROLE_MASTER
---------------------------------------------------
SET DEFINE ON
Accept installationCode VARCHAR2 PROMPT 'Enter Installation Code:'
---------------------------------------------------
--   DATA FOR TABLE CONFIGURATION
--   FILTER = none used
---------------------------------------------------
REM INSERTING into CONFIGURATION
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','REPORT_RUNTIME','LOG_DIR','&Birt_Runtime_Logs_Path','S','Directory where the logs will reside.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','JOB_MONITOR_POLLER','WAIT_PERIOD','10','I','The wait period for Batch Job Monitor. Time in Minutes');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('COMM','POLLER','WAIT_PERIOD','5000','I','The wait period for pollers in the core communication system');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','BATCH_LISTENER','MAX_LISTENERS','5','I','The max number of listeners to be spawned for batch objects. Please ensure that the connections should also be increased with an increase in the number of listeners');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','CALENDAR_CLASS','JV','com.stgmastek.core.calendar.BatchCalendar','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','CRITICAL','EMAIL','&Critical_Email_Ids','S','Escalation Level Developer. The value should be set in the object map to determine the recipient address.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DATE_FORMAT','BATCH_JOB_DATE','dd/MM/yyyy','S','The format for the batch job date');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DATE_FORMAT','BATCH_RUN_DATE','dd-MMM-yyyy HH:mm:ss','S','The date format of the batch run date');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','CALENDAR','CALENDAR','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','END_ON_DATE','END_ON_DATE','I',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','END_ON_OCCURRENCE','END_ON_OCCURRENCE','I',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','FREQUENCY','FREQUENCY','I',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','RECUR_EVERY','RECUR_EVERY','I',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','SCHEDULE_DATE','SCHEDULE_DATE','I',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','SKIP_FLAG','SKIP_FLAG','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','WEEK_DAY','WEEK_DAY','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','DEL_INSTR_PARAMS','KEEP_ALIVE','KEEP_ALIVE','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EMAIL','CONTENT_HANDLER','com.stgmastek.core.util.email.DefaultEmailContentGenerator','S','The email content handler implementation. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EMAIL','MAX_EMAILS_FAILED_OBJ','4','I','Counter to set maximum number of emails to be sent.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EMAIL','NOTIFICATION','Y','S','Indication whether to send email alerts or not');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EMAIL','NOTIFICATION_GROUP','&Notification_Group_Email_Ids','S','The email group to which the email alerts to be sent');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EMAIL','WHEN_OBJECT_FAILS','com.stgmastek.core.util.email.BatchObjectFailureEmailContentGenerator','S','The email content handler implementation. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','EV','com.stgmastek.core.logic.EventParserObjectExecutionHandler','S','Default execution handler implementation for EVent parser batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','JV','com.stgmastek.core.logic.JAVAExecutionHandler','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','PL','com.stgmastek.core.logic.PLSQLExecutionHandler','S','Default execution handler implementation for PLSQL batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','FE','com.stgmastek.jbeam.billing.impl.FlowExecutionHandler','S','Default execution handler implementation for launch flow');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','FUTURE_DATE_RUN','MAX_NO_OF_DAYS','50000','I','The max number of future days for which the batch could be run');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','GLOBAL_PARAMETER','REQUIRED','Y','S','Indication whether to set the global parameters. Usually would be ''Y''');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','HIGH','EMAIL','&High_Email_Ids','S','Escalation Level SENIOR. The value should be set in the object map to determine the recipient address.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','LOW','EMAIL','&Low_Email_Ids','S','Escalation Level BU_LEAD. The value should be set in the object map to determine the recipient address.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','MEDIUM','EMAIL','&Medium_Email_Ids','S','Escalation Level TECH_MANAGER. The value should be set in the object map to determine the recipient address.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','MODE','DEV_OR_PRE','PRE','S','Primarily used for development ease. Real-time would always be PRE. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','POLLER','WAIT_PERIOD','5000','I','The wait period for pollers in the batch core system');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE','WAIT_PERIOD','5000','I','The wait period to check the status requested to the PRE engine to execute');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_POST_LISTENER','MAX_LISTENERS','5','I','The max number of listeners to be spawned for PRE / POST objects. Please ensure that the connections should also be increased with an increase in the number of listeners');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_REQUEST_TYPE','VALUE','GENERAL','I','The max number of future days for which the batch could be run');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_STUCK_THREAD','LIMIT','120','I','The max number of future days for which the batch could be run');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_STUCK_THREAD','MAX_LIMIT','180','I','The max number of future days for which the batch could be run');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PROCESS_CLASS','JV','com.stgmastek.core.logic.Processor','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','SAVEPOINT','DIRECTORY','&Savepoints_Path','S','The savepoint file directory');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','SCHEDULE_CLASS','JV','stg.pr.engine.scheduler.CRequestScheduler','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','TEXT_LOGGING','ENABLED','N','S','Indication whether text logging is to be enabled. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','TEXT_LOGGING','FILE_PATH','&Batch_Logs_Path','S','The text log file path. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','USER','DEFAULT','ADMIN','S','Default user');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('INSTALLATION_WS','&installationCode','SERVICES','&installation_IP_Port','S','Optional. Used for development purpose. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR_WS','MONITOR_WS','SERVICES','&monitor_Comm_IP_Port','S','The <IP>:<PORT> of the monitor communication system. Needed to the core system to communicate');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','REPORT_RUNTIME','HOME_DIR','&birt_Runtime_ReportEngine_Path','S','The Runtime Report Engine Directory');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','REPORT_RUNTIME','OUTPUT_FOLDER','&Reports_Path','S','The Report Output directory.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PURGE','RETAIN_DAYS','&retain_Days','I','No fo days to retain.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PURGE','BACKUP_DIR','&backup_Dir_Path','S','The directory where the backup script files stored.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','INSTALLATION','CODE','&installationCode','S','The current or self installation code.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','INSTALLATION','NAME','&installation_Desc','S','The current or self installation name');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','ICD_END_POINT_URL','URL','&ICD_End_Point_URL/ICDService/services/ProcessManager/handleRequest','S','Default execution handler implementation for launch flow');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','ICD_SERVICE','USER_ID','&ICD_Service_UserId','S','User id for ICDService');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','ICD_SERVICE','PASSWORD','&ICD_Service_Password','S','Password for ICDService');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','AUTH_HANDLER','JV','com.stgmastek.core.authentication.defaultimpl.AuthenticationHandlerImpl','S','Authentication Handler Class');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','AUTH_FILTER_HANDLER','JV','com.stgmastek.core.authentication.defaultimpl.AuthenticationRequestFilterImpl','S','Authentication Filter Class');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','THREAD_POOL','CORE_SIZE','5','S','The number of threads to keep in the pool, even if they are idle');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','THREAD_POOL','MAX_CORE_SIZE','5','S','The maximum number of threads to allow in the pool.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','APPLICATION_REF','BILLING','Billing','S','Constant used for fetching billing application specific values');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','APPLICATION_REF','PAS','Policy','S','Constant used for fetching PAS application specific values');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','FB','com.stgmastek.jbeam.billing.impl.FlowExecutionHandlerBilling','S','Default execution handler implementation for launch flow for Billing');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','FP','com.stgmastek.jbeam.billing.impl.FlowExecutionHandlerPAS','S','Default execution handler implementation for launch flow for PAS');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','ICD_SERVICE','USE_ICD_SERVICE_ROUTER','false','S','If true, uses ICDService router for connecting to icdservice instead of provided ICDServiceUrl');

---------------------------------------------------
--   END DATA FOR TABLE CONFIGURATION
---------------------------------------------------
SET DEFINE OFF
---------------------------------------------------
--   DATA FOR TABLE COLUMN_MAP
--   FILTER = none used
---------------------------------------------------
REM INSERTING into COLUMN_MAP
Insert into COLUMN_MAP (ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL) values ('POLICY','CONTEXT','P','REFERENCE_ID#SUB_REFERENCE_ID',2,'Y');
Insert into COLUMN_MAP (ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL) values ('POLICY','POLICY_NO',null,'POLICY_NO#POLICY_RENEW_NO',2,'Y');
Insert into COLUMN_MAP (ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL) values ('ACCOUNT','ENTITY_TYPE','ACCOUNT','ACCOUNT_SYSTEM_CODE',4,'Y');
Insert into COLUMN_MAP (ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL) values ('BROKER','ENTITY_TYPE','BROKER','BROKER_SYSTEM_CODE',5,'Y');
Insert into COLUMN_MAP (ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL) values ('PRE','PRE_POST','PRE','PRIORITY_CODE_1',1,null);
Insert into COLUMN_MAP (ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL) values ('POST','PRE_POST','POST','PRIORITY_CODE_1',999,null);
Insert into COLUMN_MAP (ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL) values ('GENERAL','JOB_SEQ',null,'JOB_SEQ',6,null);

---------------------------------------------------
--   END DATA FOR TABLE COLUMN_MAP
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE BATCH_LOCK
--   FILTER = none used
---------------------------------------------------
REM INSERTING into BATCH_LOCK
Insert into BATCH_LOCK (REQ_ID,INDICATOR,LOCK_TIME) values (1,'O',to_timestamp('05-AUG-10 04.05.53.429000000 AM','DD-MON-RR HH.MI.SS.FF AM'));

---------------------------------------------------
--   END DATA FOR TABLE BATCH_LOCK
---------------------------------------------------
--------------------------------------------------------
--  Constraints for Table BATCH
--------------------------------------------------------

  ALTER TABLE "BATCH" ADD CONSTRAINT "PK2" PRIMARY KEY ("BATCH_NO", "BATCH_REV_NO") ENABLE;
 
  ALTER TABLE "BATCH" MODIFY ("BATCH_NO" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH" MODIFY ("BATCH_REV_NO" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH" MODIFY ("BATCH_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH" MODIFY ("BATCH_TYPE" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH" MODIFY ("EXEC_START_TIME" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH" MODIFY ("BATCH_START_USER" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH" MODIFY ("FAILED_OVER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BATCH_LOCK
--------------------------------------------------------

  ALTER TABLE "BATCH_LOCK" MODIFY ("REQ_ID" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH_LOCK" MODIFY ("INDICATOR" NOT NULL ENABLE);
 
  ALTER TABLE "BATCH_LOCK" MODIFY ("LOCK_TIME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CALENDAR_LOG
--------------------------------------------------------

  ALTER TABLE "CALENDAR_LOG" MODIFY ("CALENDAR_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "CALENDAR_LOG" MODIFY ("YEAR" NOT NULL ENABLE);
 
  ALTER TABLE "CALENDAR_LOG" MODIFY ("NON_WORKING_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "CALENDAR_LOG" MODIFY ("USER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table COLUMN_MAP
--------------------------------------------------------

  ALTER TABLE "COLUMN_MAP" MODIFY ("ENTITY" NOT NULL ENABLE);
 
  ALTER TABLE "COLUMN_MAP" MODIFY ("PRECEDENCE_ORDER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONFIGURATION
--------------------------------------------------------

  ALTER TABLE "CONFIGURATION" MODIFY ("CODE1" NOT NULL ENABLE);
 
  ALTER TABLE "CONFIGURATION" MODIFY ("VALUE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DEAD_MESSAGE_QUEUE
--------------------------------------------------------

  ALTER TABLE "DEAD_MESSAGE_QUEUE" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "DEAD_MESSAGE_QUEUE" MODIFY ("I_O_MODE" NOT NULL ENABLE);
 
  ALTER TABLE "DEAD_MESSAGE_QUEUE" MODIFY ("MESSAGE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FUNCTION_MASTER
--------------------------------------------------------

  ALTER TABLE "FUNCTION_MASTER" ADD CONSTRAINT "PK15" PRIMARY KEY ("FUNCTION_ID") ENABLE;
 
  ALTER TABLE "FUNCTION_MASTER" MODIFY ("FUNCTION_ID" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_MASTER" MODIFY ("FUNCTION_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_MASTER" MODIFY ("FUNCTION_INFO" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_MASTER" MODIFY ("EFF_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_MASTER" MODIFY ("CREATED_ON" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_MASTER" MODIFY ("CREATED_BY" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FUNCTION_ROLE_MASTER
--------------------------------------------------------

  ALTER TABLE "FUNCTION_ROLE_MASTER" ADD CONSTRAINT "PK18" PRIMARY KEY ("ROLE_ID", "FUNCTION_ID") ENABLE;
 
  ALTER TABLE "FUNCTION_ROLE_MASTER" MODIFY ("ROLE_ID" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_ROLE_MASTER" MODIFY ("FUNCTION_ID" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_ROLE_MASTER" MODIFY ("EFF_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_ROLE_MASTER" MODIFY ("CREATED_ON" NOT NULL ENABLE);
 
  ALTER TABLE "FUNCTION_ROLE_MASTER" MODIFY ("CREATED_BY" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table INSTRUCTION_LOG
--------------------------------------------------------

  ALTER TABLE "INSTRUCTION_LOG" ADD CONSTRAINT "INSTRUCTION_LOG_PK" PRIMARY KEY ("SEQ_NO") ENABLE;
 
  ALTER TABLE "INSTRUCTION_LOG" MODIFY ("SEQ_NO" NOT NULL ENABLE);
 
  ALTER TABLE "INSTRUCTION_LOG" MODIFY ("MESSAGE" NOT NULL ENABLE);
 
  ALTER TABLE "INSTRUCTION_LOG" MODIFY ("INSTRUCTING_USER" NOT NULL ENABLE);
 
  ALTER TABLE "INSTRUCTION_LOG" MODIFY ("INSTRUCTION_TIME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table INSTRUCTION_PARAMETERS
--------------------------------------------------------

  ALTER TABLE "INSTRUCTION_PARAMETERS" MODIFY ("INSTRUCTION_LOG_NO" NOT NULL ENABLE);
 
  ALTER TABLE "INSTRUCTION_PARAMETERS" MODIFY ("SL_NO" NOT NULL ENABLE);
 
  ALTER TABLE "INSTRUCTION_PARAMETERS" MODIFY ("NAME" NOT NULL ENABLE);
 
  ALTER TABLE "INSTRUCTION_PARAMETERS" MODIFY ("VALUE" NOT NULL ENABLE);
 
  ALTER TABLE "INSTRUCTION_PARAMETERS" MODIFY ("TYPE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table I_QUEUE
--------------------------------------------------------

  ALTER TABLE "I_QUEUE" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "I_QUEUE" MODIFY ("MESSAGE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOG
--------------------------------------------------------

  ALTER TABLE "LOG" ADD CONSTRAINT "BPMS_BATCH_LOG_PK" PRIMARY KEY ("SEQ_NO") ENABLE;
 
  ALTER TABLE "LOG" MODIFY ("SEQ_NO" NOT NULL ENABLE);
 
  ALTER TABLE "LOG" MODIFY ("BATCH_NO" NOT NULL ENABLE);
 
  ALTER TABLE "LOG" MODIFY ("BATCH_REV_NO" NOT NULL ENABLE);
 
  ALTER TABLE "LOG" MODIFY ("BE_SEQ_NO" NOT NULL ENABLE);
 
  --ALTER TABLE "LOG" MODIFY ("TASK_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "LOG" MODIFY ("OBJ_EXEC_START_TIME" NOT NULL ENABLE);
 
  ALTER TABLE "LOG" MODIFY ("STATUS" NOT NULL ENABLE);
  
  ALTER TABLE "LOG" MODIFY "LIST_IND" NUMBER(10,0);
--------------------------------------------------------
--  Constraints for Table META_DATA
--------------------------------------------------------

  ALTER TABLE "META_DATA" ADD CONSTRAINT "PK3" PRIMARY KEY ("SEQ_NO") ENABLE;
 
  ALTER TABLE "META_DATA" MODIFY ("SEQ_NO" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("TASK_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("EFF_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("PRIORITY_CODE1" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("PRIORITY_CODE2" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("DATE_GENERATE" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("GENERATE_BY" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("JOB_DESC" NOT NULL ENABLE);
 
  ALTER TABLE "META_DATA" MODIFY ("OBJECT_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OBJECT_MAP
--------------------------------------------------------

  ALTER TABLE "OBJECT_MAP" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "OBJECT_MAP" MODIFY ("OBJECT_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table O_QUEUE
--------------------------------------------------------

  ALTER TABLE "O_QUEUE" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "O_QUEUE" MODIFY ("MESSAGE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROCESS_REQUEST
--------------------------------------------------------

  ALTER TABLE "PROCESS_REQUEST" ADD CONSTRAINT "PK_PROCESS_REQUEST" PRIMARY KEY ("REQ_ID") ENABLE;
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("REQ_ID" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("USER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("REQ_DT" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("REQ_STAT" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("PROCESS_CLASS_NM" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("GRP_ST_IND" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("REQ_TYPE" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST" MODIFY ("PRIORITY" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROCESS_REQUEST_SCHEDULE
--------------------------------------------------------

  ALTER TABLE "PROCESS_REQUEST_SCHEDULE" ADD CONSTRAINT "PK_PROCESS_REQUEST_SCHEDULE" PRIMARY KEY ("SCH_ID") ENABLE;
 
  ALTER TABLE "PROCESS_REQUEST_SCHEDULE" MODIFY ("SCH_ID" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST_SCHEDULE" MODIFY ("FREQ_TYPE" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST_SCHEDULE" MODIFY ("RECUR" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST_SCHEDULE" MODIFY ("START_DT" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST_SCHEDULE" MODIFY ("SCH_STAT" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQUEST_SCHEDULE" MODIFY ("USER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROCESS_REQ_PARAMS
--------------------------------------------------------

  ALTER TABLE "PROCESS_REQ_PARAMS" ADD CONSTRAINT "PK_PROCESS_REQ_PARAMS" PRIMARY KEY ("REQ_ID", "PARAM_NO") ENABLE;
 
  ALTER TABLE "PROCESS_REQ_PARAMS" MODIFY ("REQ_ID" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQ_PARAMS" MODIFY ("PARAM_NO" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQ_PARAMS" MODIFY ("PARAM_FLD" NOT NULL ENABLE);
 
  ALTER TABLE "PROCESS_REQ_PARAMS" MODIFY ("PARAM_DATA_TYPE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROGRESS_LEVEL
--------------------------------------------------------

  ALTER TABLE "PROGRESS_LEVEL" MODIFY ("INDICATOR_NO" NOT NULL ENABLE);
 
  ALTER TABLE "PROGRESS_LEVEL" MODIFY ("PRG_ACTIVITY_TYPE" NOT NULL ENABLE);
 
  ALTER TABLE "PROGRESS_LEVEL" MODIFY ("STATUS" NOT NULL ENABLE);
 
  ALTER TABLE "PROGRESS_LEVEL" MODIFY ("START_DATETIME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ROLE_MASTER
--------------------------------------------------------

  ALTER TABLE "ROLE_MASTER" ADD CONSTRAINT "PK17" PRIMARY KEY ("ROLE_ID") ENABLE;
 
  ALTER TABLE "ROLE_MASTER" MODIFY ("ROLE_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ROLE_MASTER" MODIFY ("ROLE_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "ROLE_MASTER" MODIFY ("EFF_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "ROLE_MASTER" MODIFY ("CREATED_ON" NOT NULL ENABLE);
 
  ALTER TABLE "ROLE_MASTER" MODIFY ("CREATED_BY" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SCHEDULE_EVENT_CALENDAR
--------------------------------------------------------

  ALTER TABLE "SCHEDULE_EVENT_CALENDAR" ADD CONSTRAINT "PK4_1" PRIMARY KEY ("SCH_ID", "SERIAL_NO") ENABLE;
 
  ALTER TABLE "SCHEDULE_EVENT_CALENDAR" MODIFY ("SCH_ID" NOT NULL ENABLE);
 
  ALTER TABLE "SCHEDULE_EVENT_CALENDAR" MODIFY ("SERIAL_NO" NOT NULL ENABLE);
 
  ALTER TABLE "SCHEDULE_EVENT_CALENDAR" MODIFY ("CATEGORY" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SYSTEM_INFO
--------------------------------------------------------

  ALTER TABLE "SYSTEM_INFO" MODIFY ("BATCH_NO" NOT NULL ENABLE);
 
  ALTER TABLE "SYSTEM_INFO" MODIFY ("BATCH_REV_NO" NOT NULL ENABLE);
  
  ALTER TABLE "SYSTEM_INFO" MODIFY ("OS_CONFIG" VARCHAR2(200));
--------------------------------------------------------
--  Constraints for Table USER_MASTER
--------------------------------------------------------

  ALTER TABLE "USER_MASTER" ADD CONSTRAINT "PK16" PRIMARY KEY ("USER_ID") ENABLE;
 
  ALTER TABLE "USER_MASTER" MODIFY ("USER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "USER_MASTER" MODIFY ("USER_NAME" NOT NULL ENABLE);
 
  ALTER TABLE "USER_MASTER" MODIFY ("EMAIL_ID" NOT NULL ENABLE);
 
  ALTER TABLE "USER_MASTER" MODIFY ("EFF_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "USER_MASTER" MODIFY ("CREATED_ON" NOT NULL ENABLE);
 
  ALTER TABLE "USER_MASTER" MODIFY ("ASSIGNED_ROLE" NOT NULL ENABLE);

  create unique index o_q_i1 on o_queue (id);
  
--------------------------------------------------------
--  DDL for Index INSTRUCTION_PARAMETERS_INDEX1
--------------------------------------------------------

  CREATE INDEX "INSTRUCTION_PARAMETERS_INDEX1" ON "INSTRUCTION_PARAMETERS" ("INSTRUCTION_LOG_NO") 
  ;
--------------------------------------------------------
--  DDL for Index REF1514
--------------------------------------------------------

  CREATE INDEX "REF1514" ON "FUNCTION_ROLE_MASTER" ("FUNCTION_ID") 
  ;
--------------------------------------------------------
--  DDL for Index REF1712
--------------------------------------------------------

  CREATE INDEX "REF1712" ON "USER_MASTER" ("ASSIGNED_ROLE") 
  ;
--------------------------------------------------------
--  DDL for Index REF1713
--------------------------------------------------------

  CREATE INDEX "REF1713" ON "FUNCTION_ROLE_MASTER" ("ROLE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index REF25
--------------------------------------------------------

  CREATE INDEX "REF25" ON "PROGRESS_LEVEL" ("BATCH_NO", "BATCH_REV_NO") 
  ;
--------------------------------------------------------
--  Ref Constraints for Table FUNCTION_ROLE_MASTER
--------------------------------------------------------

  ALTER TABLE "FUNCTION_ROLE_MASTER" ADD CONSTRAINT "REFFUNCTION_MASTER14" FOREIGN KEY ("FUNCTION_ID")
	  REFERENCES "FUNCTION_MASTER" ("FUNCTION_ID") ENABLE;
 
  ALTER TABLE "FUNCTION_ROLE_MASTER" ADD CONSTRAINT "REFROLE_MASTER13" FOREIGN KEY ("ROLE_ID")
	  REFERENCES "ROLE_MASTER" ("ROLE_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table INSTRUCTION_PARAMETERS
--------------------------------------------------------

  ALTER TABLE "INSTRUCTION_PARAMETERS" ADD CONSTRAINT "INSTRUCTION_PARAMETERS_IN_FK1" FOREIGN KEY ("INSTRUCTION_LOG_NO")
	  REFERENCES "INSTRUCTION_LOG" ("SEQ_NO") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOG
--------------------------------------------------------

  ALTER TABLE "LOG" ADD CONSTRAINT "REFBPMS_BATCH19" FOREIGN KEY ("BATCH_NO", "BATCH_REV_NO")
	  REFERENCES "BATCH" ("BATCH_NO", "BATCH_REV_NO") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PROGRESS_LEVEL
--------------------------------------------------------

  ALTER TABLE "PROGRESS_LEVEL" ADD CONSTRAINT "REFBPMS_BATCH5" FOREIGN KEY ("BATCH_NO", "BATCH_REV_NO")
	  REFERENCES "BATCH" ("BATCH_NO", "BATCH_REV_NO") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SYSTEM_INFO
--------------------------------------------------------

  ALTER TABLE "SYSTEM_INFO" ADD CONSTRAINT "REFBPMS_BATCH9" FOREIGN KEY ("BATCH_NO", "BATCH_REV_NO")
	  REFERENCES "BATCH" ("BATCH_NO", "BATCH_REV_NO") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_MASTER
--------------------------------------------------------

  ALTER TABLE "USER_MASTER" ADD CONSTRAINT "REFROLE_MASTER12" FOREIGN KEY ("ASSIGNED_ROLE")
	  REFERENCES "ROLE_MASTER" ("ROLE_ID") ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIG_BATCH
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIG_BATCH" 
AFTER INSERT OR UPDATE ON BATCH
FOR EACH ROW
BEGIN
	IF INSERTING THEN
		INSERT INTO O_QUEUE 
		(
        id
        , message
        , param
    )
		VALUES
		(
        o_queue_seq.nextval
        , 'BSADDBATCH'
        , 'batchNo=' || :new.BATCH_NO || ', batchRevNo=' || :new.BATCH_REV_NO
    ); 
	ELSIF UPDATING THEN
		INSERT INTO O_QUEUE 
		(
        id
        , message
        , param
    )
		VALUES
		(
        o_queue_seq.nextval
        , 'BSUPDBATCH'
        , 'batchNo=' || :new.BATCH_NO || ', batchRevNo=' || :new.BATCH_REV_NO
    );
	END IF;  
END;
/
--------------------------------------------------------
--  DDL for Trigger TRIG_INSTRUCTION_LOG
--------------------------------------------------------

create or replace TRIGGER "TRIG_INSTRUCTION_LOG" 
AFTER INSERT OR UPDATE ON INSTRUCTION_LOG
FOR EACH ROW 
BEGIN
  IF INSERTING THEN  
    IF :new.MESSAGE = 'BSSTOBATCH' 
      THEN 
        INSERT INTO I_QUEUE 
        (
              id
            , message
            , param
        )
        VALUES 
        (
            1 -- it is OK to hard code 1 as well. ID is retained for probable future changes
            , :new.MESSAGE
            , 'UserId=' || :new.INSTRUCTING_USER || ',BatchNo=' || :new.BATCH_NO
        );		
     ELSIF :new.MESSAGE = 'BSRUNBATCH' 
      THEN
        INSERT INTO O_QUEUE 
        (
            id
            , message
            , param
        )
        VALUES
        (
            o_queue_seq.nextval
            , 'BSADDINSLG'
            , 'seqNo=' || to_char(:new.SEQ_NO)
        ); 
       END IF;
    ELSIF UPDATING THEN 
      INSERT INTO O_QUEUE 
        (
            id
            , message
            , param
        )
        VALUES
        (
            o_queue_seq.nextval
            , 'BSUPDINSLG'
            , 'seqNo=' || to_char(:new.SEQ_NO)
        );        
   END IF;
END;
/
--------------------------------------------------------
--  DDL for Trigger TRIG_LOG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIG_LOG" 
AFTER INSERT OR UPDATE ON LOG
FOR EACH ROW
DECLARE
   mmyMessage VARCHAR2(10);
BEGIN
    IF INSERTING THEN
      mmyMessage := 'BSADDBALOG';
    ELSE 
      mmyMessage := 'BSUPDBALOG';
    END IF;
    
    INSERT INTO O_QUEUE 
		(
        id
        , message
        , param
    )
		VALUES
		(
        o_queue_seq.nextval
        , mmyMessage
        , 'batchSeqNo=' || to_char(:new.SEQ_NO)
    ); 
END;
/
--------------------------------------------------------
--  DDL for Trigger TRIG_PROGRESS_LEVEL
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIG_PROGRESS_LEVEL" 
AFTER INSERT OR UPDATE ON PROGRESS_LEVEL
FOR EACH ROW
BEGIN
	IF INSERTING THEN
		INSERT INTO O_QUEUE 
		( 
        id
        , message
        , param
    )
    VALUES
    ( 
        o_queue_seq.nextval
        , 'SSADDBAPRG'
        , 'batchNo=' || :new.BATCH_NO || ', batchRevNo=' || :new.BATCH_REV_NO || 
                    ', indicatorNo=' || :new.INDICATOR_NO ); 
	ELSIF UPDATING THEN
		INSERT INTO O_QUEUE
		(
        id
        , message
        , param
    )
		VALUES
		(
        o_queue_seq.nextval
        , 'SSUPDBAPRG'
        , 'batchNo=' || :new.BATCH_NO || ', batchRevNo=' || :new.BATCH_REV_NO || 
                    ', indicatorNo=' || :new.INDICATOR_NO ); 
	END IF;  
END;
/
--------------------------------------------------------
--  DDL for Trigger TRIG_SYSTEM_INFO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIG_SYSTEM_INFO" 
AFTER INSERT ON SYSTEM_INFO
FOR EACH ROW
BEGIN
  IF INSERTING THEN  
  INSERT INTO O_QUEUE 
		(
        id
        , message
        , param
    )
		VALUES
		(
        o_queue_seq.nextval
        , 'SSADDSYSIN'
        , 'batchNo=' || :new.BATCH_NO || ', batchRevNo=' || :new.BATCH_REV_NO
    );		
	END IF;
END;
/