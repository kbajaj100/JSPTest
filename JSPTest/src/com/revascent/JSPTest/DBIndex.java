package com.revascent.JSPTest;

public class DBIndex {

	String Claims_Table = "rcmods.VW_CLAIM_CPT";
	String Flagged_Table = "rcmods.FACT_GIC_STG"; 
	String RS_Index = "rcmods.Rule_sheet_Index";
	String RS = "rcmods.Rule_Sheet";
	String RS_Left = "rcmods.Rule_Sheet_Left";
	
	public String getClaims_Table() {
		return Claims_Table;
	}
	
	public void setClaims_Table(String claims_Table) {
		Claims_Table = claims_Table;
	}
	
	public String getFlagged_Table() {
		return Flagged_Table;
	}
	
	public void setFlagged_Table(String flagged_Table) {
		Flagged_Table = flagged_Table;
	}
	
	public String getRS_Index() {
		return RS_Index;
	}
	
	public void setRS_Index(String rS_Index) {
		RS_Index = rS_Index;
	}
	
	public String getRS() {
		return RS;
	}
	
	public void setRS(String rS) {
		RS = rS;
	}
	
	public String getRS_Left() {
		return RS_Left;
	}
	
	public void setRS_Left(String rS_Left) {
		RS_Left = rS_Left;
	}
	
}
