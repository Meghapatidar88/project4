<%@page import="com.rays.pro4.Model.ClientModel"%>
<%@page import="com.rays.pro4.Bean.ClientBean"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.ClientListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />

<title>Client List</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Utilities.js"></script>

<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '2020:2025',
		//  mindefaultDate : "01-01-1962"
		});
	});
</script>

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ClientBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.CLIENT_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>Client List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="limegreen"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<%
			Map map= (Map)request.getAttribute("client");
			
				List clist = (List) request.getAttribute("FullName");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);

				Iterator<ClientBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>
					<td align="center"><label>FullName</font> :
					</label> <input type="text" name="fullName" placeholder="Enter Full Name"
					oninput=" handleLetterInput(this, 'fullNameError', 8)"
						id="fullNameError"
						
						value="<%=ServletUtility.getParameter("fullName", request)%>">

						<label></font> </label> <%-- <%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleId()), rlist) %> --%>

						<label>AppointmentDate</font> :
					</label><input type="text" name="appointmentDate" id="udate"
						readonly="readonly" size="25" placeholder="Enter appointmentDate"
						value="<%=ServletUtility.getParameter("appointmentDate", request)%>">
						
					<td align="center"><label>Phone</font> :
					</label> <input type="text" name="phone" placeholder="Enter phone"
						value="<%=ServletUtility.getParameter("phone", request)%>">
						
					<td align="center"><label>Illness</font> :
					</label> <%=HTMLUtility.getList2("illness",DataUtility.getStringData(bean.getIllness()) , map) %>
						<%-- value="<%=ServletUtility.getParameter("illness", request)%>">
 --%>


						<input type="submit" name="operation"
						value="<%=ClientListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=ClientListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: skyblue">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>FullName</th>
					<th>AppointmentDate</th>
					<th>Phone</th>
					<th>Illness</th>
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
				%>


				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getFullName()%></td>
					<td><%=bean.getAppointmentDate()%></td>
					<td><%=bean.getPhone()%></td>
					<td><%=map.get(bean.getIllness())%></td>
					<td><a href="ClientCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=ClientListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=ClientListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=ClientListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=ClientListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=ClientListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=ClientListCtl.OP_BACK%>"></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<%
				}
			%>
		
	</form>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>

	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>
