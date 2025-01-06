<%@page import="com.rays.pro4.Model.FollowUpModel"%>
<%@page import="com.rays.pro4.Bean.FollowUpBean"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.FollowUpListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />

<title>FollowUp List</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.FOLLOWUP_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>FollowUp List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="limegreen"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<%
				Map map = (Map) request.getAttribute("followup");
			%>
			

			<%

				/* List clist = (List) request.getAttribute("FullName"); */

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
			
			    Iterator<FollowUpBean> it = list.iterator();
				

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<td align="center"><label>Client</font> :
					
					</label>
						<%
							String hlist = HTMLUtility.getList2("client", DataUtility.getStringData(bean.getClient()), map);
						%> <%=hlist%>
				
				
				<td align="center"><label>Physician</font> :
					</label> <input type="text" name="physician" placeholder="Enter physician"
						value="<%=ServletUtility.getParameter("physician", request)%>">
					
					
					<label>AppointmentDate</font> :
					</label><input type="text" name="appointmentDate" id="udate"
						readonly="readonly" size="25" placeholder="Enter appointmentDate"
						value="<%=ServletUtility.getParameter("appointmentDate", request)%>">
					
				
					
					<td align="center"><label>Charges</font> :
					</label> <input type="text" name="illness" placeholder="Enter charges"
						value="<%=ServletUtility.getParameter("charges", request)%>">



						<input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=FollowUpListCtl.OP_RESET%>">
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
					<th>Client</th>
					<th>Physician</th>
					<th>AppointmentDate</th>
					<th>Charges</th>
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
					<td><%=bean.getClient()%></td>
					<td><%=bean.getPhysician()%></td>
					<td><%=bean.getAppointmentDate()%></td>
					<td><%=bean.getCharges()%></td>
                    <td><a href="FollowUpCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=FollowUpListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=FollowUpListCtl.OP_BACK%>"></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
			<input type="hidden" name="pageSize" value="<%=pageSize%>">
			
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
