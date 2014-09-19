USE [master]
GO
/****** Object:  Database [BPMS_CORE]    Script Date: 26-09-2013 PM 04:31:54 ******/
CREATE DATABASE [BPMS_CORE]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BPMS_CORE', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER1\MSSQL\DATA\BPMS_CORE.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'BPMS_CORE_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER1\MSSQL\DATA\BPMS_CORE_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [BPMS_CORE] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BPMS_CORE].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BPMS_CORE] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BPMS_CORE] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BPMS_CORE] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BPMS_CORE] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BPMS_CORE] SET ARITHABORT OFF 
GO
ALTER DATABASE [BPMS_CORE] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BPMS_CORE] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [BPMS_CORE] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BPMS_CORE] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BPMS_CORE] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BPMS_CORE] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BPMS_CORE] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BPMS_CORE] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BPMS_CORE] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BPMS_CORE] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BPMS_CORE] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BPMS_CORE] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BPMS_CORE] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BPMS_CORE] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BPMS_CORE] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BPMS_CORE] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BPMS_CORE] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BPMS_CORE] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BPMS_CORE] SET RECOVERY FULL 
GO
ALTER DATABASE [BPMS_CORE] SET  MULTI_USER 
GO
ALTER DATABASE [BPMS_CORE] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BPMS_CORE] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BPMS_CORE] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BPMS_CORE] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [BPMS_CORE]
GO
/****** Object:  Table [dbo].[BATCH]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BATCH](
	[BATCH_NO] [numeric](10, 0) NOT NULL,
	[BATCH_REV_NO] [numeric](3, 0) NOT NULL,
	[BATCH_NAME] [varchar](25) NOT NULL,
	[BATCH_TYPE] [varchar](10) NOT NULL,
	[EXEC_START_TIME] [datetime2](3) NOT NULL,
	[EXEC_END_TIME] [datetime2](3) NULL,
	[BATCH_START_USER] [varchar](30) NOT NULL,
	[BATCH_END_USER] [varchar](30) NULL,
	[PROCESS_ID] [numeric](12, 0) NULL,
	[BATCH_END_REASON] [varchar](25) NULL,
	[FAILED_OVER] [varchar](1) NOT NULL,
	[INSTRUCTION_SEQ_NO] [numeric](19, 0) NULL,
	[ROWID] [uniqueidentifier] NOT NULL,
 CONSTRAINT [PK2] PRIMARY KEY CLUSTERED 
(
	[BATCH_NO] ASC,
	[BATCH_REV_NO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[BATCH_LOCK]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BATCH_LOCK](
	[REQ_ID] [float] NOT NULL,
	[INDICATOR] [varchar](1) NOT NULL,
	[LOCK_TIME] [datetime2](3) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CALENDAR_LOG]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CALENDAR_LOG](
	[CALENDAR_NAME] [varchar](30) NOT NULL,
	[YEAR] [varchar](4) NOT NULL,
	[NON_WORKING_DATE] [datetime2](0) NOT NULL,
	[REMARK] [varchar](50) NULL,
	[USER_ID] [varchar](20) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[COLUMN_MAP]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[COLUMN_MAP](
	[ENTITY] [varchar](20) NOT NULL,
	[LOOKUP_COLUMN] [varchar](30) NULL,
	[LOOKUP_VALUE] [varchar](30) NULL,
	[VALUE_COLUMN] [varchar](30) NULL,
	[PRECEDENCE_ORDER] [numeric](3, 0) NOT NULL,
	[ON_ERROR_FAIL_ALL] [varchar](1) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CONFIGURATION]    Script Date: 26-09-2013 PM 04:31:55 ******/
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
	[VALUE] [varchar](4000) NOT NULL,
	[VALUE_TYPE] [varchar](2) NULL,
	[DESCRIPTION] [varchar](1000) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DEAD_MESSAGE_QUEUE]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DEAD_MESSAGE_QUEUE](
	[ID] [numeric](10, 0) NOT NULL,
	[I_O_MODE] [varchar](1) NOT NULL,
	[MESSAGE] [varchar](10) NOT NULL,
	[PARAM] [varchar](100) NULL,
	[ERROR_DESCRIPTION] [varchar](4000) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dual]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dual](
	[id] [numeric](18, 0) NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[FUNCTION_MASTER]    Script Date: 26-09-2013 PM 04:31:55 ******/
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
 CONSTRAINT [PK15] PRIMARY KEY CLUSTERED 
(
	[FUNCTION_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FUNCTION_ROLE_MASTER]    Script Date: 26-09-2013 PM 04:31:55 ******/
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
 CONSTRAINT [PK18] PRIMARY KEY CLUSTERED 
(
	[ROLE_ID] ASC,
	[FUNCTION_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[I_QUEUE]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[I_QUEUE](
	[ID] [numeric](10, 0) NOT NULL,
	[MESSAGE] [varchar](10) NOT NULL,
	[PARAM] [varchar](100) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[INSTRUCTION_LOG]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[INSTRUCTION_LOG](
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
/****** Object:  Table [dbo].[INSTRUCTION_PARAMETERS]    Script Date: 26-09-2013 PM 04:31:55 ******/
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
/****** Object:  Table [dbo].[LOG]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LOG](
	[SEQ_NO] [float] NOT NULL,
	[BATCH_NO] [numeric](10, 0) NOT NULL,
	[BATCH_REV_NO] [numeric](3, 0) NOT NULL,
	[BE_SEQ_NO] [varchar](12) NOT NULL,
	[TASK_NAME] [varchar](2000) NULL,
	[OBJ_EXEC_START_TIME] [datetime2](3) NOT NULL,
	[OBJ_EXEC_END_TIME] [datetime2](3) NULL,
	[STATUS] [varchar](2) NOT NULL,
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
	[CASH_BATCH_NO] [varchar](20) NULL,
	[CASH_BATCH_REV_NO] [varchar](2) NULL,
	[GBI_BILL_NO] [varchar](24) NULL,
	[PRINT_FORM_NO] [varchar](8) NULL,
	[NOTIFY_ERROR_TO] [varchar](10) NULL,
	[DATE_GENERATE] [datetime2](0) NULL,
	[GENERATE_BY] [varchar](30) NULL,
	[REC_MESSAGE] [varchar](80) NULL,
	[JOB_DESC] [varchar](80) NULL,
	[OBJECT_NAME] [varchar](80) NULL,
	[DATE_EXECUTED] [datetime2](0) NULL,
	[LIST_IND] [numeric](3, 0) NULL,
	[ENTITY_TYPE] [varchar](15) NULL,
	[ENTITY_CODE] [varchar](20) NULL,
	[REF_SYSTEM_ACTIVITY_NO] [varchar](25) NULL,
	[ERROR_TYPE] [varchar](25) NULL,
	[ERROR_DESCRIPTION] [varchar](4000) NULL,
	[CYCLE_NO] [numeric](10, 0) NULL,
	[USED_MEMORY_BEFORE] [numeric](19, 0) NULL,
	[USED_MEMORY_AFTER] [numeric](19, 0) NULL,
	[ROWID] [uniqueidentifier] NOT NULL,
 CONSTRAINT [BPMS_BATCH_LOG_PK] PRIMARY KEY CLUSTERED 
(
	[SEQ_NO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[META_DATA]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[META_DATA](
	[SEQ_NO] [varchar](12) NOT NULL,
	[TASK_NAME] [varchar](2000) NOT NULL,
	[EFF_DATE] [datetime2](0) NOT NULL,
	[EXP_DATE] [datetime2](0) NULL,
	[ON_FAIL_EXIT] [char](1) NULL,
	[PRIORITY_CODE1] [numeric](10, 0) NOT NULL,
	[PRIORITY_CODE2] [numeric](10, 0) NOT NULL,
	[PRE_POST] [varchar](5) NULL,
	[JOB_TYPE] [varchar](2) NULL,
	[LINE] [varchar](10) NULL,
	[SUBLINE] [varchar](10) NULL,
	[DATE_GENERATE] [datetime2](0) NOT NULL,
	[GENERATE_BY] [varchar](30) NOT NULL,
	[JOB_DESC] [varchar](80) NOT NULL,
	[OBJECT_NAME] [varchar](30) NOT NULL,
 CONSTRAINT [PK3] PRIMARY KEY CLUSTERED 
(
	[SEQ_NO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[O_QUEUE]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[O_QUEUE](
	[ID] [numeric](10, 0) NOT NULL,
	[MESSAGE] [varchar](10) NOT NULL,
	[PARAM] [varchar](100) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[OBJECT_MAP]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OBJECT_MAP](
	[ID] [varchar](50) NOT NULL,
	[OBJECT_NAME] [varchar](4000) NOT NULL,
	[OBJECT_TYPE] [varchar](2) NULL,
	[EFF_DATE] [datetime2](0) NULL,
	[EXP_DATE] [datetime2](0) NULL,
	[DEFAULT_VALUES] [varchar](4000) NULL,
	[ON_FAIL_EXIT] [varchar](1) NULL,
	[ON_FAIL_EMAIL] [varchar](2) NULL,
	[MIN_TIME] [numeric](10, 0) NULL,
	[AVG_TIME] [numeric](10, 0) NULL,
	[MAX_TIME] [numeric](10, 0) NULL,
	[MIN_TIME_ESCL] [varchar](1) NULL,
	[ESCALATION_LEVEL] [varchar](20) NULL,
	[CASE_DATA] [varchar](4000) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PROCESS_REQ_PARAMS]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROCESS_REQ_PARAMS](
	[REQ_ID] [numeric](12, 0) NOT NULL,
	[PARAM_NO] [numeric](12, 0) NOT NULL,
	[PARAM_FLD] [varchar](100) NOT NULL,
	[PARAM_VAL] [varchar](1000) NULL,
	[PARAM_DATA_TYPE] [varchar](2) NOT NULL,
	[STATIC_DYNAMIC_FLAG] [varchar](1) NULL,
 CONSTRAINT [PK_PROCESS_REQ_PARAMS] PRIMARY KEY CLUSTERED 
(
	[REQ_ID] ASC,
	[PARAM_NO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PROCESS_REQUEST]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROCESS_REQUEST](
	[REQ_ID] [numeric](12, 0) NOT NULL,
	[USER_ID] [varchar](15) NOT NULL,
	[REQ_DT] [datetime2](0) NOT NULL,
	[REQ_STAT] [varchar](2) NOT NULL,
	[PROCESS_CLASS_NM] [varchar](500) NOT NULL,
	[GRP_ST_IND] [varchar](2) NOT NULL,
	[REQ_START_DT] [datetime2](0) NULL,
	[REQ_END_DT] [datetime2](0) NULL,
	[GRP_ID] [numeric](12, 0) NULL,
	[GRP_REQ_SEQ_NO] [numeric](12, 0) NULL,
	[REQ_LOGFILE_NM] [varchar](500) NULL,
	[JOB_ID] [varchar](100) NULL,
	[SCHEDULED_TIME] [datetime2](0) NULL,
	[SCH_ID] [numeric](12, 0) NULL,
	[STUCK_THREAD_LIMIT] [numeric](10, 0) NULL,
	[STUCK_THREAD_MAX_LIMIT] [numeric](10, 0) NULL,
	[REQ_TYPE] [varchar](20) NOT NULL,
	[PRIORITY] [numeric](3, 0) NOT NULL,
	[EMAIL_IDS] [varchar](2000) NULL,
	[VERBOSE_TIME_ELAPSED] [varchar](200) NULL,
	[CAL_SCHEDULED_TIME] [datetime2](0) NULL,
	[JOB_NAME] [varchar](100) NULL,
	[TEXT1] [varchar](50) NULL,
	[TEXT2] [varchar](50) NULL,
	[NUM1] [numeric](12, 2) NULL,
	[NUM2] [numeric](12, 2) NULL,
	[RETRY_TIMES] [numeric](10, 0) NULL,
	[RETRY_TIME_UNIT] [varchar](10) NULL,
	[RETRY_TIME] [numeric](38, 0) NULL,
	[RETRY_CNT] [numeric](38, 0) NULL,
 CONSTRAINT [PK_PROCESS_REQUEST] PRIMARY KEY CLUSTERED 
(
	[REQ_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PROCESS_REQUEST_SCHEDULE]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROCESS_REQUEST_SCHEDULE](
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
 CONSTRAINT [PK_PROCESS_REQUEST_SCHEDULE] PRIMARY KEY CLUSTERED 
(
	[SCH_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PROGRESS_LEVEL]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROGRESS_LEVEL](
	[BATCH_NO] [numeric](10, 0) NULL,
	[BATCH_REV_NO] [numeric](3, 0) NULL,
	[INDICATOR_NO] [numeric](4, 0) NOT NULL,
	[PRG_LEVEL_TYPE] [varchar](10) NULL,
	[PRG_ACTIVITY_TYPE] [varchar](20) NOT NULL,
	[CYCLE_NO] [numeric](3, 0) NULL,
	[STATUS] [varchar](2) NOT NULL,
	[START_DATETIME] [datetime2](3) NOT NULL,
	[END_DATETIME] [datetime2](6) NULL,
	[ERROR_DESC] [varchar](100) NULL,
	[FAILED_OVER] [varchar](1) NULL,
	[ROWID] [uniqueidentifier] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ROLE_MASTER]    Script Date: 26-09-2013 PM 04:31:55 ******/
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
/****** Object:  Table [dbo].[SCHEDULE_EVENT_CALENDAR]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SCHEDULE_EVENT_CALENDAR](
	[SCH_ID] [float] NOT NULL,
	[SERIAL_NO] [float] NOT NULL,
	[CATEGORY] [varchar](10) NOT NULL,
	[PROCESS_CLASS_NM] [varchar](500) NULL,
 CONSTRAINT [PK4_1] PRIMARY KEY CLUSTERED 
(
	[SCH_ID] ASC,
	[SERIAL_NO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SYSTEM_INFO]    Script Date: 26-09-2013 PM 04:31:55 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SYSTEM_INFO](
	[BATCH_NO] [numeric](10, 0) NOT NULL,
	[BATCH_REV_NO] [numeric](3, 0) NOT NULL,
	[JAVA_VERSION] [varchar](20) NULL,
	[PRE_VERSION] [varchar](50) NULL,
	[OS_CONFIG] [varchar](100) NULL,
	[OUTPUT_DIR_PATH] [varchar](500) NULL,
	[OUTPUT_DIR_FREE_MEM] [varchar](50) NULL,
	[MAX_MEMORY] [numeric](19, 0) NULL,
	[USED_MEMORY] [numeric](19, 0) NULL,
	[ROWID] [uniqueidentifier] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[USER_MASTER]    Script Date: 26-09-2013 PM 04:31:55 ******/
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
	[ASSIGNED_ROLE] [varchar](30) NOT NULL,
 CONSTRAINT [PK16] PRIMARY KEY CLUSTERED 
(
	[USER_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[BATCH] ADD  DEFAULT ('N') FOR [FAILED_OVER]
GO
ALTER TABLE [dbo].[BATCH] ADD  DEFAULT (newid()) FOR [ROWID]
GO
ALTER TABLE [dbo].[INSTRUCTION_LOG] ADD  DEFAULT (newid()) FOR [ROWID]
GO
ALTER TABLE [dbo].[LOG] ADD  DEFAULT (newid()) FOR [ROWID]
GO
ALTER TABLE [dbo].[PROCESS_REQ_PARAMS] ADD  DEFAULT ('S') FOR [STATIC_DYNAMIC_FLAG]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT (sysdatetime()) FOR [SCHEDULED_TIME]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ((2)) FOR [STUCK_THREAD_LIMIT]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ((10)) FOR [STUCK_THREAD_MAX_LIMIT]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ('GENERAL') FOR [REQ_TYPE]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ((999)) FOR [PRIORITY]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ((0)) FOR [RETRY_TIMES]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ('MINUTES') FOR [RETRY_TIME_UNIT]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ((0)) FOR [RETRY_TIME]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST] ADD  DEFAULT ((0)) FOR [RETRY_CNT]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST_SCHEDULE] ADD  DEFAULT ('Y') FOR [FUTURE_SCHEDULING_ONLY]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST_SCHEDULE] ADD  DEFAULT ('N') FOR [FIXED_DATE]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST_SCHEDULE] ADD  DEFAULT (NULL) FOR [SKIP_FLAG]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST_SCHEDULE] ADD  DEFAULT (NULL) FOR [WEEKDAY_CHECK_FLAG]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST_SCHEDULE] ADD  DEFAULT (NULL) FOR [END_REASON]
GO
ALTER TABLE [dbo].[PROCESS_REQUEST_SCHEDULE] ADD  DEFAULT ('N') FOR [KEEP_ALIVE]
GO
ALTER TABLE [dbo].[PROGRESS_LEVEL] ADD  DEFAULT (newid()) FOR [ROWID]
GO
ALTER TABLE [dbo].[SYSTEM_INFO] ADD  DEFAULT (newid()) FOR [ROWID]
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER]  WITH NOCHECK ADD  CONSTRAINT [REFFUNCTION_MASTER14] FOREIGN KEY([FUNCTION_ID])
REFERENCES [dbo].[FUNCTION_MASTER] ([FUNCTION_ID])
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER] CHECK CONSTRAINT [REFFUNCTION_MASTER14]
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER]  WITH NOCHECK ADD  CONSTRAINT [REFROLE_MASTER13] FOREIGN KEY([ROLE_ID])
REFERENCES [dbo].[ROLE_MASTER] ([ROLE_ID])
GO
ALTER TABLE [dbo].[FUNCTION_ROLE_MASTER] CHECK CONSTRAINT [REFROLE_MASTER13]
GO
ALTER TABLE [dbo].[INSTRUCTION_PARAMETERS]  WITH NOCHECK ADD  CONSTRAINT [INSTRUCTION_PARAMETERS_IN_FK1] FOREIGN KEY([INSTRUCTION_LOG_NO])
REFERENCES [dbo].[INSTRUCTION_LOG] ([SEQ_NO])
GO
ALTER TABLE [dbo].[INSTRUCTION_PARAMETERS] CHECK CONSTRAINT [INSTRUCTION_PARAMETERS_IN_FK1]
GO
ALTER TABLE [dbo].[LOG]  WITH NOCHECK ADD  CONSTRAINT [REFBPMS_BATCH19] FOREIGN KEY([BATCH_NO], [BATCH_REV_NO])
REFERENCES [dbo].[BATCH] ([BATCH_NO], [BATCH_REV_NO])
GO
ALTER TABLE [dbo].[LOG] CHECK CONSTRAINT [REFBPMS_BATCH19]
GO
ALTER TABLE [dbo].[PROGRESS_LEVEL]  WITH NOCHECK ADD  CONSTRAINT [REFBPMS_BATCH5] FOREIGN KEY([BATCH_NO], [BATCH_REV_NO])
REFERENCES [dbo].[BATCH] ([BATCH_NO], [BATCH_REV_NO])
GO
ALTER TABLE [dbo].[PROGRESS_LEVEL] CHECK CONSTRAINT [REFBPMS_BATCH5]
GO
ALTER TABLE [dbo].[SYSTEM_INFO]  WITH NOCHECK ADD  CONSTRAINT [REFBPMS_BATCH9] FOREIGN KEY([BATCH_NO], [BATCH_REV_NO])
REFERENCES [dbo].[BATCH] ([BATCH_NO], [BATCH_REV_NO])
GO
ALTER TABLE [dbo].[SYSTEM_INFO] CHECK CONSTRAINT [REFBPMS_BATCH9]
GO
ALTER TABLE [dbo].[USER_MASTER]  WITH NOCHECK ADD  CONSTRAINT [REFROLE_MASTER12] FOREIGN KEY([ASSIGNED_ROLE])
REFERENCES [dbo].[ROLE_MASTER] ([ROLE_ID])
GO
ALTER TABLE [dbo].[USER_MASTER] CHECK CONSTRAINT [REFROLE_MASTER12]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.BATCH_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.BATCH_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.EXEC_START_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'EXEC_START_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.EXEC_END_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'EXEC_END_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.BATCH_START_USER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_START_USER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.BATCH_END_USER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_END_USER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.PROCESS_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'PROCESS_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.BATCH_END_REASON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'BATCH_END_REASON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.FAILED_OVER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'FAILED_OVER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.INSTRUCTION_SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'COLUMN',@level2name=N'INSTRUCTION_SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH.PK2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH', @level2type=N'CONSTRAINT',@level2name=N'PK2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH_LOCK.REQ_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH_LOCK', @level2type=N'COLUMN',@level2name=N'REQ_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH_LOCK.INDICATOR' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH_LOCK', @level2type=N'COLUMN',@level2name=N'INDICATOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH_LOCK.LOCK_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH_LOCK', @level2type=N'COLUMN',@level2name=N'LOCK_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.BATCH_LOCK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BATCH_LOCK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CALENDAR_LOG.CALENDAR_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'CALENDAR_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CALENDAR_LOG."YEAR"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'YEAR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CALENDAR_LOG.NON_WORKING_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'NON_WORKING_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CALENDAR_LOG.REMARK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'REMARK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CALENDAR_LOG.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CALENDAR_LOG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CALENDAR_LOG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.COLUMN_MAP.ENTITY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'ENTITY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.COLUMN_MAP.LOOKUP_COLUMN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'LOOKUP_COLUMN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.COLUMN_MAP.LOOKUP_VALUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'LOOKUP_VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.COLUMN_MAP.VALUE_COLUMN' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'VALUE_COLUMN'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.COLUMN_MAP.PRECEDENCE_ORDER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'PRECEDENCE_ORDER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.COLUMN_MAP.ON_ERROR_FAIL_ALL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP', @level2type=N'COLUMN',@level2name=N'ON_ERROR_FAIL_ALL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.COLUMN_MAP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'COLUMN_MAP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CONFIGURATION.CODE1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'CODE1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CONFIGURATION.CODE2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'CODE2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CONFIGURATION.CODE3' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'CODE3'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CONFIGURATION."VALUE"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CONFIGURATION.VALUE_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'VALUE_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CONFIGURATION.DESCRIPTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION', @level2type=N'COLUMN',@level2name=N'DESCRIPTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.CONFIGURATION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CONFIGURATION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.DEAD_MESSAGE_QUEUE.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.DEAD_MESSAGE_QUEUE.I_O_MODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'I_O_MODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.DEAD_MESSAGE_QUEUE.MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.DEAD_MESSAGE_QUEUE.PARAM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'PARAM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.DEAD_MESSAGE_QUEUE.ERROR_DESCRIPTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE', @level2type=N'COLUMN',@level2name=N'ERROR_DESCRIPTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.DEAD_MESSAGE_QUEUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DEAD_MESSAGE_QUEUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.FUNCTION_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.FUNCTION_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.FUNCTION_INFO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_INFO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_MASTER.PK15' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK15'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER.ROLE_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'ROLE_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER.FUNCTION_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'FUNCTION_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.FUNCTION_ROLE_MASTER.PK18' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'FUNCTION_ROLE_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK18'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.I_QUEUE.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'I_QUEUE', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.I_QUEUE.MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'I_QUEUE', @level2type=N'COLUMN',@level2name=N'MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.I_QUEUE.PARAM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'I_QUEUE', @level2type=N'COLUMN',@level2name=N'PARAM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.I_QUEUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'I_QUEUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.MESSAGE_PARAM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'MESSAGE_PARAM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.INSTRUCTING_USER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'INSTRUCTING_USER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.INSTRUCTION_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'INSTRUCTION_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.BATCH_ACTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_ACTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.BATCH_ACTION_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'COLUMN',@level2name=N'BATCH_ACTION_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_LOG.INSTRUCTION_LOG_PK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_LOG', @level2type=N'CONSTRAINT',@level2name=N'INSTRUCTION_LOG_PK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_PARAMETERS.INSTRUCTION_LOG_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'INSTRUCTION_LOG_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_PARAMETERS.SL_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'SL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_PARAMETERS."NAME"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_PARAMETERS."VALUE"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'VALUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_PARAMETERS."TYPE"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS', @level2type=N'COLUMN',@level2name=N'TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.INSTRUCTION_PARAMETERS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'INSTRUCTION_PARAMETERS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.BE_SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BE_SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.TASK_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'TASK_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.OBJ_EXEC_START_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'OBJ_EXEC_START_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.OBJ_EXEC_END_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'OBJ_EXEC_END_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.STATUS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.SYS_ACT_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'SYS_ACT_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.USER_PRIORITY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'USER_PRIORITY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.PRIORITY_CODE1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRIORITY_CODE1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.PRIORITY_CODE2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRIORITY_CODE2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.PRE_POST' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRE_POST'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.JOB_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'JOB_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.LINE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'LINE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.SUBLINE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'SUBLINE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.BROKER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'BROKER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.POLICY_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'POLICY_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.POLICY_RENEW_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'POLICY_RENEW_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.VEH_REF_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'VEH_REF_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.CASH_BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'CASH_BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.CASH_BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'CASH_BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.GBI_BILL_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'GBI_BILL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.PRINT_FORM_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'PRINT_FORM_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.NOTIFY_ERROR_TO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'NOTIFY_ERROR_TO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.DATE_GENERATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'DATE_GENERATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.GENERATE_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'GENERATE_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.REC_MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'REC_MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.JOB_DESC' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'JOB_DESC'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.OBJECT_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'OBJECT_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.DATE_EXECUTED' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'DATE_EXECUTED'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.LIST_IND' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'LIST_IND'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.ENTITY_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ENTITY_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.ENTITY_CODE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ENTITY_CODE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.REF_SYSTEM_ACTIVITY_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'REF_SYSTEM_ACTIVITY_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.ERROR_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ERROR_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.ERROR_DESCRIPTION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'ERROR_DESCRIPTION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.CYCLE_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'CYCLE_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.USED_MEMORY_BEFORE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'USED_MEMORY_BEFORE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.USED_MEMORY_AFTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'COLUMN',@level2name=N'USED_MEMORY_AFTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.LOG.BPMS_BATCH_LOG_PK' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG', @level2type=N'CONSTRAINT',@level2name=N'BPMS_BATCH_LOG_PK'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.TASK_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'TASK_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.ON_FAIL_EXIT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'ON_FAIL_EXIT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.PRIORITY_CODE1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'PRIORITY_CODE1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.PRIORITY_CODE2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'PRIORITY_CODE2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.PRE_POST' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'PRE_POST'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.JOB_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'JOB_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.LINE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'LINE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.SUBLINE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'SUBLINE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.DATE_GENERATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'DATE_GENERATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.GENERATE_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'GENERATE_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.JOB_DESC' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'JOB_DESC'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.OBJECT_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'COLUMN',@level2name=N'OBJECT_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.META_DATA.PK3' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'META_DATA', @level2type=N'CONSTRAINT',@level2name=N'PK3'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.O_QUEUE.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.O_QUEUE.MESSAGE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE', @level2type=N'COLUMN',@level2name=N'MESSAGE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.O_QUEUE.PARAM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE', @level2type=N'COLUMN',@level2name=N'PARAM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.O_QUEUE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'O_QUEUE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.OBJECT_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'OBJECT_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.OBJECT_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'OBJECT_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.DEFAULT_VALUES' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'DEFAULT_VALUES'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.ON_FAIL_EXIT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'ON_FAIL_EXIT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.ON_FAIL_EMAIL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'ON_FAIL_EMAIL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.MIN_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'MIN_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.AVG_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'AVG_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.MAX_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'MAX_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.MIN_TIME_ESCL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'MIN_TIME_ESCL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.ESCALATION_LEVEL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'ESCALATION_LEVEL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP.CASE_DATA' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP', @level2type=N'COLUMN',@level2name=N'CASE_DATA'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.OBJECT_MAP' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'OBJECT_MAP'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS.REQ_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS', @level2type=N'COLUMN',@level2name=N'REQ_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS.PARAM_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS', @level2type=N'COLUMN',@level2name=N'PARAM_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS.PARAM_FLD' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS', @level2type=N'COLUMN',@level2name=N'PARAM_FLD'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS.PARAM_VAL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS', @level2type=N'COLUMN',@level2name=N'PARAM_VAL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS.PARAM_DATA_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS', @level2type=N'COLUMN',@level2name=N'PARAM_DATA_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS.STATIC_DYNAMIC_FLAG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS', @level2type=N'COLUMN',@level2name=N'STATIC_DYNAMIC_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQ_PARAMS.PK_PROCESS_REQ_PARAMS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQ_PARAMS', @level2type=N'CONSTRAINT',@level2name=N'PK_PROCESS_REQ_PARAMS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.REQ_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'REQ_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.REQ_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'REQ_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.REQ_STAT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'REQ_STAT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.PROCESS_CLASS_NM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'PROCESS_CLASS_NM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.GRP_ST_IND' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'GRP_ST_IND'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.REQ_START_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'REQ_START_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.REQ_END_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'REQ_END_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.GRP_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'GRP_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.GRP_REQ_SEQ_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'GRP_REQ_SEQ_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.REQ_LOGFILE_NM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'REQ_LOGFILE_NM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.JOB_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'JOB_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.SCHEDULED_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'SCHEDULED_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.SCH_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'SCH_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.STUCK_THREAD_LIMIT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'STUCK_THREAD_LIMIT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.STUCK_THREAD_MAX_LIMIT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'STUCK_THREAD_MAX_LIMIT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.REQ_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'REQ_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.PRIORITY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'PRIORITY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.EMAIL_IDS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'EMAIL_IDS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.VERBOSE_TIME_ELAPSED' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'VERBOSE_TIME_ELAPSED'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.CAL_SCHEDULED_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'CAL_SCHEDULED_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.JOB_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'JOB_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.TEXT1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'TEXT1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.TEXT2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'TEXT2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.NUM1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'NUM1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.NUM2' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'NUM2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.RETRY_TIMES' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'RETRY_TIMES'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.RETRY_TIME_UNIT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'RETRY_TIME_UNIT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.RETRY_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'RETRY_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.RETRY_CNT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'COLUMN',@level2name=N'RETRY_CNT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST.PK_PROCESS_REQUEST' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST', @level2type=N'CONSTRAINT',@level2name=N'PK_PROCESS_REQUEST'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.SCH_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'SCH_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.FREQ_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'FREQ_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.RECUR' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'RECUR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.START_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'START_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.SCH_STAT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'SCH_STAT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.ON_WEEK_DAY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'ON_WEEK_DAY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.END_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.END_OCCUR' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_OCCUR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.ENTRY_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'ENTRY_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.MODIFY_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'MODIFY_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.MODIFY_DT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'MODIFY_DT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.REQ_STAT' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'REQ_STAT'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.OCCUR_COUNTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'OCCUR_COUNTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.PROCESS_CLASS_NM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'PROCESS_CLASS_NM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.START_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'START_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.END_TIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_TIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.FUTURE_SCHEDULING_ONLY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'FUTURE_SCHEDULING_ONLY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.FIXED_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'FIXED_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.EMAIL_IDS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'EMAIL_IDS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.SKIP_FLAG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'SKIP_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.WEEKDAY_CHECK_FLAG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'WEEKDAY_CHECK_FLAG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.END_REASON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'END_REASON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.KEEP_ALIVE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'COLUMN',@level2name=N'KEEP_ALIVE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROCESS_REQUEST_SCHEDULE.PK_PROCESS_REQUEST_SCHEDULE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROCESS_REQUEST_SCHEDULE', @level2type=N'CONSTRAINT',@level2name=N'PK_PROCESS_REQUEST_SCHEDULE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.INDICATOR_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'INDICATOR_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.PRG_LEVEL_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'PRG_LEVEL_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.PRG_ACTIVITY_TYPE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'PRG_ACTIVITY_TYPE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.CYCLE_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'CYCLE_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.STATUS' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'STATUS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.START_DATETIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'START_DATETIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.END_DATETIME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'END_DATETIME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.ERROR_DESC' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'ERROR_DESC'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL.FAILED_OVER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL', @level2type=N'COLUMN',@level2name=N'FAILED_OVER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.PROGRESS_LEVEL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PROGRESS_LEVEL'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER.ROLE_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'ROLE_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER.ROLE_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'ROLE_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.ROLE_MASTER.PK17' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ROLE_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK17'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SCHEDULE_EVENT_CALENDAR.SCH_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SCHEDULE_EVENT_CALENDAR', @level2type=N'COLUMN',@level2name=N'SCH_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SCHEDULE_EVENT_CALENDAR.SERIAL_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SCHEDULE_EVENT_CALENDAR', @level2type=N'COLUMN',@level2name=N'SERIAL_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SCHEDULE_EVENT_CALENDAR.CATEGORY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SCHEDULE_EVENT_CALENDAR', @level2type=N'COLUMN',@level2name=N'CATEGORY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SCHEDULE_EVENT_CALENDAR.PROCESS_CLASS_NM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SCHEDULE_EVENT_CALENDAR', @level2type=N'COLUMN',@level2name=N'PROCESS_CLASS_NM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SCHEDULE_EVENT_CALENDAR' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SCHEDULE_EVENT_CALENDAR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SCHEDULE_EVENT_CALENDAR.PK4_1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SCHEDULE_EVENT_CALENDAR', @level2type=N'CONSTRAINT',@level2name=N'PK4_1'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.BATCH_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'BATCH_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.BATCH_REV_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'BATCH_REV_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.JAVA_VERSION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'JAVA_VERSION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.PRE_VERSION' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'PRE_VERSION'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.OS_CONFIG' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'OS_CONFIG'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.OUTPUT_DIR_PATH' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'OUTPUT_DIR_PATH'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.OUTPUT_DIR_FREE_MEM' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'OUTPUT_DIR_FREE_MEM'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.MAX_MEMORY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'MAX_MEMORY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO.USED_MEMORY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO', @level2type=N'COLUMN',@level2name=N'USED_MEMORY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.SYSTEM_INFO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SYSTEM_INFO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.USER_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'USER_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.USER_NAME' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'USER_NAME'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.TELEPHONE_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'TELEPHONE_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.FAX_NO' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'FAX_NO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.EMAIL_ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'EMAIL_ID'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.EFF_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'EFF_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.EXP_DATE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'EXP_DATE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.CREATED_ON' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_ON'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.CREATED_BY' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'CREATED_BY'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.PASSWORD' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'PASSWORD'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.ASSIGNED_ROLE' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'COLUMN',@level2name=N'ASSIGNED_ROLE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'BPMS_CORE.USER_MASTER.PK16' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'USER_MASTER', @level2type=N'CONSTRAINT',@level2name=N'PK16'
GO
USE [master]
GO
ALTER DATABASE [BPMS_CORE] SET  READ_WRITE 
GO



---------------------------------------------------
--   DATA FOR TABLE ROLE_MASTER
--   FILTER = none used
---------------------------------------------------
--REM INSERTING into ROLE_MASTER
Insert into ROLE_MASTER (ROLE_ID,ROLE_NAME,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY) values ('USER','USER','01-JAN-09',null,'01-JAN-09','BPMS');
Insert into ROLE_MASTER (ROLE_ID,ROLE_NAME,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY) values ('ADMIN','ADMIN','01-JAN-09',null,'01-JAN-09','BPMS');

---------------------------------------------------
--   END DATA FOR TABLE ROLE_MASTER
---------------------------------------------------
---------------------------------------------------
--   DATA FOR TABLE USER_MASTER
--   FILTER = none used
---------------------------------------------------
--REM INSERTING into USER_MASTER
Insert into USER_MASTER (USER_ID,USER_NAME,TELEPHONE_NO,FAX_NO,EMAIL_ID,EFF_DATE,EXP_DATE,CREATED_ON,CREATED_BY,PASSWORD,ASSIGNED_ROLE) values ('ADMIN','ADMIN','5445464','4654646','jbeam@mastek.com','01-JAN-09',null,'12-AUG-09','MANDAR',null,'ADMIN');

---------------------------------------------------
--   END DATA FOR TABLE USER_MASTER
---------------------------------------------------

---------------------------------------------------
--   DATA FOR TABLE CONFIGURATION
--   FILTER = none used
---------------------------------------------------
--REM INSERTING into CONFIGURATION
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','REPORT_RUNTIME','LOG_DIR','D:\JBEAM-Test\jbeam-5.1.1\birt-runtime-2_5_2\logs\','S','Directory where the logs will reside.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','JOB_MONITOR_POLLER','WAIT_PERIOD','10','I','The wait period for Batch Job Monitor. Time in Minutes');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('COMM','POLLER','WAIT_PERIOD','5000','I','The wait period for pollers in the core communication system');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','BATCH_LISTENER','MAX_LISTENERS','5','I','The max number of listeners to be spawned for batch objects. Please ensure that the connections should also be increased with an increase in the number of listeners');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','CALENDAR_CLASS','JV','com.stgmastek.core.calendar.BatchCalendar','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','CRITICAL','EMAIL','Mandar.Vaidya@mastek.com','S','Escalation Level Developer. The value should be set in the object map to determine the recipient address.');
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
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EMAIL','NOTIFICATION_GROUP','Mandar.Vaidya@mastek.com','S','The email group to which the email alerts to be sent');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EMAIL','WHEN_OBJECT_FAILS','com.stgmastek.core.util.email.BatchObjectFailureEmailContentGenerator','S','The email content handler implementation. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','EV','com.stgmastek.core.logic.EventParserObjectExecutionHandler','S','Default execution handler implementation for EVent parser batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','JV','com.stgmastek.core.logic.JAVAExecutionHandler','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','PL','com.stgmastek.core.logic.PLSQLExecutionHandler','S','Default execution handler implementation for PLSQL batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','EXECUTION_HANDLER','FE','com.stgmastek.jbeam.billing.impl.FlowExecutionHandler','S','Default execution handler implementation for launch flow');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','FUTURE_DATE_RUN','MAX_NO_OF_DAYS','50000','I','The max number of future days for which the batch could be run');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','GLOBAL_PARAMETER','REQUIRED','Y','S','Indication whether to set the global parameters. Usually would be ''Y''');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','HIGH','EMAIL','Mandar.Vaidya@mastek.com','S','Escalation Level SENIOR. The value should be set in the object map to determine the recipient address.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','LOW','EMAIL','Mandar.Vaidya@mastek.com','S','Escalation Level BU_LEAD. The value should be set in the object map to determine the recipient address.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','MEDIUM','EMAIL','Mandar.Vaidya@mastek.com','S','Escalation Level TECH_MANAGER. The value should be set in the object map to determine the recipient address.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','MODE','DEV_OR_PRE','PRE','S','Primarily used for development ease. Real-time would always be PRE. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','POLLER','WAIT_PERIOD','5000','I','The wait period for pollers in the batch core system');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE','WAIT_PERIOD','5000','I','The wait period to check the status requested to the PRE engine to execute');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_POST_LISTENER','MAX_LISTENERS','5','I','The max number of listeners to be spawned for PRE / POST objects. Please ensure that the connections should also be increased with an increase in the number of listeners');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_REQUEST_TYPE','VALUE','GENERAL','I','The max number of future days for which the batch could be run');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_STUCK_THREAD','LIMIT','120','I','The limit for stuck thread in min');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PRE_STUCK_THREAD','MAX_LIMIT','180','I','The max limit for stuck thread in min');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PROCESS_CLASS','JV','com.stgmastek.core.logic.Processor','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','SAVEPOINT','DIRECTORY','D:\JBEAM-Test\jbeam-5.1.1\SAVEPOINT\','S','The savepoint file directory');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','SCHEDULE_CLASS','JV','stg.pr.engine.scheduler.CRequestScheduler','S','Default execution handler implementation for JAVA batch objects');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','TEXT_LOGGING','ENABLED','N','S','Indication whether text logging is to be enabled. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','TEXT_LOGGING','FILE_PATH','D:\JBEAM-Test\jbeam-5.1.1\batch-logs\','S','The text log file path. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','USER','DEFAULT','ADMIN','S','Default user');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('INSTALLATION_WS','STG-PRODS','SERVICES','172.16.246.108:11011','S','Optional. Used for development purpose. ');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('MONITOR_WS','MONITOR_WS','SERVICES','172.16.246.108:11012','S','The <IP>:<PORT> of the monitor communication system. Needed to the core system to communicate');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','REPORT_RUNTIME','HOME_DIR','D:\JBEAM-Test\jbeam-5.1.1\birt-runtime-2_5_2\ReportEngine\','S','The Runtime Report Engine Directory');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','REPORT_RUNTIME','OUTPUT_FOLDER','D:\JBEAM-Test\jbeam-5.1.1\PRE30\reports\','S','The Report Output directory.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PURGE','RETAIN_DAYS','&retain_Days','I','No fo days to retain.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','PURGE','BACKUP_DIR','D:\JBEAM-Test\jbeam-5.1.1\purge\','S','The directory where the backup script files stored.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','INSTALLATION','CODE','STG-PRODS','S','The current or self installation code.');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','INSTALLATION','NAME','STG PRODUCTS DEV','S','The current or self installation name');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','ICD_END_POINT_URL','URL','http://172.16.211.187:8080/ICDService/services/ProcessManager/handleRequest','S','Default execution handler implementation for launch flow');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','ICD_SERVICE','USER_ID','csr','S','User id for ICDService');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','ICD_SERVICE','PASSWORD','icd123','S','Password for ICDService');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','AUTH_HANDLER','JV','com.stgmastek.core.authentication.defaultimpl.AuthenticationHandlerImpl','S','Authentication Handler Class');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','AUTH_FILTER_HANDLER','JV','com.stgmastek.core.authentication.defaultimpl.AuthenticationRequestFilterImpl','S','Authentication Filter Class');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','THREAD_POOL','CORE_SIZE','5','S','The number of threads to keep in the pool, even if they are idle');
Insert into CONFIGURATION (CODE1,CODE2,CODE3,VALUE,VALUE_TYPE,DESCRIPTION) values ('CORE','THREAD_POOL','MAX_CORE_SIZE','5','S','The maximum number of threads to allow in the pool.');

---------------------------------------------------
--   END DATA FOR TABLE CONFIGURATION
---------------------------------------------------
---------------------------------------------------
--   DATA FOR TABLE COLUMN_MAP
--   FILTER = none used
---------------------------------------------------
--REM INSERTING into COLUMN_MAP
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
--REM INSERTING into BATCH_LOCK
Insert into BATCH_LOCK (REQ_ID,INDICATOR,LOCK_TIME) values (1,'O','05-AUG-10');

---------------------------------------------------
--   END DATA FOR TABLE BATCH_LOCK
---------------------------------------------------
 
