<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>

<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Registration</title>
</head>
<body>
	<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>
	
	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color:navy">User Registration</h1>
		
		<div style="height: 15px; margin-bottom: 12px">
		
		<h3 align="center">
		<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
		</h3>
		
		<h3 align="center">
		<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		
		</div>
		
		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
		<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">
		
		
		<table>
		
		<tr>
		<th align="left">First Name<span style="color:red">*</span></th>
		<td><input type="text" name="firstName" placeholder="Enter first name" value="<%= (bean != null) ? DataUtility.getStringData(bean.getFirstName()) : "" %>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName",request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Last Name<span style="color:red">*</span></th>
		<td><input type="text" name="lastName" placeholder="Enter last name" value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName",request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Login Id<span style="color:red">*</span></th>
		<td><input type="text" name="login" placeholder="Enter login Id" value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login",request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Password<span style="color:red">*</span></th>
		<td><input type="text" name="password" placeholder="Enter Password" value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password",request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Confirm Password<span style="color:red">*</span></th>
		<td><input type="text" name="confirmPassword" placeholder="Enter Confirm Password" value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmPassword",request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Date of Birth<span style="color:red">*</span></th>
		<td><input type="text" name="dob" id="udate" placeholder="Select date of birth" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
		<td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob",request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Gender<span style="color:red">*</span></th>
		<td>
		<%
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("Female","Female");
		map.put("Male","Male");
		String htmlList=HTMLUtility.getList("gender", bean.getGender(), map);
		%><%=htmlList%>
		</td>
		<td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request) %></font></td>
		</tr>
		
		<tr>
		<th align="left">Mobile No<span style="color:red">*</span>
		<td><input type="text" name="mobileNo" placeholder="Enter mobile number" maxlength=10 value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
		<td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request) %></font></td>
		</tr>		
		
		<tr>
					<th></th>
					<td></td>
		</tr>
		
		<tr>
		<th></th>
		<td align="left" colspan="2"><input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGNUP%>">&nbsp;
		<input type="submit" name="operation" value="<%=BaseCtl.OP_RESET%>">
		</td>
		</tr>
		
		</table>
	</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>