package ca.ulaval.glo4003.architecture_logicielle.appConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import ca.ulaval.glo4003.architecture_logicielle.model.DepartmentEntry;
import ca.ulaval.glo4003.architecture_logicielle.model.EmployeeEntry;
import ca.ulaval.glo4003.architecture_logicielle.model.ModelFacade;
import ca.ulaval.glo4003.architecture_logicielle.model.ProjectEntry;
import ca.ulaval.glo4003.architecture_logicielle.model.TaskEntry;
import ca.ulaval.glo4003.architecture_logicielle.model.UserEntry;
import ca.ulaval.glo4003.architecture_logicielle.model.WeekEntry;
import ca.ulaval.glo4003.architecture_logicielle.web.viewmodels.CreatedWeekNumber;

@Configuration
public class AppConfiguration {
	
	ModelFacade facade = new ModelFacade(); 

	// Week Entry Repository
	public String getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return ((UserEntry) auth.getPrincipal()).getEmail();
	}

	public WeekEntry getUserWeekEntry(String email, int weekNumber, int yearNumber) {
		return facade.getWeekEntry(email, weekNumber, yearNumber);
	}
	
	public List<WeekEntry> getCurrentUserWeekEntries() {
		
		String currentUserEmail = getCurrentUser();
		return facade.getWeekEntries(currentUserEmail);
	}

	@Bean
	public ArrayList<WeekEntry> getAllWeekEntries() {
		
		return facade.getAllWeekEntries();
	}
	
	public void createWeekEntry(String email, int weekNumber, int year) {
		
		facade.createWeekEntry(email, weekNumber, year);
	}
	
	public void assignHoursToEmployeeWeek(String email, int weekNumber, int yearNumber, List<Double> hours) {
		facade.assignHoursToEmployeeWeek(email, weekNumber, yearNumber, hours);
	}
	
	public void assignKilometersToEmployeeWeek(String email, int weekNumber, int yearNumber, List<Integer> kilometers) {
		facade.assignKilometersToEmployeeWeek(email, weekNumber, yearNumber, kilometers);
	}
	
	public void assignExpensesToEmployeeWeek(String email, int weekNumber,int yearNumber, List<Double> expenses) {
		facade.assignExpensesToEmployeeWeek(email, weekNumber, yearNumber, expenses);
	}
	
	public void submitWeekEntry(String email, Integer week, Integer year) {
		facade.submitWeekEntry(email, week, year);
	}

	public void approvedWeekEntry(String email, Integer week, Integer year) {
		facade.approvedWeekEntry(email, week, year);
	}

	public void deniedWeekEntry(String email, Integer week, Integer year) {
		facade.deniedWeekEntry(email, week, year);
	}
	
	// Project
	@Bean
	public ArrayList<ProjectEntry> getAllProjects() {
		return facade.getAllProjects();
	}
	
	public ProjectEntry getProjectById(Integer id) {
		return facade.getProjectById(id);
	}
	
	public TaskEntry getTaskById(Integer id) {
		return facade.getTaskById(id);
	}
	
	public void addProject(ProjectEntry project) {
		facade.addProject(project);
	}
	
	public void addTask(Integer projectId, TaskEntry task) {
		facade.addTask(projectId, task);
	}
	
	public void updateProject(Integer projectId, ProjectEntry project) {
		facade.updateProject(projectId, project);
	}

	public void updateTask(Integer taskId, TaskEntry task) {
		facade.updateTask(taskId, task);
	}
	
	public void assignedTaskToEmployee(String email, List<String> assignTasks) {
		facade.assignedTaskToEmployee(email, assignTasks);
	}
	
	// Employee - User
	
	@Bean
	public ArrayList<UserEntry> getAllUsers() {
		return facade.getAllUsers();
	}

	@Bean
	public ArrayList<EmployeeEntry> getAllEmployees() {
		return facade.getAllEmployees();
	}
	
	public UserEntry getUserByEmail(String email) {
		return facade.getUserByEmail(email);
	}
	
	public void addUser(UserEntry user) {
		facade.addUser(user);
	}
	
	public void updateRateHourUser(UserEntry user, Double rateHour) {
		facade.updateUser(user , rateHour);
	}
	
	public void deleteUser(UserEntry user) {
		facade.deleteUser(user);
	}
	
	public List<TaskEntry> getTasksEmployee(String email) {
		return facade.getTasksEmployee(email);
	}
	
	public void updateTasksEmployee(List<TaskEntry> tasks,UserEntry user){
		facade.updateTasksEmployee(tasks, user);
	}
	
	public UserEntry createEmployee(ArrayList<String> tabuser){
		return facade.createEmployee(tabuser);
	}
	// Department Entry Repository
	
	@Bean
	public ArrayList<DepartmentEntry> getAllDepartmentEntries() {
		return null;
	}
	
	public DepartmentEntry getDepartmentEntryByName(String name) {
		return facade.getDepartmentEntryByName(name);
	}

	// Mailing Treatment
	
	public void aprovedSend(String to) throws Exception{
		facade.aprovedSend(to);
	}
	
	public void refusedSend(String to) throws Exception{
		facade.refusedSend(to);
	}
	
	

	public void createWeekEntry(CreatedWeekNumber createdWeekNumber){
		
		int year = getCurrentYear();
		int currentWeek = getCurrentWeekInYear();
		
		
		if (createdWeekNumber.getWeekNumber() == null) {
//			return "redirect:/weekEntriesList";
		}
		int weekNumber = createdWeekNumber.getWeekNumber();

		String intervalleString = ca.ulaval.glo4003.architecture_logicielle.util.Configuration.getConfig("FURTHER_WK_CREATION");
		int intervalle = 0;
		try {
			intervalle = Integer.parseInt(intervalleString);
		} catch (Exception e) {

		}
		if (weekNumber < currentWeek - intervalle) {
			year++;
		} else if (weekNumber > currentWeek + intervalle) {
			year--;
		}
		createWeekEntry(getCurrentUser(), weekNumber, year);
	}
	
	
	public Map<String, Integer> getNoCreatedWeeks(){

		Map<String, Integer> noCreatedWeeks = new LinkedHashMap<>();
		

		String intervalleString = ca.ulaval.glo4003.architecture_logicielle.util.Configuration.getConfig("FURTHER_WK_CREATION");
		int intervalle = 0;
		try {
			intervalle = Integer.parseInt(intervalleString);
		} catch (Exception e) {

		}

		if (intervalle > 25) {
			intervalle = 25;
		}
		int weekNb = getCurrentWeekInYear() - intervalle;
		int year = getCurrentYear();
		int weeksInCurrentYear = getNumberWeeksInYear(year);
		if (weekNb <= 0) {
			year--;
			weeksInCurrentYear = getNumberWeeksInYear(year);
			weekNb += weeksInCurrentYear;
		}
		for (int i = 0; i <= intervalle * 2; i++) {
			if (getUserWeekEntry(getCurrentUser(), weekNb, year) == null) {
				noCreatedWeeks.put("" + weekNb, weekNb);
			}
			weekNb++;
			if (weekNb > weeksInCurrentYear) {
				year++;
				weekNb = 1;
				weeksInCurrentYear = getNumberWeeksInYear(year);
			}
		}
		
		return noCreatedWeeks;
	}
	
	public int getCurrentWeekInYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public int getNumberWeeksInYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.set(year, Calendar.DECEMBER, 31);
		int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
		while (weekNumber <= 1) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
		}
		return weekNumber;
	}
}
