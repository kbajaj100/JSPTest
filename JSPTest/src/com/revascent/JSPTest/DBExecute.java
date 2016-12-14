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


public class DBExecute {
	
	private static String dbUrl ="";
	private static String SQL; 
	private String Claims = "(";
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs= null;
	
	private static int count = 0;
	private String code;
	static DBConn myconn = new DBConn();
	
	static ArrayList<Integer> RuleList = new ArrayList<Integer>();
	static int Run_ID = 0;
	static LocalDateTime Datetime;
	
	public static int setRUN_ID(){
		
		SQL = "select max(RUN_ID) count from rcmods.FACT_GIC_STG";
		
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
		
		SQL = "select COUNT(distinct Rule_ID) count from rcmods.Rule_sheet_Index where Status = 'A'";
	
		count = myconn.execSQL_returnint(SQL);
		
		createRuleList(count);
		
		return count;
	}
		
	
	public static int getRuleCountAll() throws FileNotFoundException, IOException, SQLException{
		//Returns the number of rule IDs in the database
		
		myconn.setDBConn("C:/Props/RulesEngine/DBprops.properties");
		
		SQL = "select COUNT(distinct Rule_ID) count from rcmods.Rule_sheet_Index";
	
		count = myconn.execSQL_returnint(SQL);
		
		//createRuleList(count);
		
		return count;
	}
	
	public static void createRuleList(int count){
		
		SQL = "select Rule_ID from rcmods.Rule_sheet_Index where Status = 'A'";
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
		SQL = "select distinct Rule_Name from rcmods.Rule_sheet_Index where Rule_ID = " + rulecounter;
		
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
	
	
	
	public static int getRuleClaimCount(int rulenum, int Run){
		
		Run_ID = Run-1;
		
		SQL = "select COUNT(distinct CLM_ID) as count from rcmods.FACT_GIC_STG where Rule_ID = " + rulenum + " and RUN_ID = " + (Run_ID) + " group by Rule_ID";	
		int ruleclaimcouunt = myconn.execSQL_returnint(SQL);
		return ruleclaimcouunt;
	}
	
	public static String getSQL() {
		// TODO Auto-generated method stub
		
		return SQL;
	}
	
}