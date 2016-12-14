<%@ page import="com.revascent.JSPTest.*"  %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%= MainClass.MainExecute() %>
<br><br>
Run_ID is: <%= DBExecute.setRUN_ID()-1%>

<br><br>
<%
int count = DBExecute.getRuleCount();
int[] rulelist = new int[count];

for(int i = 0; i<count; ++i){
	rulelist[i] = DBExecute.getRuleID(i);
}

String[] rulename = new String[count];

for(int i = 0; i<count; ++i){
	rulename[i] = DBExecute.getRuleName(rulelist[i]);
}


int[] ruleclaimcount = new int[count];

for(int i = 0; i<count; ++i){
	ruleclaimcount[i] = DBExecute.getRuleClaimCount(rulelist[i], DBExecute.setRUN_ID());
}

%>

<br>
<table>
	<tr>
		<td>Rule ID</td>
		<td>Rule Name</td>
		<td># of Claims</td>		
	</tr>
	<% for (int i = 0; i< DBExecute.getRuleCount(); ++i){ %> 
	<tr>
		<td><%= rulelist[i] %></td> 
		<td><%= rulename[i] %></td>
		<td><%= ruleclaimcount[i] %></td>
	</tr><%}%>
</table>
<br><br>

</body>
</html>