ALTER TABLE LOG ALTER column [OBJECT_NAME] VARCHAR(100);
ALTER TABLE SYSTEM_INFO ALTER column [OS_CONFIG] VARCHAR(200);

Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Schedule','Schedule','Schedule Menu','07-06-2010',null,'07-06-2010','JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('View Schedules','View Schedules','View Schedules Menu','07-06-2010',null,'07-06-2010','JBEAM','Schedule');

Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('OPERATOR','Schedule','08-06-2010',null,'08-06-2010','ADMIN','jbeam');

/****** Object:  Table [dbo].[PROCESS_REQUEST_SCHEDULE]    Script Date: 09/20/2013 15:34:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROCESS_REQUEST_SCHEDULE](
	[INSTALLATION_CODE] [varchar](15) NOT NULL,
	[SCH_ID] [numeric](12, 0) NOT NULL,
	[FREQ_TYPE] [varchar](10) NOT NULL,
	[RECUR] [numeric](10, 0) NOT NULL,
	[START_DT] [datetime2](0) NOT NULL,
	[SCH_STAT] [varchar](2) NOT NULL,
	[USER_ID] [varchar](15) NOT NULL,
	[ON_WEEK_DAY] [varchar](7) NULL,
	[END_DT] [datetime2](0) NULL,
	[END_OCCUR] [numeric](12, 0) NULL,
	[ENTRY_DT] [datetime2](0) NULL,
	[MODIFY_ID] [varchar](10) NULL,
	[MODIFY_DT] [datetime2](0) NULL,
	[REQ_STAT] [varchar](2) NULL,
	[OCCUR_COUNTER] [numeric](12, 0) NULL,
	[PROCESS_CLASS_NM] [varchar](500) NULL,
	[START_TIME] [datetime2](0) NULL,
	[END_TIME] [datetime2](0) NULL,
	[FUTURE_SCHEDULING_ONLY] [varchar](1) NULL,
	[FIXED_DATE] [varchar](1) NULL,
	[EMAIL_IDS] [varchar](2000) NULL,
	[SKIP_FLAG] [varchar](2) NULL,
	[WEEKDAY_CHECK_FLAG] [varchar](2) NULL,
	[END_REASON] [varchar](2000) NULL,
	[KEEP_ALIVE] [varchar](1) NULL,
   	[BATCH_NAME] [varchar](25) NULL
 CONSTRAINT [PK_PROCESS_REQUEST_SCHEDULE] PRIMARY KEY CLUSTERED 
(
	[SCH_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.BATCH_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'BATCH_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.SCH_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'SCH_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.FREQ_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'FREQ_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.RECUR' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'RECUR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.START_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'START_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.SCH_STAT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'SCH_STAT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.ON_WEEK_DAY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'ON_WEEK_DAY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.END_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.END_OCCUR' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_OCCUR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.ENTRY_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'ENTRY_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.MODIFY_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'MODIFY_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.MODIFY_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'MODIFY_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.REQ_STAT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'REQ_STAT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.OCCUR_COUNTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'OCCUR_COUNTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.PROCESS_CLASS_NM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'PROCESS_CLASS_NM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.START_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'START_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.END_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.FUTURE_SCHEDULING_ONLY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'FUTURE_SCHEDULING_ONLY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.FIXED_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'FIXED_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.EMAIL_IDS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'EMAIL_IDS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.SKIP_FLAG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'SKIP_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.WEEKDAY_CHECK_FLAG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'WEEKDAY_CHECK_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.END_REASON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_REASON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.KEEP_ALIVE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'KEEP_ALIVE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.PK_PROCESS_REQUEST_SCHEDULE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'CONSTRAINT',@level2name=N'PK_PROCESS_REQUEST_SCHEDULE'
GO
 