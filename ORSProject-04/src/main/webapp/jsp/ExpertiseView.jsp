<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ExpertiseCtl" %>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>

<title>Add Expertise</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp" %>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ExpertiseBean" scope="request"></jsp:useBean>
	<form action="<%=ORSView.EXPERTISE_CTL %>" method="post">
	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy">
		<%
		if(bean!=null && bean.getId()>0){
		%>
		Update
		<%}else{ %>
		Add
		<%} %>
		Expertise</h1>
		
		<div  style="height: 15px; margin-bottom: 12p">
			<h3 align="center"><font color="green"><%= ServletUtility.getSuccessMessage(request)%></font></h3>
			<h3 align="center"><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></h3>
		</div>
		
			<input type="hidden" name="id" value="<%=bean.getId()%>">
		    <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

		<table>
		
		<tr>
		<th align="left">Name<span style="color:red">*</span></th>
		<td align="center"><input type="text" name="name" placeholder="Enter Expertise Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
		<td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Description<span style="color:red">*</span></th>
		<td align="center"><textarea style="width: 170px; resize: none;" name="description" rows="3" placeholder="Enter short description"><%=DataUtility.getStringData(bean.getDescription())%></textarea></td>
		<td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request) %></font></td>
		</tr>
		
		<tr>
                    <th></th>
                    <td></td>
  			<%
  			if(bean!=null && bean.getId()>0){
  			%>
  			<td align="left" colspan="2">
  				<input type="submit" name="operation" value="<%=ExpertiseCtl.OP_UPDATE%>">
  				<input type="submit" name="operation" value="<%=ExpertiseCtl.OP_CANCEL%>">
  			</td>
  			<%}else{ %>
  			<td align="left" colspan="2">
  				<input type="submit" name="operation" value="<%=ExpertiseCtl.OP_SAVE%>">
  				<input type="submit" name="operation" value="<%=ExpertiseCtl.OP_RESET%>">
  				</td>
			<%} %>
  		</tr>
  		
  		</table>
	</div>
	</form>	
</body>
</html>