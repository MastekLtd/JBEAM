Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','AUTH_HANDLER','JV','com.stgmastek.core.authentication.defaultimpl.AuthenticationHandlerImpl','S','Authentication Handler Class');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','AUTH_FILTER_HANDLER','JV','com.stgmastek.core.authentication.defaultimpl.AuthenticationRequestFilterImpl','S','Authentication Filter Class');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','THREAD_POOL','CORE_SIZE','5','S','The number of threads to keep in the pool, even if they are idle');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','THREAD_POOL','MAX_CORE_SIZE','5','S','The maximum number of threads to allow in the pool.');

ALTER TABLE LOG ALTER column [OBJECT_NAME] VARCHAR(100);