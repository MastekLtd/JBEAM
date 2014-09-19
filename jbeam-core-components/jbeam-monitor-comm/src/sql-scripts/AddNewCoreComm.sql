SET DEFINE ON
Accept installationCode VARCHAR2 PROMPT 'Enter Installation Code:'
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('&installationCode','COLLATOR','WAIT_PERIOD','10000','I','The collator wait period for installation &installationCode');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('&installationCode','PERSCANCOLLATOR','WAIT_PERIOD','1','I','This value is defined for per scan execution count collator. The time is in minutes, default is 1.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('INSTALLATION_WS','&installationCode','SERVICES','&ipPort','S','The <IP>:<PORT> of the published communication services for the installation');

Insert into REPORT_MASTER (INSTALLATION_CODE,ID,NAME,PROG_NAME,SR_NO,COMPANY_CODE,PARENT_ID,SQL_QUERY,TYPE) values ('&installationCode','1','Purge Routine Core','com.stgmastek.core.purge.PurgeBatchDetails','1','ALL',null,null,null);
Insert into INSTALLATION (INSTALLATION_CODE,INSTALLATION_DESC,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,MODIFIED_ON,MODIFIED_BY,BATCH_NO,BATCH_REV_NO,TIMEZONE_ID) values ('&installationCode','&installationDesc',to_timestamp('01-JAN-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),null,to_timestamp('01-JAN-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),'JBEAM',null,null,null,null,'America/Los_Angeles');

Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('&installationCode','PRE','PRE_POST','PRE','PRIORITY_CODE_1',1,null,'Enter the priority code 1 to execute all PRE events associated with it.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('&installationCode','POLICY','POLICY_NO',null,'POLICY_NO#POLICY_RENEW_NO',2,'Y','Enter the policy number ''#'' renew number. Two parameters required to be separated with ''#''. Use ''/'' as an escape character.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('&installationCode','ACCOUNT','ENTITY_TYPE','ACCOUNT','ENTITY_CODE',4,'Y','Enter the ACCOUNT_SYSTEM_CODE.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('&installationCode','BROKER','ENTITY_TYPE','BROKER','ENTITY_CODE',5,'Y','Enter the BROKER_SYSTEM_CODE.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('&installationCode','GENERAL','JOB_SEQ',null,'JOB_SEQ',6,null,'Enter the job sequence number');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('&installationCode','POST','PRE_POST','POST','PRIORITY_CODE_1',999,null,'Enter the priority code 1 to execute the POST jobs associated with it');
SET DEFINE OFF