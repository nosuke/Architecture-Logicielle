<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div id="logged-info">
	<ul>
		<sec:authorize access="hasRole('MANAGER')">
			<li><a href="/employeeList">Liste des employ�s</a></li>
			<li><a href="/projectList">Liste des projets</a></li>
		</sec:authorize>
		<sec:authorize access="hasRole('EMPLOYEE')">
			<li><a href="/vehicleExpenses">D�penses pour les v�hicules</a></li>
			<li><a href="/employeeExpenses">D�penses des employ�s</a></li>
			<li><a href="/workingHours">Heures r�alis�es</a></li>
		</sec:authorize>
	</ul>
</div>