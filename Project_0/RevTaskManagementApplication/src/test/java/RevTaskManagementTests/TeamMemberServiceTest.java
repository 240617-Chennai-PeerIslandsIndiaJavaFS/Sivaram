package RevTaskManagementTests;

import org.example.dao.*;
import org.example.model.*;
import org.example.service.TeamMemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeamMemberServiceTest {

    public static UserDAO userDAO;
    public static TeamDetailDAO teamDetailDAO;
    public static TimelineDAO timelineDAO;
    public static MessageDAO messageDAO;
    public static TeamMemberService teamMemberService;

    private User teamMember;
    private Task task;
    private Message message;
    private List<TeamDetail> teamDetailList;
    private List<Integer> projectManagerAndTeamList;

    @BeforeAll
    public static void setUpClass() {
        userDAO = mock(UserDAO.class);
        teamDetailDAO = mock(TeamDetailDAO.class);
        timelineDAO = mock(TimelineDAO.class);
        messageDAO = mock(MessageDAO.class);
        teamMemberService = new TeamMemberService(userDAO,teamDetailDAO,timelineDAO,messageDAO);
    }

    @BeforeEach
    public void setUp() {
        teamMember = new User(2, "Nithish", "nithish@example.com", "password567", "User", "Active", "9876543210");

        task = new Task(1, "task1", "Implement new feature A for project", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 12, 31), new Milestone(1, "In Progress", "Description"));


        message = new Message();
        message.setMessageId(1);
        message.setMessageDescription("Progress has been updated");

        Client client = new Client(1, "abc Corporation", "abc@example.com", "Description", "123 Beach St", "Chennai", "7863145236");

        Project project = new Project(101, "Project Alpha", "Description", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31), client, new User(1, "Project Manager", "pm@example.com", "password", "ProjectManager", "Active", "78786863214"));

        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("Development Team");
        team.setProject(project);

        teamDetailList = new ArrayList<>();
        teamDetailList.add(new TeamDetail(team, teamMember, task));

        projectManagerAndTeamList = new ArrayList<>();
        projectManagerAndTeamList.add(1);
        projectManagerAndTeamList.add(2);
    }

    @Test
    public void testGetProjectDetails() {
        when(teamDetailDAO.getProjectDetailsByUserId(teamMember)).thenReturn(teamDetailList);

        List<TeamDetail> result = teamMemberService.getProjectDetails(teamMember);

        Assertions.assertEquals(teamDetailList.size(), result.size());
        Assertions.assertEquals(teamDetailList, result);
    }

    @Test
    public void testUpdateTask() {
        when(timelineDAO.updateTask(teamMember, task)).thenReturn(true);

        boolean result = teamMemberService.updateTask(teamMember, task);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetProjectManagerAndTeam() {
        int projectId = 101;
        when(teamDetailDAO.getProjectManagerAndTeam(teamMember, projectId)).thenReturn(projectManagerAndTeamList);

        List<Integer> result = teamMemberService.getProjectManagerAndTeam(teamMember, projectId);

        Assertions.assertEquals(projectManagerAndTeamList.size(), result.size());
        Assertions.assertEquals(projectManagerAndTeamList, result);
    }

    @Test
    public void testUpdateProgress() {
        when(messageDAO.create(message)).thenReturn(true);

        boolean result = teamMemberService.updateProgress(message);

        Assertions.assertTrue(result);
    }
}
