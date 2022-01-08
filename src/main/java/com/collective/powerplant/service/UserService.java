package com.collective.powerplant.service;

import com.collective.powerplant.model.User;
import com.collective.powerplant.model.database.UserDB;

public interface UserService {
    UserDB findByName(String name);
    UserDB addUser(User user);
    long count();
}
