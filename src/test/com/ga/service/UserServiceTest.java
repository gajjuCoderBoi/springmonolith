package com.ga.service;

import com.ga.config.JwtUtil;
import com.ga.dao.UserDao;
import com.ga.entity.User;
import com.ga.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private User user;

    @InjectMocks
    private UserRole userRole;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void initializeDummyUser() {
        userRole.setName("ROLE_ADMIN");

        user.setUserId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
        user.setUserRole(userRole);
    }

    @Test
    public void listUsers() {
        // TODO
    }

    @Test
    public void signup_ReturnsJwt_Success() {
        String expectedToken = "12345";

        when(userDao.signup(any())).thenReturn(user);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("robin");

        String actualToken = userService.signup(user);

        assertEquals(actualToken, expectedToken);
    }

    @Test
    public void signup_UserNotFound_Error() {
        user.setUserId(null);
        user.setUsername("batman");
        user.setPassword("robin");

        when(userDao.signup(any())).thenReturn(user);

        String token = userService.signup(user);

        assertNull(token);
    }

    @Test
    public void login_ReturnsJwt_Success() {
        String expectedToken = "12345";

        when(userDao.login(any())).thenReturn(user);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("robin");

        String actualToken = userService.login(user);
        assertEquals(actualToken, expectedToken);
    }

    @Test
    public void login_UserNotFound_Error(){
        user.setUserId(null);
        user.setUsername("batman");
        user.setPassword("robin");

        when(userDao.login(any())).thenReturn(user);

        String token = userService.login(user);

        assertNull(token);
    }

    @Test
    public void update() {
        user.setUserId(1L);
        user.setUsername("batman");
        user.setPassword("robin");

        when(userDao.update(any(), anyLong())).thenReturn(user);

        User tempUser = userService.update(user, user.getUserId());

        assertEquals(tempUser.getUsername(), user.getUsername());
    }

    @Test
    public void delete() {
        // todo
    }
}
