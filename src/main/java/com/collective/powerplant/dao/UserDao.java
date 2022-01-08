package com.collective.powerplant.dao;

import com.collective.powerplant.model.database.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserDB, Long> {
    UserDB findByName(String user);
}
