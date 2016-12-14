package com.revascent.JSPTest;

//Main with ActiveRule changes

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class MainClass {
		
	// In the main function, create a database connection to get the number of rules.
	// This can be done by creating a static function 
	// Create a for loop for the where i<= number of rules
	// In the for loop call a function, pass the rule number
	// the function will receive the rule number
	// Get the rule type for the rule
	// Create a method in the DBConn class to execute for that particular rule type
	
	public static String MainExecute() throws FileNotFoundException, IOException, SQLException {

		DBRulesEngine myDB = new DBRulesEngine();

		int rulecount; // # of active rules
		int dummy = 0; 
		int runid = 0; // Run ID
		
		rulecount = myDB.getRuleCount();
		//System.out.println(rulecount);
		
		runid = myDB.setRUN_ID();
		
		//return ("Run_ID is " + myDB.getRun_ID());
		
		int i = 0;
		
		for (i = 0; i < rulecount; ++i) //i is the rule counter
			dummy = ExecuteRule(i, myDB, runid); //Will execute each rule by sending the Rule counter id
		
		return ("Completed processing " + i + " rules for Run_ID: " + runid);
	}

	private static int ExecuteRule(int rulecounter, DBRulesEngine myDB, int runid) {
		// TODO Auto-generated method stub

		int RuleTypeCount; // Number of Rule types in the rule
		int RuleID;
		
		RuleID = myDB.getRuleID(rulecounter); //gets the rule id for that rule counter

		RuleTypeCount = myDB.getRuleTypeNums(RuleID);
		System.out.println("Rule ID is: " + RuleID);
		System.out.println("Rule Type Count is: " + RuleTypeCount);
		
		for (int i = 1; i<= RuleTypeCount; ++i){	
			myDB.ExecRule_RuleTypeNum(RuleID, i, runid);	//i represents the Right Rule Type Number
		}
		
		return 1;
	}
}

