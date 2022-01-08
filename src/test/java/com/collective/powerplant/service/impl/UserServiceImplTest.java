package com.collective.powerplant.service.impl;

import com.collective.powerplant.dao.UserDao;
import com.collective.powerplant.model.User;
import com.collective.powerplant.model.database.UserDB;
import com.collective.powerplant.util.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    UserServiceImpl userService;


    @Test
    public void findExistingUser(){
        UserDB testUser = UserDB.builder().name("TestUser").password("TestPassword").build();
        when(userDao.findByName(anyString())).thenReturn(testUser);
        UserDB user = userService.findByName(testUser.getName());
        assertThat(testUser.getName()).isEqualTo(user.getName());
    }

    @Test
    public void saveUser(){
        User user = User.builder().user("TestUser2").password("TestPassword").build();
        UserDB userDb = UserDB.builder()
                .name(user.getUser())
                .password(UserUtil.hashPassword(user.getPassword()))
                .build();
        when(userDao.save(any(UserDB.class))).thenReturn(userDb);
        UserDB userDB = userService.addUser(user);

        assertThat(user.getUser()).isEqualTo(userDB.getName());
        assertThat(UserUtil.validatePassword(user.getPassword(),userDB.getPassword())).isTrue();
    }


}
