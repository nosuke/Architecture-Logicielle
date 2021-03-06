<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<jsp:include page="_meta.jsp" />
</head>
<body>
<jsp:include page="_header.jsp" />
<h1>
	Liste des utilisateurs
</h1>

<div class="container">
	<div class="row">
		<table class="table col-lg-8">
			<thead>
				<tr>
					<th>Nom</th>
					<th>Courriel</th>
					<th>R�le</th>
					<th>T�ches</th>
					<th>Taux horaire</th>
					<th colspan="4"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="entry" items="${entries}">
					<c:url var="entryUrl" value="manager/${entry.email}" />
					<tr>
						<td>${entry.name}</td>
						<td>${entry.email}</td>
						<td><%@include file="_roleTranslate.jsp"%></td>
						<td>
							<a href="/${entryUrl}/assignTasks">Assigner des t�ches</a>
						</td>
						<td>${entry.rateHour}</td>
						<td>
							<a href="/${entryUrl}/updateRateHour">Modifier le taux horaire</a>				
						</td>
						<td>
							<a href="/${entryUrl}/delete">Supprimer l'employ�</a>				
						</td>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="row">
		<a href="<c:url value="/manager/addEmployee" />" class="col-lg-offset=2 btn btn-primary">Ajouter un employ�</a>
	</div>
</div>


</body>
</html>
