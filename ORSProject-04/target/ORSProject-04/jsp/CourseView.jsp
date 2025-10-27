<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.CourseCtl"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Course</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.COURSE_CTL %>" method="post">
	<%@ include file="Header.jsp" %>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CourseBean" scope="request"></jsp:useBean>
	
	<div align="center">
	<h1>Add Course</h1>
	<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>
			</div>
			
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">
			
			<table>
			
			<tr>
					<th align="left">Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				
			<tr>
					<th align="left">Duration<span style="color: red">*</span></th>
					<td><input type="text" name="duration"
						placeholder="Enter Duration"
						value="<%=DataUtility.getStringData(bean.getDuration())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("duration", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td><input type="text" name="description"
						placeholder="Enter Description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td align="left" colspan="2">
					<input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>"> 
					<input type="submit" name="operation" value="<%=CourseCtl.OP_RESET%>">
					</td>
					
				</tr>
				<tr>
				<th></th>
				<td></td>
				<td></td>
				</tr>	
			</table>
			
	</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>

</html>