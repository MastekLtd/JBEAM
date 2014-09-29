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
 * @author sanjayts
 * 
 *
 * $Revision:: 92                                                                                                      $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/common/BusinessConstants.as $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/common/BusinessConstants.as $
 * 
 * 92    3/31/10 4:38p Gourav.rai
 * Added by Gourav Rai
 * 
 * 91    3/19/10 2:31p Gourav.rai
 * SUSPEND_NOC_ACTION,RELEASE_NOC_ACTION
 * 
 * 90    3/15/10 2:44p Ritesh.u
 * 
 * 89    3/11/10 4:16p Ritesh.u
 * 
 * 88    3/09/10 2:20p Ritesh.u
 * 
 * 87    1/29/10 5:22p Purva.mukewar
 * Flex PMD code review done
 * 
 * 86    1/20/10 9:51a Sanjay.sharma
 * added new transaction type constant TRANSACTION_TYPE_NON_MONEY_ENDORSEMENT
 * 
 * 85    1/10/10 1:35p Ritesh.u
 * added communication_ type_diary
 * 
 * 84    1/10/10 1:16p Ritesh.u
 * added constant Context_account_type,Context_policy_type and Diary_priority_normal
 * 
 * 83    1/08/10 10:42a Sanjay.sharma
 * Added new constant DIARY_STATUS_DELETE
 * 
 * Removed DIARY_STATUS_ACTIVE
 * 
 * 82    1/08/10 10:34a Sanjay.sharma
 * added DIARY_STATUS_ACTIVE
 * 
 * 81    1/07/10 10:17p Narayana
 * Submition success failure messages display changes.
 * 
 * 80    1/07/10 3:40p Sanjay.sharma
 * added constants for diary and notepad report names
 * 
 * 79    1/06/10 5:16p Sanjay.sharma
 * added COMMUNICATION_ACTION_MASTER_CODE
 * 
 * 78    1/05/10 9:33a Sanjay.sharma
 * added constants for user id and user name
 * 
 * 77    1/04/10 7:22p Narayana
 * Amount to allocate changes added.
 * 
 * 76    1/04/10 5:34p Sanjay.sharma
 * added constants for context
 * 
 * 75    1/02/10 4:02p Partha.chowdhury
 * added constants for suspend check.
 * 
 * 74    12/31/09 5:24p Sanjay.sharma
 * added new drop down entries
 * 
 * 73    12/31/09 1:40p Shrinivas
 * Constants added for drop down options
 * 
 * 72    12/31/09 12:07p Gourav.rai
 * 
 * 71    12/30/09 7:01p Shrinivas
 * 
 * 70    12/30/09 3:26p Sandeepa
 * 
 * 69    12/28/09 10:32a Shrinivas
 * Constants added for bill to indicator
 * 
 * 68    12/23/09 11:08a Gourav.rai
 * REFUND_ENTITY
 * 
 * 67    12/23/09 9:41a Sanjay.sharma
 * added header
 * 
 * 
 */
package com.majescomastek.jbeam.common
{
	/**
	 * This final class would hold the list of all business constants.
	 */
	public final class BusinessConstants
	{
		/** The default cash batch type used when closing a cash batch */
		public static const CLOSE_CASH_BATCH_TYPE:String = 'C';
		
		/** The code used for Check Payment Method */
		public static const PAYMENT_METHOD_CHECK_CODE:String = 'Q';

		/** The master code used for retrieving the bank list */
		public static const BANK_LIST_MASTER_CODE:String = 'BANK';
		
		/** The master code used for retrieving the cash type list */
		public static const CASH_TYPE_LIST_MASTER_CODE:String = 'CASH_TYPE';
		
		/** The master code used for retrieving the payment from list */
		public static const PAYMENT_FROM_LIST_MASTER_CODE:String = 'PAYMENT_FROM';
		
		/** The master code used for retrieving the commission adjustment reasons*/ 
		public static const COMMISSION_ADJUSTMENT_REASONS_MASTER_CODE:String = 'COMM_ADJ_REASONS';
		
		/** Master code used for retrieving the GLExpences list */
		public static const GL_EXPENSE_ACCOUNT_LIST_MASTER_CODE:String = 'GL_EXPENSE_ACCOUNTS';
		
		/** The YES flag for manual allocation used on the ChecksEntry screen */
		public static const MANUAL_ALLOCATION_FLAG:String = 'Y';
		
		/** The NO flag for manual allocation used on the ChecksEntry screen */
		public static const MANUAL_ALLOCATION_NO_FLAG:String = 'N';
		
		/** The code used to indicate that the Account# was entered by the user on AddChecks screen */
		public static const APPLY_TO_ACCOUNT:String = 'C';
		
		/** The code used to indicate that the Policy# was entered by the user on AddChecks screen */
		public static const APPLY_TO_POLICY:String = 'I';
		
		/** The code used to indicate that the Policy# was entered by the user on Notepad screen */
		public static const NOTEPAD_APPLY_TO_POLICY:String = 'P';
		
		/** The code used to indicate that the Agency# was entered by the user on AddChecks screen */
		public static const APPLY_TO_AGENCY:String = 'P';
		
		/** The code used to indicate that the Agency# was entered by the user on AddChecks screen */
		public static const NOTEPAD_APPLY_TO_AGENCY:String = 'A';
		
		/** The program code used when invoking the PopulatePayerDetails/PopulatePolicyDetails from the CashBatch screen */
		public static const PROGRAM_TYPE_CHECK_LIST:String = 'CHECKLIST';
		
		/** The code used to represent the cash type dummy suspense */
		public static const CASH_TYPE_DUMMY_SUSPENSE:String = '04';
		
		/** Master code used for retrieving the STATES list */
		public static const STATES_LIST_MASTER_CODE:String = 'STATES';
		
		/** Master code used for retrieving the STATES list */
		public static const POLICY_STATUS_MASTER_CODE:String = 'POLICY_STATUS';
		
		/** The minimum amount acceptable in the Cash Batch functionality */
		public static const MINIMUM_AMOUNT_CASH_BATCH:String = '0.01';
		
		/** The maximum amount acceptable in the Cash Batch functionality */
		public static const MAXIMUM_AMOUNT_CASH_BATCH:String = '999999999';
		
		/** The maximum length of the number of checks field on the Cash Batch screen */
		public static const NUMBER_OF_CHECKS_FIELD_LENGTH:Number = 3;
		
		/** The maximum length of the number of checks field on the Add Row Batch screen */
		public static const CHECK_COUNT_FIELD_LENGTH:Number = 3;
		
		/** The maximum length of the company batch field on the Cash Batch screen */
		public static const COMPANY_BATCH_FIELD_LENGTH:Number = 14;
		
		/** The maximum length of the check amount field on the Cash Batch screen */
		public static const CHECK_AMOUNT_FIELD_LENGTH:Number = 9;
		
		/** The maximum length of the check number field on the Cash Batch screen */
		public static const CHECK_NUMBER_FIELD_LENGTH:Number = 20;
		
		/** The maximum length of the remarks field on the Cash Batch screen */
		public static const REMARKS_FIELD_LENGTH:Number = 30;
		
		/** The maximum length of the reference ID field on the Cash Batch screen */
		public static const REFERENCE_ID_FIELD_LENGTH:Number = 20;
		
		/** The maximum length of the apply to code field on the Cash Batch screen */
		public static const APPLY_TO_FIELD_LENGTH:Number = 24;
		
		/** The code used to indicates that the default no. of GLAccount allocations */
		public static const GLACCOUNTS_ALLOCATIONS_LENGTH:int = 4;
		
		/** The code used to indicates that the default no. of agent allocations */
		public static const AGENT_ALLOCATIONS_LENGTH:int = 4;
		
		public static const NSF_SUPRESS_CHARGE_YES_FLAGE :String ='Y';
		
		public static const NSF_SUPRESS_CHARGE_NO_FLAGE :String ='N';
		
		public static const NSF_SUPRESS_LETTER_YES_FLAGE:String ='Y';
		
		public static const NSF_SUPRESS_LETTER_NO_FLAGE:String ='N';
		
		// Common search related constants
		/** The code used to indicate the request for a common entity search */
		public static const SEARCHID_COMMON_ENTITY_SEARCH:String = 'SEARCHID_COMMON_ENTITY_SEARCH';
		
		/** The code used to indicate the request for a common policy search */
		public static const SEARCHID_COMMON_POLICY_SEARCH:String = 'SEARCHID_COMMON_POLICY_SEARCH';
		
		/** The code used to indicate the request for a common policy search */
		public static const SEARCHID_COMMON_SAERCH:String = 'SEARCHID_COMMON_SAERCH';
		
		/** The string used to indicate that search should be done for account no. */
		public static const SEARCH_OPTION_ACCOUNT_NO:String = 'ACCOUNT_NO';
		
		/** The string used to indicate that search should be done for account no. */
		public static const SEARCH_OPTION_ENTITY_ACCOUNT_NO:String = 'ENTITY_ACCOUNT_NO';
		
		/** The string used to indicate that search should be done for agent no. */
		public static const SEARCH_OPTION_ENTITY_AGENT_NO:String = 'ENTITY_AGENT_NO';
		
		/** The string used to indicate that search should be done for account name. */
		public static const SEARCH_OPTION_ENTITY_ACCOUNT_NAME:String = 'ENTITY_ACCOUNT_NAME';
		
		/** The string used to indicate that search should be done for agent name. */
		public static const SEARCH_OPTION_ENTITY_AGENT_NAME:String = 'ENTITY_AGENT_NAME';
		
		/** The string used to indicate that search should be done for account name */
		public static const SEARCH_OPTION_ACCOUNT_NAME:String = 'ACCOUNT_NAME';
		
		/** The string used to indicate that search should be done for policy number */
		public static const SEARCH_OPTION_POLICY_NO:String = 'POLICY_NO';
		
		/** The string used to indicate that quick search for insured name */
		public static const SEARCH_OPTION_QUICK_INSURED:String = 'INSURED_QUICK';
		
		/** The string used to indicate that pattern search for insured name */
		public static const SEARCH_OPTION_PATTERN_INSURED:String = 'INSURED_PATTERN';
		
		/** The screen identifier to be used when invoking the group entity search from cash batch functionality */
		public static const SEARCH_SCREEN_IDENTIFIER_CASH_BATCH:String = 'CASH_BATCH'; 
		
		/** The screen identifier to be used when invoking the group entity search from cash allocation functionality */
		public static const SEARCH_SCREEN_IDENTIFIER_CASH_ALLOCATION:String = 'CASH_ALLOCATION';
		 
		/** The screen identifier to be used when invoking the group entity search from cash allocation functionality */
		public static const SEARCH_SCREEN_IDENTIFIER_MANUAL_DEMAND_NOTICE:String = 'MANUAL_DEMAND_NOTICE';
		
		/** The screen identifier to be used when invoking the group entity search from notepad functionality */
		public static const SEARCH_SCREEN_IDENTIFIER_NOTEPAD:String = 'NOTEPAD';
		
		/** The screen identifier to be used when invoking the group entity search from manual entry functionality */
		public static const SEARCH_SCREEN_IDENTIFIER_MANUAL_ENTRY:String = 'MANUAL_ENTRY';
		
		/** The max field length of the entity name field in the Group Entity Search screen */
		public static const ENTITY_NAME_MAX_FIELD_LENGTH:Number = 20;
		
		/** The max field length of the insured name field in the Group Entity Search screen */
		public static const INSURED_NAME_MAX_FIELD_LENGTH:Number = 20;
		
		/** The max field length of the entity code field in the Group Entity Search screen */
		public static const ENTITY_CODE_MAX_FIELD_LENGTH:Number = 20;
		
		/** The max field length of the entity zip field in the Group Entity Search screen */
		public static const ENTITY_ZIP_MAX_FIELD_LENGTH:Number = 15;
		
		/** The constant indicates allocation option NSF */
		public static const NON_SUFFICIENT_FUND_SUBCODE:String = "05";
		
		/**The constant indicates allocation option DSF */
		public static const STOP_PAYMENT_SUBCODE:String = "06";
		
		/** The constant indicates allocation option NSF */
		public static const NON_SUFFICIENT_FUND_VALUE:String = "NSF";
		
		/**The constant indicates allocation option DSF */
		public static const STOP_PAYMENT_VALUE:String = "DSF";
		
		/** The constant indicates entity type as account */
		public static const ENTITY_TYPE_ACCOUNT:String = "ACCOUNT";
		
		/**  The constant indicates entity type as agency */
		public static const ENTITY_TYPE_AGENCY:String = "AGENCY";
		
		/**  The constant indicates entity type as policy */
		public static const ENTITY_TYPE_POLICY:String = "POLICY";
		
		/**  The constant indicates entity type as broker */
		public static const ENTITY_TYPE_BROKER:String = "BROKER";
		
		public static const ENTITY_TYPE_BOTH:String = "BOTH";
		
		/**  The constant indicates entity type as broker */
		public static const ENTITY_TYPE_PRODUCER:String = "PRODUCER";
		
		/**  The constant indicates entity type as broker */
		public static const ENTITY_TYPE_ALT_AGENCY:String = "ALT_AGENCY";
		
		/**  The constant indicates entity type as broker */
		public static const ENTITY_TYPE_INSURED:String = "INSURED";
		
		/**  The constant indicates cash type premium*/
		public static const CASH_TYPE_PREMIUM:String = "01";
		
		/**  The constant indicates Account level String*/
		public static const ACCOUNT_LEVEL:String = "A";
		
		/**  The constant indicates Policy level String*/
		public static const POLICY_LEVEL:String = "P";
		
		/**  The constant indicates Account Policy level String*/
		public static const ACCOUNT_POLICY_LEVEL:String = "AP";
		
		/**  The constant indicates Suspend Policy level String*/
		public static const SUSPEND_LEVEL:String = "04";
		
		/**  The constant indicates transaction level String*/
		public static const TRANSACTION_LEVEL:String = "T";
		
		/**  The constant indicates Unassigned level String*/
		public static const UNASSIGNED_LEVEL:String = "UNASSIGNED";
		
		/** The constant indicates the success flag */
		public static const SUCCESS_FLAG:String = "00";
		
		/** The expand flag for other bill cash allocation  */
		public static const OTHER_BILL_ALLOCATION_EXPAND_FLAG:String = 'Y';
		
		/** The collapse flag for other bill cash allocation  */
		public static const OTHER_BILL_ALLOCATION_COLLAPSE_FLAG:String = 'N';
		
		/**The constant indicates agency cash allocation screen*/
		public static const AGENCY_CHECKSEARCH_SUBCODE:String = "01";		
		
		/** The constant indicates gl account screen*/
		public static const GL_ACCOUNT_SUBCODE:String = "02";
		
		/** The constant indicates other bill cash allocation screen*/
		public static const BILL_CASH_ALLOCATION_SUBCODE:String = "03";
		
		/** The constant indicates Agency allocation screen*/
		public static const AGENT_ALLOCATION_SUBCODE:String = "04";
		
		// The HoldSuspend related constants
		/** The constant used to denote the Hold status */
		public static const HOLD_STATUS:String = 'H';

		/** The constant used to denote the Suspend status */
		public static const SUSPEND_STATUS:String = 'S';
		
		/** The constant used to denote the Release status */
		public static const RELEASE_STATUS:String = 'R';
		
		/** The constant used to denote the Suspend NOC status */
		public static const SUSPEND_NOC_STATUS:String = 'N';
		
		/** The constant used to denote the Release NOC status */
		public static const RELEASE_NOC_STATUS:String = 'RN';
		
		/** The constant used to denote the Operate On Account status */
		public static const OPERATE_ON_ACCOUNT:String = 'acc';
		
		/** The constant used to denote the Operate On Policy status */
		public static const OPERATE_ON_POLICY:String = 'pol';
		
		/** The constant used to denote the break installment schedule length */
		public static const BREAK_INS_SCH_LENGTH:int = 10;
		
		/** The number of characters allowed in the Hold Suspend Comments text area */
		public static const HOLD_SUSPEND_COMMENTS_LENGTH:Number = 36;
		
		/** The change type when the action performed is adding a new field */
		public static const CHANGE_TYPE_ADDED:String = "ADDED";
		
		/** The change type when the action performed is deleting a field */
		public static const CHANGE_TYPE_DELETED:String = "DELETED";
		
		/** The change type when the action performed is updating an existing field */
		public static const CHANGE_TYPE_MODIFIED:String = "MODIFIED";
		
		/** The constant used to denote the length of gtable code 2 field when performing lov search */
		public static const FIELD_LENGTH_GTABLE_CODE_TWO:Number = 36;
		
		/** The constant used to denote the length of gtable name field when performing lov search */
		public static const FIELD_LENGTH_GTABLE_NAME:Number = 30;
		
		/** The constant used to denote the length of master code field in Lov Add screen */
		public static const FIELD_LENGTH_MASTER_CODE:Number = 30;
		
		/** The constant used to denote the length of order number field in Lov Add screen */
		public static const FIELD_LENGTH_ORDER_NUMBER:Number = 35;
		
		/** The constant used to denote the length of subcode field in Lov Add screen */
		public static const FIELD_LENGTH_SUBCODE:Number = 100;
		
		/** The constant used to denote the length of description field in Lov Add screen */
		public static const FIELD_LENGTH_DESCRIPTION:Number = 100;
		
		/** The constant used to denote the length of numeric value field in Lov Add screen */
		public static const FIELD_LENGTH_NUMERIC_VALUE:Number = 40;
		
		/** The constant used to denote the length of char value field in Lov Add screen */
		public static const FIELD_LENGTH_CHAR_VALUE:Number = 1000;
		
		/** The constant used to denote the value 'A' for all All option*/
		public static const OPTION_ALL_VALUE:String = 'A';
		
		/** The constant used to denote the value 'F' for all Future option*/
		public static const OPTION_FUTURE_VALUE:String = 'F';
		
		/*
		 * Added By Gourav Rai
		 */		
		/**  The constant indicates Account level String*/
		public static const MANUAL_REFUND_ACCOUNT_LEVEL:String = "2";
		
		/**  The constant indicates Policy level String*/
		public static const MANUAL_REFUND_POLICY_LEVEL:String = "3";
		
		/**  The constant indicates Account Policy level String*/
		public static const MANUAL_REFUND_ACCOUNT_POLICY_LEVEL:String = "23";
		
		/**  The constant indicates Suspend Policy level String*/
		public static const MANUAL_REFUND_ACCOUNT_TRANSACTION_LEVEL:String = "24";
		
		/**  The constant indicates transaction level String*/
		public static const MANUAL_REFUND_TRANSACTION_LEVEL:String = "4";
		
		/** The constant used to indicate payee indicator*/
		public static const PAYEE_INDICATOR_INSURED:String = 'INSURED';
		
		/** The constant used to indicate review*/
		public static const REVIEW:String = 'REVIEW';
		
		/** The constant used to indicate review close*/
		public static const REVIEW_CLOSE:String = 'REVIEW_CLOSE';
		
		/** The constant used to indicate Commission type agent*/
		public static const COMMMISSION_EXTRACTION_TYPE_AGENT:String = 'Agent';
		
		/** The constant used to indicate Commission extraction exclude agents*/
		public static const COMMISSION_EXTRACTION_EXCLUDE_AGENTS:String = 'E';
		
		/** The constant used to indicate Commission extraction including agents*/
		public static const COMMISSION_EXTRACTION_INCLUDE_AGENTS:String = 'I';
		
		/** The constant used to indicate Commission extraction for all the agents*/
		public static const COMMISSION_EXTRACTION_ALL_AGENTS:String = 'A';
		
		/** The code used to indicates that the default no. of agents to show on include and exclude
		 * agents popup.
		 */
		public static const DEFAULT_AGENTS_COUNT:int = 6;
		
		/** The master code for non-cancel flag drop down */
		public static const NON_CANCEL_FLAG_MASTER_CODE:String = 'PI_CAN_OPT_FLG';
		
		/** The master code for program type drop down */
		public static const PROGRAM_TYPE_MASTER_CODE:String = 'PROGRAM_TYPE';
		
		/** The master code for program type drop down */
		public static const CRT_CODES_MASTER_CODE:String = 'CRT_CODES';
		
		/** The master code for operation region drop down */
		public static const OPERATING_REGION_MASTER_CODE:String = 'OPERATING_REGION';
		
		/** The master code for audit sub types drop down */
		public static const AUDIT_SUB_TYPE_MASTER_CODE:String = 'AUDIT_SUB_TYPE';
		
		/** The master code for the discount plan codes drop down */
		public static const DISCOUNT_PLAN_CODE_MASTER_CODE:String = 'DISCOUNT_PLAN_CODE';
		
		/** The master code for the Billing flag drop down */
		public static const BILLING_FLAG_MASTER_CODE:String = 'BILLING_FLAG';
		
		/** The master code for the Country drop down */
		public static const COUNTRY_MASTER_CODE:String = 'COUNTRY';
		
		/** The master code for the county codes drop down */
		public static const COUNTY_CODES_MASTER_CODE:String = 'COUNTY_CODES';
		
		/** The master code for the states codes drop down */
		public static const ALL_STATES_MASTER_CODE:String = 'ALL_STATES';
		
		/** The master code for the bill types drop down */
		public static const BILL_TYPE_MASTER_CODE:String = 'BILL_TYPE';

		/** The master code for the payment plan drop down */
		public static const PAYMENT_PLAN_MASTER_CODE:String = 'PAYMENT_PLAN';
		
		/** The master code for the payment method drop down */
		public static const PAYMENT_METHOD_LIST_MASTER_CODE:String = 'PAYMENT_METHOD';
		
		/** The master code for the underwriting company drop down */
		public static const UNDERWRITING_COMPANY_MASTER_CODE:String = 'UNDERWRITING_COMPANY';
		
		/** The master code for the product codes drop down */
		public static const PRODUCT_CODE_MASTER_CODE:String = 'PRODUCT_CODE';
		
		/** The master code for the operating company drop down */
		public static const OPERATING_COMPANY_MASTER_CODE:String = 'OPERATING_COMPANY';
		
		/** The master code for the transaction type drop down */
		public static const TRANSACTION_TYPE_MASTER_CODE:String = 'TRANSACTION_TYPE';
		
		/** The master code for the cancellation rate drop down */
		public static const CANCELLATION_RATE_MASTER_CODE:String = 'CANCELLATION_RATE';
		
		/** The master code for the cancellation type drop down */
		public static const CANCELLATION_TYPE_MASTER_CODE:String = 'CANCELLATION_TYPE';
		
		/** The master code for the cancellation reasons drop down */
		public static const CANCELLATION_REASONS_MASTER_CODE:String = 'CANCELLATION_REASONS';
		
		/** The master code for the bill to indicator drop down */
		public static const BILL_TO_INDICATOR_MASTER_CODE:String = 'BILL_TO_INDICATOR';
		
		/** The master code for the commission paid drop down */
		public static const COMMISSION_PAID_MASTER_CODE:String = 'COMMISSION_PAID';
		
		/** The master code for the deposit account type drop down */
		public static const DEPOSIT_ACCOUNT_TYPE_MASTER_CODE:String = 'DEPOSIT_ACCOUNT_TYPE';
		
		/** The master code for the endr spread option drop down */
		public static const ENDR_SPREAD_OPTION_MASTER_CODE:String = 'ENDR_SPREAD_OPTION';
		
		/** The master code for the due day change option drop down */
		public static const ACCOUNT_TYPE_LIST_MASTER_CODE:String = "ACCOUNT_TYPE";
		
		/** The master code for the due day change option drop down */
		public static const EFT_DRAFT_OPTION_LIST_MASTER_CODE:String = "EFT_DRAFT_OPTION";
		
		/** The master code for the due day change option drop down */
		public static const BANK_ACCOUNT_TYPES_MASTER_CODE:String = "BANK_ACCOUNT_TYPE";
		
		/** The master code for the due day change option drop down */
		public static const CREDIT_CARD_TYPE_LIST_MASTER_CODE:String = "CREDIT_CARD_TYPE";
		
		/** The master code for the receivable type list drop down */
		public static const RECEIVABLE_TYPE_MASTER_CODE:String = 'RECEIVABLE_TYPE';
		
		/** The master code for the bank codes list drop down */
		public static const BANK_CODES_MASTER_CODE:String = 'BANK_CODES';
		
		/** The Entity type drop down list */
		public static const PAS_RBS_ENTITY_MAPPING:String = 'PAS_RBS_ENTITY_MAPPING';
		
		/** The Entity type drop down list */
		public static const CREDIT_CARDS_PAYMENT_METHOD:String = 'I';
		
		/** The Entity type drop down list */
		public static const RECURRING_ACH_PAYMENT_METHOD:String = 'A';
		
		/** The manual cash allocation source - edit check.*/
		public static const EDIT_CHECK_SOURCE:String = 'EDIT_CHECK';
		
		/** The manual cash allocation source - add check.*/
		public static const ADD_CHECK_SOURCE:String = 'ADD_CHECK';
		
		/** The request commission type for policy commission adjustment */
		public static const COMMISSION_ADJUSTMENT_POLICY:String = 'POLICY_COMMISSION_ADJUSTMENT';
		
		/** The request commission type for agency commission adjustment */
		public static const COMMISSION_ADJUSTMENT_AGENT:String = 'AGENCY_COMMISSION_ADJUSTMENT';
		
		/** The request commission type for commission paid detials */
		public static const COMMISSION_PAID:String = 'COMMISSION_PAID';
		
		/** The request commission type for commission return details */
		public static const COMMISSION_RETURN:String = 'COMMISSION_TOTAL_RETURNS';
		
		/** The request commission type for commission expense details */
		public static const COMMISSION_EXPENSE:String = 'COMMISSION_EXPENSE';
		
		/** The constant used to indicate request commission type as MLC expense */
		public static const COMMISSION_MLC_FLAG_YES:String = 'Y';
		
		/** The constant used to indicate request commission type is not MLC expense */
		public static const COMMISSION_MLC_FLAG_NO:String = 'N';
		
		/** The constant used to indicate include zero balance policy/account */
		public static const INCLUDE_ZERO_BALANCE_FLAG:String = "Y";
		
		/** The constant used to indicate exclude zero balance policy/account */
		public static const EXCLUDE_ZERO_BALANCE_FLAG:String = "N";
		
		/** The master code for the refund reason list drop down */		
		public static const REFUND_REASON_MASTER_CODE:String = "REFUND_REASON";
		
		/** The master code for the Yes No list drop down */		
		public static const YES_NO_OPTION_MASTER_CODE:String = "YES_NO_OPTION";
		
		/** The master code for all users list drop down */
		public static const ALL_USERS_MASTER_CODE:String = "ALL_USERS_LIST";
		
		/** The master code for Refund Entity list drop down */
		public static const REFUND_ENTITY_MASTER_CODE:String = "REFUND_ENTITY";
		
		/** The bill to indicator for the agency billed  policy */
		public static const BILL_TO_INDICATOR_P:String = "P";
		
		/** The bill to indicator for the account billed policy */
		public static const BILL_TO_INDICATOR_C:String = "C";
		
		/** The constant indicates bill type policy **/
		public static const BILL_TYPE_DIRECT:String = "S";
		
		/** The constant indicates bill type ACCOUNT **/
		public static const BILL_TYPE_ACCOUNT:String = "C";
		
		/**  The constant indicates Invoice level String*/
		public static const INVOICE_LEVEL:String = "I";
		
		/**  The constant indicates Account level transaction String*/
		public static const ACCOUNT_LEVEL_TRANSACTION:String = "Q";
		
		/**  The constant indicates Account level transaction String*/
		public static const DELETED_STATUS:String = "Deleted";
		
		/** The master code for the PDC STATUS list drop down */
		public static const PDC_STATUS_MASTER_CODE:String = "PDC_STATUS";
		
		/**  The constant indicates drop down options status Y String*/
		public static const DROP_DOWN_OPTION_Y:String = "Y";
		
		/**  The constant indicates drop down options status C String*/
		public static const DROP_DOWN_OPTION_C:String = "C";
		
		/** The master code for the context list drop down */
		public static const CONTEXT_MASTER_CODE:String = "CONTEXT";
		
		/** The master code for the communication status list drop down */
		public static const COMMUNICATION_STATUS_MASTER_CODE:String = "COMM_STATUS";
		
		/** The master code for the communication action list drop down */
		public static const COMMUNICATION_ACTION_MASTER_CODE:String = "COMM_ACTION";
		
		/** The master code for the communication priority list drop down */
		public static const COMMUNICATION_PRIORITY_MASTER_CODE:String = "COMMUNICATION_PRIORITY";
		
		/** The master code for the active user list drop down */
		public static const ACTIVE_USER_MASTER_CODE:String = "ACTIVE_USER_LIST";
		
		/** The master code for the analyst list drop down */
		public static const ANALYST_LIST_MASTER_CODE:String = "ANALYST_LIST_FOR_ACCOUNT_CURRENT";
		
		/** The master code for the account current status cash applied */
		public static const ACCOUNT_CURRENT_STATUS_CASH_APPLIED:String = "C";
		
		/** The master code for the account current status cash applied */
		public static const ACCOUNT_CURRENT_STATUS_INCOMPLETE:String = "N";
		
		/** The master code for account current status cash applied */
		public static const ACCOUNT_CURRENT_STATUS_COMPLETE:String = "Y";
		
		/** The master code for the exclude flag is false*/
		public static const EXCLUDE_FLAG_N:String = "N";
		
		/** The master code for the exclude flag is true */
		public static const EXCLUDE_FLAG_Y:String = "Y";
		
		/**  The constant indicates Unapplied level String*/
		public static const UNAPPLIED:String = "UNAPPLIED";
		
		/**  The constant indicates Unapplied level String*/
		public static const UNIDENTIFIED_LEVEL:String = "UNIDENTIFIED";
		
		/** The context code used to denote policy context */
		public static const POLICY_CONTEXT_CODE:String = "P";
		
		/** The context code used to denote account context */
		public static const ACCOUNT_CONTEXT_CODE:String = "AC";
		
		/** The context code used to denote agency context */
		public static const AGENCY_CONTEXT_CODE:String = "A";
		
		/** The context code used to denote others context */
		public static const OTHERS_CONTEXT_CODE:String = "O";
		
		/** The context code used to denote manual cash allocation. */
		public static const MANUAl_CASH_ALLOCATION:String = "MANUAl_CASH_ALLOCATION";
		
		/** The maximum length of the user id field on the user search screen */
		public static const USER_ID_MAX_LENGTH:Number = 30;
		
		/** The maximum length of the user name field on the user search screen */
		public static const USER_NAME_MAX_LENGTH:Number = 50;
		
		/** The constant for MODIFY communication action */
		public static const COMMUNICATION_ACTION_MODIFY:String = "MODIFY";
		
		/** The constant for REASSIGN communication action */
		public static const COMMUNICATION_ACTION_REASSIGN:String = "REASSIGN";
		
		/** The constant for RESCHEDULE communication action */
		public static const COMMUNICATION_ACTION_RESCHEDULE:String = "RESCHEDULE";
		
		/** The constant for DELETE communication action */
		public static const COMMUNICATION_ACTION_DELETE:String = "DELETE";
		
		/** The constant for UNDELETE communication action */
		public static const COMMUNICATION_ACTION_UNDELETE:String = "UNDELETE";
		
		/** The report name for generating the report used for Notepad module */
		public static const REPORT_NAME_COMM_VIEW_NOTES:String = "COMM_VIEW_NOTES";
		
		/** The report name for generating the report used for Diary module */
		public static const REPORT_NAME_COMM_VIEW_DIARY:String = "COMM_VIEW_DIARY";

		/** The report name for cash batch operation add checks */
		public static const ADD_CHECKS:String = "ADD_CHECKS";
		
		/** The report name for cash batch operation edit check */
		public static const EDIT_CHECK:String = "EDIT_CHECK";

		/** The report name for cash batch operation delete checks */
		public static const DELETE_CHECKS:String = "DELETE_CHECKS";
		
		/** The constant for the diary status Delete */
		public static const DIARY_STATUS_DELETE:String = "DEL";
		
		/** The constant for context type accoount*/
		public static const CONTEXT_TYPE_ACCOUNT:String = "AC";

		/** The constant for context type accoount*/
		public static const CONTEXT_TYPE_POLICY:String = "P";
		
		/** The constant for the diary priority normal flag. */
		public static const DIARY_PRIORITY_NORMAL:String = "N";
		
		/** The constant for the diary communication type */
		public static const COMMUNICATION_TYPE_DIARY:String = "DIARY";
		
		/** The transaction type constant for non money endorsement */
		public static const TRANSACTION_TYPE_NON_MONEY_ENDORSEMENT:String = "NEDR";
		
		/** The constant for showing status of policy in cancellation preview */
		public static const CANCELLATION_POLICY_STATUS_MASTER_CODE:String = "CAN_PREVIEW_POLICY_STATUS";
		
		/** The master code for the Zip codes drop down */
		public static const ZIP_CODE_MASTER_CODE:String = "ZIP_CODE";
		
		/** The master code for the Suspend codes drop down */
		public static const SUSPEND_CODE_MASTER_CODE:String = "SUSPEND_CODE";
		
		/** The constant for the suspend noc */
		public static const SUSPEND_NOC_ACTION:String = "SUSPEND_NOC_ACTION";
		
		/** The constant for the release noc */
		public static const RELEASE_NOC_ACTION:String = "RELEASE_NOC_ACTION";
		
		/** The master code for the Frequency codes drop down */
		public static const FREQUENCY_CODES:String = "FREQUENCY";
		
		/** The master code for the Skip Schedule codes drop down */
		public static const SKIP_SCHEDULE_CODE:String = "SKIP_SCHEDULE_CODE";
		
		/** The master code for the Weekday codes drop down */
		public static const WEEKDAY_CODES:String = "WEEKDAY";
		
		/** The master code for the Report type codes drop down */
		public static const REPORT_FORMAT_CODES:String = "REPORT_FORMAT";
		
		/** The master code for the getting Source System dropdowns **/
		public static const SOURCE_SYSTEM_CODE:String = 'SOURCE_SYSTEM';
		
		/** The master code for the getting Ofac status dropdowns **/
		public static const OFAC_STATUS_CODE:String = 'OFAC_STATUS';
		
		public static const ADDRESS_TYPE_ACCOUNT:String = "ACCOUNT";
		public static const ADDRESS_TYPE_AGENCY:String = "AGENCY";
		public static const ADDRESS_TYPE_PRODUCER:String = "PRODUCER";
		public static const ADDRESS_TYPE_ALT_AGENCY:String = "ALT_AGENCY";
		public static const ADDRESS_TYPE_BUSINESS:String = "BUSINESS";
		
		/** The master code for the getting Manual collection processing dropdowns **/
		public static const MANUAL_COLLECTION_PROCESSING:String = "MANUAL_COLLECTION_PROCESSING";
		
		/** The master code for the getting Manual collection agency dropdowns **/
		public static const COLLECTION_AGENCY_CODES:String = "COLLECTION_AGENCY_CODES";
		
		/** The master code for the getting form name dropdowns **/
		public static const FORM_NAME:String = "FORM_NAME";
		
		/** The constant which represents maximum number of modules needs to be 
		 * cached in multi app
		 */
		 public static const MAX_MODULES:uint = 5;
		 /**  This constant use for opening  undo adjustment popup **/ 		 
		 public static const UNDO_ADJUSTMENT_POPUP:String = 'UNDO_ADJUSTMENT_POPUP';
		 
		 /**
		 * The constant which is used to open pdf link in Agent Output Module
		 */
		 public static const AGENT_OUTPUTS:String = "AGENT_OUTPUTS";
		
		/**
		 * The constant which is used to open pdf link in Agent Output Module
		 */
		 public static const AGENT_STATEMENT:String = "AGENT_STATEMENT";
		 
		 /** The Past account current detail type */
		 public static const PAST_ACCOUNT_CURRENT_DETAIL_TYPE:String = "pastAccountCurrent"
		 
		 /** The prior account current detail type */
		 public static const PRIOR_ACCOUNT_CURRENT_DETAIL_TYPE:String = "priorAccountCurrent"
		 
		 /** The present account current detail type */
		 public static const PRESENT_ACCOUNT_CURRENT_DETAIL_TYPE:String = "presentAccountCurrent"
		 
		 /** The future account current detail type */
		 public static const FUTURE_ACCOUNT_CURRENT_DETAIL_TYPE:String = "futureAccountCurrent"
		 
		  /** The closed account current detail type */
		 public static const CLOSE_CASH_ALLOCATION_DETAIL_TYPE:String = "closeCashAllocation"
			 
		 /** The logged In User account current details type */
		 public static const ACCOUNT_CURRENT_STARTUP:String = "ACCOUNT_CURRENT_STARTUP"
			 
		 /** The search of account current details type */
		 public static const ACCOUNT_CURRENT_SEARCH:String = "ACCOUNT_CURRENT_SEARCH"
			 
		 /** The constant used for account current descrepancy report */
		 public static const DISCREPANCY_REPORT:String = "DISCREPANCY_REPORT"
			 
		 /** The constant used for account current descrepancy report template */
		 public static const REPORT_NAME:String = "cfi_discrep_af_ac"
		  
		 
		 /** The premium difference discrepancy type */ 
		 public static const DISC_TYPE_PREMIUM_DIFFERENCE:String = "PD";
		 
		 /** The commission difference discrepancy type */
		 public static const DISC_TYPE_COMMISSION_DIFFERENCE:String = "CD";
		 
		 /** The omitted items discrepancy type */
		 public static const DISC_TYPE_OMITTED_ITEMS:String = "OI";
		 
		 /** The unbooked transactions discrepancy type */
		 public static const DISC_TYPE_UNBOOKED_TRANSACTIONS:String = "UB";
		 
		 /** The code for fetching the transaction types */
		 public static const TRANSACTION_RECORD_TYPES_MASTER_CODE:String = "TRANSACTION_RECORD_TYPES";
		 
		 /** This constant use for transaction type adjustment **/
		 public static const TRANSACTION_TYPE_ADJUSTMENT:String = 'ADJ';
		 
		 /** This constant use for transaction type credit adjustment **/
		 public static const TRANSACTION_TYPE_CREDIT_ADJUSTMENT:String = 'PAYADJ';
		 
		  /** This constant use for closed items for agency check search select status **/
		 public static const INCLUDE_CLOSED_ITEMS_SELECT:String = "Y";
		 
		  /** This constant use for closed items for agency check search deselect status **/
		 public static const INCLUDE_CLOSED_ITEMS_DESELECT:String = "N";
		 
		   /** The constant used for check search descrepancy report template */
		 public static const REPORT_NAME_CHK_SEARCH:String = "cfi_discrep_af_ca";
		 
		 public static const AGENCY_CASH_SUCCESS_FLAG_1:String = "10";
		 public static const AGENCY_CASH_SUCCESS_FLAG_2:String = "11";
		 public static const AGENCY_CASH_SUCCESS_FLAG_3:String = "12";
		 public static const AGENCY_CASH_UPDATE_FLAG_YES:String = "Y";
		 public static const AGENCY_CASH_REMOVE_FLAG_YES:String = "Y";
		 
		 public static const ALLOC_CASH_FLAG_YES:String = "Y";
		 public static const ALLOC_CASH_FLAG_NO:String = "N";
		 
		 public static const ACCOUNT_CURRENT_STATUS_N:String = "N";
	}
}