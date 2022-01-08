package com.collective.powerplant.authnticator;

import com.collective.powerplant.model.User;
import com.collective.powerplant.model.database.UserDB;
import com.collective.powerplant.service.UserService;
import com.collective.powerplant.util.UserUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    UserController userController;


    @Test
    public void signUpUserTest() {

        User user = User.builder().user("testUser").password("testPassword").build();
        UserDB userDB = UserDB.builder().name("testUser").password("hashedTestPassword").build();
        when(service.addUser(any(User.class))).thenReturn(userDB);

        User user1 = userController.signUp(user);

        assertThat(user1.getUser()).isEqualTo(userDB.getName());
    }

    @Test
    public void loginTest(){
        User user = User.builder().user("testUser").password("testPassword").build();
        UserDB userDB = UserDB.builder().name("testUser").password(UserUtil.hashPassword("testPassword")).build();
        when(service.findByName(any(String.class))).thenReturn(userDB);
        User user1 = userController.login(user);
        assertThat(user1.getUser()).isEqualTo(userDB.getName());
        assertThat(user1.getToken()).isNotNull();

    }


}
