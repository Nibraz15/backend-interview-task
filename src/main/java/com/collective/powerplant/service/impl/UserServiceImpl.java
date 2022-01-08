package com.collective.powerplant.service.impl;

import com.collective.powerplant.dao.UserDao;
import com.collective.powerplant.model.User;
import com.collective.powerplant.model.database.UserDB;
import com.collective.powerplant.service.UserService;
import com.collective.powerplant.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public UserDB findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public UserDB addUser(User user) {

        return userDao.save(UserDB.builder()
                            .name(user.getUser())
                            .password(UserUtil.hashPassword(user.getPassword()))
                            .build());
    }

    @Override
    public long count() {
        return userDao.count();
    }
}
