package com.ga.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.ga.entity.User;
import com.ga.entity.UserRole;

public class UserDaoTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private User user;

    @InjectMocks
    private UserRole userRole;

    @InjectMocks
    private UserDaoImpl userDao;

    @Mock
    private UserRoleDao userRoleDao;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Transaction transaction;

    @Before
    public void init() {
        userRole.setRoleId(1L);
        userRole.setName("ROLE_ADMIN");

        user.setUserId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
        user.setUserRole(userRole);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getTransaction()).thenReturn(transaction);

    }

    @Test
    public void signup_User_Success() {
        when(userRoleDao.getRole(anyString())).thenReturn(userRole);

        User savedUser = userDao.signup(user);

        assertNotNull("Test returned null object, expected non-null", savedUser);
        assertEquals(savedUser, user);
    }


}