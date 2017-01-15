
<%@ page import="com.revascent.JSPTest.*"  %>
<html>

<body>

Let's test out if this works.<br>


<br>
Number of Rules is: <%= DBExecute.getRuleCountAll() %>
<br>

<br>
<%= DBExecute.getSQL() %>
<br><br>
<!-- Display the table and the active rules list here -->

<%
int count = DBExecute.getRuleCount();%>
<br>Active rule count is: <%= count %>
<br><br>

Active Rules Table

<% 
int[] rulelist = new int[count];

for(int i = 0; i<count; ++i){
	rulelist[i] = DBExecute.getRuleID(i);
}

String[] rulename = new String[count];

for(int i = 0; i<count; ++i){
	rulename[i] = DBExecute.getRuleName(rulelist[i]);
}

%>
<br>
<table>
	<tr>
		<td>Rule ID</td>
		<td>Rule Name</td>
		<td>Rule Status</td>		
	</tr>
	<% for (int i = 0; i< DBExecute.getRuleCount(); ++i){ %> 
	<tr>
		<td><%= rulelist[i] %></td> 
		<td><%= rulename[i] %></td>
		<td>Active</td>
		<!-- <td><form action="demo_form.asp" method="get"> <button type="submit" formaction="RuleResult.jsp">Submit</button></td> -->
	</tr><%}%>
</table>
<br><br>Inserting new line to test Jenkins.<br>
<form action="RuleResult.jsp" method="get"> <button type="submit" formaction="RuleResult.jsp">Run All Rules</button>

</body>


</html>