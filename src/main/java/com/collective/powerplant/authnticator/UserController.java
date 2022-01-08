package com.collective.powerplant.authnticator;

import com.collective.powerplant.model.User;
import com.collective.powerplant.model.database.UserDB;
import com.collective.powerplant.service.UserService;
import com.collective.powerplant.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public User login(@RequestBody User user) {

        UserDB userFromDb = userService.findByName(user.getUser());

        if (UserUtil.validatePassword(user.getPassword(),userFromDb.getPassword())){
            String token = UserUtil.getJWTToken(user.getUser());
            user.setPassword(null);
            user.setToken(token);
        }else {
            user.setPassword(null);
            user.setToken("InvalidPassword");
        }
        return user;
    }

    @PostMapping("/Signup")
    public User signUp(@RequestBody User user) {
        UserDB userDB = userService.addUser(user);

        return User.builder().user(userDB.getName()).build();
    }



}
