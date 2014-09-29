/**
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.majescomastek.jbeam.common
{
	public class SWFConstants
	{
		//IMP: Update the constants in ProgramNameConstants if you update any constant here.
					
		public static const INSTALLATION_LIST_MODULE:String = 
				CommonConstants.BASE_VIEW_PKG_PATH + 'installationlist/InstallationListModule.swf';
		
		public static const INSTALLATION_MODULE:String = 
				CommonConstants.BASE_VIEW_PKG_PATH + 'installationlist/InstallationModule.swf';
				
		public static const BATCH_DETAILS_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'batchdetails/BatchDetailsModule.swf';
				
		public static const SCHEDULE_BATCH_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'schedulebatch/ScheduleBatchModule.swf';
		
		public static const VIEW_SCHEDULE_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'viewschedule/ViewScheduleModule.swf';

		public static const SEARCH_BATCH_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'searchbatch/SearchBatchModule.swf';
		
		public static const CALENDAR_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'calendar/CalendarModule.swf';

		public static const MANAGE_USER_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'usermaster/ManageUserModule.swf';
				
		public static const USER_PROFILE_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'usermaster/UserProfileModule.swf';
				
		public static const CHANGE_PASSWORD_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'usermaster/ChangePasswordModule.swf';
				
		public static const FORGOT_PASSWORD_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'forgotpassword/ForgotPasswordModule.swf';
				
		public static const REPORTS_MODULE:String =
				CommonConstants.BASE_VIEW_PKG_PATH + 'reports/ReportsModule.swf';
		
		public static const MAP:Object = {									
			'INSTALLATION_LIST_MODULE'          : INSTALLATION_LIST_MODULE,
			'INSTALLATION_MODULE'          		: INSTALLATION_MODULE,
			'CALENDAR_MODULE'					: CALENDAR_MODULE,
			'BATCH_DETAILS_MODULE'				: BATCH_DETAILS_MODULE,
			'SCHEDULE_BATCH_MODULE'				: SCHEDULE_BATCH_MODULE,
			'SEARCH_BATCH_MODULE'				: SEARCH_BATCH_MODULE,
			'MANAGE_USER_MODULE'				: MANAGE_USER_MODULE,
			'USER_PROFILE_MODULE'				: USER_PROFILE_MODULE,
			'CHANGE_PASSWORD_MODULE'			: CHANGE_PASSWORD_MODULE,
			'FORGOT_PASSWORD_MODULE'			: FORGOT_PASSWORD_MODULE,
			'REPORTS_MODULE'					: REPORTS_MODULE,
			'VIEW_SCHEDULE_MODULE'				: VIEW_SCHEDULE_MODULE
		};
	}
}