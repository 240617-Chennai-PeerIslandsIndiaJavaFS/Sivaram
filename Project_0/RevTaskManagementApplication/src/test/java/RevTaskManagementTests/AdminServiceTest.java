package RevTaskManagementTests;

import org.example.dao.ProjectDAO;
import org.example.dao.TeamDetailDAO;
import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private ProjectDAO projectDAO;

    @Mock
    private TeamDetailDAO teamDetailDAO;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        User user = new User(1, "siva", "siva@example.com", "password", "User", "Active", "1234567890");
        when(userDAO.create(user)).thenReturn(true);

        boolean result = adminService.registerUser(user);

        Assertions.assertTrue(result);

    }

    @Test
    public void testGetUserByName() {
        String name = "sakthi";
        User user = new User(1, name, "sakthi@example.com", "password", "User", "Active", "1234567890");
        when(userDAO.getByName(name)).thenReturn(user);

        User result = adminService.getUserByName(name);

        Assertions.assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1, "Karthi", "karthi@example.com", "password", "User", "Active", "1234567890");
        when(userDAO.update(user)).thenReturn(true);

        boolean result = adminService.updateUser(user);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "ram", "ram@example.com", "password", "User", "Active", "1234567890"));
        userList.add(new User(2, "raja", "raja@example.com", "password", "User", "Active", "9876543210"));
        when(userDAO.getAll()).thenReturn(userList);

        List<User> result = adminService.getAllUser();

        Assertions.assertEquals(userList.size(), result.size());
        Assertions.assertEquals(userList, result);
    }


}
