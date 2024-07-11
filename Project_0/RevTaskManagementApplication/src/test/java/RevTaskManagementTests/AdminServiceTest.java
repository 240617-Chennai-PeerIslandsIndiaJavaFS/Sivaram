package RevTaskManagementTests;

import org.example.dao.ProjectDAO;
import org.example.dao.TeamDetailDAO;
import org.example.dao.UserDAO;
import org.example.model.Client;
import org.example.model.Project;
import org.example.model.User;
import org.example.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminServiceTest {

    public static UserDAO userDAO;
    public static ProjectDAO projectDAO;
    public static TeamDetailDAO teamDetailDAO;
    public static AdminService mockAdmin;

    private User user;
    private Client client;
    private User projectManager;
    private Project project;
    private List<Project> projectList;

    @BeforeAll
    public static void setUpClass() {
        userDAO = mock(UserDAO.class);
        projectDAO = mock(ProjectDAO.class);
        teamDetailDAO = mock(TeamDetailDAO.class);
        mockAdmin = new AdminService(userDAO, projectDAO, teamDetailDAO);
    }

    @BeforeEach
    public void setUp() {
        user = new User(1, "siva", "siva@example.com", "password123", "User", "Active", "3243242344");

        client = new Client(1, "abc corporation", "client@example.com", "BigTech company", "123 Beach Street", "chennai", "5683967123");

        projectManager = new User(1, "Raja", "pm@example.com", "password456", "ProjectManager", "Active", "32147965632");

        project = new Project(
                101,
                "Project Alpha",
                "Developing project management App",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 12, 31),
                client,
                projectManager
        );

        projectList = new ArrayList<>();
        projectList.add(project);
        projectList.add(new Project(
                102,
                "Project Beta",
                "Developing for client Web Application",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2025, 1, 31),
                client,
                projectManager
        ));
    }

    @Test
    public void testRegisterUser() {
        when(userDAO.create(user)).thenReturn(true);

        boolean result = mockAdmin.registerUser(user);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetUserByName() {
        String name = "siva";
        when(userDAO.getByName(name)).thenReturn(user);

        User result = mockAdmin.getUserByName(name);

        Assertions.assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        when(userDAO.update(user)).thenReturn(true);

        boolean result = mockAdmin.updateUser(user);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "ram", "ram@example.com", "password", "User", "Active", "1234567890"));
        userList.add(new User(2, "raja", "raja@example.com", "password", "User", "Active", "9876543210"));
        when(userDAO.getAll()).thenReturn(userList);

        List<User> result = mockAdmin.getAllUser();

        Assertions.assertEquals(userList.size(), result.size());
        Assertions.assertEquals(userList, result);
    }

    @Test
    public void testCreateProject() {
        when(projectDAO.create(project)).thenReturn(true);

        boolean result = mockAdmin.createProject(project);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetProject() {
        int projectId = 101;
        when(projectDAO.get(projectId)).thenReturn(project);

        Project result = mockAdmin.getProject(projectId);

        Assertions.assertEquals(project, result);
    }

    @Test
    public void testGetAllProjects() {
        when(projectDAO.getAll()).thenReturn(projectList);

        List<Project> result = mockAdmin.getAllProjects();

        Assertions.assertEquals(projectList.size(), result.size());
        Assertions.assertEquals(projectList, result);
    }

    @Test
    public void testUpdateProject() {
        project.setProjectName("Project Alpha Updated");
        project.setProjectDescription("Developing an project management with new features (attractive UI)");

        when(projectDAO.update(project)).thenReturn(true);

        boolean result = mockAdmin.updateProject(project);

        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteProject() {
        int projectId = 101;
        when(projectDAO.delete(projectId)).thenReturn(true);

        boolean result = mockAdmin.deleteProject(projectId);

        Assertions.assertTrue(result);
    }
}

