package RevTaskManagementTests;

import org.example.dao.*;
import org.example.model.*;
import org.example.service.ProjectManagerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectManagerServiceTest {
    public static UserDAO userDAO;
    public static ClientDAO clientDAO;
    public static ProjectDAO projectDAO;
    public static TeamDAO teamDAO;
    public static TeamDetailDAO teamDetailDAO;
    public static TaskDAO taskDAO;
    public static MilestoneDAO milestoneDAO;
    public static MessageDAO messageDAO;
    public static ProjectManagerService mockProjectManagerService;

    private User projectManager;
    private Client client;
    private Project project;
    private Team team;
    private Task task;
    private List<Project> projectList;
    private List<Milestone> milestoneList;
    private List<Task> taskList;
    private List<Team> teamList;
    private List<TeamDetail> teamDetailList;
    private List<User> userList;
    private List<Client> clientList;

    @BeforeAll
    public static void setUpClass() {
        userDAO = mock(UserDAO.class);
        clientDAO = mock(ClientDAO.class);
        projectDAO = mock(ProjectDAO.class);
        teamDAO = mock(TeamDAO.class);
        teamDetailDAO = mock(TeamDetailDAO.class);
        taskDAO = mock(TaskDAO.class);
        milestoneDAO = mock(MilestoneDAO.class);
        messageDAO = mock(MessageDAO.class);
        mockProjectManagerService = new ProjectManagerService(userDAO, clientDAO, projectDAO, teamDAO, teamDetailDAO, taskDAO, milestoneDAO, messageDAO);
    }

    @BeforeEach
    public void setUp() {
        projectManager = new User(1, "Raja", "pm@example.com", "password456", "ProjectManager", "Active", "784321803");

        client = new Client(1, "xyz corporation", "xyz@example.com", "Description", "123 Beach St", "Madurai", "465412398");

        project = new Project(
                101,
                "Project Alpha",
                "Developing for project management",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 12, 31),
                client,
                projectManager
        );

        team = new Team(1, "Development Team", projectManager, project);


        task = new Task(1, "Implement Feature Message", "Implement new message A for project", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 12, 31), new Milestone(1, "In Progress", "Description"));


        projectList = new ArrayList<>();
        projectList.add(project);

        milestoneList = new ArrayList<>();
        milestoneList.add(new Milestone(1, "Started", "Description for Milestone"));

        taskList = new ArrayList<>();
        taskList.add(task);

        teamList = new ArrayList<>();
        teamList.add(team);

        clientList = new ArrayList<>();
        clientList.add(client);

        teamDetailList = new ArrayList<>();
        teamDetailList.add(new TeamDetail(team, new User(2, "Ram", "ram@example.com", "password231", "ProjectManager", "Active", "9876543210"), task));

        userList = new ArrayList<>();
        userList.add(new User(2, "sakthi", "sakthi@example.com", "password768", "ProjectManger", "Active", "9876543210"));
    }

    @Test
    public void testPasswordReset() {
        when(userDAO.update(projectManager)).thenReturn(true);

        boolean result = mockProjectManagerService.passwordReset(projectManager);

        Assertions.assertTrue(result);
    }

    @Test
    public void testCreateClient() {
        when(clientDAO.create(client)).thenReturn(true);

        boolean result = mockProjectManagerService.createClient(client);

        Assertions.assertTrue(result);
    }


    @Test
    public void testGetClientByName() {
        String clientName = "xyz corporation";
        when(clientDAO.getClientByName(clientName)).thenReturn(client);

        Client result = mockProjectManagerService.getClientByName(clientName);

        Assertions.assertEquals(client, result);
    }


    @Test
    public void testUpdateClient() {
        when(clientDAO.update(client)).thenReturn(true);

        boolean result = mockProjectManagerService.updateClient(client);

        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteClient() {
        when(clientDAO.delete(client.getClientId())).thenReturn(true);

        boolean result = mockProjectManagerService.deleteClient(client);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetAllClients() {
        when(projectDAO.getAllClientsByManager(projectManager)).thenReturn(clientList);

        List<Client> result = mockProjectManagerService.getAllClients(projectManager);

        Assertions.assertEquals(clientList.size(), result.size());
        Assertions.assertEquals(clientList, result);
    }

    @Test
    public void testGetAllProjectsByProjectManager() {
        when(projectDAO.getAllProjectsByProjectManager(projectManager)).thenReturn(projectList);

        List<Project> result = mockProjectManagerService.getAllProjectsByProjectManager(projectManager);

        Assertions.assertEquals(projectList.size(), result.size());
        Assertions.assertEquals(projectList, result);
    }


    @Test
    public void testCreateTeam() {
        when(teamDAO.create(team)).thenReturn(true);

        boolean result = mockProjectManagerService.createTeam(team);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetTeam() {
        int teamId = 1;
        when(teamDAO.get(teamId)).thenReturn(team);

        Team result = mockProjectManagerService.getTeam(teamId);

        Assertions.assertEquals(team, result);
    }

    @Test
    public void testGetAllTeamsByProjectManager() {
        when(teamDAO.getAllTeamsByProjectManager(projectManager)).thenReturn(teamList);

        List<Team> result = mockProjectManagerService.getAllTeamsByProjectManager(projectManager);

        Assertions.assertEquals(teamList.size(), result.size());
        Assertions.assertEquals(teamList, result);
    }

    @Test
    public void testUpdateTeam() {
        when(teamDAO.update(team)).thenReturn(true);

        boolean result = mockProjectManagerService.updateTeam(team);

        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteProject() {
        int teamId = 1;
        when(teamDAO.delete(teamId)).thenReturn(true);

        boolean result = mockProjectManagerService.deleteProject(teamId);

        Assertions.assertTrue(result);
    }


    @Test
    public void testGetAllMilestone() {
        when(milestoneDAO.getAll()).thenReturn(milestoneList);

        List<Milestone> result = mockProjectManagerService.getAllMilestone();

        Assertions.assertEquals(milestoneList.size(), result.size());
        Assertions.assertEquals(milestoneList, result);
    }

    @Test
    public void testCreateTask() {
        when(taskDAO.create(task)).thenReturn(true);

        boolean result = mockProjectManagerService.createTask(task);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetAllTaskByManager() {
        when(teamDetailDAO.getAllTaskByProjectManager(projectManager)).thenReturn(taskList);

        List<Task> result = mockProjectManagerService.getAllTaskByManager(projectManager);

        Assertions.assertEquals(taskList.size(), result.size());
        Assertions.assertEquals(taskList, result);
    }

    @Test
    public void testGetAllTask() {
        when(taskDAO.getAll()).thenReturn(taskList);

        List<Task> result = mockProjectManagerService.getAllTask();

        Assertions.assertEquals(taskList.size(), result.size());
        Assertions.assertEquals(taskList, result);
    }

    @Test
    public void testDeleteTask() {
        int taskId = 1;
        when(taskDAO.delete(taskId)).thenReturn(true);

        boolean result = mockProjectManagerService.deleteTask(taskId);

        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteTaskFromTeamDetails() {
        int taskId = 1;
        when(teamDetailDAO.deleteByTaskID(taskId)).thenReturn(true);

        boolean result = mockProjectManagerService.deleteTaskFromTeamDetails(taskId);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetAllTeamMember() {
        int teamId = 1;
        when(teamDetailDAO.getAllByTeamMemberById(teamId)).thenReturn(teamDetailList);

        List<TeamDetail> result = mockProjectManagerService.getAllTeamMember(teamId);

        Assertions.assertEquals(teamDetailList.size(), result.size());
        Assertions.assertEquals(teamDetailList, result);
    }

    @Test
    public void testGetAllUniqueTeamMember() {
        int teamId = 1;
        when(teamDetailDAO.getAllUniqueTeamMember(teamId)).thenReturn(teamDetailList);

        List<TeamDetail> result = mockProjectManagerService.getAllUniqueTeamMember(teamId);

        Assertions.assertEquals(teamDetailList.size(), result.size());
        Assertions.assertEquals(teamDetailList, result);
    }



    @Test
    public void testGetAllUsers() {
        when(userDAO.getAllNormalUser()).thenReturn(userList);

        List<User> result = mockProjectManagerService.getAllUsers();

        Assertions.assertEquals(userList.size(), result.size());
        Assertions.assertEquals(userList, result);
    }

    @Test
    public void testAddTeamMember() {
        TeamDetail teamDetail = new TeamDetail(team, projectManager, task);
        when(teamDetailDAO.create(teamDetail)).thenReturn(true);

        boolean result = mockProjectManagerService.addTeamMember(teamDetail);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetTaskById() {
        int taskId = 1;
        when(taskDAO.get(taskId)).thenReturn(task);

        Task result = mockProjectManagerService.getTaskById(taskId);

        Assertions.assertEquals(task, result);
    }

    @Test
    public void testReassignTask() {
        TeamDetail teamDetail = new TeamDetail(team, projectManager, task);
        User newTeamMember = new User(3, "Ganesh", "ganesh@example.com", "password567", "User", "Active", "9876543210");
        when(teamDetailDAO.reassignTask(teamDetail, newTeamMember)).thenReturn(true);

        boolean result = mockProjectManagerService.reassignTask(teamDetail, newTeamMember);

        Assertions.assertTrue(result);
    }

    @Test
    public void testDisplayMessage() {
        when(messageDAO.displayMessage(projectManager)).thenReturn(new ArrayList<>());

        List<Message> result = mockProjectManagerService.displayMessage(projectManager);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }
}
