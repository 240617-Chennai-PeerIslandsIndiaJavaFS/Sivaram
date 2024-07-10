package RevTaskManagementTests;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginUser_ValidCredentials() {
        String email = "siva@example.com";
        String password = "password";
        User user = new User(1, "siva", email, password, "User", "Active", "1234567890");
        when(userDAO.getByEmail(email)).thenReturn(user);

        User result = userService.loginUser(email, password);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

}
