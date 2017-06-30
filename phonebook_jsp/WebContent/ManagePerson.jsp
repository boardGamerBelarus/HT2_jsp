<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page import="app.Person"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ include file="/Styles.css"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Управление данными о человеке</title>

</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Person person = new Person();
		String error_message = "";

		String current_action_result = "";

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		if (request.getAttribute("person") != null) {
			person = (Person) request.getAttribute("person");
		}

		error_message = jsp_parameters.get("error_message");
		current_action_result = jsp_parameters.get("current_action_result");
	%>

	<form action="<%=request.getContextPath()%>/" method="post">
		<input type="hidden" name="id" value="<%=person.getId()%>" />
		<table align="center" border="1" width="70%">
			<%
				if ((error_message != null) && (!error_message.equals(""))) {
			%>
			<tr>
				<td colspan="2" align="center"><span style="color: red"><%=error_message%></span></td>
			</tr>
			<%
				}
			%>

			<%
				if ((current_action_result != null) && (!current_action_result.equals(""))) {
			%>
			<tr>
				<td colspan="6" align="center"><%=jsp_parameters.get("current_action_result_label")%></td>
			</tr>
			<%
				}
			%>

			<tr>
				<td colspan="2" align="center">Информация о человеке</td>
			</tr>
			<tr>
				<td>Фамилия:</td>
				<td><input class="inputText" type="text" name="surname"
					value="<%=person.getSurname()%>" /></td>
			</tr>
			<tr>
				<td>Имя:</td>
				<td><input type="text" class="inputText" name="name"
					value="<%=person.getName()%>" /></td>
			</tr>
			<tr>
				<td>Отчество:</td>
				<td><input type="text" name="middlename" class="inputText"
					value="<%=person.getMiddlename()%>" /></td>
			</tr>

			<%
				if (jsp_parameters.get("phoneAreaPresent").equals("true")) {
			%>
			<tr>
				<td>Телефоны:</td>
				<td>
					<%
						if (person.getPhones().values() != null) {

								Iterator<Map.Entry<String, String>> it = person.getPhones().entrySet().iterator();
								while (it.hasNext()) {

									Map.Entry<String, String> pair = it.next();
					%>
					<div>
						<%
							out.write(pair.getValue() + "\n");
						%>

						<a style="margin-left: 2ex;"
							href="<%=request.getContextPath()%>/?action=editPhone&phoneId=<%=pair.getKey()%>&id=<%=person.getId()%>">Редактировать</a>
						<a style="margin-left: 2ex;"
							href="<%=request.getContextPath()%>/?action=deletePhone&phoneId=<%=pair.getKey()%>&id=<%=person.getId()%>">Удалить</a>

					</div> <%
 	}
 		}
 %>
					<div>
						<a
							href="<%=request.getContextPath()%>/?action=addPhone&id=<%=person.getId()%>">Добавить</a>
					</div>

				</td>
			</tr>
			<%
				//
				}
			%>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="<%=jsp_parameters.get("next_action")%>" class="button"
					value="<%=jsp_parameters.get("next_action_label")%>" />
					<div>
						<a href="<%=request.getContextPath()%>">Вернуться к списку</a>
					</div></td>
			</tr>
		</table>
	</form>
</body>
</html>