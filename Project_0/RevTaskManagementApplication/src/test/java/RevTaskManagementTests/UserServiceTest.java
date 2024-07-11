package RevTaskManagementTests;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private static UserDAO userDAO;

    private  static UserService userService;

    private User user;

    @BeforeAll
    public static void setUpClass() {
        userDAO = mock(UserDAO.class);
        userService = new UserService(userDAO);
    }

    @BeforeEach
    public void setUp(){
        User user = new User(1, "siva", "siva@example.com", "password456", "User", "Active", "1234567890");
    }

    @Test
    public void testLoginUser_ValidCredentials() {
        String email = "siva@example.com";
        String password = "password456";
        when(userDAO.getByEmail(email)).thenReturn(user);

        User result = userService.loginUser(email, password);
        Assertions.assertEquals(user, result);
    }

}
