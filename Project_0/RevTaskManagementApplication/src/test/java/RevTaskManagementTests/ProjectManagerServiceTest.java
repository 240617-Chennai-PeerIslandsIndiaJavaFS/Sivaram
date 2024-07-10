package RevTaskManagementTests;


import org.example.dao.*;
import org.example.model.*;
import org.example.service.ProjectManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectManagerServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private ClientDAO clientDAO;

    @Mock
    private ProjectDAO projectDAO;

    @Mock
    private TeamDAO teamDAO;

    @Mock
    private TeamDetailDAO teamDetailDAO;

    @Mock
    private TaskDAO taskDAO;

    @Mock
    private MilestoneDAO milestoneDAO;

    @Mock
    private MessageDAO messageDAO;

    @InjectMocks
    private ProjectManagerService projectManagerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPasswordReset() {
        User user = new User();
        user.setUserId(1);
        user.setUserPassword("newpassword");

        when(userDAO.update(user)).thenReturn(true);


        boolean result = projectManagerService.passwordReset(user);

        assertTrue(result);

    }

    @Test
    public void testCreateClient() {
        Client client = new Client();
        client.setClientName("Test Client");
        client.setClientEmail("client@example.com");
        client.setAddress("address");
        client.setCity("city");
        client.setPhone("3647234293");

        when(clientDAO.create(client)).thenReturn(true);

        boolean result = projectManagerService.createClient(client);

        assertTrue(result);
    }

    @Test
    public void testGetClientByName() {
        String clientName = "Test Client";
        Client client = new Client();
        client.setClientName(clientName);


        when(clientDAO.getClientByName(clientName)).thenReturn(client);

        Client actualClient = projectManagerService.getClientByName(clientName);

        assertNotNull(actualClient);
        assertEquals(clientName, actualClient.getClientName());
    }

}

