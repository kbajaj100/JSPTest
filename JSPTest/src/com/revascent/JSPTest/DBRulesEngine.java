package com.revascent.JSPTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class DBRulesEngine {
	
	private static String dbUrl ="";
	private static String SQL; 
	private String Claims = "(";
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs= null;
	
	private static int count = 0;
	private static int left_sub_count = 0;
	private String code;
	static DBConn myconn = new DBConn();
	
	static ArrayList<Integer> RuleList = new ArrayList<Integer>();
	static int Run_ID = 0;
	static LocalDateTime Datetime;
	
	static DBIndex myDBIndex = new DBIndex();
	
	public static int setRUN_ID(){
		
		SQL = "select max(RUN_ID) count from " + myDBIndex.getFlagged_Table();
		
		Run_ID = myconn.execSQL_returnint(SQL);
		++Run_ID;
		return Run_ID;
	}
	
	public static int getRun_ID(){
		return Run_ID;
	}
	
	public static void setDate(){
		
		Datetime = LocalDateTime.now();
		//return Datetime;
	}
	
	
	public static String getdbUrl() throws FileNotFoundException, IOException, SQLException{
		//Returns the number of rule IDs in the database
		
		myconn.setDBConn("C:/Props/RulesEngine/DBprops.properties");
		
		dbUrl = myconn.getdbUrl();
	
		return dbUrl;
	}
	
	public static int getRuleCount() throws FileNotFoundException, IOException, SQLException{
		//Returns the number of rule IDs in the database
		
		myconn.setDBConn("C:/Props/RulesEngine/DBprops.properties");
		
		SQL = "select COUNT(distinct Rule_ID) count from " + 
			   myDBIndex.getRS_Index() + " where Status = 'A'";
	
		count = myconn.execSQL_returnint(SQL);
		
		createRuleList(count);
		
		return count;
	}
		
	
	public static int getRuleCountAll() throws FileNotFoundException, IOException, SQLException{
		//Returns the number of rule IDs in the database
		
		myconn.setDBConn("C:/Props/RulesEngine/DBprops.properties");
		
		SQL = "select COUNT(distinct Rule_ID) count from " + myDBIndex.RS_Index;
	
		count = myconn.execSQL_returnint(SQL);
		
		//createRuleList(count);
		
		return count;
	}
	
	public static void createRuleList(int count){
		
		SQL = "select Rule_ID " + 
			  "from " + myDBIndex.getRS_Index() + " " + 
			  "where Status = 'A'";
		
		dbUrl = myconn.getdbUrl();
		int arrcounter = 0;
		
		try {
			//Step 1. Connection to the db
			conn = DriverManager.getConnection(dbUrl);
		
			// Create statement object
			stmt = conn.createStatement();
		
			// 3. Execute SQL query
			rs = stmt.executeQuery(SQL);
			
			//4. Process result set
			while (rs.next()){
				
				int RuleID = rs.getInt("Rule_ID");//.getString("count");
				System.out.println("RuleID is: " + RuleID);
				RuleList.add(arrcounter, RuleID);
				++arrcounter;
				
			}
			
			//Claims = Claims + ")";
			//System.out.println(Claims);
		}
		
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
	
		
		finally {
			//close(myConn, myStmt, myRS);
			if (rs   != null) try { rs.close();   } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (conn != null) try { conn.close(); } catch(Exception e) {}
		}
		
	}
	
	public static int getRuleID(int rulecounter) {
		// TODO Auto-generated method stub
		int RuleID = RuleList.get(rulecounter);
		return RuleID;
	}
	
	public static String getRuleName(int rulecounter) {
		// TODO Auto-generated method stub
		//int RuleID = RuleList.get(rulecounter);
		SQL = "select distinct Rule_Name " + 
			  "from " + myDBIndex.getRS_Index() + " " +  
			  "where Rule_ID = " + rulecounter;
		
		dbUrl = myconn.getdbUrl();
		String RuleName;
		
		try {
			//Step 1. Connection to the db
			conn = DriverManager.getConnection(dbUrl);
		
			// Create statement object
			stmt = conn.createStatement();
		
			// 3. Execute SQL query
			rs = stmt.executeQuery(SQL);
			
			//4. Process result set
			while (rs.next()){
				
				RuleName = rs.getString("Rule_Name");
				//System.out.println(count);
				return RuleName;

			}
			
			//Claims = Claims + ")";
			//System.out.println(Claims);
		}
		
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			//close(myConn, myStmt, myRS);
			if (rs   != null) try { rs.close();   } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (conn != null) try { conn.close(); } catch(Exception e) {}
		}
		
		
		//String RuleName = myconn.execSQL_returnString(SQL);
		return SQL;
	}
	
	
	public static int getRuleClaimCount(int rulenum){
		
		SQL = "select COUNT(distinct CLM_ID) as count " + 
			  "from " + myDBIndex.getFlagged_Table() + " " +  
			  "where Rule_ID = " + rulenum + " and RUN_ID = " + Run_ID + " group by Rule_ID";	
		int ruleclaimcouunt = myconn.execSQL_returnint(SQL);
		return ruleclaimcouunt;
	}
	
	public static String getSQL() {
		// TODO Auto-generated method stub
		
		return SQL;
	}
	
	public String getRuleResult(int RuleID, int RuleTypeCount){
		
		String rs1 = "";
		
		return rs1;
	}

	public int getRuleTypeNums(int ruleID) {
		// Get the number of rule types for a given RuleID
		
		SQL = "select count(distinct Right_Rule_Type_ID) count " + 
			  "from " + myDBIndex.getRS() + " " +  
			  "where Rule_ID = " + ruleID;

		count = myconn.execSQL_returnint(SQL);
		return count;
		
	}

	public int getRuleType(int ruleNum, int RuleTypeNum) {
		// Gets the Right Rule Type ID 
		int RuleTypeID;
		
		SQL = "select distinct Right_Rule_Type_ID count " + 
			  "from " + myDBIndex.getRS() + " " +  
			  "where Rule_ID = " + 
			  ruleNum + " and Rule_Type_Number = " + RuleTypeNum;
		
		RuleTypeID = myconn.execSQL_returnint(SQL);
		
		return RuleTypeID;

	}
	
	public int getLeftRuleType(int ruleNum) {
		// Gets the Rule Type ID 
		int LeftRuleTypeID;
		
		SQL = "select distinct Left_Rule_Type_ID count " + 
			  "from " + myDBIndex.getRS_Left() + " " + 
			  "where Rule_ID = " + ruleNum;
		
		LeftRuleTypeID = myconn.execSQL_returnint(SQL);
		
		return LeftRuleTypeID;

	}

	public void ExecRule_RuleTypeNum(int ruleID, int RuleTypeNumber, int runid) {
		// TODO Auto-generated method stub
		
		//Get the Rule Type for the given Rule ID and Rule Type Number
		int RightRuleType;
		
		RightRuleType = getRuleType(ruleID, RuleTypeNumber);
		
		// Construct SQL for the Rules
		if ((RightRuleType == 1) || (RightRuleType == 2) || (RightRuleType == 10)) 
		{
			ExecRule_123_Left(ruleID, RightRuleType, RuleTypeNumber, runid);
		}
	}
	
	public void ExecRule_123_Left(int ruleID, int RightRuleType, int RuleTypeNumber, int runID)
	{

		
		System.out.println();

		SQL = "select COUNT(distinct Left_Sub_Rule_ID) count " + 
				"from " + myDBIndex.getRS_Left() + " " + 
				"where Rule_ID = " + ruleID;

		left_sub_count = myconn.execSQL_returnint(SQL); 

		if (left_sub_count == 1)
			ExecRule_Left_Sub_Count_1(ruleID, RightRuleType, RuleTypeNumber, runID);
		
		ExecRule_1_Right(Claims, ruleID, RightRuleType, RuleTypeNumber, runID);
		
	}

	private void ExecRule_Left_Sub_Count_1(int ruleID, int RightRuleType, int RuleTypeNumber, int runID) {
		// TODO Auto-generated method stub
		
		dbUrl = myconn.getdbUrl();
		int dummy = 1;
		Claims = "(";

		for (int i = 1; i <= left_sub_count; ++i){
		
			SQL = getSQL_Left(ruleID, RightRuleType, RuleTypeNumber, i);
			
				//System.out.println(SQL);
				try {
					//Step 1. Connection to the db
					conn = DriverManager.getConnection(dbUrl);
				
					// Create statement object
					stmt = conn.createStatement();
				
					// 3. Execute SQL query
					rs = stmt.executeQuery(SQL);
					
					//4. Process result set
					while (rs.next()){
						
						int clm_id = rs.getInt("CLM_ID");//.getString("count");
						System.out.println(clm_id);
						if (dummy != 1)
							Claims = Claims + ", " + clm_id;
						else Claims = Claims + clm_id;
						++dummy;
					}
					Claims = Claims + ")";
					System.out.println(Claims);
				}
				
				// Handle any errors that may have occurred.
				catch (Exception e) {
					e.printStackTrace();
				}
			
				
				finally {
					//close(myConn, myStmt, myRS);
					if (rs   != null) try { rs.close();   } catch(Exception e) {}
					if (stmt != null) try { stmt.close(); } catch(Exception e) {}
					if (conn != null) try { conn.close(); } catch(Exception e) {}
				}
		}
	}

	private String getSQL_Left(int ruleID, int ruleType, int ruleTypeNumber, int left_sub_counter) {
		// TODO Auto-generated method stub
		System.out.println("Rule ID is : " + ruleID);
		System.out.println("Right Rule Type is : " + ruleType);
		
		//Get the LeftRuleTypeID
		int lefttype;
		lefttype = getLeftRuleType(ruleID);
		
		if (lefttype == 1) 
		{
			SQL = 	"select distinct CLM_ID CLM_ID " + 
					"from " + myDBIndex.getClaims_Table() + " " +
					"where CLM_ID in " +
					"(select CLM_ID " + 
					"from " + myDBIndex.getClaims_Table() + " " + 
					"where CPT_CODE = " +  
					"(select distinct Rule_Primary_Code " + 
					"from " + myDBIndex.getRS_Left() + " " +  
					"where Rule_ID = " + ruleID + " " +  
					"and Left_Sub_Rule_ID = " + left_sub_counter + " " +  
					") " + 
					"group by CLM_ID " + 
					"having COUNT(CPT_CODE) = 2)";
		}
		else if (lefttype == 2)
		{
			SQL = 	"select distinct a11.CLM_ID CLM_ID " +
					"from " + 
					"(select CLM_ID " + 
					"from " + myDBIndex.getClaims_Table() + " " + 
					"where CPT_CODE in " + 
					"(select Rule_Primary_Code " + 
					"from " + myDBIndex.getRS_Left() + " " +
					"where Rule_ID = " + ruleID + " " +  
					"and Left_Sub_Rule_ID = " + left_sub_counter + " " +  
					"and Rule_Left_Line_ID = 1) " +
					"group by CLM_ID " +
					"having COUNT(CPT_CODE) = 1) a11 " +
					"join " +
					"(select CLM_ID " +
					"from " + myDBIndex.getClaims_Table() + " " +
					"where CPT_CODE in " +
					"(select Rule_Primary_Code " +
					"from " + myDBIndex.getRS_Left() + " " +
					"where Rule_ID = " + ruleID + " " +  
					"and Left_Sub_Rule_ID = " + left_sub_counter + " " +  
					"and Rule_Left_Line_ID = 2) " +
					"group by CLM_ID " +
					"having COUNT(CPT_CODE) = 1) a12 on " +
					"(a11.CLM_ID = a12.CLM_ID)"; 
			System.out.println(SQL);
		}
		else if (lefttype == 3){
			String SQL_in = "";
			SQL_in = getSQLin_Left_RuleType3(ruleID, ruleType, ruleTypeNumber);
			//System.out.println(SQL);
			
			SQL = "select CLM_ID CLM_ID, count(CPT_SEQUENCE_ID) count " + 
				  "from " + myDBIndex.getClaims_Table() + " " + 
				  "where CPT_Code in " + SQL_in + " group by CLM_ID";

			
		}
		
		return SQL;
	}


	private String getSQLin_Left_RuleType3(int ruleID, int ruleType, int ruleTypeNumber) {
		
		int num_lines = 0;
		
		SQL = "select count(Rule_Left_Line_ID) count from rcmods.Rule_Sheet_Left where Rule_ID = " + ruleID;
		
		num_lines = myconn.execSQL_returnint(SQL);
		
		String SQL_in = "(";
		
		for (int i = 1; i <= num_lines; ++i){
			
			SQL = "select Rule_Primary_Code count from rcmods.Rule_Sheet_Left where Rule_ID = " + ruleID + " and Rule_Left_Line_ID = " + i;
			code = myconn.execSQL_returnString(SQL);
			
			if (i == 1){
				SQL_in = SQL_in + "'" + code + "'";
			}
			else {
				SQL_in = ", '" + code + "'";
			}
		}
		
		SQL_in = SQL_in + ")";
		
		return SQL_in;
	}
	
	private void ExecRule_1_Right(String claims_list, int ruleID, int RightRuleType, int RuleTypeNumber, int runID) {
		// TODO Auto-generated method stub

		int Sub_Rule_count;
		String Flagged_Claims;
		
		// Get the number of sub rules for that ruleID and Rule Type
		SQL = "select max(Rule_Sub_ID)  count " + 
			  "from rcmods.Rule_sheet " + 
			  "where Rule_ID = " + ruleID  + " " +
			  "and Rule_Type_number = " + RuleTypeNumber;
		
		Sub_Rule_count = myconn.execSQL_returnint(SQL);
		
		System.out.println("Max Subrule is: " + Sub_Rule_count);
		
		// for each sub rule create SQL to flag claims
		for(int i = 1; i <= Sub_Rule_count; ++i)
		{
			// Get the code for which the claim should be checked if missing
			code = "";
			
			SQL = "select Missing_Value code " +
				  "from rcmods.Rule_sheet " +
				  "where Rule_ID = " + ruleID + " " +
				  "and Rule_Type_number = " + RuleTypeNumber + " " +
				  "and Rule_Sub_ID = " + i;

			System.out.println(SQL);
			code = myconn.execSQL_returnString(SQL);
			System.out.println("Missing Code is: "+ code);
			System.out.println("RightRuleType is: " + RightRuleType);
			System.out.println("Sub_Rule_ID is: " + i);
			
			// Conduct the check based on the Right Rule Type
			if (RightRuleType ==1)
			{
				SQL = "select a11.CLM_ID CLM_ID " +
					  "from rcmdw.FACT_CLAIM_DETAIL a11 " +
					  "where a11.CPT_Code in ('" +  
					  code + "') " +
					  "and CLM_ID in " +
					  claims_list + " " +
					  "group by a11.CLM_ID " +
					  "having COUNT(a11.CPT_CODE) = 1";
				//System.out.println(SQL);
			}
			else if (RightRuleType ==2)
			{
				
				SQL = "select distinct a11.CLM_ID " +
					  "from rcmdw.FACT_CLAIM_DETAIL a11 "+
					  "join " +
					  "(select CLM_ID " +
					  "from rcmdw.FACT_CLAIM_DETAIL " +
					  "where CPT_CODE in " + 
					  "(select Rule_Sub_Primary_Code1 " + 
					  "from rcmods.Rule_Sheet " +
					  "where Rule_ID = " + ruleID + " " + 
					  "and Rule_Sub_ID = " + i + ")) a12 on " +
					  "(a11.CLM_ID = a12.CLM_ID) " +
					  "where a11.CLM_ID in " +
					  claims_list + " and " + 
					  "a11.CLM_ID not in " +
					  "( " +  
					  "(select distinct CLM_ID " +
					  "from rcmdw.FACT_CLAIM_DETAIL " +
					  "where CPT_CODE in " + 
					  "(select Missing_Value " +
					  "from rcmods.Rule_Sheet " + 
					  "where Rule_ID = " + ruleID + " " + 
					  "and Rule_Sub_ID = " + i + ")))";

			}
			else if (RightRuleType ==10)
			{
				SQL = "select distinct a11.CLM_ID " +
					  "from rcmdw.FACT_CLAIM_DETAIL a11 "+
					  "where a11.CLM_ID in " +
					  claims_list + " and " + 
					  "a11.CLM_ID not in " +
					  "( " +  
					  "select CLM_ID " +
					  "from rcmdw.FACT_CLAIM_DETAIL " +
					  "where CPT_CODE in " + 
					  "(select Rule_Sub_Primary_Code1 " + 
					  "from rcmods.Rule_Sheet " +
					  "where Rule_ID = " + ruleID + " " + 
					  "and Rule_Sub_ID = " + i + ")" +
					  "and CLM_ID in " + claims_list + 
					  ")";
				
			}
			
			count = execFlagClaimSQL(ruleID, RightRuleType, RuleTypeNumber, code, i, runID);
		}

	}

	private int execFlagClaimSQL(int ruleID, int RuleType, int RuleTypeNumber, String code, int Sub_Rule_Line, int runID) {
	// Get the list of claims that are flagged	

		System.out.println(SQL);
		int dummy;
		dbUrl = myconn.getdbUrl();
		
		try {
			//Step 1. Connection to the db
			conn = DriverManager.getConnection(dbUrl);
		
			// Create statement object
			stmt = conn.createStatement();
		
			// 3. Execute SQL query
			rs = stmt.executeQuery(SQL);
			
			//4. Process result set
			while (rs.next()){
				
				int clm_id = rs.getInt("CLM_ID");//.getString("count");
				//System.out.println(clm_id);	
				dummy = execInsertFlagClaim(clm_id, code, ruleID, Sub_Rule_Line, runID);
			}

		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
	
		finally {
			//close(myConn, myStmt, myRS);
			if (rs   != null) try { rs.close();   } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (conn != null) try { conn.close(); } catch(Exception e) {}
		}

		return 0;
	}


	private int execInsertFlagClaim(int clm_id, String code, int ruleID, int Sub_Rule_Line, int runID) {
		// Insert each claim into the flagged table
		
		//dbUrl = myconn.getdbUrl();
		
		
		SQL = "INSERT INTO rcmods.FACT_GIC_STG " +
			  "("+
			  "CLM_ID, " +
			  "RULE_ID, " +
			  "SUB_RULE_ID, " +
			  "CPT_CODE, " + 
			  "RUN_ID " +  
			  ") " +
			  "VALUES "+
			  "(" + clm_id + ", " + ruleID + ", " + Sub_Rule_Line + ", '" + code + "', " + runID + ")";
		
		
		myconn.execSQL_InsertFlagClaim(clm_id, code, ruleID, Sub_Rule_Line, SQL);
		
		return 0;

	}


}