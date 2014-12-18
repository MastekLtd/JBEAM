--------------------------------------------------------
--  File created - Tuesday-December-02-2014   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table JOB_SCHEDULE
--------------------------------------------------------

  CREATE TABLE "DEVBILL_BASE"."JOB_SCHEDULE" 
   (	"JOB_SEQ" NUMBER(22,0), 
	"JOB_NAME" NVARCHAR2(60), 
	"JOB_STATUS" NVARCHAR2(25), 
	"EXECUTION_DATE" DATE, 
	"SYSTEM_TRANSACTION_SEQ" NUMBER(22,0), 
	"GROUP_SYSTEM_CODE" NUMBER(22,0), 
	"ACCOUNT_SYSTEM_CODE" NUMBER(22,0), 
	"BROKER_SYSTEM_CODE" NUMBER(22,0), 
	"POLICY_NO" NVARCHAR2(50), 
	"POLICY_RENEW_NO" NUMBER(4,0), 
	"POLICY_TERM_ID" NUMBER(22,0), 
	"BILL_NO" NUMBER(22,0), 
	"CREATED_ON" DATE, 
	"CREATED_BY" NVARCHAR2(30), 
	"JOB_DETAIL" NVARCHAR2(1000), 
	"DATE_EXECUTED" DATE, 
	"PRIORITY_CODE_1" NUMBER(22,0), 
	"PRIORITY_CODE_2" NUMBER(22,0), 
	"LAST_MODIFIED_ON" DATE, 
	"LAST_MODIFIED_BY" NVARCHAR2(30), 
	"SYSTEM_REMARKS" NVARCHAR2(1000), 
	"USER_REMARKS" NVARCHAR2(1000), 
	"JOB_TYPE" NVARCHAR2(20), 
	"PRE_POST" NVARCHAR2(20), 
	"ENTITY_TYPE" NVARCHAR2(20), 
	"SYSTEM_ACTIVITY_NO" NVARCHAR2(25), 
	"POLICY_ID" NUMBER(22,0), 
	"JOB_DUE_DATE" DATE, 
	"SOURCE_SYSTEM_ENTITY_CODE" NVARCHAR2(20), 
	"ENTITY_GROUP" NVARCHAR2(20), 
	"LISTENER_INDICATOR" NUMBER(22,0), 
	"OUTPUT_EVENT" NVARCHAR2(40), 
	"REFERENCE_ID" NVARCHAR2(50), 
	"SUB_REFERENCE_ID" NUMBER(4,0), 
	"CONTEXT" NVARCHAR2(50), 
	"REFERENCE_SEQUENCE" NUMBER(22,0), 
	"REFERENCE_DATE" DATE, 
	"SOURCE_SYSTEM" NVARCHAR2(20)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 4194304 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA"  ENABLE ROW MOVEMENT ;
--------------------------------------------------------
--  DDL for Index IDX_JOB_SCHEDULE_FK2
--------------------------------------------------------

  CREATE INDEX "DEVBILL_BASE"."IDX_JOB_SCHEDULE_FK2" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("GROUP_SYSTEM_CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_JOB_SCHEDULE_FK3
--------------------------------------------------------

  CREATE INDEX "DEVBILL_BASE"."IDX_JOB_SCHEDULE_FK3" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("ACCOUNT_SYSTEM_CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_JOB_SCHEDULE_FK1
--------------------------------------------------------

  CREATE INDEX "DEVBILL_BASE"."IDX_JOB_SCHEDULE_FK1" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("SYSTEM_TRANSACTION_SEQ") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 524288 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_JOB_SCHEDULE_FK5
--------------------------------------------------------

  CREATE INDEX "DEVBILL_BASE"."IDX_JOB_SCHEDULE_FK5" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("POLICY_TERM_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 655360 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_JOB_SCHEDULE_FK6
--------------------------------------------------------

  CREATE INDEX "DEVBILL_BASE"."IDX_JOB_SCHEDULE_FK6" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("POLICY_NO", "POLICY_RENEW_NO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 2097152 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_JOB_SCHEDULE_FK7
--------------------------------------------------------

  CREATE INDEX "DEVBILL_BASE"."IDX_JOB_SCHEDULE_FK7" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("BILL_NO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 393216 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  DDL for Index JOB_SCHEDULE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DEVBILL_BASE"."JOB_SCHEDULE_PK" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("JOB_SEQ") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 458752 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_JOB_SCHEDULE_FK4
--------------------------------------------------------

  CREATE INDEX "DEVBILL_BASE"."IDX_JOB_SCHEDULE_FK4" ON "DEVBILL_BASE"."JOB_SCHEDULE" ("BROKER_SYSTEM_CODE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA" ;
--------------------------------------------------------
--  Constraints for Table JOB_SCHEDULE
--------------------------------------------------------

  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" MODIFY ("PRIORITY_CODE_1" NOT NULL ENABLE);
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" MODIFY ("CREATED_BY" NOT NULL DISABLE);
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" MODIFY ("CREATED_ON" NOT NULL DISABLE);
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" MODIFY ("EXECUTION_DATE" NOT NULL ENABLE);
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" MODIFY ("JOB_STATUS" NOT NULL ENABLE);
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" MODIFY ("JOB_NAME" NOT NULL ENABLE);
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" MODIFY ("JOB_SEQ" NOT NULL ENABLE);
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_PK" PRIMARY KEY ("JOB_SEQ")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 458752 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "PNCPRDCT_DATA"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table JOB_SCHEDULE
--------------------------------------------------------

  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_FK1" FOREIGN KEY ("SYSTEM_TRANSACTION_SEQ")
	  REFERENCES "DEVBILL_BASE"."TRANSACTION_LOG" ("SYSTEM_TRANSACTION_SEQ") DISABLE;
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_FK2" FOREIGN KEY ("GROUP_SYSTEM_CODE")
	  REFERENCES "DEVBILL_BASE"."ENTITY_REGISTER" ("SYSTEM_ENTITY_CODE") DISABLE;
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_FK3" FOREIGN KEY ("ACCOUNT_SYSTEM_CODE")
	  REFERENCES "DEVBILL_BASE"."ENTITY_REGISTER" ("SYSTEM_ENTITY_CODE") DISABLE;
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_FK4" FOREIGN KEY ("BROKER_SYSTEM_CODE")
	  REFERENCES "DEVBILL_BASE"."ENTITY_REGISTER" ("SYSTEM_ENTITY_CODE") DISABLE;
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_FK5" FOREIGN KEY ("POLICY_TERM_ID")
	  REFERENCES "DEVBILL_BASE"."POLICY_REGISTER" ("POLICY_TERM_ID") DISABLE;
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_FK6" FOREIGN KEY ("POLICY_NO", "POLICY_RENEW_NO")
	  REFERENCES "DEVBILL_BASE"."POLICY_REGISTER" ("POLICY_NO", "POLICY_RENEW_NO") DISABLE;
  ALTER TABLE "DEVBILL_BASE"."JOB_SCHEDULE" ADD CONSTRAINT "JOB_SCHEDULE_FK7" FOREIGN KEY ("BILL_NO")
	  REFERENCES "DEVBILL_BASE"."INSTALLMENT_SCHEDULE" ("BILL_NO") DISABLE;