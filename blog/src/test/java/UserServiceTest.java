
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import ua.edu.sumdu.blog.dao.PasswordResetTokenDao;
import ua.edu.sumdu.blog.dao.UserDao;
import ua.edu.sumdu.blog.model.PasswordResetToken;
import ua.edu.sumdu.blog.model.User;
import ua.edu.sumdu.blog.service.UserService;
import ua.edu.sumdu.blog.util.PasswordEncoder;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dedg
 */
public class UserServiceTest {
    UserService userService;

    @Mock
    UserDao userDao;

    @Mock
    PasswordResetTokenDao passwordResetTokenDao;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
        userService.setUserDao(userDao);
        userService.setPasswordResetTokenDao(passwordResetTokenDao);
        passwordEncoder = new PasswordEncoder();
    }

    @Test
    @DisplayName("Test user registration")
    void testRegister() {
        String username = "testuser";
        String password = "testpassword";
        String email = "testuser@example.com";
        doNothing().when(userDao).addUser(any(User.class));
        User registeredUser = userService.register(username, password, email);
        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        assertEquals(email, registeredUser.getEmail());
        verify(userDao, times(1)).addUser(any(User.class));
    }

    @Test
    @DisplayName("Test user authentication")
    void testAuthenticate() {
        String username = "testuser";
        String password = "testpassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        assertTrue(userService.authenticate(user, password));
        assertFalse(userService.authenticate(user, "wrongpassword"));
        assertFalse(userService.authenticate(null, password));
    }

    @Test
    @DisplayName("Test user logout")
    void testLogout() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        Cookie cookie = new Cookie("remember-me", "true");
        when(request.getSession(false)).thenReturn(session);
        when(request.getCookies()).thenReturn(new Cookie[] {cookie});
        userService.logout(request, response);
        verify(session, times(1)).invalidate();
        verify(response, times(1)).addCookie(any(Cookie.class));
    }

    @Test
    @DisplayName("Test username taken")
    void testIsUsernameTaken() {
        String username = "testuser";
        when(userDao.getUserByUsername(username)).thenReturn(new User());
        assertTrue(userService.isUsernameTaken(username));
        assertFalse(userService.isUsernameTaken("notakenusername"));
        verify(userDao, times(2)).getUserByUsername(anyString());
    }

    @Test
    @DisplayName("Test get password reset token")
    void testGetPasswordResetToken() {
        String token = "testtoken";
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        when(passwordResetTokenDao.getPasswordResetToken(token)).thenReturn(passwordResetToken);
        assertEquals(passwordResetToken, userService.getPasswordResetToken(token));
        assertNull(userService.getPasswordResetToken("wrongtoken"));
        verify(passwordResetTokenDao, times(2)).getPasswordResetToken(anyString());
    }

    @Test
    @DisplayName("Test set password reset token")
    void testSetPasswordResetToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        userService.setPasswordResetToken(passwordResetToken);
        verify(passwordResetTokenDao, times(1)).setPasswordResetToken(any(PasswordResetToken.class));
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userDao.getUserByEmail(email)).thenReturn(user);

        User result = userService.getUserByEmail(email);
        assertEquals(user, result);
        verify(userDao, times(1)).getUserByEmail(email);
    }

    @Test
    void testGetUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userDao.getUserByUsername(username)).thenReturn(user);

        User result = userService.getUserByUsername(username);
        assertEquals(user, result);
        verify(userDao, times(1)).getUserByUsername(username);
    }

    @Test
    void testUpdate() {
        User user = new User();
        userService.update(user);
        verify(userDao, times(1)).update(user);
    }
}
