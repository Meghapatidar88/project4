<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.MedicationCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Medication Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '2020:2025',
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.MedicationBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.MEDICATION_CTL%>" method="post">




			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Medication </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Medication </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
				
				<%
				
				  Map map= (Map)request.getAttribute("medication");
				
				%>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<tr>
					<th align="left">Full Name <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="fullName"
						placeholder="Enter Full Name" size="26"
						value="<%=DataUtility.getStringData(bean.getFullName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("fullName", request)%></font></td>

				</tr>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th align="left">Illness <span style="color: red">*</span> :
					</th>
					<td>
						<%
							String hlist = HTMLUtility.getList2("illness", DataUtility.getStringData(bean.getIllness()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("illness", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">PrescriptionDate Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="prescriptionDate"
						placeholder="Enter Prescription Date" size="26" id="udatee"
						value="<%=DataUtility.getDateString(bean.getPrescriptionDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("prescriptionDate", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Dosage <span style="color: red">*</span> :
					</th>
					<td><input onkeypress="return isNumberKey(event)" type="text"
						name="dosage" placeholder="Enter Dosage"
						style="width: 208px"
						value="<%=(DataUtility.getStringData(bean.getDosage()).equals("0") ? ""
					: DataUtility.getStringData(bean.getDosage()))%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dosage", request)%></font></td>
				</tr>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=MedicationCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=MedicationCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=MedicationCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=MedicationCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>