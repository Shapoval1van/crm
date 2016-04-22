package com.becomejavasenior.service;


import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserServiceImpl.class)
public class UserServiceTest {


    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private User user;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        whenNew(DaoFactoryImpl.class).withNoArguments().thenReturn(daoFactory);
        when(daoFactory.getUserDao()).thenReturn(userDao);
        userService = new UserServiceImpl();
    }

    @Test
    public void testCreate() throws Exception {
        when(userDao.create(any(User.class))).thenReturn(1);
        userService.create(user);

        verify(userDao).create(user);
    }

    @Test
    public void testGetUserById() throws Exception {
        PowerMockito.when(userDao.getUserById(1)).thenReturn(user);

        User resultUser = userService.getUserById(1);

        verify(userDao).getUserById(1);
        Assert.assertEquals(user, resultUser);

    }

    @Test
    public void testUpdate() throws Exception {
        when(userDao.update(any(User.class))).thenReturn(1);
        userService.update(user);

        verify(userDao).update(user);
    }

    @Test
    public void testDelete() throws Exception {
        when(userDao.delete(any(User.class))).thenReturn(1);
        userService.delete(user);

        verify(userDao).delete(user);
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> companies = new LinkedList<>();

        User firstUser = new User();
        firstUser.setId(1);
        firstUser.setName("First User");

        companies.add(firstUser);

        User secondUser = new User();
        secondUser.setId(2);
        secondUser.setName("Second User");

        companies.add(secondUser);

        when(userDao.findAll()).thenReturn(companies);
        List<User> result = userService.findAll();

        verify(userDao).findAll();
        assertEquals(2, result.size());
    }
}

