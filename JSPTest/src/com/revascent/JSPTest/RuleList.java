package com.revascent.JSPTest;

public class RuleList {

	private static int RuleID;
	private static String RuleName;
	private static int ClaimCount;
	private static double Dollars;
	
	public static void setRuleID(int r){
		RuleID = r;
	}
	
	public static void setRuleName(String name){
		RuleName = name;
	}
	
	public static void setClaimCount(int c){
		ClaimCount = c;
	}

	public static void setDollars(double doll){
		Dollars = doll;
	}
	
	public static int getRuleID(){
		return RuleID;
	}
	
	public static String getRuleName(){
		return RuleName;
	}
	
	public static int getClaimCount(){
		return ClaimCount;
	}
	
	public static double getDollars(){
		return Dollars;
	}
}
