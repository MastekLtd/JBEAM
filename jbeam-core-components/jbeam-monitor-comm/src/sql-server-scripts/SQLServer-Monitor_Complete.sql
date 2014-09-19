USE [BPMS_MONITOR]
GO
/****** Object:  Table [dbo].[BATCH]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BATCH](
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[BATCH_NO] [numeric](10, 0) NOT NULL,
	[BATCH_REV_NO] [numeric](3, 0) NOT NULL,
	[BATCH_NAME] [varchar](25) NOT NULL,
	[BATCH_TYPE] [varchar](20) NOT NULL,
	[EXEC_START_TIME] [datetime2](3) NOT NULL,
	[EXEC_END_TIME] [datetime2](3) NULL,
	[BATCH_START_USER] [varchar](30) NULL,
	[BATCH_END_USER] [varchar](30) NULL,
	[PROCESS_ID] [numeric](12, 0) NULL,
	[BATCH_END_REASON] [varchar](25) NULL,
	[FAILED_OVER] [varchar](1) NULL,
	[INSTRUCTION_SEQ_NO] [numeric](19, 0) NULL,
 CONSTRAINT [PK2] PRIMARY KEY CLUSTERED 
(
	[INSTALLATION_CODE] ASC,
	[BATCH_NO] ASC,
	[BATCH_REV_NO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CALENDAR_LOG]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CALENDAR_LOG](
	[INSTALLATION_CODE] [varchar](20) NOT NULL,
	[CALENDAR_NAME] [varchar](30) NOT NULL,
	[YEAR] [varchar](4) NOT NULL,
	[NON_WORKING_DATE] [datetime2](0) NOT NULL,
	[REMARK] [varchar](50) NULL,
	[USER_ID] [varchar](20) NOT NULL,
	[ROWID] [uniqueidentifier] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[COLUMN_MAP]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[COLUMN_MAP](
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[ENTITY] [varchar](20) NOT NULL,
	[LOOKUP_COLUMN] [varchar](30) NULL,
	[LOOKUP_VALUE] [varchar](30) NULL,
	[VALUE_COLUMN] [varchar](1000) NULL,
	[PRECEDENCE_ORDER] [numeric](3, 0) NOT NULL,
	[ON_ERROR_FAIL_ALL] [varchar](1) NULL,
	[DESCRIPTION] [varchar](2000) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CONFIGURATION]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CONFIGURATION](
	[CODE1] [varchar](20) NOT NULL,
	[CODE2] [varchar](20) NULL,
	[CODE3] [varchar](25) NULL,
	[VALUE] [varchar](100) NOT NULL,
	[VALUE_TYPE] [varchar](2) NULL,
	[DESCRIPTION] [varchar](1000) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DEAD_MESSAGE_QUEUE]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DEAD_MESSAGE_QUEUE](
	[ID] [numeric](10, 0) NOT NULL,
	[I_O_MODE] [varchar](1) NOT NULL,
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[MESSAGE] [varchar](10) NOT NULL,
	[PARAM] [varchar](100) NULL,
	[ERROR_DESCRIPTION] [varchar](4000) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FUNCTION_MASTER]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FUNCTION_MASTER](
	[FUNCTION_ID] [varchar](30) NOT NULL,
	[FUNCTION_NAME] [varchar](30) NOT NULL,
	[FUNCTION_INFO] [varchar](500) NOT NULL,
	[EFF_DATE] [datetime2](0) NOT NULL,
	[EXP_DATE] [datetime2](0) NULL,
	[CREATED_ON] [datetime2](0) NOT NULL,
	[CREATED_BY] [varchar](30) NOT NULL,
	[PRIOR_FUNCTION_ID] [varchar](30) NULL,
 CONSTRAINT [PK15] PRIMARY KEY CLUSTERED 
(
	[FUNCTION_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FUNCTION_ROLE_MASTER]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FUNCTION_ROLE_MASTER](
	[ROLE_ID] [varchar](30) NOT NULL,
	[FUNCTION_ID] [varchar](30) NOT NULL,
	[EFF_DATE] [datetime2](0) NOT NULL,
	[EXP_DATE] [datetime2](0) NULL,
	[CREATED_ON] [datetime2](0) NOT NULL,
	[CREATED_BY] [varchar](30) NOT NULL,
	[USER_ID] [varchar](30) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GRAPH_DATA_LOG]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GRAPH_DATA_LOG](
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[GRAPH_ID] [varchar](100) NOT NULL,
	[BATCH_NO] [numeric](10, 0) NOT NULL,
	[BATCH_REV_NO] [numeric](3, 0) NOT NULL,
	[COLLECT_TIME] [datetime2](6) NOT NULL,
	[GRAPH_X_AXIS] [varchar](100) NULL,
	[GRAPH_Y_AXIS] [varchar](100) NULL,
	[GRAPH_VALUE] [numeric](12, 2) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[INSTALLATION]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[INSTALLATION](
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[INSTALLATION_DESC] [varchar](200) NULL,
	[EFF_DATE] [datetime2](0) NOT NULL,
	[EXP_DATE] [datetime2](0) NULL,
	[CREATED_ON] [datetime2](0) NOT NULL,
	[CREATED_BY] [varchar](30) NOT NULL,
	[MODIFIED_ON] [datetime2](0) NULL,
	[MODIFIED_BY] [varchar](30) NULL,
	[BATCH_NO] [numeric](10, 0) NULL,
	[BATCH_REV_NO] [numeric](3, 0) NULL,
	[TIMEZONE_ID] [varchar](50) NULL,
 CONSTRAINT [PK29] PRIMARY KEY CLUSTERED 
(
	[INSTALLATION_CODE] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[INSTRUCTION_LOG]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[INSTRUCTION_LOG](
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[SEQ_NO] [numeric](10, 0) NOT NULL,
	[BATCH_NO] [numeric](10, 0) NULL,
	[BATCH_REV_NO] [numeric](3, 0) NULL,
	[MESSAGE] [varchar](10) NOT NULL,
	[MESSAGE_PARAM] [varchar](100) NULL,
	[INSTRUCTING_USER] [varchar](30) NOT NULL,
	[INSTRUCTION_TIME] [datetime2](3) NOT NULL,
	[BATCH_ACTION] [varchar](500) NULL,
	[BATCH_ACTION_TIME] [datetime2](3) NULL,
	[ROWID] [uniqueidentifier] NOT NULL,
 CONSTRAINT [INSTRUCTION_LOG_PK] PRIMARY KEY CLUSTERED 
(
	[SEQ_NO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[INSTRUCTION_PARAMETERS]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[INSTRUCTION_PARAMETERS](
	[INSTRUCTION_LOG_NO] [numeric](10, 0) NOT NULL,
	[SL_NO] [numeric](3, 0) NOT NULL,
	[NAME] [varchar](25) NOT NULL,
	[VALUE] [varchar](100) NOT NULL,
	[TYPE] [varchar](5) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LOG]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LOG](
	[INSTALLATION_CODE] [varchar](10) NULL,
	[BATCH_NO] [numeric](10, 0) NULL,
	[BATCH_REV_NO] [numeric](3, 0) NULL,
	[BE_SEQ_NO] [varchar](12) NOT NULL,
	[TASK_NAME] [varchar](2000) NULL,
	[OBJ_EXEC_START_TIME] [datetime2](3) NULL,
	[OBJ_EXEC_END_TIME] [datetime2](3) NULL,
	[STATUS] [varchar](2) NULL,
	[SYS_ACT_NO] [varchar](25) NULL,
	[USER_PRIORITY] [varchar](2) NULL,
	[PRIORITY_CODE1] [numeric](10, 0) NULL,
	[PRIORITY_CODE2] [numeric](10, 0) NULL,
	[PRE_POST] [varchar](20) NULL,
	[JOB_TYPE] [varchar](2) NULL,
	[LINE] [varchar](10) NULL,
	[SUBLINE] [varchar](10) NULL,
	[BROKER] [varchar](20) NULL,
	[POLICY_NO] [varchar](20) NULL,
	[POLICY_RENEW_NO] [varchar](2) NULL,
	[VEH_REF_NO] [varchar](12) NULL,
	[CASH_BATCH_NO] [varchar](25) NULL,
	[CASH_BATCH_REV_NO] [varchar](2) NULL,
	[GBI_BILL_NO] [varchar](24) NULL,
	[PRINT_FORM_NO] [varchar](8) NULL,
	[NOTIFY_ERROR_TO] [varchar](10) NULL,
	[DATE_GENERATE] [datetime2](0) NULL,
	[GENERATE_BY] [varchar](30) NULL,
	[REC_MESSAGE] [varchar](80) NULL,
	[JOB_DESC] [varchar](80) NULL,
	[OBJECT_NAME] [varchar](100) NULL,
	[DATE_EXECUTED] [datetime2](0) NULL,
	[LIST_IND] [numeric](3, 0) NULL,
	[ENTITY_TYPE] [varchar](15) NULL,
	[ENTITY_CODE] [varchar](20) NULL,
	[REF_SYSTEM_ACTIVITY_NO] [varchar](14) NULL,
	[ERROR_TYPE] [varchar](25) NULL,
	[ERROR_DESCRIPTION] [varchar](4000) NULL,
	[TIME_TAKEN] [numeric](10, 0) NULL,
	[CYCLE_NO] [numeric](10, 0) NULL,
	[USED_MEMORY_BEFORE] [numeric](19, 0) NULL,
	[USED_MEMORY_AFTER] [numeric](19, 0) NULL,
	[SEQ_NO] [float] NULL,
	[LAST_UPDATED_ON] [datetime2](3) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[O_QUEUE]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_QUEUE](
	[ID] [numeric](10, 0) NOT NULL,
	[MESSAGE] [varchar](10) NOT NULL,
	[PARAM] [varchar](100) NULL,
	[INSTALLATION_CODE] [varchar](10) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PROCESS_REQUEST_SCHEDULE]    Script Date: 26-09-2013 PM 04:06:18 ******/
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
	[BATCH_NAME] [varchar](25) NULL,
 CONSTRAINT [PK_PROCESS_REQUEST_SCHEDULE] PRIMARY KEY CLUSTERED 
(
	[SCH_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PROGRESS_LEVEL]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROGRESS_LEVEL](
	[INSTALLATION_CODE] [varchar](10) NULL,
	[BATCH_NO] [numeric](10, 0) NULL,
	[BATCH_REV_NO] [numeric](3, 0) NULL,
	[INDICATOR_NO] [numeric](4, 0) NOT NULL,
	[PRG_LEVEL_TYPE] [varchar](10) NULL,
	[PRG_ACTIVITY_TYPE] [varchar](20) NOT NULL,
	[CYCLE_NO] [numeric](3, 0) NULL,
	[STATUS] [varchar](2) NOT NULL,
	[START_DATETIME] [datetime2](3) NOT NULL,
	[END_DATETIME] [datetime2](3) NULL,
	[ERROR_DESC] [varchar](100) NULL,
	[FAILED_OVER] [varchar](1) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[REPORT_MASTER]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[REPORT_MASTER](
	[INSTALLATION_CODE] [varchar](20) NOT NULL,
	[ID] [varchar](60) NOT NULL,
	[NAME] [varchar](200) NOT NULL,
	[PROG_NAME] [varchar](4000) NULL,
	[SR_NO] [varchar](80) NULL,
	[COMPANY_CODE] [varchar](80) NOT NULL,
	[PARENT_ID] [float] NULL,
	[SQL_QUERY] [varchar](4000) NULL,
	[TYPE] [varchar](10) NULL,
 CONSTRAINT [PK_REPORT_MASTER] PRIMARY KEY CLUSTERED 
(
	[INSTALLATION_CODE] ASC,
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[REPORT_PARAMETERS]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[REPORT_PARAMETERS](
	[INSTALLATION_CODE] [varchar](20) NOT NULL,
	[ID] [varchar](60) NOT NULL,
	[PARAM_NAME] [varchar](200) NOT NULL,
	[PARAM_ORDER] [numeric](2, 0) NOT NULL,
	[DATA_TYPE] [char](4) NULL,
	[LENGTH] [numeric](5, 0) NULL,
	[FIXED_LENGTH] [char](4) NULL,
	[DEFAULT_VALUE] [varchar](280) NULL,
	[HINT] [varchar](400) NULL,
	[LABEL] [varchar](200) NULL,
	[QUERY_YN] [char](4) NULL,
	[QUERY] [varchar](2000) NULL,
	[MANDATORY_YN] [char](4) NULL,
	[STATIC_DYNAMIC_FLAG] [varchar](4) NULL,
	[PARAM_DATA_TYPE] [varchar](50) NULL,
	[OPERATOR] [varchar](15) NULL,
	[PARAM_FIELD] [varchar](50) NULL,
 CONSTRAINT [PK_REPORT_PRAMETERS] PRIMARY KEY CLUSTERED 
(
	[INSTALLATION_CODE] ASC,
	[ID] ASC,
	[PARAM_NAME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ROLE_MASTER]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ROLE_MASTER](
	[ROLE_ID] [varchar](30) NOT NULL,
	[ROLE_NAME] [varchar](50) NOT NULL,
	[EFF_DATE] [datetime2](0) NOT NULL,
	[EXP_DATE] [datetime2](0) NULL,
	[CREATED_ON] [datetime2](0) NOT NULL,
	[CREATED_BY] [varchar](30) NOT NULL,
 CONSTRAINT [PK17] PRIMARY KEY CLUSTERED 
(
	[ROLE_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SYSTEM_INFO]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SYSTEM_INFO](
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[BATCH_NO] [numeric](10, 0) NOT NULL,
	[BATCH_REV_NO] [numeric](3, 0) NOT NULL,
	[JAVA_VERSION] [varchar](20) NULL,
	[PRE_VERSION] [varchar](50) NULL,
	[OS_CONFIG] [varchar](100) NULL,
	[OUTPUT_DIR_PATH] [varchar](500) NULL,
	[OUTPUT_DIR_FREE_MEM] [varchar](50) NULL,
	[MAX_MEMORY] [numeric](19, 0) NULL,
	[USED_MEMORY] [numeric](19, 0) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[USER_INSTALLATION_ROLE]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[USER_INSTALLATION_ROLE](
	[USER_ID] [varchar](30) NOT NULL,
	[INSTALLATION_CODE] [varchar](10) NOT NULL,
	[ROLE_ID] [varchar](30) NOT NULL,
 CONSTRAINT [PK_USER_INSTALL_ROLE] PRIMARY KEY CLUSTERED 
(
	[USER_ID] ASC,
	[INSTALLATION_CODE] ASC,
	[ROLE_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[USER_MASTER]    Script Date: 26-09-2013 PM 04:06:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[USER_MASTER](
	[USER_ID] [varchar](30) NOT NULL,
	[USER_NAME] [varchar](50) NOT NULL,
	[TELEPHONE_NO] [varchar](15) NULL,
	[FAX_NO] [varchar](15) NULL,
	[EMAIL_ID] [varchar](50) NOT NULL,
	[EFF_DATE] [datetime2](0) NOT NULL,
	[EXP_DATE] [datetime2](0) NULL,
	[CREATED_ON] [datetime2](0) NOT NULL,
	[CREATED_BY] [varchar](30) NULL,
	[PASSWORD] [varchar](30) NULL,
	[FORCE_PASSWORD_FLAG] [varchar](1) NOT NULL,
	[MODIFIED_BY] [varchar](30) NULL,
	[MODIFIED_ON] [datetime2](0) NULL,
	[HINT_QUESTION] [varchar](200) NULL,
	[HINT_ANSWER] [varchar](50) NULL,
	[ADMIN_ROLE] [varchar](1) NOT NULL,
	[CONNECT_ROLE] [varchar](1) NOT NULL,
	[DEFAULT_VIEW] [varchar](20) NULL,
 CONSTRAINT [PK16] PRIMARY KEY CLUSTERED 
(
	[USER_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[CALENDAR_LOG] ADD  DEFAULT (newid()) FOR [ROWID]
GO
ALTER TABLE [dbo].[INSTRUCTION_LOG] ADD  DEFAULT (newid()) FOR [ROWID]
GO
ALTER TABLE [dbo].[REPORT_PARAMETERS] ADD  DEFAULT ('S') FOR [STATIC_DYNAMIC_FLAG]
GO
ALTER TABLE [dbo].[USER_MASTER] ADD  DEFAULT ('Y') FOR [FORCE_PASSWORD_FLAG]
GO
ALTER TABLE [dbo].[USER_MASTER] ADD  DEFAULT ('Y') FOR [ADMIN_ROLE]
GO
ALTER TABLE [dbo].[USER_MASTER] ADD  DEFAULT ('Y') FOR [CONNECT_ROLE]
GO
ALTER TABLE [dbo].[USER_MASTER] ADD  DEFAULT ('PODS_VIEW') FOR [DEFAULT_VIEW]
GO
ALTER TABLE [dbo].[BATCH]  WITH NOCHECK ADD  CONSTRAINT [REFBPMS_INSTALLATION20] FOREIGN KEY([INSTALLATION_CODE])
REFERENCES [dbo].[INSTALLATION] ([INSTALLATION_CODE])
GO
ALTER TABLE [dbo].[BATCH] CHECK CONSTRAINT [REFBPMS_INSTALLATION20]
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER]  WITH NOCHECK ADD  CONSTRAINT [REFFUNCTION_MASTER14] FOREIGN KEY([FUNCTION_ID])
REFERENCES [dbo].[FUNCTION_MASTER] ([FUNCTION_ID])
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER] CHECK CONSTRAINT [REFFUNCTION_MASTER14]
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER]  WITH NOCHECK ADD  CONSTRAINT [REFROLE_MASTER13] FOREIGN KEY([ROLE_ID])
REFERENCES [dbo].[ROLE_MASTER] ([ROLE_ID])
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER] NOCHECK CONSTRAINT [REFROLE_MASTER13]
GO
ALTER TABLE [dbo].[INSTRUCTION_PARAMETERS]  WITH NOCHECK ADD  CONSTRAINT [INSTRUCTION_PARAMETERS_IN_FK1] FOREIGN KEY([INSTRUCTION_LOG_NO])
REFERENCES [dbo].[INSTRUCTION_LOG] ([SEQ_NO])
GO
ALTER TABLE [dbo].[INSTRUCTION_PARAMETERS] CHECK CONSTRAINT [INSTRUCTION_PARAMETERS_IN_FK1]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [REFBPMS_BATCH19] FOREIGN KEY([INSTALLATION_CODE], [BATCH_NO], [BATCH_REV_NO])
REFERENCES [dbo].[BATCH] ([INSTALLATION_CODE], [BATCH_NO], [BATCH_REV_NO])
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [REFBPMS_BATCH19]
GO
ALTER TABLE [dbo].[PROGRESS_LEVEL]  WITH NOCHECK ADD  CONSTRAINT [REFBPMS_BATCH5] FOREIGN KEY([INSTALLATION_CODE], [BATCH_NO], [BATCH_REV_NO])
REFERENCES [dbo].[BATCH] ([INSTALLATION_CODE], [BATCH_NO], [BATCH_REV_NO])
GO
ALTER TABLE [dbo].[PROGRESS_LEVEL] CHECK CONSTRAINT [REFBPMS_BATCH5]
GO
ALTER TABLE [dbo].[REPORT_PARAMETERS]  WITH CHECK ADD  CONSTRAINT [FK_REPORT_PARAMETERS_REPORT_MASTER] FOREIGN KEY([INSTALLATION_CODE], [ID])
REFERENCES [dbo].[REPORT_MASTER] ([INSTALLATION_CODE], [ID])
GO
ALTER TABLE [dbo].[REPORT_PARAMETERS] CHECK CONSTRAINT [FK_REPORT_PARAMETERS_REPORT_MASTER]
GO
ALTER TABLE [dbo].[SYSTEM_INFO]  WITH NOCHECK ADD  CONSTRAINT [REFBPMS_BATCH9] FOREIGN KEY([INSTALLATION_CODE], [BATCH_NO], [BATCH_REV_NO])
REFERENCES [dbo].[BATCH] ([INSTALLATION_CODE], [BATCH_NO], [BATCH_REV_NO])
GO
ALTER TABLE [dbo].[SYSTEM_INFO] CHECK CONSTRAINT [REFBPMS_BATCH9]
GO
ALTER TABLE [dbo].[USER_INSTALLATION_ROLE]  WITH NOCHECK ADD  CONSTRAINT [FK_USER] FOREIGN KEY([USER_ID])
REFERENCES [dbo].[USER_MASTER] ([USER_ID])
GO
ALTER TABLE [dbo].[USER_INSTALLATION_ROLE] CHECK CONSTRAINT [FK_USER]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [SYS_C0011881] CHECK  (([INSTALLATION_CODE] IS NOT NULL))
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [SYS_C0011881]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [SYS_C0011882] CHECK  (([BATCH_NO] IS NOT NULL))
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [SYS_C0011882]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [SYS_C0011883] CHECK  (([BATCH_REV_NO] IS NOT NULL))
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [SYS_C0011883]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [SYS_C0011885] CHECK  (([TASK_NAME] IS NOT NULL))
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [SYS_C0011885]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [SYS_C0011886] CHECK  (([OBJ_EXEC_START_TIME] IS NOT NULL))
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [SYS_C0011886]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [SYS_C0011887] CHECK  (([STATUS] IS NOT NULL))
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [SYS_C0011887]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.BATCH_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.BATCH_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.EXEC_START_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'EXEC_START_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.EXEC_END_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'EXEC_END_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.BATCH_START_USER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_START_USER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.BATCH_END_USER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_END_USER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.PROCESS_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'PROCESS_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.BATCH_END_REASON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_END_REASON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.FAILED_OVER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'FAILED_OVER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.INSTRUCTION_SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'INSTRUCTION_SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.BATCH.PK2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'CONSTRAINT',@level2name=N'PK2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CALENDAR_LOG.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CALENDAR_LOG.CALENDAR_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'CALENDAR_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CALENDAR_LOG."YEAR"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'YEAR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CALENDAR_LOG.NON_WORKING_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'NON_WORKING_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CALENDAR_LOG.REMARK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'REMARK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CALENDAR_LOG.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CALENDAR_LOG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.ENTITY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'ENTITY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.LOOKUP_COLUMN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'LOOKUP_COLUMN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.LOOKUP_VALUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'LOOKUP_VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.VALUE_COLUMN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'VALUE_COLUMN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.PRECEDENCE_ORDER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'PRECEDENCE_ORDER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.ON_ERROR_FAIL_ALL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'ON_ERROR_FAIL_ALL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP.DESCRIPTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'DESCRIPTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.COLUMN_MAP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CONFIGURATION.CODE1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'CODE1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CONFIGURATION.CODE2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'CODE2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CONFIGURATION.CODE3' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'CODE3'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CONFIGURATION."VALUE"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CONFIGURATION.VALUE_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'VALUE_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CONFIGURATION.DESCRIPTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'DESCRIPTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.CONFIGURATION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.DEAD_MESSAGE_QUEUE.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.DEAD_MESSAGE_QUEUE.I_O_MODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'I_O_MODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.DEAD_MESSAGE_QUEUE.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.DEAD_MESSAGE_QUEUE.MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.DEAD_MESSAGE_QUEUE.PARAM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'PARAM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.DEAD_MESSAGE_QUEUE.ERROR_DESCRIPTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'ERROR_DESCRIPTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.DEAD_MESSAGE_QUEUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.FUNCTION_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.FUNCTION_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.FUNCTION_INFO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_INFO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.PRIOR_FUNCTION_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'PRIOR_FUNCTION_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_MASTER.PK15' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK15'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER.ROLE_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'ROLE_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER.FUNCTION_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.FUNCTION_ROLE_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.GRAPH_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'GRAPH_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.COLLECT_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'COLLECT_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.GRAPH_X_AXIS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'GRAPH_X_AXIS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.GRAPH_Y_AXIS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'GRAPH_Y_AXIS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG.GRAPH_VALUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG', @level2type=N'COLUMN',@level2name=N'GRAPH_VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.GRAPH_DATA_LOG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'GRAPH_DATA_LOG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.INSTALLATION_DESC' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'INSTALLATION_DESC'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.MODIFIED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'MODIFIED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.MODIFIED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'MODIFIED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.TIMEZONE_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'COLUMN',@level2name=N'TIMEZONE_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTALLATION.PK29' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTALLATION', @level2type=N'CONSTRAINT',@level2name=N'PK29'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.MESSAGE_PARAM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'MESSAGE_PARAM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.INSTRUCTING_USER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'INSTRUCTING_USER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.INSTRUCTION_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'INSTRUCTION_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.BATCH_ACTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_ACTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.BATCH_ACTION_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_ACTION_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_LOG.INSTRUCTION_LOG_PK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'CONSTRAINT',@level2name=N'INSTRUCTION_LOG_PK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_PARAMETERS.INSTRUCTION_LOG_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'INSTRUCTION_LOG_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_PARAMETERS.SL_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'SL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_PARAMETERS."NAME"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_PARAMETERS."VALUE"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_PARAMETERS."TYPE"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.INSTRUCTION_PARAMETERS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.BE_SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BE_SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.TASK_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'TASK_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.OBJ_EXEC_START_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'OBJ_EXEC_START_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.OBJ_EXEC_END_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'OBJ_EXEC_END_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.STATUS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SYS_ACT_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'SYS_ACT_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.USER_PRIORITY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'USER_PRIORITY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.PRIORITY_CODE1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRIORITY_CODE1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.PRIORITY_CODE2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRIORITY_CODE2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.PRE_POST' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRE_POST'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.JOB_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'JOB_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.LINE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'LINE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SUBLINE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'SUBLINE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.BROKER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BROKER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.POLICY_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'POLICY_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.POLICY_RENEW_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'POLICY_RENEW_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.VEH_REF_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'VEH_REF_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.CASH_BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'CASH_BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.CASH_BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'CASH_BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.GBI_BILL_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'GBI_BILL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.PRINT_FORM_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRINT_FORM_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.NOTIFY_ERROR_TO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'NOTIFY_ERROR_TO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.DATE_GENERATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'DATE_GENERATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.GENERATE_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'GENERATE_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.REC_MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'REC_MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.JOB_DESC' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'JOB_DESC'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.OBJECT_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'OBJECT_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.DATE_EXECUTED' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'DATE_EXECUTED'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.LIST_IND' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'LIST_IND'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.ENTITY_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ENTITY_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.ENTITY_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ENTITY_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.REF_SYSTEM_ACTIVITY_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'REF_SYSTEM_ACTIVITY_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.ERROR_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ERROR_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.ERROR_DESCRIPTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ERROR_DESCRIPTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.TIME_TAKEN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'TIME_TAKEN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.CYCLE_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'CYCLE_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.USED_MEMORY_BEFORE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'USED_MEMORY_BEFORE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.USED_MEMORY_AFTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'USED_MEMORY_AFTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.LAST_UPDATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'LAST_UPDATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SYS_C0011881' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'CONSTRAINT',@level2name=N'SYS_C0011881'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SYS_C0011882' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'CONSTRAINT',@level2name=N'SYS_C0011882'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SYS_C0011883' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'CONSTRAINT',@level2name=N'SYS_C0011883'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SYS_C0011885' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'CONSTRAINT',@level2name=N'SYS_C0011885'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SYS_C0011886' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'CONSTRAINT',@level2name=N'SYS_C0011886'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.LOG.SYS_C0011887' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'CONSTRAINT',@level2name=N'SYS_C0011887'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.O_QUEUE.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.O_QUEUE.MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE', @level2type=N'COLUMN',@level2name=N'MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.O_QUEUE.PARAM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE', @level2type=N'COLUMN',@level2name=N'PARAM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.O_QUEUE.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.O_QUEUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
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
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.BATCH_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'BATCH_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROCESS_REQUEST_SCHEDULE.PK_PROCESS_REQUEST_SCHEDULE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'CONSTRAINT',@level2name=N'PK_PROCESS_REQUEST_SCHEDULE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.INDICATOR_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'INDICATOR_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.PRG_LEVEL_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'PRG_LEVEL_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.PRG_ACTIVITY_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'PRG_ACTIVITY_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.CYCLE_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'CYCLE_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.STATUS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.START_DATETIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'START_DATETIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.END_DATETIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'END_DATETIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.ERROR_DESC' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'ERROR_DESC'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL.FAILED_OVER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'FAILED_OVER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.PROGRESS_LEVEL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER."NAME"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.PROG_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'PROG_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.SR_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'SR_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.COMPANY_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'COMPANY_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.PARENT_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'PARENT_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.SQL_QUERY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'SQL_QUERY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER."TYPE"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'COLUMN',@level2name=N'TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_MASTER.PK_REPORT_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK_REPORT_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.PARAM_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'PARAM_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.PARAM_ORDER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'PARAM_ORDER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.DATA_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'DATA_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.LENGTH' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'LENGTH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.FIXED_LENGTH' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'FIXED_LENGTH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.DEFAULT_VALUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'DEFAULT_VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.HINT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'HINT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.LABEL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'LABEL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.QUERY_YN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'QUERY_YN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.QUERY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'QUERY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.MANDATORY_YN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'MANDATORY_YN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.STATIC_DYNAMIC_FLAG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'STATIC_DYNAMIC_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.PARAM_DATA_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'PARAM_DATA_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.OPERATOR' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'OPERATOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.PARAM_FIELD' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'COLUMN',@level2name=N'PARAM_FIELD'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.REPORT_PARAMETERS.PK_REPORT_PRAMETERS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'REPORT_PARAMETERS', @level2type=N'CONSTRAINT',@level2name=N'PK_REPORT_PRAMETERS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER.ROLE_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'ROLE_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER.ROLE_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'ROLE_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.ROLE_MASTER.PK17' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK17'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.JAVA_VERSION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'JAVA_VERSION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.PRE_VERSION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'PRE_VERSION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.OS_CONFIG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'OS_CONFIG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.OUTPUT_DIR_PATH' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'OUTPUT_DIR_PATH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.OUTPUT_DIR_FREE_MEM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'OUTPUT_DIR_FREE_MEM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.MAX_MEMORY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'MAX_MEMORY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO.USED_MEMORY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'USED_MEMORY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.SYSTEM_INFO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_INSTALLATION_ROLE.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_INSTALLATION_ROLE', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_INSTALLATION_ROLE.INSTALLATION_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_INSTALLATION_ROLE', @level2type=N'COLUMN',@level2name=N'INSTALLATION_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_INSTALLATION_ROLE.ROLE_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_INSTALLATION_ROLE', @level2type=N'COLUMN',@level2name=N'ROLE_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_INSTALLATION_ROLE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_INSTALLATION_ROLE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_INSTALLATION_ROLE.PK_USER_INSTALL_ROLE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_INSTALLATION_ROLE', @level2type=N'CONSTRAINT',@level2name=N'PK_USER_INSTALL_ROLE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.USER_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'USER_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.TELEPHONE_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'TELEPHONE_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.FAX_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'FAX_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.EMAIL_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'EMAIL_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.PASSWORD' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'PASSWORD'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.FORCE_PASSWORD_FLAG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'FORCE_PASSWORD_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.MODIFIED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'MODIFIED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.MODIFIED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'MODIFIED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.HINT_QUESTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'HINT_QUESTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.HINT_ANSWER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'HINT_ANSWER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.ADMIN_ROLE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'ADMIN_ROLE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.CONNECT_ROLE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'CONNECT_ROLE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.DEFAULT_VIEW' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'DEFAULT_VIEW'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_MONITOR.USER_MASTER.PK16' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK16'
GO

--------------------------------------------------
--   DATA FOR TABLE INSTALLATION
--   FILTER = none used
---------------------------------------------------
--REM INSERTING into INSTALLATION
Insert into INSTALLATION (INSTALLATION_CODE,INSTALLATION_DESC,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,MODIFIED_ON,MODIFIED_BY,BATCH_NO,BATCH_REV_NO,TIMEZONE_ID) values ('STG-PRODS','STG-PRODS','01-JAN-00',null,'01-JAN-00','JOHN',null,null,null,null,'America/Los_Angeles');

---------------------------------------------------
--   END DATA FOR TABLE INSTALLATION
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE FUNCTION_MASTER
--   FILTER = none used
---------------------------------------------------
--REM INSERTING into FUNCTION_MASTER
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Schedule','Schedule','Schedule Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('View Schedules','View Schedules','View Schedules Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM','Schedule');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Run','Run','Run Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Define Calendar','Define Calendar','Define Calendar Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM','Run');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('User Master','User Master','User Master Menu','17-JUN-2010',null,'17-JUN-2010','JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Manage User','Manage User','Create User Menu','17-JUN-2010',null,'17-JUN-2010','JBEAM','User Master');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Change Password','Change Password','Change Password Menu','17-JUN-2010',null,'17-JUN-2010','JBEAM','Profile');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Run Batch','Run Batch','Run Batch Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM','Run');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Reports','Reports','Reports Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Generate Reports','Generate Reports','Generate Reports Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM','Reports');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Batch','Batch','Batch Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Current','Current','Current Batch Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM','Batch');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Search','Search','Search Completed Batch Menu','07-JUN-2010',null,'07-JUN-2010','JBEAM','Batch');
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Profile','Profile','Profile Menu','17-JUN-2010',null,'17-JUN-2010','JBEAM',null);
Insert into FUNCTION_MASTER (FUNCTION_ID,FUNCTION_NAME,FUNCTION_INFO,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PRIOR_FUNCTION_ID) values ('Edit Profile','Edit Profile','Edit Profile Menu','17-JUN-2010',null,'17-JUN-2010','JBEAM','Profile');
---------------------------------------------------
--   END DATA FOR TABLE FUNCTION_MASTER
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE ROLE_MASTER
--   FILTER = none used
---------------------------------------------------
--REM INSERTING into ROLE_MASTER
Insert into ROLE_MASTER (ROLE_ID,ROLE_NAME,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY) values ('OPERATOR','OPERATOR','29-DEC-09','29-DEC-99','29-DEC-09','ADMIN');
Insert into ROLE_MASTER (ROLE_ID,ROLE_NAME,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY) values ('USER','USER','29-DEC-09','29-DEC-99','29-DEC-09','ADMIN');

---------------------------------------------------
--   END DATA FOR TABLE ROLE_MASTER
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE FUNCTION_ROLE_MASTER
--   FILTER = none used
---------------------------------------------------
----REM INSERTING into FUNCTION_ROLE_MASTER
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('OPERATOR','Schedule','08-JUN-2010',null,'08-JUN-2010','ADMIN','jbeam');
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('ADMIN','User Master','08-JUN-2010',null,'08-JUN-2010','JBEAM','jbeam');
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('OPERATOR','Run','08-JUN-2010',null,'08-JUN-2010','JBEAM','jbeam');
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('USER','Profile','08-JUN-2010',null,'08-JUN-2010','ADMIN','jbeam');
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('USER','Batch','08-JUN-2010',null,'08-JUN-2010','ADMIN','jbeam');
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('USER','Reports','08-JUN-2010',null,'08-JUN-2010','ADMIN','jbeam');
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('ADMIN','Profile','08-JUN-2010',null,'08-JUN-2010','JBEAM','jbeam');
Insert into FUNCTION_ROLE_MASTER (ROLE_ID,FUNCTION_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,USER_ID) values ('CONNECT','Profile','08-JUN-2010',null,'08-JUN-2010','JBEAM','jbeam');


---------------------------------------------------
--   END DATA FOR TABLE FUNCTION_ROLE_MASTER
---------------------------------------------------
---------------------------------------------------
--   DATA FOR TABLE COLUMN_MAP
--   FILTER = none used
---------------------------------------------------
----REM INSERTING into COLUMN_MAP
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('STG-PRODS','PRE','PRE_POST','PRE','PRIORITY_CODE_1',1,null,'Enter the priority code 1 to execute all PRE events associated with it.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('STG-PRODS','POLICY','POLICY_NO',null,'POLICY_NO#POLICY_RENEW_NO',2,'Y','Enter the policy number ''#'' renew number. Two parameters required to be separated with ''#''. Use ''/'' as an escape character.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('STG-PRODS','ACCOUNT','ENTITY_TYPE','ACCOUNT','ENTITY_CODE',4,'Y','Enter the ACCOUNT_SYSTEM_CODE.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('STG-PRODS','BROKER','ENTITY_TYPE','BROKER','ENTITY_CODE',5,'Y','Enter the BROKER_SYSTEM_CODE.');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('STG-PRODS','GENERAL','JOB_SEQ',null,'JOB_SEQ',6,null,'Enter the job sequence number');
Insert into COLUMN_MAP (INSTALLATION_CODE,ENTITY,LOOKUP_COLUMN,LOOKUP_VALUE,VALUE_COLUMN,PRECEDENCE_ORDER,ON_ERROR_FAIL_ALL,DESCRIPTION) values ('STG-PRODS','POST','PRE_POST','POST','PRIORITY_CODE_1',999,null,'Enter the priority code 1 to execute the POST jobs associated with it');

---------------------------------------------------
--   END DATA FOR TABLE COLUMN_MAP
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE CONFIGURATION
--   FILTER = none used
---------------------------------------------------
------REM INSERTING into CONFIGURATION
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','PASSWORD','RNDFORMAT','Aa9##99aa#9#','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('STG-PRODS','COLLATOR','WAIT_PERIOD','10000','I','The collator wait period for installation STG-PRODS');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('STG-PRODS','PERSCANCOLLATOR','WAIT_PERIOD','1','I','This value is defined for per scan execution count collator. The time is in minutes, default is 1.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('INSTALLATION_WS','STG-PRODS','SERVICES','172.16.246.108:11011','S','The <IP>:<PORT> of the published communication services for the installation');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','MAIL','mailnotification','ON','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','MAIL','senderaddress','jbeam.stgprod@mastek.com','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR_WS','OUTBOUND_Q_POLLER','WAIT_PERIOD','15000','I','The monitor poller waiting period');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','MAIL','defaultsubjectpassword','Your password has been reset','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','MAIL','SMTPServerPort','37529','I',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR_WS','MONITOR_WS','SERVICES','172.16.246.108:11012','S','The <IP>:<PORT> to publish the monitor communication services');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR_WS','MONITOR_UI_WS','SERVICES','172.16.246.108:11155','S','The <IP>:<PORT> to publish the monitor services for the UI');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','MAIL','SMTPServer','172.16.210.46','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','MAIL','defaultsubjectuser','New user registration','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','PURGE','RETAIN_DAYS','517','I','No fo days to retain.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','PURGE','BACKUP_DIR','D:\JBEAM-Test\jbeam-5.1.1\purge\','S','The directory where the backup script files stored.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','WEEKDAY','1','Monday','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','WEEKDAY','0','Sunday','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','WEEKDAY','3','Wednesday','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','WEEKDAY','4','Thursday','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','WEEKDAY','5','Friday','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','WEEKDAY','6','Saturday','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','WEEKDAY','2','Tuesday','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','SKIP_SCHEDULE_CODE','D-','Prepone Day by 1 (D-)','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','SKIP_SCHEDULE_CODE','NA','Not Applicable','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','SKIP_SCHEDULE_CODE','D+','Postpone Day by 1 (D+)','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','SKIP_SCHEDULE_CODE','SS','Skip Schedule','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','REPORT_FORMAT','RTF','Rich Text Format','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','REPORT_FORMAT','XLS','Excel','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','REPORT_FORMAT','PDF','Adobe PDF','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','REPORT_FORMAT','DOC','WORD','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','REPORT_FORMAT','PPT','Power Point','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','FIRST_MTH','First [day] of the Month','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','FOURTH_MTH','Fourth [day] of the Month','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','HOUR','Hour','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','LAST_MTH','Last [day] of the Month','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','LDMONTH','Last Date of the Month','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','MINUTE','Minute','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','MONTH','Month','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','PREDEFINED','Pre Programmed Frequency','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','SECOND_MTH','Second [day] of the Month','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','THIRD_MTH','Third [day] of the Month','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','LAST_YR','Last [day] of the Year',null,null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','WEEK','Week','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','THIRD_YR','Third [day] of the Year',null,null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','FOURTH_YR','Fourth [day] of the Year',null,null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','YEAR','Year','S',null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','FIRST_YR','First [day] of the Year',null,null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','SECOND_YR','Second [day] of the Year',null,null);
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR','FREQUENCY','DAY','Day','S',null);

---------------------------------------------------
--   END DATA FOR TABLE CONFIGURATION
---------------------------------------------------
Insert into USER_MASTER (USER_ID,USER_NAME,TELEPHONE_NO,FAX_NO,EMAIL_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PASSWORD,FORCE_PASSWORD_FLAG,MODIFIED_BY,MODIFIED_ON,HINT_QUESTION,HINT_ANSWER,ADMIN_ROLE,CONNECT_ROLE) values ('jbeam','JBEAM ADMIN','null','342423432','Jbeam.admin@mastek.com','01-JAN-2001','30-DEC-2050','01-JAN-2010','USER','cojt0Pw//L6ToM8G41aOKFIWh7w=','Y','jbeam','28-JUL-2010','Admin','0DPiKuNIrrVmD8IUCuw1hQxNqZc=','Y','Y');

---------------------------------------------------
--   END DATA FOR TABLE USER_MASTER
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE USER_INSTALLATION_ROLE
--   FILTER = none used
---------------------------------------------------
----REM INSERTING into USER_INSTALLATION_ROLE
Insert into USER_INSTALLATION_ROLE (INSTALLATION_CODE,ROLE_ID,USER_ID) values ('null','ADMIN','jbeam');
Insert into USER_INSTALLATION_ROLE (INSTALLATION_CODE,ROLE_ID,USER_ID) values ('null','CONNECT','jbeam');
Insert into USER_INSTALLATION_ROLE (INSTALLATION_CODE,ROLE_ID,USER_ID) values ('STG-PRODS','USER','jbeam');
Insert into USER_INSTALLATION_ROLE (INSTALLATION_CODE,ROLE_ID,USER_ID) values ('STG-PRODS','OPERATOR','jbeam');
---------------------------------------------------
--   END DATA FOR TABLE USER_INSTALLATION_ROLE
---------------------------------------------------
---------------------------------------------------
--   DATA FOR TABLE REPORT_MASTER
--   FILTER = none used
---------------------------------------------------
----REM INSERTING into REPORT_MASTER
Insert into REPORT_MASTER (INSTALLATION_CODE,ID,NAME,PROG_NAME,SR_NO,COMPANY_CODE,PARENT_ID,SQL_QUERY,TYPE) values ('STG-PRODS','1','Purge Routine Core','com.stgmastek.core.purge.PurgeBatchDetails','1','ALL',null,null,null);

---------------------------------------------------
--   END DATA FOR TABLE REPORT_MASTER
---------------------------------------------------

