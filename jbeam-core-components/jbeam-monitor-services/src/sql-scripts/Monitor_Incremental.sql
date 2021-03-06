ALTER TABLE LOG MODIFY OBJECT_NAME VARCHAR2(100);
ALTER TABLE SYSTEM_INFO MODIFY OS_CONFIG VARCHAR2(200);

Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Schedule','Schedule','Schedule Menu',to_timestamp('07-06-2010 11:59:59','DD-MM-RR HH12:MI:SSXFF AM'),null,to_timestamp('07-06-2010 11:59:59','DD-MM-RR HH12:MI:SSXFF AM'),'JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('View Schedules','View Schedules','View Schedules Menu',to_timestamp('07-06-2010 11:59:59','DD-MM-RR HH12:MI:SSXFF AM'),null,to_timestamp('07-06-2010 11:59:59','DD-MM-RR HH12:MI:SSXFF AM'),'JBEAM','Schedule');

Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('OPERATOR','Schedule',to_timestamp('08-06-2010 11:59:59','DD-MM-RR HH12:MI:SSXFF AM'),null,to_timestamp('08-06-2010 11:59:59','DD-MM-RR HH12:MI:SSXFF AM'),'ADMIN','jbeam');

COMMIT;

CREATE TABLE PROCESS_REQUEST_SCHEDULE
   (	
   	"INSTALLATION_CODE" VARCHAR2(15 BYTE) NOT NULL ENABLE,
   	"BATCH_NAME" VARCHAR2(25 BYTE),
   	"SCH_ID" NUMBER(12,0) NOT NULL ENABLE, 
	"FREQ_TYPE" VARCHAR2(10 BYTE) NOT NULL ENABLE, 
	"RECUR" NUMBER(10,0) NOT NULL ENABLE, 
	"START_DT" DATE NOT NULL ENABLE, 
	"SCH_STAT" VARCHAR2(2 BYTE) NOT NULL ENABLE, 
	"USER_ID" VARCHAR2(15 BYTE) NOT NULL ENABLE, 
	"ON_WEEK_DAY" VARCHAR2(7 BYTE), 
	"END_DT" DATE, 
	"END_OCCUR" NUMBER(12,0), 
	"ENTRY_DT" DATE, 
	"MODIFY_ID" VARCHAR2(10 BYTE), 
	"MODIFY_DT" DATE, 
	"REQ_STAT" VARCHAR2(2 BYTE), 
	"OCCUR_COUNTER" NUMBER(12,0), 
	"PROCESS_CLASS_NM" VARCHAR2(500 BYTE), 
	"START_TIME" DATE, 
	"END_TIME" DATE, 
	"FUTURE_SCHEDULING_ONLY" VARCHAR2(1 BYTE) DEFAULT 'Y', 
	"FIXED_DATE" VARCHAR2(1 BYTE) DEFAULT 'N', 
	"EMAIL_IDS" VARCHAR2(2000 BYTE), 
	"SKIP_FLAG" VARCHAR2(2 BYTE) DEFAULT NULL, 
	"WEEKDAY_CHECK_FLAG" VARCHAR2(2 BYTE) DEFAULT NULL, 
	"END_REASON" VARCHAR2(2000 BYTE) DEFAULT NULL, 
	"KEEP_ALIVE" VARCHAR2(1 BYTE) DEFAULT 'N', 
	 CONSTRAINT "PK_PROCESS_REQUEST_SCHEDULE" PRIMARY KEY ("SCH_ID")
   ) ;
 